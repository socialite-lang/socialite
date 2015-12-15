package socialite.functions;

import socialite.type.Utf8;

public class Min2 implements MeetOp {
	public static int invoke(int ans, int newVal) {
		if (ans<newVal) return ans;
		else return newVal;
	}	
	public static long invoke(long ans, long newVal) {
		if (ans<newVal) return ans;
		else return newVal;
	}
	public static float invoke(float ans, float newVal) {
		if (ans<newVal) return ans;
		else return newVal;
	}	
	public static double invoke(double ans, double newVal) {
		if (ans<newVal) return ans;
		else return newVal;
	}	
	public static String invoke(String ans, String newVal) {
		if (ans.compareTo(newVal)<0) return ans;
		else return newVal;
	}	
	public static Utf8 invoke(Utf8 ans, Utf8 newVal) {
		if (ans.compareTo(newVal)<0) return ans;
		else return newVal;
	}	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Comparable invoke(Comparable c1, Comparable c2) {
		if (c1.compareTo(c2)<0) return c1;
		else return c2;
	}
}
