package socialite.collection;

import gnu.trove.iterator.TFloatIterator;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import socialite.util.Assert;

public class SFloatConstArrayList implements Externalizable {
	private static final long serialVersionUID = 1;
	
	int capacity;
	float constVal;
	int size;
	public SFloatConstArrayList() {}
	public SFloatConstArrayList(Object _constVal, int _capacity) {
		constVal=(Float)_constVal;
		capacity = _capacity;
	}
	
	public boolean filledToCapacity() {
		return size==capacity;
	}
	public int size() { return size; }
	public float get(int offset) {
		return constVal;
	}
	public void add(float v) {
		assert constVal==v;
		size++;	
	}
	public float set(int offset, float v) {
		if (offset >= size) {
			throw new ArrayIndexOutOfBoundsException(offset);
		}
		assert constVal==v;
		return constVal;
	}
	public void insert(int offset, float v) {
		assert constVal==v;
		size++;
	}
	public float removeAt(int offset) {
		size--;
		return constVal;
	}
	
	public void clear() { size=0; }
	public void reset() { size=0; }
	public void resetQuick() { size=0; }
	
	public TFloatIterator iterator() {
		Assert.not_implemented();
		return null;
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		constVal = in.readFloat();
		capacity = in.readInt();
		size = in.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeFloat(constVal);
		out.writeInt(capacity);
		out.writeInt(size);
	}
}