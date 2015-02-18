package socialite.eval;

import socialite.tables.ConcurrentTableInst;
import socialite.tables.TableInst;
import socialite.tables.Tuple;

import java.util.Iterator;

public class ConcurrentLoadTask implements Task {
    ConcurrentTableInst table;
    Iterator<Tuple> iter;
    public ConcurrentLoadTask(TableInst _table, Iterator<Tuple> _iter) {
        table = (ConcurrentTableInst)_table;
        iter = _iter;
    }

    public void setPriority(int priority) {    }

    public int getPriority() { return 0; }

    public void run(Worker w) {
        table.insertAtomic(iter);
    }

    public int getRuleId() {
        return 0;
    }

    public int getEpochId() {
        return 0;
    }

}
