package socialite.type;

public class Avg {
	public static boolean invoke(Avg a, int b) { return a.add(b); }
	public static boolean invoke(Avg a, long b) { return a.add(b); }
	public static boolean invoke(Avg a, float b) { return a.add(b); }
	public static boolean invoke(Avg a, double b) { return a.add(b); }	
	
	public static boolean invoke(Avg[] a, int[] b) { 
		boolean f=false;
		for (int i=0; i<a.length; i++)
			f |= a[i].add(b[i]);
		return f;
	}
	public static boolean invoke(Avg[] a, long[] b) { 
		boolean f=false;
		for (int i=0; i<a.length; i++)
			f |= a[i].add(b[i]);
		return f;
	}
	public static boolean invoke(Avg[] a, float[] b) { 
		boolean f=false;
		for (int i=0; i<a.length; i++)
			f |= a[i].add(b[i]);
		return f;
	}
	public static boolean invoke(Avg[] a, double[] b) { 
		boolean f=false;
		for (int i=0; i<a.length; i++)
			f |= a[i].add(b[i]);
		return f;
	}	
	
	
	public double value;
	int count;
	
	public void inc() { count++; }
	public boolean add(double v) {
		if (count==0) {
			inc();
			value = v;
		} else {
			inc();					
			value += (v-value)/count;
		}
		return true;
	}
	public double get() {
		return value;
	}
	public String toString() {
		return ""+value;
	}
	
	public static void main(String[] args) {
		Avg a=new Avg();
		
		a.add(10);
		a.add(11);
		System.out.println("10.5 == "+a.get());
	}
}
