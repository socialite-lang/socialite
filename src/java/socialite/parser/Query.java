package socialite.parser;

import socialite.util.AnalysisException;
import socialite.util.InternalException;

public class Query {
    Predicate predicate;
    int iterVal = -1;

    public Query(Predicate p) {
        predicate = p;
    }
    public Predicate getP() {
        return predicate;
    }
    public String toString() {
        return "?-" + predicate + ".";
    }

    public void computeParamTypes(Table t) {
        try {
            predicate.computeVarTypes(t);
        } catch (InternalException e) {
            String msg = "Error in:" + toString() + ", " + e.getMessage();
            throw new AnalysisException(msg);
        }
    }

    public void setIterVal(int val) {
        iterVal = val;
    }

    public int getIterVal() {
        assert iterVal >= 0;
        return iterVal;
    }
}