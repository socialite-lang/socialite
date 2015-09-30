package socialite.util;

public class Assert {
	public static void equals(float a, float b) {
		float diff = Math.abs(a-b);
		if (diff > Math.abs(a)*0.0001)
			throw new AssertionError(a + " != " + b);
	}
	public static void equals(double a, double b) {
		double diff = Math.abs(a-b);
		if (diff > Math.abs(a)*0.0001)
			throw new AssertionError(a + " != " + b);
	}
	
	public static void true_(boolean cond) {
		if (!cond) {
			throw new AssertionError("Assertion failed!");
		}
	}
	public static void true_(boolean cond, String msg) {
		if (!cond) {
			throw new AssertionError("Assertion failed:"+msg);
		}
	}
	
	public static void not_null(Object o) {
		Assert.true_(o!=null);
	}
	
	public static void not_null(Object o, String msg) {
		Assert.true_(o!=null, msg);
	}
	
	public static void not_true(boolean cond) {
		if (cond) {
			throw new AssertionError("Assertion failed!");
		}
	}
	
	public static void not_true(boolean cond, String msg) {
		if (cond) {
			throw new AssertionError("Assertion failed:"+msg);
		}
	}
	
	public static void impossible() { throw new AssertionError("Impossible!"); }
	
	public static void impossible(String msg) {
		throw new AssertionError("this is impossible:"+msg);
	}
	
	public static void not_supported(String msg) {
		throw new AssertionError(msg);
	}
	
	public static void not_supported() {
		throw new AssertionError("not supported");
	}
	
	public static void not_implemented() {
		throw new AssertionError("not implemented");
	}
	
	public static void not_implemented(String msg) {
		throw new AssertionError("not implemented:"+ msg);
	}
	
	public static void equals(Object o1, Object o2) {
		if (!o1.equals(o2)) {
			throw new AssertionError("Assertion failed:"+o1+" != "+o2);
		}
	}
	public static void equals(Object o1, Object o2, String msg) {
		if (!o1.equals(o2)) {
			throw new AssertionError("Assertion failed:"+msg);
		}
	}
	
	public static void die(String message) {
		throw new AssertionError(message);
	}
}
