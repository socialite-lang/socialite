package socialite.collection;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;

import socialite.util.Assert;
import socialite.util.SociaLiteException;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.TIntList;
import gnu.trove.map.hash.TDoubleIntHashMap;
import gnu.trove.map.hash.TDoubleObjectHashMap;
import gnu.trove.map.hash.TFloatIntHashMap;
import gnu.trove.map.hash.TFloatObjectHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongIntHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public final class SIndex implements Externalizable {
	static int posIndexSize=2;
	public static void setPosIndexSize(int size) {
		posIndexSize = size;		
	}	
	
	static final float LOAD_FACTOR = 0.95f;
	static final int NO_ENTRY = -1;
	static final int DEFAULT_CAPACITY = 1024;
	
	boolean singleIndex; // true if the index maps key to a single position (not list).
	int initCapacity = DEFAULT_CAPACITY;
	Object index;
	TIntArrayList wrapper;

	public SIndex() {}
	public SIndex(int capacity, int columnNum) {
		this(columnNum);
		if (singleIndex) initCapacity = capacity;
		else initCapacity = capacity/16;
	}
	public SIndex(int columnNum) {
		initSingleIndex(columnNum);		
	}
	void initSingleIndex(int columnNum) {
		if (columnNum==1) {
			singleIndex = true;
			wrapper = new TIntArrayList(1);
			wrapper.add(0);
		} else singleIndex = false;
	}
	
	public int size() {
		if (index==null) return 0;
		
		if (index instanceof TIntIntHashMap) return iIndex1().size();
		else if (index instanceof TIntObjectHashMap) return iIndex2().size();
		else if (index instanceof TLongIntHashMap) return lIndex1().size();
		else if (index instanceof TLongObjectHashMap) return lIndex2().size();
		else if (index instanceof TFloatIntHashMap) return fIndex1().size();
		else if (index instanceof TFloatObjectHashMap) return fIndex2().size();
		else if (index instanceof TDoubleIntHashMap) return dIndex1().size();
		else if (index instanceof TDoubleObjectHashMap) return dIndex2().size();
		else if (index instanceof TObjectIntHashMap) return oIndex1().size();
		else if (index instanceof HashMap) return oIndex2().size();
		else {
			throw new SociaLiteException("Unexpected index class:"+index.getClass().getSimpleName());
		}
	}
	public void clear() {
		if (index==null) return;
		
		if (index instanceof TIntIntHashMap) iIndex1().clear();
		else if (index instanceof TIntObjectHashMap) iIndex2().clear();
		else if (index instanceof TLongIntHashMap) lIndex1().clear();
		else if (index instanceof TLongObjectHashMap) lIndex2().clear();
		else if (index instanceof TFloatIntHashMap) fIndex1().clear();
		else if (index instanceof TFloatObjectHashMap) fIndex2().clear();
		else if (index instanceof TDoubleIntHashMap) dIndex1().clear();
		else if (index instanceof TDoubleObjectHashMap) dIndex2().clear();
		else if (index instanceof TObjectIntHashMap) oIndex1().clear();
		else if (index instanceof HashMap) oIndex2().clear();
		else {
			throw new SociaLiteException("Unexpected index class:"+index.getClass().getSimpleName());
		}
	}
	// int key
	TIntIntHashMap iIndex1() {
		if (index==null) index = new TIntIntHashMap(initCapacity, LOAD_FACTOR, NO_ENTRY, NO_ENTRY);
		return (TIntIntHashMap)index;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	TIntObjectHashMap<TIntArrayList> iIndex2() {
		if (index==null) index = new TIntObjectHashMap(initCapacity, LOAD_FACTOR, NO_ENTRY);
		return (TIntObjectHashMap)index;
	}
	public void add(int key, int pos) {
		if (singleIndex) iIndex1().put(key, pos);
		else {
			TIntArrayList l = iIndex2().get(key);
			if (l==null) {
				l = new TIntArrayList(posIndexSize);
				iIndex2().put(key, l);
			} 
			l.add(pos);
		}
	}
	public void remove(int key, int pos) {
		if (singleIndex) iIndex1().put(key, NO_ENTRY);
		else {
			TIntArrayList l = iIndex2().get(key);
			if (l!=null) l.remove(pos);
		}
	}
	
	
	public TIntList get(int key) {
		if (singleIndex) {
			int pos = iIndex1().get(key);
			if (pos<0) return null;
			TIntArrayList l = wrapper; 
			l.setQuick(0, pos);
			return l;
		} else return iIndex2().get(key);
	}	

	// long key
	TLongIntHashMap lIndex1() {
		if (index==null) {
			index = new TLongIntHashMap(initCapacity, LOAD_FACTOR, NO_ENTRY, NO_ENTRY);
			wrapper = new TIntArrayList(1);
			wrapper.add(0);
		}
		return (TLongIntHashMap)index;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	TLongObjectHashMap<TIntArrayList> lIndex2() {
		if (index==null) index = new TLongObjectHashMap(initCapacity, LOAD_FACTOR, NO_ENTRY);
		return (TLongObjectHashMap)index;
	}
	public void add(long key, int pos) {
		if (singleIndex) lIndex1().put(key, pos);
		else {
			TIntArrayList l = lIndex2().get(key);
			if (l==null) {
				l = new TIntArrayList(posIndexSize);
				lIndex2().put(key, l);
			} 
			l.add(pos);
		}
	}
	public void remove(long key, int pos) {
		if (singleIndex) lIndex1().put(key, NO_ENTRY);
		else {
			TIntArrayList l = lIndex2().get(key);
			if (l!=null) l.remove(pos);
		}
	}
	public TIntList get(long key) {
		if (singleIndex) {
			int pos = lIndex1().get(key);
			if (pos<0) return null;			
			TIntArrayList l = wrapper; 
			l.setQuick(0, pos);
			return l;
		} else return lIndex2().get(key);
	}
	
	// float key
	TFloatIntHashMap fIndex1() {
		if (index==null) index = new TFloatIntHashMap(initCapacity, LOAD_FACTOR, NO_ENTRY, NO_ENTRY);
		return (TFloatIntHashMap)index;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	TFloatObjectHashMap<TIntArrayList> fIndex2() {
		if (index==null) index = new TFloatObjectHashMap(initCapacity, LOAD_FACTOR, NO_ENTRY);
		return (TFloatObjectHashMap)index;
	}
	public void add(float key, int pos) {
		if (singleIndex) fIndex1().put(key, pos);
		else {
			TIntArrayList l = fIndex2().get(key);
			if (l==null) {
				l = new TIntArrayList(posIndexSize);
				fIndex2().put(key, l);
			} 
			l.add(pos);
		}
	}
	public void remove(float key, int pos) {
		if (singleIndex) fIndex1().put(key, NO_ENTRY);
		else {
			TIntArrayList l = fIndex2().get(key);
			if (l!=null) l.remove(pos);
		}
	}
	public TIntList get(float key) {
		if (singleIndex) {
			int pos = fIndex1().get(key);
			if (pos<0) return null;
			TIntArrayList l = wrapper; 
			l.setQuick(0, pos);			
			return l;
		} else return fIndex2().get(key);
	}
	
	// double key
	TDoubleIntHashMap dIndex1() {
		if (index==null) index = new TDoubleIntHashMap(initCapacity, LOAD_FACTOR, NO_ENTRY, NO_ENTRY);
		return (TDoubleIntHashMap)index;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	TDoubleObjectHashMap<TIntArrayList> dIndex2() {
		if (index==null) index = new TDoubleObjectHashMap(initCapacity, LOAD_FACTOR, NO_ENTRY);
		return (TDoubleObjectHashMap)index;
	}
	public void add(double key, int pos) {
		if (singleIndex) dIndex1().put(key, pos);
		else {
			TIntArrayList l = dIndex2().get(key);
			if (l==null) {
				l = new TIntArrayList(posIndexSize);
				dIndex2().put(key, l);
			} 
			l.add(pos);
		}
	}
	public void remove(double key, int pos) {
		if (singleIndex) dIndex1().put(key, NO_ENTRY);
		else {
			TIntArrayList l = dIndex2().get(key);
			if (l!=null) l.remove(pos);
		}
	}
	public TIntList get(double key) {
		if (singleIndex) {
			int pos = dIndex1().get(key);
			if (pos<0) return null;
			TIntArrayList l = wrapper; 
			l.setQuick(0, pos);
			return l;
		} else return dIndex2().get(key);
	}
	
	// Object key
	@SuppressWarnings("rawtypes")
	public TObjectIntHashMap oIndex1() {
		if (index==null) index = new TObjectIntHashMap(initCapacity, LOAD_FACTOR, NO_ENTRY);
		return (TObjectIntHashMap)index;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	HashMap<Object, TIntArrayList> oIndex2() {
		if (index==null) index = new HashMap(initCapacity, LOAD_FACTOR);
		return (HashMap)index;
	}
	@SuppressWarnings("unchecked")
	public void add(Object key, int pos) {
		if (singleIndex) oIndex1().put(key, pos);
		else {
			TIntArrayList l = oIndex2().get(key);
			if (l==null) {
				l = new TIntArrayList(posIndexSize);
				oIndex2().put(key, l);
			} 
			l.add(pos);
		}
	}
	@SuppressWarnings("unchecked")
	public void remove(Object key, int pos) {
		if (singleIndex) oIndex1().put(key, NO_ENTRY);
		else {
			TIntArrayList l = oIndex2().get(key);
			if (l!=null) l.remove(pos);
		}
	}
	public TIntList get(Object key) {
		if (singleIndex) {
			int pos = oIndex1().get(key);
			if (pos<0) return null;
			TIntArrayList l = new TIntArrayList(1);
			l.add(pos);
			return l;
		} else return oIndex2().get(key);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		initCapacity = in.readInt();
		if (initCapacity==0) {
			initCapacity = DEFAULT_CAPACITY; 
			return;
		} 
		Class<?> klass = (Class<?>)in.readObject();
		if (klass.equals(TIntIntHashMap.class)) {
			iIndex1().readExternal(in);;
		} else if (klass.equals(TIntObjectHashMap.class)) {
			iIndex2().readExternal(in);
		} else if (klass.equals(TLongIntHashMap.class)) {
			lIndex1().readExternal(in);		
		} else if (klass.equals(TLongObjectHashMap.class)) {
			lIndex2().readExternal(in);
		} else if (klass.equals(TFloatIntHashMap.class)) {
			fIndex1().readExternal(in);
		} else if (klass.equals(TFloatObjectHashMap.class)) {
			fIndex2().readExternal(in);
		} else if (klass.equals(TDoubleIntHashMap.class)) {
			dIndex1().readExternal(in);
		} else if (klass.equals(TDoubleObjectHashMap.class)) {
			dIndex2().readExternal(in);
		} else if (klass.equals(TObjectIntHashMap.class)) {
			oIndex1().readExternal(in);
		} else if (klass.equals(HashMap.class)) {
			Assert.not_supported();
			//oIndex2().readExternal(in);
		} else {
			throw new SociaLiteException("Unexpected index class:"+index);
		}
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		if (index==null) {
			out.writeInt(0);
			return;
		} else  if (index instanceof TIntIntHashMap) {
			out.writeInt(iIndex1().size());
			out.writeObject(TIntIntHashMap.class);
			iIndex1().writeExternal(out);
		} else if (index instanceof TIntObjectHashMap) {
			out.writeInt(iIndex2().size());
			out.writeObject(TIntObjectHashMap.class);
			iIndex2().writeExternal(out);
		} else if (index instanceof TLongIntHashMap) {
			out.writeInt(lIndex1().size());
			out.writeObject(TLongIntHashMap.class);
			lIndex1().writeExternal(out);
		} else if (index instanceof TLongObjectHashMap) {
			out.writeInt(lIndex2().size());
			out.writeObject(TLongObjectHashMap.class);
			lIndex2().writeExternal(out);
		} else if (index instanceof TFloatIntHashMap) {
			out.writeInt(fIndex1().size());
			out.writeObject(TFloatIntHashMap.class);
			fIndex1().writeExternal(out);
		} else if (index instanceof TFloatObjectHashMap) {
			out.writeInt(fIndex2().size());
			out.writeObject(TFloatObjectHashMap.class);
			fIndex2().writeExternal(out);
		} else if (index instanceof TDoubleIntHashMap) {
			out.writeInt(dIndex1().size());
			out.writeObject(TDoubleIntHashMap.class);
			dIndex1().writeExternal(out);			
		} else if (index instanceof TDoubleObjectHashMap) {
			out.writeInt(dIndex2().size());
			out.writeObject(TDoubleObjectHashMap.class);
			dIndex2().writeExternal(out);
		} else if (index instanceof TObjectIntHashMap) {
			out.writeInt(oIndex1().size());
			out.writeObject(TObjectIntHashMap.class);
			oIndex1().writeExternal(out);
		} else if (index instanceof HashMap) {
			Assert.not_supported("SIndex::writeExternal(): support HashMap");
			//oIndex2().writeExternal(out);
		} else {
			throw new SociaLiteException("Unexpected index class:"+index);
		}		
	}
	/*
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeBoolean(singleIndex);
		out.writeObject(index);
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		singleIndex = in.readBoolean();
		index = in.readObject();
	}*/
}