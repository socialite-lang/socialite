package socialite.tables;

import java.util.List;
import socialite.visitors.VisitorImpl;
import socialite.visitors.IVisitor;

public interface TableInst {
    int id();
    boolean isEmpty();
    void init(List<Object> args); //  Initializes the table with the args. Only for (flat) array table.
    void clear();
    String name();
    int totalAllocSize(); // total allocated memory.
    void iterate_at(ColumnConstraints constr, int offset, Object v);
    void enableInternalLock(boolean writeOnly);
    void disableInternalLock();
    LockStatus isLockEnabled();

    enum LockStatus {
        enabled,
        writeLockEnabled,
        disabled
    }
}