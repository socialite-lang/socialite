package socialite.collection;

import gnu.trove.iterator.TDoubleIterator;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import socialite.util.Assert;

public class SDoubleConstArrayList implements Externalizable {
	private static final long serialVersionUID = 1;
	
	int capacity;
	double constVal;
	int size;
	public SDoubleConstArrayList() {}
	public SDoubleConstArrayList(Object _constVal, int _capacity) {
		constVal=(Double)_constVal;
		capacity = _capacity;
	}
	
	public boolean filledToCapacity() {
		return size==capacity;
	}
	public int size() { return size; }
	public double get(int offset) {
		return constVal;
	}
	public void add(double v) {
		assert constVal==v;
		size++;	
	}
	public double set(int offset, double v) {
		if (offset >= size) {
			throw new ArrayIndexOutOfBoundsException(offset);
		}
		assert constVal==v;
		return constVal;
	}
	public void insert(int offset, double v) {
		assert constVal==v;
		size++;
	}
	public double removeAt(int offset) {
		size--;
		return constVal;
	}
	
	public void clear() { size=0; }
	public void reset() { size=0; }
	public void resetQuick() { size=0; }
	
	public TDoubleIterator iterator() {
		Assert.not_implemented();
		return null;
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		constVal = in.readDouble();
		capacity = in.readInt();
		size = in.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeDouble(constVal);
		out.writeInt(capacity);
		out.writeInt(size);
	}
}