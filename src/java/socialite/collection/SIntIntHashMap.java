package socialite.collection;

import gnu.trove.map.hash.TIntIntHashMap;

public class SIntIntHashMap extends TIntIntHashMap {
	int prevKey;
	int prevVal;
	public SIntIntHashMap(int initCapacity, float f, int minValue, int noEntryValue) {
		super(initCapacity, f, minValue, noEntryValue);
		prevKey = noEntryValue;
		prevVal = noEntryValue;
	}

	@Override
	public int get(int key) {
		if (prevKey!=super.no_entry_key && key==prevKey) {
			return prevVal;
		}
		prevKey = key;
		prevVal = super.get(key);
		return prevVal;
	}
	
	@Override
	public int put(int key, int value) {
		prevKey = key;
		prevVal = value;
		return super.put(key, value);
	}
	@Override
	public int remove(int key) {
		prevKey = super.no_entry_key;
		prevVal = super.no_entry_value;
		return super.remove(key);
	}
	@Override
	public void clear() {
		prevKey=super.no_entry_key;
		super.clear();
	}
}
