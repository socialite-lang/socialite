package socialite.tables;

import java.util.concurrent.locks.ReadWriteLock;

import gnu.trove.map.hash.TIntObjectHashMap;
import org.roaringbitmap.IntIterator;
import org.roaringbitmap.MyRoaringBitmap;
import org.roaringbitmap.RoaringBitmap;

import socialite.visitors.IVisitor;
import socialite.visitors.VisitorImpl;
import socialite.collection.SArrayList;

/**
 * Helps iterating/adding values in indices.
 * It uses a reader-writer lock if the host table is modifiable
 */

public class IndexUtil {
    public static int makePos(int nodePos, int offset) { return (nodePos << 8) | offset; }
    public static int getPos(int val) { return (val >>> 8); }
    public static int getOffset(int val) { return (val & 0xff); }

    final TableInst table;
    volatile ReadWriteLock rwlock;
    public IndexUtil(TableInst _table, ReadWriteLock _rwlock) {
        table = _table;
        rwlock = _rwlock;
    }

    void wlock() {
        if (table.isLockEnabled() == TableInst.LockStatus.disabled) { return; }
        rwlock.writeLock().lock();
    }
    void wunlock() {
        if (table.isLockEnabled() == TableInst.LockStatus.disabled) { return; }
        rwlock.writeLock().unlock();
    }
    void rlock() {
        if (table.isLockEnabled() == TableInst.LockStatus.disabled) { return; }
        rwlock.readLock().lock();
    }
    void runlock() {
        if (table.isLockEnabled() == TableInst.LockStatus.disabled) { return; }
        rwlock.readLock().unlock();
    }

    RoaringBitmap getIndex(ColumnValue colVal, IndexMap indexMap) {
        int col = colVal.getColumn();
        Class type = colVal.getType();
        if (type.equals(int.class)) {
            int ival = colVal.getValue(0);
            return indexMap.get(col).get(ival);
        } else if (type.equals(long.class)) {
            long lval = colVal.getValue(0L);
            return indexMap.get(col).get(lval);
        } else {
            Object val = colVal.getValue();
            return indexMap.get(col).get(val);
        }
    }
    /* Returns a new non-shared index, satisfying the given constraints.
     */
    public RoaringBitmap getExclusiveIndex(IndexMap indexMap,
                                  ColumnConstraints constr) {
        //rlock();
        try {
            if (constr.size() == 1) {
                Constraint c = constr.getAt(0);
                if (c instanceof ColumnValue) {
                    return getIndex((ColumnValue)c, indexMap);
                }
            }
            RoaringBitmap[] indices = new RoaringBitmap[constr.size()];
            int idxlen = 0;
            for (int i=0; i<constr.size(); i++) {
                Constraint c = constr.getAt(i);
                if (!(c instanceof ColumnValue)) { continue; }

                RoaringBitmap tmpidx = getIndex((ColumnValue)c, indexMap);
                if (tmpidx != null) {
                    indices[idxlen++] = tmpidx;
                }
            }
            if (idxlen == 0) {
                return null;
            } else if (idxlen == 1) {
                return indices[0];
            } else {
                return MyRoaringBitmap.and(indices);
            }
        } finally {
            //runlock();
        }
    }

    public void addIndex(SIndex index, int ival, int pos) {
        wlock();
        try { index.add(ival, pos); }
        finally { wunlock(); }
    }
    public void addIndex(SIndex index, long lval, int pos) {
        wlock();
        try { index.add(lval, pos); }
        finally { wunlock(); }
    }
    public void addIndex(SIndex index, Object oval, int pos) {
        wlock();
        try { index.add(oval, pos); }
        finally { wunlock(); }
    }

    public void iterateBy(IndexMap indexMap, ColumnConstraints constr, Object v, TableInst table) {
        RoaringBitmap index = getExclusiveIndex(indexMap, constr);
        if (index == null) { return; }
        IntIterator iter = index.getIntIterator();
        while (iter.hasNext()) {
            int offset = iter.next();
            table.iterate_at(constr, offset, v);
        }
    }
}
