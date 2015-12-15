package socialite.dist;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class PathTo {
	public static final Log L=LogFactory.getLog(PathTo.class);

	static String sep=File.separator;

	static String curDir=null;

	// workspace
	public static String cwd() { // current workspace directory
		if (curDir==null) curDir = System.getProperty("user.dir");
		return curDir;
	}
	public static String cwd(String... paths) {
		String s=cwd();
		for (String path:paths)
			s += sep+path;
		return s;
	}
	public static void setcwd(String dir) {
		curDir = dir;
	}
	
	// (local) temporary directory for generated code
	static String tmpDir=System.getProperty("java.io.tmpdir");
	static String outputDir=System.getProperty("socialite.output.dir");
	static String pythonOutputDir=null;
	static {
		if (outputDir==null) outputDir=tmpDir;
		
		pythonOutputDir = outputDir+sep+"org.python.pycode".replace(".",sep);				
		outputDir = outputDir+sep+"socialite";
		new File(outputDir).mkdirs();
		new File(pythonOutputDir).mkdirs();
	}
	
	public static String pythonOutput() { return pythonOutputDir; }	
	private static String output() { return outputDir; }
	public static String output(String path) {
		assert path!=null;
		return output()+sep+path;
	}	
	public static String classOutput() {
		return output()+sep+"classes";
	}
	
	public static String classOutput(String ...paths) {
		String s=classOutput();
		for (String p:paths)
			s += sep+p;
		return s;
	}
	// utils	
	
	// strips non-directory suffix
	public static String dirname(String path) {
		int idx=path.lastIndexOf(sep);
		if (idx==path.length()-1) {
			idx = path.lastIndexOf(sep, idx);
		}
		return path.substring(0, idx);
	}
	
	// strips directory (but NOT suffix)
	public static String basename(String path) {		
		int idx=path.lastIndexOf(sep);
		if (idx==path.length()-1) 
			idx=path.lastIndexOf(sep, idx);
		
		String basename=path.substring(idx+1);
		if (basename.endsWith(sep)) {
			basename = basename.substring(0, basename.length()-2);
		}
		return basename;
	}
	
	public static String concat(String base, String file) {
		return base+sep+file;
	}
	public static String concat(String base, String dir, String file) {
		return base+sep+dir+sep+file;
	}
	public static String concat(String base, String...dir) {
		String s=base;
		for (String d:dir) { s += sep+d; }
		return s;
	}
}
