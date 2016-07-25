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

import socialite.dist.master.MasterNode;
import socialite.functions.FunctionLoader;
import socialite.parser.*;
import socialite.parser.antlr.ClearTable;
import socialite.parser.antlr.ColumnGroup;
import socialite.parser.antlr.DropTable;
import socialite.parser.antlr.RuleDecl;
import socialite.parser.antlr.TableStmt;
import socialite.resource.RuleMap;
import socialite.util.AnalysisException;
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
        this(_p.getRules(), _p.getNewTables(), _p.getTableMap(),
                _p.getTableStmts(), _p.getQuery());
        p = _p;
    }

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
                if (g.hasIndex() && !g.hasArrayIndex()) {
                    String msg = "Cannot use both indexby and sortby to a same nested table.";
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
                if (isDistributed()) {
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
        makeEpochs();

        optimizeLock();
        createDeltaRules();
        processRemoteRules();

        prepareEpochs();
        processDropTableStmt();
    }

    void optimizeLock() {
        for (Rule r: rules) {
            selectPartitionTable(r);
        }
    }
    void selectPartitionTable(Rule r) {
        Table headT = tableMap.get(r.getHead().name());
        Param param = r.getHead().inputParams()[headT.getPartitionColumn()];
        if (r.getBodyP().size()==0) {
            r.setAsyncEval();
            return;
        }
        Literal l = r.getBody().get(0);
        if (l instanceof Predicate) {
            Predicate p = (Predicate)l;
            Table t = tableMap.get(p.name());
            r.setPartitionTable(t, p);
            if (p.first().equals(param)) {
                r.setAsyncEval();
                return;
            }
        }
        for (Predicate p:r.getBodyP()) {
            Table t = tableMap.get(p.name());
            int col = t.getPartitionColumn();
            if (p.inputParams()[col].equals(param)) {
                r.setAsyncEval();
                r.setPartitionTable(t, p);
                return;
            }
        }
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

    void setGroupby() {
        for (Rule r : rules) {
            setGroupby(r);
        }
    }

    void setGroupby(Rule r) {
        Predicate head = r.getHead();
        if (!head.hasFunctionParam()) return;

        int idx = head.firstFunctionIdx();
        Table t = tableMap.get(head.name());

        if (t instanceof IterTableMixin) {
            IterTableMixin it = (IterTableMixin)t;
            for (String name: it.getAllTableNames()) {
                Table t2 = tableMap.get(name);
                setGroupby(r, t2, idx);
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

    public static boolean canMatch(Predicate p1, Predicate p2,
                                   Map<String, Table> map) {
        if (!p1.name().equals(p2.name())) return false;

        Table t = map.get(p1.name());
        Param[] params1 = p1.inputParams();
        Param[] params2 = p2.inputParams();
        assert params1.length==params2.length;
        int compareUpto=t.numColumns();
        if (p1.hasFunctionParam())
            compareUpto = p1.firstFunctionIdx();
        if (p2.hasFunctionParam())
            compareUpto = p2.firstFunctionIdx();

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

    /**
     * returns true if the updating shard at the head table is same as the block
     * at the first table.
     */
    public static boolean updateParallelShard(Rule r, Map<String, Table> tableMap) {
        if (isSequentialRule(r, tableMap)) return false;

        return isReferringSamePartition(r.getHead(), r.firstP(), tableMap);
    }

    static boolean isReferringSamePartition(Predicate h, Predicate f, Map<String, Table> tableMap) {
         if (f.first() instanceof Const || h.first() instanceof Const) {
            return false;
        }
        Variable v1 = (Variable)f.first();
        Variable v2 = (Variable)h.first();
        if (!v1.equals(v2)) {
            return false;
        }
        Table ht = tableMap.get(h.name());
        Table ft = tableMap.get(f.name());
        if (ht instanceof GeneratedT || ft instanceof GeneratedT) {
            return false;
        }
        Column c1 = ht.getColumn(0);
        Column c2 = ft.getColumn(0);
        return isSameArrayColumn(c1, c2) || isHashShardedColumn(c1, c2);
    }

    static boolean isHashShardedColumn(Column c1, Column c2) {
        if (!c1.hasRange() && !c2.hasRange()) {
            return true;
        }
        return false;
    }
    static boolean isSameArrayColumn(Column c1, Column c2) {
        if (c1.hasRange() && c2.hasRange()) {
            int[] r1 = c1.getRange();
            int[] r2 = c2.getRange();
            if (r1[0]==r2[0] && r1[1]==r2[1])
                return true;
        }
        return false;
    }

    public static boolean isParallelRule(Rule r, Map<String, Table> tableMap) {
        if (isSequentialRule(r, tableMap)) return false;
        return true;
    }

    public static boolean isSequentialRule(Rule r, Map<String, Table> tableMap) {
        Object first = r.getBody().get(0);
        if (!(first instanceof Predicate))
            return true;

        Predicate firstP = (Predicate)first;
        Table firstT = tableMap.get(firstP.name());

        if (firstP.first() instanceof Const) {
            return true;
        } else {
            return false;
        }
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
        if (bodyP.size() <= 1) {
            return false;
        }

        List<Integer> transferPos = tableTransferPos(r, tableMap);
        return transferPos.size() > 0;
    }

    static boolean hasSamePartition(Table t1, Table t2) {
        if (t1 instanceof ArrayTable && t2 instanceof ArrayTable) {
            return t1.arrayBeginIndex() == t2.arrayBeginIndex() &&
                    t1.arrayEndIndex() == t2.arrayEndIndex();
        } else if (!(t1 instanceof ArrayTable) && !(t2 instanceof ArrayTable)) {
            return t1.getColumn(0).type().equals(t2.getColumn(0).type());
        } else {
            return false;
        }
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
    public static List<Integer> tableTransferPos(Rule r, Map<String, Table> tableMap) {
        List<Predicate> bodyP = r.getBodyP();

        List<Integer> transferPos = new ArrayList<>();
        Predicate prevp = null;
        for (Predicate p : bodyP) {
            if (prevp == null || prevp instanceof PrivPredicate) {
                prevp = p;
                continue;
            }
            if (requireTransfer(prevp, p, tableMap)) {
                if (collectLiveVarsAt(r, p.getPos()).isEmpty()) {
                    continue;
                }
                transferPos.add(p.getPos());
            }
            prevp = p;
        }
        return transferPos;
    }

    public static boolean hasRemoteRuleHead(Rule r, Map<String, Table> tableMap) {
        Predicate h = r.getHead();
        if (r.isSimpleArrayInit()) return false;
        if (r.getBodyP().size()==0) return true;

        List<Predicate> body = r.getBodyP();
        Predicate p=body.get(body.size()-1);
        return requireTransfer(p, h, tableMap);
    }

    protected boolean isDistributed() {
        return MasterNode.getInstance() != null;
    }
    void processRemoteRules() {
        if (!isDistributed()) return;

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
                if (t.hasNesting()) {
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
        List<Integer> sendPos = tableTransferPos(r, tableMap);//tableTransferPos(r.getBodyP());
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
        SArrayList<Param> vars = new SArrayList<>();
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
        deltaRules = new ArrayList<>();
        assert ruleMap != null;

        List<DeltaRule> toAdd = new ArrayList<DeltaRule>();
        Set<String> deltaRuleSigs = new LinkedHashSet<String>();
        for (RuleComp rc : ruleComps) {
            if (!rc.scc()) { continue; }
            deltaRuleSigs.clear();
            for (Rule r: rc.getRules()) {
                List<Rule> depRules = new ArrayList<>(r.getDependingRules());
                for (Rule r2 : depRules) {
                    String name = r2.getHead().name();
                    for (Predicate p : getMatchingPs(r, name)) {
                        if (p.isNegated()) { continue; }

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

        for (DeltaRule dr : deltaRules) {
            dr.updateRuleDeps();
        }

        List<DeltaRule> startingDeltaRules = createStartingDeltaRules();
        rules.addAll(deltaRules);
        rules.addAll(startingDeltaRules);
    }

    List<DeltaRule> createStartingDeltaRules() {
        List<DeltaRule> startingDeltaRules = new ArrayList<>();
        for (RuleComp rc: ruleComps) {
            if (!rc.scc()) continue;

            List<DeltaRule> addedRules = new ArrayList<DeltaRule>();

            for (Rule r:rc.getRules()) {
                if (!(r instanceof DeltaRule)) continue;
                DeltaRule dr = (DeltaRule)r;

                Table t = tableMap.get(dr.getTheP().origName());
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
        List<AggrFunction> list = p.getAggrFuncs();
        for (AggrFunction aggr: list) {
            int idx = p.functionIdx(aggr);
            aggr.initPredicateInfo(t, p, idx);
        }
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
            if (t instanceof IterTableMixin) {
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
        if (r.getBodyP().size() != 1) {
            return false;
        }

        Predicate h = r.getHead();
        if (h.hasFunctionParam()) {
            return false;
        }

        Predicate b = r.getBodyP().get(0);
        return h.name().equals(b.name());
    }

    boolean findScc(Table root, Map<Table, List<Table>> deps, Table cur, Set<Table> path, Set<Table> visited) {
        if (!deps.containsKey(cur)) { return false; }
        if (visited.contains(cur)) { return false; }

        visited.add(cur);
        boolean scc = false;
        for (Table v: deps.get(cur)) {
            if (root.equals(v)) {
                scc = true;
                path.add(v);
            } else if (findScc(root, deps, v, path, new HashSet<>(visited))) {
                scc = true;
                path.add(v);
            }
        }
        return scc;
    }

    List<RuleComp> findRuleComp() {
        // find rules that form strongly-connected components in dependency graph.

        Set<Rule> addedRules = new LinkedHashSet<>();
        List<RuleComp> comps = new ArrayList<>();
        Map<Table, List<Table>> deps = new HashMap<>();

        for (Rule r: rules) {
            if (r.isSimpleArrayInit()) continue;
            if (isTrivialRecursion(r)) continue;

            Table vertice = tableMap.get(r.getHead().name());
            if (!deps.containsKey(vertice)) {
                deps.put(vertice, new ArrayList<>());
            }
            for (Predicate p: r.getBodyP()) {
                Table t = tableMap.get(p.name());
                deps.get(vertice).add(t);
            }
        }

        List<Set<Table>> sccList = new ArrayList<>();
        HashSet<Table> visited = new HashSet<>();
        for (Table v: deps.keySet()) {
            Set<Table> scc = new HashSet<>();
            if (findScc(v, deps, v, scc, visited)) {
                sccList.add(scc);
            }
            visited.addAll(scc);
        }

        for (Set<Table> scc: sccList) {
            List<Rule> sccRules = new ArrayList<>();
            for (Rule r: rules) {
                if (r.isSimpleArrayInit()) continue;
                if (isTrivialRecursion(r)) continue;

                Table h = tableMap.get(r.getHead().name());
                if (scc.contains(h)) {
                    sccRules.add(r);
                }
            }
            RuleComp comp = new SCC(sccRules);
            comps.add(comp);
            addedRules.addAll(sccRules);
        }

        // RuleComp for single rule (not in scc)
        for (Rule r: rules) {
            if (!addedRules.contains(r))
                comps.add(new RuleComp(r));
        }
        return comps;
    }

    List<Rule> findPipelinedCompFrom(Rule from) {
        Table headT = tableMap.get(from.getHead().name());
        if (!headT.isApprox()) return null;

        List<Rule> rules = new ArrayList<>();
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
        boolean change;
        do {
            change = false;
            for (RuleComp rc : ruleComps) {
                int l = levelMap.get(rc);
                l=l+1;

                List<RuleComp> usedBy = new ArrayList<RuleComp>(
                        rc.getRuleCompsUsingThis());
                usedBy.remove(rc);

                change = change | updateEpochLevel(usedBy, levelMap, l);
                int newHighest = getHighestLevel(usedBy, levelMap);
                highest = Math.max(highest, newHighest);
            }
        } while (change);

        for (int i = 0; i <= highest; i++) {
            List<RuleComp> ruleCompsInLevel = new ArrayList<>();
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
                for (Function f:r.getHead().getAggrFuncs()) {
                    if (!resolvedVars[i].containsAll(f.getVariables())) {
                        String msg = "Unbound variable(s) in rule:" + r;
                        throw new AnalysisException(msg, r);
                    }
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
        for (Literal o : rule.getBody()) {
            if (o instanceof Predicate) {
                Predicate p = (Predicate) o;
                vars = p.getVariables();
            } else if (o instanceof Expr) {
                Expr e = (Expr) o;
                if (e.root instanceof AssignOp) {
                    AssignOp op = (AssignOp) e.root;
                    vars = op.getLhsVars();
                }
            } else {
                assert false:"should not reach here";
            }

            resolved[i] = new LinkedHashSet<>();
            if (i > 0) {
                resolved[i].addAll(resolved[i - 1]);
                resolved[i].addAll(prevVars);
            }
            prevVars = vars;
            i++;
        }
		/* resolved vars at the end of the rule */
        resolved[i] = new LinkedHashSet<>();
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
        if (param instanceof Variable) {
            return isResolved(resolvedVarsArray, p, (Variable)param);
        }
        assert !(param instanceof Function);
        return true;
    }

    public static boolean isResolved(Set<Variable>[] resolvedVarsArray, Predicate p, Variable v) {
        return resolvedVarsArray[p.getPos()].contains(v);
    }
}
