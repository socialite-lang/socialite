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
	public static void setSocialiteModule(String mod) { 
		PySocialiteLexer.setSocialiteModule(mod);
	}
	public static void setSubstFunc(PyObject subst) {
		PySocialiteLexer.setSubstFunc(subst);
	}

	public static String run(String src) throws Exception {
		if (src.indexOf('`') < 0) return src;
		try {
			PySocialiteLexer lexer = new PySocialiteLexer(new ANTLRStringStream(src));
			PySocialiteParser parser = new PySocialiteParser(new CommonTokenStream(lexer));
			String processed = parser.prog();
			return processed;
		} catch(Exception e) {
			throw new SociaLiteException(e);
		}
	}
	public static void main(String[] args) throws Exception {
		PySocialiteLexer lexer = new PySocialiteLexer(new ANTLRFileStream("./test.py"));
		PySocialiteParser parser = new PySocialiteParser(new CommonTokenStream(lexer));
		String processed = parser.prog();
		System.out.println(processed);
	} 
}
