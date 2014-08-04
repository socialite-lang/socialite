package socialite.functions;

import java.util.ArrayList;
import java.util.Iterator;

public class Str {
	public static String trim(String s, char d) {
        int len = s.length();
        int idx = 0;
        while ((idx < len) && (s.charAt(idx)<=d))
        	idx++;
	    while ((idx < len) && (s.charAt(len-1)<=d))
	    	len--;
	    return ((idx > 0) || (len < s.length())) ? s.substring(idx, len) : s;
	}
	public static String[] split(String str, char d) {
		return split(str, d, -1);
	}
	public static String[] split(String str, char d, int maxsplit) {
		ArrayList<String> splitted = new ArrayList<String>();
		int idx = 0, newIdx = -1;
		int splitnum=0;
		while ((newIdx = str.indexOf(d, idx)) != -1) {
			if (newIdx==idx) splitted.add("");
			if (newIdx > idx) splitted.add(str.substring(idx, newIdx));
			idx = newIdx + 1;
			splitnum++;
			if (maxsplit>0 && splitnum+1 >= maxsplit)
				break;
		}
		if (idx < str.length()) splitted.add(str.substring(idx));

		return splitted.toArray(new String[splitted.size()]);
	}
	
	public static String[] split(String str, String delim) {
		return split(str, delim, -1);
	}
	public static String[] split(String str, String delim, int maxsplit) {
		int delimLength = delim.length();
		if (delimLength==1) { return split(str, delim.charAt(0), maxsplit); }
		
		ArrayList<String> splitted = new ArrayList<String>();
		int idx = 0, newIdx = -1;
		int splitnum=0;
		while ((newIdx = str.indexOf(delim, idx)) != -1) {
			if (newIdx==idx) splitted.add("");
			if (newIdx > idx) splitted.add(str.substring(idx, newIdx));
			idx = newIdx + delimLength;
			splitnum++;
			if (maxsplit>0 && splitnum+1 >= maxsplit)
				break;
		}
		if (idx < str.length()) splitted.add(str.substring(idx));
		
		return splitted.toArray(new String[splitted.size()]);	
	}
	
	public static Iterator<String> splitIter(String str, String delim) {
		String[] splitted = split(str, delim);
		return new StrIter(splitted);
	}
	static class StrIter implements Iterator<String> {
		String[] items;
		int pos=0;
		StrIter(String[] _items) {
			items=_items;
		}
		@Override
		public boolean hasNext() { return pos<items.length; }
		@Override
		public String next() { return items[pos++]; }
		@Override
		public void remove() { }
	}
}
