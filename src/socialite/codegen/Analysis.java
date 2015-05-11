package socialite.codegen;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.engine.Config;
import socialite.functions.Choice;
import socialite.functions.FunctionLoader;
import socialite.functions.Max;
import socialite.functions.MeetOp;
import socialite.functions.Min;
import socialite.parser.Param;
import socialite.parser.Literal;
import socialite.parser.AggrFunction;
import socialite.parser.AssignOp;
import socialite.parser.BinOp;
import socialite.parser.CmpOp;
import socialite.parser.Column;
import socialite.parser.Const;
import socialite.parser.DeltaPredicate;
import socialite.parser.DeltaRule;
import socialite.parser.DeltaTable;
import socialite.parser.Expr;
import socialite.parser.Function;
import socialite.parser.IterTable;
import socialite.parser.PrivPredicate;
import socialite.parser.MyType;
import socialite.parser.NoType;
import socialite.parser.Op;
import socialite.parser.OpVisitor;
import socialite.parser.Parser;
import socialite.parser.Predicate;
import socialite.parser.PrivateTable;
import socialite.parser.Query;
import socialite.parser.RemoteBodyTable;
import socialite.parser.RemoteHeadTable;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.parser.Variable;
import socialite.parser.antlr.ClearTable;
import socialite.parser.antlr.ColumnGroup;
import socialite.parser.antlr.DropTable;
import socialite.parser.antlr.RuleDecl;
import socialite.parser.antlr.TableDecl;
import socialite.parser.antlr.TableStmt;
import socialite.resource.RuleMap;
import socialite.resource.SRuntime;
import socialite.resource.SRuntimeMaster;
import socialite.util.AnalysisException;
import socialite.util.Assert;
import socialite.util.InternalException;
import socialite.collection.SArrayList;

public class Analysis {
	public static final Log L = LogFactory.getLog(Analysis.class);

	List<DeltaRule> deltaRules;
	List<Rule> rules;
	List<Table> newTables;
	Set<Table> accessedTables;
	Set<Table> writeAccessedTables;
	List<Table> deltaTables;
	List<Table> remoteTables;
	List<Table> privTables;
	List<TableStmt> tableStmts;
	Map<String, List<Rule>> rulesByHeadName;
	Map<String, Table> tableMap;
	Query query;
	RuleMap ruleMap;
	List<RuleComp> ruleComps;
	List<Epoch> epochs;
	Config conf;
	Parser p;

	protected Analysis(List<Rule> _rules, List<Table> _newTables,
			Map<String, Table> _tableMap, List<TableStmt> _tableStmts,
			Query _query) {
		rules = _rules;
		newTables =	_newTables;
		tableMap = _tableMap;
		rulesByHeadName = new HashMap<String, List<Rule>>();
		tableStmts = _tableStmts;
		query = _query;
		epochs = new ArrayList<Epoch>();
		deltaTables = new ArrayList<Table>();
		remoteTables = new ArrayList<Table>();
		privTables = new ArrayList<Table>();
		accessedTables = new HashSet<Table>();
		writeAccessedTables = new HashSet<Table>();
		ruleMap = new RuleMap();
		
		makeRuleByHeadMaps();
		prepareTableStmts();		
	}

	public Analysis(Parser _p) {
		this(_p, Config.seq());
		p = _p;
	}

	public Analysis(Parser _p, Config _conf) {
		this(_p.getRules(), _p.getNewTables(), _p.getTableMap(),
				_p.getTableStmts(), _p.getQuery());
		conf = _conf;
		p = _p;
	}

	public Rule getCanonicalRule(Rule r) { return p.getCanonRule(r); }
	public Object monitor() { return p.monitor(); }
	
	public Query getQuery() { return query; }
	public List<Epoch> getEpochs() { return epochs; }
	public List<Rule> getRules() { return rules; }
	public Set<Table> getReads() { return accessedTables; }
	public Set<Table> getWrites() { return writeAccessedTables; }
	public List<Table> getNewTables() { return newTables; }
	public List<Table> getDeltaTables() { return deltaTables; }
	public List<Table> getRemoteTables() { return remoteTables; }
	public List<Table> getPrivateTables() {	return privTables; }
	public Map<String, Table> getTableMap() { return new HashMap<String, Table>(tableMap); }
	public List<TableStmt> getTableStmts() { return tableStmts; }
	
	void checkGroupbyAndIndexBy() {
        for (Table t:newTables) {
            if (!t.hasGroupby()) continue;
            if (!t.hasIndexby()) continue;
            int groupby = t.groupbyColNum();
            for (int col:t.indexedCols()) {
                if (col == groupby) {
                    Column c = t.getColumn(col);
                    String msg = "Cannot add indexby to groupby target column (column "+c.name()+" in table "+t.name()+")";
                    p.removeTableDecl(t.decl());
                    throw new AnalysisException(msg, t);
                }
            }
        }
    }
	void checkSortBy() {
        for (Table t:newTables) {
            if (!t.hasSortby()) continue;
            for (ColumnGroup g:t.getColumnGroups()) {
            	if (!g.isSorted()) continue;
            	if (g.hasIndex()) {
            		String msg = "Cannot use both indexby and sortby to a (nested) table.";
                    p.removeTableDecl(t.decl());
                    throw new AnalysisException(msg, t);
            	}
            }
        }
        for (Rule r:rules) {
        	if (!r.getHead().hasFunctionParam()) continue;
        	
        	Predicate h = r.getHead();
        	Table t = tableMap.get(r.getHead().name());
        	if (t.hasSortby()) {
        		String msg = "Cannot apply an aggregate function to a table with sorted column";
        		throw new AnalysisException(msg, r);
        	}
        }
    }
	void checkTableDecls() {
		checkTableOpts();
	}
	
	void checkTableOpts() {
        checkGroupbyAndIndexBy();
        checkSortBy();
    }

	void checkFreeVarsInHead() {
		for (Rule r:rules) {
			Set<Variable> bodyVars=r.getBodyVariables();
			for (Variable v:r.getHeadVariables()) {
				if (v.dontCare && r.isSimpleArrayInit()) continue;
				if (v.dontCare && !r.isSimpleArrayInit()) {
					String msg="Unexpected dont-care(s) in the head";
					throw new AnalysisException(msg, r);
				}
				if (!bodyVars.contains(v)) {
					String msg="Unbound variable in the head: "+v;
					throw new AnalysisException(msg, r);	
				}
			}			
		}
	}
	
	int untypedVarCount=-1;
	void maybeThrowUntypedVarError() {
		if (untypedVarCount==-1) {
			untypedVarCount = untypedVarCount();
			return;
		}
		if (untypedVarCount == untypedVarCount()) {
			for (Rule r:rules) {
				for (Variable v:r.getBodyVariables()) {
					if (v.hasType()) continue;
					String msg="Cannot determine the type of "+v;
					if (v.name.contains(".")) {
						String rootVarName=v.name.substring(0, v.name.indexOf('.'));
						Variable rootVar=r.getBodyVariableMap().get(rootVarName);
						msg += " (type of "+rootVar.name+":"+rootVar.type.getSimpleName()+")";
					}
					throw new AnalysisException(msg, r);
				}
			}
		}
		untypedVarCount = untypedVarCount();
	}
	
	void runTypeCheck() {
		untypedVarCount=-1;
		computeParamTypes();
		FunctionLoader.loadAll(rules);
		while (requireTypeChecking()) {		
			if (FunctionLoader.allLoaded(rules)) {
				computeParamTypes();
				if (requireTypeChecking())
					throwUnresolvedTypeException();				
			}
			computeParamTypes();
			FunctionLoader.loadAll(rules);
			
			maybeThrowUntypedVarError();
		}
		FunctionLoader.loadAll(rules);
		
		assert FunctionLoader.allLoaded(rules);

		checkUnresolvedVars();
		typeCheckRuleHeads();
	}
	
	
	void checkOrderBy() {
		for (Table t:newTables) {
			if (t.hasOrderBy()) {
				if (conf.isDistributed()) {
					String msg="OrderBy is not supported in distributed configuration";
					throw new AnalysisException(msg, t);
				}
			}
		}
	}
	
	void prepare() {
		RuleComp.init();		
		initPredicatePos();
		prepareAggrFunctions();
		findAccessedTables();		
		markSimpleArrayInit();
		setGroupby();
		checkOrderBy();
	}
	void runErrorCheck() {		
		checkTableDecls();
		checkFreeVarsInHead();
		ensureSingleAssign();
		runTypeCheck();	
	}

	public void run() {		
		prepare();
		
		runErrorCheck();

		computeRuleDeps();
		prepareRecOpts();
		optimize();
		makeEpochs();	
		
		createDeltaRules();

		privatize();
		processRemoteRules();

		prepareEpochs();
		
		processDropTableStmt();
	}

    void optimize() {
	}
    
	void processDropTableStmt() {
		for (TableStmt s:tableStmts) {
			if (s instanceof DropTable) {
				p.dropTable(s.tableName());			
			}
		}
	}
	
	void ensureSingleAssign() {
		if (true) return;
		
		for (Rule r : rules) {
			ensureSingleAssign(r);			
		}
	}
	void ensureSingleAssign(Rule r) {		
		Set<Variable> assigned = new HashSet<Variable>();
		for (Object o : r.getBody()) {
			if (o instanceof Predicate) {
				Predicate p = (Predicate) o;
				assigned.addAll(p.getVariables());
			} else if (o instanceof Expr) {
				Expr e = (Expr) o;
				if (!(e.root instanceof AssignOp)) continue;
				
				AssignOp op = (AssignOp) e.root;
				Set<Variable> vars = op.getLhsVars();
				for (Variable v:vars) {
					if (assigned.contains(v)) {
						String msg="Variable "+v.name+" is already assigned a value, so cannot be reassigned in "+e;
						throw new AnalysisException(msg, r);								
					}
				}
				assigned.addAll(vars);				
			} 
		}
	}
	
	void findAccessedTables() {		
		for (Rule r:rules) {
			for (Predicate p:r.getAllP()) {
				Table t = tableMap.get(p.name());
				accessedTables.add(t);
			}
			Table t=tableMap.get(r.getHead().name());
			writeAccessedTables.add(t);
		}
	}
	void prepareRecOpts() {
		prepareLeftRecAndDijkstraOpt();
		prepareDeltaStepOpt();
		mergeLinearRecursionBaseIntoScc();
	}	

	void prepareLeftRecAndDijkstraOpt() {
		for (Rule r : rules) {
			if (r.isSimpleUpdate()) continue;
			if (isTrivialRecursion(r)) continue;
			markIfLeftRec(r);
			markIfDijkstraOpt(r);
		}
	}	
	void prepareDeltaStepOpt() {
		if (!conf.isParallel()) return;
		
		for (Rule r:rules) {
			if (r.isLeftRec()) {
				if (!r.getHead().hasFunctionParam()) 
					continue;				
				markIfDeltaStepOpt(r);								
			}				
		}
	}	
	Set<Variable> originVars(Rule r, Variable targetVar) {
		Set<Variable> vars=new LinkedHashSet<Variable>();
		for (Expr e:r.getExprs()) {
			if (e.root instanceof AssignOp) {
				AssignOp op=(AssignOp)e.root;
				if (op.getLhsVars().contains(targetVar)) {
					vars.addAll(op.getRhsVars());
				}
			}
		}
		return vars;
	}
	void markIfDeltaStepOpt(Rule r) {
		assert r.isLeftRec();
		AggrFunction f=r.getHead().getAggrF();
		assert f!=null;
		
		if (!(f.klass().equals(Min.class) || f.klass().equals(Max.class))) return;		
		if (f.getArgs().size()>=2) return;
		
		r.setDeltaStepOpt();		
		
		Set<Variable> args=new LinkedHashSet<Variable>(f.getInputVariables());
		Set<Variable> varsInDeltaP = r.firstP().getVariables();
		
		if (varsInDeltaP.containsAll(args)) return;
		
		if (!MeetOp.class.isAssignableFrom(f.klass())) return;
		
		Set<Variable> varsInRestP = new LinkedHashSet<Variable>();
		for (int i=1; i<r.getBodyP().size(); i++) {
			Predicate p = r.getBodyP().get(i);
			varsInRestP.addAll(p.getVariables());
		}		
		varsInRestP.removeAll(varsInDeltaP);
		
		for (Variable v:args) {
			Set<Variable> originVars=originVars(r, v);
			originVars.removeAll(varsInDeltaP);
			if (!originVars.isEmpty()) {
				Variable theVar=originVars.iterator().next();
				r.setDeltaStepOpt(theVar);
				return;
			}
		}
	}	

	public void showAllRules() {
		for (Rule r : rules)
			System.out.println(r);
		for (Rule r : deltaRules)
			System.out.println(r);
	}

	void setGroupby() {
		for (Rule r : rules)
			setGroupby(r);
		for (Table t:newTables) {
			if (!t.hasGroupby()) {
				if (t.getColumn(t.numColumns()-1).isIndexed()) {
					continue;
				}
				int groupby = t.getColumns().length-1;
				if (t.nestingBegins(groupby)) continue;
				try { t.setGroupByColNum(groupby); }
		        catch (InternalException e) { 
		        	Assert.impossible(); 
		        }
			}
		}
	}

	void setGroupby(Rule r) {
		Predicate head = r.getHead();
		if (!head.hasFunctionParam()) return;

		int idx = head.functionIdx();
		Table t = tableMap.get(head.name());

        if (t instanceof IterTable) {
            TableDecl decl = t.decl();
            for (int i=0; i<decl.maxIter(); i++) {
                t = tableMap.get(IterTable.name(decl.name(), i));
                setGroupby(r, t, idx);
            }
        } else {
            setGroupby(r, t, idx);
        }
	}
    void setGroupby(Rule r, Table t, int col) {
        try {
            t.setGroupByColNum(col);
        } catch (InternalException e) {
            throw new AnalysisException(e.getMessage(), r);
        }
    }

	void markIfLeftRec(Rule r) {
		if (isLeftRec(r)) {
			r.setLeftRec();
		}
	}
	boolean isLeftRec(Rule r) {
		List<Rule> rulesUsingThis = r.getRulesUsingThis();
		if (!rulesUsingThis.contains(r)) return false;

		Predicate h = r.getHead();
		int count = 0;
		for (Predicate p : r.getBodyP()) {
			if (p.isNegated()) continue;
			if (h.name().equals(p.name())) {
				count++;
			}
		}
		Predicate f = r.firstP();
		if (count == 1 && canMatch(h, f)) 
			return true;		
		return false;
	}
	void markIfDijkstraOpt(Rule r) {
		if (!isLeftRec(r)) return;
		
		Predicate h = r.getHead();
		if (!h.hasFunctionParam()) return;
		
		// We apply dijkstra opt only if the head table is array table
		Table hT = tableMap.get(h.name());
		if (!hT.isArrayTable()) return;
		
		if (hT.hasNestedT()) {
			// if head table has nesting, the function should not be applied to nested columns
			// for dijkstra optimization (see Heap.stg for why)
			if (hT.nestPos()[0] < h.functionIdx()) {
				return;
			}
		}
		r.setDijkstraOpt();
	}

	public static boolean canMatch(Predicate p1, Predicate p2,
			Map<String, Table> map) {
		if (!p1.name().equals(p2.name())) return false;

		Table t = map.get(p1.name());	
		Param[] params1 = p1.outputParams();
		Param[] params2 = p2.outputParams();
		int compareUpto=t.numColumns();
		if (p1.hasFunctionParam())
			compareUpto = p1.functionIdx();		
		if (p2.hasFunctionParam())
			compareUpto = p2.functionIdx();
		
		for (int i = 0; i < compareUpto; i++) {
			if (params1[i] instanceof Const &&  params2[i] instanceof Const) {
				Const c1=(Const)params1[i];
				Const c2=(Const)params2[i];
				if (!c1.val.equals(c2.val))
					return false;
			}
		}
		return true;
	}

	boolean canMatch(Predicate p1, Predicate p2) {
		return canMatch(p1, p2, tableMap);
	}

	boolean canMatchAny(Predicate p1, List<Predicate> plist) {
		for (Predicate p2 : plist) {
			if (canMatch(p1, p2))
				return true;
		}
		return false;
	}
	boolean canMatchAnyButNegated(Predicate p1, List<Predicate> plist) {
		for (Predicate p2 : plist) {
			if (p2.isNegated()) continue;
			if (canMatch(p1, p2))
				return true;
		}
		return false;
	}

	int getDependingRuleDepth(Rule r) {
		List<Rule> rulesUsingThis = r.getRulesUsingThis();
		if (rulesUsingThis.size() == 0)
			return 0;
		int maxSubDepth = 0;
		for (Rule r2 : rulesUsingThis) {
			int tmp = getDependingRuleDepth(r2);
			if (tmp > maxSubDepth)
				maxSubDepth = tmp;
		}
		return maxSubDepth + 1;
	}

	/**
	 * returns true if the updating shard at the head table is same as the block
	 * at the first table.
	 */
	public static boolean updateParallelShard(Rule r, Map<String, Table> tableMap) {
		if (isSequentialRule(r, tableMap)) return false;
		
		return updateSameShard(r.getHead(), r.firstP(), tableMap);
	}

	static boolean updateSameShard(Predicate h, Predicate f, Map<String, Table> tableMap) {
		Table hT = tableMap.get(h.name());
		Table fT = tableMap.get(f.name());

		Object hParams[] = h.inputParams();
		Object fParams[] = f.inputParams();

		int hidx = firstShardedColumnWithVar(h, hT);
		if (hidx == -1) return false;

		Variable v1 = (Variable) hParams[hidx];
		Column c1 = hT.getColumn(hidx);

		int fidx = firstShardedColumnWithVar(f, fT);
		if (fidx == -1) return false;

		Variable v2 = (Variable) fParams[fidx];
		Column c2 = fT.getColumn(fidx);
		
		if (isSameArrayColumn(c1, c2))
			return v1.equals(v2);
		if (isHashShardedColumn(hT, c1, fT, c2))
			return v1.equals(v2);
		return false;
	}
	static boolean isHashShardedColumn(Table t1, Column c1, Table t2, Column c2) {
		if (t1.isModTable() && t2.isModTable()) {
			if (c1.getAbsPos()==0 && c2.getAbsPos()==0) {
				if (!c1.hasRange() && !c2.hasRange() 
						&& !c1.isSorted() && !c2.isSorted())
					return true;
			}
		}
		return false;
	}
	static boolean isSameArrayColumn(Column c1, Column c2) {
		if (c1.hasRange() && c2.hasRange()) {
			int[] r1 = c1.getRange();
			int[] r2 = c1.getRange();
			if (r1[0]==r2[0] && r1[1]==r2[1])
				return true;
		}
		return false;
	}

	static int firstColumnWithVar(Predicate p, Table t) {
		for (ColumnGroup group:t.getColumnGroups()) {
			Column c=group.first();
			if (c.isPrimaryShard()) continue;
			int paramIdx=c.getAbsPos();
			if (p.params.get(paramIdx) instanceof Variable)
				return c.getAbsPos();				
		}
		return -1;
	}
	
	public static int firstShardedColumnWithVar(Predicate p, Table t) {
		t = getOrigT(t);
		int idx=firstColumnWithVar(p, t);
		if (idx<0) return idx;
		Column c=t.getColumn(idx);
		if (c.hasRange()) return idx;
		if (idx==0 && t.isModTable()) return idx;		
		return -1;		
	}
	

	public static boolean isParallelRule(Rule r, Map<String, Table> tableMap) {
		if (isSequentialRule(r, tableMap)) return false;
		return true;
	}

	static Table getOrigT(Table t) {
		if (t instanceof RemoteHeadTable) {
			return ((RemoteHeadTable)t).origT();
		} else if (t instanceof DeltaTable) {
			return ((DeltaTable)t).origT();
		} else if (t instanceof PrivateTable) {
			return ((PrivateTable)t).origT();
		} else { return t; }
	}
	
	public static boolean isSequentialRule(Rule r, Map<String, Table> tableMap) {
		Object first = r.getBody().get(0);
		if (!(first instanceof Predicate))
			return true;
		
		Predicate firstP = (Predicate)first;
		Table firstT = tableMap.get(firstP.name());
		if (firstT.hasOrderBy()) return true;
		if (firstT instanceof PrivateTable) return false;
		
		int column=firstShardedColumnWithVar(firstP, firstT);
		if (column<0) return true; // no sharded column with variables
		
		return false;
	}	
		
	// XXX: re-implement privatization as rule rewriting, so that VisitorCodeGen does not need to know the details.
	// e.g.  Triangle(0, $inc(1)) :- Edge(a,b), Edge(b,c), Edge(c,a).
	//       => _Thread_Local_Triangle(worker, $inc(1)) :- Edge(a,b), Edge(b,c), Edge(c,a), worker=$workerId().
	void privatize() {
		if (true) return;		
	}

	@SuppressWarnings("rawtypes")
	List copyParams(List params) {
		List<Object> tmp = new ArrayList<Object>();
		for (Object o : params) {
			if (o instanceof AggrFunction) {
				AggrFunction f = (AggrFunction) o;
				List<Param> args = new ArrayList<Param>();
				for (Param a: f.getArgs())
					args.add(a);
				AggrFunction aggr = new AggrFunction(f, args);
				tmp.add(aggr);
			} else
				tmp.add(o);
		}
		return tmp;
	}

	public static boolean hasRemoteRuleBody(Rule r, Map<String, Table> tableMap) {
		List<Predicate> bodyP = r.getBodyP();
		if (bodyP.size() <= 1)
			return false;

		List<Integer> sendPos = tableSendPos(r, tableMap);
		if (sendPos.isEmpty())
			return false;
		return true;
	}

    static boolean hasSamePartition(Table t1, Table t2) {
        if (t1.isModTable() && t2.isModTable()) {
            return t1.getColumn(0).type().equals(t2.getColumn(0).type());
        }
        if (t1.isArrayTable() && t2.isArrayTable()) {
            return t1.arrayBeginIndex() == t2.arrayBeginIndex() &&
                    t1.arrayEndIndex() == t2.arrayEndIndex();
        }
        return false;
    }
    static boolean requireTransfer(Predicate prev, Predicate p, Map<String, Table> tableMap) {
        if (!prev.first().equals(p.first())) {
            return true;
        }
        Table t1 = tableMap.get(prev.name());
        Table t2 = tableMap.get(p.name());
        if (t1 instanceof RemoteHeadTable || t2 instanceof RemoteHeadTable) {
            return false;
        }
        if (t1 instanceof RemoteBodyTable || t2 instanceof RemoteBodyTable) {
            return false;
        }
        return !hasSamePartition(t1, t2);
    }
	public static List<Integer> tableSendPos(Rule r, Map<String, Table> tableMap) {
		List<Predicate> bodyP = r.getBodyP();
		
		List<Integer> sendPos = new ArrayList<Integer>();
		Predicate prevp = null;
		for (Predicate p : bodyP) {
			if (prevp == null || prevp instanceof PrivPredicate) {
				prevp = p;
				continue;
			}
			if (requireTransfer(prevp, p, tableMap)) {
				if (collectLiveVarsAt(r, p.getPos()).isEmpty()) {
					//Assert.impossible();
					continue;			
				}
				sendPos.add(p.getPos());
			}
			prevp = p;
		}
		return sendPos;	
	}
	
	public static boolean hasRemoteRuleHead(Rule r, Map<String, Table> tableMap) {
		Predicate h = r.getHead();
		if (r.isSimpleArrayInit()) return false;
		if (r.getBodyP().size()==0) return true;
		
		List<Predicate> body = r.getBodyP();
		Predicate p=body.get(body.size()-1);
        return requireTransfer(p, h, tableMap);
	}

	void processRemoteRules() {
		if (!conf.isDistributed()) return;
		
		List<Rule> toAdd = new ArrayList<Rule>();
		for (RuleComp rc : ruleComps) {
			for (Rule r : rc.getRules()) {
				if (hasRemoteRuleHead(r, tableMap))
					processRemoteRuleHead(r, toAdd);
			}
			rc.addAll(toAdd);
			toAdd.clear();
			for (Rule r : rc.getRules()) {
				if (hasRemoteRuleBody(r, tableMap))
					processRemoteRuleBody(r, toAdd);
			}
			rc.addAll(toAdd);

			while (!toAdd.isEmpty()) {
				List<Rule> toAdd2 = new ArrayList<Rule>();
				for (Rule r : toAdd) {
					if (hasRemoteRuleHead(r, tableMap))
						processRemoteRuleHead(r, toAdd2);
					if (hasRemoteRuleBody(r, tableMap))
						processRemoteRuleBody(r, toAdd2);
				}
				rc.addAll(toAdd2);
				toAdd = toAdd2;
			}
		}
	}

	public static List<Variable> collectLiveVarsAt(Rule r, int pos) {
		List<Variable> liveVars = new ArrayList<Variable>();
		liveVars.addAll(getResolvedVars(r)[pos]);

		List<Variable> usedVars = new ArrayList<Variable>();
		for (int i = pos; i < r.getBody().size(); i++) {
			Object o = r.getBody().get(i);
			if (o instanceof Predicate) {
				Predicate p = (Predicate) o;
				usedVars.addAll(p.getVariables());
			} else {
				assert o instanceof Expr;
				Expr e = (Expr) o;
				usedVars.addAll(e.root.getVars());
			}
		}
		usedVars.addAll(r.getHeadVariables());		
		Iterator<Variable> i=liveVars.iterator();
		while (i.hasNext()) { 
			if (!usedVars.contains(i.next())) 
				i.remove(); 
		}
		return liveVars;
	}
	
	boolean beginNestingAfter(Variable v, Rule r) {
		for (Predicate p:r.getBodyP()) {
			int i=0;				
			for (Object o:p.inputParams()) {
				if (!v.equals(o)) continue;
				
				Table t=tableMap.get(p.name());
				if (t.hasNestedT()) {
					ColumnGroup group=t.getColumnGroups().get(0);
					if (i<group.size()) {
						return true;
					}
				}					
				i++;
			}
		}
		return false;
	}
	
	List<List<Variable>> findVarGroupsForNesting(Rule r, List<Variable> _vars) {
		List<List<Variable>> groups = new ArrayList<List<Variable>>(3);
		List<Variable> vars = new ArrayList<Variable>();
		
		for (Variable v:_vars) {
			vars.add(v);			
			if (beginNestingAfter(v, r) && groups.size()+1 < 3) {
				groups.add(vars);
				vars = new ArrayList<Variable>();
			}		
		}
		if (vars.size()>0) {			
			groups.add(vars);
		}
		assert groups.size()<=3;
		return groups;
	}
	
	@SuppressWarnings("rawtypes")
	List<Variable> topologicalSort(List<Variable> relatedVars, Map<Object, Set> oneToMany) {
		List<Variable> sorted = new ArrayList<Variable>();
		Set<Variable> initSet = new HashSet<Variable>();
		Set<Variable> visited = new HashSet<Variable>();

		initSet.addAll(relatedVars);
		for (Variable v:relatedVars) {
			Set deps = oneToMany.get(v);
			if (deps!=null) initSet.removeAll(deps);
		}
		if (initSet.isEmpty()) {
			initSet.add(relatedVars.get(0));
		}
		
		for (Variable v:initSet)
			dfsVisit(v, oneToMany, visited, sorted);
		return sorted;
	}
	@SuppressWarnings("rawtypes")
	void dfsVisit(Variable v, Map<Object, Set> oneToMany, Set<Variable> visited, List<Variable> sorted) {
		if (visited.contains(v)) return;		
		visited.add(v);
		Set s=oneToMany.get(v);
		if (s!=null) {
			for (Object o:s) {
				if (o instanceof Variable) {
					Variable child=(Variable)o;
					dfsVisit(child, oneToMany, visited, sorted);
				}
			}
		}
		sorted.add(v);
	}
	
	@SuppressWarnings("rawtypes")
	Map<Rule, Map<Object, Set>> oneToManyMap = new HashMap<Rule, Map<Object, Set>>();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	Map<Object, Set> getOneToManyRelation(Rule r, int transferPos) {
		if (oneToManyMap.containsKey(r))
			return oneToManyMap.get(r);
		Map<Object, Set> oneToMany = new HashMap<Object, Set>();
		oneToManyMap.put(r, oneToMany);
		for (Predicate p:r.getBodyP()) {
			if (p.getPos() >= transferPos) break;
			Table t = tableMap.get(p.name());
			
			int prevPos=0;
			for (int pos:t.nestPos()) {
				for (int i=prevPos; i<pos; i++) {
					if (!(p.params.get(i) instanceof Variable))
						continue;
					for (int j=pos;j<p.params.size(); j++) {
						Set s = oneToMany.get(p.params.get(i));
						if (s==null) {
							s = new HashSet<Object>();
							oneToMany.put(p.params.get(i), s);
						}						
						s.add(p.params.get(j));
					}
				}
				prevPos = pos;
			}
		}	
		return oneToMany;
	}
	List<List<Variable>> inferNesting(Rule r, List<Variable> vars, int pos) {
		List<List<Variable>> nestedColumns = new ArrayList<List<Variable>>();
		@SuppressWarnings("rawtypes")
		Map<Object, Set> oneToMany = getOneToManyRelation(r, pos);
		
		List<Variable> sortedVars = topologicalSort(vars, oneToMany);
		sortedVars.retainAll(vars);
		Collections.reverse(sortedVars);
		List<Variable> currentCols = new ArrayList<Variable>();
		nestedColumns.add(currentCols);
	
outer:	for (Variable v:sortedVars) {
			for (Variable v2:currentCols) {
				Set many = oneToMany.get(v2);
				if (many!=null && many.contains(v)) { 
					currentCols = new ArrayList<Variable>();
					nestedColumns.add(currentCols);
					currentCols.add(v);
					continue outer;
				}
			}
			currentCols.add(v);	
		}
		return nestedColumns;
	}
	
	RemoteBodyTable getRemoteBodyTable(Rule _r, int pos, List<Variable> vars) {
		Rule r = p.getCanonRule(_r);
		String name = RemoteBodyTable.name(r, pos);

		if (tableMap.containsKey(name)) {
			RemoteBodyTable rt = (RemoteBodyTable)tableMap.get(name);
			vars.clear();
			vars.addAll(rt.getParamVars());
			return rt;
		} else {			
			List<List<Variable>> nestedColumns = inferNesting(r, vars, pos);					
			vars.clear();
			List<List<Class>> nestedColumnTypes = new ArrayList<List<Class>>();
			for (int i=0; i<nestedColumns.size(); i++) {
				nestedColumnTypes.add(MyType.javaTypes(nestedColumns.get(i)));
				vars.addAll(nestedColumns.get(i));			
			}			
			RemoteBodyTable rt = new RemoteBodyTable(r, pos, nestedColumnTypes);
			rt.setParamVars(vars);
			remoteTables.add(rt);
			tableMap.put(rt.name(), rt);
			newTables.add(rt);
			return rt;
		}
	}

	@SuppressWarnings("unchecked")
	void processRemoteRuleBody(Rule r, List<Rule> toAdd) {
		List<Integer> sendPos = tableSendPos(r, tableMap);//tableSendPos(r.getBodyP());
		assert !sendPos.isEmpty();
		for (int pos : sendPos) {
			List<Variable> vars = collectLiveVarsAt(r, pos);
			assert !vars.isEmpty();
			RemoteBodyTable rt =  getRemoteBodyTable(r, pos, vars);

			// add new rule
            SArrayList<Param> x = new SArrayList<Param>(); x.addAll(vars);
			Predicate newP = new PrivPredicate(rt.name(), x);
			newP.setPos(0);
			@SuppressWarnings("rawtypes")
			List body = new ArrayList();
			body.add(newP);
			for (int i = pos; i < r.getBody().size(); i++) {
				Object o = r.getBody().get(i);
				if (o instanceof Predicate) {
					Predicate p = (Predicate) o;
					Predicate clonedP = new Predicate(p.name(), p.params);
					clonedP.setPos(i - pos + 1);
					body.add(clonedP);
				} else body.add(o);
			}
			Rule newR = new Rule(new RuleDecl(r.getHead(), body));
			newR.copyRuleProperties(r);
			
			if (hasDelta(r)) addDeltaRuleMapping(newR.id(), r.id());
			
			ruleMap.setRemoteBodyDep(r.id(), pos, newR.id());
			toAdd.add(newR);
		}
	}	
	
	boolean hasDelta(Rule r) { return r.inScc() || r.hasPipelinedRules(); }

	void addDeltaRuleMapping(int newR, int rid) {
		TIntArrayList deltaRules = ruleMap.getDeltaRules1(rid);
		if (deltaRules!=null) {
			for (int i=0; i<deltaRules.size(); i++) {
				int dr = deltaRules.get(i);
				ruleMap.addDeltaRuleMapping(newR, dr, true);
			}
		}
		TIntArrayList deltaRules2 = ruleMap.getDeltaRulesRest(rid);
		if (deltaRules2!=null) {
			for (int i=0; i<deltaRules2.size(); i++) {
				int dr = deltaRules2.get(i);
				ruleMap.addDeltaRuleMapping(newR, dr, false);
			}
		}
		if (deltaRules==null && deltaRules2==null) {
			L.warn("Expected delta-rules, but none exists for rule["+rid+"]");
		}
	}
	RemoteHeadTable getRemoteHeadTable(Table t, Rule _r) {
		Rule r = p.getCanonRule(_r);
		String name = RemoteHeadTable.name(t, r);
		if (tableMap.containsKey(name)) {
			return (RemoteHeadTable)tableMap.get(name);
		} else {
			RemoteHeadTable rt = new RemoteHeadTable(t, r);
			remoteTables.add(rt);
			tableMap.put(rt.name(), rt);
			newTables.add(rt);
			return rt;
		}
	}
	@SuppressWarnings("unchecked")
	void processRemoteRuleHead(Rule r, List<Rule> toAdd) {
		Predicate h = r.getHead();
		Table ht = tableMap.get(h.name());
		if (ht instanceof PrivateTable) {
			ht = ((PrivateTable) ht).origT();
			assert ht.id() >= 0:"Private Table:"+ht.name()+", orig table:"+ht.name()+", id:"+ht.id();
		}
		RemoteHeadTable rt = getRemoteHeadTable(ht, r);

		// new rule (for the receiving node) [ OrigPredicate() :- RemoteTable(). ]
		SArrayList<Param> vars = new SArrayList<Param>();
		Param[] params = h.inputParams();
		for (int i=0; i<params.length; i++) {
			int varIdx=i;
			vars.add(new Variable("$"+varIdx, MyType.javaType(params[i])));
		}
		Predicate bodyP = new PrivPredicate(rt.name(), vars, true/*rename param vars*/);
		// getting renamed vars
		vars = bodyP.params;
		bodyP.setPos(0);
		List body = new ArrayList();
		body.add(bodyP);
		Predicate newH = new Predicate(ht.name(), makeNewParams(h.params, vars));
		newH.setAsHeadP();
		prepareAggrFunction(newH);
		Rule newR = new Rule(new RuleDecl(newH, body));
		newR.copyRuleProperties(r);
		if (hasDelta(r)) addDeltaRuleMapping(newR.id(), r.id());
		
		ruleMap.setRemoteHeadDep(r.id(), newR.id());
		toAdd.add(newR);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	List makeNewParams(List<Param> oldParams, List<Param> input) {
		List<Param> newParam = new ArrayList<Param>();
		int i=0;
		for (Param o:oldParams) {
			if (o instanceof AggrFunction) {
				AggrFunction f = (AggrFunction) o;
				List args = new ArrayList();
				for (Object a : f.getArgs())
					args.add(input.get(i++));
				AggrFunction aggr = new AggrFunction(f, args);
				newParam.add(aggr);
			} else {
				newParam.add(input.get(i++));
			}
		}
		return newParam;	
  }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	List replaceInputWithVars(List params, List<Variable> vars) {
		List replaced = new ArrayList();
		int i=0;
		for (Object o : params) {
			if (o instanceof AggrFunction) {
				AggrFunction f = (AggrFunction) o;
				List args = new ArrayList();
				for (Object a : f.getArgs())
					args.add(vars.get(i++));
				AggrFunction aggr = new AggrFunction(f, args);
				replaced.add(aggr);
			} else
				replaced.add(vars.get(i++));
		}
		return replaced;	
	}

	DeltaRule createStartingDeltaRule(Table t, DeltaRule dependingRule) {
		SArrayList<Param> vars = new SArrayList<Param>();
		for (int i=0; i<t.numColumns(); i++) {
			Variable v = new Variable("v"+i, t.types()[i]);
			vars.add(v);
		}
		Predicate h = new Predicate(t.name(), vars);
		h.setAsHeadP();
		DeltaPredicate deltaH = new DeltaPredicate(h);		
		Predicate b = new Predicate(t.name(), vars);
		b.setPos(0);		
		List<Literal> params = new ArrayList<Literal>(); params.add(b);
		
		RuleDecl decl = new RuleDecl(deltaH, params);
		DeltaRule deltaRule = new DeltaRule(decl, deltaH);
		deltaRule.setInScc();
		addToRulesByHeadName(deltaRule);
		boolean isFirstP = dependingRule.firstP().name().equals(DeltaTable.name(t));
		ruleMap.addDeltaRuleMapping(deltaRule.id(), dependingRule.id(), isFirstP);
		
		deltaRules.add(deltaRule);
		assert tableMap.containsKey(deltaH.name());
		assert deltaTables.contains(tableMap.get(deltaH.name()));
		return deltaRule;
	}
	
	void registerDeltaRule(DeltaRule deltaRule) {
		addToRulesByHeadName(deltaRule);
		
		Rule origRule = deltaRule.origRule();
		DeltaPredicate deltaP = deltaRule.getTheP();
		boolean isFirstP = origRule.firstP().getPos()==deltaP.getPos();
		
		List<Rule> relevantRules = rulesByHeadName.get(deltaP.getOrigP().name());
		for (Rule r : relevantRules) {
			ruleMap.addDeltaRuleMapping(r.id(), deltaRule.id(), isFirstP);
		}
		
		deltaRules.add(deltaRule);
		Table t = tableMap.get(deltaP.getOrigP().name());
		DeltaTable dt = new DeltaTable(t, deltaRule);		
		assert deltaP.name().equals(dt.name());
		tableMap.put(deltaP.name(), dt);
		deltaTables.add(dt);
        newTables.add(dt);
	}
		
	void addDependencyBetweenDeltaRules(List<DeltaRule> deltaRules) {
		for (DeltaRule dr1:deltaRules) {
			for (DeltaRule dr2:deltaRules) {
				String headT=dr1.getHead().name();
				String deltaName = dr2.getTheP().origName();
				if (headT.equals(deltaName)) {
					boolean isDeltaFirst=dr2.getTheP().getPos()==0;
					ruleMap.addDeltaRuleMapping(dr1.id(), dr2.id(), isDeltaFirst);
				}
			}
		}
	}

	List<Predicate> getMatchingPs(Rule r, String name) {
		List<Predicate> list = new ArrayList<Predicate>();
		for (Predicate p : r.getBodyP()) {
			if (name.equals(p.name()))
				list.add(p);
		}
		return list;
	}

	void createDeltaRules() {
		assert (deltaRules == null);
		deltaRules = new ArrayList<DeltaRule>();
		assert ruleMap != null;

		List<DeltaRule> toAdd = new ArrayList<DeltaRule>();
		Set<String> deltaRuleSigs = new LinkedHashSet<String>();
		for (RuleComp rc : ruleComps) {
			deltaRuleSigs.clear();
			if (rc.scc() || rc.hasPipeliningFrom()) {			
				for (Rule r: rc.getRules()) {
					List<Rule> depRules = new ArrayList<Rule>(r.getDependingRules());
					for (Rule r2 : depRules) {						
						String name = r2.getHead().name();
						for (Predicate p : getMatchingPs(r, name)) {
							if (p.isNegated()) continue;
							
							DeltaPredicate deltaP = new DeltaPredicate(p);
							DeltaRule deltaRule = new DeltaRule(r, deltaP);
							boolean existingRule = tableMap.containsKey(DeltaTable.name(p)) &&
										deltaRuleSigs.contains(deltaRule.signature(tableMap));
							if (!existingRule) {								
								registerDeltaRule(deltaRule);
								toAdd.add(deltaRule);
								deltaRuleSigs.add(deltaRule.signature(tableMap));
							}
						}
					}
				}
				addDependencyBetweenDeltaRules(toAdd);
				rc.addAll(toAdd);
				toAdd.clear();
			}
		}
				
		for (DeltaRule dr : deltaRules) {
			dr.updateRuleDeps();			
		}
		
		List<DeltaRule> startingDeltaRules = createStartingDeltaRules();		
		rules.addAll(deltaRules);
		rules.addAll(startingDeltaRules);
	}
	void mergeLinearRecursionBaseIntoScc() {
		List<RuleComp> toRemove = new ArrayList<RuleComp>();
		for (RuleComp rc : ruleComps) {
			if (rc.scc()) continue;
			if (rc.size() > 1) continue;
			assert rc.size()==1;
			
			Rule base = rc.get(0);
			List<RuleComp> depComps = rc.getRuleCompsUsingThis();
			for (RuleComp dep:depComps) {
				if (!dep.scc()) continue;
				if (dep.size()>1) continue;
					
				Rule r = dep.getRules().get(0);
				if (r.isLeftRec() && 
						base.getHead().name().equals(r.firstP().name())) {
					rc.removeAllStartingRule();	
					
					dep.removeAllStartingRule();
					dep.addStartingRule(base);
					dep.setPipeliningFrom(base);					
					dep.add(base);
					
					base.setInScc();					
					
					toRemove.add(rc);
				}				
			}
		}
		ruleComps.removeAll(toRemove);
	}
	List<DeltaRule> createStartingDeltaRules() {
		List<DeltaRule> startingDeltaRules = new ArrayList<DeltaRule>();
		for (RuleComp rc: ruleComps) {	
			if (!rc.scc()) continue;
			if (rc.get(0).isLeftRec()) continue;
			
			List<DeltaRule> addedRules = new ArrayList<DeltaRule>();
			rc.removeAllStartingRule();
			for (Rule r:rc.getRules()) {
				if (!(r instanceof DeltaRule)) continue;
				DeltaRule dr = (DeltaRule)r;
				
				Table t = tableMap.get(dr.getTheP().origName());
				if (t.isArrayTable() && t.numColumns()<=2) {
					rc.addStartingRule(dr.origRule());
					continue;
				}
				DeltaRule startRule = createStartingDeltaRule(t, dr);
				startingDeltaRules.add(startRule);
				rc.addStartingRule(startRule);
				addedRules.add(startRule);
			}
			rc.addAll(addedRules);
		}
		return startingDeltaRules;
	}

	int untypedVarCount() {
		int count=0;
		for (Rule r : rules) {
			count += untypedVarCount(r);
		}
		return count;
	}
	int untypedVarCount(Rule r) {
		Set<Variable> vars=r.getBodyVariables();
		int cnt = vars.size() - countTypedVars(vars);
		assert cnt >= 0;
		return cnt;
	}
	boolean requireTypeChecking(Rule r) {
		int typedVars = countTypedVars(r.getBodyVariables());
		if (typedVars == r.getBodyVariables().size())
			return false;
		return true;
	}	
	public boolean requireTypeChecking() {		
		for (Rule r : rules) {
			if (requireTypeChecking(r))
				return true;
		}
		return false;
	}
	
	Set<Variable> throwUnresolvedTypeException() {		
		for (Rule r : rules) {
			if (requireTypeChecking(r)) {
				Set<Variable> vars = new LinkedHashSet<Variable>();
				for (Variable v:r.getBodyVariables()) {
					if (!v.hasType())
						vars.add(v);
				}
				throw new AnalysisException("Cannot resolve types of the variable(s) "+vars, r);
			}
		}
		assert false:"never reaches here";
		return null;
	}

	int countTypedVars(Set<Variable> vars) {
		int typedVars = 0;
		for (Variable v : vars) {
			if (v.hasType()/* || v.dontCare*/)
				typedVars++;
		}
		return typedVars;
	}

	void initPredicatePos() {
		initPredicatePos(rules);
		if (query != null) {
			Predicate queryP = query.getP();
			queryP.setAsHeadP();
		}
	}
	void initPredicatePos(List<Rule> _rules) {
		for (Rule r : _rules) {
			initPredicatePos(r);
			r.getHead().setAsHeadP();
		}		
	}
	void initPredicatePos(Rule r) {
		for (int i = 0; i < r.getBody().size(); i++) {
			Object o = r.getBody().get(i);
			if (o instanceof Predicate) {
				Predicate p = (Predicate) o;
				p.setPos(i);
			}
		}
	}

	void makeRuleByHeadMaps() {
		for (Rule r: rules) {
			addToRulesByHeadName(r);			
		}
	}
	void addToRulesByHeadName(Rule r) {
		List<Rule> list = rulesByHeadName.get(r.getHead().name());
		if (list == null) {
			list = new ArrayList<Rule>();
			rulesByHeadName.put(r.getHead().name(), list);
		}
		list.add(r);
	}	

	void prepareTableStmts() {
		if (tableStmts == null) return;
		
		for (TableStmt stmt : tableStmts) {
			String name = stmt.tableName();
			Table t = tableMap.get(name);
			if (t==null) {
				L.warn("Table "+name+" does not exist.");
				continue;
			}
			stmt.setId(t.id());
		}
	}

	public RuleMap getRuleMap() {
		return ruleMap;
	}
	void prepareAggrFunctions() {
		for (Rule r: rules) {
			if (r.getHead().hasFunctionParam()) {
				prepareAggrFunction(r.getHead());				
			}
			for (Predicate p:r.getBodyP()) {
				if (p.hasFunctionParam()) {
					String msg="Functions cannot be in a parameter list of a predicate except for head predicate";
					throw new AnalysisException(msg, r);
				}						
			}
		}
	}	

	void prepareAggrFunction(Predicate p) {
		Table t = tableMap.get(p.name());
		prepareAggrFunction(p, t);
	}
	void prepareAggrFunction(Predicate p, Table t) {
		AggrFunction f = p.getAggrF();
		if (f == null) return;
		int idx = p.functionIdx();
		f.initPredicateInfo(t, p, idx);
	}

	void prepareEpochs() {
		if (epochs.size()==0) {
			if (tableStmts.isEmpty() && newTables.isEmpty()) return;
			epochs.add(new Epoch());
		}
		
		epochs.get(0).addNewTables(newTables);
		epochs.get(0).addTableStmts(tableStmts);
		for (Epoch e:epochs) {
			e.setTableMap(tableMap);
			e.setRuleMap(ruleMap);
			prepareNextIter(e);
		}
	}
	void prepareNextIter(Epoch e) {
		List<Table> initTables = new ArrayList<Table>();
		for (Rule r:e.getRules()) {
			if (r.isSimpleArrayInit()) {
				Table t = tableMap.get(r.getHead().name());
				initTables.add(t);
			}
		}
		List<TableStmt> clearTables = new ArrayList<TableStmt>();
		for (Rule r:e.getRules()) {
			Table t = tableMap.get(r.getHead().name());
			if (t instanceof IterTable) {
				if (initTables.contains(t)) continue;
				clearTables.add(new ClearTable(t.name(), t.id()));
			}
		}
		e.addTableStmts(clearTables);
	}

    /*
	void setAccessedTables(Epoch e) {		
		for (RuleComp rc : e.getRuleComps()) {
			for (Rule r : rc.getRules()) {
				//if (r.isSimpleArrayInit()) continue;

				Table t = tableMap.get(r.getHead().name());
				e.addAccessedTable(t);
				for (Predicate p : r.getBodyP()) {
					Table t2 = tableMap.get(p.name());
					e.addAccessedTable(t2);
				}
			}
		}
	}*/

	boolean appearsInAnyRuleBody(Table t) {
		for (Rule rule : rules) {
			if (appearsInRuleBody(t, rule))
				return true;
		}
		return false;
	}

	boolean appearsInRuleBody(Table table, Rule rule) {
		for (Predicate p : rule.getBodyP()) {
			Table t = tableMap.get(p.name());
			if (table.equals(t))
				return true;
		}
		return false;
	}

	void computeRuleDeps() {
		for (Rule r1 : rules) {
			if (r1.isSimpleArrayInit()) continue;
			for (Predicate p : r1.getBodyP()) {
				List<Rule> list = rulesByHeadName.get(p.name());
				if (list == null) continue;
				for (Rule r2 : list) {
					if (r2.isSimpleArrayInit()) continue;

					if (canMatchAny(r2.getHead(), r1.getBodyP())) {
						r1.dependOn(r2);
						r2.usedBy(r1);
					}
				}
			}
		}
		ruleComps = findRuleComp();
	}

	// returns true iff P(x, y, ..) :- P(a, b, ..).
	// where x,y,.. a,b are non-functions
	boolean isTrivialRecursion(Rule r) {
		if (r.getBody().size() != 1) return false;
		if (r.getBodyP().size() != 1) return false;

		Predicate h = r.getHead();
		if (h.hasFunctionParam())
			return false;

		Predicate b = r.getBodyP().get(0);
		if (h.name().equals(b.name()))
			return true;
		return false;
	}
	boolean isFirstTableOrderBy(Rule r) {
		if (r.getBodyP().size()==0) return false;		
		if (!(r.getBody().get(0) instanceof Predicate)) return false;
		Predicate p=(Predicate)r.getBody().get(0);
		Table t=tableMap.get(p.name());
		return t.hasOrderBy();
	}

	List<RuleComp> findRuleComp() {
		// find SCC (strongly-connectec-component) and other rule compoments
		// see {@link RuleComp}
		
		Set<Rule> addedRules = new LinkedHashSet<Rule>();
		List<RuleComp> comps = new ArrayList<RuleComp>();
		Set<Rule> marked = new LinkedHashSet<Rule>();
		for (Rule r : rules) {
			if (r.isSimpleUpdate()) continue;
			if (r.isSimpleArrayInit()) continue;
			if (isTrivialRecursion(r)) continue;
			if (isFirstTableOrderBy(r)) continue;
			
			if (addedRules.contains(r)) continue;
			
			marked.clear();
			List<Rule> result = findSCCfrom(r, marked, r);
			if (result != null) {
				assert !addedRules.contains(result.get(0));
				for (Rule _r:result) _r.setInScc();
				addedRules.addAll(result);
				RuleComp rc = new RuleComp(result, true/*scc*/);
				comps.add(rc);	
			}
			
			result = findPipelinedCompFrom(r);
			if (result != null) {
				addedRules.addAll(result);
				RuleComp rc = new RuleComp(result, false/*not scc*/);
				rc.addStartingRule(r);
				rc.setPipeliningFrom(r);
				comps.add(rc);
			}
		}

		// (single rule itself becomes a trivial scc)
		for (Rule r : rules) {
			if (!addedRules.contains(r))
				comps.add(new RuleComp(r, false/*not scc*/));
		}
		return comps;
	}

	List<Rule> findPipelinedCompFrom(Rule from) {
		Table headT = tableMap.get(from.getHead().name());
		if (!headT.isApprox()) return null;
		
		List<Rule> rules = new ArrayList<Rule>();
		for (Rule to:from.getRulesUsingThis()) {
			rules.add(to);
		}
		return rules;
	}
	
	boolean hasNoMatchButNegatedOne(Rule r, Predicate p) {
		for (Predicate p2:r.getBodyP()) {
			if (p2.isNegated()) continue;
			if (p2.name().equals(p.name()))
				return false;
		}
		return true;
	}
	List<Rule> findSCCfrom(Rule root, Set<Rule> marked, Rule currentRule) {
		marked.add(currentRule);
		for (Rule r2 : currentRule.getRulesUsingThis()) {
			if (hasNoMatchButNegatedOne(r2, currentRule.getHead())) continue;
			if (r2 == root && canMatchAny(currentRule.getHead(), r2.getBodyP())) {
				List<Rule> result = new ArrayList<Rule>();
				result.add(r2);
				if (!result.contains(currentRule))
					result.add(currentRule);
				return result;
			}
			if (marked.contains(r2)) continue;
			
			List<Rule> scc = findSCCfrom(root, marked, r2);
			if (scc != null) {
				if (!scc.contains(currentRule))
					scc.add(currentRule);
				return scc;
			}
		}
		return null;
	}

	boolean updateEpochLevel(RuleComp rc, TObjectIntHashMap<RuleComp> epoch, int l) {
		int l2 = epoch.get(rc);
		if (l2 < l) {
			epoch.put(rc, l);
			return true;
		}
		return false;
	}
	boolean updateEpochLevel(List<RuleComp> rclist, TObjectIntHashMap<RuleComp> epoch, int l) {
		boolean change = false;
		for (RuleComp rc : rclist) {
			if (updateEpochLevel(rc, epoch, l))
				change = true;
		}
		return change;
	}
	int getHighestLevel(List<RuleComp> rclist, TObjectIntHashMap<RuleComp> stratum) {
		int highest = 0;
		for (RuleComp rc : rclist) {
			int l = stratum.get(rc);
			highest = Math.max(highest, l);
		}
		return highest;
	}
	void assertSameLevel(List<RuleComp> ruleComps,
			TObjectIntHashMap<RuleComp> stratum) {
		RuleComp rc = ruleComps.get(0);
		int l = stratum.get(rc);
		for (RuleComp rc2 : ruleComps) {
			if (l != stratum.get(rc2))
				assert false:"RuleComp[" + rc2 + "]" + "'s level:"
						+ stratum.get(rc2) + "!=" + l;
		}
	}
	List<String> getNegatedPnames(Rule r) {
		List<String> l = new ArrayList<String>();
		for (Predicate p:r.getBodyP()) {
			if (p.isNegated()) l.add(p.name());
		}
		return l;
	}
	void makeEpochs() {
		TObjectIntHashMap<RuleComp> levelMap = new TObjectIntHashMap<RuleComp>();
		for (RuleComp rc : ruleComps)
			levelMap.put(rc, 0);

		int highest = 0;
		boolean change = false;
		do {
			change = false;
			for (RuleComp rc : ruleComps) {
				int l = levelMap.get(rc);				
				l=l+1;
				
				List<RuleComp> usedBy = new ArrayList<RuleComp>(
												rc.getRuleCompsUsingThis());
				usedBy.remove(rc);
				//removeNegatedDepRule(usedBy);
				
				change = change | updateEpochLevel(usedBy, levelMap, l);
				int newHighest = getHighestLevel(usedBy, levelMap);
				highest = Math.max(highest, newHighest);
			}
		} while (change);

		for (int i = 0; i <= highest; i++) {
			List<RuleComp> ruleCompsInLevel = new ArrayList<RuleComp>();
			for (RuleComp rc : ruleComps) {
				int l = levelMap.get(rc);
				if (l == i)
					ruleCompsInLevel.add(rc);
			}
			if (ruleCompsInLevel.size() > 0) {
				Epoch s = new Epoch(ruleCompsInLevel, i);
				epochs.add(s);
			}
		}
	}
	
	void removeNegatedDepRule(List<RuleComp> usedBy) {
		/** 
		 * Path(s, depth, $Choice(to)) :- s=0, depth=1, Edge(s, to), !Path(s, _, to);   .... (1)
		 *			  				   :- Path(_, prevDepth, s), depth=prevDepth+1, Edge(s, to), !Path(s, _, to).  ... (2)
		 *
		 * remove rule (1) from usedBy list of rule (2) so that the negation has the natural semantics.
		 */
		List<RuleComp> toRemove = new ArrayList<RuleComp>();
		for (RuleComp _rc:usedBy) {
			if (_rc.size()==0) toRemove.add(_rc); // merged-rule (base-case for linear recursion)
			if (!_rc.scc() && _rc.size()==1) {
				List<String> negatedPnames = getNegatedPnames(_rc.get(0));
				if (negatedPnames.contains(_rc.get(0).getHead().name())) {
					toRemove.add(_rc);
				}								
			}
		}
		usedBy.removeAll(toRemove);
	}

	void computeParamTypes() {
		for (Rule r : rules) {
			r.computeParamTypes(tableMap);
			computeExprTypes(r);
		}		
		if (query != null) {
			Table t = tableMap.get(query.getP().name());
			query.computeParamTypes(t);
		}
	}

	void computeExprTypes(Rule r) {
		Expr currentExpr=null;
		try {
			for (Object o : r.getBody()) {
				if (o instanceof Expr) {
					currentExpr = (Expr)o;
					computeExprType(currentExpr);
				}
			}
		} catch (InternalException e) {
			throw new AnalysisException(e, r, currentExpr); 
		}
		for (Object o : r.getBody()) {
			if (o instanceof Expr)
				forceTypesForAssignOp((Expr) o);
		}
	}

	void computeExprType(Expr expr) throws InternalException {
		if (expr.root instanceof AssignOp) {
			AssignOp op = (AssignOp)expr.root;			
			op.computeLhsTypes();			
		}
	}
	boolean containsOnlyPrim(Set<Class> types) {
		for (Class type:types) {
			if (!MyType.isPrimitive(type))
				return false;
		}
		return true;
	}

	void forceTypesForAssignOp(Expr expr) {
		if (true) return;
		
		
		/*
		if (expr.root instanceof AssignOp) {
			AssignOp op = (AssignOp) expr.root;
			if (op.isMultipleAssign())
				return;

			final Class assignedType = ((Variable) op.arg1).type;
			if (assignedType.equals(double.class)) {
				op.visit(new OpVisitor() {
					public void visit(Op o) {
						if (o instanceof BinOp) {
							BinOp binop = (BinOp) o;
							binop.forceType(assignedType);
						}
					}
				});
			}
		} 
		*/		
	}

	void markSimpleArrayInit() {
	nextRule: 
		for (Rule r:rules) {
			if (!r.hasOnlySimpleAssignExpr()) continue;
			Predicate headP = r.getHead();
			if (headP.hasFunctionParam()) continue;
			Table headT = tableMap.get(headP.name());
			if (!headT.isArrayTable()) continue;

			Param[] params = headP.inputParams();
			int dontCare = 0;
			for (int i = 0; i < params.length; i++) {
				Param p = params[i];
				if (p instanceof Variable) {
					Variable v = (Variable) p;
					Column c = headT.getColumn(i);
					if (v.dontCare) dontCare++;
					if (v.dontCare && !c.isArrayIndex())
						continue nextRule;
				}
			}
			if (dontCare == 0) continue;
			if (dontCare >= 2) {
				String msg="Cannot have more than 2 don't-cares in rule head";
				throw new AnalysisException(msg, r);
			}
			r.setSimpleArrayInit();
		}
	}

	void typeCheckRuleHeads() {
		for (Rule r:rules) {
			Predicate h=r.getHead();
			Table ht=tableMap.get(h.name());
			
			for (int i=0; i<h.params.size(); i++) {
				Param p=h.params.get(i);
				if (p instanceof Function) break;
				typeCheckInHead(p, ht.types()[i], r);
			}
		}
	}	
	@SuppressWarnings("unchecked")
	void typeCheckInHead(Param p, @SuppressWarnings("rawtypes") Class type, Rule r) {
		if (p instanceof Variable && ((Variable)p).dontCare) return;
		
		if (!type.isAssignableFrom(MyType.javaType(p))) {
			String msg="";
			if (p instanceof Variable) {
				Variable v=(Variable)p;
				msg += v.name ;
			} else if (p instanceof Const) {
				Const c=(Const)p;
				msg += c.val;
			} else msg += p;
			
			msg += " in the rule head ("+r.getHead()+") is incompatible with the table declaration.";
			throw new AnalysisException(msg, r);
		}
	}
	void checkUnresolvedVars() {
		for (Rule r:rules) {
			Set<Variable>[] resolvedVars = getResolvedVars(r);
			int i = 0;
			for (Object o : r.getBody()) {
				if (o instanceof Expr) {
					Expr expr = (Expr) o;
					Set<Variable> vars = expr.getVariables();
					if (expr.root instanceof AssignOp) {
						AssignOp op = (AssignOp) expr.root;
						vars.removeAll(op.getLhsVars());
					}
					if (!resolvedVars[i].containsAll(vars)) {
						String msg="Unbound variable(s) in expression:"+expr+", in "+r;
						throw new AnalysisException(msg, r);
					}
				}
				i++;
			}
			if (r.getHead().hasFunctionParam()) {
				Function f = r.getHead().getAggrF();
				
				if (!resolvedVars[i].containsAll(f.getVariables())) {
					String msg="Unbound variable(s) in rule:"+r;
					throw new AnalysisException(msg, r);					
				}
			}
		}
	}

	public static LinkedHashSet<Variable>[] getResolvedVars(Rule rule) {
		int termCount = rule.getBody().size();
		@SuppressWarnings("unchecked")
		LinkedHashSet<Variable> resolved[] = new LinkedHashSet[termCount+1];
		Set<Variable> vars = null, prevVars = null;
		int i = 0;
		for (Object o : rule.getBody()) {
			if (o instanceof Predicate) {
				Predicate p = (Predicate) o;
				vars = p.getVariables();
			} else if (o instanceof Expr) {
				Expr e = (Expr) o;
				if (e.root instanceof AssignOp) {
					AssignOp op = (AssignOp) e.root;
					vars = op.getLhsVars();
				}
			} else assert false:"should not reach here";
			
			resolved[i] = new LinkedHashSet<Variable>();
			if (i > 0) {
				resolved[i].addAll(resolved[i - 1]);
				resolved[i].addAll(prevVars);
			}
			prevVars = vars;
			i++;
		}
		/* resolved vars at the end of the rule */
		resolved[i] = new LinkedHashSet<Variable>();
		resolved[i].addAll(resolved[i - 1]);
		resolved[i].addAll(prevVars);
		return resolved;
	}

	public static boolean isResolved(Rule rule, Predicate p, Object param) {
		if (param instanceof Variable)
			return isResolved(rule, p, (Variable) param);
		assert !(param instanceof Function);
		return true;
	}

	public static boolean isResolved(Rule rule, Predicate p, Variable v) {
		assert rule.getBodyP().contains(p);
		Set<Variable>[] resolved = getResolvedVars(rule);
		return resolved[p.getPos()].contains(v);
	}
	public static boolean isResolved(Set<Variable>[] resolvedVarsArray, Predicate p, Object param) {
		if (param instanceof Variable)
			return isResolved(resolvedVarsArray, p, (Variable)param);
		assert !(param instanceof Function);
		return true;
	}
	
	public static boolean isResolved(Set<Variable>[] resolvedVarsArray, Predicate p, Variable v) {
		return resolvedVarsArray[p.getPos()].contains(v);
	}
}
