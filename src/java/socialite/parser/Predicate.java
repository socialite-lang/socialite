package socialite.parser;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import socialite.util.InternalException;
import socialite.collection.SArrayList;

public class Predicate implements Literal {
    private static final long serialVersionUID = 1;

    String name;
    public SArrayList<Param> params;
    transient Param[] inputParams; /* contains params -- (for functions, contains args of functions */
    boolean isNegated;
    int posAtRuleBody;
    boolean isHeadPredicate;

    @Override
    public Predicate clone() {
        SArrayList<Param> newParams = new SArrayList<>();
        newParams.addAll(params);
        Predicate p = new Predicate(name, newParams);
        p.isNegated = isNegated;
        p.posAtRuleBody = posAtRuleBody;
        p.isHeadPredicate = isHeadPredicate;
        return p;
    }

    public Predicate() {
    }

    public Predicate(String _name, List<Param> _params) {
        name = _name;
        params = new SArrayList<Param>(_params);
        isNegated = false;
        posAtRuleBody = -1;
        isHeadPredicate = false;
        setAggrFuncIdx();
    }

    void setAggrFuncIdx() {
        for (int i = 0; i < params.size(); i++) {
            Object p = params.get(i);
            if (p instanceof AggrFunction)
                ((AggrFunction) p).setIdx(i);
        }
    }

    public void setName(String _name) {
        name = _name;
    }

    public String name() {
        return name;
    }

    public int getPos() {
        return posAtRuleBody;
    }

    public void setPos(int i) {
        posAtRuleBody = i;
    }

    public boolean isHeadP() {
        return isHeadPredicate;
    }

    public void setAsHeadP() {
        isHeadPredicate = true;
    }

    public void setNegated() {
        isNegated = true;
    }

    public boolean isNegated() {
        return isNegated;
    }

    public List<Object> getConstValues() {
        List<Object> consts = new ArrayList<Object>();
        for (int i = 0; i < params.size(); i++) {
            if (params.get(i) instanceof Const) {
                Const c = (Const) params.get(i);
                consts.add(c.val);
            }
        }
        return consts;
    }

    public String signature(String tableSignature) {
        return descr(tableSignature, true);
    }

    public String toString() {
        return descr(name(), false);
    }

    String descr(String _name, boolean sig) {
        String result = _name;
        if (isNegated) result = "!" + result;

        result += "(";
        for (int i = 0; i < params.size(); i++) {
            Object p = params.get(i);
            if (sig) {
                result += p;
            } else {
                if (p instanceof Const) {
                    result += ((Const) p).constValStr();
                } else {
                    result += p;
                }
            }
            if (i != params.size() - 1) result += ",";
        }
        result += ")";

        return result;
    }

    public boolean hasFunctionParam() {
        for (Param o : params) {
            if (o instanceof Function) {
                return true;
            }
        }
        return false;
    }

    public Function getF() {
        for (Param o : params) {
            if (o instanceof Function)
                return (Function) o;
        }
        return null;
    }

    public AggrFunction getAggrF() {
        for (Param o : params) {
            if (o instanceof AggrFunction)
                return (AggrFunction) o;
        }
        return null;
    }
    public List<AggrFunction> getAggrFuncs() {
        ArrayList<AggrFunction> funcs = new ArrayList<AggrFunction>();
        for (Param o:params) {
            if (o instanceof AggrFunction) {
                funcs.add((AggrFunction)o);
            }
        }
        return funcs;
    }

    public int firstFunctionIdx() {
        int i = 0;
        for (Param param:params) {
            if (param instanceof Function) {
                return i;
            }
            i++;
        }
        assert false : "should not reach here";
        return -1;
    }

    public int functionIdx(AggrFunction f) {
        int i=0;
        for (Param param:params) {
            if (param == f) {
                return i;
            }
            i++;
        }
        return -1;
    }
    public Param removeParamAt(int i) {
        inputParams = null;
        return params.remove(i);
    }

    public Set<Variable> getVariables() {
        Set<Variable> vars = new LinkedHashSet<>();
        for (Param p : params) {
            if (p instanceof Variable) vars.add((Variable) p);
            if (p instanceof AggrFunction) {
                AggrFunction f = (AggrFunction) p;
                vars.addAll(f.getInputVariables());
            }
        }
        return vars;
    }

    public Param paramAt(int pos) {
        return params.get(pos);
    }
    public Param first() {
        return params.get(0);
    }

    public Param last() {
        return params.get(params.size() - 1);
    }

    public Param[] inputParams() {
        if (inputParams == null) {
            List<Param> _params = new ArrayList<>();
            for (Param o : params) {
                if (o instanceof AggrFunction) {
                    AggrFunction f = (AggrFunction) o;
                    for (Param arg : f.getArgs()) _params.add(arg);
                } else {
                    _params.add(o);
                }
            }
            inputParams = _params.toArray(new Param[0]);
        }
        return inputParams;
    }

    public void computeVarTypes(Table t) throws InternalException {
        Class<?>[] types = t.types();
        for (int i = 0; i < params.size(); ) {
            if (params.get(i) instanceof Variable) {
                Variable v = (Variable) params.get(i);
                v.setType(types[i]);
                i++;
            } else if (params.get(i) instanceof AggrFunction) {
                AggrFunction f = (AggrFunction) params.get(i);
                i += f.args.size();
            } else {
                i++; // constants
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        params = new SArrayList(0);
        params.readExternal(in);
        isNegated = in.readBoolean();
        posAtRuleBody = in.readInt();
        char[] n = new char[in.readInt()];
        for (int i = 0; i < n.length; i++)
            n[i] = in.readChar();
        name = new String(n);
        isHeadPredicate = in.readBoolean();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        params.writeExternal(out);
        out.writeBoolean(isNegated);
        out.writeInt(posAtRuleBody);
        out.writeInt(name.length());
        out.writeChars(name);
        out.writeBoolean(isHeadPredicate);
    }
}
