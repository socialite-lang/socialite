package socialite.functions;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import socialite.type.Utf8;

public class Intern {
	public static void clear() {
		pool = null;
	}
	static HashMap<Object, Object> pool = null;
	static HashMap<Object, Object> getPool() {
		if (pool==null) pool = new HashMap<Object, Object>(512*1024);
		return pool;
	}
	public static synchronized Utf8 intern(Utf8 c) {
		return (Utf8)intern((Object)c);
	}
	public static synchronized Object intern(Object c) {		
		Object ref = null;
		ref = getPool().get(c);
		if (ref==null) {
			getPool().put(c, c);
			ref = c;
		}
		return ref;
	}
	
	public static String invoke(String str) {
		return invoke(str, 64);
	}
	public static String invoke(String str, int len) {
		if (str.length()<=len)
			return (String)intern(str);
		return str;
	}

	public static Utf8 invoke(Utf8 u) {
		return invoke(u, 64);
	}	
	public static Utf8 invoke(Utf8 u, int len) {
		if (u.byteLength() <= len)
			return (Utf8)intern(u);
		return u;
	}
}
