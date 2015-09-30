package socialite.codegen;

import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import socialite.parser.AssignOp;
import socialite.parser.Column;
import socialite.parser.Const;
import socialite.parser.Expr;
import socialite.parser.MyType;
import socialite.parser.Param;
import socialite.parser.Predicate;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.parser.Variable;
import socialite.resource.SRuntime;
import socialite.yarn.ClusterConf;

public class InitCodeGen {
	static String initPackage="socialite.eval";
	
	STGroup tmplGroup;
	ST initTmpl;
	Rule r;
	Map<String, Table> tableMap;
	String initName;
	SRuntime runtime;
	
	public InitCodeGen(Rule _r, Map<String, Table> _tableMap, SRuntime _rt) {
		r = _r;
		tableMap = _tableMap;
		runtime = _rt;

		tmplGroup = CodeGen.getEvalGroup();		
		initTmpl = tmplGroup.getInstanceOf("initClass");
		initName = "Init_"+(r.id());
	}
	public String name() { return initPackage+"."+initName; }
	
	boolean isGenerated() {
		return initTmpl.getAttribute("name")!=null;
	}
	public String generate() {
		if (isGenerated()) return initTmpl.render();
		
		initTmpl.add("name", initName);
		genSimpleAssignment();
		return initTmpl.render();
	}

	ST getNewMethodTmpl(String name, String modifier, String type) {
		ST m = tmplGroup.getInstanceOf("method");
		m.add("modifier", modifier);
		m.add("type", type);
		m.add("name", name);
		return m;
	}
		
	void genSetArgs() {
		ST setargs=getNewMethodTmpl("setArgs", "public", "void");
		initTmpl.add("methodDecls", setargs);
		List<Const> constVars = r.getConsts();
		setargs.add("args", "List $params");
		setargs.add("stmts", "assert $params.size()=="+constVars.size());

		int constIdx=0;
		for (Const c:constVars) {
			String type="("+MyType.javaObjectTypeName(c.type)+")";
			setargs.add("stmts", c+"="+type+"$params.get("+constIdx+")");
			constIdx++;
		}
	}
	void genSimpleAssignment() {
		assert r.isSimpleArrayInit():"rule:"+r;
		
		Predicate headP=r.getHead();
		Table t=tableMap.get(headP.name());
		assert t.isArrayTable();
		assert !t.hasNestedT();
		
		initTmpl.add("tableid", t.id());
		initTmpl.add("tableRegVar", registryVar());
		initTmpl.add("partitionMapVar", partitionMapVar());
		
		for (Const c:r.getConsts()) {
			String type=MyType.javaTypeName(c);
			initTmpl.add("fieldDecls", type+" "+c);
		}
		genSetArgs();

		ST code = initTmpl;
				
		String decl=t.className()+"[] "+localVarName(t)+"="+
					"("+t.className()+"[])"+registryVar()+".getTableInstArray("+t.id()+")";
		code.add("stmts", decl);		
		
		// initialize variables
		for (Variable v:r.getBodyVariables()) {
			if (v.dontCare) continue;
			String type=MyType.javaTypeName(v);
			code.add("stmts", type+" "+v);
		}
		for (Expr e:r.getExprs()) {
			AssignOp op=(AssignOp)e.root;			
			code.add("stmts",op.codegen());
		}
		
		Object first = headP.first();
		String partitionIdx = partitionIdxGetter(t, first);
		Column dontCare = getDontCareColumn(headP, t);
		assert dontCare!=null;
		assert dontCare.isArrayIndex();
		
		String myPartitionNum = partitionMapVar()+".virtualPartitionNum("+t.id()+","+dontCare.getAbsPos()+")/" +
								ClusterConf.get().getNumWorkerThreads();
		String beginSlice = "(id*"+myPartitionNum+")";
		String endSlice = "((id+1)*"+myPartitionNum+"-1)";
		
		code.add("stmts", "int $from="+beginSlice);
		code.add("stmts", "int $to="+endSlice);
		
		ST if_ = tmplGroup.getInstanceOf("if");
		code.add("stmts", if_);
		if_.add("cond", partitionMapVar()+".virtualPartitionNum("+t.id()+")==1");
		if_.add("stmts", "if(id!=0) return;");
		if_.add("stmts", "$from = 0; $to=0;");
			
		String beginIdx=partitionMapVar()+".getRange("+t.id()+","+dontCare.getAbsPos()+",$from)[0]";
		String endIdx=partitionMapVar()+".getRange("+t.id()+","+dontCare.getAbsPos()+",$to)[1]";
		
		ST for_ = tmplGroup.getInstanceOf("for");
		for_.add("init", "int $i="+beginIdx);
		for_.add("cond", "$i<="+endIdx);
		for_.add("inc", "$i++");	
		for_.add("stmts", invokeInsert(localVarName(t), headP, partitionIdx));
		code.add("stmts", for_);
	}
	
	String registryVar() { return "tableReg"; }
	String partitionMapVar() { return "partitionMap"; }
	String localVarName(Table t) {
		String firstLetter = t.name().substring(0, 1);
		String rest = t.name().substring(1);		
		String varName = "__" + firstLetter.toLowerCase() + rest;
		return varName;
	}
	
	Column getDontCareColumn(Predicate p, Table t) {
		int i=0;
		for (Param o:p.inputParams()) {
			if (o instanceof Variable) {
				Variable v=(Variable)o;
				if (v.dontCare) return t.getColumn(i);
			}
			i++;
		}
		return null;
	}
	
	String partitionIdxGetter(Table t, Object first) {
		if (t.isPartitioned()) {
			if (first instanceof Variable && ((Variable)first).dontCare)
				return partitionMapVar()+".getIndex("+t.id()+", $i)";
			else
				return partitionMapVar()+".getIndex("+t.id()+", "+first+")";
		}
		return "0";
	}
	boolean isDontCare(Object o) {
		if (o instanceof Variable) 
			return ((Variable)o).dontCare;
		return false;
	}
	String invokeInsert(String tableVar, Predicate p, String partitionIdx) {
		String invokeInsert = tableVar+"["+partitionIdx+"].insert(";
		boolean first=true;
		for (int i=0; i<p.inputParams().length; i++, first=false) {
			if (!first) invokeInsert += ", ";
			Object o=p.inputParams()[i];
			if (isDontCare(o)) invokeInsert += "$i";
			else invokeInsert += o;
		}
		invokeInsert += ")";		
		return invokeInsert;
	}	
}
