package socialite.type;

import socialite.collection.SArrayList;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TLongArrayList;

public class Median {
	public static boolean invoke(Median a, int b) { return a.add(b); }
	public static boolean invoke(Median a, long b) { return a.add(b); }
	public static boolean invoke(Median a, float b) { return a.add(b); }
	public static boolean invoke(Median a, double b) { return a.add(b); }

	public int getI() { return ilist.get(ilist.size()/2); }
	public long getL() { return llist.get(llist.size()/2); }
	public float getF() { return flist.get(flist.size()/2); }
	public double getD() { return dlist.get(dlist.size()/2); }
	public Object getO() { return olist.get(olist.size()/2); }	
	
	public Number value;
	TIntArrayList ilist;
	TLongArrayList llist;
	TFloatArrayList flist;
	TDoubleArrayList dlist;
	SArrayList<Object> olist;	
	int defaultCapacity = 1024;

	public Median() { }
	public Median(int _capacity) {
		defaultCapacity = _capacity;
	}
	
	public String toString() {
		return "Median(median=)";
	}
	
	public boolean add(int i) {
		if (ilist == null) {
			ilist = new TIntArrayList(defaultCapacity);
		}
		int pos = ilist.binarySearch(i);
		if (pos>=0) return false;
		
		pos = -pos-1;
		ilist.insert(pos, i);
		return true;
	}
	public boolean add(long l) {
		if (llist == null) {
			llist = new TLongArrayList(defaultCapacity);
		}
		int pos = llist.binarySearch(l);
		if (pos>=0) return false;
		
		pos = -pos-1;
		llist.insert(pos, l);
		return false;
	}
	public boolean add(float f) {
		if (flist == null) {
			flist = new TFloatArrayList(defaultCapacity);
		}
		int pos = flist.binarySearch(f);
		if (pos>=0) return false;
		
		pos = -pos-1;
		flist.insert(pos, f);
		return false;
	}
	public boolean add(double d) {
		if (dlist == null) {
			dlist = new TDoubleArrayList(defaultCapacity);
		}
		int pos = dlist.binarySearch(d);
		if (pos>=0) return false;
		
		pos = -pos-1;
		dlist.insert(pos, d);
		return false;
	}
	public boolean add(Object o) {
		if (olist == null) {
			olist = new SArrayList<Object>(defaultCapacity);
		}
		int pos = olist.binarySearch(o);
		if (pos>=0) return false;
		
		pos = -pos-1;
		olist.insert(pos, o);
		return false;
	}	
}
