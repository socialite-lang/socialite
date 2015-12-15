package socialite.util.concurrent;

public class NonAtomicReferenceArray<E> {
    final Object[] array;
    public NonAtomicReferenceArray(int length) {
        array = new Object[length];
    }

    public final int length() { return array.length; }
    public final E get(int i) {
        return (E)array[i];
    }
    public final void set(int i, E val) {
        array[i] = val;
    }

    public final boolean compareAndSet(int i, E expect, E update) {
        Object old = array[i];
        if (old == null) {
            if (expect == null) {
                array[i] = update;
                return true;
            } else {
                return false;
            }
        }

        if (old.equals(expect)) {
            array[i] = update;
            return true;
        } else {
            return false;
        }
    }

}
