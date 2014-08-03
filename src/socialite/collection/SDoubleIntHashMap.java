package socialite.collection;

import gnu.trove.map.hash.TDoubleIntHashMap;

public class SDoubleIntHashMap extends TDoubleIntHashMap {
	double prevKey;
	int prevVal;
	
	public SDoubleIntHashMap(int initCapacity, float f, double noEntryKey, int noEntryValue) {
		super(initCapacity, f, noEntryKey, noEntryValue);
		prevKey = noEntryValue;
		prevVal = noEntryValue;
	}

	@Override
	public int get(double key) {
		if (prevKey!=super.no_entry_key && key==prevKey) {
			return prevVal;
		}
		prevKey = key;
		prevVal = super.get(key);
		return prevVal;
	}
	
	@Override
	public int put(double key, int value) {
		prevKey = key;
		prevVal = value;
		return super.put(key, value);
	}
	@Override
	public int remove(double key) {
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
