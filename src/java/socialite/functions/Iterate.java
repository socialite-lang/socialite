package socialite.functions;

import java.util.Iterator;

import socialite.util.SociaLiteException;

public class Iterate {
	public static Iterator<Object> invoke(Object o) {
		if (!(o instanceof Iterable)) {
			String msg="The argument to $Iterate is required to implement Iterable";
			throw new SociaLiteException(msg);
		}
		@SuppressWarnings("unchecked")
		Iterable<Object> iter=(Iterable<Object>)o;
		return iter.iterator();
	}
}
