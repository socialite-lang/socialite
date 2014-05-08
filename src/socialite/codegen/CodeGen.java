package socialite.codegen;


import gnu.trove.TIntCollection;
import gnu.trove.list.array.TIntArrayList;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.python.modules.synchronize;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import socialite.parser.AggrFunction;
import socialite.parser.Column;
import socialite.parser.Const;
import socialite.parser.Function;
import socialite.parser.MyType;
import socialite.parser.Predicate;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.parser.Variable;
import socialite.util.IdFactory;
import socialite.util.InternalException;
import socialite.util.MySTGroupFile;
import socialite.util.SociaLiteException;

// Utility class for code generation
// used mostly by VisitorCodeGen and UpdaterCodeGen
public class CodeGen {	
	static String visitorBaseGroupFile = "VisitorBase.stg";
	static String visitorGroupFile = "Visitor.stg";
	static String evalGroupFile = "EvalEpoch.stg";
	static String tupleGroupFile = "Tuple.stg";
	
	static STGroup visitorBaseGroup = null;
	static STGroup visitorTmplGroup = null;	
	static STGroup evalTmplGroup = null;
	static STGroup tupleGroup = null;		
	
	static {
		visitorTmplGroup = new MySTGroupFile(VisitorCodeGen.class.getResource(visitorGroupFile),
				"UTF-8", '<', '>');
		synchronized(visitorTmplGroup) {
			visitorTmplGroup.load();
		}
		visitorBaseGroup = new MySTGroupFile(VisitorBaseGen.class.getResource(visitorBaseGroupFile),
				"UTF-8", '<', '>');
		synchronized(visitorBaseGroup) {
			visitorBaseGroup.load();
		}
		evalTmplGroup = new MySTGroupFile(EvalCodeGen.class.getResource(evalGroupFile), 
				"UTF-8", '<', '>');
		synchronized(evalTmplGroup) {
			evalTmplGroup.load();
		}
		tupleGroup = new MySTGroupFile(TupleCodeGen.class.getResource(tupleGroupFile),
				"UTF-8", '<', '>');
		synchronized(tupleGroup) {
			tupleGroup.load();
		}
	}
	
	public static String uniqueVar(String prefix) {
		return prefix+IdFactory.nextVarId();
	}
	
	public static STGroup getVisitorGroup() {
		return visitorTmplGroup;
	}
	public static STGroup getVisitorBaseGroup() {
		return visitorBaseGroup;
	}
	
	public static ST getVisitorST(String name) {
		STGroup group=getVisitorGroup();
		return group.getInstanceOf(name);
	}
	
	public static STGroup getEvalGroup() { 
		return evalTmplGroup;
	}
	
	public static STGroup getTupleGroup() {
		return tupleGroup;
	}

	public static ST expr() { return getVisitorST("expr"); }
	
	public static ST stmts() { return getVisitorST("simpleStmts"); }
	
	public static String capitalizeFirstLetter(String str) {
		String firstLetter = str.substring(0, 1);
		String rest = str.substring(1);		
		return firstLetter.toUpperCase() + rest;
	}

	public static void throwNotImplementedException(ST m) {
		String throwStmt="throw new RuntimeException(\"not implemented\")";
		m.add("stmts", throwStmt);
	}

	public static Class[] getArgTypes(Table t, int startCol, int endCol) {
		Class[] result = new Class[endCol-startCol+1];
		Class[] types = t.types();
		for (int i=startCol; i<=endCol; i++) {
			Class type=types[i];
			result[i-startCol] = type;
		}
		return result;
	}
	public static void fillArgTypes(ST method, Predicate p, int startCol, int endCol) {
		Object[] params = p.getAllInputParams();		
		for (int i=startCol; i<=endCol; i++) {
			String arg="_"+(i-startCol); // fillVisitMethodBody
			method.add("args", MyType.visitTypeName(params[i])+" "+arg);
		}
	}
	
	public static ST findRelevantCase(ST switch_, int caseValue) {
		Object attr = switch_.getAttribute("cases");
		if (attr instanceof List) {
			List cases = (List)attr;
			if (cases!=null) {
				for (Object o:cases) {
					ST case_=(ST)o;
					int val = (Integer)case_.getAttribute("val");
					if (val==caseValue) return case_;
				}
			}
		} else {
			ST case_=(ST)attr;
			if (case_!=null) {
				int val = (Integer)case_.getAttribute("val");
				if(val==caseValue) return case_;
			}
		}
		STGroup group = getVisitorGroup();
		ST mycase = getVisitorGroup().getInstanceOf("case");
		mycase.add("val", caseValue);
		switch_.add("cases", mycase);
		return mycase;
	}
	
	public static ST findSwitch(ST code) {
		Object attr = code.getAttribute("stmts");
		ST switch_;
		if (attr instanceof List)
			switch_ = (ST)((List)attr).get(0);
		else switch_= (ST)attr;
		
		if (switch_==null) {
			STGroup group=getVisitorGroup();
			/* synchronized(group) */ {
				switch_=group.getInstanceOf("switch");
			}
			switch_.add("cond", currentPredicateVar());
			code.add("stmts", switch_);
		}
		return switch_;
	}
	
	public static String currentPredicateVar() {
		return "$currentPredicate";
	}
	
	static ST findTryIfExists(ST code) {
		ST stmts = (ST)code.getAttribute("stmts");
		if (stmts==null) return code;
		if (stmts instanceof ST) {
			String name = stmts.getName();
			if (name.equals("/try") || name.equals("/tryCatch")) {
				return stmts;
			}
		}
		return code;
	}
	static ST findDoWhile0(ST code) {
		ST stmts = (ST)code.getAttribute("stmts");
		if (stmts==null) return code;
		if (stmts instanceof ST) {
			String name = stmts.getName();
			if (name.equals("/doWhile0")) return stmts;
		}
		return code;
	}
	public static ST findCodeBlockFor(ST methodOrIter, Predicate p) {
		ST body=null;
		String name=methodOrIter.getName();
		if (name.equals("/withIterator")) {
			body = methodOrIter;
			return body;
		}
		assert name.equals("/method");
		ST method = methodOrIter;
		if (p.isHeadP()) { // query predicate
			ST code = method;
			body = findDoWhile0(code);
		} else {			
			ST code = method;
			code = findDoWhile0(code);
			code = findTryIfExists(code);
			ST switch_= findSwitch(code);
			ST mycase = findRelevantCase(switch_, p.getPos());			
			body = mycase;
		}
		return body;
	}
	/*
	public static void addToVisitMethodOrIterBody(ST methodOrIter, Predicate p, Object stmt) {
		String name=methodOrIter.getName();
		if (name.equals("method")) {
			ST body=findCodeBlockFor(methodOrIter, p);
			body.add("stmts", stmt);
		} else if (name.equals("withIterator")) {
			ST body=methodOrIter;
			body.add("stmts", stmt);
		} else {
			Assert.impossible("Unexpected template:"+name);
		}
	}
	
	public static void addToVisitMethod(ST method, Predicate p, Object stmt) {
		ST body=findCodeBlockFor(method, p);
		body.add("stmts", stmt);
	}
	*/	
	/*
	static Set<Variable> emptySet=new HashSet<Variable>();
	public static void fillVisitMethodBody(ST m, Predicate p, int startCol, int endCol) {
		fillVisitMethodBody(m, p, startCol, endCol, emptySet);
	}*/
	/* startCol, endCol include the primary index column */
	public static void fillVisitMethodBody(ST method, Predicate p, 
			int startCol, int endCol, Collection<Variable> resolved) {
		fillVisitMethodBody(method, p, startCol, endCol, resolved, new TIntArrayList(0)); 
	}
	public static void fillVisitMethodBody(ST method, Predicate p, 
									int startCol, int endCol, 
									Collection<Variable> resolved, TIntCollection idxbyCols) {
		ST body=findCodeBlockFor(method, p);
		//if (startCol>=1) addFilterForDontCares(body, p, startCol, endCol);
			
		Object[] params = p.getAllParamsExpanded();
		for (int i=startCol; i<=endCol; ) {		
			String arg="_"+(i-startCol); // same as fillArgTypes
			if (params[i] instanceof Variable &&
					((Variable)params[i]).dontCare) {				
				i++; continue;
			}
			if (idxbyCols.contains(i)) {
				i++;
			} else if (resolved.contains(params[i])) {	
				assert !p.isNegated();
				ST if_=generateFilter(params[i], arg);
				body.add("stmts", if_);
				i++;
			} else if (params[i] instanceof Variable) {
				Variable v=(Variable)params[i];
				body.add("stmts", v+"=("+v.type.getSimpleName()+")"+arg);
				i++;
			} else if (params[i] instanceof Function) {
				Function f = (Function)params[i];
				for (Object o:f.getArgs()) {
					if (o instanceof Variable) {
						Variable v=(Variable)o;
						if (!v.dontCare) {
							String var=v.toString();
							body.add("stmts", var+"="+arg);
						}
						i++;
					}
				}				
			} else { // constants
				assert !p.isNegated();
				assert params[i] instanceof Const;
				ST if_=generateFilter(params[i], arg);
				body.add("stmts", if_);
				i++;
			}
		}
	}
	
	static ST generateFilter(Object param, Object arg) {
		ST if_=getVisitorGroup().getInstanceOf("if");
		if (MyType.isPrimitive(param)) if_.add("cond", param+"!="+arg);	
		else if_.add("cond", "!"+param+".equals("+arg+")");
		if_.add("stmts", "break");
		return if_;
	}
	
	public static ST withReadLock(String lockMapVar, Object tableId) {
		assert tableId instanceof Integer ||tableId instanceof String;
		STGroup group=getVisitorGroup();
		ST try_ = getVisitorGroup().getInstanceOf("try");
		try_.add("stmts", lockMapVar+".rlock("+tableId+")");
		try_.add("finally", lockMapVar+".runlock("+tableId+")");
		return try_;
	}
	
	public static ST withReadLock(String lockMapVar, Object tableId, String sliceExpr) {
		assert tableId instanceof Integer ||tableId instanceof String;
		STGroup group=getVisitorGroup();
		ST try_ = getVisitorGroup().getInstanceOf("try");
		String var = uniqueVar("$_slice_");
		try_.add("preStmts", "int "+var+"="+sliceExpr);
		try_.add("stmts", lockMapVar+".rlock("+tableId+", "+var+")");
		try_.add("finally", lockMapVar+".runlock("+tableId+", "+var+")");
		return try_;
	}
	
	@Deprecated
	public static ST withWriteLock(String lockMapVar, Object tableId, String sliceExpr) {
		assert tableId instanceof Integer ||tableId instanceof String;
		STGroup group = getVisitorGroup();
		ST try_ = group.getInstanceOf("try");
		String var = uniqueVar("$_slice_"); 
		try_.add("preStmts", "int "+var+"="+sliceExpr);
		try_.add("stmts", lockMapVar+".wlock("+tableId+", "+var+")");
		try_.add("finally", lockMapVar+".wunlock("+tableId+", "+var+")");
		return try_;
	}
	@Deprecated
	public static ST withoutWriteLock(String lockMapVar, Object tableId, String sliceExpr) {
		assert tableId instanceof Integer ||tableId instanceof String;
		STGroup group = getVisitorGroup();
		ST try_ = group.getInstanceOf("try");
		String var = uniqueVar("$_slice_"); 
		try_.add("preStmts", "int "+var+"="+sliceExpr);
		try_.add("stmts", lockMapVar+".wunlock("+tableId+", "+var+")");
		try_.add("finally", lockMapVar+".wlock("+tableId+", "+var+")");
		return try_;
	}
	
	public static ST withArrayLock(String lockMapVar, int tableid, Object arrayIndex) {
		STGroup group = getVisitorGroup();
		ST sync = group.getInstanceOf("synchronized");
		sync.add("monitor", lockMapVar+".arraylock("+tableid+", "+arrayIndex+")");		
		return sync;
		/*STGroup group = getVisitorGroup();
		ST try_ = group.getInstanceOf("try");
		try_.add("stmts", lockMapVar+".arraylock("+tableid+","+arrayIndex+")");
		try_.add("finally", lockMapVar+".arrayunlock("+tableid+","+arrayIndex+")");
		return try_;*/
	}
	public static ST withLock(String lockMapVar, Object tableId, String sliceExpr) {
		assert tableId instanceof Integer ||tableId instanceof String;
		STGroup group = getVisitorGroup();
		ST try_ = group.getInstanceOf("try");
		String var = uniqueVar("$_slice_"); 
		try_.add("preStmts", "int "+var+"="+sliceExpr);
		
		try_.add("stmts", lockMapVar+".lock("+tableId+", "+var+")");
		try_.add("finally", lockMapVar+".unlock("+tableId+", "+var+")");
		return try_;
	}	
	public static ST withoutLock(String lockMapVar, Object tableId, String sliceExpr) {
		assert tableId instanceof Integer ||tableId instanceof String;
		STGroup group = getVisitorGroup();
		ST try_ = group.getInstanceOf("try");
		String var = uniqueVar("$_slice_"); 
		try_.add("preStmts", "int "+var+"="+sliceExpr);
		try_.add("stmts", lockMapVar+".unlock("+tableId+", "+var+")");
		
		//try_.add("finally", "long $start$=System.currentTimeMillis()");
		try_.add("finally", lockMapVar+".lock("+tableId+", "+var+")");
		//try_.add("finally", "long $end$=System.currentTimeMillis()");
		//try_.add("finally", "if ($end$-$start$>=1) System.out.println(\"relock duration:\"+($end$-$start$))");
		return try_;
	}
	
	public static String tupleClass(Class[] types) {
		String klass = "Tuple";
		for (Class t:types) {
			if (t.equals(int.class)) {
				klass += "_int";
			} else if (t.equals(long.class)) {
				klass += "_long";
			} else if (t.equals(float.class)) {
				klass += "_float";
			} else if (t.equals(double.class)) {
				klass += "_double";
			} else {
				klass += "_Object";
			}
		}
		return klass;
	}
	
	public static String tupleAlloc(Class[] types) {
		String alloc = "new "+tupleClass(types)+"()";
		return alloc;
	}
		
	public static String fastTupleSetter(String tuple, int column, Object o) {
		return tuple+"._"+column+"="+o;
	}
	public static String tupleSetter(String tuple, int column, Object o) {
		if (o instanceof Variable) {
			Variable v=(Variable)o;
			if (v.type.equals(int.class)) {
				return tuple + ".setInt("+column+","+o+")";
			} else if (v.type.equals(long.class)) {
				return tuple + ".setLong("+column+","+o+")";			
			}else if (v.type.equals(float.class)) {
				return tuple + ".setFloat("+column+","+o+")";			
			} else if (v.type.equals(double.class)) {
				return tuple + ".setDouble("+column+","+o+")";			
			} else {
				return tuple + ".setObject("+column+","+o+")";			
			}
		} else if (o instanceof Integer) {
			return tuple + ".setInt("+column+","+o+")";			
		} else if (o instanceof Long) {
			return tuple + ".setLong("+column+","+o+")";			
		} else if (o instanceof Float) {
			return tuple + ".setFloat("+column+","+o+")";			
		} else if (o instanceof Double) {
			return tuple + ".setDouble("+column+","+o+")";
		} else {
			return tuple + ".setObject("+column+","+o+")";
		}
	}
	
	public static String fastTupleGetter(Variable v, String tuple, int column) {
		if (MyType.isPrimitive(v))
			return v+"="+tuple+"._"+column;
		else
			return v+"=("+MyType.javaTypeName(v)+")"+tuple+"._"+column;
	}
	
	public static String tupleGetter(Variable v, String tuple, int column) {
		return tupleGetter(v.type, tuple, column);
	}
	
	public static String tupleGetter(Class type, String tuple, int column) {
		if (type.equals(int.class)) {
			return tuple + ".getInt("+column+")";
		} else if (type.equals(long.class)) {
			return tuple + ".getLong("+column+")";
		} else if (type.equals(float.class)) {
			return tuple + ".getFloat("+column+")";
		} else if (type.equals(double.class)) {
			return tuple + ".getDouble("+column+")";
		} else {
			return "("+(MyType.javaTypeName(type))+")"+tuple + ".getObject("+column+")";
		}
	}
	
	static boolean isPrefixConstant(Predicate p, int prefixSize) {
		for (int i=0; i<prefixSize; i++) {
			if (!isConstant(p.params.get(i))) return false;
		}
		return true;
	}
	static boolean isConstant(Object param) {
		if (!(param instanceof Variable)) return true;
		// XXX: do simple analysis to see if the variable(param) is constant in this rule
		return false;
	}

	public static String getVisitColumns(int start, int end, int arity) {
		if (end == arity-1) return "";
		
		String result="";
		for (int i=start; i<=end; i++) result += "_"+i;
		return result;
	}
}
