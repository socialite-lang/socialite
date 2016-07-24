package socialite.codegen;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import socialite.parser.*;

import socialite.parser.antlr.ColumnGroup;
import socialite.tables.QueryVisitor;
import socialite.tables.TableUtil;

//import org.antlr.stringtemplate.StringTemplate;
//import org.antlr.stringtemplate.StringTemplateGroup;

import gnu.trove.map.hash.TIntObjectHashMap;

public class QueryCodeGen {
    static String queryPackage = "socialite.query";
    static int queryCount = 0;

    String queryVisitorName;
    Query query;
    QueryVisitor queryVisitor;
    STGroup tmplGroup;
    ST queryTmpl;
    Table table;
    Predicate queryP;
    TIntObjectHashMap<Variable> constVars;

    public QueryCodeGen(Query _query, Map<String, Table> _tableMap, QueryVisitor qv) {
        query = _query;
        tmplGroup = CodeGenBase.getVisitorGroup();
        queryTmpl = tmplGroup.getInstanceOf("class");
        table = _tableMap.get(query.getP().name());
        constVars = makeConstVars(query.getP());
        queryP = makeNewPredicate(query.getP(), constVars);

        queryVisitorName = "Query" + queryP.name() + "_" + queryCount++;
        assert !queryP.hasFunctionParam();
    }

    @SuppressWarnings("unchecked")
    Predicate makeNewPredicate(Predicate oldP, TIntObjectHashMap<Variable> constVars) {
        Predicate p = oldP.clone();
        for (int i = 0; i < oldP.params.size(); i++) {
            Object o = oldP.params.get(i);
            if (o instanceof Const)
                p.params.set(i, constVars.get(i));
        }
        return p;
    }

    TIntObjectHashMap<Variable> makeConstVars(Predicate p) {
        TIntObjectHashMap<Variable> vars = new TIntObjectHashMap<Variable>();
        Column cols[] = table.getColumns();
        for (int i = 0; i < p.inputParams().length; i++) {
            Param o = p.inputParams()[i];
            if (o instanceof Const) {
                Const cons = (Const) o;
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
        return queryPackage + "." + queryVisitorName;
    }

    public String generate() {
        if (queryTmpl.getAttribute("packageStmt") != null) {
            return queryTmpl.render();
        }

        queryTmpl.add("packageStmt", "package " + queryPackage);
        queryTmpl.add("description", "for query: " + query);
        queryTmpl.add("modifier", "public");
        queryTmpl.add("name", queryVisitorName);

        importTable();

        queryTmpl.add("extends", "extends "+table.visitorClass());
        queryTmpl.add("interfaces", "QueryRunnable");

		/* declare fields */
        String tableClass = table.className();
        if (isTablePartitioned(table)) {
            tableClass += "[]";
        }
        queryTmpl.add("fieldDecls", tableClass + " " + varName(table));

        if (paramNum() > 1) {
            Class[] types = getTupleTypes();
            ST tupTmpl = tmplGroup.getInstanceOf("tuple");
            for (int i = 0; i < types.length; i++) {
                tupTmpl.add("types", types[i]);
            }
            String tupleCls = tupTmpl.render();
            String tupleDecl = tupleCls + " " + tupleVar() + " = new " + tupleCls + "()";
            queryTmpl.add("fieldDecls", tupleDecl);
        }

        queryTmpl.add("fieldDecls", "TablePartitionMap " + partitionMapVar());
        queryTmpl.add("fieldDecls", "QueryVisitor " + queryVisitorVar());
        queryTmpl.add("fieldDecls", "boolean " + isKilledVar() + "=false");
        for (Variable v : queryP.getVariables()) {
            queryTmpl.add("fieldDecls", MyType.javaTypeName(v) + " " + v);
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
        for (int i = 0, j = 0; i < types.length; i++) {
            if (iterColumn() == i) types[i] = int.class;
            else types[i] = table.types()[j++];
        }
        return types;
    }

    int iterColumn() {
        if (table instanceof IterTableMixin) {
            IterTableMixin it = (IterTableMixin) table;
            return it.iterColumn();
        } else return -1;
    }

    String argVarName(int argIdx) {
        return "$a" + argIdx;
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
        ST run = getNewMethodTmpl("run", "public", "void");
        queryTmpl.add("methodDecls", run);

        ST code = run;
        String tableVar = varName(table);
        if (isTablePartitioned(table)) {
            boolean partitionSelected = isFirstParamResolved();
            Variable v = constVars.get(0);
            if (partitionSelected) {
                tableVar += "[" + partitionMapVar() + ".getIndex(" + table.id() + "," + v + ")" + "]";
            } else {
                ST for_ = tmplGroup.getInstanceOf("for");
                for_.add("init", "int $i=0");
                for_.add("cond", "$i<" + partitionMapVar() + ".partitionNum(" + table.id() + ")");
                for_.add("inc", "$i++");
                tableVar += "[$i]";
                run.add("stmts", for_);
                code = for_;
            }
        }
        String invokeIterate;
        if (isIndexColResolved()) {
            String by = "new ColumnConstraints()";
            Param[] params = queryP.inputParams();
            for (int i=0; i<params.length; i++) {
                Param p = params[i];
                if (table.getColumn(i).isIndexed() && constVars.get(i) != null) {
                    by += ".add("+i+", "+p+")";
                }
            }
            invokeIterate = ".iterate_by("+by+", this)";
        } else {
            invokeIterate = ".iterate(this)";
        }
        code.add("stmts", tableVar + invokeIterate);

        run.add("stmts", queryVisitorVar() + ".finish()");
    }

    boolean isIndexColResolved() {
        for (int i : constVars.keys()) {
            if (table.getColumn(i).isIndexed()) {
                return true;
            }
        }
        return false;
    }

    boolean isFirstParamResolved() {
        return constVars.keySet().contains(0);
    }

    void generateTerminateMethods() {
        ST kill = getNewMethodTmpl("kill", "public", "void");
        queryTmpl.add("methodDecls", kill);
        kill.add("stmts", isKilledVar() + "=true");
    }

    void generateSetArgs() {
        ST setargs = getNewMethodTmpl("setArgs", "public", "void");
        queryTmpl.add("methodDecls", setargs);
        setargs.add("args", "List $params");
        setargs.add("stmts", "assert $params.size()==" + constVars.size());

        int paramIdx = 0;
        Object[] params = queryP.inputParams();
        for (int i = 0; i < params.length; i++) {
            if (constVars.containsKey(i)) {
                Variable v = constVars.get(i);
                String type = "(" + MyType.javaObjectTypeName(v) + ")";
                setargs.add("stmts", v + "=" + type + "$params.get(" + paramIdx + ")");
                paramIdx++;
            }
        }
    }

    void generateSetQueryVisitor() {
        ST setqv = getNewMethodTmpl("setQueryVisitor", "public", "void");
        queryTmpl.add("methodDecls", setqv);
        setqv.add("args", "QueryVisitor $qv");
        setqv.add("stmts", queryVisitorVar() + "=$qv");
    }

    ST getVisitMethod(int startCol, int endCol, int numColumns) {
        String name = "visit" + CodeGenBase.getVisitColumns(startCol, endCol, numColumns);

        ST method = getNewMethodTmpl(name, "public", "boolean");
        method.add("ret", "return true");
        Predicate p = queryP;
        CodeGenBase.addArgTypes(method, p, startCol, endCol);
        queryTmpl.add("methodDecls", method);
        return method;
    }

    boolean allDontCares(int from) {
        Predicate p = queryP;
        Object[] params = p.inputParams();
        for (int i = from; i < params.length; i++) {
            if (!(params[i] instanceof Const)) {
                return false;
            }
        }
        return true;
    }

    void generateVisitMethod() {
        List<ColumnGroup> columnGroups = table.getColumnGroups();
        int numColumns = table.numColumns();
        Collection<Variable> resolved = constVars.valueCollection();
        ST method = null;
        Predicate p = queryP;

        for (ColumnGroup g : columnGroups) {
            int startCol = g.startIdx();
            int endCol = g.endIdx();
            if (allDontCares(startCol)) {
                method.add("stmts", "return false");
                break;
            }
            method = getVisitMethod(startCol, endCol, numColumns);
            CodeGenBase.fillVisitMethodBody(method, p, startCol, endCol, resolved);
        }
        invokeQueryVisitor(method);
    }

    int paramNum() {
        int num = queryP.inputParams().length;
        if (table instanceof IterTableMixin) { num++; }
        return num;
    }

    void invokeQueryVisitor(ST method) {
        if (paramNum() == 1) {
            Object param = queryP.inputParams()[0];
            String invoke = queryVisitorVar() + ".visit(" + param + ")";
            method.add("stmts", invoke);
        } else {
            int i = 0;
            for (Object param : queryP.inputParams()) {
                if (i == iterColumn()) {
                    method.add("stmts", tupleVar() + "._" + i + "=" + query.getIterVal());
                    i++;
                }
                method.add("stmts", tupleVar() + "._" + i + "=" + param);
                i++;
            }
            String invoke = "boolean $cont=" + queryVisitorVar() + ".visit(" + tupleVar() + ")";
            method.add("stmts", invoke);
            method.add("stmts", "if(!$cont) return false");
        }
        method.add("stmts", "if(" + isKilledVar() + ") throw new SocialiteFinishEval()");
    }

    String tupleVar() {
        return "tuple";
    }

    boolean isTablePartitioned(Table t) {
        return !(t instanceof GeneratedT);
    }

    void generateConstructor() {
        ST m = getNewMethodTmpl(queryVisitorName, "public", "");
        queryTmpl.add("methodDecls", m);

        String tableClass = table.className();
        if (isTablePartitioned(table)) {
            tableClass += "[]";
        }
        String var = varName(table);
        String argVar = "_$"+var;
        m.add("args", tableClass + " " + argVar);
        m.add("stmts", var + "=" + argVar);
        m.add("args", "QueryVisitor _$queryVisitor");
        m.add("stmts", queryVisitorVar() + "=_$queryVisitor");
        m.add("args", "TablePartitionMap _$partitionMap");
        m.add("stmts", partitionMapVar() + "=_$partitionMap");
    }

    void importTable() {
        String tableClass = TableUtil.getTablePath(table.className());
        queryTmpl.add("imports", tableClass);
    }

    String queryVisitorVar() { return "$queryVisitor"; }

    String partitionMapVar() {
        return "$partitionMap";
    }

    String isKilledVar() {
        return "$isKilled";
    }
}