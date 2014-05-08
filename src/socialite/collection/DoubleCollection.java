package socialite.collection;

public interface DoubleCollection {
	public boolean add(double d);
	public void clear();
	public boolean contains(double d);
	public boolean isEmpty();
	public int size();
	public DoubleIterator iterator();
}
