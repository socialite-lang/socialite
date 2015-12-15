package socialite.util;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FastQueue<T> {
	ArrayList<T> queue;
	ArrayList<T> reserved;
	String name;
	boolean wait=false;
	public FastQueue(String name) {
		this(1024, name);
	}
	public FastQueue(int size, String _name) {
		queue = new ArrayList<T>(size);
		reserved = new ArrayList<T>(16);
		name = _name;
	}
	
	public synchronized void empty() {
		queue.clear();
	}
	public int likelySize() {
		return queue.size();
	}	
	public synchronized boolean isEmpty() {
		return queue.isEmpty() && reserved.isEmpty();
	}	
	public boolean isLikelyEmpty() {
		return queue.isEmpty() && reserved.isEmpty();// && queue.isEmpty();
	}
	
	public synchronized T reserve() throws InterruptedException {
		while (queue.isEmpty()) {
			wait=true;
			wait();
		}	
		T t=queue.remove(0);
		reserved.add(t);
		return t;
	}	
	
	public synchronized void pop(T t) {
		reserved.remove(t);
	}
	
	public synchronized void add(T t) {
		queue.add(t);
		if (wait) {
			wait=false;
			notifyAll();
		}
		/*if (queue.size() > 20*1024) {
			System.out.println("Queue size too big:"+name+"["+queue.size()+"]");
		}*/
	}
}

/*
public class FastQueue<T> {
	ConcurrentLinkedQueue<T> queue;
	ConcurrentLinkedQueue<T> reserved;
	boolean wait=false;
	public FastQueue() {
		this(4*1024);
	}
	public FastQueue(int size) {
		queue = new ConcurrentLinkedQueue<T>();
		reserved = new ConcurrentLinkedQueue<T>();
	}
	
	public void empty() {
		queue.clear();
	}
	public synchronized boolean isEmpty() {
		return queue.isEmpty() && reserved.isEmpty();
	}
	
	public boolean isLikelyEmpty() {
		return queue.isEmpty() && reserved.isEmpty() && queue.isEmpty();
	}
	
	public T reserve() throws InterruptedException {
		while (true) {
			synchronized(this) {
				T t=queue.poll();
				if (t==null) {
					wait=true;
					wait();
				} else {
					reserved.add(t);
					return t;
				}
			}
		}		
	}
	
	public void pop(T t) {
		reserved.remove(t);
	}
	
	public void add(T t) {
		boolean wasEmpty = queue.isEmpty();
		queue.add(t);
		if (wasEmpty) {
			synchronized(this) {
				wait=false;
				notifyAll();
			}
		}
	}
}
*/