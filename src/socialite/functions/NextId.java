package socialite.functions;

import java.util.concurrent.atomic.AtomicInteger;

public class NextId {
	// for distributed setting, id is initialized to be the machine id,
	// and is incremented by the cluster size.
	static AtomicInteger id=new AtomicInteger(0);
	
	public static int set(int val) {
      id.set(val);
      return val;
  }
	public static int reset() {
      id.set(0);
      return 0;
  }

  static void maybeInit() {
      if (id.get()==0) {
          int init = Builtin.id();
          id.compareAndSet(0, init);
      }
  }
	public static int invoke() {
		  maybeInit();
      return id.getAndIncrement();
	}
	
	public static int invoke(int inc) {
      maybeInit();
		  return id.getAndAdd(inc);
	}
	public static int invoke(int old, int incr) {
		return old;
	}
}