package socialite.parser;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.*;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.parser.antlr.ClearTable;
import socialite.parser.antlr.DropTable;
import socialite.parser.antlr.RuleDecl;
import socialite.parser.antlr.SociaLiteLexer;
import socialite.parser.antlr.SociaLiteParser;
import socialite.parser.antlr.SociaLiteRule;
import socialite.parser.antlr.TableDecl;
import socialite.parser.antlr.TableStmt;
import socialite.parser.antlr.ColumnDecl;
import socialite.util.AnalysisException;
import socialite.util.Assert;
import socialite.util.ParseException;
import socialite.util.SociaLiteException;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;


/**
 * Parser instance keeps track of declared tables
 * to check if predicates confirms to the table declarations.
 *  
 * @author jiwon
 *
 */
public class Parser {
	public static final Log L = LogFactory.getLog(Parser.class);
	
	// monitor to synchronize parsing and analysis process.
	Object monitor = new Object(); 
		
	String program;
	SociaLiteParser antlrParser;
	Map<String, TableDecl> tableDeclMap = new HashMap<String, TableDecl>();
	List<RuleDecl> ruleDecls = new ArrayList<RuleDecl>();
	List<TableStmt> tableStmts = new ArrayList<TableStmt>();
	Map<String, Rule> canonRuleMap = new HashMap<String, Rule>();
	List<Rule> newRules = new ArrayList<Rule>();
	List<Table> newTables = new ArrayList<Table>();
	Map<String, Table> tableMap = new LinkedHashMap<String, Table>();
	Query query;
	TIntObjectHashMap<Rule> ruleMap = new TIntObjectHashMap<Rule>(128); 
	
	public Parser(String program) {
		prepare(program);
	}
	
	public Parser(Map<String, Table> existingTableMap) {
		addExistingTables(existingTableMap);
	}
	
	public Parser() {}

	public void removeTables(List<Table> _tables) {
		for (Table t:_tables) {
			removeTableDecl(t.decl());
		}
	}
	public void removeTableDecl(TableDecl decl) {
		tableDeclMap.remove(decl.name());
		if (decl.hasIterColumn()) {
			for (int i=0; i<decl.maxIter(); i++) {
				Table t= tableMap.get(IterTable.name(decl.name(), i));
				tableMap.remove(t.name());
				newTables.remove(t);
			}
		} else {
			Table t=tableMap.get(decl.name());
			tableMap.remove(decl.name());
			newTables.remove(t);
		}
	}
	public void addExistingTables(Map<String, Table> _tableMap) {
		// only used by resuming after check-pointing.		
		for (Table t:_tableMap.values()) {
			TableDecl decl = t.decl;
			assert decl.name().equals(t.name()): "decl.name():"+decl.name()+" t.name():"+t.name();
			if (tableDeclMap.containsKey(decl.name())) {
				assert tableDeclMap.get(decl.name()).equals(decl);
			} else {
				tableDeclMap.put(decl.name(), decl);
				tableMap.put(t.name(), t);
			}
		}
	}
	
	public Object monitor() {
		return monitor;
	}

	void prepare(String program) {
		this.program = program;		
		SociaLiteLexer lexer = new SociaLiteLexer(new ANTLRStringStream(program));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		antlrParser = new SociaLiteParser(tokens);
		antlrParser.parser = this;
	}
	
	public void parse(String program) {
		synchronized(monitor) {			
			prepare(program);
			parse();
		}
	}

	private String getProgram() { return program; }
	
	public String generateErrorMsg(ParseException e) {
		String msg="\n";
		String prog = getProgram();
		
		String[] lines=prog.split("\n");
		int lineNum = e.getLine();
		int pos = e.getPos();
		if (lineNum >= lines.length) {
			lineNum = lines.length-1;
			pos = lines[lineNum].length();
		}
		String line;
    if (lineNum>=0) { line = lines[lineNum]; }
    else { line = ""; }
		
		msg += line+"\n";
		for (int i=0; i<pos; i++)
			msg += " ";
		msg += "^\n";
		msg += "Syntax error:"+e.getMessage();
		return msg;
	}
	
	void removeCompiledTables(List<Table> _tables) {
		List<Table> toRemove=new ArrayList<Table>();
		for (Table t:_tables) {
			if (t.isCompiled()) {
				toRemove.add(t);
			}
		}
		_tables.removeAll(toRemove);
	}
	
	void exceptionCleanup() {
		List<TableDecl> toRemove = new ArrayList<TableDecl>();
		for (TableDecl decl:tableDeclMap.values()) {
			if (decl.hasIterColumn()) {
				boolean remove=false;
				for (int i=0; i<decl.maxIter(); i++) {
					if (!tableMap.containsKey(IterTable.name(decl.name(), i))) {
						toRemove.add(decl);
						remove = true;
						break;
					}
				}
				if (remove) {
					for (int i=0; i<decl.maxIter(); i++) {
						tableMap.remove(IterTable.name(decl.name(), i));
					}
				}
			} else {
				if (!tableMap.containsKey(decl.name())) {
					toRemove.add(decl);
				}
			}
		}
		for (TableDecl decl:toRemove) {
			tableDeclMap.remove(decl.name());
		}
	}
	
	public void parse() {
		removeCompiledTables(newTables);
		
		SociaLiteParser.prog_return prog=null;
		try {
			prog = antlrParser.prog();
		} catch (ParseException e) {
			exceptionCleanup();
			throw e;
		} catch (Exception e) {
			exceptionCleanup();
			throw new SociaLiteException(e);
		}
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(prog.getTree());
		SociaLiteRule socialite = new SociaLiteRule(nodes);
		socialite.parser = this;
		socialite.tableDeclMap = tableDeclMap;
		SociaLiteRule.prog_return ast=null;		
		try {
			ast = socialite.prog();
		} catch (ParseException e) {
			exceptionCleanup();
			throw e;
		} catch (Exception e) {
			exceptionCleanup();
			throw new SociaLiteException(e);
		}
		
		List decls = ast.result;
		tableStmts = new ArrayList<TableStmt>(4);		
		newRules = new ArrayList<Rule>();
		query = null;
		int queryNum=0;
		for (Object decl:decls) {
			if (decl instanceof TableDecl) {
				TableDecl td = (TableDecl)decl;
				assert tableDeclMap.containsKey(td.name());				
				createTable(td);
			} else if (decl instanceof RuleDecl) {
				RuleDecl rd = (RuleDecl)decl;
				createRule(rd);				
			} else if (decl instanceof Query) {
				if (queryNum++ > 0)
					throw new SociaLiteException("Cannot have multiple query statements.");
				createQuery((Query)decl);				
			} else if (decl instanceof TableStmt) {
				TableStmt stmt = (TableStmt)decl;
				addTableStmt(stmt);
			}
		}
	}	
	void createQuery(Query qdecl) {
		query = qdecl;
		adjustIterColumn(query);
	}
	boolean isIntConst(Object o) {
		if (o instanceof Const)
			return  MyType.javaType(o).equals(Integer.class) ||
						MyType.javaType(o).equals(int.class);		 
		return false;		
	}
	Integer iterColumnVal(Object iterParam) {
		return (Integer)((Const)iterParam).val;
	}
	void adjustIterColumn(Query q) {
		TableDecl tdecl = tableDeclMap.get(q.getP().name());
		if (!tdecl.hasIterColumn()) return;
		
		int iterCol = tdecl.iterColumn();
		Object iterParam = q.getP().inputParams()[iterCol];
		if (!isIntConst(iterParam)) {
			String msg="Integer constant is required for the iterator column ("+iterCol+"'th column) of "+q.getP().name();
			throw new AnalysisException(msg, q);
		}
		Predicate p=q.getP();
		int col = tdecl.iterColumn();
		
		p.setName(IterTable.name(p.name(), iterColumnVal(iterParam)%tdecl.maxIter()));
		q.setIterVal(iterColumnVal(iterParam));
		p.removeParamAt(col);		
	}
	void addTableStmt(TableStmt stmt) {
		String name = stmt.tableName();
		TableDecl decl = tableDeclMap.get(name);
		if (decl == null) { throw new AnalysisException("Table "+name+" not declared:"); }
		if (!decl.hasIterColumn()) {
			tableStmts.add(stmt);
			return;
		}
		
		for (int i=0; i<decl.maxIter(); i++) {
			String tableName = IterTable.name(decl.name(), i);			
			if (stmt instanceof ClearTable) {
				tableStmts.add(new ClearTable(tableName));
			} else if (stmt instanceof DropTable) {
				tableStmts.add(new DropTable(tableName));
			} else {
				throw new AnalysisException("Unexpected table statment:"+stmt);
			}
		}
	}
	void createRule(RuleDecl ruledecl) {
		Rule r = new Rule(ruledecl);
		adjustIterColumn(r);				
		newRules.add(r);
		ruleMap.put(r.id(), r);
	}
	void adjustIterColumn(Rule r) {
		for (Predicate p:r.getAllP()) {
			TableDecl tdecl = tableDeclMap.get(p.name());			
			if (tdecl==null) {throw new AnalysisException("Table "+p.name()+" not declared");}
			if (!tdecl.hasIterColumn()) continue;
			int iterCol = tdecl.iterColumn();
			Object iterParam = p.inputParams()[iterCol];
			if (!isIntConst(iterParam)) {
				String msg="Integer constant is required for the iterator column ("+iterCol+"'th column) of "+p.name();
				throw new AnalysisException(msg, r);
			}
			
			p.setName(IterTable.name(p.name(), iterColumnVal(iterParam)%tdecl.maxIter()));
			p.removeParamAt(tdecl.iterColumn());
		}
	}
	public Rule getCanonRule(Rule r) {
		String sig = r.signature(tableMap);
		Rule canonRule = canonRuleMap.get(sig);
		if (canonRule==null) {
			canonRuleMap.put(sig, r);
			canonRule = r;
		}
		return canonRule;
	}
	void createTable(TableDecl decl) {
		if (decl.hasIterColumn()) {
			for (int i=0; i<decl.maxIter(); i++) {
				IterTable itable = new IterTable(decl, i);
				newTables.add(itable);
				tableMap.put(itable.name(), itable);
			}
		} else {
			Table t = new Table(decl);
			newTables.add(t);
			tableMap.put(t.name(), t);
		}
	}
	public void dropTable(String name) {
		TableDecl decl = tableDeclMap.get(name);
		if (decl==null) return;

		if (decl.hasIterColumn()) {
			for (int i=0; i<decl.maxIter(); i++) {						
				tableMap.remove(IterTable.name(decl.name(), i));
			}
			tableDeclMap.remove(name);
		} else {
			tableMap.remove(name);
			tableDeclMap.remove(name);
		}
	}
		
	public Rule getRuleById(int ruleid) { return ruleMap.get(ruleid); }
	public List<TableStmt> getTableStmts() { return tableStmts; }
	public List<Rule> getRules() { return newRules; }	
	public List<Table> getNewTables() {	return new ArrayList<Table>(newTables); }
	public Map<String, Table> getTableMap() { return tableMap; /*new HashMap<String, Table>(tableMap);*/ }
	public Map<String, TableDecl> getTableDeclMap() { return tableDeclMap; }
	public Query getQuery() { return query; }	
}
