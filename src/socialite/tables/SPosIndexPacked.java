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

public class SPosIndexPacked {
    public static final Log L = LogFactory.getLog(SPosIndexPacked.class);

    volatile Object index;

    public void clear() {
        if (index == null) { return; }

        if (index instanceof TSynchronizedIntIntMap) {
            iIndex().clear();
        } else if (index instanceof TSynchronizedLongIntMap) {
            lIndex().clear();
        } else if (index instanceof TSynchronizedObjectIntMap) {
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
    TSynchronizedIntIntMap iIndex() {
        if (index == null) {
            synchronized (this) {
                if (index == null) {
                    index = new TSynchronizedIntIntMap(new TIntIntHashMap(64, 0.75f));
                }
            }
        }
        return (TSynchronizedIntIntMap) index;
    }
    public void add(int key, int pos) {
        if (iIndex().containsKey(key)) {
            throw new AssertionError("Key " + key + " already exists!");
        } else {
            iIndex().put(key, pos);
        }
    }
    public boolean contains(int key) {
        return iIndex().containsKey(key);
    }
    public ConcurrentReadIntArrayList get(int key) {
        ConcurrentReadIntArrayList list = null;
        if (iIndex().containsKey(key)) {
            list = new ConcurrentReadIntArrayList(1);
            list.add(iIndex().get(key));
        }
        return list;
    }
    public int get1(int key) {
        if (iIndex().containsKey(key)) {
            return iIndex().get(key);
        } else {
            return -1;
        }
    }

    // long-type key
    @SuppressWarnings({"unchecked", "rawtypes"})
    TSynchronizedLongIntMap lIndex() {
        if (index == null) {
            synchronized (this) {
                if (index == null) {
                    index = new TSynchronizedLongIntMap(new TLongIntHashMap(64, 0.75f));
                }
            }
        }
        return (TSynchronizedLongIntMap) index;
    }
    public void add(long key, int pos) {
        if (lIndex().containsKey(key)) {
            throw new AssertionError("Key " + key + " already exists!");
        } else {
            lIndex().put(key, pos);
        }
    }
    public boolean contains(long key) {
        return lIndex().containsKey(key);
    }
    public ConcurrentReadIntArrayList get(long key) {
        ConcurrentReadIntArrayList list = null;
        if (lIndex().containsKey(key)) {
            list = new ConcurrentReadIntArrayList(1);
            list.add(lIndex().get(key));
        }
        return list;
    }
    public int get1(long key) {
        if (lIndex().containsKey(key)) {
            return lIndex().get(key);
        } else {
            return -1;
        }
    }

    // Object-type key
    @SuppressWarnings({"unchecked", "rawtypes"})
    TSynchronizedObjectIntMap oIndex() {
        if (index == null) {
            synchronized (this) {
                if (index == null) {
                    index = new TSynchronizedObjectIntMap(new TObjectIntHashMap(64, 0.75f));
                }
            }
        }
        return (TSynchronizedObjectIntMap) index;
    }
    public void add(Object key, int pos) {
        if (oIndex().containsKey(key)) {
            throw new AssertionError("Key " + key + " already exists!");
        } else {
            oIndex().put(key, pos);
        }
    }
    public boolean contains(Object key) {
        return oIndex().containsKey(key);
    }
    public ConcurrentReadIntArrayList get(Object key) {
        ConcurrentReadIntArrayList list = null;
        if (oIndex().containsKey(key)) {
            list = new ConcurrentReadIntArrayList(1);
            list.add(oIndex().get(key));
        }
        return list;
    }
    public int get1(Object key) {
        if (oIndex().containsKey(key)) {
            return oIndex().get(key);
        } else {
            return -1;
        }
    }
}
