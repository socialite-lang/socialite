package socialite.parser;

import socialite.parser.antlr.TableDecl;
import socialite.util.IdFactory;

public class IterTable extends Table {
	static final long serialVersionUID = 1;
	public static String name(String declName, int iter) {
		return declName+"$"+iter;
	}
	
	String name;
	int iter;
	public IterTable(TableDecl _decl, int _iter) {
		decl = _decl;
		id = IdFactory.nextTableId();
		iter = _iter;
		name = IterTable.name(decl.name(), iter);
		init();
	}
	
	public int iterColumn() { return decl.iterColumn(); }
	public String name() { return name; }
}