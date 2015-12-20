package socialite.rpc;

import socialite.tables.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * Bridges between Socialite objects and Thrift structures
 */
public class Bridge {
    public static ScalarVal getScalarVal(Object o) {
        ScalarVal scalarVal = new ScalarVal();
        if (o instanceof Integer) {
            scalarVal.setNum((Integer)o);
        } else if (o instanceof Long) {
            scalarVal.setNum((Long)o);
        } else if (o instanceof Float) {
            scalarVal.setReal((Float)o);
        } else if (o instanceof Double) {
            scalarVal.setReal((Double)o);
        } else if (o instanceof String) {
            scalarVal.setStr((String)o);
        } else {
            scalarVal.setStr(o.toString());
        }
        return scalarVal;
    }

    public static TTuple translate(Tuple t) {
        TTuple ttuple = new TTuple();
        if (t.size() == 1) {
            ttuple.setCol0(getScalarVal(t.get(0)));
        } else if (t.size() == 2) {
            ttuple.setCol0(getScalarVal(t.get(0)));
            ttuple.setCol1(getScalarVal(t.get(1)));
        } else if (t.size() == 3) {
            ttuple.setCol0(getScalarVal(t.get(0)));
            ttuple.setCol1(getScalarVal(t.get(1)));
            ttuple.setCol2(getScalarVal(t.get(2)));
        } else {
            Map<Short, ScalarVal> colValMap = new HashMap<>();
            for (int i=0; i<t.size(); i++) {
                colValMap.put((short)i, getScalarVal(t.get(i)));
            }
            ttuple.setColValMap(colValMap);
        }
        return ttuple;
    }

}
