package socialite.collection;

import gnu.trove.map.hash.TFloatIntHashMap;

public class SFloatIntHashMap extends TFloatIntHashMap {
	float prevKey;
	int prevVal;
	
	public SFloatIntHashMap(int initCapacity, float f, float noEntryKey, int noEntryValue) {
		super(initCapacity, f, noEntryKey, noEntryValue);
		prevKey = noEntryValue;
		prevVal = noEntryValue;
	}

	@Override
	public int get(float key) {
		if (prevKey!=super.no_entry_key && key==prevKey) {
			return prevVal;
		}
		prevKey = key;
		prevVal = super.get(key);
		return prevVal;
	}
	
	@Override
	public int put(float key, int value) {
		prevKey = key;
		prevVal = value;
		return super.put(key, value);
	}
	@Override
	public int remove(float key) {
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
