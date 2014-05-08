package socialite.tables;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.antlr.grammar.v3.ANTLRv3Parser.throwsSpec_return;

import socialite.util.Assert;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TDoubleIntHashMap;
import gnu.trove.map.hash.TDoubleObjectHashMap;
import gnu.trove.map.hash.TFloatIntHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongIntHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class GroupbyMap implements Externalizable {
	private static final long serialVersionUID = 1;

	/*Map<Tuple, TIntArrayList> map;
	Map<Object, TIntArrayList> objMap;
	TIntObjectHashMap<TIntArrayList> intMap;
	TDoubleObjectHashMap<TIntArrayList> doubleMap;*/
	TIntIntHashMap intIntMap;
	TLongIntHashMap longIntMap;
	TFloatIntHashMap floatIntMap;
	TDoubleIntHashMap doubleIntMap;
	TObjectIntHashMap objIntMap;
	TObjectIntHashMap<Tuple> tupIntMap;
	transient int initCapacity=2048;
		
	public GroupbyMap() { }
	public GroupbyMap(int _initCapacity) { initCapacity=_initCapacity; }
	
	// add1 methods (for each type)
	public void add1(int i, int pos) {
		if (intIntMap==null) intIntMap = new TIntIntHashMap(initCapacity, 0.7f, Integer.MIN_VALUE, -1);
		assert intIntMap.containsKey(i)==false:"intIntMap["+i+"]:"+intIntMap.get(i);
		intIntMap.put(i, pos);
	}
	public void add1(long l, int pos) {
		if (longIntMap==null) longIntMap = new TLongIntHashMap(initCapacity, 0.7f, Integer.MIN_VALUE, -1);
		longIntMap.put(l, pos);
	}
	public void add1(float f, int pos) {
		if (floatIntMap==null) floatIntMap = new TFloatIntHashMap(initCapacity, 0.7f, Integer.MIN_VALUE, -1);
		floatIntMap.put(f, pos);
	}
	public void add1(double d, int pos) {
		if (doubleIntMap==null) doubleIntMap = new TDoubleIntHashMap(initCapacity, 0.7f, Integer.MIN_VALUE, -1);
		doubleIntMap.put(d, pos);
	}
	public void add1(Object o, int pos) {
		if (objIntMap==null) objIntMap = new TObjectIntHashMap(initCapacity, 0.7f, -1);
		assert objIntMap.containsKey(o)==false;
		objIntMap.put(o, pos);
	}	
	public void add1(Tuple t, int pos) {
		if (tupIntMap==null) tupIntMap = new TObjectIntHashMap<Tuple>(initCapacity, 0.7f, -1);
		assert tupIntMap.containsKey(t)==false;
		tupIntMap.put(t, pos);
	}
	
	// remove1 methods (for each type)
	public void remove1(int i) {
		if (intIntMap==null) return;
		intIntMap.remove(i);
	}
	public void remove1(long l) {
		if (longIntMap==null) return;
		longIntMap.remove(l);
	}
	public void remove1(float f) {
		if (floatIntMap==null) return;
		floatIntMap.remove(f);
	}
	public void remove1(double d) {
		if (doubleIntMap==null) return;
		doubleIntMap.remove(d);
	}
	public void remove1(Object o) {
		if (objIntMap==null) return;
		objIntMap.remove(o);
	}	
	public void add1(Tuple t) {
		if (tupIntMap==null) return;
		tupIntMap.remove(t);
	}
	/*
	// add methods
	public void add(int i, int pos) {
		if (intMap==null) {
			intMap = new TIntObjectHashMap<TIntArrayList>(initCapacity);
		}
		TIntArrayList posList = intMap.get(i);
		if (posList==null) {
			posList = new TIntArrayList();
			intMap.put(i, posList);
		}
		posList.add(pos);
	}
	public void add(double d, int pos) {
		if (doubleMap==null) {
			doubleMap = new TDoubleObjectHashMap<TIntArrayList>(initCapacity);
		}
		TIntArrayList posList = doubleMap.get(d);
		if (posList==null) {
			posList = new TIntArrayList();
			doubleMap.put(d, posList);
		}
		posList.add(pos);
	}
	
	public void add(Tuple _t, int pos) {
		if (map==null) {
			map = new HashMap<Tuple, TIntArrayList>(initCapacity);
		}
		TIntArrayList posList = map.get(_t);
		if (posList==null) {
			posList = new TIntArrayList();
			Tuple t=_t.clone();
			map.put(t, posList);
		}
		posList.add(pos);		
	}	
	public void add(Object o, int pos) {
		if (objMap==null) {
			objMap = new HashMap<Object, TIntArrayList>(initCapacity);
		}
		TIntArrayList posList = objMap.get(o);
		if (posList==null) {
			posList = new TIntArrayList();
			objMap.put(o, posList);
		}
		posList.add(pos);
	}
	*/
	public int get1(int i) {
		if (intIntMap==null) return -1;
		return intIntMap.get(i);
	}
	public int get1(long l) {
		if (longIntMap==null) return -1;
		return longIntMap.get(l);
	}
	public int get1(float f) {
		if (floatIntMap==null) return -1;
		return floatIntMap.get(f);
	}
	public int get1(double d) {
		if (doubleIntMap==null) return -1;
		return doubleIntMap.get(d);
	}
	public int get1(Object o) {
		if (objIntMap==null) return -1;
		return objIntMap.get(o);
	}
	public int get1(Tuple t) {
		if (tupIntMap==null) return -1;
		return tupIntMap.get(t);
	}
	/*
	public TIntArrayList get(Tuple t) {
		if (map==null) return null;
		return map.get(t);
	}
	public TIntArrayList get(int i) {
		if (intMap==null) return null;
		return intMap.get(i); 
	}	
	public TIntArrayList get(double d) {
		if (doubleMap==null) return null;
		return doubleMap.get(d);
	}
	public TIntArrayList get(Object o) {
		if (objMap==null) return null;
		return objMap.get(o);
	}*/
	
	public void clear() {
		/*if (map!=null) map.clear();
		if (objMap!=null) objMap.clear();
		if (intMap!=null) intMap.clear();		
		if (doubleMap!=null) doubleMap.clear();*/
		
		if (intIntMap!=null) intIntMap.clear();
		if (longIntMap!=null) longIntMap.clear();
		if (floatIntMap!=null) floatIntMap.clear();
		if (doubleIntMap!=null) doubleIntMap.clear();
		if (objIntMap!=null) objIntMap.clear();
		if (tupIntMap!=null) tupIntMap.clear();		
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		byte b=in.readByte();
		if (b==1) {
			intIntMap = (TIntIntHashMap) in.readObject();
		}
		b=in.readByte();
		if (b==1) {
			longIntMap = (TLongIntHashMap) in.readObject();
		}
		b=in.readByte();
		if (b==1) {
			floatIntMap = (TFloatIntHashMap) in.readObject();
		}
		b=in.readByte();
		if (b==1) {
			doubleIntMap = (TDoubleIntHashMap) in.readObject();
		}
		b=in.readByte();
		if (b==1) {
			objIntMap = (TObjectIntHashMap) in.readObject();
		}
		b=in.readByte();
		if (b==1) {
			tupIntMap = (TObjectIntHashMap) in.readObject();
		}
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		if (intIntMap==null) {
			out.writeByte(0);
		} else {
			out.writeByte(1);
			intIntMap.writeExternal(out);
		}
		
		if (longIntMap==null) {
			out.writeByte(0);
		} else {
			out.writeByte(1);
			longIntMap.writeExternal(out);;
		}
		
		if (floatIntMap==null) {
			out.writeByte(0);
		} else {
			out.writeByte(1);
			floatIntMap.writeExternal(out);
		}
		
		if (doubleIntMap==null) {
			out.writeByte(0);
		} else {
			out.writeByte(1);
			doubleIntMap.writeExternal(out);
		}
		
		if (objIntMap==null) {
			out.writeByte(0);
		} else {
			out.writeByte(1);
			objIntMap.writeExternal(out);
		}
		
		if (tupIntMap==null) {
			out.writeByte(0);
		} else {
			out.writeByte(1);
			tupIntMap.writeExternal(out);
		}
	}
}