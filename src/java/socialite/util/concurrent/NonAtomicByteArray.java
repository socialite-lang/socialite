package socialite.util.concurrent;

import java.util.Arrays;

// This class is non-atomic version of AtomicByetArray.
// Used by ArrayTable.stg and ArrayNestedTable.stg, if concurrency is not needed for the generated table
public final class NonAtomicByteArray {
    private final byte[] array;

    public NonAtomicByteArray(int _length) {
        array = new byte[_length];
    }

    public void fill(int from, int to, byte val) {
        if (to > array.length) to = array.length;
        Arrays.fill(array, from, to, val);
    }
    public void fill(byte val) {
        Arrays.fill(array, 0, array.length, val);
    }
    public void unsafeSet(int i, byte val) { array[i] = val; }
    public void set(int i, byte val) { array[i] = val; }
    public boolean compareAndSet(int i, byte expected, byte val) {
        if (array[i] == expected) {
            array[i] = val;
            return true;
        }
        return false;
    }

    public final byte getAndIncrement(byte i) {
        return getAndAdd(i, (byte)1);
    }

    public final byte getAndDecrement(byte i) {
        return getAndAdd(i, (byte)-1);
    }

    public final byte getAndAdd(int i, byte delta) {
        byte current = get(i);
        byte next = (byte) (current + delta);
        set(i, next);
        return current;
    }

    public final byte incrementAndGet(int i) {
        return addAndGet(i, (byte)1);
    }

    public final byte decrementAndGet(int i) {
        return addAndGet(i, (byte)-1);
    }

    public final byte addAndGet(int i, byte delta) {
        byte current = get(i);
        byte next = (byte)(current + delta);
        set(i, next);
        return next;
    }

    public byte get(final int i) { return array[i]; }

    public int length() { return array.length; }

    public static void main(String[] args) {
        final NonAtomicByteArray barray = new NonAtomicByteArray(16);

        assert barray.get(1) == 0;
        for (int i=0; i<barray.length(); i++) {
            barray.set(i, (byte)(i+1));
        }
        for (int i=0; i<barray.length(); i++) {
            assert barray.get(i) == i+1;
        }
        for (int i=0; i<barray.length(); i++) {
            barray.unsafeSet(i, (byte) (i + 2));
        }
        for (int i=0; i<barray.length(); i++) {
            assert barray.get(i) == i+2;
            byte val = barray.getAndAdd(i, (byte)42);
            assert val == i+2;
            assert barray.get(i) == i+2+42;
            val = barray.addAndGet(i, (byte) -42);
            assert val == i+2;
            assert barray.get(i) == i+2;
        }
    }
}