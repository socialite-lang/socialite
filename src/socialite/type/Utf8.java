package socialite.type;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * An immutable Utf8 string.
 */
public class Utf8 implements Comparable<Utf8>, CharSequence, Externalizable {
    static final long serialVersionUID = 42;
    static final Charset UTF8 = Charset.forName("UTF-8");
	static final byte[] EMPTY_BYTES = new byte[0];

	protected byte[] bytes = EMPTY_BYTES;
	protected int hash=0;

	public Utf8() { }

	public Utf8(String s) { 
		this.bytes = s.getBytes(UTF8); 
	}

	public Utf8(Utf8 other) {
		int len = other.bytes.length;
		this.bytes = new byte[len];
		System.arraycopy(other.bytes, 0, this.bytes, 0, len);
	}

	public Utf8(byte[] bytes, int start, int end) {
		this.bytes = new byte[end-start];
		System.arraycopy(bytes, start, this.bytes, 0, end-start);
	}

	public Utf8(byte[] bytes) {
		this(bytes, 0, bytes.length);
	}

	public Utf8(byte[] bytes, boolean dontcopy) {
		if (dontcopy) this.bytes = bytes;
		else {
			this.bytes = new byte[bytes.length];
			System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof Utf8)) return false;
		
		Utf8 u = (Utf8) o;
		if (bytes.length != u.bytes.length) 
			return false;
		
		byte[] b1=bytes;
		byte[] b2=u.bytes;			
		for (int i=0; i<bytes.length; i++)
			if (b1[i] != b2[i]) 
				return false;
		
		return true;
	}

	
	// Copied from http://d3s.mff.cuni.cz/~holub/sw/javamurmurhash/MurmurHash.java (Public domain)
	static int hash32(final byte[] data) {
		int length = data.length;
		int seed = 0x9747b28c;
		// 'm' and 'r' are mixing constants generated offline.
		// They're not really 'magic', they just happen to work well.
		final int m = 0x5bd1e995;
		final int r = 24;
		// Initialize the hash to a random value
		int h = seed^length;
		int length4 = length/4;

		for (int i=0; i<length4; i++) {
			final int i4 = i*4;
			int k = (data[i4+0]&0xff) +
					((data[i4+1]&0xff)<<8) +
					((data[i4+2]&0xff)<<16) +
					((data[i4+3]&0xff)<<24);
			k *= m; k ^= k >>> r; k *= m;
			h *= m; h ^= k;
		}
		
		// Handle the last few bytes of the input array
		switch (length%4) {
			case 3: h ^= (data[(length&~3) +2]&0xff) << 16;
			case 2: h ^= (data[(length&~3) +1]&0xff) << 8;
			case 1: h ^= (data[length&~3]&0xff);
					h *= m;
		}
		h ^= h >>> 13;
		h *= m;
		h ^= h >>> 15;

		return h;
	}
	
	@Override
	public int hashCode() {
		int h = hash;
		if (h==0 && bytes.length>0) {
			for (int i = 0; i < bytes.length; i++)
				h = h * 31 + bytes[i];
			//h = hash32(bytes);
			hash = h;
		}
		return h;
	}
		
	public byte[] getBytes() {
		byte[] copy = new byte[bytes.length];
		System.arraycopy(bytes, 0, copy, 0, copy.length);
		return copy;
	}

	public int byteLength() { return bytes.length; }

	@Override
	public String toString() {
		if (bytes.length == 0) return "";
		return new String(bytes, 0, bytes.length, UTF8);
	}
	
	@Override
	public int compareTo(Utf8 that) {
		int len = bytes.length;
		if (that.bytes.length < len)
			len = that.bytes.length;

		for (int i = 0; i < len; i++) {
			int a = (bytes[i] & 0xff);
			int b = (that.bytes[i] & 0xff);
			if (a != b) return a - b;
		}
		return bytes.length - that.bytes.length;
	}

	public byte byteAt(int index) {
		return bytes[index];
	}
	
	@Override
	public char charAt(int index) {
		return toString().charAt(index);
	}

	@Override
	public int length() {
		return toString().length();
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return new Utf8(bytes, start, end);
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(bytes.length);
		if (bytes.length==0) return;
		
		for (int i=0; i<bytes.length; i++) {
			out.write(bytes[i]);
		}
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		int len = in.readInt();
		if (len==0) {
			bytes = EMPTY_BYTES;
			return;
		}
		bytes = new byte[len];
		for (int i=0; i<len; i++)
			bytes[i] = in.readByte();
	}	
}