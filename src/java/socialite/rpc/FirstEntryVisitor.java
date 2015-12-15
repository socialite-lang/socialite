package socialite.rpc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.crypto.UnsupportedCodecException;
import socialite.tables.*;

public class FirstEntryVisitor extends QueryVisitor {
    public static final Log L= LogFactory.getLog(FirstEntryVisitor.class);

    Tuple tupleVal;

    public boolean visit(int i) {
        tupleVal = new Tuple_int(i);
        return false;
    }

    public boolean visit(long l) {
        tupleVal = new Tuple_long(l);
        return false;
    }

    public boolean visit(float f) {
        tupleVal = new Tuple_float(f);
        return false;
    }

    public boolean visit(double d) {
        tupleVal = new Tuple_double(d);
        return false;
    }

    public boolean visit(Object o) {
        tupleVal = new Tuple_Object(o);
        return false;
    }

    public boolean visit(Tuple t) {
        tupleVal = t;
        return false;
    }

    public Tuple firstTuple() {
        return tupleVal;
    }
}
