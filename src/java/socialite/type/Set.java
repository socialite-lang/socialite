package socialite.type;

import java.util.HashSet;
import java.util.Collection;

public class Set extends HashSet {
	public Set(Set s) {
		super((Collection)s);
	}
}
