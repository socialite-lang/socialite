package socialite.parser.antlr;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

public abstract class TableOpt implements Externalizable {
	private static final long serialVersionUID = 1;

	String columnName;
	public TableOpt() {}
	public TableOpt(String _columnName) {
		columnName = _columnName;
	}
	public String columnName() {
		return columnName;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		columnName=(String)in.readObject();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(columnName);
	}
}
