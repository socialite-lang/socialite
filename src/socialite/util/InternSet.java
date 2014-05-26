package socialite.util;

import socialite.collection.ArrayList;

public class InternSet<T> {
	static int hash(int h) {
	    if (h<0) {
	    	if (h==Integer.MIN_VALUE) h++;
	    	return -h;
	    }
	    return h;
	}
	 
	Object[] entry;
	int size;

	public InternSet(int capacity) {
		entry = new Object[capacity];
	}
	
	@SuppressWarnings("unchecked")
	public T get(T t) {
		int h = hash(t.hashCode());
		int idx = h%entry.length;
		Object o = entry[idx];
		if (o == null) {
			entry[idx] = (Object)t;
			size++;
			return t;
		} else if (o instanceof ArrayList) {
			ArrayList<T> list = (ArrayList<T>)o;
			for (int i=0; i<list.size(); i++) {
				T x = list.get(i);
				if (t.equals(x))
					return x;				
			}
			list.add(t);
			size++;
			return t;
		} else {
			if (t.equals(o)) return (T)o;
			else {
				@SuppressWarnings("rawtypes")
				ArrayList<T> list = new ArrayList(2);
				list.add((T)o);
				list.add(t);
				entry[idx] = list;
				size++;
				return t;
			}
		}
		
	}
	public void clear() {
		Object[] tab = entry;
		for (int i=0; i<tab.length; i++)
			tab[i] = null;
		size=0;
	}
	public int size() {
		return size;
	}
}
