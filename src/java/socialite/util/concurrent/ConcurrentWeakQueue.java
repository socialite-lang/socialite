package socialite.util.concurrent;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentWeakQueue<E> {
    ConcurrentLinkedQueue<WeakReference<E>> queue;
    public ConcurrentWeakQueue() {
        queue = new ConcurrentLinkedQueue<WeakReference<E>>();
    }

    public boolean add(E e) {
        WeakReference<E> ref = new WeakReference<E>(e);
        return queue.add(ref);
    }

    public E poll() {
        while (true) {
            WeakReference<E> ref = queue.poll();
            if (ref == null)
                return null;
            E e =  ref.get();
            if (e != null)
                return null;
        }
    }

    public int size() {
        return queue.size();
    }
}
