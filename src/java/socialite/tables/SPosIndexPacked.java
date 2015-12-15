package socialite.tables;

import gnu.trove.impl.sync.TSynchronizedIntIntMap;
import gnu.trove.impl.sync.TSynchronizedLongIntMap;
import gnu.trove.impl.sync.TSynchronizedObjectIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TLongIntHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import socialite.util.concurrent.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SPosIndexPacked {
    public static final Log L = LogFactory.getLog(SPosIndexPacked.class);

    volatile Object index;
    ReadWriteLock rwlock = new ReentrantReadWriteLock();

    public void clear() {
        if (index == null) { return; }

        if (index instanceof TIntIntHashMap) {
            iIndex().clear();
        } else if (index instanceof TLongIntHashMap) {
            lIndex().clear();
        } else if (index instanceof TObjectIntHashMap) {
            oIndex().clear();
        } else {
            throw new AssertionError("Unexpected index type:" + index.getClass().getSimpleName());
        }
    }
    public int size() {
        if (index == null) { return 0; }

        if (index instanceof TSynchronizedIntIntMap) {
            return iIndex().size();
        } else if (index instanceof TSynchronizedLongIntMap) {
            return lIndex().size();
        } else if (index instanceof TSynchronizedObjectIntMap) {
            return oIndex().size();
        } else {
            throw new AssertionError("Unexpected index type:" + index.getClass().getSimpleName());
        }
    }
    // int-type key
    @SuppressWarnings({"unchecked", "rawtypes"})
    TIntIntHashMap iIndex() {
        if (index == null) {
            synchronized (this) {
                if (index == null) {
                    index = new TIntIntHashMap(64, 0.75f);
                }
            }
        }
        return (TIntIntHashMap)index;
    }
    public void add(int key, int pos) {
        rwlock.writeLock().lock();
        try {
            if (iIndex().containsKey(key)) {
                throw new AssertionError("Key " + key + " already exists!");
            } else {
                iIndex().put(key, pos);
            }
        } finally {
            rwlock.writeLock().unlock();
        }
    }
    public boolean contains(int key) {
        rwlock.readLock().lock();
        try {
            return iIndex().containsKey(key);
        } finally {
            rwlock.readLock().unlock();
        }
    }
    public ConcurrentReadIntArrayList get(int key) {
        rwlock.readLock().lock();
        try {
            ConcurrentReadIntArrayList list = null;
            if (iIndex().containsKey(key)) {
                list = new ConcurrentReadIntArrayList(1);
                list.add(iIndex().get(key));
            }
            return list;
        } finally {
            rwlock.readLock().unlock();
        }
    }
    public int get1(int key) {
        rwlock.readLock().lock();
        try {
            if (iIndex().containsKey(key)) {
                return iIndex().get(key);
            } else {
                return -1;
            }
        } finally {
            rwlock.readLock().unlock();
        }
    }

    // long-type key
    @SuppressWarnings({"unchecked", "rawtypes"})
    TLongIntHashMap lIndex() {
        if (index == null) {
            synchronized (this) {
                if (index == null) {
                    index = new TLongIntHashMap(64, 0.75f);
                }
            }
        }
        return (TLongIntHashMap) index;
    }
    public void add(long key, int pos) {
        rwlock.writeLock().lock();
        try {
            if (lIndex().containsKey(key)) {
                throw new AssertionError("Key " + key + " already exists!");
            } else {
                lIndex().put(key, pos);
            }
        } finally {
            rwlock.writeLock().unlock();
        }
    }
    public boolean contains(long key) {
        rwlock.readLock().lock();
        try {
            return lIndex().containsKey(key);
        } finally {
            rwlock.readLock().unlock();
        }
    }
    public ConcurrentReadIntArrayList get(long key) {
        rwlock.readLock().lock();
        try {
            ConcurrentReadIntArrayList list = null;
            if (lIndex().containsKey(key)) {
                list = new ConcurrentReadIntArrayList(1);
                list.add(lIndex().get(key));
            }
            return list;
        } finally {
            rwlock.readLock().unlock();
        }
    }
    public int get1(long key) {
        rwlock.readLock().lock();
        try {
            if (lIndex().containsKey(key)) {
                return lIndex().get(key);
            } else {
                return -1;
            }
        } finally {
            rwlock.readLock().unlock();
        }
    }

    // Object-type key
    @SuppressWarnings({"unchecked", "rawtypes"})
    TObjectIntHashMap oIndex() {
        if (index == null) {
            synchronized (this) {
                if (index == null) {
                    index = new TObjectIntHashMap(64, 0.75f);
                }
            }
        }
        return (TObjectIntHashMap) index;
    }
    public void add(Object key, int pos) {
        rwlock.writeLock().lock();
        try {
            if (oIndex().containsKey(key)) {
                throw new AssertionError("Key " + key + " already exists!");
            } else {
                oIndex().put(key, pos);
            }
        } finally {
            rwlock.writeLock().unlock();
        }
    }
    public boolean contains(Object key) {
        rwlock.readLock().lock();
        try {
            return oIndex().containsKey(key);
        } finally {
            rwlock.readLock().unlock();
        }
    }
    public ConcurrentReadIntArrayList get(Object key) {
        rwlock.readLock().lock();
        try {
            ConcurrentReadIntArrayList list = null;
            if (oIndex().containsKey(key)) {
                list = new ConcurrentReadIntArrayList(1);
                list.add(oIndex().get(key));
            }
            return list;
        } finally {
            rwlock.readLock().unlock();
        }
    }
    public int get1(Object key) {
        rwlock.readLock().lock();
        try {
            if (oIndex().containsKey(key)) {
                return oIndex().get(key);
            } else {
                return -1;
            }
        } finally {
            rwlock.readLock().unlock();
        }
    }
}
