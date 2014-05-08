package socialite.parser.antlr;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ClearTable implements TableStmt {
	private static final long serialVersionUID = 1;	
	
	String tableName;
	int tableId;
	public ClearTable() {}
	
	public ClearTable(String _tableName) {
		this(_tableName, -1);
	}
	public ClearTable(String _tableName, int id) {
		tableName = _tableName;
		tableId = id;
	}
	
	public String toString() {
		return "ClearTable "+tableName+".";
	}
	public String tableName() { return tableName; }
	public void setId(int id) { tableId = id; } 
	public int id() { return tableId; }
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