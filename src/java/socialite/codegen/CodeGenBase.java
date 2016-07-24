package socialite.codegen;


import gnu.trove.TIntCollection;

import java.util.Collection;
import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import socialite.parser.Const;
import socialite.parser.MyType;
import socialite.parser.Predicate;
import socialite.parser.Table;
import socialite.parser.Variable;
import socialite.util.IdFactory;
import socialite.util.MySTGroupFile;

// Utility class for code generation
// used mostly by JoinerCodeGen and UpdaterCodeGen
public class CodeGenBase {
    static String visitorBaseGroupFile = "VisitorBase.stg";
    static String visitorGroupFile = "Visitor.stg";
    static String evalGroupFile = "EvalEpoch.stg";
    static String tupleGroupFile = "Tuple.stg";
    static String combinedIterateGroupFile = "CombinedIterator.stg";

    static STGroup combinedIterateGroup = null;
    static STGroup visitorBaseGroup = null;
    static STGroup visitorTmplGroup = null;
    static STGroup evalTmplGroup = null;
    static STGroup tupleGroup = null;

    static {
        visitorTmplGroup = new MySTGroupFile(CodeGenBase.class.getResource(visitorGroupFile));
        visitorTmplGroup.load();

        combinedIterateGroup = new MySTGroupFile(JoinerCodeGen.class.getResource(combinedIterateGroupFile));
        combinedIterateGroup.load();

        visitorBaseGroup = new MySTGroupFile(VisitorBaseGen.class.getResource(visitorBaseGroupFile));
        visitorBaseGroup.load();

        evalTmplGroup = new MySTGroupFile(EvalCodeGen.class.getResource(evalGroupFile));
        evalTmplGroup.load();

        tupleGroup = new MySTGroupFile(TupleCodeGen.class.getResource(tupleGroupFile));
        tupleGroup.load();
    }

    public static String uniqueVar(String prefix) {
        return prefix+IdFactory.nextVarId();
    }

    public static STGroup getCombinedIterateGroup() {
        return combinedIterateGroup;
    }
    public static STGroup getVisitorGroup() {
        return visitorTmplGroup;
    }
    public static STGroup getVisitorBaseGroup() {
        return visitorBaseGroup;
    }

    public static ST getVisitorST(String name) {
        STGroup group=getVisitorGroup();
        return group.getInstanceOf(name);
    }

    public static STGroup getEvalGroup() {
        return evalTmplGroup;
    }

    public static STGroup getTupleGroup() {
        return tupleGroup;
    }

    public static ST expr() { return getVisitorST("expr"); }

    public static ST stmts() { return getVisitorST("simpleStmts"); }

    public static String visitorClass(Table table) {
        return table.visitorClass();
    }

    public static Class[] getArgTypes(Table t, int startCol, int endCol) {
        Class[] result = new Class[endCol-startCol+1];
        Class[] types = t.types();
        for (int i=startCol; i<=endCol; i++) {
            Class type=types[i];
            result[i-startCol] = type;
        }
        return result;
    }
    public static void addArgTypes(ST method, Predicate p, int startCol, int endCol) {
        Object[] params = p.inputParams();
        for (int i=startCol; i<=endCol; i++) {
            String arg="_"+(i-startCol); // fillVisitMethodBody
            method.add("args", MyType.visitTypeName(params[i])+" "+arg);
        }
    }

    public static void fillVisitMethodBody(ST method, Predicate p,
                                           int startCol, int endCol,
                                           Collection<Variable> resolved) {
        Object[] params = p.inputParams();
        for (int i=startCol; i<=endCol; ) {
            String arg="_"+(i-startCol); // same as addArgTypes
            if (params[i] instanceof Variable &&
                    ((Variable)params[i]).dontCare) {
                i++;
                continue;
            }
            if (resolved.contains(params[i])) {
                assert !p.isNegated();
                ST if_=generateFilter(params[i], arg);
                method.add("stmts", if_);
                i++;
            } else if (params[i] instanceof Variable) {
                Variable v=(Variable)params[i];
                method.add("stmts", v+"=("+v.type.getSimpleName()+")"+arg);
                i++;
            } else { // Constants
                assert params[i] instanceof Const;
                ST if_=generateFilter(params[i], arg);
                method.add("stmts", if_);
                i++;
            }
        }
    }
    static ST generateFilter(Object param, Object arg) {
        ST if_=getVisitorGroup().getInstanceOf("if");
        if (MyType.isPrimitive(param)) {
            if_.add("cond", param+"!="+arg);
        } else {
            if_.add("cond", "!"+param+".equals("+arg+")");
        }
        if_.add("stmts", "return false");
        return if_;
    }

    public static String getVisitColumns(int start, int end, int arity) {
        if (end == arity-1) { return ""; }

        String result="";
        for (int i=start; i<=end; i++) { result += "_"+i; }
        return result;
    }
}