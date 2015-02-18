package socialite.codegen;


import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.*;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.PathTo;
import socialite.util.Loader;
import socialite.util.SociaLiteException;

public class Compiler {
	public static final Log L = LogFactory.getLog(Compiler.class);	
	static List<File> defaultClassPaths=new ArrayList<File>();
	public static void addToDefaultClassPath(String path) {
		File f = new File(path);
		defaultClassPaths.add(f);
	}
	static LinkedHashMap<String, byte[]> EMPTY_MAP = new LinkedHashMap<String, byte[]>(0);
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	DiagnosticCollector<JavaFileObject> dc = new DiagnosticCollector<JavaFileObject>();
    LinkedHashMap<String, byte[]> compiledClasses = EMPTY_MAP;
    String errorMsg="";
	boolean verbose = false;

    public Compiler() { }
	public Compiler(boolean _verbose) {
		verbose = _verbose;
	}

    public LinkedHashMap<String, byte[]> getCompiledClasses() { return compiledClasses; }
    public String getErrorMsg() { return errorMsg; }

	public boolean compile(String name, String sourceCode) {
		JavaFileObject javaSrc = new JavaSourceFromString(name, sourceCode);
        JavaMemFileManager fileManager = new JavaMemFileManager(compiler.getStandardFileManager(dc, null, null));
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
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, dc, options, null, compUnit);
		boolean success = task.call();
		if (success) {
			compiledClasses = fileManager.getCompiledClasses();
			Loader.loadFromBytes(compiledClasses);
    		}
		if (!success || verbose) {
			errorMsg = "";
			for (Diagnostic diag : dc.getDiagnostics()) {
				if (diag.getKind().equals(Diagnostic.Kind.ERROR)) {
					errorMsg += "in "+name+"\n";
					errorMsg += diag.getKind() + ", line:" + diag.getLineNumber() + ", pos:" + diag.getColumnNumber() + "\n";
					errorMsg += diag.getMessage(null);
					errorMsg += "\n";
				}
			}
		}
		try { fileManager.close(); }
		catch (IOException e) { L.warn("Error while closing FileManager:"+e); }
		return success;
	}
}

class JavaMemFileManager extends ForwardingJavaFileManager {
    static class ClassMemFileObject extends SimpleJavaFileObject {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ClassMemFileObject(String className) {
            super(URI.create("mem:///" + className + Kind.CLASS.extension), Kind.CLASS);
        }
        byte[] getBytes() { return os.toByteArray(); }
        public OutputStream openOutputStream() throws IOException { return os; }
    }
    static boolean isAnonymousClass(String name) {
        int lastDollarIndex = name.lastIndexOf('$');
        if (lastDollarIndex<0) return false;

        String suffix = name.substring(lastDollarIndex+1);
        for (int i=0; i<suffix.length(); i++) {
            if (!Character.isDigit(suffix.charAt(i)))
                return false;
        }
        return true;
    }
    TreeMap<String, ClassMemFileObject> topoSortedClasses =
                new TreeMap<String, ClassMemFileObject>(
                        new Comparator<String>() {
                        // classes are sorted so that outmost class comes first
                            public int compare(String a, String b) {
                                /** "$Nested" in a class name indicates nested table.
                                 *  @see TableCodeGen#nestedTableName(int) */
                                int nestLevelA = StringUtils.countMatches(a, "$Nested");
                                int nestLevelB = StringUtils.countMatches(b, "$Nested");
                                if (nestLevelA == nestLevelB) {
                                    if (isAnonymousClass(a)) return -1;
                                    if (isAnonymousClass(b)) return 1;

                                    return StringUtils.countMatches(a, "$")- StringUtils.countMatches(b, "$");
                                } else {
                                    return nestLevelA - nestLevelB;
                                }

                            }
                        });
    protected JavaMemFileManager(JavaFileManager _fileManager) {
        super(_fileManager);
    }
    @Override
    public JavaFileObject getJavaFileForOutput(Location location,
                                               String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        ClassMemFileObject clazz = new ClassMemFileObject(className);
        if (!Loader.exists(className)) {
            topoSortedClasses.put(className, clazz);
        }
        return clazz;
    }
    public LinkedHashMap<String, byte[]> getCompiledClasses() {
        LinkedHashMap<String, byte[]> compiledClasses = new LinkedHashMap<String, byte[]>();
        for (Map.Entry<String, ClassMemFileObject> entry: topoSortedClasses.entrySet()) {
            compiledClasses.put(entry.getKey(), entry.getValue().getBytes());
        }
        return compiledClasses;
    }

    public void setLocation(JavaFileManager.Location location, Iterable<? extends File> path) throws IOException {
        ((StandardJavaFileManager)fileManager).setLocation(location, path);
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
