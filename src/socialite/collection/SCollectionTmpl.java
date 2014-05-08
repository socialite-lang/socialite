package socialite.collection;

import socialite.tables.Tuple;

public abstract class SCollectionTmpl implements SCollection {
	public boolean add(Tuple t) {
		throw new UnsupportedOperationException();
	}
	public boolean add(Object o) {
		throw new UnsupportedOperationException();
	}
	public boolean add(int i) {
		throw new UnsupportedOperationException();
	}
	public boolean add(long l) {
		throw new UnsupportedOperationException();
	}
	public boolean add(float f) {
		throw new UnsupportedOperationException();
	}
	public boolean add(double d) {
		throw new UnsupportedOperationException();
	}
	
	public boolean remove(Tuple t) {
		throw new UnsupportedOperationException();
	}
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}
	public boolean remove(int i) {
		throw new UnsupportedOperationException();
	}
	public boolean remove(long l) {
		throw new UnsupportedOperationException();
	}
	public boolean remove(float f) {
		throw new UnsupportedOperationException();
	}
	public boolean remove(double d) {
		throw new UnsupportedOperationException();
	}
	
	public boolean removeFirst() {
		throw new UnsupportedOperationException();
	}
	public boolean removeLast() {
		throw new UnsupportedOperationException();
	}
	
	public boolean isSorted() { return false; }
	public boolean isSortedAsc() { return false; }
	public boolean isSortedDesc() { return isSorted() && !isSortedAsc(); }
	
	public boolean contains(Tuple t) {
		throw new UnsupportedOperationException();
	}
	public boolean contains(Object o) {
		throw new UnsupportedOperationException();
	}
	public boolean contains(int i) {
		throw new UnsupportedOperationException();
	}
	public boolean contains(long l) {
		throw new UnsupportedOperationException();
	}
	public boolean contains(float f) {
		throw new UnsupportedOperationException();
	}
	public boolean contains(double d) {
		throw new UnsupportedOperationException();
	}
}
