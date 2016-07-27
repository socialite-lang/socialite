package socialite.parser;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import socialite.codegen.Epoch;
import socialite.parser.antlr.RuleDecl;
import socialite.util.AnalysisException;
import socialite.util.Assert;
import socialite.util.IdFactory;
import socialite.util.InternalException;

public class Rule implements Externalizable {
    private static final long serialVersionUID = 1;

    transient List<Rule> deps = new ArrayList<>();
    transient List<Rule> usedBy = new ArrayList<>();
    transient Epoch epoch=null;

    int epochId;
    int id;
    RuleDecl ruleDecl;
    boolean inScc=false;
    boolean simpleArrayInit=false;
    boolean hasPipelined = false;
    boolean asyncEval = false;
    Table partitionTable;
    Predicate partitionPredicate;

    public Rule() { }

    public Rule(RuleDecl _ruleDecl) {
        ruleDecl = _ruleDecl;
        id = IdFactory.nextRuleId();
    }

    public void setAsyncEval() {
        asyncEval = true;
    }
    public boolean isAsyncEval() { return asyncEval; }

    public void setPartitionTable(Table t, Predicate p) {
        partitionTable = t;
        partitionPredicate = p;
    }
    public Table getPartitionTable() {
        return partitionTable;
    }
    public Predicate getPartitionPredicate() { return partitionPredicate; }

    Set<Function> findFunctions() {
        Set<Function> s = new LinkedHashSet<>();
        if (ruleDecl.head.hasFunctionParam()) {
            for (AggrFunction f:ruleDecl.head.getAggrFuncs()) {
                s.add(f);
            }
        }
        for (Object o:ruleDecl.body) {
            if (!(o instanceof Expr)) continue;
            Expr e=(Expr)o;
            if (!(e.root instanceof AssignOp)) continue;
            AssignOp op = (AssignOp)e.root;
            if (op.fromFunction()) {
                Function f=op.getFunction();
                s.add(f);
            }
        }
        return s;
    }

    public int getEpochId() { return epochId; }
    public void setInScc() { inScc=true; }
    public boolean inScc() { return inScc; }

    public boolean isSimpleUpdate() { return ruleDecl.isSimpleUpdate(); }

    public void copyRuleProperties(Rule r) {
        epochId = r.epochId;
        inScc = r.inScc;
        simpleArrayInit = r.simpleArrayInit;
        hasPipelined = r.hasPipelined;
    }

    public void setEpoch(Epoch _e) {
        epoch = _e;
        epochId = epoch.id();
    }
    public void recomputeDeps() {
        Iterator<Rule> it = deps.iterator();
        while(it.hasNext()) {
            Rule r=it.next();
            if (r.epoch != epoch)
                it.remove();
        }
        it = usedBy.iterator();
        while(it.hasNext()) {
            Rule r=it.next();
            if (r.epoch != epoch)
                it.remove();
        }
    }

    public int id() {return id;}
    public int hashCode() { return id;}

    public boolean equals(Object o) {
        if (!(o instanceof Rule)) return false;
        Rule r=(Rule)o;
        return r.ruleDecl.equals(ruleDecl) && getClass().equals(r.getClass());
    }

    public List<Expr> getExprs() {
        List<Expr> exprlist = null;
        for (Object o:getBody()) {
            if (o instanceof Expr) {
                if (exprlist==null) exprlist = new ArrayList<Expr>();
                exprlist.add((Expr)o);
            }
        }
        if (exprlist==null) return Collections.emptyList();
        else return exprlist;
    }
    public Set<Function> getFunctions() {
        return findFunctions();
    }

    public List<Const> getConstsAssigns() {
        assert simpleArrayInit;

        Param[] params = getHead().inputParams();
        Map<Integer, Const> assigns = new HashMap<>(params.length);
        Map<String, Integer> varPos = new HashMap<>();
        int pos = 0;
        for (Param p: params) {
            if (p instanceof Const) {
                assigns.put(pos, (Const)p);
            } else {
                Variable v = (Variable)p;
                assert v.dontCare || pos > 0;
                varPos.put(v.name, pos);
            }
            pos++;
        }
        for (Literal l:getBody()) {
            if (l instanceof Expr) {
                Expr e = (Expr)l;
                if (e.root instanceof AssignOp) {
                    AssignOp op = (AssignOp) e.root;
                    Variable v = (Variable)op.arg1;
                    pos = varPos.get(v.name);
                    Const _const = (Const)op.arg2;
                    assigns.put(pos, _const);
                }
            }
        }
        List<Const> consts = new ArrayList<>();
        for (int i=1; i<params.length; i++) {
            consts.add(assigns.get(i));
        }
        return consts;
    }
    public List<Const> getConsts() {
        SortedSet<Const> _consts = new TreeSet<>();
        for (Object o:getHead().inputParams()) {
            if (o instanceof Const)
                _consts.add((Const)o);
        }
        for (Object o:getBody()) {
            if (o instanceof Predicate) {
                Predicate p=(Predicate)o;
                for (Object x:p.inputParams()) {
                    if (x instanceof Const)
                        _consts.add((Const)x);
                }
            } else {
                Expr e = (Expr)o;
                _consts.addAll(e.getConsts());
            }
        }
        return new ArrayList<Const>(_consts);
    }
    public void computeParamTypes(Map<String, Table> tableMap) {
        Predicate currentP=null;
        try {
            for (Predicate p:getBodyP()) {
                currentP=p;
                Table t = tableMap.get(p.name());
                p.computeVarTypes(t);
            }
            //currentP=getHead();
            //getHead().computeVarTypes(tableMap);
        } catch (InternalException e) {
            throw new AnalysisException(e, this, currentP);
        }
    }

    public void setHasPipelinedRule() {
        hasPipelined = true;
    }
    public boolean hasPipelinedRules() {
        return hasPipelined;
    }

    public Map<String, Variable> getBodyVariableMap() {
        HashMap<String, Variable> map = new HashMap<>();
        for (Variable v:getBodyVariables()) {
            map.put(v.name, v);
        }
        return map;
    }
    public Set<Variable> getBodyVariables() {
        Set<Variable> vars = new LinkedHashSet<Variable>();
        for (Object o:getBody()) {
            if (o instanceof Predicate) {
                Predicate p = (Predicate)o;
                vars.addAll(p.getVariables());
            } else if (o instanceof Expr) {
                vars.addAll(((Expr)o).getVariables());
            } else if (o instanceof Function) {
                Function f = (Function)o;
                vars.addAll(f.getInputVariables());
                vars.addAll(f.getReturnVars());
            } else {
                Assert.die("Unexpected type, o["+
                        o.getClass().getSimpleName()+"]");
            }
        }
        return vars;
    }

    public boolean hasOnlySimpleAssignExpr() {
        for (Object o:getBody()) {
            if (!(o instanceof Expr))
                return false;
            Expr expr = (Expr)o;
            if (!(expr.root instanceof AssignOp))
                return false;
            AssignOp op=(AssignOp)expr.root;
            if (op.fromFunction()) // not a simple assignment
                return false;
        }
        return true;
    }

    public void setSimpleArrayInit() { simpleArrayInit=true; }
    public boolean isSimpleArrayInit() { return simpleArrayInit; }

    public Set<Variable> getHeadVariables() {
        return ruleDecl.head.getVariables();
    }

    public String name() {
        return ruleDecl.head.name()+"#"+id;
    }
    public Predicate getHead() {
        return ruleDecl.head;
    }

    public List<Literal> getBody() {
        return ruleDecl.body;
    }

    public Predicate firstP() {
        for (Object o:ruleDecl.body){
            if (o instanceof Predicate) return (Predicate)o;
        }
        return null;
    }

    public List<Predicate> getBodyP() {
        return ruleDecl.getBodyP();
    }

    public List<Predicate> getAllP() {
        List<Predicate> list = new ArrayList<>(getBodyP());
        list.add(getHead());
        return list;
    }

    public List<Rule> getDependingRules() { return deps; }
    public List<Rule> getRulesUsingThis() { return usedBy; }

    public void dependOn(Rule r) {
        if (deps.contains(r)) return;
        deps.add(r);
    }

    public void usedBy(Rule r) {
        if (usedBy.contains(r)) return;
        usedBy.add(r);
    }

    public String toString() {
        return ruleDecl.toString();
    }
    public String signature(Map<String, Table> tableMap) {
        String sig="";
        Predicate p=getHead();
        Table t=tableMap.get(p.name());
        sig += p.signature(t.signature()); //sig += p.signature(p.name());
        sig += ":-";
        boolean first=true;
        for (Object o:getBody()) {
            if (!first) sig += ",";
            if (o instanceof Predicate) {
                Predicate p2=(Predicate)o;
                t=tableMap.get(((Predicate)o).name());
                sig += p2.signature(t.signature());
                //sig += p2.signature(p2.name());
            } else {
                Expr e=(Expr)o;
                sig += e.sig();
            }
            first=false;
        }
        sig += ".";
        return sig;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        ruleDecl = new RuleDecl();
        ruleDecl.readExternal(in);
        id = in.readInt();
        epochId = in.readInt();
        inScc = in.readBoolean();
        simpleArrayInit = in.readBoolean();
        hasPipelined = in.readBoolean();
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        ruleDecl.writeExternal(out);
        out.writeInt(id);
        out.writeInt(epochId);
        out.writeBoolean(inScc);
        out.writeBoolean(simpleArrayInit);
        out.writeBoolean(hasPipelined);
    }
}
