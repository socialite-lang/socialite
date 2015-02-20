package socialite.codegen;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.python.antlr.ast.Tuple;
import org.python.modules.synchronize;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import socialite.parser.Column;
import socialite.parser.Const;
import socialite.parser.IterTable;
import socialite.parser.MyType;
import socialite.parser.Param;
import socialite.parser.Predicate;
import socialite.parser.Query;
import socialite.parser.Table;
import socialite.parser.Variable;
import socialite.parser.antlr.ColumnGroup;
import socialite.tables.QueryVisitor;
import socialite.tables.TableUtil;
import socialite.util.InternalException;
import socialite.util.SociaLiteException;

//import org.antlr.stringtemplate.StringTemplate;
//import org.antlr.stringtemplate.StringTemplateGroup;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.TIntSet;

public class QueryCodeGen {
	static String queryPackage="socialite.query";
	static int queryCount=0;
	
	String queryVisitorName;
	Query query;
	QueryVisitor queryVisitor;	
	STGroup tmplGroup;
	ST queryTmpl;
	Table table;
	Predicate queryP;
	TIntObjectHashMap<Variable> constVars;
	boolean isSequential;
	
	public QueryCodeGen(Query _query, Map<String, Table> _tableMap, QueryVisitor qv,
						boolean _isSequential) {
		query = _query;
	
		tmplGroup = CodeGen.getVisitorGroup();
		queryTmpl = tmplGroup.getInstanceOf("class");	
		table = _tableMap.get(query.getP().name());		
		constVars = makeConstVars(query.getP());		
		queryP = makeNewPredicate(query.getP(), constVars);	
				
		queryVisitorName = "Query"+queryP.name()+"_"+queryCount++;
		isSequential=_isSequential;
		assert !queryP.hasFunctionParam();
	}
	
	@SuppressWarnings("unchecked")
	Predicate makeNewPredicate(Predicate oldP, TIntObjectHashMap<Variable> constVars) {
		Predicate p = oldP.clone();
		for (int i=0; i<oldP.params.size(); i++) {
			Object o = oldP.params.get(i);
			if (o instanceof Const)
				p.params.set(i, constVars.get(i));
		}
		return p;
	}
	TIntObjectHashMap<Variable> makeConstVars(Predicate p) {
		TIntObjectHashMap<Variable> vars = new TIntObjectHashMap<Variable>();		
		Column cols[] = table.getColumns();
		for (int i=0; i<p.inputParams().length; i++) {
			Param o = p.inputParams()[i];
			if (o instanceof Const) {
				Const cons = (Const)o;
				Column c = cols[i];			
				assert MyType.javaType(cons.type).equals(c.type());
				vars.put(i, new Variable(cons.toString(), c.type()));
			}
		}
		return vars;
	}
	
	ST getNewMethodTmpl(String name, String modifier, String type) {
		ST m = tmplGroup.getInstanceOf("method");
		m.add("modifier", modifier);
		m.add("type", type);
		m.add("name", name);
		return m;
	}
	
	String varName(Table t) {
		String tableName = t.name();
		String firstLetter = tableName.substring(0, 1);
		String rest = tableName.substring(1);		
		String varName = "_" + firstLetter.toLowerCase() + rest + "_";
		return varName;
	}
	
	public String queryName() {
		return queryPackage+"."+queryVisitorName;
	}
	
	public String generate() {
		if (queryTmpl.getAttribute("packageStmt")!=null) {
			return queryTmpl.render();
		}

		queryTmpl.add("packageStmt", "package "+queryPackage);
		queryTmpl.add("description", "for query: "+query);
		queryTmpl.add("modifier", "public");
		queryTmpl.add("name", queryVisitorName);
		
		importTable();
		
		queryTmpl.add("extends", "extends VisitorImpl");
		queryTmpl.add("interfaces", "QueryRunnable");
		if (table.visitorInterface()!=null)
			queryTmpl.add("interfaces", table.visitorInterface());
		
		/* declare fields */
		if (isTableSliced(table)) queryTmpl.add("fieldDecls", table.className()+"[] "+varName(table));
		else queryTmpl.add("fieldDecls", table.className()+" "+varName(table));
		
		if (paramNum() > 1) {
			Class[] types = getTupleTypes();			
			ST tupTmpl = tmplGroup.getInstanceOf("tuple");
			for (int i=0; i<types.length; i++) {
				tupTmpl.add("types", types[i]);
			}
			String tupleCls = tupTmpl.render();			
			String tupleDecl = tupleCls + " "+tupleVar()+" = new "+tupleCls+"()";
			queryTmpl.add("fieldDecls", tupleDecl);
		}
		
		queryTmpl.add("fieldDecls", "TableSliceMap "+sliceMapVar());
		queryTmpl.add("fieldDecls", "QueryVisitor "+queryVisitorVar());
		queryTmpl.add("fieldDecls", "boolean "+isTerminatedVar()+"=false");
		for (Variable v:queryP.getVariables()) {
			queryTmpl.add("fieldDecls", MyType.javaTypeName(v)+" "+v);
		}
		
		/* declare methods */
		generateConstructor();		
		generateRunMethod();
        generateIdGetters();
		generateVisitMethod();
		generateSetArgs();
		generateTerminateMethods();
		generateSetQueryVisitor();
		
		return queryTmpl.render();
	}	
	Class[] getTupleTypes() {
		Class[] types = new Class[paramNum()];		
		for (int i=0, j=0; i<types.length; i++) {
			if (iterColumn()==i) types[i] = int.class;
			else types[i] = table.types()[j++];
		}
		return types;		
	}
	
	int iterColumn() {
		if (table instanceof IterTable) {
			IterTable itable = (IterTable)table;
			return itable.iterColumn();
		} else return -1;
	}	
	String argVarName(int argIdx) {
		return "$a"+argIdx;
	}

    void generateIdGetters() {
        ST getter = getNewMethodTmpl("getRuleId", "public", "int");
        getter.add("stmts", "return -1");
        queryTmpl.add("methodDecls", getter);

        getter = getNewMethodTmpl("getEpochId", "public", "int");
        getter.add("stmts", "return -1");
        queryTmpl.add("methodDecls", getter);
    }

	void generateRunMethod() {
		ST run=getNewMethodTmpl("run", "public", "void");
		queryTmpl.add("methodDecls", run);
		
		ST code=run;
		String tableVar=varName(table);		
		if (isTableSliced(table)) {
			boolean sliceSelected = isFirstParamResolved();
			Variable v=constVars.get(0);
			if (sliceSelected) {
				tableVar += "["+sliceMapVar()+".getIndex("+table.id()+","+v+")"+"]";
			} else {
				ST for_ = tmplGroup.getInstanceOf("for");
				for_.add("init", "int $i=0");
				for_.add("cond", "$i<"+sliceMapVar()+".sliceNum("+table.id()+")");
				for_.add("inc", "$i++");
				tableVar += "[$i]";
				run.add("stmts", for_);
				code = for_;
			}
		}
		String invokeIterate=".iterate";			
		if (isIndexColResolved()) {
			invokeIterate += getIteratebySuffix();
		} else {
			invokeIterate += "(this)";
		}
		code.add("stmts", tableVar+invokeIterate);
		
		run.add("stmts", queryVisitorVar()+".finish()");		
	}
	boolean isIndexColResolved() {
		for (int i:constVars.keys()) {
			if (table.getColumn(i).isIndexed())
				return true;
		}
		return false;
	}
	
	ArrayList<Column> getResolvedIndexCols() {
		ArrayList<Column> idxCols = new ArrayList<Column>();
		TIntSet resolvedIdx = constVars.keySet();
		for (Column c:table.getColumns()) {
			if (c.isIndexed() && resolvedIdx.contains(c.getAbsPos()))
				idxCols.add(c);
		}
		return idxCols;
	}
	
	TIntArrayList getIndexByCols() {
		ArrayList<Column> resolvedIdxCols = getResolvedIndexCols();		
		TIntArrayList idxbyCols=new TIntArrayList(4);
		if (resolvedIdxCols.size()>=1)
			idxbyCols.add(resolvedIdxCols.get(0).getAbsPos());
		if (resolvedIdxCols.size()<=1) return idxbyCols;
		
		List<ColumnGroup> colGroups = table.getColumnGroups();
		if (colGroups.size()==1) return idxbyCols;
		
		int nest=1;
		for (ColumnGroup g:colGroups.subList(1, colGroups.size())) {
			if (nest > 3) break;
			if (g.first().isArrayIndex()) {
				Column arrayCol = g.first();
				if (resolvedIdxCols.contains(arrayCol))
					idxbyCols.add(arrayCol.getAbsPos());
			} else { break; }
			nest++;
		}
		return idxbyCols;
	}
	
	String getIteratebySuffix() {
		TIntArrayList idxbyCols = getIndexByCols();
		Param[] params = queryP.inputParams();
		String iteratebySuffix = "_by";
		for (int i=0; i<idxbyCols.size(); i++) {
			int idxPos = idxbyCols.get(i);
			iteratebySuffix += "_"+idxPos;
		}
		iteratebySuffix += "(";
		for (int i=0; i<idxbyCols.size(); i++) {
			int idxPos = idxbyCols.get(i);
			iteratebySuffix += constVars.get(idxPos)+", ";
		}
		iteratebySuffix += "this)";
		return iteratebySuffix;
	}
	
	boolean isFirstParamResolved() {
		return constVars.keySet().contains(0);
	}
	
	void generateTerminateMethods() {
		ST terminated=getNewMethodTmpl("setTerminated", "public", "void");
		queryTmpl.add("methodDecls", terminated);
		terminated.add("stmts", isTerminatedVar()+"=true");		
	}
	void generateSetArgs() {
		ST setargs=getNewMethodTmpl("setArgs", "public", "void");
		queryTmpl.add("methodDecls", setargs);
		setargs.add("args", "List $params");
		setargs.add("stmts", "assert $params.size()=="+constVars.size());
		
		int paramIdx=0;
		Object[] params = queryP.inputParams();
		for (int i=0; i<params.length; i++) {
			if (constVars.containsKey(i)) {					
				Variable v = constVars.get(i);
				String type = "("+MyType.javaObjectTypeName(v)+")";
				setargs.add("stmts", v+"="+type+"$params.get("+paramIdx+")");
				paramIdx++;
			}					
		}
	}
	
	void generateSetQueryVisitor() {
		ST setqv=getNewMethodTmpl("setQueryVisitor", "public", "void");
		queryTmpl.add("methodDecls", setqv);
		setqv.add("args", "QueryVisitor $qv");
		setqv.add("stmts", queryVisitorVar()+"=$qv");		
	}

	ST getVisitMethod(int startCol, int endCol, int arity) {
		String name="visit"+CodeGen.getVisitColumns(startCol, endCol, arity);
		
		ST method = getNewMethodTmpl(name, "public", "boolean");
		
		method.add("ret", "return true");
		ST dowhile0 = tmplGroup.getInstanceOf("doWhile0");
		method.add("stmts", dowhile0);		
		
		Predicate p =queryP;
		CodeGen.fillArgTypes(method, p, startCol, endCol);
		queryTmpl.add("methodDecls", method);		
		return method;
	}
	
	boolean allDontCares(int from) {
		Predicate p = queryP;
		Object[] params=p.inputParams();
		for (int i=from; i<params.length; i++) {
			if (!(params[i] instanceof Const)) {
				return false;
			}
		}
		return true;
	}
	void generateVisitMethod() {
		List<ColumnGroup> columnGroups = table.getColumnGroups();		
		int arity = table.numColumns();		
		Collection<Variable> resolved = constVars.valueCollection();
		ST method=null;
		Predicate p = queryP;
				
		for (ColumnGroup g: columnGroups) {
			int startCol = g.startIdx();
			int endCol = g.endIdx();
			if (allDontCares(startCol)) {
				method.add("stmts", "return false");
				break;
			}
			method = getVisitMethod(startCol, endCol, arity);
			CodeGen.fillVisitMethodBody(method, p, 
										startCol, endCol, 
										resolved, getIndexByCols());			
		}
		ST code = CodeGen.findCodeBlockFor(method, p);
		invokeQueryVisitor(code);
	}
		
	int paramNum() {
		int num = queryP.inputParams().length;
		if (table instanceof IterTable)	num++;
		return num;
	}
	
	void invokeQueryVisitor(ST method) {
		if (paramNum()==1) {
			Object param = queryP.inputParams()[0];
			String invoke=queryVisitorVar()+".visit("+param+")";
			method.add("stmts", invoke);
		} else {
			int i=0;
			for (Object param:queryP.inputParams()) {
				if (i==iterColumn()) {
					method.add("stmts", tupleVar()+"._"+i+"="+query.getIterVal());
					i++;
				} 
				method.add("stmts", tupleVar()+"._"+i+"="+param);
				i++;
			}
			String invoke="boolean $cont="+queryVisitorVar()+".visit("+tupleVar()+")";
			method.add("stmts", invoke);
			method.add("stmts", "if(!$cont) return false");
		}
		method.add("stmts", "if("+isTerminatedVar()+") throw new SocialiteFinishEval()");
	}
	
	String tupleVar() { return "tuple"; }
	boolean isParallel() { return !isSequential; }
	boolean isSequential() { return isSequential; }
	
	boolean isTableSliced(Table t) {
		if (isSequential) return false;
		return t.isSliced();
	}
	void generateConstructor() {
		ST m=getNewMethodTmpl(queryVisitorName, "public", "");
		queryTmpl.add("methodDecls", m);
		
		if (isTableSliced(table)) m.add("args", table.className()+"[] _"+varName(table));
		else m.add("args", table.className()+" _"+varName(table));
		m.add("stmts", varName(table)+"=_"+varName(table));
		m.add("args", "QueryVisitor _queryVisitor");
		m.add("stmts", queryVisitorVar()+"=_queryVisitor");
		m.add("args", "TableSliceMap _sliceMap");		
		m.add("stmts", sliceMapVar()+"=_sliceMap");
	}
	
	void importTable() {		
		String tableClass=TableUtil.getTablePath(table.className());
		queryTmpl.add("imports", tableClass);
	}
	
	String queryVisitorVar() { return "queryVisitor"; }
	String sliceMapVar() { return "$sliceMap"; }
	String isTerminatedVar() { return "$isTerminated"; }
}