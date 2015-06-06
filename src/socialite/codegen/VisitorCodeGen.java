package socialite.codegen;

import gnu.trove.list.array.TIntArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import socialite.engine.Config;
import socialite.parser.AggrFunction;
import socialite.parser.AssignOp;
import socialite.parser.CmpOp;
import socialite.parser.Column;
import socialite.parser.Const;
import socialite.parser.DeltaPredicate;
import socialite.parser.DeltaRule;
import socialite.parser.DeltaTable;
import socialite.parser.Expr;
import socialite.parser.Function;
import socialite.parser.GeneratedT;
import socialite.parser.MyType;
import socialite.parser.Op;
import socialite.parser.Param;
import socialite.parser.Predicate;
import socialite.parser.PrivPredicate;
import socialite.parser.RemoteBodyTable;
import socialite.parser.RemoteHeadTable;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.parser.Variable;
import socialite.parser.antlr.ColumnGroup;
import socialite.resource.SRuntimeMaster;
import socialite.tables.TableUtil;
import socialite.util.Assert;
import socialite.functions.Choice;
import socialite.functions.Max;

//import org.antlr.stringtemplate.StringTemplate;
//import org.antlr.stringtemplate.StringTemplateGroup;

public class VisitorCodeGen {
    static boolean disableDeltaStepOpt = false;

    static String visitorPackage = "socialite.visitors";

    Config conf;
    Rule rule;
    STGroup tmplGroup;
    ST visitorTmpl;
    String visitorName;

    Epoch epoch;
    Map<String, Table> tableMap;
    Map<String, ST> visitMethodMap;
    Predicate headP, iterStartP;
    Table headT;
    DeltaTable deltaHeadT;
    int isParRule=-1;
    Set<Variable>[] resolvedVarsArray;

    public VisitorCodeGen(Epoch e, Rule r, Map<String, Table> _tableMap, Config _conf) {
        epoch = e;
        rule = r;
        tableMap = _tableMap;
        conf = _conf;

        visitorName = visitorClassName(rule, tableMap);

        headP = rule.getHead();
        headT = tableMap.get(headP.name());
        tmplGroup = CodeGen.getVisitorGroup();
        visitorTmpl = tmplGroup.getInstanceOf("class");

        visitMethodMap = new HashMap<String, ST>();
        if (rule.inScc()) {
            if (headT instanceof DeltaTable)
                deltaHeadT = (DeltaTable) tableMap.get(headT.name());
            else deltaHeadT = (DeltaTable) tableMap.get(DeltaTable.name(headT));
        }

        resolvedVarsArray = Analysis.getResolvedVars(rule);
    }
    boolean isParallelRule() {
        if (isParRule<0) {
            isParRule = Analysis.isParallelRule(rule, tableMap)?1:0;
            if (conf.isDistributed()) isParRule = 1;
        }
        return isParRule==1;
    }
    private String getVisitMethodSig(String name, Class... types) {
        String sig = name;
        for (Class type : types)
            sig += "#" + type.getSimpleName();
        return sig;
    }

    boolean needChoiceSkipCode(Predicate p, int startCol, int endCol) {
        Predicate choiceP = firstOneWithAllResolvedButChoiceArg();
        if (choiceP==null) return false;
        if (p.getPos() > choiceP.getPos()) return true;
        if (p.getPos()==choiceP.getPos()) {
            for (int i=startCol; i<=endCol; i++) {
                if (p.inputParams()[i] instanceof Variable) {
                    Variable v=(Variable)p.inputParams()[i];
                    if (v.equals(choiceArgVar()))
                        return true;
                }
            }
        }
        return false;
    }
    ST skipCodeForChoice(ST method, Predicate p) {
        Predicate choiceP = firstOneWithAllResolvedButChoiceArg();
        ST if_ =tmplGroup.getInstanceOf("if");
        if_.add("cond", choiceMadeVar()+"&&"+currentPredicateVar()+"=="+p.getPos());
        if (p.getPos()==choiceP.getPos()) {
            if_.add("stmts", choiceMadeVar()+"=false");
        }
        if_.add("stmts", "return false");
        return if_;
    }

    ST getVisitMethod(Predicate p, int startCol, int endCol, int arity) {
        String name = "visit" + CodeGen.getVisitColumns(startCol, endCol, arity);
        Table t = getTable(p);
        Class[] argTypes = CodeGen.getArgTypes(t, startCol, endCol);
        String sig = getVisitMethodSig(name, argTypes);
        ST method = visitMethodMap.get(sig);
        if (method == null) {
            method = getNewMethodTmpl(name, "boolean");
            if (useChoice() && needChoiceSkipCode(p, startCol, endCol)) {
                method.add("ret", skipCodeForChoice(method, p));
            }
            method.add("ret", "return true");
            ST doWhile0 ;
            doWhile0 = tmplGroup.getInstanceOf("doWhile0");
            method.add("stmts", doWhile0);
            CodeGen.fillArgTypes(method, p, startCol, endCol);
            visitMethodMap.put(sig, method);
            visitorTmpl.add("methodDecls", method);
        }
        return method;
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

    public String visitorName() {
        return visitorPackage + "." + visitorName;
    }

    boolean isGenerated() {
        return visitorTmpl.getAttribute("name") != null;
    }
    public String generate() {
        if (isGenerated()) return visitorTmpl.render();

        visitorTmpl.add("packageStmt", "package " + visitorPackage);
        visitorTmpl.add("modifier", "public");
        visitorTmpl.add("name", visitorName);

        visitorTmpl.add("extends", "extends VisitorImpl");
        visitorTmpl.add("interfaces", "Runnable");

    HashSet<String> addedInterfaces = new HashSet<String>();
        for (Predicate p:rule.getBodyP()) {
            Table t = getTable(p);
            if (t.visitorInterface()==null) continue;
      if (addedInterfaces.contains(t.visitorInterface())) continue;
            visitorTmpl.add("interfaces", t.visitorInterface());
      addedInterfaces.add(t.visitorInterface());
        }

        importTablesDeclareFields();
        generateConstructor();
        generateMethods();
        return visitorTmpl.render();
    }

    private void importTablesDeclareFields() {
        for (Predicate p : rule.getAllP()) {
            String var=null;
            if (p.isHeadP()) var = headTableVar();
            else var = getVarName(p);

            Table t = getTable(p);
            String tableClass = t.className();

            visitorTmpl.add("imports", TableUtil.getTablePath(tableClass));
            if (isTableSliced(t)) tableClass += "[]";
            visitorTmpl.add("fieldDecls", tableClass + " " + var);
        }
        declareDeltaTableIfAny();

        visitorTmpl.add("fieldDecls", "SRuntime " + runtimeVar());
        visitorTmpl.add("fieldDecls", "final int " + epochIdVar());
        visitorTmpl.add("fieldDecls", "final int " + ruleIdVar());
        visitorTmpl.add("fieldDecls", "TableInstRegistry " + registryVar());
        visitorTmpl.add("fieldDecls", "TableSliceMap " + sliceMapVar());
        visitorTmpl.add("fieldDecls", "LockMap " + lockMapVar());
        for (Const c:rule.getConsts()) {
            String type = MyType.javaTypeName(c);
            visitorTmpl.add("fieldDecls", "final "+type+" "+c);
        }
        declareParamVariables();

        visitorTmpl.add("fieldDecls", "int " + currentPredicateVar());
        visitorTmpl.add("fieldDecls", "int " + firstTableIdx());
        visitorTmpl.add("fieldDecls", "int " + logCount());

        generateHeapIfAny();
        //declareAggrVarsAndAlloc();
        declareRemoteTablesIfAny();
        maybeDeclareDeltaStepWindowVar();
        if (useChoice()) {
            visitorTmpl.add("fieldDecls", "boolean " + choiceMadeVar()+"=false");
        }
    }
    void maybeDeclareDeltaStepWindowVar() {
        if (!useDeltaStepOpt()) return;

        visitorTmpl.add("fieldDecls", "DeltaStepWindow "+ deltaStepWindowVar());
    }

    void declareParamVariables() {
        for (Variable v : rule.getBodyVariables()) {
            if (!v.isRealVar()) continue;
            String type = MyType.javaTypeName(v);
            visitorTmpl.add("fieldDecls", type + " " + v);
        }
        if (headP.hasFunctionParam()) {
            AggrFunction f = headP.getAggrF();
            for (Variable v : f.getReturns()) {
                String type = MyType.javaTypeName(v);
                visitorTmpl.add("fieldDecls", type + " " + v);
            }
            String aggrType=MyType.javaTypeName(f.getAggrColumnType());
            visitorTmpl.add("fieldDecls", aggrType + " " + aggrVar());
        }
    }

    void declareDeltaTableIfAny() {
        if (!accumlDelta(rule)) return;

        String tableClass = deltaHeadT.className();
        assert !isTableSliced(deltaHeadT);
        if (useDeltaStepOpt()) {
            visitorTmpl.add("fieldDecls", tableClass+"[] "+deltaTableVar());
            visitorTmpl.add("fieldDecls", tableClass+"[] "+deltaTableReturnVar());
        } else {
            visitorTmpl.add("fieldDecls", tableClass + " " + deltaTableVar());
            visitorTmpl.add("fieldDecls", tableClass+" "+deltaTableReturnVar());
        }
    }

    void declareRemoteTablesIfAny() {
        declareRemoteBodyTablesIfAny();
        declareRemoteHeadTableIfAny();
    }

    void declareRemoteBodyTablesIfAny() {
        if (!conf.isDistributed()) return;
        for (int pos : tableSendPos()) {
            String tableCls = tableMap.get(RemoteBodyTable.name(rule, pos))
                    .className();
            String decl = tableCls + "[] " + remoteTableVar(pos) + "=new "
                    + tableCls + "[" + machineNum() + "]";
            visitorTmpl.add("fieldDecls", decl);
        }
    }

    void declareRemoteHeadTableIfAny() {
        if (!conf.isDistributed()) return;
        if (!hasRemoteRuleHead()) return;

        Table t = headT;
        String tableCls = tableMap.get(RemoteHeadTable.name(t, rule)).className();
        int clusterSize = SRuntimeMaster.getInst().getWorkerAddrMap().size();

        String decl = tableCls + "[] " + remoteTableVar("head") + "=new "
                            + tableCls + "[" + clusterSize + "]";
        visitorTmpl.add("fieldDecls", decl);
    }

    String deltaTableReturnVar() {
        //assert useDeltaStepOpt(rule);
        return "ret$delta$" + headT.name();
    }

    String deltaTableVar() {
        return "delta$" + headT.name();
    }

    String deltaTableVar0() {
        return "delta$" + headT.name() + "0";
    }

    boolean accumlDelta(Rule r) {
        if (r.inScc() && !useDijkstraOpt())
            return true;
        if (r.hasPipelinedRules())
            return true;
        return false;
    }

    boolean allDontCareOrConst(Object[] params) {
        for (Object o : params) {
            if (o instanceof Variable) {
                Variable v = (Variable) o;
                if (!v.dontCare)
                    return false;
            }
            if (o instanceof Function)
                Assert.not_supported();
        }
        return true;
    }

    void generateHeapIfAny() {
        if (!useDijkstraOpt()) return;

        AggrFunction f = headP.getAggrF();
        ST heap = tmplGroup.getInstanceOf("heap");
        String type = MyType.javaTypeName(f.getReturns().get(0));

        if (f.getReturns().size() > 1) {
            Object[] params = rule.firstP().inputParams();
            Object[] paramsButFirst2 = Arrays.copyOfRange(params, 2,
                    params.length);
            if (allDontCareOrConst(paramsButFirst2)) {
                // pass
            } else {
                Assert.not_implemented();
                // type = tupleClass();
            }
        }
        heap.add("type", type);
        heap.add("fname", f.name().replace('.', '_'));
        heap.add("f", f.name());
        heap.add("tableClass", headT.className());

        visitorTmpl.add("classDefs", heap);
        if (useDijkstraOpt())
            visitorTmpl.add("fieldDecls", heapClass() + " " + heapVar());
    }

    Table getTable(Predicate p) {
        return tableMap.get(p.name());
    }

    String tableGetter(int tableId) {
        return registryVar() + ".getTableInstArray(" + tableId + ")";
    }

    void generateConstructor() {
        ST code = getNewMethodTmpl(visitorName, "");
        visitorTmpl.add("methodDecls", code);

        code.add("args", "int _$epochId");
        code.add("args", "int _$ruleId");
        for (Const c:rule.getConsts()) {
            String argVar = "_$"+c;
            code.add("args", c.type.getSimpleName()+" "+argVar);
            code.add("stmts", c+"="+argVar);
        }

        for (Predicate p : rule.getBodyP()) {
            Table t = getTable(p);
            String var = getVarName(p);
            String argVar = "_$"+var;
            String tableClass = t.className();

            if (isTableSliced(t)) tableClass+="[]";
            code.add("args", tableClass + " " + argVar);
            code.add("stmts", var + " = " + argVar);
        }

        String headVarType = headT.className();
        if (isTableSliced(headT)) headVarType += "[]";

        if (headT instanceof DeltaTable) {
            code.add("stmts", headTableVar()+"=null/*delta head table*/");
        } else {
            String argVar = "$"+headTableVar();
            code.add("args",headVarType +" "+argVar);
            code.add("stmts", headTableVar()+"=$"+headTableVar());
        }
        code.add("args", "SRuntime _$runtime");
        code.add("args", "int _$firstTableIdx");

        code.add("stmts", epochIdVar()+"=_$epochId");
        code.add("stmts", ruleIdVar()+"=_$ruleId");
        code.add("stmts", runtimeVar()+"=_$runtime");
        code.add("stmts", sliceMapVar() + " = _$runtime.getSliceMap()");
        code.add("stmts", lockMapVar() + " = _$runtime.getLockMap()");
        code.add("stmts", registryVar() + "= _$runtime.getTableRegistry()");
        code.add("stmts", firstTableIdx() + " = _$firstTableIdx");
        code.add("stmts", currentPredicateVar() + "=0");

        if (useDijkstraOpt()) { // XXX: add baseIndex to heap class
            int heapSize = headT.arrayBeginIndex() + headT.arrayTableSize();
            code.add("stmts", heapVar() + "= new " + heapClass() + "("
                    + heapSize + "," + headTableVar() + ")");
        }
    }

    boolean hasChoiceFunc() {
        Predicate h=rule.getHead();
        if (h.hasFunctionParam() &&
                h.getAggrF().klass().equals(Choice.class) &&
                h.getAggrF().getArgs().get(0) instanceof Variable) {
            return true;
        }
        return false;
    }
    int useChoice=-1;
    boolean useChoice() {
        if (useChoice==0) return false;
        if (useChoice==1) return true;
        assert useChoice==-1;

        Predicate h=rule.getHead();
        if (hasChoiceFunc() &&
              (firstOneWithAllResolvedButChoiceArg()!=null ||
               hasOnlyChoiceVar(h))) {
            useChoice=1;
            return true;
        }
        useChoice=0;
        return false;
    }
    Variable choiceArgVar() {
        Predicate h=rule.getHead();
        return (Variable)h.getAggrF().getArgs().get(0);
    }
    boolean hasOnlyChoiceVar(Predicate p) {
        for (Param o:p.inputParams()) {
            if (o instanceof Variable) {
                Variable v=(Variable)o;
                if (!v.equals(choiceArgVar()))
                    return false;
            }
        }
        return true;
    }
    Predicate _choiceP;
    Predicate firstOneWithAllResolvedButChoiceArg() {
        if (_choiceP==Predicate.NIL) return null;
        if (_choiceP!=null) return _choiceP;

        List body = rule.getBody();
        for (int i=body.size()-1; i>=0; i--) {
            Object o = body.get(i);
            if (o instanceof Predicate) {
                Predicate p = (Predicate)o;
                if (allVarsResolvedOrDontcare(p))
                    continue;

                Set<Variable> newVars = p.getVariables();
                newVars.removeAll(resolvedVarsArray[p.getPos()]);
                for (Variable newVar:newVars) {
                    if (newVar.dontCare) continue;
                    if (newVar.equals(choiceArgVar())) continue;
                    return null;
                }
                _choiceP = p;
                return p;
            } else {
                Expr e = (Expr)o;
                if (e.root instanceof AssignOp) {
                    _choiceP = Predicate.NIL;
                    return null;
                }
            }
        }
        assert false:"never reaches here";
        return null;
    }

    boolean useDijkstraOpt() {
        if (!conf.getDebugOpt("DijkstraOpt")) {
            return false;
        }
        return isSequential() && rule.isDijkstraOpt();
    }

    boolean useDeltaStepOpt() {
        if (!conf.getDebugOpt("DeltaStepOpt"))
            return false;
        return isParallel() && rule.isDeltaStepOpt() && singlePrimAggrParam();
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
        visitorTmpl.add("methodDecls", code);
    }

    void generateRemoteTableMethods() {
        genGetRemoteTables();
    }

    String nullifyRemoteTableMethod(Object suffix) {
        String method = "nullifyRemoteTable_";
        if (suffix != null)
            method += suffix;
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
        visitorTmpl.add("methodDecls", method);
        genGetDeltaTableArrayReally(method, deltaTableVar());
    }

    void genGetDeltaTableArrayReally(ST method, String deltaVar) {
        if (accumlDelta(rule)) {
            if (useDeltaStepOpt())
                method.add("stmts", "return "+deltaTableReturnVar());
            else method.add("stmts", "return new TableInst[]{"+deltaTableReturnVar()+"}");
        } else {
            method.add("stmts", "return null");
        }
    }

    void genGetDeltaTable() {
        if (!accumlDelta(rule)) return;

        ST method = getNewMethodTmpl("getDeltaTable", deltaHeadT.className());
        visitorTmpl.add("methodDecls", method);

        genGetDeltaTableReally(method, deltaTableVar());
    }

    void genGetDeltaTableReally(ST method, String deltaVar) {
        if (useDeltaStepOpt()) {
            String deltaCls = deltaHeadT.className();
            ST if_=tmplGroup.getInstanceOf("if");
            method.add("stmts", if_);
            if_.add("cond", deltaVar+"==null");
            if_.add("stmts", deltaVar+"=new "+deltaCls+"["+deltaStepWindowVar()+".maxLevel()]");
            if_.add("stmts", deltaTableReturnVar()+"=new "+deltaCls+"["+deltaStepWindowVar()+".maxLevel()]");

            String aggrParamType = aggrParamType().getSimpleName();
            method.add("args", aggrParamType+" _$aggrParam");

            String priorityVar="$priority";
            method.add("stmts", "int "+priorityVar);
            String isMin="true";
            if (rule.getHead().getAggrF().klass().equals(Max.class)) isMin = "false";
            method.add("stmts", priorityVar+"="+deltaStepWindowVar()+".selectLevel(_$aggrParam,"+isMin+")");

            genGetDeltaTableReallyWithPriority(method, deltaVar+"["+priorityVar+"]",
                                               deltaTableReturnVar()+"["+priorityVar+"]",priorityVar);
        } else {
            genGetDeltaTableReallyWithPriority(method, deltaVar, deltaTableReturnVar(), "0");
        }
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

        if (headTableLockAtStart()) {
            ST withoutLock = CodeGen.withoutLock(lockMapVar(), headT.id(), firstTableIdx());
            if_.add("stmts", withoutLock);
            if_ = withoutLock;
        }

        if_.add("stmts", deltaVar+"="+alloc);
        if_.add("stmts", deltaRetVar+"="+deltaVar+".isEmpty()?"+deltaVar+":null");

        if_ = tmplGroup.getInstanceOf("if");
        if (useShortCircuit()) {
            // do nothing
        } else method.add("stmts", if_);

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
        if (headTableLockAtStart()) {
            ST withoutLock = CodeGen.withoutLock(lockMapVar(), headT.id(), firstTableIdx());
            code.add("stmts", withoutLock);
            code = withoutLock;
        }
        String deltaRulesArray=runtimeVar()+".getRuleMap(getRuleId()).getDeltaRules(getRuleId()).toArray()";
        ST forEachDepRule = tmplGroup.getInstanceOf("forEach");
        code.add("stmts", deltaVar+".setReuse(false)");

        code.add("stmts", forEachDepRule);
        forEachDepRule.add("set", deltaRulesArray);
        forEachDepRule.add("elem", "int _$pr");

        ST forEachVisitor = tmplGroup.getInstanceOf("forEach");
        forEachDepRule.add("stmts", forEachVisitor);
        String visitors = runtimeVar()+".getVisitorBuilder(_$pr).getNewVisitorInst(_$pr, new TableInst[]{"+deltaVar+"})";
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

    List<Integer> tableSendPos() {
        if (tableSendPos == null)
            tableSendPos = Analysis.tableSendPos(rule, tableMap);
        return tableSendPos;
    }


    void genGetRemoteBodyTable() {
        for (int pos : tableSendPos()) {
            RemoteBodyTable rt = (RemoteBodyTable)tableMap.get(RemoteBodyTable.name(rule, pos));
            String tableCls = rt.className();

            // generating get method
            ST method = getNewMethodTmpl(getRemoteTableMethod(pos), tableCls);
            visitorTmpl.add("methodDecls", method);
            method.add("comment", "generated by genGetRemoteBodyTable()");
            method.add("args", "int rangeOrHash");

            Predicate nextP = (Predicate) rule.getBody().get(pos);
            Table joinT = tableMap.get(nextP.name());
            String machineIdx = sliceMapVar() + ".machineIndexFor("+joinT.id()+", rangeOrHash)";
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
            visitorTmpl.add("methodDecls", method);
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
        visitorTmpl.add("methodDecls", method);
        method.add("args", "int _$rangeOrHash");
        genGetRemoteHeadTableReally(method, rt, tableCls);

        method = getNewMethodTmpl(nullifyRemoteTableMethod("head"), "void");
        visitorTmpl.add("methodDecls", method);
        method.add("args", "int _$machineIdx");
        genNullifyRemoteHeadTableReally(method, rt, tableCls);
    }

    void genGetRemoteHeadTableReally(ST method, RemoteHeadTable rt, String tableCls) {
        // generating getRemoteTable_head (int _$rangeOrHash)
        String machineIdx = sliceMapVar()+".machineIndexFor("+rt.origId()+", _$rangeOrHash)";
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

    boolean isDeltaFirstT() {
        return getFirstPifDelta() != null;
    }

    int origHeadTid() {
        return headT.id();
    }

    void maybeSendToRemoteHead(ST code, String table,
                               String machineIdx, Object idxParam) {
        ST if_ = tmplGroup.getInstanceOf("if");
        code.add("stmts", if_);
        if_.add("cond", table+".vacancy()==0");

        ST send = if_;
        if (headTableLockAtStart()) {
            ST withoutLock = CodeGen.withoutLock(lockMapVar(), headT.id(),	firstTableIdx());
            if_.add("stmts", withoutLock);
            send = withoutLock;
        }

        RemoteHeadTable rt = remoteHeadT();
        String tableCls = rt.className();
        /*String sliceIdx = sliceMapVar()+".getIndex("+rt.origId()+","+idxParam+")";
        if (!shardedRemoteHeadT(rt)) sliceIdx = "0";*/
        String sliceIdx="0";

        sendToRemoteHead(send, table, sliceIdx, machineIdx);
    }

    void sendToRemoteHead(ST code, String table, String sliceIdx, String machineIdx) {
        code.add("stmts", "SRuntime _$rt=SRuntimeWorker.getInst()");
        code.add("stmts", "RuleMap _$rm=_$rt.getRuleMap(getRuleId())");
        code.add("stmts", "int _$depRuleId=_$rm.getRemoteHeadDep(getRuleId())");
        code.add("stmts", "EvalWithTable _$cmd=new EvalWithTable("+epochIdVar()+", _$depRuleId,"+table+","+sliceIdx+")");
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
        if (headTableLockAtStart()) {
            ST withoutLock = CodeGen.withoutLock(lockMapVar(), headT.id(), firstTableIdx());
            if_.add("stmts", withoutLock);
            send = withoutLock;
        }
        String machineIdx = sliceMapVar()+".machineIndexFor("+t.id()+","+joinP.first()+")";
        sendToRemoteBody(send, table, machineIdx, ""+joinP.getPos());
    }
    void maybeBroadcastToRemoteBody(ST code, String table, Predicate joinP) {
        Table t = tableMap.get(joinP.name());
        ST if_= tmplGroup.getInstanceOf("if");
        code.add("stmts", if_);
        if_.add("cond", table+".vacancy()==0");

        ST send = if_;
        if (headTableLockAtStart()) {
            ST withoutLock = CodeGen.withoutLock(lockMapVar(), headT.id(), firstTableIdx());
            if_.add("stmts", withoutLock);
            send = withoutLock;
        }
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
        getter.add("stmts", "return " + ruleIdVar());
        visitorTmpl.add("methodDecls", getter);

        getter = getNewMethodTmpl("getEpochId", "int");
        getter.add("stmts", "return " + epochIdVar());
        visitorTmpl.add("methodDecls", getter);
    }

    boolean singlePrimAggrParam() {
        if (headP.getAggrF().getArgs().size()!=1)
            return false;
        Object param=headP.getAggrF().getArgs().get(0);
        return MyType.isPrimitive(param);
    }
    Class aggrParamType() {
        assert useDeltaStepOpt();
        assert singlePrimAggrParam();
        return MyType.javaType(headP.getAggrF().getArgs().get(0));
    }

    void maybeInitDeltaStepWindow(ST run) {
        if (!useDeltaStepOpt()) return;
        if (!singlePrimAggrParam()) return;

        run.add("stmts", deltaStepWindowVar()+"=getWorker().getQueue().deltaStepWindow()");
    }
    void maybeUpdateDeltaStepWindow(ST run) {
        if (!useDeltaStepOpt()) return;
        if (!singlePrimAggrParam()) return;

        run.add("stmts", deltaStepWindowVar()+".updateShared()");
    }

    void generateRunMethod() {
        ST run = getNewMethodTmpl("run", "void");
        visitorTmpl.add("methodDecls", run);
        maybeInitDeltaStepWindow(run);

        ST doWhile0 = tmplGroup.getInstanceOf("doWhile0");
        run.add("stmts", doWhile0);
        ST code = doWhile0;
        for (Object o : rule.getBody()) {
            if (o instanceof Expr) {
                code = insertExprCode(code, (Expr) o);
            } else if (o instanceof Predicate) {
                Predicate p = (Predicate) o;
                if (allVarsResolvedOrDontcare(p)) {
                    code = continueIfNotContains(code, p);
                } else {
                    Table t = getTable(p);
                    String tableVar = getVarName(p);
                    if (isTableSliced(t)) {
                        boolean first = rule.getBody().get(0).equals(p);
                        if (first) tableVar += "[" + firstTableIdx() + "]";
                        else if (isResolved(p, p.first())) {
                            tableVar += "[" + sliceIdxGetter(t, p.first()) + "]";
                        } else {
                            ST for_ = tmplGroup.getInstanceOf("for");
                            for_.add("init", "int _$i=0");
                            for_.add("cond", "_$i<"+sliceMapVar()+".sliceNum("+t.id()+")");
                            for_.add("inc", "_$i++");
                            code.add("stmts", for_);
                            code = for_;
                            tableVar += "[_$i]";
                        }
                    }
                    iterStartP = p;
                    insertIterateCodeInRun(code, tableVar);
                    break;
                }
            } else assert false:"Expecting Expr or Predicate, but got:"+o;
        }
        if (iterStartP == null) {
            if (headTableLockAtStart()) {
                int column = headTableWriteLockColumn();
                Object param = headP.inputParams()[column];
                ST withlock = CodeGen.withLock(lockMapVar(), headT.id(), sliceIdxGetter(headT, column, param));
                code.add("stmts", withlock);
                code = withlock;
            }
            insertUpdateAccumlOrPipelining(code);
            genRunMethodFini(run);
            return;
        }

        if (useDijkstraOpt()) {
            addDijkstraSuffixInRun(code);
        }

        if (useShortCircuit()) {
            addShortCircuitSuffixInRun(code);
        }
        genRunMethodFini(run);
    }

    void addDijkstraSuffixInRun(ST code) {
        Predicate p = iterStartP;
        code.add("stmts", currentPredicateVar() + "=" + p.getPos());

        ST while_ = tmplGroup.getInstanceOf("while");
        while_.add("cond", "!" + heapVar() + ".isEmpty()");
        String type = MyType.javaTypeName(headP.first());
        while_.add("stmts", type + " _$idx=" + heapVar() + ".popMin()");
        while_.add("stmts", iterStartP.first() + "=_$idx");
        // while_.add("stmts", "$headTable.iterate_first($idx, this)");
        assert (!isTableSliced(headT));
        while_.add("stmts", headTableVar() + ".iterate_by_0(_$idx, this)");
        code.add("stmts", while_);
    }
    boolean useShortCircuit() {
        return isSequential() &&
                rule.isLeftRec() && !rule.isDijkstraOpt() &&
                iterStartP instanceof DeltaPredicate;
    }
    void addShortCircuitSuffixInRun(ST code) {
        assert !useDijkstraOpt();
        assert rule.isLeftRec():"rule is not left rec";
        assert iterStartP instanceof DeltaPredicate:"iterStartP is not DeltaPredicate:"+iterStartP;

        ST while_ = tmplGroup.getInstanceOf("while");
        code.add("stmts", while_);
        String cond="("+deltaTableVar()+"!=null) && (!"+deltaTableVar()+".isEmpty())";
        while_.add("cond", cond);

        Table t = tableMap.get(iterStartP.name());
        String tableClass = t.className();

        String tmpDelta = CodeGen.uniqueVar("tmpDelta");
        while_.add("stmts", tableClass+" "+tmpDelta+"="+getVarName(iterStartP));
        while_.add("stmts", getVarName(iterStartP)+"="+deltaTableVar());
        while_.add("stmts", deltaTableVar()+"="+tmpDelta);
        while_.add("stmts", deltaTableVar()+".clear()");
        while_.add("stmts", getVarName(iterStartP)+".iterate(this)");
        code.add("stmts", deltaTableReturnVar()+"=null");
    }

    void genRunMethodFini(ST code) {
        finishSendRemoteTablesIfAny(code);
        freeRemoteTables(code);
        maybeUpdateDeltaStepWindow(code);
    }

    void freeRemoteTables(ST code) {
        if (hasRemoteRuleHead()) {
            code.add("stmts", "TmpTablePool.free(worker.id(),"+remoteTableVar("head")+")");
        }
        if (hasRemoteRuleBody()) {
            for (int pos : tableSendPos()) {
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
        for (int pos:tableSendPos()) {
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

    String remoteBodyTablePos(String table) {
        String result = "";
        for (int pos : tableSendPos()) {
            RemoteBodyTable rt = (RemoteBodyTable)tableMap.get(RemoteBodyTable.name(rule, pos));
            result += "(" + table + " instanceof " + rt.className() + ")?"
                    + pos + ":\n\t\t";
        }
        result += "-1";
        return result;
    }

    void finishSendRemoteTablesIfAny(ST code) {
        if (hasRemoteRuleHead()) {
            finishSendRemoteRuleHead(code);
        }
        if (hasRemoteRuleBody()) {
            finishSendRemoteRuleBody(code);
        }
    }

    boolean bodyTableLockRequired() {
        if (conf.isSequential()) return false;

        if (headT instanceof DeltaTable) {
            DeltaTable deltaHeadT = (DeltaTable)headT;
            Table t = deltaHeadT.origT();
            assert rule.getBodyP().size()==1;
            assert t.name().equals(iterStartP.name());
            return true;
        } else return false;
    }

    void insertIterateCodeInRun(ST run, String tableVar) {
        ST code = run;
        Table iterStartT = getTable(iterStartP);
        assert rule.firstP().equals(iterStartP);
        boolean iterStartWithFirstP = rule.firstP().equals(iterStartP);
        if (headTableLockAtStart()) {
            assert iterStartP == rule.firstP();
            ST withlock = CodeGen.withLock(lockMapVar(), headT.id(), firstTableIdx());
            code.add("stmts", withlock);
            code = withlock;
        }
        if (bodyTableLockRequired()) {
            assert rule.getBodyP().size()==1;
            DeltaTable deltaHeadT = (DeltaTable)headT;
            assert iterStartT.equals(deltaHeadT.origT());
            ST withlock = CodeGen.withLock(lockMapVar(), iterStartT.id(), firstTableIdx());
            code.add("stmts", withlock);
            code = withlock;
        }
        code.add("stmts", currentPredicateVar() + "=" + iterStartP.getPos());

        String invokeIter;
        Set<Variable> resolvedVars = resolvedVarsArray[iterStartP.getPos()];
        if (updateFromRemoteHeadT()) {
            invokeIter = tableVar+".iterate(this)";
            code.add("stmts", invokeIter);
        } else if (iterStartWithFirstP && doIterateRange()) {
            invokeIter = iterateRange(iterStartT, tableVar);
            code.add("stmts", invokeIter);
        } else if (iterStartWithFirstP && doDynamicIterateRange()) {
            invokeIter = iterateRangeDynamic(tableVar);
            code.add("stmts", invokeIter);
        } else {
            invokeIter = tableVar+invokeIterate(iterStartP, resolvedVars);
            code.add("stmts", invokeIter);
        }
    }

    // see also {@link QueryCodeGen#getIndexByCols()}
    TIntArrayList getIndexByCols(Predicate p) {
        // returns the columns used for the iterate_by_# method
        ArrayList<Column> resolvedIdxCols = getResolvedIndexCols(p);
        TIntArrayList idxbyCols = new TIntArrayList(4);
        if (resolvedIdxCols.size()>=1)
            idxbyCols.add(resolvedIdxCols.get(0).getAbsPos());
        if (resolvedIdxCols.size()<=1) return idxbyCols;

        Table t = getTable(p);
        List<ColumnGroup> colGroups = t.getColumnGroups();
        if (colGroups.size()==1) return idxbyCols;

        int nest=1;
        for (ColumnGroup g:colGroups.subList(1, colGroups.size())) {
            if (nest > 3) break;
            if (g.first().isIndexed()) {
                Column idxCol = g.first();
                if (resolvedIdxCols.contains(idxCol))
                    idxbyCols.add(idxCol.getAbsPos());
            } else { break; }
            nest++;

        }
        return idxbyCols;
    }
    ArrayList<Column> getResolvedIndexCols(Predicate p) {
        Table t = getTable(p);
        Object[] params = p.inputParams();
        ArrayList<Column> idxCols = new ArrayList<Column>();
        Set<Variable> resolvedVars = resolvedVarsArray[p.getPos()];
        for (int i=0; i<params.length; i++) {
            if (isConstOrResolved(resolvedVars, params[i])) {
                if (t.getColumn(i).isIndexed()) {
                    idxCols.add(t.getColumn(i));
                }
                /*if (t.getColumn(i).isSorted()) {
                    idxCols.add(t.getColumn(i));
                }*/
            }
        }
        return idxCols;
    }
    boolean isOutmostIdxColResolved(Predicate p) {
        return getOutmostResolvedIdxCol(p) >= 0;
    }
    int getOutmostResolvedIdxCol(Predicate p) {
        Table t = getTable(p);
        if (!t.hasNestedT())
            return -1;
        ColumnGroup outmostGroup = t.getColumnGroups().get(0);
        ArrayList<Column> resolvedIdxCols = getResolvedIndexCols(p);
        for (Column c:resolvedIdxCols) {
            if (c.position() >= outmostGroup.startIdx() &&
                    c.position() <= outmostGroup.endIdx())
                return c.position();
        }
        return -1;
    }

    boolean isIndexColResolved(Predicate p) {
        if (getResolvedIndexCols(p).size()==0)
            return false;
        return true;
    }
    String getIteratebySuffix(Predicate p, String trailingArgs) {
        TIntArrayList idxbyCols = getIndexByCols(p);
        Table t=getTable(p);
        Object[] params = p.inputParams();
        String iteratebySuffix = "_by";
        for (int i=0; i<idxbyCols.size(); i++) {
            int idxPos = idxbyCols.get(i);
            iteratebySuffix += "_"+idxPos;
        }
        iteratebySuffix += "(";
        for (int i=0; i<idxbyCols.size(); i++) {
            int idxPos = idxbyCols.get(i);
            iteratebySuffix += params[idxPos] +", ";
        }
        iteratebySuffix += "this"+trailingArgs+")";
        return iteratebySuffix;
    }

    boolean doDynamicIterateRange() {
        if (iterStartP instanceof DeltaPredicate) return true;

        return false;
    }
    String iterateRangeDynamic(String tableVar) {
        ST ifElse = tmplGroup.getInstanceOf("ifElse");
        ifElse.add("cond", tableVar + ".virtualSliceNum()>1");

        String sliceNum = tableVar + ".virtualSliceNum()";
        String sliceSize = "((" + tableVar + ".size()+" + sliceNum + "-1)/"
                + sliceNum + ")";
        String from = sliceSize + "*" + firstTableIdx();
        String to = sliceSize + "*" + "(" + firstTableIdx() + "+1)-1";
        ifElse.add("stmts", tableVar+".iterate_range("+from+","+to+",this)");
        ifElse.add("elseStmts", tableVar + ".iterate(this)");
        return ifElse.render();
    }

    int resolvedSingleNestedArrayIndex(Predicate p) {
        assert !p.hasFunctionParam();
        Table t = getTable(p);
        if (!t.hasNestedT()) return -1;

        assert t.getColumn(0).hasRange();
        Object[] params = p.inputParams();
        for (int i = 1; i < t.numColumns(); i++) {
            Column c = t.getColumn(i);
            if (!c.hasRange()) continue;

            if (isResolved(p, params[i])) return i;
            else return -1;
        }
        return -1;
    }

    boolean doIterateRange() {
        if (!isParallelRule()) return false;

        Table t = tableMap.get(iterStartP.name());
        int column = Analysis.firstShardedColumnWithVar(iterStartP, t);
        if (column < 0) return false;
        Column c = t.getColumn(column);
        if (c.hasRange()) return true;
        else return false;
    }
    String iterateRange(Table t, String tableVar) {
        int id = t.id();
        if (t instanceof RemoteHeadTable) id = ((RemoteHeadTable) t).origId();
        else assert !(t instanceof GeneratedT);

        int rangeCol = Analysis.firstShardedColumnWithVar(iterStartP, t);
        String range = sliceMapVar()+".getRange("+id+","+rangeCol+","+firstTableIdx()+")";
        String begin = range + "[0]", end = range + "[1]";
        Object[] params = iterStartP.inputParams();
        int nestedArrayIdx = resolvedSingleNestedArrayIndex(iterStartP);
        Object nestedArrayIdxVal = nestedArrayIdx>rangeCol ? params[nestedArrayIdx]:null;
        String invokeIter = tableVar+".iterate_range_"+rangeCol;
        if (nestedArrayIdx > rangeCol) {
            invokeIter += "_by_"+nestedArrayIdx+"("+nestedArrayIdxVal+",";
        } else invokeIter += "(";

        invokeIter += begin + "," + end + ",this)";
        return invokeIter;
    }

    String iteratePrefix(Predicate p, String tableVar) {
        return tableVar + ".iterate("+p.first()+", this)";
    }

    boolean isConstOrResolved(Set<Variable> resolvedVars, Object varOrConst) {
        if (varOrConst instanceof Variable) {
            Variable v = (Variable) varOrConst;
            return resolvedVars.contains(v);
        }
        return true;
    }

    boolean allVarsResolved(Set<Variable> resolvedVars, Predicate p) {
        for (Object param : p.inputParams()) {
            if (!isConstOrResolved(resolvedVars, param))
                return false;
        }
        return true;
    }

    boolean isConstOrDontCare(Object o) {
        if (o instanceof Variable)
            return isDontCare(o);
        else
            return true;
    }

    boolean isDontCare(Object o) {
        if (o instanceof Variable)
            if (((Variable) o).dontCare)
                return true;
        return false;
    }

    boolean allVarsResolvedOrDontcare(Predicate p) {
        Set<Variable> resolvedVars = resolvedVarsArray[p.getPos()];
        for (Object param : p.inputParams()) {
            if (!isDontCare(param) && !isConstOrResolved(resolvedVars, param))
                return false;
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

        Table t = getTable(p);
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

        Table t = getTable(p);
        Object lhs = cmpOp.getLHS();
        Object rhs = cmpOp.getRHS();
        int sortCol = idxForIteratePart(p);
        Variable v = (Variable) p.inputParams()[sortCol];
        if (v.equals(lhs))
            return rhs;
        else
            return lhs;
    }

    int idxForIteratePart(Predicate p) {
        Object next = rule.getBody().get(p.getPos() + 1);
        Op op = ((Expr) next).root;
        CmpOp cmpOp = (CmpOp) op;

        Table t = getTable(p);
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
        Assert.impossible("VisitorCodeGen.idxForIteratePart(): Should not reach here!");
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
                if (allVarsResolvedOrDontcare(p)) {
                    assert (code != null);
                    code = continueIfNotContains(code, (Predicate) o);
                } else {
                    boolean added = insertContinuationCode(code, prevp, p, resolved[i]);
                    code = genVisitMethodFor(p, resolved[i]);
                    prevp = p;
                    if (added && iteratePart(p, resolved[i]))
                        i++;
                }
            } else if (o instanceof Expr) {
                Expr e = (Expr) o;
                assert (code != null);
                code = insertExprCode(code, e);
            } else
                Assert.impossible();
        }
        insertUpdateAccumlOrPipelining(code);
        if (useDijkstraOpt()) {
            Set<Variable> resolved2 = new HashSet<Variable>();
            int groupbyNum = headP.functionIdx();
            for (int i = 0; i < groupbyNum; i++) {
                Object v = iterStartP.inputParams()[i];
                if (v instanceof Variable)
                    resolved2.add((Variable) v);
            }
            Table iterStartT = tableMap.get(iterStartP.name());
            if (!headT.hasSameNestStructure(iterStartT))
                genVisitMethodFor(iterStartP, resolved2);
        }
    }

    void assertCaseOrIterOrNull(ST code) {
        if (code == null)
            return;
        String name = code.getName();
        Assert.true_(name.equals("withIterator") || name.equals("/case"),
                "Unexpected template name:" + name);
    }

    boolean hasRemoteRuleBody() {
        if (!conf.isDistributed()) return false;
        return Analysis.hasRemoteRuleBody(rule, tableMap);
    }

    boolean hasRemoteRuleHead() {
        if (!conf.isDistributed()) return false;
        return Analysis.hasRemoteRuleHead(rule, tableMap);
    }

    ST ifLocalHead(ST code) {
        Table t = headT;
        ST if_ = tmplGroup.getInstanceOf("if");
        code.add("stmts", if_);
        if_.add("cond", sliceMapVar()+".isLocal("+t.id()+","+headP.first()+")");
        ST ifLocal = tmplGroup.getInstanceOf("simpleStmts");
        if_.add("stmts", ifLocal);
        return ifLocal;
    }

    void insertUpdateAccumlOrPipelining(ST code) {
        code.add("stmts", "boolean " + isUpdatedVar() + "=false");
        if (hasRemoteRuleHead()) {
            ST ifLocal = genAccumlRemoteHeadTable(code);
            code = ifLocal;
        }
        ST ifUpdated = genUpdateCode(code);
        code = ifUpdated;
        genAccumlDeltaIfAny(code);
    }

    String getRemoteBodyTable(int pos, Object rangeOrHash) {
        Object param = isInt(rangeOrHash)? rangeOrHash:"HashCode.get("+rangeOrHash+")";
        return getRemoteTableMethod(pos)+"("+param+")";
    }

    String getRemoteHeadTable(Object machineIdx) {
        Object param = isInt(machineIdx)? machineIdx:"HashCode.get("+machineIdx+")";
        return getRemoteTableMethod("head")+"("+param+")";
    }

        boolean isInt(Object o) {
            return MyType.javaType(o).equals(int.class);
        }
    ST genAccumlRemoteHeadTable(ST code) {
        Table t = headT;
        ST ifLocalElse = tmplGroup.getInstanceOf("ifElse");
        code.add("stmts", ifLocalElse);
        ifLocalElse.add("cond", sliceMapVar()+".isLocal("+t.id()+","+headP.first()+")");
        ST ifLocal = tmplGroup.getInstanceOf("simpleStmts");
        ifLocalElse.add("stmts", ifLocal);

        RemoteHeadTable rt = remoteHeadT();
        String tableCls = rt.className();
        ifLocalElse.add("elseStmts", tableCls + " _$remoteT");
        ifLocalElse.add("elseStmts", "int _$machineIdx=" + sliceMapVar()+
                            ".machineIndexFor("+rt.origId()+","+headP.first()+")");
        ifLocalElse.add("elseStmts", "_$remoteT="+getRemoteHeadTable(headP.first()));

        ST stmts = tmplGroup.getInstanceOf("simpleStmts");
        ifLocalElse.add("elseStmts", stmts);

        stmts.add("stmts", insertArgs("_$remoteT", headP.inputParams()));

        ST maybeSend = tmplGroup.getInstanceOf("simpleStmts");
        ifLocalElse.add("elseStmts", maybeSend);
        maybeSendToRemoteHead(maybeSend, "_$remoteT", "_$machineIdx", headP.first());
        return ifLocal;
    }

    boolean requireBroadcast(Predicate p) {
        if (p instanceof PrivPredicate) return false;
        if (isResolved(p, p.first())) return false;
        else return true;
    }
    ST genAccumlRemoteBodyTable(ST code, Predicate p) {
        assert tableSendPos().contains(p.getPos());

        if (requireBroadcast(p)) {
            return genBroadcastAccumlRemoteBodyTable(code, p);
        }
        Table joinT = tableMap.get(p.name());
        ST ifElse = tmplGroup.getInstanceOf("ifElse");
        code.add("stmts", ifElse);
        ifElse.add("cond", sliceMapVar()+".isLocal(" + joinT.id()+", "+p.first() + ")");
        ST ifLocal = tmplGroup.getInstanceOf("simpleStmts");
        ifElse.add("stmts", ifLocal);

        RemoteBodyTable rt = (RemoteBodyTable)tableMap.get(RemoteBodyTable.name(rule, p.getPos()));
        String tableCls = rt.className();
        ifElse.add("elseStmts", tableCls+" _$remoteT="+getRemoteBodyTable(p.getPos(), p.first()));
        ifElse.add("elseStmts", insertArgs("_$remoteT",rt.getParamVars()));

        ST maybeSend = tmplGroup.getInstanceOf("simpleStmts");
        ifElse.add("elseStmts", maybeSend);
        maybeSendToRemoteBody(maybeSend, "_$remoteT", p);
        return ifLocal;
    }
    ST genBroadcastAccumlRemoteBodyTable(ST code, Predicate p) {
        RemoteBodyTable rt = (RemoteBodyTable)tableMap.get(RemoteBodyTable.name(rule, p.getPos()));
        String tableCls = rt.className();
        code.add("stmts", tableCls+" _$remoteT="+getRemoteBodyTable(p.getPos(), "0"));
        code.add("stmts", insertArgs("_$remoteT",rt.getParamVars()));
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
        if (useDijkstraOpt()) {
            code.add("stmts", heapVar() + ".insert(" + headP.first() + ")");
            return;
        }
        if (!accumlDelta(rule))
            return;

        if (useDeltaStepOpt()) {
            AggrFunction f = headP.getAggrF();
            assert f!=null && f.getReturns().size()==1;
            String deltaTableSlice = "getDeltaTable("+toArgs(f.getReturns())+")";
            code.add("stmts", updateHeadParamsTo(deltaTableSlice));
        } else {
            String deltaTableSlice = "getDeltaTable()";
            code.add("stmts", updateHeadParamsTo(deltaTableSlice));
        }
    }

    boolean isNextId(AggrFunction f) {
        String n=f.name();
        return n.equals("Builtin.nextId") || n.equals("NextId.invoke");
    }
    boolean isMinOrMax(AggrFunction f) {
        String n=f.name();
        return n.equals("Builtin.min") || n.equals("Builtin.max") ||
                n.equals("Min.invoke") || n.equals("Max.invoke") ;
    }
    boolean priorTestForMinMax() {
        if (!headTableLockAtEnd()) return false;
        boolean test = headP.hasFunctionParam() &&
                    isMinOrMax(headP.getAggrF()) &&
                    headT.isArrayTable() &&
                    headP.getAggrF().getIdx()==1 &&
                    headT.getColumn(1).type().isPrimitive();
        return test;
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

        String headTableSlice = headTableVar();
        if (isTableSliced(headT)) {
            String sliceIdx;
            if (updateFromRemoteHeadT()) {
                sliceIdx = sliceIdxGetter(headT, headP.first());
            } else if (Analysis.updateParallelShard(rule, tableMap)) {
                sliceIdx = firstTableIdx();
            } else {
                sliceIdx = sliceIdxGetter(headT, headP.first());
            }
            headTableSlice += "[" + sliceIdx + "]";
        }
        String _headTable = "_$$headTable";
        code.add("stmts", headT.className()+" "+_headTable+"="+headTableSlice);

        if (priorTestForMinMax())
            genPriorTestForMinMax(code, _headTable);

        ST prevCode=code;
        if (headTableLockAtEnd()) {
            int column = headTableWriteLockColumn();
            Param param = headP.inputParams()[column];
            ST withLock;
            withLock = CodeGen.withLock(lockMapVar(), headT.id(),sliceIdxGetter(headT, column, param));
            code.add("stmts", withLock);
            code = withLock;
        }

        if (headP.hasFunctionParam()) {
            genAggrCode(code, _headTable, ifUpdated);
        } else genInsertCode(code, _headTable, ifUpdated);

        ST if_ = tmplGroup.getInstanceOf("if");
        if_.add("cond", isUpdatedVar());
        if_.add("stmts", ifUpdated);
        prevCode.add("stmts", if_);
        return ifUpdated;
    }

    String containsGroupbyPrefix(String headTable, AggrFunction f) {
        String contains = headTable + ".contains(";
        for (int i = 0; i < f.getIdx(); i++) {
            Object p = headP.inputParams()[i];
            contains += p;
            if (i != f.getIdx() - 1)
                contains += ", ";
        }
        contains += ")";
        return contains;
    }

    void genPriorTestForMinMax(ST code, String headTable) {
        AggrFunction f=headP.getAggrF();
        ST if_ = tmplGroup.getInstanceOf("if");
        code.add("stmts", if_);
        String contains = containsGroupbyPrefix(headTable, f);
        if_.add("cond", contains);
        Variable newAnsVar = f.getReturns().get(0);
        assert newAnsVar.type.isPrimitive();
        String groupbyRetType = MyType.javaTypeName(newAnsVar);
        if_.add("stmts",groupbyRetType + " _$oldAns");
        String groupby = invokeGroupby(headTable, headP);
        if_.add("stmts", "_$oldAns="+groupby);
        if_.add("stmts", newAnsVar+"="+f.codegen("_$oldAns").render());
        if_.add("stmts", "if (_$oldAns!=0 && _$oldAns=="+newAnsVar+") continue");
    }

    void genAggrCode(ST code, String headTable, ST ifUpdated) {
        AggrFunction f = headP.getAggrF();

        String contains = containsGroupbyPrefix(headTable, f);
        ST ifElse = tmplGroup.getInstanceOf("ifElse");
        if (f.isSimpleAggr()) {
            // if the groupby prefix params don't exist in the table, we insert the params.
            ifElse.add("cond", "!" + contains);
            if (isNextId(f)) {
                String nextId="Builtin.nextId("+f.getArgs().get(0)+")";
                ifElse.add("stmts", assignVars(Arrays.asList(new Object[]{aggrVar()}), Arrays.asList(new Object[]{nextId})));
            } else {
                ifElse.add("stmts", assignVars(Arrays.asList(new Object[]{aggrVar()}), f.getArgs()));
            }
            ifElse.add("stmts", insertHeadParamsTo(headTable));
            ifElse.add("stmts", isUpdatedVar()+"=true");
            //ifElse.add("stmts", ifUpdated);
            //ifElse.add("stmts", "continue");
            code.add("stmts", ifElse);
        } else {
            ifElse.add("cond", "!" + contains);
            if (f.getAggrColumnType().isArray()) {
                String aggrValType = f.getAggrColumnType().getComponentType().getSimpleName();
                Variable v=(Variable)f.getArgs().get(0);
                ifElse.add("stmts", aggrValType+"[] "+aggrVar()+"=new "+aggrValType+"["+v+".length]");
                ifElse.add("stmts", "for(int _$$i=0;_$$i<"+aggrVar()+".length;_$$i++)"+aggrVar()+"[_$$i]=new "+aggrValType+"()");
            } else {
                String aggrValType = f.getAggrColumnType().getSimpleName();
                ifElse.add("stmts", aggrValType + " "+aggrVar()+"=new "+aggrValType+"("+")");
            }
            ifElse.add("stmts", isUpdatedVar()+"="+f.codegen(aggrVar()).render());
            ifElse.add("stmts", insertHeadParamsTo(headTable));
            ifElse.add("stmts", "continue");
            code.add("stmts", ifElse);
        }
        ST elseStmts = tmplGroup.getInstanceOf("simpleStmts");
        ifElse.add("elseStmts", elseStmts);

        genAggrUpdateCode(elseStmts, headTable);
        if (useChoice()) {
            ifUpdated.add("stmts", choiceMadeVar()+"=true");
            if (hasOnlyChoiceVar(rule.getHead())) {
                ifUpdated.add("stmts", "if (true) throw new SocialiteFinishEval()");
            }
        }
    }
    void genAggrUpdateCode(ST code, String headTable) {
        AggrFunction f = headP.getAggrF();
        boolean aggrPrimType= MyType.javaType(f.getArgs().get(0)).isPrimitive();
        String groupbyRetType = MyType.javaTypeName(f.getAggrColumnType());
        code.add("stmts", groupbyRetType + " _$oldAns");
        String groupby = invokeGroupby(headTable, headP);
        code.add("stmts", "_$oldAns="+groupby);

        String eq = aggrPrimType?"==":".equals";
        String contIfOldAns="if("+aggrVar()+eq+"(_$oldAns)) continue";

        if (f.isSimpleAggr()) {
            code.add("stmts", aggrVar()+"="+f.codegen("_$oldAns").render());
            boolean hasNestingAfterAggr=headT.numColumns()>f.getIdx()+1 &&
                                            headT.nestingBeginsAfter(f.getIdx()+1);
            if (hasNestingAfterAggr) {
                Object aggrParam = f.getArgs().get(0);
                code.add("stmts", "if(!("+aggrVar()+eq+"("+aggrParam+"))) continue;");
            } else {
                code.add("stmts", contIfOldAns);
            }
            code.add("stmts", isUpdatedVar()+"="+updateHeadParamsTo(headTable));
        } else {
            code.add("stmts", aggrVar()+"=_$oldAns");
            code.add("stmts", isUpdatedVar()+"="+f.codegen("_$oldAns").render());
        }
    }

    String invokeGroupby(String tableVar, Predicate p, Object... rest) {
        AggrFunction f = p.getAggrF();

        String invoke = tableVar + ".groupby(";
        Object params[] = p.inputParams();
        for (int i = 0; i < f.getIdx(); i++) {
            if (i != 0)
                invoke += ", ";
            invoke += params[i];
        }
        if (rest.length == 1)
            invoke += ", " + rest[0];
        else Assert.true_(rest.length == 0);

        invoke += ")";
        return invoke;
    }

    String assignVars(List lhs, List rhs) {
        assert lhs.size()==rhs.size();
        String assign = "";
        for (int i = 0; i < lhs.size(); i++) {
            assign += lhs.get(i) + "=" + rhs.get(i) + "; ";
        }
        return assign;
    }

    String updateToHead(String table) {
        String insert = table + ".update(";
        boolean first = true;
        Object[] params = headP.outputParams();
        for (Object o : params) {
            if (!first) insert += ", ";
            insert += o;
            first = false;
        }
        insert += ")";
        return insert;
    }

    String insertHeadParamsTo(String tableSlice) {
        String insert = tableSlice + ".insert";
        insert += makeHeadParamsArgs();
        return insert;
    }

    String updateHeadParamsTo(String tableSlice) {
        String update = tableSlice + ".update";
        update += makeHeadParamsArgs();
        return update;
    }

    String makeHeadParamsArgs() {
        String args = "(";
        boolean first=true;
        Object[] params = headP.params.toArray();
        for (Object o:params) {
            if (!first) args += ", ";
            if (o instanceof Function) {
                args += aggrVar();
            } else { args += o; }
            first = false;
        }
        return args+")";
    }

    String insertArgs(String table, Object[] args) {
        String insert = table + ".insert(";
        boolean firstParam = true;
        for (Object a:args) {
            if (!firstParam) insert += ", ";
            insert += a;
            firstParam = false;
        }
        insert += ")";
        return insert;
    }
    String insertArgs(String table, List args) {
        String insert = table + ".insert(";
        boolean firstParam = true;
        for (Object a:args) {
            if (!firstParam) insert += ", ";
            insert += a;
            firstParam = false;
        }
        insert += ")";
        return insert;
    }

    void genInsertCode(ST code, String headTableVar, ST ifUpdated) {
        String inserter = insertHeadParamsTo(headTableVar);
        code.add("stmts", isUpdatedVar()+"=" + inserter);

        ST if_ = tmplGroup.getInstanceOf("if");
        if_.add("cond", isUpdatedVar());
        if_.add("stmts", ifUpdated);
        code.add("stmts", if_);
    }

    String sliceIdxGetter(Table t, Object firstParam) {
        return sliceIdxGetter(t, 0, firstParam);
    }

    String sliceIdxGetter(Table t, int column, Object val) {
        if (isSequential()) return "0";

        int id = t.id();
        Column c = t.getColumn(column);
        if (c.hasRange()) {
            return sliceMapVar() + ".getRangeIndex("+id+","+column+","+val+")";
        } else {
            assert column == 0;
            return sliceMapVar() + ".getHashIndex("+id+","+val+")";
        }
    }

    boolean isReturnType(ST method, Class type) {
        String ret = (String) method.getAttribute("type");
        if (type.getSimpleName().equals(ret))
            return true;
        else
            return false;
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
        if (conf.errorRecovery()) {
            ST if_ = tmplGroup.getInstanceOf("if");
            tryCatch.add("catchStmts", if_);
            if_.add("cond", logCount()+"<10");
            if_.add("stmts", logCount()+"++");
            String log = "UserLog.warn("+msg+")";
            if_.add("stmts", log);

            if_.add("stmts", "_$e.printStackTrace()");
        } else {
            _throw="throw new SociaLiteException("+msg+"+\", \"+_$e)";
            tryCatch.add("catchStmts", _throw);
        }
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
            if_.add("stmts", "continue");
            code.add("stmts", if_);
        }
        return code;
    }

    boolean isSequential() {
        return conf.isSequential();
    }

    boolean isParallel() {
        return !isSequential();
    }

    int headTableWriteLockColumn() {
        int column = 0;
        if (isParallelRule()) {
            column = Analysis.firstShardedColumnWithVar(headP, headT);
            if (column < 0) column = 0;
        }
        return column;
    }

    boolean updateFromRemoteHeadT() {
        if (rule.getBodyP().size() == 1) {
            Table onlyT = tableMap.get(rule.firstP().name());
            if (onlyT instanceof RemoteHeadTable)
                return true;
        }
        return false;
    }

    boolean headTableLockNeeded() {
        if (conf.isSequential()) { return false; }
        if (headT instanceof DeltaTable) { return false; }

        return true;
    }

    boolean headTableLockAtStart() {
        if (!headTableLockNeeded())	{ return false; }
        if (!conf.isDistributed() && Analysis.updateParallelShard(rule, tableMap)) { return true; }
        // !rule.inScc(): HACK!
        if (!conf.isDistributed() && Analysis.isSequentialRule(rule, tableMap) &&
                !rule.inScc()) { return true; }
        return false;
    }

    // true if write lock is needed for head table insertion (per insertion)
    boolean headTableLockAtEnd() {
        if (!headTableLockNeeded()) return false;
        if (headTableLockAtStart()) return false;

        return true;
    }

    String invokeIterate(Predicate p, Set<Variable> resolvedVars) {
        String invokeIterate = null;
        if (iteratePart(p, resolvedVars)) {
            int idx = idxForIteratePart(p);
            CmpOp.CmpType cmpType = cmpTypeForIteratePart(p);
            boolean inclusive = cmpType.inclusive();
            Object cmpVal = cmpValForIteratePart(p);
            if (cmpType.greaterThan()) {
                invokeIterate = ".iterate_part_from_"+idx;
            } else {
                invokeIterate = ".iterate_part_to_"+idx;
            }
            if (getNestingLevel(p, idx) >= 1 && isOutmostIdxColResolved(p)) {
                int idxCol = getOutmostResolvedIdxCol(p);
                Object[] params = p.inputParams();
                invokeIterate += "_by_"+idxCol+"("+params[idxCol]+",";
            } else { invokeIterate += "("; }
            invokeIterate += cmpVal+","+inclusive+", this)";
        } else if (isIndexColResolved(p)) {
            invokeIterate = ".iterate"+getIteratebySuffix(p, "");
        } else {
            invokeIterate = ".iterate(this)";
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
        ST origCode=code;

        if (hasRemoteRuleBody() && tableSendPos().contains(p.getPos())) {
            ST ifLocal = genAccumlRemoteBodyTable(code, p);
            code = ifLocal;
        }
        code.add("stmts", currentPredicateVar() + "=" + p.getPos());

        Table t = getTable(p);
        String invokeIterate = invokeIterate(p, resolved);

        String tableVar = getVarName(p);
        if (isTableSliced(t)) {
            boolean sliceSelected = Analysis.isResolved(resolvedVarsArray, p, p.first());
            if (sliceSelected) {
                tableVar += "[" + sliceIdxGetter(t, p.first()) + "]";
            } else {
                int tid = t.id();
                ST for_ = CodeGen.getVisitorST("for");
                code.add("stmts", for_);
                for_.add("init", "int _$$i=0");
                for_.add("cond", "_$$i<" + sliceMapVar() + ".sliceNum(" + tid + ")");
                for_.add("inc", "_$$i++");
                tableVar += "[_$$i]";
                code = for_;
            }
        }
        code.add("stmts", tableVar+invokeIterate);

        origCode.add("stmts", currentPredicateVar() + "=" + prevp.getPos());
        return true;
    }

    int getPrefixParamsForIter(Set<Variable> resolved, Predicate p) {
        Table t = getTable(p);
        if (t instanceof RemoteHeadTable) return 0;

        int[] indexed = t.indexedCols();
        if (indexed.length == 0) return 0;

        Param[] params = p.inputParams();
        int resolvedPrefix = 0;
        for (int i = 0; i < params.length; i++) {
            if (isConstOrResolved(resolved, params[i]))
                resolvedPrefix++;
            else break;
        }
        if (resolvedPrefix == 0) return 0;

        for (int i = 0; i < indexed.length; i++) {
            if (indexed[i] > resolvedPrefix - 1) {
                if (i == 0) return 0;
                else return indexed[i - 1] + 1;
            }
        }
        return indexed[indexed.length - 1] + 1;
    }

    boolean prefixResolved(Set<Variable> resolved, Object[] params, int prefix) {
        Assert.true_(params.length > prefix);
        for (int i = 0; i < prefix; i++) {
            Object p = params[i];
            if (!isConstOrResolved(resolved, p))
                return false;
        }
        return true;
    }

    boolean allDontCareVars(List params) {
        // returns true if all the params are don't-care variables
        // (if the params have constants, this returns false)
        for (Object o : params) {
            if (o instanceof Variable) {
                Variable v = (Variable) o;
                if (!v.dontCare)
                    return false;
            } else if (o instanceof Function) {
                Function f = (Function) o;
                Set<Variable> inputs = f.getInputVariables();
                for (Variable v : inputs) {
                    if (!v.dontCare)
                        return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    boolean dontCareVar(Object o) {
        if (o instanceof Variable) {
            Variable v = (Variable) o;
            if (v.dontCare)
                return true;
            else
                return false;
        } else {
            return false;
        }
    }
    boolean[] getDontcareFlags(Predicate p) {
        boolean[] dontCares=new boolean[p.inputParams().length];
        boolean dontCareExists=false;
        int i=0;
        for (Param o:p.inputParams()) {
            dontCares[i]=false;
            if (o instanceof Variable) {
                Variable v=(Variable)o;
                if (v.dontCare) {
                    dontCareExists=true;
                    dontCares[i]=true;
                }
            }
            i++;
        }
        if (dontCareExists) return dontCares;
        else return new boolean[] {};
    }

    ST returnIfNotContains(ST code, Predicate p) {
        boolean[] dontCares = getDontcareFlags(p);
        // p.isNegated() is used to generate negated filter
        return ifNotContains(code, p, "return true", dontCares);
    }
    ST continueIfNotContains(ST code, Predicate p) {
        boolean[] dontCares = getDontcareFlags(p);
        // p.isNegated() is used to generate negated filter
        return ifNotContains(code, p, "continue", dontCares);
    }
    ST ifNotContains(ST code, Predicate p, String actionStmt, boolean[] dontcares) {
        if (hasRemoteRuleBody() && tableSendPos().contains(p.getPos())) {
            ST ifLocal = genAccumlRemoteBodyTable(code, p);
            code = ifLocal;
        }
        Table t = getTable(p);

        String contains = ".contains(";
        boolean firstParam = true;
        for (Param o : p.inputParams()) {
            if (!firstParam) contains += ", ";
            contains += o;
            firstParam = false;
        }
        if (dontcares.length>0) {
            contains += ", new boolean[]{";
            for (int i=0; i<dontcares.length; i++) {
                if (i!=0) contains += ",";
                contains += dontcares[i];
            }
            contains += "}";
        }
        contains += ")";

        String condition;
        String var = getVarName(p);
        if (isTableSliced(t)) {
            if (dontcares.length>0 && dontcares[0]) {
                String fallthrou=CodeGen.uniqueVar("_$fallthrou");
                code.add("stmts", "boolean "+fallthrou+"=true");

                ST for_ = tmplGroup.getInstanceOf("for");
                for_.add("init", "int _$$i=0");
                for_.add("cond", "_$$i<"+sliceMapVar()+".sliceNum("+t.id()+")");
                for_.add("inc", "_$$i++");
                code.add("stmts", for_);
                ST if2_ = CodeGen.getVisitorST("if");
                for_.add("stmts", if2_);

                var += "[_$$i]";
                condition = var+contains;
                if2_.add("cond", condition);
                if2_.add("stmts", fallthrou+"=false; break");
                if (p.isNegated()) condition = "!"+fallthrou;
                else condition = fallthrou;
            } else {
                var += "["+sliceIdxGetter(t, p.first())+"]";
                if (p.isNegated()) condition = var+contains;
                else condition = "!" + var+contains;
            }
        } else {
            if (p.isNegated()) condition = var+contains;
            else condition = "!" + var+contains;
        }

        ST if_ = CodeGen.getVisitorST("if");
        code.add("stmts", if_);
        if_.add("cond", condition);
        if_.add("stmts", actionStmt);
        return code;
    }

    ST genVisitMethodFor(Predicate p, Set<Variable> resolved) {
        TIntArrayList idxbyCols = getIndexByCols(p);

        Table t = getTable(p);
        List<ColumnGroup> columnGroups = t.getColumnGroups();
        ST m = null;
        int arity = t.numColumns();
        for (ColumnGroup g : columnGroups) {
            int startCol = g.startIdx();
            int endCol = g.endIdx();

            m = getVisitMethod(p, startCol, endCol, arity);
            if (allDontCaresWithin(p.inputParams(), g.startIdx(), arity-1)) {
                // stop iteration after the visit method m.
                ST if_ = tmplGroup.getInstanceOf("if");
                if_.add("cond", currentPredicateVar() + "==" + p.getPos());
                if_.add("stmts", "return false");
                CodeGen.findCodeBlockFor(m, p).add("endstmt", if_);
                break;
            } else {
                CodeGen.fillVisitMethodBody(m, p, startCol, endCol, resolved, idxbyCols);
            }
        }
        ST code = CodeGen.findCodeBlockFor(m, p);
        return code;
    }

    boolean isResolved(Predicate p, Object param) {
        Set<Variable> resolved = resolvedVarsArray[p.getPos()];
        if (param instanceof Variable)
            return resolved.contains(param);
        Assert.not_true(param instanceof Function);
        return true;
    }

    boolean allDontCaresWithin(Object[] params, int startidx, int endidx) {
        if (startidx > endidx)
            return false;
        for (int i = startidx; i <= endidx; i++) {
            Object p = params[i];
            if (!isConstOrDontCare(p))
                return false;
        }
        return true;
    }

    boolean isTableSliced(Table t) {
        if (isSequential()) return false;
        return t.isSliced();
    }

    public static String getVarName(String table) {
        String firstLetter = table.substring(0, 1);
        String rest = table.substring(1);
        String varName = "_" + firstLetter.toLowerCase() + rest;
        return varName;
    }

    static String visitorClassName(Rule r, Map<String, Table> tableMap) {
        Class visitorClass = CodeGenMain.visitorClass$.get(r.signature(tableMap));
        if (visitorClass!=null) {
            return visitorClass.getSimpleName();
        }
        String name = "Visitor" + r.getHead().name() + "_" + r.id();
        if (r instanceof DeltaRule) {
            DeltaRule dr=(DeltaRule)r;
            if (dr.getTheP().isHeadP()) name += "_delta_head";
            else name += "_delta"+dr.getTheP().getPos();
        }
        return name;
    }

    String heapClass() {
        Function f = headP.getAggrF();
        return "Heap_" + f.name().replace('.', '_');
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

    String currentPredicateVar() { return CodeGen.currentPredicateVar(); }
    String getVarName(Predicate p) { return "$"+getVarName(p.name()) + p.getPos(); }
    String headTableVar() { return "$headTable"; }
    String logCount() { return "$userlogCount"; }
    String firstTableIdx() { return "$firstTableIdx"; }
    String registryVar() { return "$registry"; }
    String lockMapVar() { return "$lockMap"; }
    String sliceMapVar() { return "$sliceMap"; }
    String runtimeVar() { return "$runtime"; }
    String ruleIdVar() { return "$ruleId"; }
    String epochIdVar() { return "$epochId"; }
    String choiceMadeVar() { return "$choiceMade"; }
    String remoteBodyTableMapVar() { return "$remoteBodyTableMap"; }
    String remoteHeadTableMapVar() { return "$remoteHeadTableMap"; }
    String isUpdatedVar() { return "$isUpdated"; }
    String heapVar() { return "$heapQueue"; }
    String aggrVar() { return "$aggrVar"; }

    String deltaStepWindowVar() {
        assert useDeltaStepOpt();
        return "$deltaStepWindow";
    }
}
