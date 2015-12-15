package socialite.parser;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.stringtemplate.v4.ST;

import socialite.codegen.CodeGen;

public class TypeCast extends Op implements UnaryOp {
	private static final long serialVersionUID = 1L;
	
	public Class<?> type;
	public Object arg;
    public TypeCast() { }
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

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		char[] tmp = new char[in.readInt()];
        for (int i=0; i<tmp.length; i++) {
            tmp[i] = in.readChar();
        }
        String typeName = new String(tmp);
        if (typeName.equals("int")) {
            type = int.class;
        } else if (typeName.equals("long")) {
            type = long.class;
        } else if (typeName.equals("float")) {
            type = float.class;
        } else if (typeName.equals("double")) {
            type = double.class;
        } else {
            assert !typeName.contains("."):"Not supported type:"+typeName;
            type = Class.forName(typeName);
        }
		arg = in.readObject();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		String typeName = type.getName();
		out.writeInt(typeName.length());
		out.writeChars(typeName);
		out.writeObject(arg);
	}
}
