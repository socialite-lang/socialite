package socialite.functions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.antlr.gunit.Interp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.BytesWritable;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import socialite.util.InternalException;
import socialite.util.Loader;
import socialite.util.SociaLiteException;
import socialite.util.SocialiteInputStream;
import socialite.util.SocialiteOutputStream;

public class PyInterp extends PythonInterpreter {
	public static final Log L=LogFactory.getLog(PyInterp.class);
			
	static PythonInterpreter interp = null;
	
	public static PythonInterpreter get() {
		assert interp!=null;
		return interp;
	}
	public static void set(PythonInterpreter _interp) {
		interp = _interp;
	}	
	
	public static PyFunction load(String name) throws InternalException {
		try {
			PyFunction f = (PyFunction) interp.get(name);			
			return f;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void addFunctions(List<PyFunction> funcs) {
		for (PyFunction pyf:funcs) {
			interp.set(pyf.__name__, pyf);
		}
	}
	static boolean isInternal(PyObject po) {
		// see SociaLite.py internal decorator function
		PyFunction pyf=(PyFunction)po;
		if (pyf.__dict__ instanceof PyStringMap) {
			PyStringMap dict=(PyStringMap)pyf.__dict__;
			if (dict.has_key("internal"))
				return true;
		}
		return false;
	}
	
	public static List<PyFunction> getFunctions() {				
		PyObject _locals=interp.getLocals();
		List<PyFunction> funcs=new ArrayList<PyFunction>(_locals.__len__());
		PyIterator it=null;
		if (_locals instanceof PyDictionary) {
			it=(PyIterator)((PyDictionary)_locals).itervalues();
		} else {
			it=(PyIterator)((PyStringMap)_locals).itervalues();
		}
		while (true) {
			PyObject p=it.__iternext__();
			if (p==null) break;
			if (p instanceof PyFunction && !isInternal(p)) {
				funcs.add((PyFunction)p);
			}
		}
		return funcs;
	}
	
	static PyObject getReturns(PyFunction func) {
		PyStringMap dict=(PyStringMap)func.__dict__;
		return dict.__finditem__("returns");
	}
	public static BytesWritable toBytesWritable(List<PyFunction> pyfuncs) {
		int numFuncs = pyfuncs.size();
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream(numFuncs*12*1024);
			ObjectOutputStream oos=new SocialiteOutputStream(byteOut);
			oos.writeInt(numFuncs);
			for (PyFunction pyf:pyfuncs) {
				String codeClassName; 
				try {
					Field f = pyf.func_code.getClass().getDeclaredField("funcs");
					f.setAccessible(true);
					codeClassName = f.get(pyf.func_code).getClass().getName();
				} catch (Exception e) {
					L.warn("Unexpected PyFunction.func_code. Exception:"+e);
					continue;
				}
				oos.writeInt(codeClassName.length());
				oos.writeChars(codeClassName);
				oos.writeInt(pyf.__name__.length());
				oos.writeChars(pyf.__name__);		
				oos.writeObject(getReturns(pyf));				
			}
			oos.close();
			return new BytesWritable(byteOut.toByteArray());
		} catch (Exception e) {
			throw new SociaLiteException(e);
		}
		
	}
	public static List<PyFunction> fromBytesWritable(BytesWritable bw) {
		try {
			ByteArrayInputStream byteIn = new ByteArrayInputStream(bw.getBytes());
			ObjectInputStream ois = new SocialiteInputStream(byteIn);
			int numFuncs = ois.readInt();
			List<PyFunction> pyfuncs = new ArrayList<PyFunction>(numFuncs);
			for (int i=0; i<numFuncs; i++) {
				char[] chars=new char[ois.readInt()];
				for (int j=0; j<chars.length; j++) chars[j] = ois.readChar();
				Class<?> codeClass = Loader.forName(new String(chars));

				chars=new char[ois.readInt()];
				for (int j=0; j<chars.length; j++) chars[j] = ois.readChar();
				String namePrefix = new String(chars) +"$";
				
				Field[] fields = codeClass.getDeclaredFields();
				PyFunction pyfunc=null;
				for (Field f:fields) {
					if (!f.getType().equals(PyCode.class)) continue;
					if (f.getName().startsWith(namePrefix)) {
						f.setAccessible(true);
						pyfunc=new PyFunction(interp.getLocals(), null, (PyCode)f.get(null));
						break;
					}
				}
				if (pyfunc==null) {
					L.warn("Cannot find PyCode object for "+namePrefix);
					continue;
				}
				PyObject rets=(PyObject)ois.readObject();
				if (rets!=null) {
					PyStringMap dict = new PyStringMap();
					dict.__setitem__("returns", rets);
					pyfunc.setDict(dict);
				}				
				pyfuncs.add(pyfunc);				
			}			
			ois.close();
			return pyfuncs;
		} catch (Exception e) {
			throw new SociaLiteException(e);
		}
	}
}

