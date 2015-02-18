package socialite.functions;

import socialite.collection.ArrayList;
import socialite.util.SociaLiteException;

import java.util.Iterator;

public class TestFunc {
	public static int[] getIntArray(int a, int b) {
		return new int[] {a, b};
	}
	public static Object[] getObjArray(int a, int b) { return new Integer[]{a,b};}
    public static Iterator<Object[]> getObjArrayIterator(int a, int b) {
        Object[] x={a,b};
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        list.add(x);
        return list.iterator();
    }
	public static Iterator<int[]> getIntArrayIterator(int a, int b) {
		int[] x={a,b};
		ArrayList<int[]> list = new ArrayList<int[]>();
		list.add(x);
		return list.iterator();
	}

    public static int raiseError(String s) {
        throw new SociaLiteException(s);
    }
	public static int print(String s) {
		System.out.println(s);
		return 1;
	}
}
