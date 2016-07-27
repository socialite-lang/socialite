package socialite.codegen;

import java.util.*;

import org.apache.commons.lang3.StringEscapeUtils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import socialite.dist.master.MasterNode;
import socialite.parser.*;
import socialite.parser.antlr.ColumnGroup;
import socialite.resource.SRuntimeMaster;
import socialite.tables.TableUtil;
import socialite.util.Assert;
import socialite.yarn.ClusterConf;

public class JoinerCodeGen {
    static String joinerPackage = "socialite.tables";

    Rule rule;
    STGroup tmplGroup;
    ST joinerTmpl;
    String joinerName;

    Epoch epoch;
    Map<String, Table> tableMap;
    Map<String, ST> visitMethodMap = new HashMap<>();
    Map<Predicate, ST> visitorMap = new HashMap<>();
    Predicate headP, iterStartP;
    Table headT;
    DeltaTable deltaHeadT;
    Set<Variable>[] resolvedVarsArray;

    public JoinerCodeGen(Epoch e, Rule r, Map<String, Table> _tableMap) {
        epoch = e;
        rule = r;
        tableMap = _tableMap;

        joinerName = joinerClassName(rule, tableMap);

        headP = rule.getHead();
        headT = tableMap.get(headP.name());
        tmplGroup = CodeGenBase.getVisitorGroup();
        joinerTmpl = tmplGroup.getInstanceOf("class");

        if (rule.inScc()) {
            if (headT instanceof DeltaTable) {
                deltaHeadT = (DeltaTable) tableMap.get(headT.name());
            } else {
                deltaHeadT = (DeltaTable) tableMap.get(DeltaTable.name(headT));
            }
        }

        resolvedVarsArray = Analysis.getResolvedVars(rule);
    }
    boolean isDistributed() {
        return MasterNode.getInstance() != null;
    }
    String getVisitMethodSig(Predicate p, String visitName, Class... types) {
        String sig = p.name()+"."+p.getPos()+"."+visitName+"#";
        for (Class type : types) {
            sig += "#" + type.getSimpleName();
        }
        return sig;
    }

    ST getVisitMethod(Predicate p, int startCol, int endCol, int numColumns) {
        String name = "visit" + CodeGenBase.getVisitColumns(startCol, endCol, numColumns);
        Table t = getTable(p);
        Class[] argTypes = CodeGenBase.getArgTypes(t, startCol, endCol);
        String sig = getVisitMethodSig(p, name, argTypes);
        ST method = visitMethodMap.get(sig);
        if (method == null) {
            method = getNewVisitMethodTmpl(name);
            method.add("ret", "return true");
            CodeGenBase.addArgTypes(method, p, startCol, endCol);
            visitMethodMap.put(sig, method);
            ST visitor = visitorMap.get(p);
            visitor.add("methods", method);
        }
        return method;
    }

    ST getNewVisitMethodTmpl(String name) {
        ST m = tmplGroup.getInstanceOf("visitMethod");
        m.add("name", name);
        return m;
    }
    ST getNewMethodTmpl(String name, String type) {
        return getNewMethodTmpl(name, "public", type);
    }

    ST getNewMethodTmpl(String name, String modifier, String type) {
        ST m = tmplGroup.getInstanceOf("method");
        m.add("modifier", modifier);
        m.add("type", type);
        m.add("name", name);
        return m;
    }

    public String joinerName() {
        return joinerPackage + "." + joinerName;
    }

    boolean isGenerated() {
        return joinerTmpl.getAttribute("name") != null;
    }
    public String generate() {
        if (isGenerated()) { return joinerTmpl.render(); }

        joinerTmpl.add("packageStmt", "package " + joinerPackage);
        joinerTmpl.add("modifier", "public");
        joinerTmpl.add("name", joinerName);

        joinerTmpl.add("extends", "extends Joiner");
        joinerTmpl.add("interfaces", "Runnable");

        importTablesDeclareFields();
        generateConstructor();
        generateMethods();
        return joinerTmpl.render();
    }


    void importTablesDeclareFields() {
        for (Predicate p : rule.getAllP()) {
            String var = getVarName(p);
            Table t = getTable(p);
            String tableClass = t.className();

            joinerTmpl.add("imports", TableUtil.getTablePath(tableClass));
            if (isTablePartitioned(t)) { tableClass += "[]"; }
            joinerTmpl.add("fieldDecls", tableClass + " " + var);
        }
        maybeDeclareDeltaTable();

        joinerTmpl.add("fieldDecls", decl("SRuntime", runtimeVar()));
        joinerTmpl.add("fieldDecls", decl("int", epochIdVar()));
        joinerTmpl.add("fieldDecls", decl("int", ruleIdVar()));
        joinerTmpl.add("fieldDecls", decl("TableInstRegistry", registryVar()));
        joinerTmpl.add("fieldDecls", decl("TablePartitionMap", partitionMapVar()));
        for (Const c:rule.getConsts()) {
            String type = MyType.javaTypeName(c);
            joinerTmpl.add("fieldDecls", decl(type, c));
        }
        declareParamVariables();

        joinerTmpl.add("fieldDecls", decl("int", myPartitionIdx()));

        declareRemoteTablesIfAny();
        declareVisitorVars();
        declareConstraintVars();
    }
    void declareVisitorVars() {
        for (Predicate p:rule.getBodyP()) {
            Table t = getTable(p);
            joinerTmpl.add("fieldDecls", decl(t.visitorClass(), visitorVar(p)));
        }
    }
    void declareConstraintVars() {
        for (Predicate p:rule.getBodyP()) {
            Table t = getTable(p);
            if (t.hasIndexby() || t.hasSortby()) {
                joinerTmpl.add("fieldDecls", decl("ColumnConstraints", constraintVar(p)));
            }
        }
    }

    void declareParamVariables() {
        for (Variable v : rule.getBodyVariables()) {
            if (!v.isRealVar()) { continue; }
            String type = MyType.javaTypeName(v);
            joinerTmpl.add("fieldDecls", decl(type, v));
        }
    }

    void maybeDeclareDeltaTable() {
        if (!accumlDelta(rule)) { return; }

        String tableClass = deltaHeadT.className();
        joinerTmpl.add("fieldDecls", decl(tableClass, deltaTableVar()));
        joinerTmpl.add("fieldDecls", decl(tableClass, deltaTableReturnVar()));
    }

    void declareRemoteTablesIfAny() {
        declareRemoteBodyTablesIfAny();
        declareRemoteHeadTableIfAny();
    }

    void declareRemoteBodyTablesIfAny() {
        if (!isDistributed()) { return; }
        for (int pos : tableTransferPos()) {
            String tableCls = tableMap.get(RemoteBodyTable.name(rule, pos)).className();
            String init = "= new "+tableCls+"["+machineNum()+"]";
            joinerTmpl.add("fieldDecls", decl(tableCls+"[]", remoteTableVar(pos))+init);
        }
    }

    void declareRemoteHeadTableIfAny() {
        if (!isDistributed()) { return; }
        if (!hasRemoteRuleHead()) { return; }

        Table t = headT;
        String tableCls = tableMap.get(RemoteHeadTable.name(t, rule)).className();
        int clusterSize = SRuntimeMaster.getInst().getWorkerAddrMap().size();

        String init = "=new " + tableCls + "[" + clusterSize + "]";
        joinerTmpl.add("fieldDecls", decl(tableCls+"[]", remoteTableVar("head"))+init);
    }

    String deltaTableReturnVar() {
        return "ret$delta$" + headT.name();
    }

    String deltaTableVar() {
        return "delta$" + headT.name();
    }

    boolean accumlDelta(Rule r) {
        if (r.inScc()) { return true; }
        if (r.hasPipelinedRules()) {
            return true;
        }
        return false;
    }

    Table getTable(Predicate p) {
        return tableMap.get(p.name());
    }

    String concat(String prefix, Object o) {
        return prefix + o;
    }
    String cast(String type) {
        return "("+type+")";
    }
    void generateConstructor() {
        ST code = getNewMethodTmpl(joinerName, "");
        joinerTmpl.add("methodDecls", code);

        code.add("args", decl("int", concat("_", epochIdVar())));
        code.add("args", decl("int", concat("_", ruleIdVar())));
        for (Const c:rule.getConsts()) {
            String argVar = concat("_$", c);
            code.add("args", decl(c.type, argVar));
            code.add("stmts", init(c, argVar));
        }
        for (Predicate p : rule.getBodyP()) {
            Table t = getTable(p);
            if (t instanceof GeneratedT) {
                String var = getVarName(p);
                String argVar = concat("_$", var);
                code.add("args", decl(t.className(), argVar));
                code.add("stmts", init(var, argVar));
            }
        }

        code.add("args", decl("SRuntime", concat("_", runtimeVar())));
        code.add("args", decl("int", concat("_", myPartitionIdx())));

        code.add("stmts", init(epochIdVar(), concat("_", epochIdVar())));
        code.add("stmts", init(ruleIdVar(), concat("_", ruleIdVar())));
        code.add("stmts", init(runtimeVar(), concat("_", runtimeVar())));
        code.add("stmts", init(partitionMapVar(), call(runtimeVar(), "getPartitionMap")));
        code.add("stmts", init(registryVar(), call(runtimeVar(), "getTableRegistry")));
        code.add("stmts", init(myPartitionIdx(), concat("_", myPartitionIdx())));

        for (Predicate p : rule.getAllP()) {
            Table t = getTable(p);
            if (t instanceof GeneratedT) { continue; }
            String var = getVarName(p);
            String tableClass = t.className();
            if (isTablePartitioned(t)) {
                tableClass+="[]";
            }
            code.add("stmts", init(var, cast(tableClass)+call(registryVar(), "getTableInstArray", t.id())));
        }

        for (Predicate p:rule.getBodyP()) {
            ST newVisitor = createNewVisitor(p);
            newVisitor.add("var", visitorVar(p));
            code.add("stmts", newVisitor);
        }
        for (Predicate p:rule.getBodyP()) {
            Table t = getTable(p);
            if (t.hasIndexby() || t.hasSortby()) {
                code.add("stmts", createNewConstraints(p));
            }
        }
    }
    ST createNewVisitor(Predicate p) {
        ST visitor = visitorMap.get(p);
        if (visitor == null) {
            visitor = tmplGroup.getInstanceOf("newVisitor");
            Table t = getTable(p);
            visitor.add("visitorClass", t.visitorClass());
            visitorMap.put(p, visitor);
        }
        return visitor;
    }
    String createNewConstraints(Predicate p) {
        Table t = getTable(p);
        String buildConstr = constraintVar(p);
        buildConstr = init(buildConstr, "new ColumnConstraints()");
        return buildConstr;
    }

    void generateMethods() {
        generateIdGetters();
        generateRunMethod();
        generateVisitMethods();
        generateRemoteTableMethods();
        generateDeltaTableMethods();
        generateToString();
    }

    void generateToString() {
        ST code = getNewMethodTmpl("toString", "String");
        String ruleStr = StringEscapeUtils.escapeJava(rule.toString());
        code.add("stmts", "String str=\"" + ruleStr + " epoch:\"+"+epochIdVar());
        code.add("ret", "return str");
        joinerTmpl.add("methodDecls", code);
    }

    void generateRemoteTableMethods() {
        genGetRemoteTables();
    }

    String nullifyRemoteTableMethod(Object suffix) {
        String method = "nullifyRemoteTable_";
        if (suffix != null) {
            method += suffix;
        }
        return method;
    }
    String getRemoteTableMethod(Object suffix) {
        String method = "getRemoteTable_";
        if (suffix != null)
            method += suffix;
        return method;
    }

    void genGetRemoteTables() {
        if (hasRemoteRuleBody()) {
            genGetRemoteBodyTable();
        }
        if (hasRemoteRuleHead()) {
            genGetRemoteHeadTable();
        }
    }

    void generateDeltaTableMethods() {
        genGetDeltaTableArray();
        genGetDeltaTable();
    }

    void genGetDeltaTableArray() {
        ST method = getNewMethodTmpl("getDeltaTables", "TableInst[]");
        joinerTmpl.add("methodDecls", method);
        genGetDeltaTableArrayReally(method);
    }

    void genGetDeltaTableArrayReally(ST method) {
        if (accumlDelta(rule)) {
            method.add("stmts", "return new TableInst[]{"+deltaTableReturnVar()+"}");
        } else {
            method.add("stmts", "return null");
        }
    }

    void genGetDeltaTable() {
        if (!accumlDelta(rule)) return;

        ST method = getNewMethodTmpl("getDeltaTable", deltaHeadT.className());
        joinerTmpl.add("methodDecls", method);

        genGetDeltaTableReally(method, deltaTableVar());
    }

    void genGetDeltaTableReally(ST method, String deltaVar) {
        genGetDeltaTableReallyWithPriority(method, deltaVar, deltaTableReturnVar(), "0");
    }

    void genGetDeltaTableReallyWithPriority(ST method, String deltaVar, String deltaRetVar, String priority) {
        ST if_ = tmplGroup.getInstanceOf("if");
        method.add("stmts", if_);
        if_.add("cond", deltaVar + "==null");
        String deltaCls = deltaHeadT.className();
        String alloc;
        if (hasPipelinedRules())
            alloc="("+deltaCls+")"+TmpTablePool_get()+"Small("+deltaCls+".class)";
        else alloc="("+deltaCls+")"+TmpTablePool_get()+"(getWorkerId(),"+deltaCls+".class,"+priority+")";

        if_.add("stmts", deltaVar+"="+alloc);
        if_.add("stmts", deltaRetVar+"="+deltaVar+".isEmpty()?"+deltaVar+":null");

        if_ = tmplGroup.getInstanceOf("if");
        method.add("stmts", if_);

        if_.add("cond", deltaVar+".vacancy()==0");
        if (hasPipelinedRules()) {
            addPipeliningCode(if_, deltaVar);
            if_.add("stmts", deltaVar+".clear()");
            if_.add("stmts", "TmpTablePool.free(getWorkerId(), "+deltaVar+")");
            if_.add("stmts", deltaVar+"="+alloc);
            if_.add("stmts", deltaRetVar+"="+deltaVar);
        } else {
            String cond= "if("+deltaRetVar+"!=null)";
            if_.add("stmts", cond+" getWorker().addTasksForDelta(getRuleId(),"+deltaVar+", "+priority+")");
            if_.add("stmts", deltaVar+"="+alloc);
            if_.add("stmts", deltaRetVar+"="+deltaVar+".isEmpty()?"+deltaVar+":null");
        }
        method.add("ret", "return " + deltaVar);
    }
    void addPipeliningCode(ST code, String deltaVar) {
        String deltaRulesArray=runtimeVar()+".getRuleMap(getRuleId()).getDeltaRules(getRuleId()).toArray()";
        ST forEachDepRule = tmplGroup.getInstanceOf("forEach");
        code.add("stmts", deltaVar+".setReuse(false)");

        code.add("stmts", forEachDepRule);
        forEachDepRule.add("set", deltaRulesArray);
        forEachDepRule.add("elem", "int _$pr");

        ST forEachVisitor = tmplGroup.getInstanceOf("forEach");
        forEachDepRule.add("stmts", forEachVisitor);
        String visitors = runtimeVar()+".getJoinerBuilder(_$pr).getNewJoinerInst(_$pr, new TableInst[]{"+deltaVar+"})";
        forEachVisitor.add("set", visitors);
        forEachVisitor.add("elem", "IVisitor _$v");
        forEachVisitor.add("stmts", "_$v.setWorker(getWorker())");
        forEachVisitor.add("stmts", "try { _$v.run(); } catch (SocialiteFinishEval e) {}");

        code.add("stmts", deltaVar+".setReuse(true)");
    }
    boolean hasPipelinedRules() {
        return rule.hasPipelinedRules();
    }

    boolean isRemoteHeadFirstT() {
        Predicate p = rule.firstP();
        Table t = tableMap.get(p.name());
        if (t instanceof RemoteHeadTable)
            return true;
        return false;
    }

    String remoteTable0MapKey(int tableId, String machineIdx) {
        return "((((long)" + machineIdx + ")<<30) |" + "(((long)" + tableId
                + ")<<1)" + "|0)";
    }

    String remoteTableMapKey(int tableId, String machineIdx) {
        return "((((long)" + machineIdx + ")<<30) |" + "(((long)" + tableId
                + ")<<1)" + "|1)";
    }

    String machineIdxFromKey(String longTypeKey) {
        return "(int)(" + longTypeKey + ">>>30)";
    }

    List<Integer> tableSendPos = null;

    List<Integer> tableTransferPos() {
        if (tableSendPos == null) {
            tableSendPos = Analysis.tableTransferPos(rule, tableMap);
        }
        return tableSendPos;
    }

    void genGetRemoteBodyTable() {
        for (int pos : tableTransferPos()) {
            RemoteBodyTable rt = (RemoteBodyTable)tableMap.get(RemoteBodyTable.name(rule, pos));
            String tableCls = rt.className();

            // generating get method
            ST method = getNewMethodTmpl(getRemoteTableMethod(pos), tableCls);
            joinerTmpl.add("methodDecls", method);
            method.add("comment", "generated by genGetRemoteBodyTable()");
            method.add("args", "int rangeOrHash");

            Predicate nextP = (Predicate) rule.getBody().get(pos);
            Table joinT = tableMap.get(nextP.name());
            String machineIdx = partitionMapVar() + ".machineIndexFor("+joinT.id()+", rangeOrHash)";
            method.add("stmts", "int _$machineIdx=" + machineIdx);
            method.add("stmts", tableCls+" _$remoteT="+remoteTableVar(pos)+"[_$machineIdx]");

            // alloc table
            ST if_= tmplGroup.getInstanceOf("if");
            method.add("stmts", if_);
            if_.add("cond", "_$remoteT==null");
            if_.add("stmts", "_$remoteT=("+tableCls+")"+TmpTablePool_get()+"(worker.id(),"+tableCls+".class)");
            if_.add("stmts", remoteTableVar(pos) + "[_$machineIdx]=_$remoteT");

            method.add("ret", "return _$remoteT");

            // generating nullify method
            method = getNewMethodTmpl(nullifyRemoteTableMethod(pos), "void");
            joinerTmpl.add("methodDecls", method);
            method.add("args", "int machineIdx");
            method.add("stmts", remoteTableVar(pos)+"[machineIdx]=null");
        }
    }

    RemoteHeadTable remoteHeadT() {
        Table t = headT;
        String name = RemoteHeadTable.name(t, rule);
        RemoteHeadTable rt = (RemoteHeadTable) tableMap.get(name);
        assert rt != null;
        return rt;
    }

    void getTableSize(ST code, String tableArrayVar, String sizeVar) {
        ST if_ = tmplGroup.getInstanceOf("if");
        code.add("stmts", if_);
        if_.add("cond", tableArrayVar + "!=null");

        ST for_ = tmplGroup.getInstanceOf("for");
        if_.add("stmts", for_);
        for_.add("init", "int i=0");
        for_.add("cond", "i<" + tableArrayVar + ".length");
        for_.add("inc", "i++");
        if_ = tmplGroup.getInstanceOf("if");
        if_.add("cond", tableArrayVar + "[i]!=null");
        for_.add("stmts", if_);
        if_.add("stmts", sizeVar + "+=" + tableArrayVar + "[i].size()");
    }

    void genGetRemoteHeadTable() {
        RemoteHeadTable rt = remoteHeadT();
        String tableCls = rt.className();

        ST method = getNewMethodTmpl(getRemoteTableMethod("head"), tableCls);
        joinerTmpl.add("methodDecls", method);
        method.add("args", "int _$rangeOrHash");
        genGetRemoteHeadTableReally(method, rt, tableCls);

        method = getNewMethodTmpl(nullifyRemoteTableMethod("head"), "void");
        joinerTmpl.add("methodDecls", method);
        method.add("args", "int _$machineIdx");
        genNullifyRemoteHeadTableReally(method, rt, tableCls);
    }

    void genGetRemoteHeadTableReally(ST method, RemoteHeadTable rt, String tableCls) {
        // generating getRemoteTable_head (int _$rangeOrHash)
        String machineIdx = partitionMapVar()+".machineIndexFor("+rt.origId()+", _$rangeOrHash)";
        method.add("stmts", "int _$machineIdx="+machineIdx);
        String getter = tableCls+" _$remoteT="+remoteTableVar("head")+"[_$machineIdx]";
        method.add("stmts", getter);

        ST if_ = tmplGroup.getInstanceOf("if");
        method.add("stmts", if_);
        if_.add("cond", "_$remoteT==null");
        if_.add("stmts", "_$remoteT=("+tableCls+")"+TmpTablePool_get()+"(worker.id(),"+tableCls+".class)");
        if_.add("stmts", remoteTableVar("head")+"[_$machineIdx]=_$remoteT");

        method.add("ret", "return _$remoteT");
    }

    String TmpTablePool_get() {
        if (rule.firstP()==null) return "TmpTablePool.get";

        Table t=tableMap.get(rule.firstP().name());
        if (t instanceof RemoteBodyTable || t instanceof RemoteHeadTable) {
            return "TmpTablePool.__get";
        } else if (rule instanceof DeltaRule) {
            return "TmpTablePool._get";
        } else {
            return "TmpTablePool.get";
        }
    }
    void genNullifyRemoteHeadTableReally(ST method, RemoteHeadTable rt, String tableCls) {
        // generating nullifyRemoteTable_head (int _$machineIdx)
        String getter = tableCls+" _$remoteT="+remoteTableVar("head")+"[_$machineIdx]";
        method.add("stmts", getter);
        method.add("stmts", "assert _$remoteT!=null");
        method.add("stmts", remoteTableVar("head")+"[_$machineIdx]=null");
    }

    DeltaPredicate getFirstPifDelta() {
        if (rule.firstP() instanceof DeltaPredicate)
            return (DeltaPredicate) rule.firstP();
        return null;
        /*
         * if (iterStartP==null) return null;
         *
         * if (iterStartP instanceof DeltaPredicate) return
         * (DeltaPredicate)iterStartP; return null;
         */
    }

    void maybeSendToRemoteHead(ST code, String table,
                               String machineIdx, Object idxParam) {
        ST if_ = tmplGroup.getInstanceOf("if");
        code.add("stmts", if_);
        if_.add("cond", table+".vacancy()==0");

        ST send = if_;
        String partitionIdx="0";
        sendToRemoteHead(send, table, partitionIdx, machineIdx);
    }

    void sendToRemoteHead(ST code, String table, String partitionIdx, String machineIdx) {
        code.add("stmts", "SRuntime _$rt=SRuntimeWorker.getInst()");
        code.add("stmts", "RuleMap _$rm=_$rt.getRuleMap(getRuleId())");
        code.add("stmts", "int _$depRuleId=_$rm.getRemoteHeadDep(getRuleId())");
        code.add("stmts", "EvalWithTable _$cmd=new EvalWithTable("+epochIdVar()+", _$depRuleId,"+table+","+partitionIdx+")");
        String send = "boolean _$reuse = _$rt.sender().send("+machineIdx+","+"_$cmd)";
        code.add("stmts", send);
        code.add("stmts", "if(!_$reuse)"+nullifyRemoteTableMethod("head")+"("+machineIdx+")");
    }

    void maybeSendToRemoteBody(ST code, String table, Predicate joinP) {
        Table t = tableMap.get(joinP.name());
        ST if_= tmplGroup.getInstanceOf("if");
        code.add("stmts", if_);
        if_.add("cond", table+".size()=="+table+".capacity()");

        ST send = if_;
        String machineIdx = partitionMapVar()+".machineIndexFor("+t.id()+","+joinP.first()+")";
        sendToRemoteBody(send, table, machineIdx, ""+joinP.getPos());
    }
    void maybeBroadcastToRemoteBody(ST code, String table, Predicate joinP) {
        Table t = tableMap.get(joinP.name());
        ST if_= tmplGroup.getInstanceOf("if");
        code.add("stmts", if_);
        if_.add("cond", table+".vacancy()==0");

        ST send = if_;
        broadcastToRemoteBody(send, table, ""+joinP.getPos());
    }

    void broadcastToRemoteBody(ST code, String table, String pos) {
        code.add("stmts", "SRuntime _$rt=SRuntimeWorker.getInst()");
        code.add("stmts", "RuleMap _$rm=_$rt.getRuleMap(getRuleId())");
        code.add("stmts", "int _$depRuleId=_$rm.getRemoteBodyDep(getRuleId(),"+pos+")");
        code.add("stmts", "EvalWithTable _$cmd=new EvalWithTable("+epochIdVar()+",_$depRuleId,"+table+",0)");
        String send = "boolean _$reuse = _$rt.sender().send(-1, _$cmd)";
        code.add("stmts", send);
        code.add("stmts", "if(!_$reuse)"+nullifyRemoteTableMethod(pos)+"(0)");
    }
    void sendToRemoteBody(ST code, String table, String machineIdx, String pos) {
        code.add("stmts", "SRuntime _$rt=SRuntimeWorker.getInst()");
        code.add("stmts", "RuleMap _$rm=_$rt.getRuleMap(getRuleId())");
        code.add("stmts", "int _$depRuleId=_$rm.getRemoteBodyDep(getRuleId(),"+pos+")");
        code.add("stmts", "EvalWithTable _$cmd=new EvalWithTable("+epochIdVar()+",_$depRuleId,"+table+",0)");
        String send = "boolean _$reuse = _$rt.sender().send("+machineIdx+", _$cmd)";
        code.add("stmts", send);
        code.add("stmts", "if(!_$reuse)"+nullifyRemoteTableMethod(pos)+"("+machineIdx+")");
    }

    void generateIdGetters() {
        ST getter = getNewMethodTmpl("getRuleId", "int");
        getter.add("stmts", returnStmt(ruleIdVar()));
        joinerTmpl.add("methodDecls", getter);

        getter = getNewMethodTmpl("getEpochId", "int");
        getter.add("stmts", returnStmt(epochIdVar()));
        joinerTmpl.add("methodDecls", getter);
    }

    String invokeCombinedIterate(Predicate p1, Predicate p2) {
        String invoke = "combined_iterate(";
        Table t1 = getTable(p1);
        String v1 = getVarName(p1);
        String v2 = getVarName(p2);
        if (p1.getPos() == 0) {
            v1 += "["+ myPartitionIdx()+"]";
            v2 += "["+ myPartitionIdx()+"]";
            invoke += concatCommaSeparated(v1, v2)+")";
            return invoke;
        } else {
            // this should be impossible, because we should have reordered the rule
            // such that the combined-iteration comes at the beginning.
            ST for_ = tmplGroup.getInstanceOf("for");
            for_.add("init", "int _$i=0");
            for_.add("cond", "_$i<"+partitionMapVar()+".partitionNum("+t1.id()+")");
            for_.add("inc", "_$i++");
            v1 += "[_$i]";
            v2 += "[_$i]";
            invoke += concatCommaSeparated(v1, v2)+")";
            for_.add("stmts", invoke);
            return for_.render();
        }

    }

    void generateRunMethod() {
        ST run = getNewMethodTmpl("run", "void");
        joinerTmpl.add("methodDecls", run);
        run.add("stmts", "_run()");

        ST _run = getNewMethodTmpl("_run", "boolean");
        joinerTmpl.add("methodDecls", _run);
        _run.add("ret", "return true");

        ST code = _run;
        for (Object o : rule.getBody()) {
            if (o instanceof Expr) {
                code = insertExprCode(code, (Expr) o);
            } else if (o instanceof Predicate) {
                Predicate p = (Predicate) o;
                if (allVarsResolved(p)) {
                    code = returnIfNotContains(code, p);
                } else if (canCombineIterate(p)) {
                    Predicate p2 = (Predicate)rule.getBody().get(p.getPos()+1);
                    genCombinedIterate(p, p2);
                    code.add("stmts", invokeCombinedIterate(p, p2));
                    iterStartP = p;
                    break;
                } else {
                    Table t = getTable(p);
                    int shardCol = t.getPartitionColumn();
                    String tableVar = getVarName(p);
                    if (isTablePartitioned(t)) {
                        if (t.equals(rule.getPartitionTable())) {
                            tableVar += "[" + myPartitionIdx() + "]";
                        } else if (isResolved(p, p.paramAt(shardCol))) {
                            tableVar += "[" + partitionIdx(t, p.paramAt(shardCol)) + "]";
                        } else {
                            ST for_ = forVarUpto("_$i", call(partitionMapVar(), "partitionNum", t.id()));
                            code.add("stmts", for_);
                            code = for_;
                            tableVar += "[_$i]";
                        }
                    }
                    iterStartP = p;
                    insertIterateCodeInRun(code, tableVar);
                    break;
                }
            } else { assert false:"Expecting Expr or Predicate, but got:"+o; }
        }
        if (iterStartP == null) {
            insertUpdateAccumlOrPipelining(code);
        }
        genRunMethodFini(run);
    }

    void genRunMethodFini(ST code) {
        finishSendRemoteTablesIfAny(code);
        freeRemoteTables(code);
    }

    void freeRemoteTables(ST code) {
        if (hasRemoteRuleHead()) {
            code.add("stmts", "TmpTablePool.free(worker.id(),"+remoteTableVar("head")+")");
        }
        if (hasRemoteRuleBody()) {
            for (int pos : tableTransferPos()) {
                code.add("stmts", "TmpTablePool.free(worker.id(),"+remoteTableVar(pos)+")");
            }
        }
    }

    void finishSendRemoteRuleHead(ST code) {
        ST for_ = tmplGroup.getInstanceOf("for");
        code.add("stmts", for_);
        // going over machines (_$i for machine index)
        for_.add("init", "int _$i=0");
        for_.add("cond", "_$i<" + remoteTableVar("head") + ".length");
        for_.add("inc", "_$i++");
        for_.add("stmts", "TmpTableInst _$t=" + remoteTableVar("head") + "[_$i]");
        ST if_ = tmplGroup.getInstanceOf("if");
        for_.add("stmts", if_);
        if_.add("cond", "_$t!=null && _$t.size()>0");
        sendToRemoteHead(if_, "_$t", "0", "_$i");
    }

    void finishSendRemoteRuleBody(ST code) {
        for (int pos: tableTransferPos()) {
            ST for_ = tmplGroup.getInstanceOf("for");
            code.add("stmts", for_);
            Predicate p=(Predicate)rule.getBody().get(pos);
            if (requireBroadcast(p)) {
                for_.add("init", "int _$i=0");
                for_.add("cond", "_$i<1");
                for_.add("inc", "_$i++");
                for_.add("stmts", "if ("+remoteTableVar(pos)+"[0]==null) continue");
                broadcastToRemoteBody(for_, remoteTableVar(pos)+"[0]", ""+pos);
            } else {
                // going over machines (_$i for machine-index)
                for_.add("init", "int _$i=0");
                for_.add("cond", "_$i<" + remoteTableVar(pos) + ".length");
                for_.add("inc", "_$i++");

                //String tableCls = tableMap.get(RemoteBodyTable.name(rule, pos)).className();
                for_.add("stmts", /*tableCls +*/"TmpTableInst _$t=" + remoteTableVar(pos) + "[_$i]");
                ST if_ = tmplGroup.getInstanceOf("if");
                for_.add("stmts", if_);
                if_.add("cond", "_$t!=null && _$t.size()>0");
                sendToRemoteBody(if_, "_$t", "_$i", "" + pos);
            }
        }
    }

    void finishSendRemoteTablesIfAny(ST code) {
        if (hasRemoteRuleHead()) {
            finishSendRemoteRuleHead(code);
        }
        if (hasRemoteRuleBody()) {
            finishSendRemoteRuleBody(code);
        }
    }

    void insertIterateCodeInRun(ST run, String tableVar) {
        ST code = run;

        String invokeIter;
        Set<Variable> resolvedVars = resolvedVarsArray[iterStartP.getPos()];
        if (updateFromRemoteHeadT()) {
            invokeIter = call(tableVar, "iterate", visitorVar(rule.firstP()));
            code.add("stmts", invokeIter);
        } else {
            invokeIter = tableVar+invokeIterate(iterStartP, resolvedVars);
            code.add("stmts", invokeIter);
        }
    }

    ArrayList<Column> getResolvedIndexCols(Predicate p) {
        Table t = getTable(p);
        Object[] params = p.inputParams();
        ArrayList<Column> idxCols = new ArrayList<>();
        Set<Variable> resolvedVars = resolvedVarsArray[p.getPos()];
        for (int i=0; i<params.length; i++) {
            if (isConstOrResolved(resolvedVars, params[i])) {
                if (t.getColumn(i).isIndexed()) {
                    idxCols.add(t.getColumn(i));
                } else if (t.getColumn(i).isSorted()) {
                    idxCols.add(t.getColumn(i));
                }
            }
        }
        return idxCols;
    }

    boolean isIndexColResolved(Predicate p) {
        if (getResolvedIndexCols(p).size()==0) {
            return false;
        }
        return true;
    }

    boolean isConstOrResolved(Set<Variable> resolvedVars, Object varOrConst) {
        if (varOrConst instanceof Variable) {
            Variable v = (Variable) varOrConst;
            return resolvedVars.contains(v);
        }
        return true;
    }

    boolean isDontCare(Param param) {
        if (param instanceof Variable) {
            return (((Variable)param).dontCare);
        }
        return false;
    }

    boolean allVarsResolved(Predicate p) {
        Set<Variable> resolvedVars = resolvedVarsArray[p.getPos()];
        for (Object param : p.inputParams()) {
            if (!isConstOrResolved(resolvedVars, param)) {
                return false;
            }
        }
        return true;
    }

    int getPosInParams(Predicate p, Variable v) {
        Object params[] = p.inputParams();
        for (int i = 0; i < params.length; i++) {
            if (params[i].equals(v))
                return i;
        }
        return -1;
    }

    Column getSortedColumn(Predicate p, Variable v) {
        Table t = getTable(p);
        int pos = getPosInParams(p, v);
        if (pos >= 0) {
            Column c = t.getColumn(pos);
            if (c.isSorted()) { return c; }
        }
        return null;
    }

    boolean isSortedColumn(Predicate p, Variable v) {
        return getSortedColumn(p, v) != null;
    }

    CmpOp.CmpType cmpTypeForIteratePart(Predicate p) {
        Object next = rule.getBody().get(p.getPos() + 1);
        Op op = ((Expr) next).root;
        CmpOp cmpOp = (CmpOp) op;

        Object lhs = cmpOp.getLHS();
        Object rhs = cmpOp.getRHS();

        Object val = cmpValForIteratePart(p);
        if (val.equals(rhs))
            return cmpOp.cmpType();
        else
            return cmpOp.cmpType().reverse();
    }
    Object cmpValForIteratePart(Predicate p) {
        Object next = rule.getBody().get(p.getPos() + 1);
        Op op = ((Expr) next).root;
        CmpOp cmpOp = (CmpOp) op;

        Object lhs = cmpOp.getLHS();
        Object rhs = cmpOp.getRHS();
        int sortCol = idxForIteratePart(p);
        Variable v = (Variable) p.inputParams()[sortCol];
        if (v.equals(lhs)) { return rhs; }
        else { return lhs; }
    }

    int idxForIteratePart(Predicate p) {
        Object next = rule.getBody().get(p.getPos() + 1);
        Op op = ((Expr) next).root;
        CmpOp cmpOp = (CmpOp) op;

        Object lhs = cmpOp.getLHS();
        Object rhs = cmpOp.getRHS();
        if (lhs instanceof Variable) {
            Variable v = (Variable) lhs;
            Column c = getSortedColumn(p, v);
            if (c != null && c.isSorted())
                return c.getAbsPos();
        }
        if (rhs instanceof Variable) {
            Variable v = (Variable) rhs;
            Column c = getSortedColumn(p, v);
            assert (c != null && c.isSorted());
            return c.getAbsPos();
        }
        Assert.impossible("JoinerCodeGen.idxForIteratePart(): Should not reach here!");
        return -1;
    }

    boolean hasCmpNext(Predicate p) {
        if (getNextCmpOp(p)==null) return false;
        return true;
    }
    CmpOp getNextCmpOp(Predicate p) {
        if (p.getPos() == rule.getBody().size() - 1)
            return null;
        Object next = rule.getBody().get(p.getPos() + 1);
        if (!(next instanceof Expr))
            return null;
        Op op = ((Expr) next).root;
        if (!(op instanceof CmpOp))
            return null;
        return (CmpOp)op;
    }

    int getNestingLevel(Predicate p, int col) {
        Table t = getTable(p);
        int level=0;
        for (int pos:t.nestPos()) {
            if (pos <= col)
                level++;
        }
        return level;
    }
    int getNestingLevel(Predicate p, Column c) {
        return getNestingLevel(p, c.position());
    }
    boolean iteratePart(Predicate p, Set<Variable> resolved) {
        if (!hasCmpNext(p)) return false;

        CmpOp cmpOp = getNextCmpOp(p);
        if (cmpOp.getOp().equals("!="))
            return false; // not worth extra effort, so don't optimize for this.
        if (cmpOp.getOp().equals("=="))
            return false; // should be handled by iterate_by

        Object lhs = cmpOp.getLHS();
        Object rhs = cmpOp.getRHS();
        if (lhs instanceof Variable) {
            Variable v = (Variable) lhs;
            return canBinarySearch(p, v, rhs, resolved);
        }
        if (rhs instanceof Variable) {
            Variable v = (Variable) rhs;
            return canBinarySearch(p, v, lhs, resolved);
        }
        return false;
    }
    boolean canBinarySearch(Predicate p, Variable v, Object cmp, Set<Variable> resolved) {
        if (!isSortedColumn(p, v)) { return false; }
        Column sortedCol = getSortedColumn(p, v);
        if (getNestingLevel(p, sortedCol) >= 2) {
            // nesting level too deep, see table code templates (e.g. DynamicNestedTable.stg).
            return false;
        }
        return isConstOrResolved(resolved, cmp) || isInPrevColGroup(p, v, cmp);
    }

    boolean isInPrevColGroup(Predicate p, Variable v, Object param) {
        Table t = getTable(p);
        List<ColumnGroup> colGroups = t.getColumnGroups();
        int colGroupIdxOfV = -1;
        int colGroupIdxOfParam = -1;
        for (ColumnGroup cg : colGroups) {
            for (int i = cg.startIdx(); i <= cg.endIdx(); i++) {
                if (p.inputParams()[i].equals(v))
                    colGroupIdxOfV = cg.startIdx();
                if (p.inputParams()[i].equals(param))
                    colGroupIdxOfParam = cg.startIdx();
            }
        }
        assert (colGroupIdxOfV >= 0 && colGroupIdxOfParam >= 0);
        if (colGroupIdxOfParam < colGroupIdxOfV)
            return true;
        return false;
    }

    Column getSortedColumn(Table t) {
        for (Column c:t.getColumns()) {
            if (c.isSorted()) {
                return c;
            }
        }
        return null;
    }
    Variable getSortedColumnVar(Predicate p, Table t) {
        Column sortedCol = getSortedColumn(t);
        if (sortedCol == null) { return null; }
        Param param = p.inputParams()[sortedCol.getAbsPos()];
        if (param instanceof Variable) {
            return (Variable)param;
        } else {
            return null;
        }
    }

    boolean canCombineIterate(Predicate p) {
        // Returns true if p and the next predicate can be iterated in a lock-step.
        if (true) {return false; }

        int nextPos = p.getPos() + 1;
        List body = rule.getBody();
        if (nextPos >= body.size()) { return false; }

        Object next = body.get(nextPos);
        if (!(next instanceof Predicate)) { return false; }
        Predicate nextP = (Predicate) next;

        Table t1 = getTable(p);
        Table t2 = getTable(nextP);
        return CombinedIteratorGen.canCombineIterate(t1, t2, p, nextP);
    }

    void genCombinedIterate(Predicate p1, Predicate p2) {
        Table t1 = getTable(p1);
        Table t2 = getTable(p2);
        String m = CombinedIteratorGen.generate(t1, t2, visitorVar(p1), visitorVar(p2));
        joinerTmpl.add("methodDecls", m);
    }

    void generateVisitMethods() {
        if (iterStartP == null) return;

        Set<Variable>[] resolved = resolvedVarsArray;
        ST code = null;

        List body = rule.getBody();
        Predicate prevp = null;
        for (int i = iterStartP.getPos(); i < body.size(); i++) {
            Object o = body.get(i);
            if (o instanceof Predicate) {
                Predicate p = (Predicate) o;
                if (allVarsResolved(p)) {
                    code = returnIfNotContains(code, (Predicate) o);
                } else if (canCombineIterate(p)) {
                    Predicate p2 = (Predicate) body.get(i+1);
                    if (p != iterStartP) {
                        genCombinedIterate(p, p2);
                    }
                    genVisitMethodFor(p, resolved[i]);
                    i++;
                    code = genVisitMethodFor(p2, resolved[i]);
              } else {
                    boolean added = insertContinuationCode(code, prevp, p, resolved[i]);
                    code = genVisitMethodFor(p, resolved[i]);
                    prevp = p;
                    if (added && iteratePart(p, resolved[i])) {
                        i++;
                    }
                }
            } else if (o instanceof Expr) {
                Expr e = (Expr) o;
                assert (code != null);
                code = insertExprCode(code, e);
            } else
                Assert.impossible();
        }
        insertUpdateAccumlOrPipelining(code);
    }

    boolean hasRemoteRuleBody() {
        if (!isDistributed()) return false;
        return Analysis.hasRemoteRuleBody(rule, tableMap);
    }

    boolean hasRemoteRuleHead() {
        if (!isDistributed()) return false;
        return Analysis.hasRemoteRuleHead(rule, tableMap);
    }

    void insertUpdateAccumlOrPipelining(ST code) {
        code.add("stmts", decl("boolean", init(isUpdatedVar(), "false")));
        if (hasRemoteRuleHead()) {
            ST ifLocal = genAccumlRemoteHeadTable(code);
            code = ifLocal;
        }
        ST ifUpdated = genUpdateCode(code);
        code = ifUpdated;
        genAccumlDeltaIfAny(code);
    }

    boolean isInt(Object o) { return MyType.javaType(o).equals(int.class); }
    String getRemoteBodyTable(int pos, Object rangeOrHash) {
        Object param = isInt(rangeOrHash)? rangeOrHash:"HashCode.get("+rangeOrHash+")";
        return getRemoteTableMethod(pos)+"("+param+")";
    }

    String getRemoteHeadTable(Object machineIdx) {
        Object param = isInt(machineIdx)? machineIdx:"HashCode.get("+machineIdx+")";
        return getRemoteTableMethod("head")+"("+param+")";
    }

    ST genAccumlRemoteHeadTable(ST code) {
        Table t = headT;
        ST ifLocalElse = tmplGroup.getInstanceOf("ifElse");
        code.add("stmts", ifLocalElse);
        ifLocalElse.add("cond", partitionMapVar()+".isLocal("+t.id()+","+headP.first()+")");
        ST ifLocal = tmplGroup.getInstanceOf("simpleStmts");
        ifLocalElse.add("stmts", ifLocal);

        RemoteHeadTable rt = remoteHeadT();
        String tableCls = rt.className();
        ifLocalElse.add("elseStmts", tableCls + " _$remoteT");
        ifLocalElse.add("elseStmts", "int _$machineIdx=" + partitionMapVar()+
                            ".machineIndexFor("+rt.origId()+","+headP.first()+")");
        ifLocalElse.add("elseStmts", "_$remoteT="+getRemoteHeadTable(headP.first()));

        ST stmts = tmplGroup.getInstanceOf("simpleStmts");
        ifLocalElse.add("elseStmts", stmts);

        stmts.add("stmts", call("_$remoteT", "insert", headP.inputParams()));

        ST maybeSend = tmplGroup.getInstanceOf("simpleStmts");
        ifLocalElse.add("elseStmts", maybeSend);
        maybeSendToRemoteHead(maybeSend, "_$remoteT", "_$machineIdx", headP.first());
        return ifLocal;
    }

    boolean requireBroadcast(Predicate p) {
        if (p instanceof PrivPredicate) { return false; }
        if (isResolved(p, p.first())) {
            return false;
        } else {
            return true;
        }
    }
    ST genAccumlRemoteBodyTable(ST code, Predicate p) {
        assert tableTransferPos().contains(p.getPos());

        if (requireBroadcast(p)) {
            return genBroadcastAccumlRemoteBodyTable(code, p);
        }
        Table joinT = tableMap.get(p.name());
        ST ifElse = tmplGroup.getInstanceOf("ifElse");
        code.add("stmts", ifElse);
        ifElse.add("cond", partitionMapVar()+".isLocal(" + joinT.id()+", "+p.first() + ")");
        ST ifLocal = tmplGroup.getInstanceOf("simpleStmts");
        ifElse.add("stmts", ifLocal);

        RemoteBodyTable rt = (RemoteBodyTable)tableMap.get(RemoteBodyTable.name(rule, p.getPos()));
        String tableCls = rt.className();
        ifElse.add("elseStmts", tableCls+" _$remoteT="+getRemoteBodyTable(p.getPos(), p.first()));
        ifElse.add("elseStmts", call("_$remoteT", "insert", rt.getParamVars()));

        ST maybeSend = tmplGroup.getInstanceOf("simpleStmts");
        ifElse.add("elseStmts", maybeSend);
        maybeSendToRemoteBody(maybeSend, "_$remoteT", p);
        return ifLocal;
    }
    ST genBroadcastAccumlRemoteBodyTable(ST code, Predicate p) {
        RemoteBodyTable rt = (RemoteBodyTable)tableMap.get(RemoteBodyTable.name(rule, p.getPos()));
        String tableCls = rt.className();
        code.add("stmts", tableCls+" _$remoteT="+getRemoteBodyTable(p.getPos(), "0"));
        code.add("stmts", call("_$remoteT", "insert", rt.getParamVars()));
        ST maybeSend = tmplGroup.getInstanceOf("simpleStmts");
        code.add("stmts", maybeSend);
        maybeBroadcastToRemoteBody(maybeSend, "_$remoteT", p);

        ST ifLocal = tmplGroup.getInstanceOf("simpleStmts");
        code.add("stmts", ifLocal);
        return ifLocal;
    }

    String toArgs(List params) {
        String args="";
        for (int i=0; i<params.size(); i++) {
            args += params.get(i);
            if (i!=params.size()-1) args += ", ";
        }
        return args;
    }
    void genAccumlDeltaIfAny(ST code) {
        if (!accumlDelta(rule)) { return; }

        String deltaTable = "getDeltaTable()";
        code.add("stmts", updateHeadParamsTo(deltaTable));
    }

    String headTablePartition() {
        if (!isTablePartitioned(headT)) { return ""; }

        int shardCol = headT.getPartitionColumn();
        if (updateFromRemoteHeadT()) {
                return "["+ partitionIdx(headT, headP.paramAt(shardCol))+"]";
        } else if (Analysis.updateParallelShard(rule, tableMap)) {
                return "["+ myPartitionIdx()+"]";
        } else {
                return "["+ partitionIdx(headT, headP.paramAt(shardCol))+"]";
        }
    }

    // returns place-holder for if-updated code
    ST genUpdateCode(ST code) {
        ST ifUpdated = tmplGroup.getInstanceOf("simpleStmts");
        if (headT instanceof DeltaTable) {
            // For some starting rules in SCC, like DeltaFoo(a,b) :- Foo(a,b). , where Foo is in SCC
            // do nothing here, and set updatedVar true, so that delta table is updated
            code.add("stmts", isUpdatedVar()+"=true");
            return code;
        }

        String _headTable = "_$"+headTableVar();
        code.add("stmts", headT.className()+" "+_headTable+"="+headTableVar() + headTablePartition());

        ST prevCode=code;
        if (headP.hasFunctionParam()) {
            genAggrCode(code, _headTable, ifUpdated);
        } else {
            genInsertCode(code, _headTable, ifUpdated);
        }

        ST if_ = tmplGroup.getInstanceOf("if");
        if_.add("cond", isUpdatedVar());
        if_.add("stmts", ifUpdated);
        prevCode.add("stmts", if_);
        return ifUpdated;
    }

    void genAggrCode(ST code, String headTableVar, ST ifUpdated) {
        List<AggrFunction> funcs = headP.getAggrFuncs();
        String gbupdate = groupbyUpdate(headTableVar, funcs);
        code.add("stmts", isUpdatedVar()+"=" + gbupdate);
        ST if_ = tmplGroup.getInstanceOf("if");
        if_.add("cond", isUpdatedVar());
        if_.add("stmts", ifUpdated);
    }

    String groupbyUpdate(String tablePartition, List<AggrFunction> funcs) {
        int numAggrColumn = headT.numColumns() - headT.groupbyColNum();
        String[] suffixArgs = new String[numAggrColumn];
        int i=0;
        for (AggrFunction f:funcs) {
            suffixArgs[i++] = call(f.className(),"get");
        }
        if (i < numAggrColumn) { suffixArgs[i++] = "null"; }

        return call(tablePartition, "groupby_update", makeHeadParamsArgs(suffixArgs));
    }

    String updateHeadParamsTo(String tablePartition) {
        return call(tablePartition, "update", makeHeadParamsArgs());
    }

    List<Object> headParams() {
        List<Object> params = new ArrayList<>();
        for (Param p:headP.params) {
            if (p instanceof Function) {
                Function f = (Function)p;
                for (Param a:f.getArgs()) {
                    params.add(a);
                }
            } else { params.add(p); }
        }
        return params;
    }
    String makeHeadParamsArgs() {
        return makeArgs(headParams());
    }
    String makeHeadParamsArgs(String... suffixArgs) {
        List<Object> args = headParams();
        for(String a:suffixArgs) {
            args.add(a);
        }
        return makeArgs(args);
    }

    void genInsertCode(ST code, String headTableVar, ST ifUpdated) {
        code.add("stmts", isUpdatedVar()+"=" + call(headTableVar, "insert", makeHeadParamsArgs()));
        ST if_ = tmplGroup.getInstanceOf("if");
        if_.add("cond", isUpdatedVar());
        if_.add("stmts", ifUpdated);
    }

    String partitionIdx(Table t, Object val) {
        if (isSequential()) return "0";

        return call(partitionMapVar(), "getIndex", t.id(), val);
    }

    ST handleErrorFor(AssignOp assign, ST code) {
        assert assign.fromFunction();
        Function f = (Function)assign.arg2;

        ST tryCatch = tmplGroup.getInstanceOf("tryCatch");
        code.add("stmts", tryCatch);

        tryCatch.add("errorVar", "_$e");
        String _throw="if(_$e instanceof SociaLiteException) throw (SociaLiteException)_$e";
        tryCatch.add("catchStmts", _throw);

        String msg = "\"Error while invoking $"+f.name()+"(\"";
        for (int i=0; i<f.getArgs().size(); i++) {
            if (i!=0) msg += "+\",\"";
            Object a = f.getArgs().get(i);
            if (MyType.javaType(a).equals(String.class))
                a = "\"\\\"\"+" + a + "+\"\\\"\"";
            msg += "+"+a;
        }
        msg += "+\"), \""+"+_$e";
        msg += "+\"\"";
        tryCatch.add("catchStmts", "VisitorImpl.L.error(ExceptionUtils.getStackTrace(_$e))");
        _throw="throw new SociaLiteException("+msg+"+\", \"+_$e)";
        tryCatch.add("catchStmts", _throw);
        return tryCatch;
    }
    ST insertExprCode(ST code, Expr expr) {
        if (expr.root instanceof AssignOp) {
            AssignOp assign = (AssignOp) expr.root;
            if (assign.fromFunction()) {
                code = handleErrorFor(assign, code);
            }
            ST code2 = assign.codegen();
            code.add("stmts", code2);

            if (assign.multiRows())
                code = code2; // returning body of an iterator-loop
        } else {
            ST if_  = tmplGroup.getInstanceOf("if");
            if_.add("cond", "!" + expr.codegen().render());
            if_.add("stmts", "return true");
            code.add("stmts", if_);
        }
        return code;
    }

    boolean isSequential() {
        return ClusterConf.get().getNumWorkerThreads() == 1;
    }

    boolean updateFromRemoteHeadT() {
        if (rule.getBodyP().size() == 1) {
            Table onlyT = tableMap.get(rule.firstP().name());
            if (onlyT instanceof RemoteHeadTable) {
                return true;
            }
        }
        return false;
    }

    String concatCommaSeparated(Object ...args) {
        String result = "";
        for (int i=0; i<args.length; i++) {
            result += args[i];
            if (i!=args.length-1) { result += ","; }
        }
        return result;
    }

    String negInf(Class type) {
        if (MyType.javaType(type).equals(int.class)) {
            return ""+Integer.MIN_VALUE;
        } else if (MyType.javaType(type).equals(long.class)) {
            return ""+Long.MIN_VALUE;
        } else if (MyType.javaType(type).equals(float.class)) {
            return ""+Float.MIN_VALUE;
        } else if (MyType.javaType(type).equals(double.class)) {
            return ""+Double.MIN_VALUE;
        } else {
            return "null";
        }
    }
    String posInf(Class type) {
        if (MyType.javaType(type).equals(int.class)) {
            return ""+Integer.MAX_VALUE;
        } else if (MyType.javaType(type).equals(long.class)) {
            return ""+Long.MAX_VALUE;
        } else if (MyType.javaType(type).equals(float.class)) {
            return ""+Float.MAX_VALUE;
        } else if (MyType.javaType(type).equals(double.class)) {
            return ""+Double.MAX_VALUE;
        } else {
            return "null";
        }
    }
    String invokeIterate(Predicate p, Set<Variable> resolvedVars) {
        String invokeIterate;
        String visitor = visitorVar(p);
        final String constraint = constraintVar(p);
        if (iteratePart(p, resolvedVars)) {
            int sortedCol = idxForIteratePart(p);
            Column col = getTable(p).getColumn(sortedCol);
            CmpOp.CmpType cmpType = cmpTypeForIteratePart(p);
            Object cmpVal = cmpValForIteratePart(p);

            String range;
            if (cmpType.greaterThan()) {
                range = call(constraint, "setRange", sortedCol, cmpVal, posInf(col.type()));
            } else {
                range = call(constraint, "setRange", sortedCol, negInf(col.type()), cmpVal);
            }
            invokeIterate = ".iterate_by("+range+", "+visitor+")";
        } else if (isIndexColResolved(p)) {
            String buildConstr = constraint;
            Table t = getTable(p);
            Object[] params = p.inputParams();
            for (int i=0; i<params.length; i++) {
                if (isConstOrResolved(resolvedVars, params[i])) {
                    buildConstr = call(buildConstr, "set", i, params[i]);
                }
            }
            invokeIterate = ".iterate_by("+buildConstr+","+visitor+")";
        } else {
            invokeIterate = ".iterate("+visitor+")";
        }
        return invokeIterate;
    }

    // returns true if continuation inserted
    boolean insertContinuationCode(ST code, Predicate prevp, Predicate p,
            Set<Variable> resolved) {
        if (code == null) {
            assert (prevp == null);
            return false;
        }
        if (hasRemoteRuleBody() && tableTransferPos().contains(p.getPos())) {
            ST ifLocal = genAccumlRemoteBodyTable(code, p);
            code = ifLocal;
        }

        Table t = getTable(p);
        String invokeIterate = invokeIterate(p, resolved);

        String tableVar = getVarName(p);
        if (isTablePartitioned(t)) {
            int shardCol = t.getPartitionColumn();
            Param shardParam = p.inputParams()[shardCol];
            boolean shardSelected = Analysis.isResolved(resolvedVarsArray, p, shardParam);
            if (t.equals(rule.getPartitionTable())) {
                tableVar += arrayIdx(myPartitionIdx());
            } else if (shardSelected) {
                tableVar += arrayIdx(partitionIdx(t, shardParam));
            } else {
                ST for_ = forVarUpto("_$$i", call(partitionMapVar(), "partitionNum", t.id()));
                code.add("stmts", for_);
                tableVar += arrayIdx("_$$i");
                code = for_;
            }
        }
        code.add("stmts", tableVar+invokeIterate);

        return true;
    }

    ST returnIfNotContains(ST code, Predicate p) {
        // p.isNegated() is used to generate negated filter
        return ifNotContains(code, p, "return true");
    }
    ST ifNotContains(ST code, Predicate p, String actionStmt) {
        if (hasRemoteRuleBody() && tableTransferPos().contains(p.getPos())) {
            ST ifLocal = genAccumlRemoteBodyTable(code, p);
            code = ifLocal;
        }

        Table t = getTable(p);
        String var = getVarName(p);
        String condition="";
        if (isTablePartitioned(t)) {
            if (t instanceof  ArrayTable) {
                condition = "!" + call(partitionMapVar(), "isValidRange", t.id(), p.first()) + "||";
            }
            var += arrayIdx(partitionIdx(t, p.first()));
            if (p.isNegated()) {
                condition += call(var, "contains", makeArgs(p.inputParams()));
            } else {
                condition += "!" + call(var, "contains", makeArgs(p.inputParams()));
            }
        } else {
            if (p.isNegated()) {
                condition = call(var, "contains", makeArgs(p.inputParams()));
            } else {
                condition = "!" + call(var, "contains", makeArgs(p.inputParams()));
            }
        }

        ST if_ = CodeGenBase.getVisitorST("if");
        code.add("stmts", if_);
        if_.add("cond", condition);
        if_.add("stmts", actionStmt);
        return code;
    }

    ST genVisitMethodFor(Predicate p, Set<Variable> resolved) {
        Table t = getTable(p);
        List<ColumnGroup> columnGroups = t.getColumnGroups();
        ST m = null;
        int numColumns = t.numColumns();
        for (ColumnGroup g : columnGroups) {
            int startCol = g.startIdx();
            int endCol = g.endIdx();

            m = getVisitMethod(p, startCol, endCol, numColumns);
            if (areAllParamsDontcare(p.inputParams(), g.startIdx(), numColumns-1)) {
                m.add("ret", "return false");// stop iteration after the visit method m.
                break;
            } else {
                CodeGenBase.fillVisitMethodBody(m, p, startCol, endCol, resolved);
            }
        }
        return m;
    }

    boolean isResolved(Predicate p, Param param) {
        Set<Variable> resolved = resolvedVarsArray[p.getPos()];
        if (param instanceof Variable) {
            return resolved.contains(param);
        }
        return true;
    }

    boolean areAllParamsDontcare(Param[] params, int startidx, int endidx) {
        if (startidx > endidx) {
            return false;
        }
        for (int i = startidx; i <= endidx; i++) {
            Param p = params[i];
            if (!isDontCare(p)) {
                return false;
            }
        }
        return true;
    }

    boolean isTablePartitioned(Table t) {
        return !(t instanceof GeneratedT);
    }

    public static String getVarName(String table) {
        String firstLetter = table.substring(0, 1);
        String rest = table.substring(1);
        return "_" + firstLetter.toLowerCase() + rest;
    }

    static String joinerClassName(Rule r, Map<String, Table> tableMap) {
        Class visitorClass = CodeGenMain.joinerClass$.get(r.signature(tableMap));
        if (visitorClass!=null) {
            return visitorClass.getSimpleName();
        }
        String name = "Joiner" + r.getHead().name() + "_" + r.id();
        if (r instanceof DeltaRule) {
            DeltaRule dr=(DeltaRule)r;
            if (dr.getTheP().isHeadP()) name += "_delta_head";
            else name += "_delta"+dr.getTheP().getPos();
        }
        return name;
    }

    int machineNum() {
        return SRuntimeMaster.getInst().getWorkerAddrMap().size();
    }

    String remoteTableVar(Object suffix) {
        String var = "remoteTable_";
        if (suffix != null)
            var += suffix;
        return var;
    }

    String printOut(Object ...args) {
        String out = "System.out.println(\"\"";
        for (Object a:args) {
            out += "+"+a;
        }
        out += ")";
        return out;
    }
    String returnStmt(Object retVar) {
        return "return "+retVar;
    }
    String init(Object lhs, Object initCode) {
        return lhs + "=" + initCode;
    }
    String decl(Class type, Object fieldOrMethod) {
        return decl(type.getSimpleName(), fieldOrMethod);
    }
    String decl(Object type, Object fieldOrMethod) {
        return type + " " + fieldOrMethod;
    }
    String inc(Object obj) {
        return obj + "++";
    }

    ST forVarUpto(String var, String upto) {
        ST for_ = tmplGroup.getInstanceOf("for");
        for_.add("init", decl("int", init(var, "0")));
        for_.add("cond", var+"<"+upto);
        for_.add("inc", inc(var));
        return for_;
    }

    String arrayIdx(String obj, String idx) {
        return obj+"["+idx+"]";
    }
    String arrayIdx(String idx) {
        return "["+idx+"]";
    }
    <T> String call(String obj, String method, T ...args) {
        return obj+"."+method+"("+makeArgs(args)+")";
    }

    String makeArgs(List<Object> args) {
        return makeArgs(args.toArray());
    }
    <T> String makeArgs(T ... args) {
        String[] strArgs = Arrays.stream(args).map(a->a.toString()).toArray(String[]::new);
        return String.join(",", strArgs);
    }
    String visitorVar(Predicate p) { return "$v"+p.getPos(); }
    String getVarName(Predicate p) {
        if (p.isHeadP()) {
            return headTableVar();
        } else {
            return "$"+getVarName(p.name()) + p.getPos();
        }
    }
    String constraintVar(Predicate p) { return "$constr"+p.getPos(); }
    String headTableVar() { return "$headTable"; }
    String myPartitionIdx() { return "$selectedTablePartitionIdx"; }
    String registryVar() { return "$registry"; }
    String partitionMapVar() { return "$partitionMap"; }
    String runtimeVar() { return "$runtime"; }
    String ruleIdVar() { return "$ruleId"; }
    String epochIdVar() { return "$epochId"; }
    String isUpdatedVar() { return "$isUpdated"; }
}
