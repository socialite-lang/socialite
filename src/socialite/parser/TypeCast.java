package socialite.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.stringtemplate.v4.ST;

import socialite.codegen.CodeGen;

public class TypeCast extends Op implements UnaryOp {
	private static final long serialVersionUID = 1864131799421671063L;
	
	public Class<?> type;
	public Object arg;
	public TypeCast(Class<?> _type, Object _arg) {
		type = _type;
		arg = _arg;
	}
	@Override
	public Class<?> opType() {
		return type;
	}
	@SuppressWarnings("rawtypes")
	public void getTypes(Collection<Class> types) { 
		types.add(type);
	}
	public boolean isValid(Class<?> fromType) {
		if (type.equals(fromType)) return true;
		
		if (fromType.isPrimitive()) {
			if (boolean.class.equals(fromType) ||
					boolean.class.equals(type)) {
				return false;
			}
			return true;
		} else {
			if (Boolean.class.equals(fromType) ||
					Boolean.class.equals(type)) {
				return false;
			}
			
			return fromType.isAssignableFrom(type) ||
					type.isAssignableFrom(fromType);
		}
	}
	@Override
	public ST codegen() {
		ST expr=CodeGen.expr();
		String castExpr = "("+type.getSimpleName()+")"+codeStr(arg);
		expr.add("expr", castExpr);
		return expr;
	}
	@Override
	public void visit(OpVisitor v) {
	}
	@Override
	public Set<Variable> getVars() {		
		if (arg instanceof Variable) {
			Set<Variable> vars=new HashSet<Variable>();
			vars.add((Variable)arg);
			return vars;
		} else if (arg instanceof Op) {
			return ((Op)arg).getVars();
		} else {
			return Collections.emptySet();
		}		
	}
	@Override
	public SortedSet<Const> getConsts() {
		if (arg instanceof Const) {
			SortedSet<Const> consts = new TreeSet<Const>();
			consts.add((Const)arg);
			return consts;
		} else if (arg instanceof Op) {
			return ((Op)arg).getConsts();
		} else {
			SortedSet<Const> consts = new TreeSet<Const>();
			return consts;
		}
	}
	@Override
	public String sig() {
		String sig="("+type.getSimpleName()+")";
		if (arg instanceof Op) {
			sig += ((Op)arg).sig();
		} else {
			sig += arg;
		}
		return sig;
	}
	
	@Override
	public String toString() {
		String str="("+type.getSimpleName()+")";
		str += arg;
		return str;
	}
}
