package socialite.collection;

import gnu.trove.map.hash.TObjectIntHashMap;

public class SObjectIntHashMap<T> extends TObjectIntHashMap<T> {
	T prevKey=null;
	int prevVal;
	
	public SObjectIntHashMap(int initCapacity, float f, int noEntryValue) {
		super(initCapacity, f, noEntryValue);
		prevVal = noEntryValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int get(Object key) {
		if (prevKey != null && key.equals(prevKey)) {
			return prevVal;
		}
		prevKey = (T)key;
		prevVal = super.get(key);
		return prevVal;
	}
	
	@Override
	public int put(T key, int value) {
		prevKey = key;
		prevVal = value;
		return super.put(key, value);
	}
	@Override
	public int remove(Object key) {
		prevKey = null;
		prevVal = super.no_entry_value;
		return super.remove(key);
	}
	@Override
	public void clear() {
		prevKey=null;
		super.clear();
	}
}
