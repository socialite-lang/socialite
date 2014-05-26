package socialite.functions;


import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.python.core.CodeFlag;
import org.python.core.Py;
import org.python.core.PyBaseCode;
import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyGenerator;
import org.python.core.PyInteger;
import org.python.core.PyJavaType;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PySequenceList;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.core.ThreadState;

import socialite.eval.Manager;
import socialite.util.SociaLiteException;

class MyArray<E> {
	static final long serialVersionUID = 1L;

	Object[] elems;
	MyArray() {
		this(32);
	}
	
	MyArray(int capacity) {
		elems = new Object[capacity];
	}
	
	
	static class Resize {
		final Object[] newElems;		
		Resize(Object[] oldElems, int newSize) {
			newElems = Arrays.copyOf(oldElems, newSize);
		}
	}
	public void ensureCapacity(int size) {		
		if (elems.length < size) {
			int minCapacity = elems.length*3/2;
			if (minCapacity < size) minCapacity = size;
			elems = new Resize(elems, minCapacity).newElems;
		}
	}
	
	@SuppressWarnings("unchecked")
	public E get(int idx) {
		return (E)elems[idx];
	}
	public E set(int idx, E e) {
		@SuppressWarnings("unchecked")
		E old=(E)elems[idx];
		elems[idx] = e;
		return old;
	}
	public void clear() {
		Arrays.fill(elems, null);
	}
}
class PyStateCache {
	static MyArray<ThreadState> states = new MyArray<ThreadState>();
	static MyArray<MyArray<PyFrame>> pyFrames = new MyArray<MyArray<PyFrame>>();
	static void init(int funcId, PyFunction func) {
		Manager m = Manager.getInst();
		if (m==null) return;
		int workernum = m.getWorkerNum();
		
		// initializing Python thread states
		states.ensureCapacity(workernum);
		for (int i=0; i<workernum; i++) {
			if (states.get(i)==null) {
				ThreadState ts=new ThreadState(null, PyInterp.get().getSystemState());
				states.set(i, ts);
			}
		}
		
		// initializing Python thread states
		pyFrames.ensureCapacity(workernum);
		for (int i=0; i<workernum; i++) {
			MyArray<PyFrame> frameList = pyFrames.get(i);
			if (frameList==null) {
				int size = Math.max(16, funcId+1);
				frameList = new MyArray<PyFrame>(size);
				pyFrames.set(i, frameList);
			} 
			PyFrame frame = frameList.get(funcId);
			if (frame==null) {			
				frame = new PyFrame((PyBaseCode)func.func_code, func.func_globals);
				frameList.set(funcId, frame);
			}
		}
	}
	static ThreadState get(int workerId) {
		return states.get(workerId);
		
	}
	static PyFrame getFrame(int workerId, int funcId) {
		MyArray<PyFrame> frameList = pyFrames.get(workerId);
		if (frameList == null) {
			int size = Math.max(16, funcId+1);
			frameList = new MyArray<PyFrame>(size);
			pyFrames.set(workerId, frameList);
		}
		PyFrame frame = frameList.get(funcId);
		if (frame==null) {
			PyFunction func = PyFunctionCache.get(funcId);			
			frame = new PyFrame((PyBaseCode)func.func_code, func.func_globals);
			frameList.set(funcId, frame);
		}
		return frame;
	}
}

class PyFunctionCache {
	static TObjectIntHashMap<PyFunction> funcToIdx = new TObjectIntHashMap<PyFunction>(); 
	static MyArray<PyFunction> functions=new MyArray<PyFunction>(8);
	static TObjectIntHashMap<String> nameToIdx = new TObjectIntHashMap<String>();
		
	static synchronized void put(int idx, PyFunction pyfunc) {
		functions.ensureCapacity(idx+1);
		functions.set(idx, pyfunc);
		funcToIdx.put(pyfunc, idx);
		nameToIdx.put(pyfunc.__name__, idx);
				
		PyStateCache.init(idx, pyfunc);
	}
	static PyFunction get(int idx) {
		return functions.get(idx);
	}
	static synchronized int get(String name) {
		if (!nameToIdx.contains(name))
			return -1;
		return nameToIdx.get(name);
	}
	static synchronized int get(PyFunction pyf) {
		if (!funcToIdx.contains(pyf))
			return -1;	
		return funcToIdx.get(pyf);		
	}
	static void clear() {
		funcToIdx.clear();
		functions.clear();
		nameToIdx.clear();
	}
}
public class PyInvoke {
	public static int getRegisteredIdx(PyFunction pyfunc) {
		return PyFunctionCache.get(pyfunc);
	}
	public static void register(int idx, PyFunction pyfunc) {
		PyFunctionCache.put(idx, pyfunc);
	}
	public static void update(PyFunction pyfunc) {
		int idx=PyFunctionCache.get(pyfunc.__name__);
		if (idx>=0) PyFunctionCache.put(idx, pyfunc);
	}
	public static void update(List<PyFunction> pyfuncs) {
		for (PyFunction pyfunc:pyfuncs) 
			update(pyfunc);
	}
	
	static boolean isGenerator(PyBaseCode code) {
		return code.co_flags.isFlagSet(CodeFlag.CO_GENERATOR);
	}
	// these functions are adapted from python.core.PyBaseCode.java
	public static Object invoke(int workerId, int funcIdx) {
		PyFunction func = PyFunctionCache.get(funcIdx);
		ThreadState ts = PyStateCache.get(workerId);
		PyFrame frame = PyStateCache.getFrame(workerId, funcIdx);
		PyBaseCode code = (PyBaseCode)func.func_code;
		if (isGenerator(code)) {
			frame = new PyFrame((PyBaseCode)func.func_code, func.func_globals);			
			PyGenerator pygen = new PyGenerator(frame, func.func_closure);
			return new PyGenIterator(pygen);
		}
		PyObject ret = code.call(ts, frame, func.func_closure);
		return toJava(ret);
	}
	public static Object invoke(int workerId, int funcIdx, Object arg0) {
		PyFunction func = PyFunctionCache.get(funcIdx);
		ThreadState ts = PyStateCache.get(workerId);
		PyFrame frame = PyStateCache.getFrame(workerId, funcIdx);
		frame.f_fastlocals[0] = Py.java2py(arg0);
		PyBaseCode code = (PyBaseCode)func.func_code;
		if (isGenerator(code)) {
			frame = new PyFrame((PyBaseCode)func.func_code, func.func_globals);
			frame.f_fastlocals[0] = Py.java2py(arg0);
			PyGenerator pygen = new PyGenerator(frame, func.func_closure);
			return new PyGenIterator(pygen);
		}
		PyObject ret = code.call(ts, frame, func.func_closure);
		return toJava(ret);
	}
	public static Object invoke(int workerId, int funcIdx, Object arg0, Object arg1) {
		PyFunction func = PyFunctionCache.get(funcIdx);
		ThreadState ts = PyStateCache.get(workerId);
		PyFrame frame = PyStateCache.getFrame(workerId, funcIdx);
		frame.f_fastlocals[0] = Py.java2py(arg0);
		frame.f_fastlocals[1] = Py.java2py(arg1);
		PyBaseCode code = (PyBaseCode)func.func_code;
		if (isGenerator(code)) {
			frame = new PyFrame((PyBaseCode)func.func_code, func.func_globals);
			frame.f_fastlocals[0] = Py.java2py(arg0);
			frame.f_fastlocals[1] = Py.java2py(arg1);
			PyGenerator pygen = new PyGenerator(frame, func.func_closure);
			return new PyGenIterator(pygen);
		}
		PyObject ret = code.call(ts, frame, func.func_closure);		
		return toJava(ret);		
	}
	public static Object invoke(int workerId, int funcIdx, Object arg0, Object arg1, Object arg2) {
		PyFunction func = PyFunctionCache.get(funcIdx);
		ThreadState ts = PyStateCache.get(workerId);
		PyFrame frame = PyStateCache.getFrame(workerId, funcIdx);
		frame.f_fastlocals[0] = Py.java2py(arg0);
		frame.f_fastlocals[1] = Py.java2py(arg1);
		frame.f_fastlocals[2] = Py.java2py(arg2);
		PyBaseCode code = (PyBaseCode)func.func_code;
		if (isGenerator(code)) {
			frame = new PyFrame((PyBaseCode)func.func_code, func.func_globals);
			frame.f_fastlocals[0] = Py.java2py(arg0);
			frame.f_fastlocals[1] = Py.java2py(arg1);
			frame.f_fastlocals[2] = Py.java2py(arg2);
			PyGenerator pygen = new PyGenerator(frame, func.func_closure);
			return new PyGenIterator(pygen);
		}
		PyObject ret = code.call(ts, frame, func.func_closure);		
		return toJava(ret);
	}
	public static Object invoke(int workerId, int funcIdx, 
								Object arg0, Object arg1, Object arg2, Object arg3) {
		PyFunction func = PyFunctionCache.get(funcIdx);
		ThreadState ts = PyStateCache.get(workerId);
		PyFrame frame = PyStateCache.getFrame(workerId, funcIdx);
		frame.f_fastlocals[0] = Py.java2py(arg0);
		frame.f_fastlocals[1] = Py.java2py(arg1);
		frame.f_fastlocals[2] = Py.java2py(arg2);
		frame.f_fastlocals[3] = Py.java2py(arg3);
		PyBaseCode code = (PyBaseCode)func.func_code;
		if (isGenerator(code)) {
			frame = new PyFrame((PyBaseCode)func.func_code, func.func_globals);
			frame.f_fastlocals[0] = Py.java2py(arg0);
			frame.f_fastlocals[1] = Py.java2py(arg1);
			frame.f_fastlocals[2] = Py.java2py(arg2);
			frame.f_fastlocals[3] = Py.java2py(arg3);
			PyGenerator pygen = new PyGenerator(frame, func.func_closure);
			return new PyGenIterator(pygen);
		}
		PyObject ret = code.call(ts, frame, func.func_closure);		
		return toJava(ret);
	}
	public static Object invoke(int workerId, int funcIdx, 
			Object arg0, Object arg1, Object arg2, Object arg3, Object ...rest) {
		PyFunction func = PyFunctionCache.get(funcIdx);
		ThreadState ts = PyStateCache.get(workerId);
		PyFrame frame = PyStateCache.getFrame(workerId, funcIdx);
		frame.f_fastlocals[0] = Py.java2py(arg0);
		frame.f_fastlocals[1] = Py.java2py(arg1);
		frame.f_fastlocals[2] = Py.java2py(arg2);
		frame.f_fastlocals[3] = Py.java2py(arg3);
		if (rest!=null && rest.length>0) {
			int idx=4;
			for (Object o:rest) {
				frame.f_fastlocals[idx++] = Py.java2py(o);
			}
		}
		PyBaseCode code = (PyBaseCode)func.func_code;
		if (isGenerator(code)) {
			frame = new PyFrame((PyBaseCode)func.func_code, func.func_globals);
			frame.f_fastlocals[0] = Py.java2py(arg0);
			frame.f_fastlocals[1] = Py.java2py(arg1);
			frame.f_fastlocals[2] = Py.java2py(arg2);
			frame.f_fastlocals[3] = Py.java2py(arg3);
			if (rest!=null && rest.length>0) {
				int idx=4;
				for (Object o:rest) {
					frame.f_fastlocals[idx++] = Py.java2py(o);
				}
			}
			PyGenerator pygen = new PyGenerator(frame, func.func_closure);
			return new PyGenIterator(pygen);
		}
		PyObject ret = code.call(ts, frame, func.func_closure);		
		return toJava(ret);
	}
	
	static class PyGenIterator implements Iterator<Object> {
		PyGenerator gen;
		PyObject val;
		PyGenIterator(PyGenerator _gen) {
			gen = _gen; 
		}
		
		@Override
		public boolean hasNext() {
			try {
				val = gen.next();
				return true;
			} catch (PyException e) {
				if (e.type.equals(Py.StopIteration))
					return false;
				throw e;
			}			
		}

		@Override
		public Object next() {
			return toJava(val);
		}

		@Override
		public void remove() {}		
	}
	static Object toJava(PyObject py) {
		if (py instanceof PyInteger) {
			return py.__tojava__(Integer.class);
		} else if (py instanceof PyLong) {
			return py.__tojava__(Long.class);
		} else if (py instanceof PyFloat) {
			return py.__tojava__(Double.class);
		} else if (py instanceof PyString) {
			return py.__tojava__(String.class);
		} else if (py instanceof PyGenerator) { 
			return new PyGenIterator((PyGenerator)py);
		} else if (py instanceof PySequenceList) {	
			PySequenceList pyseq = (PySequenceList)py;
			int size = pyseq.size();
			Object[] a = new Object[size];			
			for (int i=0; i<size; i++) {
				a[i] = toJava(pyseq.__finditem__(i));
			}
			return a;
		} else if (py instanceof PyJavaType) {			
			PyJavaType t = (PyJavaType)py;
			Class<?> klass = t.getProxyType();
			return py.__tojava__(klass);
		} else {			
			return py.__tojava__(Object.class);
		}
	}

	@SuppressWarnings("rawtypes")
	public static Class py2javaType(PyType c) {
		if (PyInteger.TYPE.equals(c)) {
			return int.class;
		} else if (PyLong.TYPE.equals(c)) {
			return long.class;
		} else if (PyFloat.TYPE.equals(c)) {
			return double.class;
		} else if (PyString.TYPE.equals(c)) {
			return String.class;
		} else if (c instanceof PyJavaType) {
			PyJavaType t = (PyJavaType)c;
			return t.getProxyType();			
		} else {
			return Object.class;
		}
	}
}
