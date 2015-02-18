package socialite.parser;


import java.io.Externalizable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.stringtemplate.v4.ST;

import socialite.util.Assert;

public abstract class Op implements Externalizable {
	public abstract ST codegen();
	@SuppressWarnings("rawtypes")
	public void getTypes(Collection<Class> types) { Assert.not_implemented(); }
	public Set<Variable> getLhsVars() {
		return new HashSet<Variable>();
	}
	public abstract void visit(OpVisitor v);
	public abstract Set<Variable> getVars();	
	public abstract SortedSet<Const> getConsts();

	public abstract String sig();
	
	public Class type(Object arg) {
		if (arg instanceof BinOp) {
			return MyType.javaType(((BinOp)arg).opType());
		} else if (arg instanceof UnaryOp) {
			return MyType.javaType(((UnaryOp)arg).opType());
		} else {
			assert arg instanceof Variable || arg instanceof Const;
			return MyType.javaType(arg);
		}
	}
	
	protected void getConsts(SortedSet<Const> consts, Object arg) {		
		if (arg instanceof Variable) {
			// pass
		} else if (arg instanceof Function) {
			Function f = (Function)arg;
			for (Object a:f.getArgs()) {
				if (a instanceof Const)
					consts.add((Const)a);
			}
		} else if (arg instanceof Op) {
			Op op = (Op)arg;
			consts.addAll(op.getConsts());
		} else if (arg instanceof List) {
			for (Object o:(List)arg) 
				getConsts(consts, o);
		} else {
			consts.add((Const)arg);
		}
	}
	
	protected void getVariables(Set<Variable> vars, Object arg) {
		if (arg instanceof Op) {
			Op op = (Op)arg;
			vars.addAll(op.getVars());
		} else if (arg instanceof Variable) {
			Variable v=(Variable)arg;
			vars.add(v);
		} else if (arg instanceof Function) {
			Function f=(Function)arg;
			vars.addAll(f.getVariables());
		} else if (arg instanceof List) {
			for (Object o:(List)arg)
				getVariables(vars, o);
		} else {
			assert arg instanceof Const;
		}
	}
		
	String codeStr(Object o) {
		if (o instanceof Op) 
			return ((Op)o).codegen().render();
		else return o.toString();
	}
	
	String descr(boolean sig, Object arg) {
		String descr;
		if (arg instanceof Const) {
			if (sig) descr = ""+arg;
			else descr = ((Const)arg).constValStr();
		} else if (arg instanceof Op) {
			if (sig) descr = ((Op)arg).sig();
			else descr = ""+arg;
		} else if (arg instanceof Variable) {			
			descr = ""+arg;
		} else {
			assert arg instanceof Function;
			descr = ((Function)arg).descr(sig);
		}
		return descr;
	}
}
