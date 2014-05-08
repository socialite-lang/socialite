package socialite.parser;

import gnu.trove.list.array.TIntArrayList;

import java.util.ArrayList;
import java.util.List;

import socialite.codegen.CodeGen;
import socialite.dist.msg.WorkerMessage;
import socialite.parser.GeneratedT.ConstCols;
import socialite.parser.antlr.IndexBy;
import socialite.parser.antlr.MultiSet;
import socialite.parser.antlr.SortBy;
import socialite.parser.antlr.TableDecl;
import socialite.parser.antlr.TableOpt;
import socialite.parser.antlr.ColumnDecl;
import socialite.parser.antlr.ColSize;
import socialite.util.Assert;
import socialite.util.ByteBufferPool;

// table class for remote rule head for the given rule r
// e.g. for Foo[a](b) :- Bar[a](c), Baz[c](b).
//          we generate RemoteFoo table.
public class RemoteHeadTable extends Table implements GeneratedT {
	int origId;
	Table origT;
	Rule rule;
	public RemoteHeadTable(Table t, Rule r) {
		super(genDecl(t, r));
		ConstCols.init(this, r.getHead());
		
		origT=t;
		origId=t.id();
		rule = r;
		
		className = signature();
		
		assertNormalTable(t);
		assert !isArrayTable;		
	}
	
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
		for (Object o:r.getHead().getAllParamsExpanded()) {
			Class type = MyType.javaType(o);
			String colname = "col"+(columns++);
			colDecls.add(new ColumnDecl(type, colname));
		}
		TableDecl decl=new TableDecl(newName, null, colDecls, null);		
		setsize(decl, r);
		setMultiset(decl);
		return decl;
	}
	static void setsize(TableDecl decl, Rule r) {		
		assert decl.locationColDecl()==null;
		int rowSize=1;
		TIntArrayList constCols = new TIntArrayList(ConstCols.get(r.getHead()));
		for (ColumnDecl d:decl.colDecls()) {
			if (constCols.contains(d.pos())) 
				rowSize += 1;
			else rowSize += d.getTypeSize();
		}		
		int defaultSize = (ByteBufferPool.bufferSize()-decl.numAllColumns()*12)/rowSize;
		ColSize vsize = new ColSize(defaultSize);
		ColumnDecl first = decl.first();
		first.setOption(vsize);
	}
	static void setMultiset(TableDecl decl) {
		List<TableOpt> opts = new ArrayList<TableOpt>();
		opts.add(new MultiSet());
		decl.setOptions(opts);	
	}
	
	@Override
	public boolean isModTable() {
		//if (rule.inScc()) return false;
		
		return origT.isModTable();
	}
	
	@Override
	public boolean isSliced() {
		return false;
	}
	
	@Override public Table origT() { return origT; }
	@Override public int origId() { return origId; }
	
	static void assertNormalTable(Table t) {
		assert !(t instanceof DeltaTable): "Delta Table:"+t;
		assert !(t instanceof PrivateTable):"Private Table:"+t;
		assert !(t instanceof GeneratedT): "Generated table"+t;
	}
	
	public static String name(Table origT, Rule r) {
		assertNormalTable(origT);		
		return "Remote_"+origT.name()+"_"+r.id();
	}
}