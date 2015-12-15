package socialite.tables;

import java.util.Iterator;

public interface ConcurrentTableInst extends TableInst {
    public void insertAtomic(Iterator<Tuple> iterator);
}
