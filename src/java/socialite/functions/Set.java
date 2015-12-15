package socialite.functions;

import java.util.ArrayList;
import java.util.Iterator;

public class Set {
	public static socialite.type.Set make(socialite.type.Set s, Object o) {
		socialite.type.Set s2=new socialite.type.Set(s);
		s2.add(o);
		return s2;
	}
	public static int contains(socialite.type.Set s, Object o) {
		if (s.contains(o)) return 1;
		else return 0;
	}
}
