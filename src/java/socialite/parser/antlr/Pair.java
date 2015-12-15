package socialite.parser.antlr;

public class Pair<F, S> {
	public F first;
	public S second;
	public Pair(F first_, S second_) {
		first=first_;
		second=second_;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Pair)) return false;
		
		Pair p = (Pair)o;
		if (!p.first.equals(first)) return false;
		if (!p.second.equals(second)) return false;
		
		return true;
	}
}
