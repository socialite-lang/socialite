package socialite.tables;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.util.Assert;

import java.util.List;

public abstract class AbstractTableInst implements TableInst {
    public static final Log L=LogFactory.getLog(AbstractTableInst.class);

    public void init(List<Object> args) {
        throw new UnsupportedOperationException();
    }
    public int totalAllocSize() {
        throw new UnsupportedOperationException();
    }
}
