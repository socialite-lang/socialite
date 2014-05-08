package socialite.parser.antlr;

import java.io.Externalizable;

public interface TableStmt extends Externalizable {
	String tableName();
	void setId(int id);
	int id();
}
