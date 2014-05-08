package socialite.parser.antlr;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ColIter extends ColOpt {
	private static final long serialVersionUID = 1;

	public ColIter() {}
	
	public int hashCode() {
		return 42;
	}
	public boolean equals(Object o) {
		if (!(o instanceof ColIter)) return false;
		return true;
	}
	@Override
	public void readExternal(ObjectInput arg0) throws IOException,
			ClassNotFoundException {
		
	}
	@Override
	public void writeExternal(ObjectOutput arg0) throws IOException {
		
	}

	@Override
	public ColOpt clone() {
		return new ColIter();
	}
	
}
