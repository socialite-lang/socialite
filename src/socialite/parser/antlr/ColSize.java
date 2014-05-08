package socialite.parser.antlr;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ColSize extends ColOpt {
	private static final long serialVersionUID = 1;

	int size;
	public ColSize() {}
	public ColSize(int _size) {
		size=_size;
	}
	public int size() { return size; }

	public int hashCode() {
		return size;
	}
	public boolean equals(Object o) {
		if (!(o instanceof ColSize)) return false;
		ColSize vs=(ColSize)o;
		return size==vs.size;
	}
	
	public ColSize clone() {
		return new ColSize(size);
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		size=in.readInt();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(size);
		
	}
}
