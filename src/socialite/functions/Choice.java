package socialite.functions;

public class Choice {
	public static int invoke(int v) { 
	    System.out.println("Choice.invoke("+v+") called!");
	    return v; 
	}
	public static int invoke(int old, int _new) { return old; }
	
	public static long invoke(long v) { return v; }
	public static long invoke(long old, long _new) { return old; }
	
	public static float invoke(float v) { return v; }
	public static float invoke(float old, float _new) { return old; }
	
	public static double invoke(double v) { return v; }
	public static double invoke(double old, double _new) { return old; }
	
	public static Object invoke(Object v) { return v; }
	public static Object invoke(Object old, Object _new) { return old; }
}
