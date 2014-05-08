package socialite.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.stringtemplate.v4.ST;

import socialite.codegen.CodeGen;
import socialite.util.Assert;
import socialite.util.InternalException;


public class CmpOp extends Op {
	String op;
	Object arg1, arg2;
	public CmpOp(String _op, Object _arg1, Object _arg2) throws InternalException {
		op = _op;
		arg1=_arg1;
		arg2=_arg2;
		assert !(arg1 instanceof Function);
		assert !(arg2 instanceof Function);		
	}
	
	public ST codegen() {
		ST expr=CodeGen.expr();	
		
		String cmpExpr="("+cmpExprStr()+")";;
		expr.add("expr", cmpExpr);
		return expr;
	}

	public String sig() {
		return descr(true);
	}
	public String toString() {
		return descr(false);
	}
	String descr(boolean sig) {
		String arg1Str=descr(sig, arg1);		
		String arg2Str=descr(sig, arg2);
		return arg1Str + op + arg2Str;
	}
	
	public String cmpExprStr() {
		if (!MyType.isPrimitive(arg1)) {
			return toStringForObjectArgs();
		}
		return arg1 + op + arg2;
	}
	
	String toStringForObjectArgs() {
		if (op.equals("==")) {
			return arg1+".equals("+arg2+")";
		} else if (op.equals("!=")) {
			return "!"+arg1+".equals("+arg2+")";
		} else if (op.equals("<")) {
			return arg1+".compareTo("+arg2+")<0";
		} else if (op.equals("<=")) {
			return arg1+".compareTo("+arg2+")<=0";
		} else if (op.equals(">")) {
			return arg1+".compareTo("+arg2+")>0";
		} else if (op.equals(">=")) {
			return arg1+".compareTo("+arg2+")>=0";
		} else {
			Assert.not_supported();
			return null;
		}
	}

	public void getTypes(Collection<Class> types) {
		if (arg1 instanceof Op) ((Op)arg1).getTypes(types);
		else types.add(MyType.javaType(arg1));
		
		if (arg2 instanceof Op) ((Op)arg2).getTypes(types);
		else types.add(MyType.javaType(arg2));
	}
	
	public Set<Variable> getVars() {
		Set<Variable> vars = new HashSet<Variable>();
		getVariables(vars, arg1);
		getVariables(vars, arg2);
		return vars;
	}
	
	public SortedSet<Const> getConsts() {
		SortedSet<Const> consts = new TreeSet<Const>();
		getConsts(consts, arg1);
		getConsts(consts, arg2);
		return consts;
	}
	
	public Object getLHS() { return arg1; }
	public Object getRHS() { return arg2; }
	public String getOp() { return op; }
	
	public void visit(OpVisitor v) {
		v.visit(this);
		if (arg1 instanceof Op)
			v.visit((Op)arg1);
		if (arg2 instanceof Op)
			v.visit((Op)arg2);
	}
	
	public int opIdReversed() {
		return -opId();
	}
	public int opId() {
		if (op.equals("<")) {
			return -2;
		} else if (op.equals("<=")) {
			return -1;
		} else if (op.equals("==")) {
			return 0;
		} else if (op.equals(">=")) {
			return 1;
		} else if (op.equals(">")) {
			return 2;
		} else {
			throw new RuntimeException("Unsupported cmp-op:"+op);
		}
	}
	/*
	public Set<Variable> getAllVariables() {
		return getVariables(arg1, arg2);
	}

	public Set<Variable> getReadVariables() {
		return getVariables(arg1, arg2);
	}

	public Set<Object> getAllVarsAndConsts() {
		return getVarsAndConsts(arg1, arg2);
	}

	public Set<Object> getReadVarsAndConsts() {
		return getVarsAndConsts(arg1, arg2);
	}*/
}
