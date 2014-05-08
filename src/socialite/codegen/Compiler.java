package socialite.codegen;


import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.security.SecureClassLoader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import javax.tools.JavaFileManager.Location;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.PathTo;
import socialite.engine.Config;
import socialite.util.Assert;
import socialite.util.Loader;
import socialite.util.SociaLiteException;

public class Compiler {
	public static final Log L = LogFactory.getLog(Compiler.class);	
	static List<File> defaultClassPaths=new ArrayList<File>();
	public static void addToDefaultClassPath(String path) {
		File f = new File(path);
		defaultClassPaths.add(f);
	}
	
	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	DiagnosticCollector<JavaFileObject> dc = new DiagnosticCollector<JavaFileObject>();
	String errorMsg="";
	boolean verbose = false;
	Config conf;

	public Compiler(Config _conf) {
		conf=_conf;
		verbose = conf.isVerbose();		
	}
	
	public String getErrorMsg() { return errorMsg; }

	public boolean compile(String name, String sourceCode) {
		JavaFileObject javaSrc = new JavaSourceFromString(name, sourceCode);
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(dc, null, null);
		try {
			File outputDir = new File(PathTo.classOutput());
			Loader.addClassPath(outputDir);
			
			fileManager.setLocation(StandardLocation.CLASS_OUTPUT,Arrays.asList(outputDir));

			// classpath the compiler uses to compile sourceCode
			List<File> classpaths = new ArrayList<File>();
			classpaths.add(outputDir);
			classpaths.add(new File(PathTo.cwd("_classes")));
			for (String path : System.getProperty("java.class.path").split(
									System.getProperty("path.separator"))) {
				classpaths.add(new File(path));
			}
			classpaths.addAll(defaultClassPaths);
			fileManager.setLocation(StandardLocation.CLASS_PATH, classpaths);
		} catch (IOException e) { L.error(e.toString()); }

		Iterable<? extends JavaFileObject> compUnit = Arrays.asList(javaSrc);
		Iterable<String> options = null; //Arrays.asList(new String[] { "-g:none"});
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
				dc, options, null, compUnit);

		boolean success = task.call();
		if (!success || verbose) {
			errorMsg = "";
			for (Diagnostic diag : dc.getDiagnostics()) {
				//errorMsg += diag.getCode()+"\n"; 
				errorMsg += "\n";
				errorMsg += diag.getKind()+"\n";
				//errorMsg += diag.getPosition()+"\n";  
				//errorMsg += diag.getStartPosition()+"\n";
				//errorMsg += diag.getEndPosition()+"\n";
				errorMsg += diag.getSource()+"\n";
				errorMsg += diag.getMessage(null)+"\n";
			}
		}
		try { fileManager.close(); }
		catch (IOException e) { L.warn("Error while closing FileManager:"+e); }
		return success;
	}
}

class JavaSourceFromString extends SimpleJavaFileObject {
	final String code;

	JavaSourceFromString(String name, String code) {
		super(new File(PathTo.classOutput() + File.separator
						+ name.replace('.', File.separatorChar)
						+ Kind.SOURCE.extension).toURI(), Kind.SOURCE);
		this.code = code;
		try {
			new File(toUri()).getParentFile().mkdirs();
			BufferedWriter bw = 
				new BufferedWriter(new FileWriter(super.toUri().getPath()));
			bw.write(code);
			bw.close();
		} catch (IOException e) { throw new SociaLiteException("Cannot write generated code:"+e); }
	}
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return code;
	}
}
