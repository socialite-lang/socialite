package socialite.parser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import socialite.tables.Tuple;
import socialite.type.Utf8;
import socialite.util.Assert;
import socialite.util.InternalException;
import socialite.util.SociaLiteException;

public class MyType {
	public static boolean isPrimNumberType(Class type) {
		Class[] numberTypes = new Class[] { 
				int.class, long.class, float.class, double.class,
				short.class, byte.class};
		for (Class t: numberTypes) {
			if (type.equals(t)) return true;
		}
		return false;
	}
	
	public static boolean isInstanceof(Class type, Object o) {
		Class instType = javaType(o);
		if (instType.equals(type)) return true;
		
		return false;
	}
	
	public static boolean isPrimitive(Object o) {
		return javaType(o).isPrimitive();
	}
	
	public static List<Class> javaTypes(List<Variable> vars) {
		List<Class> types = new ArrayList<Class>();
		for (Variable v:vars) {
			assert v.hasType();
			types.add(v.type);
		}
		return types;
	}
	public static Class javaType(Class klass) {
		if (klass.equals(Integer.class)) {
			klass=int.class;
		} else if (klass.equals(Long.class)) {
			klass=long.class;
		} else if (klass.equals(Double.class)){
			klass=double.class;
		} else if (klass.equals(Float.class)){
			klass=float.class;
		}
		return klass;
	}
	
	@SuppressWarnings("rawtypes")
	public static Class javaType(Object o) {
		if (o instanceof Function) {
			Function f = (Function)o;
			if (!f.isLoaded()) return NoType.class;
			
			if (f.getReturns().size()>1) return Tuple.class;
			Assert.true_(f.getReturns().size()==1);
			return javaType(f.getReturns().get(0));
		} else if (o instanceof Variable) {
			Variable v=(Variable)o;
			return javaType(v.type);
		} else if  (o instanceof Const) {
			Const c = (Const)o;
			return javaType(c.type);
		} else if (o instanceof Class) {
			return javaType((Class)o);
		} else if (o instanceof Op) {
			assert o instanceof BinOp || o instanceof UnaryOp;
			if (o instanceof BinOp) return javaType(((BinOp)o).opType());
			else return javaType(((UnaryOp)o).opType());
		} else {
			if (o==null) return NoType.class;
			return javaType(o.getClass());
		}
	}
	public static String javaTypeName(Class klass) {
		if (klass.isArray()) {
			Class elemType = klass.getComponentType();			
			if (elemType.isArray()) {
				return javaTypeName(elemType)+"[]";	
			} else {
				return javaType(elemType).getName()+"[]";
			}
		}
		return javaType(klass).getName();
	}
	public static void main(String[] args) {
		//System.out.println(javaTypeName(int[].class));		
		//System.out.println(javaTypeName(int[][].class));
		//System.out.println(javaTypeName(String[][].class));
				
		System.out.println(javaObjectTypeName(String[][].class));
		System.out.println(javaObjectTypeName(int.class));
		System.out.println(javaObjectTypeName(int[].class));
				
		int i= -1/100;
		System.out.println("-1/100 = "+i);
	}
	
	public static String javaTypeName(Variable v) {
		return javaTypeName(v.type);
	}
	public static String javaTypeName(Const c) {
		return javaTypeName(c.type);
	}
	
	public static String javaTypeName(Object o) {
		if (o instanceof Variable) {
			return javaTypeName((Variable)o);
		} else if (o instanceof Const) {
			return javaTypeName((Const)o);
		} else if (o instanceof Class) {
			return javaTypeName((Class)o);
		} else {
			return javaTypeName(o.getClass());	
		}
	}
	
	public static String javaObjectTypeName(Variable v) {
		Class type = MyType.javaType(v);
		return javaObjectTypeName(type);
	}
    public static Class javaObjectType(Class type) {
        if (type.isPrimitive()) {
            if (type.equals(int.class))
                return Integer.class;
            else if (type.equals(long.class))
                return Long.class;
            else if (type.equals(float.class))
                return Float.class;
            else if (type.equals(double.class))
                return Double.class;
            else if (type.equals(short.class))
                return Short.class;
            else if (type.equals(byte.class))
                return Byte.class;
            else if (type.equals(boolean.class))
                return Boolean.class;
            else if (type.equals(char.class))
                return Character.class;
            throw new SociaLiteException("Unexpected primitive type:"+type.getSimpleName());
        }
        return type;
    }
	public static String javaObjectTypeName(Class type) {
		if (type.isPrimitive()) {
			if (type.equals(int.class))
				return "Integer";
			else if (type.equals(long.class))
				return "Long";
			else if (type.equals(float.class))
				return "Float";
			else if (type.equals(double.class))
				return "Double";
			else if (type.equals(short.class))
				return "Short";
			else if (type.equals(byte.class))
				return "Byte";
			else if (type.equals(boolean.class))
				return "Boolean";
			else if (type.equals(char.class))
				return "Character";
			throw new SociaLiteException("Unexpected primitive type:"+type.getSimpleName());
		}
		if (Number.class.isAssignableFrom(type)||type.equals(Boolean.class)||type.equals(Character.class)) {
			return type.getSimpleName();
		}
		return javaTypeName(type);
	}
	
	public static String visitTypeName(Object o) {
		String type=javaTypeName(o);
		if (type.equals("int") || type.equals("long") ||
				type.equals("float") || type.equals("double"))
			return type;
		else return "Object";
	}		
}
