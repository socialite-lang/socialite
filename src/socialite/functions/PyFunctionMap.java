package socialite.functions;

import java.util.ArrayList;

import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import org.python.core.PyCode;
import org.python.core.PyFunction;

public class PyFunctionMap {
	/*
	static PyFunctionMap theInst = null;
	public static PyFunctionMap get() {
		if (theInst==null) {
			theInst = new PyFunctionMap();
		}
		return theInst;
	}
	
	TObjectIntHashMap<String> nameToIdx = new TObjectIntHashMap<String>();
	ArrayList<PyFunction> funcList = new ArrayList<PyFunction>();
	public int register(PyFunction pyf) {
		String name = pyf.__name__;
		if (nameToIdx.containsKey(name)) {
			int idx = nameToIdx.get(name);
			funcList.set(idx, pyf); // override registered function
			return idx;
		}
		int idx = funcList.size();
		funcList.add(pyf);
		nameToIdx.put(name, idx);
		return idx;
	}
	
	public PyFunction getFunc(int key) {
		return funcList.get(key);
	}
	public PyFunction getFunc(String name) {
		if (nameToIdx.containsKey(name)) {
			int idx = nameToIdx.get(name);
			return funcList.get(idx);
		}
		return null;
	}
	*/
}
