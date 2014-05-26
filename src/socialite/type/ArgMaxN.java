package socialite.type;

import java.util.List;

import socialite.collection.SArrayList;
import gnu.trove.list.array.TIntArrayList;

public class ArgMaxN {
	TIntArrayList args;
	SArrayList<Object> vals;
	
	public ArgMaxN () {
		args=new TIntArrayList();
		vals=new SArrayList<Object>();
	}
	
	public boolean add(int limit, Object v, int _arg) {
		if (args.size() < limit) {
			int pos = args.binarySearch(_arg);
			if (pos<0) pos=-pos-1;
			args.insert(pos, _arg);
			vals.add(pos, v);
			return true;
		}
		assert args.size()==limit;
		
		int pos = args.binarySearch(_arg);
		if (pos<0) pos=-pos-1;
		
		if (pos==0) return false;
		else {
			for (int i=0; i<pos-1; i++) {
				args.setQuick(i, args.getQuick(i+1));
				vals.setQuick(i, vals.getQuick(i+1));				
			}
			args.setQuick(pos-1, _arg);
			vals.setQuick(pos-1, v);
			return true;
		}
	}
	
	public List<?> get() {
		return (List<?>)vals;
	}
	
	public static void main(String args[]) {
		ArgMaxN maxn = new ArgMaxN();
		
		for (int i=9; i>=5; i--) {
			maxn.add(9, ""+i, i);	
		}		
		for (int i=0; i<5; i++) {
			maxn.add(9, ""+i, i);	
		}		
		maxn.add(9, "10",10);
		for (Object o:maxn.get()) {
			System.out.println(o);
		}
	}
	public static void test(Object i, int a) {
		System.out.println(i+", "+a);
	}
}
