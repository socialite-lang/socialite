package socialite.parser.antlr;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ColRange extends ColOpt {
	private static final long serialVersionUID = 1;

	int from, to;
	public ColRange() {}
	public ColRange(int _from, int _to) {
		from = _from;
		to = _to;
	}
	public int from() { return from; }
	public int to() { return to; }
	public void set(int _from, int _to) {
		from=_from;
		to=_to;
	}
	
	public int hashCode() {
		int h=0;
		h ^= from;
		h ^= to;
		return h;
	}
	public boolean equals(Object o) {
		if (!(o instanceof ColRange)) return false;
		ColRange range=(ColRange)o;
		return from==range.from && to==range.to;
	}
	
	public ColRange clone() {
		return new ColRange(from, to);
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		from=in.readInt();
		to=in.readInt();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(from);
		out.writeInt(to);
	}
}
