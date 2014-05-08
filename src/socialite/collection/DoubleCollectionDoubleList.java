package socialite.collection;

import gnu.trove.TDoubleCollection;
import gnu.trove.list.array.TDoubleArrayList;

public class DoubleCollectionDoubleList implements DoubleCollection {
	TDoubleArrayList list;
	
	public DoubleCollectionDoubleList() {}
	
	public void update(TDoubleCollection _list) {
		list = (TDoubleArrayList)_list;
	}
	
	@Override
	public boolean add(double d) {
		return list.add(d);
	}

	@Override
	public void clear() {
		list.resetQuick();
	}

	@Override
	public boolean contains(double d) {
		return list.contains(d);
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public DoubleIterator iterator() {
		return new DoubleIteratorDouble();
	}

	@Override
	public int size() {
		return list.size();
	}

	final class DoubleIteratorDouble implements DoubleIterator {
		int pos;
		DoubleIteratorDouble() { pos=0; }
		@Override
		public boolean hasNext() {			
			return pos<list.size();
		}
		@Override
		public double next() {
			double next=list.get(pos);
			pos++;
			return next;
		}

		@Override
		public void remove() {
			assert pos>0;
			list.removeAt(pos-1);
		}
		
	}
}
