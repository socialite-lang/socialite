package socialite.parser;

import gnu.trove.list.array.TIntArrayList;

import java.util.ArrayList;
import java.util.List;

import socialite.parser.antlr.ColIter;
import socialite.parser.antlr.MultiSet;
import socialite.parser.antlr.TableDecl;
import socialite.parser.antlr.TableOpt;
import socialite.parser.antlr.ColumnDecl;
import socialite.util.ByteBufferPool;

// table class for remote rule head for the given rule r
// e.g. for Foo[a](b) :- Bar[a](c), Baz[c](b).
//          we generate Remote-Foo table.
public class RemoteHeadTable extends Table implements GeneratedT, FixedSizeTable {
	int origId;
	Table origT;
	Rule rule;
	final int size;
	public RemoteHeadTable(Table t, Rule r) {
		super(genDecl(t, r));
		assert !(t instanceof GeneratedT);
		ConstCols.init(this, r.getHead());		
		size = determineSize(t.decl(), r);
		origT=t;
		origId=t.id();
		rule = r;		
		className = signature();
	}
	
	public int size() { return size; }
	
	public Rule getOrigRule() { return rule; }
	
	public String signature() {
		if (rule==null) return "not-initialized";
		
		String sig=defaultsig();
		
		for (int c:ConstCols.get(rule.getHead()))
			sig += "_const"+c;
		return sig+"_id"+origId;
	}
	
	static TableDecl genDecl(Table t, Rule r) {
		// We use flat (unnested) table for RemoteHeadTables.		
		String newName=name(t, r);
		List<ColumnDecl> colDecls = new ArrayList<ColumnDecl>();
		int columns=0;
		for (Object o:r.getHead().inputParams()) {
			Class type = MyType.javaType(o);
			String colname = "col"+(columns++);
			colDecls.add(new ColumnDecl(type, colname));
		}
		TableDecl decl=new TableDecl(newName, colDecls, null);		
		setMultiset(decl);
		return decl;
	}
	static int determineSize(TableDecl decl, Rule r) {
		int rowSize=0;
		TIntArrayList constCols = new TIntArrayList(ConstCols.get(r.getHead()));
		for (ColumnDecl d:decl.colDecls()) {
			if (d.option() instanceof ColIter) continue;
			if (constCols.contains(d.pos())) 
				rowSize += 1;
			else rowSize += d.getTypeSize();
		}		
		int overhead = 128+decl.numAllColumns()*32;
		return (ByteBufferPool.bufferSize()-overhead)/rowSize;		
	}
	static void setMultiset(TableDecl decl) {
		List<TableOpt> opts = new ArrayList<TableOpt>();
		opts.add(new MultiSet());
		decl.setOptions(opts);	
	}
	
	@Override
	public boolean isModTable() {
		return origT.isModTable();
	}
	
	@Override
	public boolean isSliced() {
		return false;
	}
	
	@Override public Table origT() { return origT; }
	@Override public int origId() { return origId; }
	
	public static String name(Table origT, Rule r) {
		assert !(origT instanceof GeneratedT);
		return "Remote_"+origT.name()+"_"+r.id();
	}
}