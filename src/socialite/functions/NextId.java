package socialite.functions;

import java.util.concurrent.atomic.AtomicInteger;

public class NextId {
	static AtomicInteger id=new AtomicInteger(0);
	
	public static void reset() {
		id.set(0);
	}
	public static int invoke() {
		return id.getAndIncrement();
	}
	
	public static int invoke(int i) {
		return id.getAndAdd(i);
	}
	public static int invoke(int old, int incr) {
		return old;
	}
}
