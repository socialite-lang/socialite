package socialite.util;

import java.util.Arrays;


public class ArrayQueue<E> {
    E[] items;
    int size;
    int front, back;

   @SuppressWarnings("unchecked")
	public ArrayQueue(int capacity) {
        items = (E[])new Object[capacity];
        empty();
    }
	
	public int size() { return size; }
    public boolean isEmpty() { return size == 0; }    

    public void clear() { empty(); }    
    public void empty() {
    	if (size < items.length/4) {
    		while (size>0) {
    			items[front] = null;
    			front = incrementIdx(front);
    			size--;
    		}
    	} else {
    		Arrays.fill(items, null);	
    	}
        size = 0;
        front = 0;
        back = -1;        
    }

    public boolean contains(E e) {
    	if (e==null) return false;
    	int cnt=0;
    	int idx = front;
    	while (cnt < size) {
    		if (e.equals(items[idx]))
    			return true;
    		idx = incrementIdx(idx);
    		cnt++;
    	}
    	return false;
    }
    
    public E get() { return dequeue(); }
    public E dequeue() {
        if (isEmpty()) return null;
            
        size--;
        E e = items[front];
        items[front] = null;
        front = incrementIdx(front);
        return e;
    }
    
    public E peek() {
        if (isEmpty()) return null;
        return items[front];
    }

    public void add(E e) { enqueue(e); }
    public void enqueue(E e) {
        if (size == items.length)
            expand( );
        back = incrementIdx( back );
        items[back] = e;
        size++;
    }

    int incrementIdx(int idx) {
        if (++idx == items.length) idx = 0;
        return idx;
    }
    
	@SuppressWarnings("unchecked")
    void expand() {
        E[] newItems = (E[])new Object[(int)(items.length*1.7f)];

        for (int i = 0; i<size; i++, front=incrementIdx(front))
            newItems[i] = items[front];

        items = newItems;
        front = 0;
        back = size - 1;
    }
	
	public static void main(String[] args) {
		
		/*
		ArrayQueue<Integer> q = new ArrayQueue(3);
		
		q.enqueue(10);
		System.out.println(q.dequeue());
		q.enqueue(11);
		q.enqueue(12);
		System.out.println(q.dequeue());
		q.enqueue(13);
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
		
		q.enqueue(14);
		q.enqueue(15);
		q.enqueue(16);
		q.enqueue(17);
		System.out.println(q.dequeue());
		
		System.out.println("must be true:"+q.contains(16));
		System.out.println("must be true:"+q.contains(15));
		
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());		
		System.out.println(q.dequeue());
		System.out.println("must be false:"+q.contains(16));
		System.out.println("must be false:"+q.contains(15));*/
	}
}