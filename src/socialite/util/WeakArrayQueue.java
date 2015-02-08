package socialite.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/**
 * implemented based on {@link socialite.util.ArrayQueue}
 */
public class WeakArrayQueue<E> {
	WeakReference<E>[] items;
	int size;
	int front, back;
	
	@SuppressWarnings("unchecked")
	public WeakArrayQueue(int capacity) {
		items = new WeakReference[capacity];
        size = 0;
        front = 0;
        back = -1;
	}	

	public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public synchronized void clear() { 
        while (size>0) {
        	size--;
        	WeakReference<E> ref = items[front];
        	items[front] = null;
        	ref.enqueue();        	
        	front = incrementIdx(front);
        }
        front = 0;
        back = -1;
    }

    public synchronized boolean contains(E e) {
        if (e==null) return false;
        int cnt=0;
        int idx = front;
        while (cnt < size) {
                WeakReference<E> ref = items[idx];
                if (e.equals(ref.get()))
                        return true;
                idx = incrementIdx(idx);
                cnt++;
        }
        return false;
    }
    public synchronized E get() { return dequeue(); }
    public synchronized E dequeue() {
        if (isEmpty()) return null;
           
        E e=null;
        while (size>0) {
        	size--;
        	WeakReference<E> ref = items[front];
        	e = ref.get();        	
        	items[front] = null;
        	front = incrementIdx(front);
        	if (e!=null) break;
        }
        return e;
    }
    public synchronized WeakReference<E> peek() {
        if (isEmpty()) return null;
           
        WeakReference<E> ref = items[front];
        return ref;
    }
    
    public synchronized void add(WeakReference<E> ref) { enqueue(ref); }
	public synchronized void enqueue(WeakReference<E> ref) {
        if (size == items.length)
            expand( );
        back = incrementIdx( back );
        items[back] = ref;
        size++;
    }

    int incrementIdx(int idx) {
        if (++idx == items.length) idx = 0;
        return idx;
    }
    
	@SuppressWarnings("unchecked")
    void expand() {
        WeakReference<E>[] newItems = new WeakReference[(int)(items.length*1.7f)];

        for (int i = 0; i<size; i++, front=incrementIdx(front))
            newItems[i] = items[front];

        items = newItems;
        front = 0;
        back = size - 1;
    }
}
