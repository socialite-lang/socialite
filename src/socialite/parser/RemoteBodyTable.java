package socialite.parser;


import java.util.ArrayList;
import java.util.List;

import socialite.dist.msg.WorkerMessage;
import socialite.parser.antlr.IndexBy;
import socialite.parser.antlr.MultiSet;
import socialite.parser.antlr.NestedTableDecl;
import socialite.parser.antlr.Predefined;
import socialite.parser.antlr.TableDecl;
import socialite.parser.antlr.TableOpt;
import socialite.parser.antlr.ColumnDecl;
import socialite.parser.antlr.ColOpt;
import socialite.parser.antlr.ColSize;
import socialite.util.Assert;
import socialite.util.ByteBufferPool;

// table class for remote rule body
// e.g. for Triangle[0]($inc(1)) :- Edge[a](b), Edge[b](c), Edge[c](a).
//          we generate remote body tables(RemoteBody#1,2)
//			and also generate the following rule:
//			  Triangle[0]($inc(1)) :- RemoteBody#1(a, b), Edge[b](c), Edge[c](a).
//			  Triangle[0]($inc(1)) :- RemoteBody#2(a, b, c), Edge[c](a).
public class RemoteBodyTable extends Table implements GeneratedT {
	static final long serialVersionUID = 1;
	
	static List<ColumnDecl> createColDecls(List<Class> types, String colNameBase) {
		List<ColumnDecl> colDecls = new ArrayList<ColumnDecl>();
		for (int i=0; i<types.size(); i++) {
			ColOpt opt=null;			
			ColumnDecl decl=new ColumnDecl(types.get(i), colNameBase+i, opt);
			colDecls.add(decl);
		}		
		return colDecls;
	}
	
	static double guessRowSizeFromTypes(List<List<Class>> nestedTypes) { 
		double rowSize=0;			
		for (int i=0; i<nestedTypes.size(); i++) {
			List<Class> types = nestedTypes.get(i);
			
			float _size=ColumnDecl.guessRowSizeFromTypes(types);
			if (i!=nestedTypes.size()-1) _size += 4; // for idxToNest{1,2,3}
			rowSize = rowSize/4 + _size;
		}
		return 1+Math.ceil(rowSize);
	}
	
	static TableDecl genDecl(Rule r, int pos, List<List<Class>> nestedTypes) {
		if (nestedTypes.size()>4) {
			List<Class> lastCol=nestedTypes.get(3);			
			for (int i=4; i<nestedTypes.size(); i++)
				lastCol.addAll(nestedTypes.get(i));
			int removeCnt=nestedTypes.size()-4;
			for (int i=0; i<removeCnt; i++)
				nestedTypes.remove(4);
		}
		TableDecl td = null;
		ColumnDecl locationColDecl = null; /* No idx column for RemoteBodyTable (no [] operator) */
		NestedTableDecl nestedTableDecl = null;
		List<ColumnDecl> colDecls = null;
		for (int i=nestedTypes.size()-1; i>=0; i--) {
			if (i!=nestedTypes.size()-1) {
				nestedTableDecl = new NestedTableDecl(colDecls, nestedTableDecl);
			}
			
			colDecls = createColDecls(nestedTypes.get(i), "nest"+i+"_");			
			if (i==0) {
				td = new TableDecl(name(r, pos), locationColDecl, colDecls, nestedTableDecl);				
				double rowSize = guessRowSizeFromTypes(nestedTypes);
				int size = (int)((ByteBufferPool.bufferSize()-td.numAllColumns()*24)/rowSize);
				ColumnDecl last = td.last();
				last.setOption(new ColSize(size));
				return td;
			}
		}
		assert false:"should not reach here";
		return null;
	}
	
	Rule r;
	int pos;
	int lastColumnSize;
	double rowSize;
	List<Variable> vars;
	public RemoteBodyTable(Rule _r, int _pos, List<List<Class>> types) {
		super(genDecl(_r, _pos, types));
		r = _r;
		pos = _pos;
		lastColumnSize = ((ColSize)decl.last().option()).size();
		rowSize = guessRowSizeFromTypes(types);
	}
			
	public int lastColumnSize() { return lastColumnSize; }
	public double rowSize() { return rowSize; }
	
	public void setParamVars(List<Variable> _vars) {
		assert vars==null;
		vars = _vars;
	}
	public List<Variable> getParamVars() {
		assert vars!=null;
		return vars; 
	}
	
	@Override
	public boolean isSliced() { return false; }
	@Override
	public boolean isModTable() { return false; }
	
	public static String name(Rule r, int pos) {
		return "RemoteBodyTable_"+r.id()+"_"+pos;
	}

	@Override
	public int origId() {
		Assert.not_supported();
		return -1; // never reaches
	}
	@Override
	public Table origT() {
		Assert.not_supported();
		return null;
	}
}
