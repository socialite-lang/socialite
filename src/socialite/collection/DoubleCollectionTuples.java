package socialite.collection;

import java.util.Collection;
import java.util.Iterator;

import socialite.tables.Tuple;


public class DoubleCollectionTuples implements DoubleCollection {
	Collection<Tuple> tuples;
	int column;
	Tuple deltaTuple;
	
	public DoubleCollectionTuples() { column=0; }
	public DoubleCollectionTuples(int _column) { column=_column; }
	

	public void update(Collection<Tuple> _tuples) {	tuples = _tuples; }	
	public void update(Tuple t) { deltaTuple = t; }

	@Override
	public boolean add(double d) {
		deltaTuple.setDouble(column, d);
		return tuples.add(deltaTuple);
	}

	@Override
	public void clear() {
		tuples.clear();
	}
	@Override
	public boolean contains(double v) {
		DoubleIterator it = iterator();
		while(it.hasNext()) {
			double d=it.next();
			if (d==v) return true;
		}
		return false;
	}
	@Override
	public boolean isEmpty() {
		return tuples.isEmpty();
	}
	@Override
	public int size() {
		return tuples.size();
	}
	@Override
	public DoubleIterator iterator() {
		return new DoubleIteratorTuples();
	}

	final class DoubleIteratorTuples implements DoubleIterator {
		Iterator<Tuple> iter;
		DoubleIteratorTuples() {
			iter = tuples.iterator();
		}
		public boolean hasNext() {
			return iter.hasNext();
		}
		public double next() {
			Tuple t=iter.next();
			return t.getDouble(column);
		}
		public void remove() {
			iter.remove();
		}
	}

}
