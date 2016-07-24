package socialite.tables;

import com.intellij.util.containers.*;
import org.roaringbitmap.MyRoaringBitmap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * Created by jiwon on 1/25/16.
 */
public class SIndex {
    public static final Log L = LogFactory.getLog(SIndex.class);

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
    private ConcurrentIntObjectMap<MyRoaringBitmap> iIndex() {
        if (index == null) {
            synchronized(this) {
                if (index == null) {
                    index = new ConcurrentIntObjectHashMap<MyRoaringBitmap>(64, 0.75f, 2);
                }
            }
        }
        return (ConcurrentIntObjectMap<MyRoaringBitmap>)index;
    }
    public void add(int key, int pos) {
        if (iIndex().containsKey(key)) {
            MyRoaringBitmap indices = iIndex().get(key);
            indices.add(pos);
        } else {
            MyRoaringBitmap indices = new MyRoaringBitmap();
            indices.add(pos);
            indices = iIndex().putIfAbsent(key, indices);
            if (indices != null) { indices.add(pos); }

        }
    }
    public boolean contains(int key) {
        return iIndex().containsKey(key);
    }
    public MyRoaringBitmap get(int key) {
        return iIndex().get(key);
    }
    public int get1(int key) {
        MyRoaringBitmap indices = iIndex().get(key);
        synchronized (indices) {
            if (indices == null) {
                return -1;
            } else {
                return indices.getIntIterator().next();
            }
        }
    }

    // long-type key
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private ConcurrentLongObjectMap<MyRoaringBitmap> lIndex() {
        if (index == null) {
            synchronized (this) {
                if (index == null) {
                    index = new ConcurrentLongObjectHashMap<MyRoaringBitmap>(64, 0.75f, 2);
                }
            }
        }
        return (ConcurrentLongObjectMap<MyRoaringBitmap>)index;
    }
    public void add(long key, int pos) {
        if (lIndex().containsKey(key)) {
            MyRoaringBitmap indices = lIndex().get(key);
            indices.add(pos);
        } else {
            MyRoaringBitmap indices = new MyRoaringBitmap();
            indices.add(pos);
            indices = lIndex().putIfAbsent(key, indices);
            if (indices != null) { indices.add(pos); }
        }
    }
    public boolean contains(long key) {
        return lIndex().containsKey(key);
    }
    public MyRoaringBitmap get(long key) {
        return lIndex().get(key);
    }
    public int get1(long key) {
        MyRoaringBitmap indices = lIndex().get(key);
        if (indices == null) {
            return -1;
        } else {
            return indices.getIntIterator().next();
        }
    }

    // Object-type key
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private ConcurrentHashMap<Object, MyRoaringBitmap> oIndex() {
        if (index == null) {
            synchronized (this) {
                if (index == null) {
                    index = new ConcurrentHashMap<Object, MyRoaringBitmap>(32, 0.75f, 8);
                }
            }
        }
        return (ConcurrentHashMap<Object, MyRoaringBitmap>)index;
    }
    public void add(Object key, int pos) {
        if (oIndex().containsKey(key)) {
            MyRoaringBitmap indices = oIndex().get(key);
            indices.add(pos);
        } else {
            MyRoaringBitmap indices = new MyRoaringBitmap();
            indices.add(pos);
            indices = oIndex().putIfAbsent(key, indices);
            if (indices != null) { indices.add(pos); }
        }
    }
    public boolean contains(Object key) {
        return oIndex().containsKey(key);
    }
    public MyRoaringBitmap get(Object key) {
        if (key instanceof Integer) {
            int val = (Integer)key;
            return get(val);
        } else if (key instanceof Long) {
            long val = (Long)key;
            return get(val);
        } else {
            return oIndex().get(key);
        }
    }
    public int get1(Object key) {
        MyRoaringBitmap indices = oIndex().get(key);
        if (indices == null) {
            return -1;
        } else {
            return indices.getIntIterator().next();
        }
    }
}
