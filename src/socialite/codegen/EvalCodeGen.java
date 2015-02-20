package socialite.codegen;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import socialite.engine.Config;
import socialite.parser.Column;
import socialite.parser.GeneratedT;
import socialite.parser.Param;
import socialite.parser.Predicate;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.parser.Variable;

//import org.antlr.stringtemplate.StringTemplate;
//import org.antlr.stringtemplate.StringTemplateGroup;

public class EvalCodeGen {
	static int id=0;
	static String evalPackage="socialite.eval";
	
	STGroup tmplGroup;
	ST evalTmpl;
	Epoch epoch;
	List<Rule> rules;
	List<Table> newTables;
	
	Map<String, Table> tableMap;
	List<Rule> simpleInitRule;
	String evalName;
	Config conf;
	
	public EvalCodeGen(Epoch _epoch, List<Table> _tables,
			Map<String, Table> _tableMap, Config _conf) {
		epoch = _epoch;
		rules = epoch.getRules();
		newTables = _tables;
		tableMap = _tableMap;
		conf = _conf;
		
		tmplGroup = CodeGen.getEvalGroup();
		evalTmpl = tmplGroup.getInstanceOf("evalClass");
		evalName="Eval"+(id++);
		
		simpleInitRule = new ArrayList<Rule>();
		for (Rule rule:rules) {
			if (rule.isSimpleArrayInit()) {
				Predicate p=rule.getHead();
				Table t=tableMap.get(p.name());
				assert t.isArrayTable();
				simpleInitRule.add(rule);
			}
		}
	}
	
	boolean isGenerated() {
		return evalTmpl.getAttribute("name")!=null;
	}
	public String generate() {
		if (isGenerated()) return evalTmpl.render();
		
		evalTmpl.add("name", evalName);
		if (conf.isDistributed()) evalTmpl.add("evalBaseClass", "EvalDist");
		else {
			assert conf.isParallel() || conf.isSequential();
			evalTmpl.add("evalBaseClass", "EvalParallel");
		}
					
		for (Table t:newTables) {
			assert !(t instanceof GeneratedT):"newTables should not contain instances of GeneratedT";			
			// table array decl			
			String sliceNum = "sliceMap.sliceNum("+t.id()+")";
			String decl=t.className()+"[] "+varName(t)+"= new "+t.className()+"["+sliceNum+"]";
			evalTmpl.add("tableVars", varName(t));
			evalTmpl.add("tableDecls", decl);
			
			// create table
			ST for_=tmplGroup.getInstanceOf("for");
			for_.add("init", "int $i=0");
			for_.add("cond", "$i<"+sliceNum);
			for_.add("inc", "$i++");
			String tableInstStmt;
			
			if (t.isArrayTable()) {
				String base = "sliceMap.localBeginIndex("+t.id()+")";				
				String size = "sliceMap.localSize("+t.id()+")";
				tableInstStmt = varName(t)+"[$i]="+t.className()+".create("+base+", "+size+")";
			} else {
				tableInstStmt = varName(t)+"[$i]="+t.className()+".create()";
			}
			
			for_.add("stmts", tableInstStmt);			
			evalTmpl.add("tableInsts", for_);
			
			// table registration			
			String registr = registryVar()+".setTableInstArray("+t.id()+", "+varName(t)+")";
			evalTmpl.add("tableRegs", registr);

            String unregistr = registryVar()+".setTableInstArray("+t.id()+", null)";
            evalTmpl.add("tableUnregs", unregistr);
		}
		return evalTmpl.render();
	}
		
	ST genParallelInit(ST code, Rule r) {
		ST parInit=tmplGroup.getInstanceOf("parallelInit");
		code.add("stmts", parInit);
		parInit.add("name", "InitRule"+r.id());
		parInit.add("numThreads", conf.getWorkerThreadNum()-1);
		return parInit;
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
	boolean isDontCare(Object o) {
		if (o instanceof Variable) 
			return ((Variable)o).dontCare;
		return false;
	}
		
	public String evalName() { return evalPackage+"."+evalName; }	
	String registryVar() { return "tableRegistry"; }
	
	String localVarName(Table t) {
		String firstLetter = t.name().substring(0, 1);
		String rest = t.name().substring(1);		
		String varName = "__" + firstLetter.toLowerCase() + rest;
		return varName;
	}
	String varName(Table t) {
		String firstLetter = t.name().substring(0, 1);
		String rest = t.name().substring(1);		
		String varName = "_" + firstLetter.toLowerCase() + rest;
		return varName;
	}	
}