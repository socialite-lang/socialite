package socialite.util.concurrent;

import java.util.Arrays;


/**
 * This class allows concurrent read access while a single writer updates.
 * If there exist multiple writers, explicit lock is required.
 * {@link "socialite/codegen/DynamicTable.stg"}
 */
public class ConcurrentReadArrayList<E> {
    protected volatile Object[] elems;
    protected volatile int size;

    public ConcurrentReadArrayList(int initCapacity) {
        elems = new Object[initCapacity];
    }
    public ConcurrentReadArrayList() { this(10); }

    private ConcurrentReadArrayList(Object[] _elems, int _size) {
        elems = _elems;
        size = _size;
    }


    public void ensureCapacity(int minCapacity) {
        int oldCapacity = elems.length;
        if (minCapacity > oldCapacity) {
            @SuppressWarnings("unused")
            Object oldData[] = elems;
            int newCapacity = (oldCapacity*3+1)/2+1;
            if (newCapacity < minCapacity)
                newCapacity = minCapacity;
            Object[] _elems = Arrays.copyOf(elems, newCapacity);
            elems = _elems;
        }
    }

    public synchronized ConcurrentReadArrayList<E> snapshot() {
        Object[] _elems = Arrays.copyOf(elems, size);
        int _size = size;
        return new ConcurrentReadArrayList<>(_elems, _size);
    }
    public synchronized void replaceWith(int idx, E e1, E e2) {
        Object[] elems2;
        int size2 = Math.max(elems.length, size+1);
        elems2 = new Object[size2];

        System.arraycopy(elems, 0, elems2, 0, idx);
        elems2[idx] = e1;
        elems2[idx+1] = e2;
        System.arraycopy(elems, idx+1, elems2, idx+2, size-idx-1);
        elems = elems2;
        size++;
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
    public E get(int idx) {
        checkBounds(idx);
        return (E) elems[idx];
    }

    public E getQuick(int idx) {
        return (E) elems[idx];
    }

    public E set(int idx, E e) {
        checkBounds(idx);
        @SuppressWarnings("unchecked")
        E oldVal = (E) elems[idx];
        elems[idx] = e;
        return oldVal;
    }
    public E setQuick(int idx, E e) {
        @SuppressWarnings("unchecked")
        E oldVal = (E) elems[idx];
        elems[idx] = e;
        return oldVal;
    }

    public boolean add(E e) {
        ensureCapacity(size + 1);  // Increments modCount!!
        elems[size] = e;
        size++;
        return true;
    }



    public void clear() {
        for (int i=0; i<size; i++) {
            elems[i] = null;
        }
        size = 0;
    }

    void checkBounds(int idx) {
        if (idx >= size) {
            throw new IndexOutOfBoundsException("Index:"+idx+", Size:"+size);
        }
    }
}
