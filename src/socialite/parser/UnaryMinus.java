package socialite.parser;


import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


import org.stringtemplate.v4.ST;

import socialite.codegen.CodeGen;
import socialite.util.AnalysisException;
import socialite.util.InternalException;

public class UnaryMinus extends Op implements UnaryOp {
    private static final long serialVersionUID = 1L;

	Object arg;
    public UnaryMinus() { }
	public UnaryMinus(Object _arg) throws InternalException {
		arg = _arg;
		assert !(arg instanceof Function);
	}
	public Class opType() {
		return type(arg);
	}
	public ST codegen() {
		ST expr=CodeGen.expr();
		expr.add("expr", "(-"+codeStr(arg)+")");
		return expr;
	}

	public String toString() {
		return descr(false);
	}
	public String sig() {
		return descr(true);
	}
	String descr(boolean sig) {
		return "(-"+descr(sig, arg)+")";
	}
	
	public void getTypes(Collection<Class> types) {
		if (arg instanceof Op) {
			LinkedHashSet<Class> argTypes = new LinkedHashSet<Class>();
			for (Class type:argTypes) {
				if (!type.isPrimitive()) 
					throw new AnalysisException("Cannot use unary minus to non primitive type:"+this);
			}
			((Op)arg).getTypes(types);
		} else {
			Class type=MyType.javaType(arg);
			if (!type.isPrimitive()) 
				throw new AnalysisException("Cannot use unary minus to non primitive type:"+this); 
			types.add(type);
		}
	}
	
	public Set<Variable> getVars() {
		Set<Variable> vars = new HashSet<Variable>();
		getVariables(vars, arg);
		return vars;
	}
	
	public SortedSet<Const> getConsts() {
		SortedSet<Const> consts = new TreeSet<Const>();
		getConsts(consts, arg);
		return consts;
	}
	
	public void visit(OpVisitor v) {
		v.visit(this);
		if (arg instanceof Op)
			v.visit((Op)arg);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		arg=in.readObject();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(arg);
	}
	/*
	public Set<Variable> getAllVariables() {
		return getVariables(arg);
	}
	public Set<Variable> getReadVariables() {
		return getVariables(arg);
	}
	public Set<Object> getAllVarsAndConsts() {
		return getVarsAndConsts(arg);
	}
	public Set<Object> getReadVarsAndConsts() {
		return getVarsAndConsts(arg);
	}	
	*/
}
