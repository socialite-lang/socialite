package socialite.tables;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import socialite.collection.SDoubleIntHashMap;
import socialite.collection.SFloatIntHashMap;
import socialite.collection.SIntIntHashMap;
import socialite.collection.SLongIntHashMap;
import socialite.collection.SObjectIntHashMap;
import socialite.util.Assert;
import gnu.trove.map.hash.TDoubleIntHashMap;
import gnu.trove.map.hash.TFloatIntHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TLongIntHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class GroupbyMap implements Externalizable {
	private static final long serialVersionUID = 1;

	Object map;
	
	transient int initCapacity=2048;
		
	public GroupbyMap() { }
	public GroupbyMap(int _initCapacity) { initCapacity=_initCapacity; }
	
	SIntIntHashMap intIntMap() {
		if (map==null) {map = (Object)new SIntIntHashMap(initCapacity, 0.8f, Integer.MIN_VALUE, -1);}
		return (SIntIntHashMap)map;
	}
	SLongIntHashMap longIntMap() {
		if (map==null) {map = (Object)new SLongIntHashMap(initCapacity, 0.8f, Long.MIN_VALUE, -1);}
		return (SLongIntHashMap)map;		
	}
	SFloatIntHashMap floatIntMap() {
		if (map==null) {map = (Object)new SFloatIntHashMap(initCapacity, 0.8f, Float.MIN_VALUE, -1);}
		return (SFloatIntHashMap)map;
	}
	SDoubleIntHashMap doubleIntMap() {
		if (map==null) {map = (Object)new SDoubleIntHashMap(initCapacity, 0.8f, Double.MIN_VALUE, -1);}
		return (SDoubleIntHashMap)map;
	}
	@SuppressWarnings("unchecked")
	SObjectIntHashMap<Object> objIntMap() {
		if (map==null) {map = (Object)new SObjectIntHashMap<Object>(initCapacity, 0.8f, -1);}
		return (SObjectIntHashMap<Object>)map;
	}
	
	@SuppressWarnings("unchecked")
	SObjectIntHashMap<Tuple> tupIntMap() {
		if (map==null) {map = (Object)new SObjectIntHashMap<Tuple>(initCapacity, 0.8f, -1);}
		return (SObjectIntHashMap<Tuple>)map;
	}
	
	
	// add1 methods (for each type)
	public void add1(int i, int pos) {
		assert intIntMap().containsKey(i)==false:"intIntMap["+i+"]:"+intIntMap().get(i);
		intIntMap().put(i, pos);
	}
	public void add1(long l, int pos) {
		longIntMap().put(l, pos);
	}
	public void add1(float f, int pos) {
		floatIntMap().put(f, pos);
	}
	public void add1(double d, int pos) {
		doubleIntMap().put(d, pos);
	}
	public void add1(Object o, int pos) {
		assert objIntMap().containsKey(o)==false;
		objIntMap().put(o, pos);
	}	
	public void add1(Tuple t, int pos) {
		assert tupIntMap().containsKey(t)==false;
		tupIntMap().put(t, pos);
	}
	
	// remove1 methods (for each type)
	public void remove1(int i) { intIntMap().remove(i);}
	public void remove1(long l) { longIntMap().remove(l);}
	public void remove1(float f) { floatIntMap().remove(f);}
	public void remove1(double d) { doubleIntMap().remove(d); }
	public void remove1(Object o) { objIntMap().remove(o); }	
	public void remove1(Tuple t) { tupIntMap().remove(t); }
	
	public int get1(int i) { return intIntMap().get(i); }
	public int get1(long l) { return longIntMap().get(l);	}
	public int get1(float f) { return floatIntMap().get(f); }
	public int get1(double d) { return doubleIntMap().get(d); }
	public int get1(Object o) { return objIntMap().get(o); }
	public int get1(Tuple t) { return tupIntMap().get(t); }
	
	public boolean containsKey(int i) { return intIntMap().containsKey(i); }
	public boolean containsKey(long l) { return longIntMap().containsKey(l);	}
	public boolean containsKey(float f) { return floatIntMap().containsKey(f); }
	public boolean containsKey(double d) { return doubleIntMap().containsKey(d); }
	public boolean containsKey(Object o) { return objIntMap().containsKey(o); }
	public boolean containsKey(Tuple t) { return tupIntMap().containsKey(t); }
	
	@SuppressWarnings("rawtypes")
	public void clear() {
		if (map==null) return;
		if (map instanceof TIntIntHashMap) {
			((SIntIntHashMap)map).clear();
		} else if (map instanceof SLongIntHashMap) {
			((SLongIntHashMap)map).clear();
		} else if (map instanceof SFloatIntHashMap) {
			((SFloatIntHashMap)map).clear();
		} else if (map instanceof SDoubleIntHashMap) {
			((SDoubleIntHashMap)map).clear();
		} else if (map instanceof SObjectIntHashMap) {
			((SObjectIntHashMap)map).clear();
		} else {
			Assert.impossible();
		}		
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		byte b=in.readByte();
		if (b==1) {	map = (TIntIntHashMap) in.readObject();	}
		b=in.readByte();
		if (b==1) { map = (TLongIntHashMap) in.readObject(); }
		b=in.readByte();
		if (b==1) {	map = (TFloatIntHashMap) in.readObject(); }
		b=in.readByte();
		if (b==1) { map = (TDoubleIntHashMap) in.readObject(); }
		b=in.readByte();
		if (b==1) {	map = (TObjectIntHashMap) in.readObject(); }		
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		if (map instanceof TIntIntHashMap) {
			out.writeByte(1);
			intIntMap().writeExternal(out);
		} else { out.writeByte(0); } 
		
		if (map instanceof TLongIntHashMap) {
			out.writeByte(1);
			longIntMap().writeExternal(out);
		} else { out.writeByte(0); }
		
		if (map instanceof TFloatIntHashMap) {
			out.writeByte(1);
			floatIntMap().writeExternal(out);
		} else { out.writeByte(0); }
		
		if (map instanceof TDoubleIntHashMap) {
			out.writeByte(1);
			doubleIntMap().writeExternal(out);
		} else { out.writeByte(0); }
		
		if (map instanceof TObjectIntHashMap) {
			out.writeByte(1);
			((TObjectIntHashMap)map).writeExternal(out);
		} else { out.writeByte(0); }
	}
}