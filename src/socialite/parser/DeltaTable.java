package socialite.parser;

import gnu.trove.list.array.TIntArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import socialite.parser.antlr.IndexBy;
import socialite.parser.antlr.MultiSet;
import socialite.parser.antlr.SortBy;
import socialite.parser.antlr.TableDecl;
import socialite.parser.antlr.TableOpt;
import socialite.parser.antlr.ColumnDecl;
import socialite.parser.antlr.ColSize;
import socialite.resource.Sender;
import socialite.util.AnalysisException;
import socialite.util.Assert;
import socialite.util.InternalException;

// DeltaTable does not have nesting (so based on DynamicTable.stg)
public class DeltaTable extends Table implements GeneratedT {
	int origId=-1;
	Table origT=null;	
	DeltaRule rule=null;
	public DeltaTable(Table t, DeltaRule r) {
		super(genDecl(t, r));
		ConstCols.init(this, r.getTheP());
		
		origT=t;
		origId=t.id();
		rule = r;
		assertNormalTable(t);

		className = signature();
	}
	public DeltaRule getDeltaRule() { return rule; }
	
	@Override public Table origT() { return origT; }
	@Override public int origId() { return origId; }
	
	public String signature() {
		if (rule==null) return "not-initialized";
		
		String sig=defaultsig();
		
		for (int c:ConstCols.get(rule.getTheP()))
			sig += "_const"+c;
		return sig+"_id"+origId;
	}
	
	public boolean isCompatible(DeltaRule dr) {		
		if (!dr.getTheP().name().equals(rule.getTheP().name()))
			return false;
		
		int[] otherConstCols = ConstCols.get(dr.getTheP());
		int[] constCols = ConstCols.get(rule.getTheP());
		if (Arrays.equals(constCols, otherConstCols))
			return true;
		return false;
	}
		
	/** compare {@link RemoteHeadTable#genDecl()} */
	static TableDecl genDecl(Table t, DeltaRule r) {		
		String newName=name(t);
		List<ColumnDecl> colDecls = new ArrayList<ColumnDecl>();
		int columns=0;
		for (Object o:r.getTheP().getAllParamsExpanded()) {				
			Class type = MyType.javaType(o);
			String colname = "col"+(columns++);
			colDecls.add(new ColumnDecl(type, colname));
		}
		TableDecl decl=new TableDecl(newName, null, colDecls, null);		
		setsize(decl, t, r);
		setMultiset(decl);
		return decl;
	}
	static void setsize(TableDecl decl, Table t, DeltaRule r) {
		ColumnDecl first = decl.first();
		first = decl.first();
		int size=-1;
		if (t.getColumn(0).hasRange()) {
			int[] range=t.getColumn(0).getRange();
			size=(range[1]-range[0])/10;
		} else if (t.getColumn(0).hasSize()) {
			size = t.getColumn(0).getSize()/16;	
		} else { size = 1024;}
		//if (r.isDeltaStepOpt()) size/=2;
		assert size>=0;
		if (size<1024) size=1024;
		ColSize optSize = new ColSize(size);
		first.setOption(optSize);
	}
	static void setMultiset(TableDecl decl) {
		List<TableOpt> opts = new ArrayList<TableOpt>();
		opts.add(new MultiSet());		
		decl.setOptions(opts);
	}
	
	@Override
	public boolean isModTable() { return false; }
	@Override
	public boolean hasOrderBy() { return false; }
	
	@Override
	public boolean isSliced() { return false; }
	
	// 
	static void assertNormalTable(Table t) {
		assert !(t instanceof GeneratedT): "Table "+t.name()+" is not a normal table!";
	}	
	public static String name(Table origT) {
		assertNormalTable(origT);
		return "Delta_"+origT.name();
		//return "Delta$"+origT.name();
	}
	public static String name(Predicate origP) {
		return "Delta_"+origP.name();
	}
}
