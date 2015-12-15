package socialite.util.concurrent;

import java.util.Arrays;

// This class allows concurrent read access while a single writer updates.
// If there exist multiple writers, explicit lock is required.
public class ConcurrentReadIntArrayList {
    protected volatile int[] elems;
    protected volatile int size;

    public ConcurrentReadIntArrayList(int initCapacity) {
        elems = new int[initCapacity];
    }
    public ConcurrentReadIntArrayList() { this(10); }

    public void ensureCapacity(int minCapacity) {
        int oldCapacity = elems.length;
        if (minCapacity > oldCapacity) {
            @SuppressWarnings("unused")
            int oldData[] = elems;
            int newCapacity = (oldCapacity*3+1)/2+1;
            if (newCapacity < minCapacity)
                newCapacity = minCapacity;
            int[] _elems = Arrays.copyOf(elems, newCapacity);
            elems = _elems;
        }
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    public int indexOf(Object o) {
        if (o == null) { return -1; }

        for (int i=0; i < size; i++) {
            if (o.equals(elems[i])) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    public int get(int idx) {
        checkBounds(idx);
        return elems[idx];
    }

    public int getQuick(int idx) {
        return elems[idx];
    }

    public int set(int idx, int e) {
        checkBounds(idx);
        @SuppressWarnings("unchecked")
        int oldVal = elems[idx];
        elems[idx] = e;
        return oldVal;
    }
    public int setQuick(int idx, int e) {
        @SuppressWarnings("unchecked")
        int oldVal = elems[idx];
        elems[idx] = e;
        return oldVal;
    }

    public boolean add(int e) {
        ensureCapacity(size + 1);  // Increments modCount!!
        elems[size] = e;
        size++;
        return true;
    }

    public void replaceWith(int idx, int e1, int e2) {
        int[] elems2;
        int size2 = Math.min(elems.length, size+1);
        elems2 = new int[size2];

        System.arraycopy(elems, 0, elems2, 0, idx-1);
        elems2[idx] = e1;
        elems2[idx+1] = e2;
        System.arraycopy(elems, idx+1, elems2, idx+2, size-idx-1);
        size++;
        elems = elems2;
    }

    public void clear() {
        size = 0;
    }

    void checkBounds(int idx) {
        if (idx >= size) {
            throw new IndexOutOfBoundsException("Index:"+idx+", Size:"+size);
        }
    }
}
