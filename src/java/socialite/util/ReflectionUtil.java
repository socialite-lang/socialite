package socialite.util;

import java.lang.reflect.*;
import java.util.*;

import socialite.type.ArgMaxN;
import socialite.type.Utf8;

// Reference: http://www.xinotes.org/notes/note/1329/, 
//            https://code.google.com/a/eclipselabs.org/p/semanticui/source/browse/stable-version/com.onpositive.semantic.model.java/src/com/onpositive/semantic/model/api/method/java/ReflectionUtil.java?r=1564
public class ReflectionUtil {
	private static Map<Class<?>, Class<?>> primitiveMap = new HashMap<Class<?>, Class<?>>();
	static {
		primitiveMap.put(boolean.class, Boolean.class);
		primitiveMap.put(byte.class, Byte.class);
		primitiveMap.put(char.class, Character.class);
		primitiveMap.put(short.class, Short.class);
		primitiveMap.put(int.class, Integer.class);
		primitiveMap.put(long.class, Long.class);
		primitiveMap.put(float.class, Float.class);
		primitiveMap.put(double.class, Double.class);
	}

	public static Method getCompatiblePublicMethod(Class<?> c, String methodName, Class<?>... paramTypes) {
		Method[] methods = c.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];
			if (!m.getName().equals(methodName)) {
				continue;
			}

			Class<?>[] actualTypes = m.getParameterTypes();
			if (actualTypes.length != paramTypes.length) {
				continue;
			}

			boolean found = true;
			for (int j = 0; j < actualTypes.length; j++) {
				if (!actualTypes[j].isAssignableFrom(paramTypes[j])) {
					if (actualTypes[j].isPrimitive()) {
						found = primitiveMap.get(actualTypes[j]).equals(
								paramTypes[j]);
					} else if (paramTypes[j].isPrimitive()) {
						found = primitiveMap.get(paramTypes[j]).equals(
								actualTypes[j]);
					}
				}

				if (!found) {
					break;
				}
			}

			if (found) {
				return m;
			}
		}
		if (isOptArgs(paramTypes)) {						
			Class<?> optArgType = Array.newInstance(paramTypes[0],0).getClass();
			return getCompatiblePublicMethod(c, methodName, new Class[]{optArgType});
		}

		return null;
	}
	static boolean isOptArgs(Class<?>... paramTypes) {
		if (paramTypes.length==0) return false;
		if (paramTypes.length==1 && paramTypes[0].isArray()) return false;
		
		Class type = paramTypes[0];
		for (Class t:paramTypes) {
			if (!t.equals(type))
				return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		Method m=getCompatiblePublicMethod(ArgMaxN.class, "add",new Class[] {int.class, Utf8.class, int.class});
		System.out.println("method:"+m);
	}
}
