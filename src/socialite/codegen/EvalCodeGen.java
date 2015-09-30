package socialite.codegen;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import socialite.dist.master.MasterNode;
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

    boolean isDistributed() {
        return MasterNode.getInstance() != null;
    }
    boolean isGenerated() {
        return evalTmpl.getAttribute("name")!=null;
    }
    public String generate() {
        if (isGenerated()) return evalTmpl.render();

        evalTmpl.add("name", evalName);
        if (isDistributed()) { evalTmpl.add("evalBaseClass", "EvalDist");}
        else { evalTmpl.add("evalBaseClass", "EvalParallel"); }

        for (Table t:newTables) {
            assert !(t instanceof GeneratedT):"newTables should not contain instances of GeneratedT";
            // table array decl            
            String sliceNum = "partitionMap.partitionNum("+t.id()+")";
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
                String base = "partitionMap.localBeginIndex("+t.id()+")";
                String size = "partitionMap.localSize("+t.id()+")";
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

    public String evalName() { return evalPackage+"."+evalName; }
    String registryVar() { return "tableRegistry"; }

    String varName(Table t) {
        String firstLetter = t.name().substring(0, 1);
        String rest = t.name().substring(1);
        String varName = "_" + firstLetter.toLowerCase() + rest;
        return varName;
    }
}
