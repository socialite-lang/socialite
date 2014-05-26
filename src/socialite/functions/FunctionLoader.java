package socialite.functions;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import socialite.parser.AssignOp;
import socialite.parser.Expr;
import socialite.parser.Function;
import socialite.parser.Rule;
import socialite.tables.Tuple;
import socialite.util.AnalysisException;
import socialite.util.Assert;
import socialite.util.InternalException;
import socialite.util.Loader;
import socialite.util.SociaLiteException;


class FunctionNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2774815654552982394L;

	public FunctionNotFoundException(Exception e) {
		super(e);
	}
}
public class FunctionLoader {
	public static String getFunctionPath(String functionName) {
		return "socialite.functions."+functionName;
	}
	
	public static Class<?> load(String name) {
		String className=name;		
		String fullName = getFunctionPath(className);
		if (Loader.exists(fullName)) {
			return Loader.forName(fullName);
		} else { return null; }
	}	
	public static boolean hasMethod(Class<?> klass, String name, Class<?>[] argTypes) {
		try {
			@SuppressWarnings("unused")
			Method m=klass.getMethod(name, argTypes);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static Method loadMethod(Class<?> klass, Class<?>[] argTypes) {
		try {
			Method m= klass.getMethod("invoke", argTypes);
			return m;
		} catch (SecurityException e) {
			throw new SociaLiteException(e);
		} catch (NoSuchMethodException e) {
			throw new SociaLiteException("Method "+e.getMessage()+" does not exist");
		}
	}
	
	public static Method loadMethod(Class<?> klass, String name, Class<?>[] argTypes) {
		try {
			return klass.getMethod(name, argTypes);
		} catch (SecurityException e) {
			throw new RuntimeException("Cannot access "+name+" method from "+klass+"("+e+")");
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Method["+name+"] does not exist in "+klass+"("+e+")");
		}
	}
		
	public static void loadAllIn(Rule rule) {
		Expr currentExpr=null;
		for (Expr expr:rule.getExprs()) {
			currentExpr=expr;
			if (!expr.isFunctionAssign()) continue;
			AssignOp op=(AssignOp)expr.root;
			Function f = (Function)op.arg2;
			
			try {
				if (!f.isLoaded()) f.load();
			} catch (InternalException e) {
				throw new AnalysisException(e, rule, currentExpr);
			}
		}
		if (rule.getHead().hasFunctionParam()) {
			Function f = rule.getHead().getAggrF();
			try {			
				if (!f.isLoaded()) f.load();
			} catch (InternalException e) {
				throw new AnalysisException(e, rule, rule.getHead());
			}	
		}		
	}
	
	public static void loadAll(List<Rule> rules) {
		for (Rule r:rules) {
			loadAllIn(r);
		}
	}
	
	public static boolean allLoaded(List<Rule> rules) {
		for (Rule r:rules) {
			for (Function f:r.getFunctions())
				if (!f.isLoaded()) 
					return false;
		}
		return true;
	}
}
