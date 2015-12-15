package socialite.tables;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import socialite.codegen.Compiler;
import socialite.codegen.TableCodeGen;
import socialite.functions.FunctionLoader;
import socialite.util.Assert;
import socialite.util.Loader;

public class TableUtil {
	public static String getTablePackage() { return "socialite.tables"; }

	public static String getTablePath(String tableName) {
		return getTablePackage()+"."+tableName;
	}
	
	public static boolean exists(String name) {
		return Loader.exists(TableUtil.getTablePath(name));
	}
	
	public static Class load(String name) {
		return Loader.forName(TableUtil.getTablePath(name));
	}
	
	public static TableInst create(String className) {
		Class c=TableUtil.load(className);
		try {
			Method m1=FunctionLoader.loadMethod(c, "create", null);
			return (TableInst)m1.invoke(null);
		} catch (Exception e) {
			Assert.die("Exception while instantiating (class:"+className+")");
			return null;
		}
	}	
}
