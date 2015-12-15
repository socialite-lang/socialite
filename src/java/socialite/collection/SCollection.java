package socialite.collection;

import socialite.tables.Tuple;

public interface SCollection {
	public boolean isEmpty();
	public void clear();
	public boolean add(Tuple t);
	public boolean add(int i);
	public boolean add(long l);
	public boolean add(float f);
	public boolean add(double d);
	
	public boolean remove(Tuple t);
	public boolean remove(int i);
	public boolean remove(long l);
	public boolean remove(float f);
	public boolean remove(double d);
	
	public boolean contains(Tuple t);
	public boolean contains(int i);
	public boolean contains(long l);
	public boolean contains(float f);
	public boolean contains(double d);
	
	public boolean isSorted();
	public boolean isSortedAsc();
	public boolean isSortedDesc();
	public boolean removeLast();
	public boolean removeFirst();
	
	public void iterate(TupleVisitor v);
}

