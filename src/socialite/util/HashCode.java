package socialite.util;

public class HashCode {
	public static int get(long l) {
		return (int)(l ^ (l >>> 32));
	}
	
	public static int get(int i) {
		return i;
	}
	
	public static int get(double d) {
		long bits=Double.doubleToLongBits(d);
		return (int)(bits^(bits>>>32));
	}
	
	public static int get(float f) {
		int bits=Float.floatToRawIntBits(f);
		return (int)(bits^(bits>>>16));
	}
	
	public static int get(Object o) {
		return o.hashCode();
	}	
}
