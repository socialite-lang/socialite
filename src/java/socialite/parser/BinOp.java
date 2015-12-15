package socialite.parser;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.stringtemplate.v4.ST;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import socialite.codegen.CodeGen;
import socialite.util.InternalException;

public class BinOp extends Op {
    private static final long serialVersionUID = 1L;

	public String op;
	public Object arg1, arg2;
    public BinOp()  { }
	public BinOp(String _op, Object _arg1, Object _arg2) throws InternalException {
		if (_op.equals("mod")) _op = "%";
		op = _op;
		arg1 = _arg1;
		arg2 = _arg2;
		assert !(arg1 instanceof Function);
		assert !(arg2 instanceof Function);		
	}
	public Class opType() {
		return type(arg1);
	}
	
	public ST codegen() {
		ST expr=CodeGen.expr();
		String binExpr="(";
		binExpr += codeStr(arg1);
		binExpr += op;
		binExpr += codeStr(arg2);		
		binExpr += ")";
		expr.add("expr", binExpr);
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
		
		return "(" + arg1Str + op + arg2Str + ")";
	}
	
	public void getTypes(Collection<Class> types) {
		if (arg1 instanceof Op) ((Op)arg1).getTypes(types);
		else types.add(MyType.javaType(arg1));
		
		if (arg2 instanceof Op) ((Op)arg2).getTypes(types);
		else types.add(MyType.javaType(arg2));
	}
	
	public SortedSet<Const> getConsts() {
		SortedSet<Const> consts = new TreeSet<Const>();
		getConsts(consts, arg1);
		getConsts(consts, arg2);
		return consts;
	}
	
	public Set<Variable> getVars() {
		Set<Variable> vars=new HashSet<Variable>();
		getVariables(vars, arg1);
		getVariables(vars, arg2);
		return vars;
	}
	
	public void visit(OpVisitor v) {
		v.visit(this);
		if (arg1 instanceof Op)
			v.visit((Op)arg1);
		if (arg2 instanceof Op)
			v.visit((Op)arg2);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		char[] _op = new char[in.readInt()];
		for (int i=0; i<_op.length; i++)
			_op[i] = in.readChar();
		op = new String(_op);
		arg1=in.readObject();
		arg2=in.readObject();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(op.length());
		out.writeChars(op);
		out.writeObject(arg1);
		out.writeObject(arg2);
	}
	
	/*
	public Set<Variable> getReadVariables() {
		return getVariables(arg1, arg2);
	}
	public Set<Variable> getAllVariables() {
		return getVariables(arg1, arg2);
	}
	
	public Set<Object> getAllVarsAndConsts() {
		return getVarsAndConsts(arg1, arg2);
	}
	@Override
	public Set<Object> getReadVarsAndConsts() {
		return getVarsAndConsts(arg1, arg2);
	}*/	
}
