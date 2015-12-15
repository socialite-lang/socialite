	package pysocialite;

import pysocialite.antlr.PySocialiteLexer;
import pysocialite.antlr.PySocialiteParser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import socialite.util.SociaLiteException;
import org.python.core.PyObject;

public class Preprocess {
	static String socialiteModule = null;
	public static void setSocialiteModule(String mod) {
		socialiteModule = mod;
		PySocialiteLexer.setSocialiteModule(mod);
	}
	public static String getSocialiteModule() {
		return socialiteModule;
	}
	public static synchronized String run(String src) {
		if (src.indexOf('`') < 0) return src;
		try {
			PySocialiteLexer lexer = new PySocialiteLexer(new ANTLRStringStream(src));
			PySocialiteParser parser = new PySocialiteParser(new CommonTokenStream(lexer));
			String processed = parser.prog();
			return processed;
		} catch(Exception e) {
			if (e instanceof SociaLiteException) {
				throw (SociaLiteException)e;
			} else {
				throw new SociaLiteException(e);
			}
		}
	}
	public static void main(String[] args) throws Exception {
		PySocialiteLexer lexer = new PySocialiteLexer(new ANTLRFileStream("./test.py"));
		PySocialiteParser parser = new PySocialiteParser(new CommonTokenStream(lexer));
		String processed = parser.prog();
		System.out.println(processed);
	} 
}