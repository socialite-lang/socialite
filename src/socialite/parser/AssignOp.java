package socialite.parser;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.stringtemplate.v4.ST;

import socialite.codegen.CodeGen;
import socialite.util.InternalException;

public class AssignOp extends Op {
	private static final long serialVersionUID = -7554928921566465892L;
	
	public Object arg1, arg2;
	TypeCast cast;
	public AssignOp() { }
	public AssignOp(Object _arg1, Object _arg2) throws InternalException {	
		this(_arg1, null, _arg2);
	}
	public AssignOp(Object _arg1, TypeCast _cast, Object _arg2) throws InternalException {	
		arg1 = _arg1;
		cast = _cast;
		arg2 = _arg2;
		if (!(arg1 instanceof Variable || arg1 instanceof List)) {
			throw new InternalException("cannot assign value(s) to "+arg1);
		}		
		if (arg2 instanceof Function) {
			Function f=(Function)arg2;
			f.setReturnVars(arg1);
		}
		
		if (isMultipleAssign()) {
			Function f=(Function)arg2;
			assert f.getReturns().size()==((List<?>)arg1).size();
		}
	}
	
	public boolean fromFunction() { return arg2 instanceof Function; }
	public Function getFunction() {
		assert fromFunction();
		return (Function)arg2;
	}
	public boolean multiRows() { 
		if (!fromFunction()) return false;
		Function f=(Function)arg2;
		return f.multiRows();
	}
	
	public void computeLhsTypes() throws InternalException {
		if (isMultipleAssign()) {
			computeMultiLhsTypes();
		} else {
			computeSingleLhsType();
		}
	}
	void computeMultiLhsTypes() throws InternalException {
		Function f=(Function)arg2;
		f.computeReturnVarTypes(cast);
	}
	void computeSingleLhsType() throws InternalException {
		Variable lhs = (Variable)arg1;
		if (arg2 instanceof Function) {
			Function f=(Function)arg2;
			f.computeReturnVarTypes(cast);
			return;
		}
		
		@SuppressWarnings("rawtypes")
		Set<Class> types = new LinkedHashSet<Class>();
		getRhsTypes(types);
		if (types.contains(NoType.class)) return;
		
		if (types.size() == 1) {
			lhs.setType(doTypeCast(types.iterator().next()));
		} else {			
			if (!containsOnlyPrimOrStr(types)) {
				Class<?> t=getNonPrimOrStr(types);
				throw new InternalException("Unexpected type ("+t.getSimpleName()+") in "+this);
			}
			
			if (types.contains(String.class)) {
				lhs.setType(doTypeCast(String.class));
			} else if (types.contains(double.class)) {
				lhs.setType(doTypeCast(double.class));
			} else if (types.contains(float.class)) {
				lhs.setType(doTypeCast(float.class));
			} else if (types.contains(long.class)) {
				lhs.setType(doTypeCast(long.class));
			} else if (types.contains(int.class)) {
				lhs.setType(doTypeCast(int.class));
			} else {
				assert false:"impossible";
			}
		}				
	}
	Class<?> doTypeCast(Class<?> fromType) throws InternalException {
		if (cast==null) return fromType;
		
		if (!cast.isValid(fromType)) {
			String msg="Unexpected type cast from "+fromType.getSimpleName()+" to "+cast.type.getSimpleName();
			throw new InternalException(msg);
		}
		return cast.type;
	}
	
	static Class<?> getNonPrimOrStr(@SuppressWarnings("rawtypes") Set<Class> types) {
		for (Class<?> type:types) {
			if (!(MyType.isPrimitive(type)||type.equals(String.class)))
				return type;
		}
		return null;
	}
	static boolean containsOnlyPrimOrStr(@SuppressWarnings("rawtypes") Set<Class> types) {
		for (Class<?> type:types) {
			if (!(MyType.isPrimitive(type)||type.equals(String.class)))
				return false;
		}
		return true;
	}
	
	public ST codegen() {
		ST stmts=CodeGen.stmts();
		if (arg2 instanceof Function) {
			return genFunctionAssign(stmts);
		} else {
			String assign="";
			String type= MyType.javaTypeName(arg1);
			assign += codeStr(arg1)+"=("+type+")"+codeStr(arg2);
			stmts.add("stmts", assign);
			return stmts;
		}
	}	
	
	ST genFunctionAssign(ST stmts) {
		Function f=(Function)arg2;
		if (multiRows()) {
			ST withIter=CodeGen.getVisitorST("withIterator");
			stmts.add("stmts", withIter);
			if (f.isPython()) withIter.add("iterGetter", "(Iterator)"+f.codegen().render());
			else withIter.add("iterGetter", f.codegen().render());
			withIter.add("iterVar", CodeGen.uniqueVar("$iter"));
			String funcRet=CodeGen.uniqueVar("$v");
			withIter.add("var", funcRet);

            if (isMultipleAssign() && f.isArrayType()) {
				@SuppressWarnings("unchecked")
				List<Variable> lhs=(List<Variable>)arg1;
				if (f.isPrimArrayType()) withIter.add("iterType", MyType.javaType(lhs.get(0))+"[]");
				else withIter.add("iterType", "Object[]");
				for (int i=0; i<lhs.size(); i++) {
					Variable v=lhs.get(i);
					String varType = MyType.javaObjectTypeName(v);
					withIter.add("stmts", v+"=("+varType+")"+funcRet+"["+i+"]");
				}
			} else if (f.isPython()) {
				Variable lhs = (Variable)arg1;		
				String pyToJava=MyType.javaObjectTypeName(lhs);				
				if (MyType.isPrimNumberType(lhs.type)) {
					withIter.add("iterType", "Number");
					String valueGetter=valueGetter(lhs.type);
					withIter.add("stmts", lhs+"=("+pyToJava+")((Number)"+funcRet+")"+valueGetter);
				} else {
					withIter.add("iterType", MyType.javaObjectTypeName(lhs));
					withIter.add("stmts", lhs+"=("+pyToJava+")"+funcRet);				
				}
			} else {
				Variable lhs = (Variable)arg1;
                String lhsType;
                if (MyType.javaType(lhs).isArray()) {
                    lhsType = MyType.javaType(lhs).getComponentType().getName()+"[]";
                } else { lhsType = MyType.javaType(lhs).getName(); }
				withIter.add("iterType", lhsType);
				withIter.add("stmts", lhs+"=("+lhsType+")"+funcRet);
			}
			return withIter;
		} else {
			if (isMultipleAssign() && f.isArrayType()) {
				String arrayVar=CodeGen.uniqueVar("$array");
				@SuppressWarnings("unchecked")
				List<Variable> lhs=(List<Variable>)arg1;
				String arrType = "Object[]";
				if (f.isPrimArrayType()) arrType = MyType.javaType(lhs.get(0))+"[]";
				stmts.add("stmts", arrType+" "+arrayVar+"=("+arrType+")"+f.codegen().render());
				for (int i=0; i<lhs.size(); i++) {
					Variable v=lhs.get(i);
					String varType = MyType.javaObjectTypeName(v);
					stmts.add("stmts", v+"=("+varType+")"+arrayVar+"["+i+"]");
				}
			} else if (f.isPython()) {
				Variable lhs=(Variable)arg1;
				String pyToJava=MyType.javaObjectTypeName(lhs);
				if (MyType.isPrimNumberType(lhs.type)) {
					String valueGetter=valueGetter(lhs.type);
					stmts.add("stmts", arg1+"=("+pyToJava+")((Number)"+f.codegen().render()+")"+valueGetter);
				} else {
					stmts.add("stmts", arg1+"=("+pyToJava+")("+f.codegen().render()+")");
				}
			} else {
				stmts.add("stmts", arg1+"="+f.codegen().render());
			}
			return stmts;
		}	
	}
	String valueGetter(Class<?> type) {
		if (type.equals(int.class)) return ".intValue()";
		if (type.equals(long.class)) return ".longValue()";
		if (type.equals(float.class)) return ".floatValue()";
		if (type.equals(double.class)) return ".doubleValue()";
		if (type.equals(short.class)) return ".shortValue()";
		if (type.equals(byte.class)) return ".byteValue()";
		assert false:"impossible";
		return null;
	}
	
	public String toString() {
		return descr(false);
	}
	public String sig() {
		return descr(true);
	}
	String descr(boolean sig) {
		String arg1Str=null;
		if (arg1 instanceof List) {
			arg1Str = "(";
			List<?> l = (List<?>)arg1;
			for (int i=0; i<l.size(); i++) {
				if (i!=0) arg1Str += ", ";
				arg1Str += l.get(i);
			}
			arg1Str += ")";
		} else arg1Str = ""+arg1;
		
		String arg2Str = descr(sig, arg2);
		return arg1Str+"="+arg2Str;
	}
	
	
	public boolean isMultipleAssign() { return arg1 instanceof List; }
	
	public boolean isArrayAssign() {
		if (arg2 instanceof Function) {
			Function f=(Function)arg2;
			return f.isArrayType();
		}
		return false;
	}
	
	public void getRhsTypes(@SuppressWarnings("rawtypes") Collection<Class> types) throws InternalException {
		if (arg2 instanceof Op) {
			((Op)arg2).getTypes(types);
		} else if (arg2 instanceof Function) {
			Function f = (Function)arg2;
			if (!f.isLoaded()) {
				types.add(NoType.class);
				return;
			}
				
			if (f.getReturns().size()>1) {
				for (int i=0; i<f.getReturns().size(); i++) {
					Class<?> t = MyType.javaType(f.getReturns().get(0));
					types.add(t);
				}
			} else {
				assert f.getReturns().size()==1;
				Class<?> t = MyType.javaType(f.getReturns().get(0));
				types.add(t);
			}
		} else {
			Class<?> t = MyType.javaType(arg2);
			types.add(t);
		}
	}
	
	public Set<Variable> getRhsVars() {
		Set<Variable> vars=new LinkedHashSet<Variable>();
		if (arg2 instanceof Op) {
			vars.addAll(((Op)arg2).getVars());
		} else if (arg2 instanceof Function) {
			Function f = (Function)arg2;
			vars.addAll(f.getInputVariables());
		} else if (arg2 instanceof Variable) {
			vars.add((Variable)arg2);
		}
		return vars;
	}
	@SuppressWarnings("unchecked")
	public Set<Variable> getLhsVars() {
		Set<Variable> vars = new HashSet<Variable>();
		if (arg1 instanceof Variable) {
			vars.add((Variable)arg1);
		} else {
			for (Variable v:(List<Variable>)arg1)
				vars.add(v);
		}
		return vars;
	}
	
	public SortedSet<Const> getConsts() {
		SortedSet<Const> consts = new TreeSet<Const>();
		getConsts(consts, arg2);
		return consts;
	}
	public Set<Variable> getVars() {
		Set<Variable> vars=getLhsVars();		
		getVariables(vars, arg2);
		return vars;
	}
	public void visit(OpVisitor v) {
		v.visit(this);
		if (arg2 instanceof Op) 
			v.visit((Op)arg2);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		arg1=in.readObject();
		arg2=in.readObject();
		if (in.readBoolean()) {
			cast = new TypeCast();
			cast.readExternal(in);
		}
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(arg1);
		out.writeObject(arg2);
		out.writeBoolean(cast!=null);
		if (cast!=null) cast.writeExternal(out);
	}
}
