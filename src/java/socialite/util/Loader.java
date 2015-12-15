package socialite.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.Introspector;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

import org.python.core.Py;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.core.imp;

import socialite.codegen.CodeGenMain;

public class Loader extends URLClassLoader {
    public static final Log L=LogFactory.getLog(Loader.class);

    static List<URL> classPath = Collections.synchronizedList(new ArrayList<URL>());
    
    static final boolean usePythonLoader;
    static {
        String s = System.getProperty("socialite.pythonloader");
        if (s==null || s.equalsIgnoreCase("false")) {
            usePythonLoader = false;
        } else {
            usePythonLoader = true;            
        }
    }

    static int id=0;
    static Loader theInst = new Loader();
    public static ClassLoader get() {
    	if (usePythonLoader)
    	    return imp.getSyspathJavaLoader();
    	return theInst;
    }    
    public static List<URL> getClassPath() {
    	return classPath;
    }
    
    String name;

    public Loader() { 
    	super(classPath.toArray(new URL[0]));
    	name = "socialite.util.Loader";
    	id++;
    }

    public Loader(String _name) { 
    	super(classPath.toArray(new URL[0]));
    	name = "socialite.util.Loader("+_name+",id="+id+")";
    	id++;
    }

    @Override
    public synchronized void addURL(URL url) {
    	super.addURL(url);
    }

    // static methods
    public static boolean exists(String klass) {
    	try {    			
    		Class.forName(klass, true, Loader.get());
    		return true;
    	} catch (ClassNotFoundException e) {
    		return false;    		
    	}
    }
    
    public static Class<?> forName(String klass) {
    	ClassLoader l=(ClassLoader)Loader.get();
    	synchronized(l) {
    		try {    			
    			return Class.forName(klass, true, l);
    		} catch (ClassNotFoundException e) {
    			L.error("Cannot load class:"+klass);
    			L.error(ExceptionUtils.getStackTrace(e));
    			throw new SociaLiteException("Cannot load "+klass + ", "+e);    		
    		}
    	}
    }

    public static void loadFromBytes(LinkedHashMap<String, byte[]> rawClasses) {
        ((Loader)Loader.get())._loadFromBytes(rawClasses);
    }
    void _loadFromBytes(LinkedHashMap<String, byte[]> rawClasses) {
    	ArrayList<Class<?>> classes = new ArrayList<Class<?>>(rawClasses.size());
    	for (Map.Entry<String, byte[]> entry : rawClasses.entrySet()) {
    		String n = entry.getKey();
    		byte[] b = entry.getValue();
    		classes.add(super.defineClass(n, b, 0, b.length));
    	}
    	for (int i = 0; i < classes.size(); i++) {
    		resolveClass(classes.get(i));
    	}
    }
    public static void loadFromBytes(List<String> classNames, List<byte[]> classFiles) {
    	((Loader)Loader.get())._loadFromBytes(classNames, classFiles);
    }
    void _loadFromBytes(List<String> classNames, List<byte[]> classFiles) {    	
    	Class<?>[] classes=new Class[classNames.size()];
    	for (int i=0; i<classNames.size(); i++) {
    		String n = classNames.get(i);
    		byte[] b = classFiles.get(i);
    		classes[i] = super.defineClass(n, b, 0, b.length);
    	}
    	for (int i=0; i<classes.length; i++) {
    		resolveClass(classes[i]);    		
    	}
    }
    
    public String toString() {
    	return name + "/(current id="+id+")";
    }    
    public static void addClassPath(File f) {
	try {
		addClassPath(f.toURI().toURL());
	} catch (MalformedURLException e) {
		L.error("Cannot add to class path:"+e);
		L.error(ExceptionUtils.getStackTrace(e));
		throw new SociaLiteException(e);
	}
    }
    public static void addClassPath(URL url) {     
        addClassPath(classPath.size(), url);
    }
    static void addClassPath(int pos, URL url) {     
    	if (classPath.contains(url)) return;    
    	classPath.add(pos, url);
    	if (usePythonLoader) {
    		addToSysPath(url);	
    	} else {
    		theInst.addURL(url);
    	}
    }
    static void addToSysPath(URL url) {
    	PySystemState sys = Py.getSystemState();
    	PyString pystr = new PyString(url.getFile());
    	if (sys.path.contains(pystr))
    		return;
    	sys.path.add(pystr);
    }
    
    public static void removeClassPath(URL url) {
    	if (classPath.contains(url)) {
    		classPath.remove(url);
    		cleanup();
    		theInst = new Loader();
    	} else {
    		L.debug("\n\nTrying to remove a non-existing class path:"+url);
    	}
    }
    
    public static void cleanup() {
    	Loader l = (Loader)get();
        releaseCommonsLoggingCache(l);
        clearSunSoftCache(ObjectInputStream.class, "subclassAudits");
        clearSunSoftCache(ObjectOutputStream.class, "subclassAudits");
        clearSunSoftCache(ObjectStreamClass.class, "localDescs");
        clearSunSoftCache(ObjectStreamClass.class, "reflectors");
        Introspector.flushCaches();
        CodeGenMain.clearCache();
        theInst = new Loader();
    }
    static void releaseCommonsLoggingCache(Loader l) {
    	 try {
             Class<?> logFactory = l.loadClass("org.apache.commons.logging.LogFactory");
             Method release = logFactory.getMethod("release", new Class[] {ClassLoader.class});
             release.invoke(null, new Object[] {l});
         } catch (Throwable ignored) {
             // there is nothing a user could do about this anyway
         }
    }
    @SuppressWarnings("rawtypes")
	static void clearSunSoftCache(Class<?> clazz, String fieldName) {
		Map cache = null;
        try {
        	Class<?> cacheCls = null;
        	for (Class<?> c:clazz.getDeclaredClasses()) {
        		if (c.getName().endsWith("$Caches")) {
        			cacheCls = c;
        		}
        	}
        	if (cacheCls==null) return;
        	
            Field field = cacheCls.getDeclaredField(fieldName);            
            field.setAccessible(true);
            cache = (Map)field.get(null);
        } catch (Throwable ignored) {
            // there is nothing a user could do about this anyway
        	L.warn("cannot clear Sun soft cache");
        }

        if (cache != null) {
            synchronized (cache) {
                cache.clear();
            }
        }
    }
}
