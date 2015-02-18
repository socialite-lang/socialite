package socialite.functions;

import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.iterator.TFloatIterator;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.iterator.TLongIterator;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TLongArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import socialite.type.ApproxCount;
import socialite.type.ApproxSet;
import socialite.type.Avg;
import socialite.type.Count;
import socialite.type.Utf8;
import socialite.util.SociaLiteException;

public class Builtin {
	public static int nextId(int inc) { return NextId.invoke(inc); }
	public static int nextId(int prevId, int ignored) { return prevId; }
	public static int resetId() { NextId.reset(); return 0; }
	
	public static int contains(ApproxSet s, int v) { return s.contains(v)?1:0; }
	public static boolean add(ApproxSet s, int v) { return s.add(v); }
	public static boolean add(ApproxCount c, int v) { return c.add(v); }
	
	public static Object choice(Object a, Object b) { return Choice.invoke(a, b); }
	public static int choice(int a, int b) { return Choice.invoke(a, b); }
	public static long choice(long a, long b) { return Choice.invoke(a, b); }
	public static float choice(float a, float b) { return Choice.invoke(a, b); }
	public static double choice(double a, double b) { return Choice.invoke(a, b); }
	public static String choice(String a, String b) { return (String)Choice.invoke(a, b); }
	public static Utf8 choice(Utf8 a, Utf8 b) { return (Utf8)Choice.invoke(a, b); }
		
	public static int toInt(String s) { return Integer.parseInt(s); }
	public static long toLong(String s) { return Long.parseLong(s); }
	public static float toFloat(String s) { return Float.parseFloat(s); }
	public static double toDouble(String s) { return Double.parseDouble(s); }
	public static Utf8 toUtf8(String s) { return new Utf8(s); }
		
	public static String toStr(int i) { return ""+i; }
	public static String toStr(long l) { return ""+l; }
	public static String toStr(float f) { return ""+f; }
	public static String toStr(double d) { return ""+d; }
	public static String toStr(char c) { return ""+c; }
	public static String toStr(short s) { return ""+s; }
	public static String toStr(boolean b) { return ""+b; }
	public static String toStr(Object o) { return ""+o; }

	public static int inc(int a, int b) { return Sum.invoke(a,b); }
	public static long inc(long a, long b) { return Sum.invoke(a,b); }
	public static float inc(float a, float b) { return Sum.invoke(a,b); }
	public static double inc(double a, double b) { return Sum.invoke(a,b); }
	public static String inc(String a, String b) { return Sum.invoke(a,b); }
	public static Utf8 inc(Utf8 a, Utf8 b) { return Sum.invoke(a,b); }
	public static int[] inc(int[] a, int[] b) { return Sum.invoke(a,b); }
	public static long[] inc(long[] a, long[] b) { return Sum.invoke(a,b); }
	public static float[] inc(float[] a, float[] b) { return Sum.invoke(a,b); }
	public static double[] inc(double[] a, double[] b) { return Sum.invoke(a,b); }
	public static String[] inc(String[] a, String[] b) { return Sum.invoke(a,b); }
	public static Utf8[] inc(Utf8[] a, Utf8[] b) { return Sum.invoke(a,b); }
		
	public static int sum(int a, int b) { return Sum.invoke(a,b); }
	public static long sum(long a, long b) { return Sum.invoke(a,b); }
	public static float sum(float a, float b) { return Sum.invoke(a,b); }
	public static double sum(double a, double b) { return Sum.invoke(a,b); }
	public static String sum(String a, String b) { return Sum.invoke(a,b); }
	public static Utf8 sum(Utf8 a, Utf8 b) { return Sum.invoke(a,b); }
	public static int[] sum(int[] a, int[] b) { return Sum.invoke(a,b); }
	public static long[] sum(long[] a, long[] b) { return Sum.invoke(a,b); }
	public static float[] sum(float[] a, float[] b) { return Sum.invoke(a,b); }
	public static double[] sum(double[] a, double[] b) { return Sum.invoke(a,b); }
	public static String[] sum(String[] a, String[] b) { return Sum.invoke(a,b); }
	public static Utf8[] sum(Utf8[] a, Utf8[] b) { return Sum.invoke(a,b); }
	
	public static boolean avg(Avg a, int b) { return Avg.invoke(a,b); }
	public static boolean avg(Avg a, long b) { return Avg.invoke(a,b); }
	public static boolean avg(Avg a, float b) { return Avg.invoke(a,b); }
	public static boolean avg(Avg a, double b) { return Avg.invoke(a,b); }
	public static boolean avg(Avg[] a, int[] b) { return Avg.invoke(a,b); }
	public static boolean avg(Avg[] a, long[] b) { return Avg.invoke(a,b); }
	public static boolean avg(Avg[] a, float[] b) { return Avg.invoke(a,b); }
	public static boolean avg(Avg[] a, double[] b) { return Avg.invoke(a,b); }
	
	public static boolean median(Avg a, int b) { return Avg.invoke(a,b); }
	public static boolean median(Avg a, long b) { return Avg.invoke(a,b); }
	public static boolean median(Avg a, float b) { return Avg.invoke(a,b); }
	public static boolean median(Avg a, double b) { return Avg.invoke(a,b); }
	
	public static boolean count(Count a, int b) { return Count.invoke(a,b); }
	public static boolean count(Count a, long b) { return Count.invoke(a,b); }
	public static boolean count(Count a, float b) { return Count.invoke(a,b); }
	public static boolean count(Count a, double b) { return Count.invoke(a,b); }
	public static boolean count(Count a, Object b) { return Count.invoke(a,b); }

	public static int min(int a, int b) { return Min.invoke(a,b); }
	public static long min(long a, long b) { return Min.invoke(a,b); }
	public static float min(float a, float b) { return Min.invoke(a,b); }
	public static double min(double a, double b) { return Min.invoke(a,b); }
	public static String min(String a, String b) { return Min.invoke(a,b); }
	public static Utf8 min(Utf8 a, Utf8 b) { return Min.invoke(a,b); }
	
	public static int max(int a, int b) { return Max.invoke(a,b); }
	public static long max(long a, long b) { return Max.invoke(a,b); }
	public static float max(float a, float b) { return Max.invoke(a,b); }
	public static double max(double a, double b) { return Max.invoke(a,b); }
	public static String max(String a, String b) { return Max.invoke(a,b); }
	public static Utf8 max(Utf8 a, Utf8 b) { return Max.invoke(a,b); }
	
	public static boolean argmin(socialite.type.ArgMin a, int i, int v) { return a.argmin(i,v); }
	public static boolean argmin(socialite.type.ArgMin a, int i, long v) { return a.argmin(i,v); }
	public static boolean argmin(socialite.type.ArgMin a, int i, float v) { return a.argmin(i,v); }
	public static boolean argmin(socialite.type.ArgMin a, int i, double v) { return a.argmin(i,v); }
	
	public static Iterator<String> read(String file) { return Read.invoke(file); }

	public static String[] split(String s, String delim, int maxsplit) { return Str.split(s, delim, maxsplit); }
	public static String[] split(String s, String delim) { return Str.split(s, delim); }
	public static String[] split(String s) { return Str.split(s, "\t"); }

	public static Iterator<String> splitIter(String s) { return Str.splitIter(s, "\t"); }
	public static Iterator<String> splitIter(String s, String delim) { return Str.splitIter(s, delim); }

	public static int id() { return MyId.invoke(); }
	
	public static TIntIterator range(int s, int e) { return Range.invoke(s,e); }

	public static Object[] array(Object...o) { return o; }
	public static String[] sarray(String ...s) { return s; }	
	public static Utf8[] uarray(Utf8 ...u) { return u; }	
	public static int[] iarray(int ...i) { return i; }	
	public static long[] larray(long ...l) { return l; }	
	public static float[] farray(float ...f) { return f; }	
	public static double[] darray(double ...d) { return d; }
	
	public static Object[] arraynew(int len, Object init) { return ArrayUtil.fill(new Object[len], init); }
	public static String[] arraynew(int len, String init) { return ArrayUtil.fill(new String[len], init); }
	public static Utf8[] arraynew(int len, Utf8 init) { return ArrayUtil.fill(new Utf8[len], init); }
	public static int[] arraynew(int len, int init) { return ArrayUtil.fill(new int[len], init); }
	public static long[] arraynew(int len, long init) { return ArrayUtil.fill(new long[len], init); }
	public static float[] arraynew(int len, float init) { return ArrayUtil.fill(new float[len], init); }
	public static double[] arraynew(int len, double init) { return ArrayUtil.fill(new double[len], init); }	
	
	public static Object itemAt(Object[] arr, int idx) { return arr[idx]; }
	public static String itemAt(String[] arr, int idx) { return arr[idx]; }
	public static Utf8 itemAt(Utf8[] arr, int idx) { return arr[idx]; } 
	public static int itemAt(int[] arr, int idx) { return arr[idx]; } 
	public static long itemAt(long[] arr, int idx) { return arr[idx]; } 
	public static float itemAt(float[] arr, int idx) { return arr[idx]; } 
	public static double itemAt(double[] arr, int idx) { return arr[idx]; } 
		
	public static Object[] unpack(Object o) {
		if (o.getClass().isArray()) return (Object[] )o;
		else throw new SociaLiteException("Cannot unpack non-array type object:"+o);
	}
	public static Avg[] unpack(Avg[] d) { return d; }	
	public static Count[] unpack(Count[] d) { return d; }
	public static String[] unpack(String[] s) { return s; }
	public static Utf8[] unpack(Utf8[] u) { return u; }
	public static int[] unpack(int[] i) { return i; }
	public static long[] unpack(long[] l) { return l; }
	public static float [] unpack(float [] f) { return f; }
	public static double[] unpack(double[] d) { return d; }	
	
	public static Iterator<?> iterate(Object[] o) { return Arrays.asList(o).iterator(); }
	public static Iterator<String> iterate(String[] s) { return Arrays.asList(s).iterator(); }
	public static Iterator<Utf8> iterate(Utf8[] u) { return Arrays.asList(u).iterator(); }
	public static TIntIterator iterate(int[] a) { return new TIntArrayList(a).iterator(); }
	public static TLongIterator iterate(long[] a) { return new TLongArrayList(a).iterator(); }
	public static TFloatIterator iterate(float[] a) { return new TFloatArrayList(a).iterator(); }
	public static TDoubleIterator iterate(double[] a) { return new TDoubleArrayList(a).iterator(); }
}

class ArrayUtil {
	static int[] fill(int[] arr, int init) {
		Arrays.fill(arr, init);
		return arr;
	}
	static long[] fill(long[] arr, long init) {
		Arrays.fill(arr, init);
		return arr;
	}
	static float[] fill(float[] arr, float init) {
		Arrays.fill(arr, init);
		return arr;
	}
	static double[] fill(double[] arr, double init) {
		Arrays.fill(arr, init);
		return arr;
	}
	static String[] fill(String[] arr, String init) {
		Arrays.fill(arr, init);
		return arr;
	}
	static Utf8[] fill(Utf8[] arr, Utf8 init) {
		Arrays.fill(arr, init);
		return arr;
	}
	static Object[] fill(Object[] arr, Object init) {
		Arrays.fill(arr, init);
		return arr;
	}
}
