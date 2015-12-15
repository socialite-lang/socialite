package socialite.parser.antlr;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class DropTable implements TableStmt {
	String tableName;
	int tableId;
	public DropTable() {} 
	
	public DropTable(String _tableName) {
		tableName = _tableName;
		tableId = -1;
	}
	public String tableName() { return tableName; }
	public void setId(int id) { tableId = id; }
	public int id() { return tableId; }

	public String toString() {
		return "DropTable "+tableName+".";
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		char[] _name = new char[in.readInt()];
		for (int i=0; i<_name.length; i++)
			_name[i] = in.readChar();
		tableName = new String(_name);
		tableId = in.readInt();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(tableName.length());
		out.writeChars(tableName);
		out.writeInt(tableId);
	}
}