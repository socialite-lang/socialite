package socialite.tables;

import com.intellij.util.containers.*;
import socialite.util.concurrent.*;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SPosIndex {
    public static final Log L = LogFactory.getLog(SPosIndex.class);

    volatile Object index;

    public void clear() {
        if (index == null) { return; }

        if (index instanceof ConcurrentIntObjectMap) {
            iIndex().clear();
        } else if (index instanceof ConcurrentLongObjectMap) {
            lIndex().clear();
        } else if (index instanceof ConcurrentHashMap) {
            oIndex().clear();
        } else {
            throw new AssertionError("Unexpected index type:" + index.getClass().getSimpleName());
        }
    }

    public int size() {
        if (index == null) { return 0; }

        if (index instanceof ConcurrentIntObjectMap) {
            return iIndex().size();
        } else if (index instanceof ConcurrentLongObjectMap) {
            return lIndex().size();
        } else if (index instanceof ConcurrentHashMap) {
            return oIndex().size();
        } else {
            throw new AssertionError("Unexpected index type:" + index.getClass().getSimpleName());
        }
    }
    // int-type key
    @SuppressWarnings({ "unchecked", "rawtypes" })
    ConcurrentIntObjectMap<ConcurrentReadIntArrayList> iIndex() {
        if (index == null) {
            synchronized(this) {
                if (index == null) {
                    index = new ConcurrentIntObjectHashMap<ConcurrentReadIntArrayList>(64, 0.75f, 2);
                }
            }
        }
        return (ConcurrentIntObjectMap<ConcurrentReadIntArrayList>)index;
    }
    public void add(int key, int pos) {
        if (iIndex().containsKey(key)) {
            iIndex().get(key).add(pos);
        } else {
            ConcurrentReadIntArrayList list = new ConcurrentReadIntArrayList(1);
            list.add(pos);
            list = iIndex().putIfAbsent(key, list);
            if (list != null) { list.add(pos); }
        }
    }
    public boolean contains(int key) {
        return iIndex().containsKey(key);
    }
    public ConcurrentReadIntArrayList get(int key) {
        return iIndex().get(key);
    }
    public int get1(int key) {
        ConcurrentReadIntArrayList list = iIndex().get(key);
        if (list == null) {
            return -1;
        } else {
            return list.get(0);
        }
    }

    // long-type key
    @SuppressWarnings({ "unchecked", "rawtypes" })
    ConcurrentLongObjectMap<ConcurrentReadIntArrayList> lIndex() {
        if (index == null) {
            synchronized (this) {
                if (index == null) {
                    index = new ConcurrentLongObjectHashMap<ConcurrentReadIntArrayList>(64, 0.75f, 2);
                }
            }
        }
        return (ConcurrentLongObjectMap<ConcurrentReadIntArrayList>)index;
    }
    public void add(long key, int pos) {
        if (lIndex().containsKey(key)) {
            lIndex().get(key).add(pos);
        } else {
            ConcurrentReadIntArrayList list = new ConcurrentReadIntArrayList(2);
            list.add(pos);
            list = lIndex().putIfAbsent(key, list);
            if (list != null) { list.add(pos); }
        }
    }
    public boolean contains(long key) {
        return lIndex().containsKey(key);
    }
    public ConcurrentReadIntArrayList get(long key) {
        return lIndex().get(key);
    }
    public int get1(long key) {
        ConcurrentReadIntArrayList list = lIndex().get(key);
        if (list == null) {
            return -1;
        } else {
            return list.get(0);
        }
    }

    // Object-type key
    @SuppressWarnings({ "unchecked", "rawtypes" })
    ConcurrentHashMap<Object, ConcurrentReadIntArrayList> oIndex() {
        if (index == null) {
            synchronized (this) {
                if (index == null) {
                    index = new ConcurrentHashMap<Object, ConcurrentReadIntArrayList>(32, 0.75f, 8);
                }
            }
        }
        return (ConcurrentHashMap<Object, ConcurrentReadIntArrayList>)index;
    }
    public void add(Object key, int pos) {
        if (oIndex().containsKey(key)) {
            oIndex().get(key).add(pos);
        } else {
            ConcurrentReadIntArrayList list = new ConcurrentReadIntArrayList(2);
            list.add(pos);
            list = oIndex().putIfAbsent(key, list);
            if (list != null) { list.add(pos); }
        }
    }
    public boolean contains(Object key) {
        return oIndex().containsKey(key);
    }
    public ConcurrentReadIntArrayList get(Object key) {
        return oIndex().get(key);
    }
    public int get1(Object key) {
        ConcurrentReadIntArrayList list = oIndex().get(key);
        if (list == null) {
            return -1;
        } else {
            return list.get(0);
        }
    }
}