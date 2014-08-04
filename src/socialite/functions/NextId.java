package socialite.functions;

import java.util.concurrent.atomic.AtomicInteger;

public class NextId {
	// for distributed setting, id is initialized to be the machine id,
	// and is incremented by the cluster size.
	static AtomicInteger id=new AtomicInteger(0);
	
	public static void set(int val) { id.set(val); }
	public static void reset() { id.set(0); }
	
	public static int invoke() {
		return id.getAndIncrement();
	}
	
	public static int invoke(int inc) {
		return id.getAndAdd(inc);
	}
	public static int invoke(int old, int incr) {
		return old;
	}
}