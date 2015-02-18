package socialite.collection;

import socialite.collection.ArrayList;

import java.util.Collection;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.util.Arrays;

public class SArrayList<E> extends ArrayList<E> implements Externalizable {
	private static final long serialVersionUID = 1L;
	
	public SArrayList() { this(0); }
	public SArrayList(int size) { super(size); }	
	public SArrayList(Collection<? extends E> c) { super(c); }
	
	public void resetQuick() {
		size=0;
	}
	public int capacity() { 
		return elems.length;
	}
	// only used by DynamicNestedTables.stg#insert()
	public void setSize(int _size) {
		size = _size;
	}
	public boolean filledToCapacity() {
		int capacity = elems.length;
		if (size() == capacity) return true;
		return false;
	}
	
	public void addAllFast(SArrayList<E> other) {
		if (other==null) return;
		addAll(other);
	}
	
	public E getQuick(int offset) {
		return get(offset);
	}
	public E setQuick(int offset, E e) {
		return set(offset, e);
	}
	public int binarySearch(E value) {
		assert value instanceof Comparable;
		return binarySearch(value, 0, size());
	}
	public int binarySearch(E value, SIntArrayList sortedIndices) {
		assert value instanceof Comparable;
		return binarySearch(value, 0, size(), sortedIndices);
	}
	
	// copied from trove, TIntArrayList.binarySearch()
	@SuppressWarnings("unchecked")
	int binarySearch(E value, int fromIndex, int toIndex) {
		Comparable<E> v = (Comparable<E>)value;
		int low = fromIndex;
		int high = toIndex-1;
		
		while (low<=high) {
			int mid = ( low + high ) >>> 1;
	        @SuppressWarnings("rawtypes")
			Comparable midVal = (Comparable)get(mid);

	        if (midVal.compareTo(v) < 0)
	        	low = mid + 1;
	        else if (midVal.compareTo(v) > 0)
	        	high = mid - 1;
	        else return mid; // value found
		}
		return -(low+1);
	}	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	int binarySearch(E value, int fromIndex, int toIndex, SIntArrayList idx) {
		Comparable v = (Comparable)value;
		int low = fromIndex;
		int high = toIndex-1;
		
		while (low<=high) {
			int mid = ( low + high ) >>> 1;
	        Comparable midVal = (Comparable)get(idx.get(mid));

	        if (midVal.compareTo(v) < 0)
	        	low = mid + 1;
	        else if (midVal.compareTo(v) > 0)
	        	high = mid - 1;
	        else return mid; // value found
		}
		return -(low+1);
	}

	public void insert(int offset, E e) {
		add(offset, e);
	}
	public E removeAt(int offset) {
		return remove(offset);
	}
	
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(size());
		for (int i=0; i<size(); i++) {
			out.writeObject(elems[i]);			
		}
	}
	@SuppressWarnings("unchecked")
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		int len = in.readInt();
		int capacity = elems.length;
		if (capacity < len) {
			ensureCapacity(len);
		}
		for (int i=0; i<len; i++) {
			elems[i] = (E)in.readObject();
		}
		size = len;
	}
	
	public static void main(String[] args) {
		SArrayList<String> l = new SArrayList<String>();
		String[] input=new String[] {
			"this","you","input","assignment","a","characters","code", "composed" };
		for (String s:input) {
			int pos = l.binarySearch(s);
			if (pos < 0)
				pos = -pos-1;
			l.insert(pos, s);
		}
		for (String s:l) {
			System.out.println(s);
		}
		/*
		SArrayList<String> l1 = new SArrayList<String>();
		
		SArrayList<Integer> sortedl = new SArrayList<Integer>();
		SIntArrayList idx = new SIntArrayList();
		
		l0.add(1);
		l1.add("1");
		sortedl.add(1);
		idx.add(0);		
		
		int[] vals= new int[] {3,9,5,3,45,7,1,10,5};
		for (int i:vals) {
			l0.add(i);
			l1.add(""+i);
			int pos = sortedl.binarySearch(i);
			assert pos<0;
			if (pos<0)
				pos = -pos-1;
			sortedl.insert(pos, i);
			idx.insert(pos, l0.size()-1);
		}
		
		for (int i=0; i<idx.size(); i++) {
			int pos = idx.get(i);
			Integer ivalue = l0.get(pos);
			System.out.println(pos+", "+ivalue+":"+l1.get(pos));
		}*/
	}
}
