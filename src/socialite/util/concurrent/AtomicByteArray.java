package socialite.util.concurrent;

import socialite.util.SociaLiteException;

import java.util.concurrent.atomic.AtomicIntegerArray;

public final class AtomicByteArray {
    private final AtomicIntegerArray array;
    private final int length;

    public AtomicByteArray(int _length) {
        length = _length;
        array = new AtomicIntegerArray((length + 3) / 4);
    }
    // this assumes synchronized call (using lazySet)
    public void fill(int from, int to, byte val) {
        from = from >>> 2;
        to = to >>> 2;
        if (to > array.length()) to = array.length();
        int ival = val | val<<8 | val<<16 | val<<24;
        for (int i=from; i<to; i++) {
            array.lazySet(i, ival);
        }
    }
    // this assumes synchronized call (using lazySet)
    public void fill(byte val) {
        int ival = val | val<<8 | val<<16 | val<<24;
        for (int i=0; i<array.length(); i++) {
            array.lazySet(i, ival);
        }
    }
    public void unsafeSet(int i, byte val) {
        int idx = i >>> 2;
        int shift = (i & 3) << 3;
        int mask = 0xFF << shift;
        int alignedVal = (val & 0xff) << shift;

        int old = this.array.get(idx);
        int newVal = (old & ~mask) | alignedVal;
        array.lazySet(idx, newVal);
    }

    public void set(int i, byte val) {
        int idx = i >>> 2;
        int shift = (i & 3) << 3;
        int mask = 0xFF << shift;
        int alignedVal = (val & 0xff) << shift;

        while (true) {
            int old = array.get(idx);
            int newVal = (old & ~mask) | alignedVal;
            if ((old == newVal) || array.compareAndSet(idx, old, newVal)) {
                return;
            }
        }
    }
    public boolean compareAndSet(int i, byte expected, byte val) {
        int idx = i >>> 2;
        int shift = (i & 3) << 3;
        int mask = 0xFF << shift;
        int alignedExpected = (expected & 0xff) << shift;
        int alignedVal = (val & 0xff) << shift;

        while (true) {
            int old = array.get(idx);
            if ((old & mask) != alignedExpected) return false;

            int newVal = (old & ~mask) | alignedVal;
            if ((old == newVal) || this.array.compareAndSet(idx, old, newVal)) {
                return true;
            }
        }
    }

    public final byte getAndIncrement(int i) { return getAndAdd(i, (byte)1); }

    public final byte getAndDecrement(int i) {
        return getAndAdd(i, (byte)-1);
    }

    public final byte getAndAdd(int i, byte delta) {
        while (true) {
            byte current = get(i);
            byte next = (byte)(current + delta);
            if (compareAndSet(i, current, next))
                return current;
        }
    }

    public final byte incrementAndGet(int i) {
        return addAndGet(i, (byte)1);
    }

    public final byte decrementAndGet(int i) {
        return addAndGet(i, (byte)-1);
    }

    public final byte addAndGet(int i, byte delta) {
        while (true) {
            byte current = get(i);
            byte next = (byte)(current + delta);
            if (compareAndSet(i, current, next))
                return next;
        }
    }

    public byte get(final int i) {
        return (byte)(array.get(i >>> 2) >> ((i & 3) << 3));
    }

    public int length() { return length; }

    public static void main(String[] args) {
        final AtomicByteArray barray = new AtomicByteArray(16);

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
        }

        ParUtil.parallel(16, new ParUtil.CallableBlock() {
            public void call(int index) {
                for (int i=0; i<1000; i++) {
                    barray.set(index, (byte)(index+42));
                }
            }
        });
        for (int i=0; i<barray.length(); i++) {
            assert barray.get(i) == i+42;
        }
        ParUtil.parallel(16, new ParUtil.CallableBlock() {
            public void call(int index) {
                for (int i=0; i<10001; i++) {
                    if (i%2==0) barray.compareAndSet(index, (byte)(index+42), (byte)(index+1));
                    else barray.compareAndSet(index, (byte)(index+1), (byte)(index+42));
                }
            }
        });

        for (int i=0; i<barray.length(); i++) {
            assert barray.get(i) == i+1;
        }
        barray.fill(1-1, 3+3, (byte)0);
        assert barray.get(0) == 0:barray.get(0)+"";
        assert barray.get(1) == 0;
        assert barray.get(2) == 0;
        assert barray.get(3) == 0;
        assert barray.get(4) != 0;
        barray.fill(4, barray.length()+4, (byte)2);
        assert barray.get(4) == 2;
        assert barray.get(5) == 2;
        assert barray.get(6) == 2;
        assert barray.get(7) == 2;
        assert barray.get(8) == 2;
        assert barray.get(barray.length()-1) == 2;
    }
}