package socialite.functions;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

import socialite.tables.Tuple;
import socialite.type.Utf8;
import socialite.util.Assert;
import gnu.trove.TDoubleCollection;
import gnu.trove.TIntCollection;
import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.iterator.TIntIterator;

public class Sum {
	public static int invoke(int a, int b) { return a+b; }	
	public static long invoke(long a, long b) { return a+b; }
	public static float invoke(float a, float b) { return a+b; }
	public static double invoke(double a, double b) { return a+b; }
	public static String invoke(String a, String b) { return a+b; }
	public static Utf8 invoke(Utf8 a, Utf8 b) { 
		byte[] c1=a.getBytes();
		byte[] c2=b.getBytes();
		byte[] r=new byte[c1.length+c2.length];
		System.arraycopy(c1, 0, r, 0, c1.length);
		System.arraycopy(c2, 0, r, c1.length, c2.length);
		return new Utf8(r);
	}
	
	public static int[] invoke(int[] a, int[] b) {
		for (int i=0; i<a.length; i++) a[i] += b[i];
		return a;
	}
	public static long[] invoke(long[] a, long[] b) {
		for (int i=0; i<a.length; i++) a[i] += b[i];
		return a;
	}
	public static float[] invoke(float[] a, float[] b) {
		for (int i=0; i<a.length; i++) a[i] += b[i];
		return a;
	}
	public static double[] invoke(double[] a, double[] b) {
		for (int i=0; i<a.length; i++) a[i] += b[i];
		return a;
	}
	public static String[] invoke(String[] a, String[] b) {
		for (int i=0; i<a.length; i++) a[i] += b[i];
		return a;
	}
	public static Utf8[] invoke(Utf8[] a, Utf8[] b) {
		for (int i=0; i<a.length; i++) a[i] = invoke(a[i], b[i]);
		return a;
	}
	
	public static void main(String[] args) {
		Utf8 a[]=new Utf8[]{new Utf8("a"), new Utf8("1")};
		Utf8 b[]=new Utf8[]{new Utf8("b"), new Utf8("2")};
		Utf8 c[]=Sum.invoke(b, a);
		for (Utf8 u:c) {
			System.out.println(u);
		}
	}
}
