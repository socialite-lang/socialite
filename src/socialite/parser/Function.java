package socialite.parser;

import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.iterator.TFloatIterator;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.iterator.TIterator;
import gnu.trove.iterator.TLongIterator;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.python.core.CodeFlag;
import org.python.core.CompilerFlags;
import org.python.core.PyBaseCode;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyStringMap;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.stringtemplate.v4.ST;

import socialite.codegen.CodeGen;
import socialite.functions.FunctionLoader;
import socialite.functions.PyInterp;
import socialite.functions.PyInvoke;
import socialite.functions.returns;
import socialite.type.Utf8;
import socialite.util.IdFactory;
import socialite.util.InternalException;
import socialite.util.ReflectionUtil;
import socialite.util.SociaLiteException;
import socialite.collection.SArrayList;

public class Function implements Param {
	static final long serialVersionUID = 1;
	
	protected String name;
	protected SArrayList<Variable> returns;	
	protected SArrayList<Param> args;

	boolean isArrayType=false;
	boolean isPrimArrayType=false;
	int pyfuncIdx=-1;
	transient String pyname;
	transient Class<?> klass;
	transient Method method;
	transient PyFunction pyfunc;
		
	public Function() { }
	public Function(String _name, List<Param> _args) {
		if (_name.indexOf('.')>=0) name = _name; 
		else name = "Builtin."+_name;

		if (_args==null) args = new SArrayList<Param>(0);
		else args = new SArrayList<Param>(_args);

		returns = null;
	}
	
	public Class<?> klass() {
		if (klass==null) {
			try { load(); }
			catch (Exception e) { throw new SociaLiteException(e); }
		}
		return klass;
	}
	public Set<Variable> getVariables() {
		Set<Variable> vars = new LinkedHashSet<Variable>();
		for (Object o : getArgs()) {
			if (o instanceof Variable)
				vars.add((Variable) o);
		}
		return vars;
	}

	public boolean isAggregate() { return false; }
	public boolean isInRuleHead() { return false; }
	public boolean isLoaded() { return method != null || pyfunc != null; }
	public boolean isPython() {
		assert isLoaded();
		return pyfunc != null;
	}
	
	public boolean isArrayType() {
		assert isLoaded();
		return isArrayType;
	}
	public boolean isPrimArrayType() {
		assert isLoaded();
		return isPrimArrayType;
	}

	@SuppressWarnings("unchecked")
	public void setReturnVars(Object ret) throws InternalException {
		assert ret instanceof Variable || ret instanceof List;
		SArrayList<Variable> vars;
		if (ret instanceof Variable) {
			vars = new SArrayList<Variable>();
			vars.add((Variable) ret);
		} else {
			vars = new SArrayList<Variable>((List<Variable>)ret);
		}
		assert returns == null;
		for (Object o : vars) {
			if (!(o instanceof Variable)) {
				throw new InternalException("cannot assign value(s) to "+o);
			}
		}
		returns = vars;
	}

	boolean allArgsTyped() {
		for (Object o : getArgs()) {
			if (o instanceof Variable) {
				Variable v = (Variable) o;
				if (!v.hasType())
					return false;
			}
		}
		return true;
	}

	public boolean multiRows() {
		if (method!=null) {
			Class<?> type = getMethodReturnType();
			return Iterator.class.isAssignableFrom(type) ||
					TIterator.class.isAssignableFrom(type);
		} else {
			CompilerFlags flags = ((PyBaseCode)pyfunc.func_code).co_flags;
			return flags.isFlagSet(CodeFlag.CO_GENERATOR);
		}
	}
	public boolean singleRow() { return !multiRows(); }

	@SuppressWarnings("rawtypes")
	Class[] makeArgTypes() throws InternalException {
		Class[] argTypes = new Class[getArgs().size()];
		for (int i = 0; i < getArgs().size(); i++) {
			Object o = getArgs().get(i);
			argTypes[i] = MyType.javaType(o);
		}
		return argTypes;
	}

	void throwIfNotStatic(Method m) throws InternalException {
		if (!Modifier.isStatic(m.getModifiers())) {
			String msg=klass().getSimpleName()+"."+m.getName()+"(";
			for (int i=0; i<m.getParameterTypes().length; i++) {
				if (i!=0) msg += ", ";
				msg += m.getParameterTypes()[i].getSimpleName();
			}
			msg += ")";
			throw new InternalException(msg+" is not decared static");
		}
	}
	
	void loadPyFunc() throws InternalException {
		if (name.startsWith("Builtin.")) pyname = name.substring("Builtin.".length());
		else pyname = name;
		
		if (pyname.indexOf('.')>=0) throw new InternalException("Cannot find $"+pyname);

		pyfunc = PyInterp.load(pyname);			
		if (pyfunc==null) throw new InternalException("Cannot find $"+name);
		
		if (pyfunc.func_defaults!=null && pyfunc.func_defaults.length>0) 
			throw new InternalException("Default values for args are not supported:$"+pyname);
		
		CompilerFlags f = ((PyBaseCode)pyfunc.func_code).co_flags;
		if (f.isFlagSet(CodeFlag.CO_VARARGS)||f.isFlagSet(CodeFlag.CO_VARKEYWORDS)) { 
			throw new InternalException("Varargs/varkeywords are not supported:$"+pyname);
		}
		
		if (!(pyfunc.func_code instanceof PyBaseCode)) {
			throw new InternalException("Unexpected func_code type("+pyfunc.func_code.getClass().getSimpleName()+") for "+pyname);
		}		
		
		int idx = PyInvoke.getRegisteredIdx(pyfunc);
		if (idx < 0) {
			if (pyfuncIdx < 0) pyfuncIdx = IdFactory.nextPyFuncId();
			PyInvoke.register(pyfuncIdx, pyfunc);
		} else {
			pyfuncIdx = idx;
		}
	}
	String className() {
		assert name.indexOf('.') >= 0;
		return name.substring(0, name.indexOf('.'));
	}
	String methodName() {
		return name.substring(name.indexOf('.')+1);
	}
	void loadReally() throws InternalException {
		klass = FunctionLoader.load(className());
		if (klass==null) {
			loadPyFunc();
			return;
		}
		Class<?>[] argTypes = makeArgTypes();
		if (FunctionLoader.hasMethod(klass(), methodName(), argTypes)) {
			method = FunctionLoader.loadMethod(klass(), methodName(), argTypes);			
		} else { 
			method = ReflectionUtil.getCompatiblePublicMethod(klass(), methodName(), argTypes); 
		}
		if (method==null) {
			loadPyFunc();
			return;
		}
		if (method==null) {
			String args="";
			for (int i=0; i<argTypes.length; i++) {
				if (i!=0) args+=",";
				args += argTypes[i].getSimpleName();					
			}
			throw new InternalException("Cannot find "+name()+"("+args+")");
		}
		throwIfNotStatic(method);
	}
	
	
	Class<?> getMethodReturnType() {
		if (method!=null) {
			return method.getReturnType();
		} else {
			assert pyfunc != null;		
			PyStringMap fmap = (PyStringMap)((PyFunction)pyfunc).getDict();
			PyObject returns = fmap.__finditem__("returns");		
			if (returns instanceof PyType) {
				return PyInvoke.py2javaType((PyType)returns);
			} else { 
				return Object[].class; 
			}
		}
	}
	
	public String nameWithArgTypes() {
		String s=name()+"(";
		boolean first=true;
		for (Object a:getArgs()) {
			Class<?> t=MyType.javaType(a);			
			if (!first) s+=" ,";
			if (t.isArray()) s += t.getComponentType().getSimpleName();
			else s += t.getSimpleName();
			first=false;
		}
		return s+")";
	}
	
	public void computeReturnVarTypes(TypeCast cast) throws InternalException {
		if (!isLoaded()) return;
		if (method!=null) computeJavaReturnVarTypes(cast);
		else computePyReturnVarTypes(cast);
	}
	
	Class<?>[] getTypesFromJavaAnnotation() {
		returns ann=method.getAnnotation(returns.class);
		if (ann==null) return null;
		return ann.value();
	}
	Class<?> doTypeCast(TypeCast cast, Class<?> fromType) throws InternalException  {
		if (cast==null) return fromType;
		
		if (!cast.isValid(fromType)) {
			String msg="Unexpected type cast from "+fromType.getSimpleName()+" to "+cast.type.getSimpleName();
			throw new InternalException(msg);  
		}
		return cast.type;
	}
	void computeJavaReturnVarTypes(TypeCast cast) throws InternalException {
		Class<?> retType = getMethodReturnType();		
		if (retType.equals(Iterator.class)) {
			Type genRetType = method.getGenericReturnType();
			if (!(genRetType instanceof ParameterizedType)) {
				String msg = "Require parameterized Iterator type such as Iterator<String>.";
				throw new InternalException(msg);
			}
            Type paramType = ((ParameterizedType) genRetType).getActualTypeArguments()[0];
			if (paramType instanceof GenericArrayType) {
            	Type compType=((GenericArrayType)paramType).getGenericComponentType();
            	retType = (Class<?>)compType;
				computeJavaArrayReturnVarTypes(cast, retType);	
			} else {
            	retType = (Class<?>)paramType;
            	if (retType.isArray() && getReturns().size()>=2) {
					computeJavaArrayReturnVarTypes(cast, retType);
				} else {
					retType = doTypeCast(cast, retType);
					Variable v=getReturns().get(0);
					v.setType(retType);
            	}
			}
		} else if (TIterator.class.isAssignableFrom(retType)) {
			String msg="Unexpected return type from $"+name;
			if (getReturns().size()!=1) throw new InternalException(msg);
			Variable v = (Variable) getReturns().get(0);
			if (retType.equals(TIntIterator.class)) v.setType(doTypeCast(cast, int.class));
			else if (retType.equals(TLongIterator.class)) v.setType(doTypeCast(cast, long.class));
			else if (retType.equals(TFloatIterator.class)) v.setType(doTypeCast(cast, float.class));
			else if (retType.equals(TDoubleIterator.class)) v.setType(doTypeCast(cast, double.class));			
			else throw new InternalException(msg);
		} else if (retType.isArray()) {
			computeJavaArrayReturnVarTypes(cast, retType);
		} else {
			if (getReturns().size()!=1) 
				throw new InternalException("Unexpected return type from $"+name);
			Variable v = (Variable) getReturns().get(0);
			retType = doTypeCast(cast, retType);
			v.setType(retType);
		}		
	}
	void computeJavaArrayReturnVarTypes(TypeCast cast, Class<?> methodRetType) throws InternalException {
		if (getReturns().size()==1) {
			methodRetType = doTypeCast(cast, methodRetType);
			Variable v=getReturns().get(0);
			v.setType(methodRetType);
		} else {
			Class<?>[] types=getTypesFromJavaAnnotation();
			isArrayType=true;
			if (types==null) {
				Class<?> arrType = methodRetType;
				if (methodRetType.getComponentType().isPrimitive()) {
					isPrimArrayType=true;
				}
				if (!arrType.isArray()) {
					String msg = "Expecting an array return from $"+name;
					throw new InternalException(msg);
				}
				Class<?> elemType = arrType.getComponentType();
				elemType = doTypeCast(cast, elemType);				
				for (int i=0; i<getReturns().size(); i++) {
					getReturns().get(i).setType(elemType);
				}				
				return;
			} else {
				if (cast!=null) throw new InternalException("Unexpected type cast to "+cast.type.getSimpleName());
				
				if (types.length!=getReturns().size()) {
					String msg = "Number of returns does not match for $"+name;
					throw new InternalException(msg);
				}
				for (int i=0; i<types.length; i++) {
					getReturns().get(i).setType(types[i]);
				}
			}
		}
	}

	Class<?>[] typesFromPyTuple(PyTuple pytypes) throws InternalException {
    if (pytypes==null) {
      String msg = "Missing @returns in "+pyname+" for return type annotation";
      throw new InternalException(msg);
    }
		List<Class<?>> types = new ArrayList<Class<?>>();
		for (int i=0; i<pytypes.size(); i++) {
			assert pytypes.__finditem__(i) instanceof PyType;
			Class<?> type=PyInvoke.py2javaType((PyType)pytypes.__finditem__(i));			
			types.add(type);
		}
		return types.toArray(new Class[0]);
	}
	void computePyReturnVarTypes(TypeCast cast) throws InternalException {
		PyStringMap fmap = (PyStringMap)((PyFunction)pyfunc).getDict();
		PyObject pytypes = fmap.__finditem__("returns");
		String msg="Unexpected return type from $"+name();
		if (pytypes instanceof PyType) {
			if (getReturns().size()>1) 
				throw new InternalException(msg);			
			Class<?> type = doTypeCast(cast, PyInvoke.py2javaType((PyType)pytypes));
			getReturns().get(0).setType(type);
		} else {
			assert pytypes instanceof PyTuple;
			isArrayType=true;
			Class<?>[] types = typesFromPyTuple((PyTuple)pytypes);
			
			if (types.length!=getReturns().size()) 
				throw new InternalException(msg);			
			for (int i=0; i<types.length; i++) {
				getReturns().get(i).setType(types[i]);
			}
		}
	}
	
	public void load() throws InternalException {
		if (!allArgsTyped()) return;

		loadReally();	
		//computeReturnVarTypes();
	}

	void getReturnTypesForTuple() throws InternalException {
		if (method!=null) {
			getJavaReturnTypesForTuple();
		} else {			
			getPyReturnTypesForTuple();
		}
	}
	void getPyReturnTypesForTuple() throws InternalException {
		PyStringMap fmap = (PyStringMap)((PyFunction)pyfunc).getDict();
		PyObject returns = fmap.__finditem__("returns");
		
		Class<?>[] types = typesFromPyTuple((PyTuple)returns);
		if (!(types.length == getReturns().size())) {
			String msg = "Unexpected number of returns from "+pyname;
			throw new InternalException(msg);
		}
		for (int i = 0; i < types.length; i++)
			getReturns().get(i).setType(types[i]);
	}
	void getJavaReturnTypesForTuple() throws InternalException {
		Class<?> argTypes[] = null;
		Method tupleType = FunctionLoader.loadMethod(klass(), "tupleType", argTypes);
		Class<?> types[] = null;
		try {
			types = (Class[]) tupleType.invoke(null, (Object[])null);
		} catch (Exception e) {
			String msg = "'public static Class[] tupleType()' not in "+klass().getSimpleName();
			throw new InternalException(msg);
		}
		if (!(types.length == getReturns().size())) {
			String msg = "Unexpected number of returns from "+name;
			throw new InternalException(msg);
		}
		for (int i = 0; i < types.length; i++)
			getReturns().get(i).setType(types[i]);
	}

	public ST codegen() {
		ST expr = CodeGen.expr();		
		String invoke = invokeBegin();
		for (int i = 0; i < getArgs().size(); i++) {
			invoke += getArgs().get(i);
			if (i != getArgs().size() - 1)
				invoke += ", ";
		}
		invoke += ")";
		expr.add("expr", invoke);
		return expr;
	}
	
	String invokeBegin() {
		if (method!=null) return name()+"(";
		
		assert pyfunc!=null;
		String begin="PyInvoke.invoke(getWorkerId(), "+pyfuncIdx;
		if (getArgs().size()>0) begin += ", ";
		return begin;		
	}
	public static String javaTypeName(Variable v) {
		return MyType.javaObjectTypeName(v);
	}
	
	public String signature() {
		return descr(true);
	}
	public String toString() {
		return descr(false);
	}
	String descr(boolean sig) {
		String str = "$" + name();
		if (getArgs() != null) {
			str += "(";
			for (int i = 0; i < getArgs().size(); i++) {
				Object o = getArgs().get(i);
				if (sig) str += o;	
				else {
					if (o instanceof Const) {
						Const c=(Const)o;
						if (c.val instanceof String) {
							str += "\""+c.val+"\"";
						} else if (c.val instanceof Utf8) {
							str += "u\""+c.val+"\"";
						} else str += c.val;
					} else str += o;
				}
				if (i != getArgs().size() - 1)
					str += ", ";
			}
			str += ")";
		}
		return str;
	}

	public Set<Variable> getInputVariables() {
		Set<Variable> set = new LinkedHashSet<Variable>();
		for (Object o : getArgs()) {
			if (o instanceof Variable) {
				set.add((Variable) o);
			}
		}
		return set;
	}

	public Set<Variable> getReturnVars() {
		Set<Variable> set = new LinkedHashSet<Variable>();
		for (Object o : getReturns()) {
			if (o instanceof Variable) {
				set.add((Variable) o);
			}
		}
		return set;
	}
	public List<Variable> getReturns() {
		return returns;
	}
	public void setArgs(SArrayList<Param> args) {
		this.args = args;
	}
	public SArrayList<Param> getArgs() {
		return args;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		int len=in.readInt();
		char[] _name = new char[len];
		for (int i=0; i<len; i++) 
			_name[i] = in.readChar();
		name = new String(_name);
		args = new SArrayList<Param>(0);
		args.readExternal(in);
		returns = new SArrayList<Variable>(0);
		returns.readExternal(in);
		//args = (List) in.readObject();
		//returns = (List<Variable>) in.readObject();
		pyfuncIdx = in.readInt();
		try { load(); } 
		catch (InternalException e) { throw new IOException(e); }		
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(name.length());
		out.writeChars(name);
		args.writeExternal(out);
		returns.writeExternal(out);
		//out.writeObject(args);
		//out.writeObject(returns);
		out.writeInt(pyfuncIdx);
	}
	public String name() {
		if (method!=null) return name;
		else if (pyname!=null) return pyname;
		else return name;
	}
}
