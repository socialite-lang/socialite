package socialite.util;

import java.lang.ref.SoftReference;
import java.util.Arrays;

/**
 * implemented based on {@link socialite.util.ArrayQueue}
 */
public class SoftRefArrayQueue<E> {
	SoftReference<E>[] items;
	int size;
	int front, back;
	
	@SuppressWarnings("unchecked")
	public SoftRefArrayQueue(int capacity) {
		items = new SoftReference[capacity];
		empty();
	}	

	public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public synchronized void clear() { empty(); }
    public synchronized void empty() {
        size = 0;
        front = 0;
        back = -1;
        Arrays.fill(items, null);
    }

    public synchronized boolean contains(E e) {
    	if (e==null) return false;
    	int cnt=0;
    	int idx = front;
    	while (cnt < size) {
    		SoftReference<E> ref = items[idx];
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
        	SoftReference<E> ref = items[front];
        	e = ref.get();        	
        	items[front] = null;
        	front = incrementIdx(front);
        	if (e!=null) break;
        }
        return e;
    }
    
    public synchronized void add(E e) { enqueue(e); }
	public synchronized void enqueue(E e) {
        if (size == items.length)
            expand( );
        back = incrementIdx( back );
        items[back] = new SoftReference<E>(e);
        size++;
    }

    int incrementIdx(int idx) {
        if (++idx == items.length) idx = 0;
        return idx;
    }
    
	@SuppressWarnings("unchecked")
    void expand() {
        SoftReference<E>[] newItems = new SoftReference[(int)(items.length*1.7f)];

        for (int i = 0; i<size; i++, front=incrementIdx(front))
            newItems[i] = items[front];

        items = newItems;
        front = 0;
        back = size - 1;
    }
}