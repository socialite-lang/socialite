package socialite.type;

import socialite.collection.SArrayList;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TLongArrayList;

@SuppressWarnings("rawtypes")
public class Count implements Comparable {
	public static boolean invoke(Count a, int b) { return a.add(b); }
	public static boolean invoke(Count a, long b) { return a.add(b); }
	public static boolean invoke(Count a, float b) { return a.add(b); }
	public static boolean invoke(Count a, double b) { return a.add(b); }
	public static boolean invoke(Count a, Object b) { return a.add(b); }

	public int value=0;
	
	TIntArrayList ilist;
	TLongArrayList llist;
	TFloatArrayList flist;
	TDoubleArrayList dlist;
	SArrayList<Object> olist;
	
	int defaultCapacity = 1024;

	public Count() { }
	public Count(int _capacity) {
		defaultCapacity = _capacity;
	}
	
	public String toString() {
		return "Count(value="+count()+")";
	}
	@Override
	public int hashCode() {
		return value;
	}
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Count)) return false;
		Count c = (Count)o;
		if (value!=c.value) return false;
		if (ilist!=null && !ilist.equals(c.ilist)) return false;
		if (llist!=null && !llist.equals(c.llist)) return false;
		if (flist!=null && !flist.equals(c.flist)) return false;
		if (dlist!=null && !dlist.equals(c.dlist)) return false;
		
		return false;
	}
	public boolean add(int i) {
		if (ilist == null) {
			ilist = new TIntArrayList(defaultCapacity);
		}
		int pos = ilist.binarySearch(i);
		if (pos>=0) { 
			return false;
		}
		
		pos = -pos-1;
		ilist.insert(pos, i);
		value++;
		assert value==ilist.size();
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
		value++;
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
		value++;
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
		value++;
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
		value++;
		return false;
	}	
	
	public boolean add(Count other) {
		boolean added=false;
		if (ilist!=null) {			
			added = ilist.addAll(other.ilist);
			value = ilist.size();
		} else if (llist!=null) {			
			added = llist.addAll(other.llist);
			value = llist.size();
		} else if (flist!=null) {			
			added = flist.addAll(other.flist);
			value = flist.size();
		} else if (dlist!=null) {			
			added = dlist.addAll(other.dlist);
			value = dlist.size();
		} else {
			added = olist.addAll(other.olist);
			value = olist.size();
		}
		return added;
	}
	public int count() {
		return value;		
	}
	
	@Override
	public int compareTo(Object o) {
		if (!(o instanceof Count)) return -1;
		Count c = (Count)o;
		int myCount=count();
		int yourCount=c.count();
		if (myCount == yourCount) return 0;
		else if (myCount < yourCount) return -1;
		else return 1;
	}
}