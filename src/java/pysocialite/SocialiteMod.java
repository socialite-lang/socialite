package pysocialite;

import org.python.core.Options;
import org.python.core.Py;
import org.python.core.PyBoolean;
import org.python.core.PyDictionary;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.core.imp;

import socialite.dist.PathTo;
import socialite.util.Info;

public class SocialiteMod {
	PyObject socialite;
	String modUniqueName;
	public SocialiteMod(int threadNum, boolean dist, boolean interactive) {
		String homeDir = System.getProperty("socialite.home", "");
		String modPath = PathTo.concat(homeDir, "src", "pysocialite");
		
		PySystemState sys = Py.getSystemState();
		sys.path.insert(0, new PyString(modPath));
		String jythonLib = PathTo.concat(homeDir, "jython", "Lib");
		sys.path.insert(0, new PyString(jythonLib));
		String sitePackages = PathTo.concat(jythonLib, "site-packages");
		sys.path.insert(0, new PyString(sitePackages));		
		
		socialite = imp.importName("SociaLite", true);

		String[] keywords={"cpu", "dist", "interactive", "verbose"};
		PyObject[] args = new PyObject[4];
		if (threadNum>0) { args[0] = new PyInteger(threadNum); }
		else { args[0] = Py.None; }
		if (dist) { args[1] = Py.True; }
		else { args[1] = Py.False; }
		if (interactive) { args[2] = Py.True; }
		else { args[2] = Py.False; }
		if (Options.verbose>Py.MESSAGE) { args[3] = new PyInteger(Options.verbose); }
		else { args[3] = Py.None; }
		
		socialite.__getattr__(new PyString("init")).__call__(args, keywords);
		
		PyObject impSocialite = imp.importName("jy_impSocialite", true);
		impSocialite.__getattr__("init").__call__();
		
		long uid = System.currentTimeMillis()%100000;
		modUniqueName = "socialite"+uid;
		Preprocess.setSocialiteModule(modUniqueName);
	}
	public String modName() {
		return "socialite";
	}
	public String modUniqueName() {
		return modUniqueName;
	}
	
	public PyObject getModule() {
		return socialite;
	}
	public PyObject __all__() {
		return socialite.__getattr__("__all__");
	}
	public PyObject getAttr(String attrname) {
		return socialite.__getattr__(attrname);
	}
	public PyObject getAttr(PyString attrname) {
		return socialite.__getattr__(attrname);
	}	
}