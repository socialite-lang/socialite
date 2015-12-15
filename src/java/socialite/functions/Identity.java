package socialite.functions;

public class Identity {
	public static int invoke(int x) { return x; }
	public static double invoke(double x) { return x; }
	public static Object invoke(Object x) { return x; }
	
	public static int invoke(int x, int y) { return x; }
	public static double invoke(double x, double y) { return x; }
	public static Object invoke(Object x, Object y) { return x; }
	
}
