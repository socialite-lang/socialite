package socialite.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.Collection;

import javax.naming.OperationNotSupportedException;

public class BloomFilter implements Externalizable {
	static final long serialVersionUID = 1L;

	private BitSet bitset;
	private float bitsPerElement;
	private int numElements;
	private short k; // # of hash functions

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(bitset);
		out.writeFloat(bitsPerElement);
		out.writeInt(numElements);
		out.writeShort(k);
	}

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		bitset = (BitSet) in.readObject();
		bitsPerElement = in.readFloat();
		numElements = in.readInt();
		k = in.readShort();
	}

	/**
	 * @param c the number of bits used per element.
	 * @param n the expected number of elements the filter will contain.
	 * @param k the number of hash functions used.
	 */
	public BloomFilter(float c, int n, int k) {
		this.k = (short) k;
		this.bitsPerElement = (float) c;
		int bitSetSize = (int) Math.ceil(c * n);
		numElements = 0;
		this.bitset = new BitSet(bitSetSize);
	}

	public BloomFilter(double c, int n, int k) {
		this((float) c, n, k);
	}

	/**
	 * Compares the contents of two instances to see if they are equal.
	 * 
	 * @param obj is the object to compare to.
	 * @return True if the contents of the objects are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BloomFilter other = (BloomFilter) obj;

		if (this.k != other.k) return false;
		if (this.bitsPerElement != other.bitsPerElement) return false;
		if (this.numElements != other.numElements) return false;
		return this.bitset.equals(other.bitset);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 61 * hash + (this.bitset != null ? this.bitset.hashCode() : 0);
		hash = 61 * hash + this.getBitSetSize();
		hash = 61 * hash + this.k;
		hash = 61 * hash + HashCode.get(bitsPerElement);
		hash = 61 * hash + HashCode.get(numElements);
		return hash;
	}


	/**
	 * Returns the value chosen for K (# of hash functions). 
	 * @return k.
	 */
	public int getK() { return k; }

	public void clear() {
		bitset.clear();
		numElements = 0;
	}


	static int hash0(int val) {
		// Robert Jenkin's 32bit int hash function
		val = (val + 0x7ed55d16) + (val << 12);
		val = (val ^ 0xc761c23c) ^ (val >> 19);
		val = (val + 0x165667b1) + (val << 5);
		val = (val + 0xd3a2646c) ^ (val << 9);
		val = (val + 0xfd7046c5) + (val << 3);
		val = (val ^ 0xb55a4f09) ^ (val >> 16);
		return val;
	}
	static int hash1(int val) {
		return Murmur3.hash32(val);
	}

	static long hash0(long val) {
		// see: https://gist.github.com/badboy/6267743
		val = (~val) + (val << 21);;
		val = val ^ (val >>> 24);
		val = (val + (val << 3)) + (val << 8); 
		val = val ^ (val >>> 14);
		val = (val + (val << 2)) + (val << 4); 
		val = val ^ (val >>> 28);
		val = val + (val << 31);
		return val;
	}
	static long hash1(long val) {
		return Murmur3.hash64(val);
	}
	
	int[] hash(int val) {
		int[] h = new int[k];
		int h0=hash0(val);
		int h1=hash1(val);
		for (int i=0; i<k; i++) {
			int x=h0+i*h1;
			h[i] = Math.abs(x)%getBitSetSize();
		}
		return h;
	}
	int[] hash(long val) {
		int[] h = new int[k];
		long h0=hash0(val);
		long h1=hash1(val);
		for (int i=0; i<k; i++) {
			long x=h0+i*h1;
			if (x<0) {
				x = -x;
				if (x==Long.MIN_VALUE)
					x = 0;
			}
			h[i] = (int)(x%getBitSetSize());
		}
		return h;
	}

	public boolean isFull() {
		if (bitset.cardinality()*2+1 >= getBitSetSize())
			return true;
		return false;
	}

	public boolean add(int v) {
		boolean added = false;
		for (int h:hash(v)) {
			if (!bitset.get(h))
				added = true;
			bitset.set(h, true);
		}
		numElements++;
		return added;		
	}
	public boolean add(long v) {
		boolean added = false;
		for (int h:hash(v)) {
			if (!bitset.get(h))
				added = true;
			bitset.set(h, true);
		}
		numElements++;
		return added;
	}
	public boolean add(float f) {
		int i = Float.floatToRawIntBits(f);
		return add(i);
	}
	public boolean add(double d) {
		long l = Double.doubleToRawLongBits(d);
		return add(l);
	}
	public boolean add(Object o) {
		throw new SociaLiteException("Object type is not supported by BloomFilter yet.");
	}

	public boolean contains(int v) {
		for (int h:hash(v)) {
			if (!bitset.get(h))
				return false;
		}
		return true;		
	}
	public boolean contains(long v) {
		for (int h:hash(v)) {			
			if (!bitset.get(h))
				return false;
		}
		return true;
	}
	public boolean contains(float f) {
		int i = Float.floatToRawIntBits(f);
		return contains(i);
	}
	public boolean contains(double d) {
		long l = Double.doubleToRawLongBits(d);
		return contains(l);
	}
	public boolean contains(Object o) {
		throw new SociaLiteException("Adding object to BloomFilter is not supported yet");
	}

	public int bitSetSize() { return this.getBitSetSize(); }
	public int size() { return this.numElements; }

	public int count() { return this.numElements; }

	public int getNumExpectedElements() { return (int)(this.getBitSetSize()/bitsPerElement); }
	
	public double getExpectedBitsPerElement() { return (double)this.bitsPerElement; }

	int getBitSetSize() { return bitset.size(); }
}
