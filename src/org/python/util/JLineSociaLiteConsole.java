package org.python.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import org.python.core.ParserFacade;
import org.python.core.Py;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.core.imp;
import org.python.util.InteractiveConsole;
import org.python.util.JLineConsole;

import pysocialite.Preprocess;
import pysocialite.SocialiteMod;
import socialite.functions.PyInterp;
import socialite.util.Info;
import socialite.util.SociaLiteException;

import org.apache.commons.lang3.CharEncoding;

public class JLineSociaLiteConsole extends JLineConsole {
	SocialiteMod mod;
	boolean inQuery;

	public JLineSociaLiteConsole(SocialiteCommandLineOptions opt) {
		super(null, CONSOLE_FILENAME);
		PyInterp.set(this);
		initSocialite(opt);
	}

	public static String getDefaultBanner() {
		String help = "Type \"help\" for more information.\nType quit() to quit.";
		return String.format("SociaLite %s on %s\n%s", Info.version(), PySystemState.platform, help);
	}

	public void initSocialite(SocialiteCommandLineOptions opt) {
		mod = new SocialiteMod(opt.threadNum, opt.dist, opt.interactive);
		getLocals().__setitem__(new PyString(mod.modName()), mod.getModule());
		getLocals().__setitem__(new PyString(mod.modUniqueName()), mod.getModule());
		PyList all = (PyList)mod.__all__();
		for (int i=0; i<all.size(); i++) {
			String k=(String)all.get(i);
			PyObject attr=mod.getAttr(k);
			getLocals().__setitem__(k, attr);
		}
	}
	
	@Override
	public boolean push(String line) {
		if (inQuery) {
			if (hasDeclEnd(line)) {
				inQuery = false;
				return super.push(line);
			} else {
				if (line.trim().length()>0)
					buffer.append("\n"+line);
				return true;
			}
		} else {
			if (hasDeclBegin(line) && !hasDeclEnd(line)) {
				inQuery = true;
				buffer.append("\n"+line);
				return true;
			}
		}
		return super.push(line);
	}
	
	@Override
	public boolean runsource(String source, String filename) {
		String gen = Preprocess.run(source);
		return super.runsource(gen, filename);
	}
	
	boolean hasDeclBegin(String line) {
		if (line.trim().indexOf('`') == 0)
			return true;
		return false;
	}
	boolean hasDeclEnd(String line) {
		String trimmed = line.trim();
		if (trimmed.lastIndexOf('`') == trimmed.length()-1)
			return true;
		return false;
	}

	String fileToString(InputStream is, String encoding) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, encoding));
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			stringBuffer.append(line).append("\n");
		}
		return stringBuffer.toString();
	}
	
	String getEncoding(InputStream is) {
		Method readEncoding;
		try {
			readEncoding = ParserFacade.class.getDeclaredMethod("readEncoding", InputStream.class);
			readEncoding.setAccessible(true);
			String encoding = (String)readEncoding.invoke(null, new Object[]{is});
			if (encoding==null) encoding=CharEncoding.US_ASCII;
			return encoding;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
    public void execfile(InputStream is, String filename) {
		try {
			BufferedInputStream bis = new BufferedInputStream(is);
			String encoding = getEncoding(bis);
			String src = fileToString(bis, encoding);
			String gen = Preprocess.run(src);
			bis.close();
			InputStream is2 = new ByteArrayInputStream(gen.getBytes(encoding));
			super.execfile(is2, filename);
			is2.close();
		} catch (IOException e) {
			throw Py.IOError(e);
		}
	}
}
