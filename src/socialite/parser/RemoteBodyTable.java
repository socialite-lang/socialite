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
import socialite.util.Assert;
import socialite.util.ByteBufferPool;

/**
 *  Table class representing remote rule body
 *  e.g. Triange(0, $inc(1)) :- Edge(a,b), Edge(b,c), Edge(c,a).
 *         For the first and second join operation in the rule body,
 *         we perform distributed join. 
 *         The system automatically transfers and stores the data in "remote body tables",
 *         and adds extra rules as following.
 *       Triange(0, $inc(1)) :- RemoteEdge(a,b), Edge(b,c), Edge(c,a).
 *       Triange(0, $inc(1)) :- RemoteEdge(a,b,c), Edge(c,a).
 *         where the join operation betweem RemoteEdge to the next table is always local join operation.
 *       
 */
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
	
	static TableDecl genDecl(String name, List<List<Class>> nestedTypes) {
		if (nestedTypes.size()>4) {
			List<Class> lastCol=nestedTypes.get(3);			
			for (int i=4; i<nestedTypes.size(); i++)
				lastCol.addAll(nestedTypes.get(i));
			int removeCnt=nestedTypes.size()-4;
			for (int i=0; i<removeCnt; i++)
				nestedTypes.remove(4);
		}
		NestedTableDecl nestedTableDecl = null;
		List<ColumnDecl> colDecls = null;
		for (int i=nestedTypes.size()-1; i>=0; i--) {
			if (i!=nestedTypes.size()-1) {
				nestedTableDecl = new NestedTableDecl(colDecls, nestedTableDecl);
			}
			
			colDecls = createColDecls(nestedTypes.get(i), "nest"+i+"_");			
			if (i==0) {
				return new TableDecl(name, colDecls, nestedTableDecl);
			}
		}
		assert false:"should not reach here";
		return null;
	}
	
	Rule r;
	int pos;
	List<Variable> vars;
	public RemoteBodyTable(Rule _r, int _pos, List<List<Class>> types) {
		super(genDecl(name(_r, _pos), types));
		r = _r;
		pos = _pos;
	}
	
	// XXX: make RemoteBodyTable to have the related predicate, which has the variables.
	public void setParamVars(List<Variable> _vars) {
		assert vars==null;
		vars = _vars;
	}
	public List<Variable> getParamVars() { return vars;	}
	
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
