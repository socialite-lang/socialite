package socialite.collection;

import gnu.trove.iterator.TLongIterator;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import socialite.util.Assert;

public class SLongConstArrayList implements Externalizable {
	private static final long serialVersionUID = 42;
	
	int capacity;
	long constVal=Long.MIN_VALUE;
	int size;
	public SLongConstArrayList() {}
	public SLongConstArrayList(int _capacity) {		
		capacity = _capacity;
	}
	
	public int capacity() { return capacity; }
	
	public boolean filledToCapacity() {
		return size==capacity;
	}
	public int size() { return size; }
	public long getQuick(int offset) {
		return constVal;
	}
	public long get(int offset) {
		return constVal;
	}
	public void add(long v) {
		constVal = v;
		size++;
	}
	public long setQuick(int offset, long v) {
		return set(offset, v);
	}
	public long set(int offset, long v) {
		if (offset >= size) {
			throw new ArrayIndexOutOfBoundsException(offset);
		}
		constVal = v;
		return constVal;
	}
	
	public void addAllFast(SLongConstArrayList other) {
		if (size==0) constVal = other.constVal;
		assert constVal == other.get(0);
		capacity += other.capacity();
		size += other.size();
	}
	public void insert(int offset, long v) {
		constVal = v;
		assert offset == size;
		size++;
	}
	public long removeAt(int offset) {
		size--;
		return constVal;
	}
	
	public void clear() { size=0; }
	public void reset() { size=0; }
	public void resetQuick() { size=0; }
	
	public TLongIterator iterator() {
		Assert.not_implemented();
		return null;
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		constVal = in.readLong();
		capacity = in.readInt();
		size = in.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(constVal);
		out.writeInt(capacity);
		out.writeInt(size);
	}
}
