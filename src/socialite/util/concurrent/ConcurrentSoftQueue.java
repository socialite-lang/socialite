package socialite.util.concurrent;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentSoftQueue<E> {
    ConcurrentLinkedQueue<SoftReference<E>> queue;
    public ConcurrentSoftQueue() {
        queue = new ConcurrentLinkedQueue<SoftReference<E>>();
    }

    public boolean add(E e) {
        SoftReference<E> ref = new SoftReference<E>(e);
        return queue.add(ref);
    }

    public E poll() {
        while (true) {
            SoftReference<E> ref = queue.poll();
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
