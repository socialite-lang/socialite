package socialite.tables;

import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TLongIntHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import org.roaringbitmap.MyRoaringBitmap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Optimized index class holding a single position per key.
 * Used for group-by maps.
 */

public class SIndex1 extends SIndex {
    public static final Log L = LogFactory.getLog(SIndex1.class);

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

        if (index instanceof TIntIntHashMap) {
            return iIndex().size();
        } else if (index instanceof TLongIntHashMap) {
            return lIndex().size();
        } else if (index instanceof TObjectIntHashMap) {
            return oIndex().size();
        } else {
            throw new AssertionError("Unexpected index type:" + index.getClass().getSimpleName());
        }
    }

    // int-type key
    @SuppressWarnings({"unchecked", "rawtypes"})
    private TIntIntHashMap iIndex() {
        if (index == null) {
            synchronized (this) {
                if (index == null) {
                    index = new TIntIntHashMap(64, 0.75f);
                }
            }
        }
        return (TIntIntHashMap) index;
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

    public MyRoaringBitmap get(int key) {
        MyRoaringBitmap indices = null;
        if (iIndex().containsKey(key)) {
            indices = new MyRoaringBitmap();
            indices.add(iIndex().get(key));
        }
        return indices;
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
    private TLongIntHashMap lIndex() {
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
        if (lIndex().containsKey(key)) {
            throw new AssertionError("Key " + key + " already exists!");
        } else {
            lIndex().put(key, pos);
        }
    }

    public boolean contains(long key) {
        return lIndex().containsKey(key);
    }

    public MyRoaringBitmap get(long key) {
        MyRoaringBitmap indices = null;
        if (lIndex().containsKey(key)) {
            indices = new MyRoaringBitmap();
            indices.add(lIndex().get(key));
        }
        return indices;
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
    private TObjectIntHashMap oIndex() {
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
        if (oIndex().containsKey(key)) {
            throw new AssertionError("Key " + key + " already exists!");
        } else {
            oIndex().put(key, pos);
        }
    }

    public boolean contains(Object key) {
        return oIndex().containsKey(key);
    }

    public MyRoaringBitmap get(Object key) {
        MyRoaringBitmap indices = null;
        if (oIndex().containsKey(key)) {
            indices = new MyRoaringBitmap();
            indices.add(oIndex().get(key));
        }
        return indices;
    }

    public int get1(Object key) {
        if (oIndex().containsKey(key)) {
            return oIndex().get(key);
        } else {
            return -1;
        }
    }
}
