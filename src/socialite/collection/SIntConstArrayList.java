package socialite.collection;

import gnu.trove.iterator.TIntIterator;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import socialite.util.Assert;

public class SIntConstArrayList implements Externalizable {
	private static final long serialVersionUID = 42;
	
	int capacity;
	int constVal=Integer.MIN_VALUE;
	int size;
	public SIntConstArrayList() {}
	public SIntConstArrayList(int _capacity) {		
		capacity = _capacity;
	}
	
	public int capacity() { return capacity; }
	
	public boolean filledToCapacity() {
		return size==capacity;
	}
	public int size() { return size; }
	public int getQuick(int offset) {
		return constVal;
	}
	public int get(int offset) {
		return constVal;
	}
	public void add(int v) {
		constVal = v;
		size++;
	}
	public int setQuick(int offset, int v) {
		return set(offset, v);
	}
	public int set(int offset, int v) {
		if (offset >= size) {
			throw new ArrayIndexOutOfBoundsException(offset);
		}
		constVal = v;
		return constVal;
	}
	
	public void addAllFast(SIntConstArrayList other) {
		if (size==0) constVal = other.constVal;
		assert constVal == other.get(0);
		capacity += other.capacity();
		size += other.size();
	}
	public void insert(int offset, int v) {
		constVal = v;
		assert offset == size;
		size++;
	}
	public int removeAt(int offset) {
		size--;
		return constVal;
	}
	
	public void clear() { size=0; }
	public void reset() { size=0; }
	public void resetQuick() { size=0; }
	
	public TIntIterator iterator() {
		Assert.not_implemented();
		return null;
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		constVal = in.readInt();
		capacity = in.readInt();
		size = in.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(constVal);
		out.writeInt(capacity);
		out.writeInt(size);
	}
}
