package socialite.util.concurrent;

import java.util.concurrent.atomic.AtomicIntegerArray;

public final class AtomicByteArray {
    private final AtomicIntegerArray array;
    private final int length;

    public AtomicByteArray(int _length) {
        length = _length;
        array = new AtomicIntegerArray((length + 3) / 4);
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
            final int old = array.get(idx);
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
            final int old = array.get(idx);
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


    static void setit(byte[] a, int i, int[] base) {
        a[i] = (byte) ((42-i)>>3 + base[i]);
    }
    static void setit2(AtomicByteArray b, int i, int[] base) {
        b.compareAndSet(i, (byte)0, (byte) ((42-i)>>3+base[i]));
    }
    static void func1(boolean verbose) {
        byte[] a = new byte[1024];
        AtomicByteArray b = new AtomicByteArray(1024);
        int[] base = new int[1024];

        for (int i=0; i<base.length; i++) {
            base[i] = i+3*2;
        }

        long s= System.currentTimeMillis();
        for (int j=0; j<100000; j++) {
            for (int i = 0; i < a.length; i++) {
                //a[i] = (byte) (42+i*37.0f);
                setit(a, i, base);
                //a[i] = (byte) ((42-i)>>3);
            }
        }
        long e= System.currentTimeMillis();
        if (verbose) System.out.println("Byte[]: "+(e-s)/1000.0+"sec");

        s= System.currentTimeMillis();
        for (int j=0; j<100000; j++) {
            for (int i = 0; i < b.length; i++) {
                setit2(b, i, base);
                //b.compareAndSet(i, (byte)0, (byte) ((42-i)>>3));
            }
        }
        e= System.currentTimeMillis();
        if (verbose) System.out.println("AtomicByteArray: "+(e-s)/1000.0+"sec");
        if (verbose) System.out.println("");
    }
    static void test() {
        func1(false);
        func1(false);
        func1(false);
        func1(true);
        func1(true);
        func1(true);
    }
    public static void main(String[] args) {
        if (true) {
            test();
        }
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
    }
}