/**
 * Authored by Jiwon Seo (seojiwon@gmail.com)
 * This file is under Apache V2 License
 */

package socialite.util.concurrent;
import java.util.*;
import java.util.concurrent.atomic.*;

/**
 * A memory-efficient concurrent map with primitive float keys.
 * The data structure is optimized to have small memory footprint.
 * It is assumed that this map is primarily used for look-ups (read access) rather than insertions (write access),
 * especially with concurrent insertion methods (putAtomic and putAtomicIfAbsent).
 *
 * This class implements methods for simple Map-like data structure.
 * The following methods (and class) are implemented:
 *
 *  public boolean contains(float key)
 *  public V get(float key)
 *  public V remove(float key)
 *  public boolean remove(float key, V value)
 *  public boolean replace(float key, V old, V value)
 *  public V put(float key, V value)
 *  public V putAtomic(float key, V value)
 *  public V putAtomicIfAbsent(float key, V value)
 *  public FloatObjectIterator<V> iterator()
 *  public FloatObjectIterator<V> iteratorFrom(float low, boolean lowInclusive)
 *  public FloatObjectIterator<V> iteratorTo(float high, boolean highInclusive)
 *  public FloatObjectIterator<V> iterator(float low, float high, boolean lowInclusive, boolean highInclusive)
 *  public class FloatObjectIterator<V> {
 *      public boolean hasNext()
 *      public void next()
 *      public float key()
 *      public V value()
 *      public void cleanup() // if the iterator is not used up, cleanup() must be called.
 *  }
 *
 * Beware that the put(key, value) method assumes that there are no concurrent read/write access to the map instance.
 * For concurrent put operations, use putAtomic(key, value) or putAtomicIfAbsent(key, value) instead.
 * Since put(key, value) assumes no concurrent access, its implementation is more efficient than putAtomic and putAtomicIfAbsent.
 * The two method -- putAtomic and putAtomicIfAbsent -- perform copy-on-write, while put simply inserts in-place.
 *
 * It is intended that the non-concurrent put operation is initially used to build the map,
 * then concurrent methods are used to allow concurrent read/write by multiple threads.
 */
public class ConcurrentFloatOrderedListMap<V> {
    /*
     * This class augments two data strudtures -- skip list and chunked linked list --
     * to implement memory-efficient concurrent map.
     *
     * The following picture shows the map with nine keys -- 1, 4, 5, 9, 11, 15, 20, 21, 25 -- and
     * corresponding nine values -- v1, v4, v5, v9, v11, v15, v20, v21, v25.
     * The skip list has two levels, and the nodes at level 0 store keys and values,
     * where the keys are the smallest keys in each chunked list node,
     * and the values are the chunked list nodes themselves.
     *
     * The keys and values stored in the chunked list are sorted by the keys,
     * so that look-up can be done with binary search.
     *  +-------------------------- [skip list] -----------------------+
     *  |                                                              |
     *  | Head nodes                              Index nodes          |
     *  | +-+    right                            +-+                  |
     *  | |2|------------------------------------>| |-------->null     |
     *  | +-+                                     +-+                  |
     *  |  | down                                  |                   |
     *  |  v                                       v                   |
     *  | +-+                 +-+                 +-+                  |
     *  | |1|---------------->| |---------------->| |-------->null     |
     *  | +-+                 +-+                 +-+                  |
     *  |  v                   |                   |                   |
     *  | Nodes next           v                   v                   |
     *  | +-+   +-+           +-+                +--+                  |
     *  | | |-->|1|---------> |9|--------------->|20|-------->null     |
     *  | +-+   +-+           +-+                +--+                  |
     *  |        |             |                  |                    |
     *  +--------|-------------|------------------|--------------------+
     *           |             |                  |
     *           |             |                  |
     *  +--------|-------------|------------------|--------------------+
     *  |        v             v                  v                    |
     *  |   +----------+    +------------+    +-------------+          |
     *  |   |[ 1, 4, 5]|--->|[ 9, 11, 15]|--->|[ 20, 21, 25]|--->null  |
     *  |   |[v1,v4,v5]|    |[v9,v11,v15]|    |[v20,v21,v25]|          |
     *  |   +----------+    +------------+    +-------------+          |
     *  |                                                              |
     *  +------------------ [chunked (sorted) linked list] ------------+
     *
     * We use a memory pool (Node.pool) to re-use the nodes in chunked linked list.
     * To make sure that a node is not re-used while it is being accessed,
     * we use reference counting. See John D. Valois "Lock-free linked lists using compare-and-swap".
     */

    /*
     * Special value returned from some methods of Node class,
     * indicating that the operation by the method failed.
     */
    final static Object Retry = new Object();

    /*
     * Callback function that is invoked by skipListMap.findGrandPredecessor().
     * This guarantees that the reference count of the node found in the skipListMap is positive,
     * so that it does not get garbage-collected and re-used.
     */
    static ConcurrentFloatSkipListMap.CallbackOnFound<Node> refCntCallback =
                    new ConcurrentFloatSkipListMap.CallbackOnFound<Node>() {
                        public void found(Node n) { n.incRefCnt(); }
                        public void cancel(Node n) {
                            // It's possible (although very unlikely) that this release operation sets n.refcnt to 0,
                            // in which case n.refcnt could have been incremented from 0 by found(Node n) above.
                            // Hence, n.refcnt can be decremented to 0 two times; so even when n.refcnt is 0,
                            // we don't decrement n.next.refcnt immediately since it can double-decrement n.next.refcnt.
                            // We decrement n.next.refcnt in Node.alloc(), where we know that only a single thread is
                            // accessing the node.
                            Node.release(n);
                        }
                    };

    static final int CHUNK_SIZE = 16;

    final Node head;
    ConcurrentFloatSkipListMap<Node> skipListMap;

    public ConcurrentFloatOrderedListMap() {
        skipListMap = new ConcurrentFloatSkipListMap<Node>();
        head = new HeadNode(0);
    }

    public boolean contains(float key) { return get(key) != null; }

    /*
     * Methods for fetching next pointer of a node, preventing the node from being reclaimed by the pool.
     * release() simply decrements the reference count of the node.
     */
    Node safeNext(Node b) {
        return Node.safeNext(b);
    }
    void release(Node n) {
        Node.release(n);
    }
    void release(Node b, Node n) {
        Node.release(n);
        Node.release(b);
    }
    void release(Node b, Node n, Node f) {
        Node.release(f);
        Node.release(n);
        Node.release(b);
    }

    public double _fillRatio() {
        long capacity=0;
        long count=0;
        for (Node n=head; n!=null; n=n.next) {
            capacity += n.keys.length;
            count += n.length;
        }
        return 100.0*(double)count/(double)capacity;
    }
    public void _print() {
        for (Node n=head; n!=null; n=n.next) {
            n._print();
        }
    }
    public void clear() {
        for (Node n=head.next; n!=null; n=n.next) {
            Node.release(n);
            assert n.getRefCnt()==0;
            n.clearEntry();
            Node.free(n);
        }
        head.next = null;
    }
    public void doSanityCheck() {
        int count=0;
        float prevKey = Float.MIN_VALUE;
        Node n = safeNext(head);
        while (n!=null) {
            count++;
            if (!skipListMap.containsKey(n.first())) {
                System.out.println("Node n not in skiplistMap:"+n.first());
                n._print();
            }
            if (n.first() < prevKey) {
                System.out.println("Error in ordering, prevKey:"+prevKey+", n:"+n);
            }
            prevKey = n.first();
            Node next = safeNext(n);
            release(n);
            n = next;
        }
        if (skipListMap.size() != count) {
            System.out.println("skipListMap.size:"+skipListMap.size()+", count:"+count);
        }
    }

    Node ceilingNode(float key) {
        for (;;) {
            Node b = skipListMap.findGrandPredecessor(key, refCntCallback);
            if (b == null) b = head;
            if (b != head && key <= b.last()) {
                return b;
            }
            assert b == head || b.last() < key;
            Node n = safeNext(b);
            release(b);
            if (n == null) {
                return null;
            } else if (key <= n.last()) {
                return n;
            }
            Node f = safeNext(n);
            release(n);
            if (f == null) {
                return null;
            } else {
                if (isInconsistentNextNode(f, key)) {
                    release(f);
                    continue;
                }
                return f;
            }
        }
    }


    private boolean isInconsistentNextNode(Node f, float key) {
        /** With skipListMap.findGrandPredecessor,
         *  we expect the key of the target node (next of next node of returned node)
         *  to be greater than or equal to the given key.
         *  If not, there is inconsistency between skipListMap and this ordered list map.
         *  @see Node#putAtomicReally for the cause of this inconsistency
         */
        return f.next != null && f.first() < key;
    }
    public V get(float key) {
        for (;;) {
            Node b = skipListMap.findGrandPredecessor(key, refCntCallback);
            if (b == null) b = head;
            if (b != head && b.first() <= key && key <= b.last()) {
                // can happen if there's inconsistency between skipListMap and orderedMap(this).
                // The inconsistency can happen from Node.putAtomicReally(),
                // where it adds to, and removes from skiplistMap.
                Object val = b.get(key);
                release(b);
                if (val == Retry) continue;
                return (V)val;
            }
            assert b == head || b.last() < key;
            Node n = safeNext(b);
            release(b);
            if (n == null) {
                return null;
            } else if (n.first() <= key && key <= n.last()) {
                Object val = n.get(key);
                release(n);
                if (val == Retry) continue;
                return (V)val;
            }
            Node f = safeNext(n);
            release(n);
            if (f == null) {
                return null;
            } else {
                if (isInconsistentNextNode(f, key)) {
                    release(f);
                    continue;
                }
                Object val = f.get(key);
                release(f);
                if (val == Retry) continue;
                return (V)val;
            }
        }
    }
    public V remove(float key) {
        return doRemove(key, null);
    }
    public boolean remove(float key, V old) {
        if (old == null)
            throw new NullPointerException();
        return doRemove(key, old) != null;
    }
    public boolean replace(float key, V old, V value) {
        if (old == null || value == null)
            throw new NullPointerException();
        return doReplace(key, old, value) != null;
    }
    V doRemove(float key, V old) {
        return doReplace(key, old, null);
    }
    V doReplace(float key, V old, V value) {
        for (;;) {
            Node b = skipListMap.findGrandPredecessor(key, refCntCallback);
            if (b == null) b = head;
            if (b != head && b.first() <= key && key <= b.last()) {
                // can happen if there's inconsistency between skipListMap and orderedMap(this).
                // The inconsistency can happen from Node.putAtomicReally(),
                // where it adds to, and removes from skiplistMap.
                Object val = b.replace(key, old, value);
                release(b);
                if (val == Retry) continue;
                return (V)val;
            }
            assert b == head || b.last() < key;
            Node n = safeNext(b);
            release(b);
            if (n == null) {
                return null;
            } else if (n.first() <= key && key <= n.last()) {
                Object val = n.replace(key, old, value);
                release(n);
                if (val == Retry) continue;
                return (V)val;
            }
            Node f = safeNext(n);
            release(n);
            if (f == null) {
                return null;
            } else {
                if (isInconsistentNextNode(f, key)) {
                    release(f);
                    continue;
                }
                Object val = f.replace(key, old, value);
                release(f);
                if (val == Retry) continue;
                return (V)val;
            }
        }
    }

    // no concurrent read/write is assumed
    public V put(float key, V value) {
        if (value == null) throw new NullPointerException();
        // not incrementing b's refcnt, so need not release it.
        Node b = skipListMap.findGrandPredecessor(key);
        if (b == null) b = head;
        Node n = b.next;
        if (n == null) {
            n = Node.alloc();
            n.put(key, value, this);
            n.next = null;
            b.next = n;
            return null;
        }
        Node f = n.next;
        if (f == null) { // put (key,value) into node n
            Object val = n.put(key, value, this);
            return (V)val;
        } else {
            assert f.len() > 0 && f.first() >= key;
            if (f.first() == key) {
                Object val = f.put(key, value, this);
                return (V)val;
            } else {
                Object val = n.put(key, value, this);
                return (V)val;
            }
        }
    }
    public V putAtomic(float key, V value) {
        if (value == null) throw new NullPointerException();
        return putAtomic(key, value, false);
    }
    public V putAtomicIfAbsent(float key, V value) {
        if (value == null) throw new NullPointerException();
        return putAtomic(key, value, true);
    }

    V putAtomic(float key, V value, boolean onlyIfAbsent) {
        if (value == null)
            throw new NullPointerException();

        for (;;) {
            Node b = skipListMap.findGrandPredecessor(key, refCntCallback);
            if (b == null) b = head;
            if (b != head && b.first() <= key && key <= b.last()) {
                release(b);
                continue;
            }
            assert b == head || b.last() < key;
            Node n = safeNext(b);
            if (n == null) {
                if (b.appendNewAtomic(key, value, this)) {
                    release(b);
                    return null;
                } else {
                    release(b);
                    continue;
                }
            } else if (n.first() <= key && key <= n.last()) {
                Object val = n.putAtomic(key, value, b, onlyIfAbsent, this);
                release(b, n);
                if (val == Retry) continue;
                return (V)val;
            }
            Node f = safeNext(n);
            if (f == null) {
                Object val = n.putAtomic(key, value, b, onlyIfAbsent, this);
                release(b, n);
                if (val == Retry) continue;
                return (V)val;
            } else {
                if (f.first() == key) {
                    Object val = f.putAtomic(key, value, n, onlyIfAbsent, this);
                    release(b, n, f);
                    if (val == Retry) continue;
                    return (V)val;
                } else {
                    if (key > f.first()) { // inconsistent read, retry
                        release(b, n, f);
                        continue;
                    }
                    Object val = n.putAtomic(key, value, b, onlyIfAbsent, this);
                    release(b, n, f);
                    if (val == Retry) continue;
                    return (V)val;
                }
            }
        }
    }

    static class Node {
        static ConcurrentSoftQueue<Node> pool = new ConcurrentSoftQueue<Node>();
        static Node alloc() {
            final int threshold = 2;
            int tryCnt = 0;
            while (true) {
                tryCnt++;
                Node n = pool.poll();
                if (n == null) {
                    return new Node();
                } else {
                    if (n.getRefCnt() > 0) {
                        pool.add(n);
                        if (tryCnt <= threshold) continue;
                        else return new Node();
                    } else {
                        if (n.next != null)
                            n.next.decRefCnt();
                        n.init();
                        return n;
                    }
                }
            }
        }
        static void free(Node n) { pool.add(n); }

        static private void nodeCopy(float[] srcKeys, Object[] srcVals, int srcBegin,
                                     float[] targetKeys, Object[] targetVals, int targetBegin, int copyLen) {
            System.arraycopy(srcKeys, srcBegin, targetKeys, targetBegin, copyLen);
            System.arraycopy(srcVals, srcBegin, targetVals, targetBegin, copyLen);
        }

        static Node safeNext(Node b) {
            while (true) {
                Node n = b.next;
                if (n == null)
                    return null;
                n.incRefCnt();
                if (n == b.next) return n;
                else release(n);
            }
        }
        static void release(Node n) {
            n.decRefCnt();
        }

        volatile int refcnt;
        volatile int length;
        final float[] keys;
        final Object[] vals;
        volatile Node next;

        Node() { this(CHUNK_SIZE); }
        Node(int capacity) {
            keys = new float[capacity];
            vals = new Object[capacity];
            refcnt = 1;
            next = null;
        }

        void init() {
            incRefCnt();
            length = 0;
            next = null;
            clearEntry();
        }
        public void clearEntry() {
            Arrays.fill(vals, null);
        }

        public String toString() {
            String str = "Node(refcnt:"+refcnt+", isMarked:"+isMarked()+", length:"+length+")[";
            for (int i=0; i<len(); i++) {
                str += keys[i]+" ";
            }
            str += "]";
            return str;
        }
        void _print() {
            System.out.print("Node(refcnt:"+refcnt+","+length+"/"+keys.length+")[");
            for (int i=0; i<length; i++) {
                //System.out.print(keys[i]+":"+vals[i]+" ");
                System.out.print(keys[i] + " ");
            }
            System.out.println("]");
        }

        static final AtomicReferenceFieldUpdater<Node, Node> nextUpdater =
                        AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class, "next");
        static final AtomicIntegerFieldUpdater<Node> lenUpdater =
                        AtomicIntegerFieldUpdater.newUpdater(Node.class, "length");
        static final AtomicIntegerFieldUpdater<Node> refcntUpdater =
                AtomicIntegerFieldUpdater.newUpdater(Node.class, "refcnt");
        boolean casNext(Node cmp, Node val) {
            boolean success = nextUpdater.compareAndSet(this, cmp, val);
            if (!success) {
                System.err.println("setting next failed");
            }
            return success;
        }
        boolean casLen(int cmp, int val) { return lenUpdater.compareAndSet(this, cmp, val); }

        int incRefCnt() { return refcntUpdater.incrementAndGet(this); }
        int decRefCnt() {
            int refcnt = refcntUpdater.decrementAndGet(this);
            if (refcnt < 0) {
                throw new RuntimeException("refcnt:"+refcnt);
            }
            return refcnt;
        }
        int getRefCnt() { return refcnt; }

        boolean isFull() { return len() == keys.length; }
        int len() {
            int len = length;
            return (len >= 0) ? len : -len;
        }

        boolean mark() {
            int len = length;
            if (len < 0) return false;
            return casLen(len, -len);
        }
        boolean isMarked() { return length < 0; }

        float first() {
            assert len() != 0;
            return keys[0];
        }
        float last() {
            assert len() != 0;
            return keys[len()-1];
        }

        boolean contains(float key) {
            return Arrays.binarySearch(keys, 0, len(), key) >= 0;
        }

        int findKeyIndex(float key) { return Arrays.binarySearch(keys, 0, len(), key); }
        Object get(float key) {
            if (len() == 0) return null;
            int pos;
            if (key == keys[0]) pos = 0;
            else pos = Arrays.binarySearch(keys, 0, len(), key);
            if (pos < 0) return null;
            return vals[pos];
        }
        Object replace(float key, Object expect, Object value) {
            synchronized(this) {
                if (isMarked()) return Retry;

                int len = len();
                int pos = Arrays.binarySearch(keys, 0, len, key);
                if (pos < 0) return null;
                Object old = vals[pos];
                if (expect == null) {
                    vals[pos] = value;
                    return old;
                } else if (expect.equals(old)) {
                    vals[pos] = value;
                    return old;
                } else { return null; }
            }
        }

        // no concurrent read/write access is assumed
        Object put(float key, Object value, ConcurrentFloatOrderedListMap orderedMap) {
            int len = len();
            int pos = Arrays.binarySearch(keys, 0, len, key);
            if (pos >= 0) {
                Object old = vals[pos];
                vals[pos] = value;
                return old;
            } else {
                pos = -(pos+1);
                putReally(pos, key, value, orderedMap);
                return null;
            }
        }

        private boolean emptySlotInNextTwo() {
            if (next == null) return false;
            if (!next.isFull())  return true;
            if (next.next == null) return false;
            if (!next.next.isFull())  return true;
            return false;
        }

        // no concurrent read/write access is assumed
        private void putReally(int pos, float key, Object value, ConcurrentFloatOrderedListMap orderedMap) {
            int len = len();
            if (len+1 <= keys.length) { // inserted in the current node
                nodeCopy(keys, vals, pos, keys, vals, pos+1, len-pos);
                keys[pos] = key;
                vals[pos] = value;
                length = len+1;
                if (pos == 0) {
                    orderedMap.skipListMap.put(keys[0], this);
                    if (len != 0) orderedMap.skipListMap.remove(keys[1], this);
                }
            } else if (emptySlotInNextTwo()) {
                if (pos == len) {
                    next.put(key, value, orderedMap);
                    return;
                }
                next.put(keys[len-1], vals[len-1], orderedMap);
                nodeCopy(keys, vals, pos, keys, vals, pos+1, len-pos-1);
                keys[pos] = key;
                vals[pos] = value;
                if (pos==0) {
                    orderedMap.skipListMap.remove(keys[1], this);
                    orderedMap.skipListMap.put(keys[0], this);
                }
            } else { // current node is full, so requires a new node
                Node n = Node.alloc();
                float[] nkeys = n.keys;
                Object[] nvals = n.vals;
                int l1=len/2, l2=len-l1;
                if (next == null && pos == len) { // this is the last node, simply add to the new node.
                    nkeys[0] = key;
                    nvals[0] = value;
                    n.length = 1;
                    orderedMap.skipListMap.put(nkeys[0], n);
                } else if (pos < l1) { // key,value is stored in the current node
                    length = l1+1;
                    n.length = l2;
                    nodeCopy(keys, vals, l1, nkeys, nvals, 0, l2);

                    nodeCopy(keys, vals, pos, keys, vals, pos+1, l1-pos);
                    keys[pos] = key;
                    vals[pos] = value;
                    if (pos == 0) { 
                        orderedMap.skipListMap.remove(keys[1]); 
                        orderedMap.skipListMap.put(keys[0], this);
                    }
                    orderedMap.skipListMap.put(nkeys[0], n);
                } else { // key,value is stored in the new node
                    length = l1;
                    n.length = l2+1;
                    int newpos = pos-l1;

                    nodeCopy(keys, vals, l1, nkeys, nvals, 0, newpos);
                    nkeys[newpos] = key;
                    nvals[newpos] = value;
                    nodeCopy(keys, vals, pos, nkeys, nvals, newpos+1, l2-newpos);

                    orderedMap.skipListMap.put(nkeys[0], n);
                }
                n.next = this.next;
                this.next = n;
            }
        }

        // concurrent read/write access is allowed
        boolean appendNewAtomic(float key, Object value, ConcurrentFloatOrderedListMap orderedMap) {
            synchronized(this) {
                if (isMarked()) return false;
                if (next != null) return false;

                Node n = Node.alloc();
                n.put(key, value, orderedMap);
               // assert n.len()==1;
                n.next = null;
                boolean success = casNext(null, n);
                assert success;
                return true;
            }
        }
        // concurrent read/write access is allowed
        Object putAtomic(float key, Object value, Node b, boolean onlyIfAbsent, ConcurrentFloatOrderedListMap orderedMap) {
            synchronized(b) {
                if (b.isMarked()) return Retry;
                synchronized (this) {
                    if (isMarked()) return Retry;

                    int len = len();
                    int pos = Arrays.binarySearch(keys, 0, len, key);
                    if (pos >= 0) {
                        Object old = vals[pos];
                        if (onlyIfAbsent) {
                            if (old == null) {
                                vals[pos] = value;
                                return null;
                            } else {
                                return old;
                            }
                        }
                        vals[pos] = value;
                        return old;
                    }
                    pos = -(pos + 1);
                    putAtomicReally(b, pos, key, value, orderedMap);
                    return null;
                }
            }
        }

        // only used by putAtomic and PutAtomicIfAbsent. Inside synchronized(b) and synchronized(this).
        private void putAtomicReally(Node b, int pos, float key, Object value, ConcurrentFloatOrderedListMap orderedMap) {
            int len = len();
            if (len+1 <= keys.length) {
                if (pos == len) { // in-place append in the current node
                    keys[pos] = key;
                    vals[pos] = value;
                    length = len+1;
                    if (pos == 0) { orderedMap.skipListMap.put(keys[0], this); }
                } else { // copied to a new node, replacing the current node
                    mark();
                    Node n = Node.alloc();
                    n.next = this.next;
                    if (next != null) next.incRefCnt();
                    float[] nkeys = n.keys;
                    Object[] nvals = n.vals;
                    n.length = len+1;
                    nodeCopy(keys, vals, 0, nkeys, nvals, 0, pos);
                    nkeys[pos] = key; nvals[pos] = value;
                    nodeCopy(keys, vals, pos, nkeys, nvals, pos+1, len-pos);

                    orderedMap.skipListMap.put(nkeys[0], n);
                    b.casNext(this, n); // should always succeed.
                    if (pos == 0) { orderedMap.skipListMap.remove(keys[0], this); }
                    release(this);
                    free(this);
                }
            } else { // requires 2 new nodes, to replace the current node
                mark();
                Node n1 = Node.alloc();
                float[] n1keys = n1.keys;
                Object[] n1vals = n1.vals;
                Node n2 = Node.alloc();
                float[] n2keys = n2.keys;
                Object[] n2vals = n2.vals;
                int l1=len/2, l2=len-l1;
                if (pos < l1) { // key, value stored in n1
                    n1.length = l1+1;
                    n2.length = l2;

                    nodeCopy(keys, vals, 0, n1keys, n1vals, 0, pos);
                    n1keys[pos] = key; n1vals[pos] = value;
                    nodeCopy(keys, vals, pos, n1keys, n1vals, pos+1, l1-pos);
                    nodeCopy(keys, vals, l1, n2keys, n2vals, 0, l2);

                    n1.next = n2;
                    n2.next = this.next;
                    if (next != null) next.incRefCnt();

                    orderedMap.skipListMap.put(n1keys[0], n1);
                    orderedMap.skipListMap.put(n2keys[0], n2);
                    b.casNext(this, n1); // should always succeed.
                    if (pos == 0) { orderedMap.skipListMap.remove(keys[0], this); }
                    release(this);
                    free(this);
                } else { // key,value is stored in n2
                    n1.length = l1;
                    n2.length = l2+1;
                    int newpos = pos-l1;

                    nodeCopy(keys, vals, 0, n1keys, n1vals, 0, l1);

                    nodeCopy(keys, vals, l1, n2keys, n2vals, 0, newpos);
                    n2keys[newpos] = key; n2vals[newpos] = value;
                    nodeCopy(keys, vals, pos, n2keys, n2vals, newpos+1, l2-newpos);

                    n1.next = n2;
                    n2.next = this.next;
                    if (next != null) next.incRefCnt();

                    orderedMap.skipListMap.put(n1keys[0], n1);
                    orderedMap.skipListMap.put(n2keys[0], n2);
                    b.casNext(this, n1); // should always succeed.
                    release(this);
                    free(this);
                }
            }
        }
    }

    static class HeadNode extends Node {
        HeadNode() { super(); }
        HeadNode(int capacity) {
            super(capacity);
        }
        void _print() { }
        int incRefCnt() { return 1; }
        int decRefCnt() { return 1; }
        int getRefCnt() { return 1; }
        public String toString() {
            String str = "HeadNode(refcnt:"+refcnt+", isMarked:"+isMarked()+", length:"+length+")[";
            for (int i=0; i<len(); i++) {
                str += keys[i]+" ";
            }
            str += "]";
            return str;
        }
    }

    public static class FloatObjectIterator<V> {
        Node current;
        int len;
        int pos;
        float low, high;
        boolean lowInclusive, highInclusive;
        ConcurrentFloatOrderedListMap orderedMap;
        float key;
        V val;
        FloatObjectIterator(ConcurrentFloatOrderedListMap map, float lo, float hi, boolean loIncl, boolean hiIncl) {
            low = lo;
            high = hi;
            orderedMap = map;
            lowInclusive = loIncl;
            highInclusive = hiIncl;
            if (low == Float.MIN_VALUE) {
                current = orderedMap.safeNext(orderedMap.head);
            } else {
                current = orderedMap.ceilingNode(low);
            }
            pos = 0;
            if (current == null) {
                len = 0;
            } else {
                pos = findBeginIndex(current, low, lowInclusive);
                len = findEndIndex(current, high, highInclusive)+1;
            }
        }
        int findBeginIndex(Node n, float key, boolean inclusive) {
            boolean exclusive = !inclusive;
            if (key <= n.first()) {
                if (key == n.first() && exclusive)
                    return 1;
                return 0;
            }
            int pos = n.findKeyIndex(key);
            if (pos < 0) pos = -(pos+1);
            else if (!inclusive) pos++;

            return pos;
        }
        int findEndIndex(Node n, float key, boolean inclusive) {
            boolean exclusive = !inclusive;
            if (key < n.first()) return -1;
            if (key >= n.last()) {
                if (key == n.last() && exclusive)
                    return n.len()-2;
                return n.len()-1;
            }
            int pos = n.findKeyIndex(key);
            if (pos < 0) pos = -(pos+1);
            else if (!inclusive) pos--;

            if (pos == n.len()) pos--;
            return pos;
        }
        public void cleanup() {
            if (current == null) return;
            orderedMap.release(current);
            current = null;
        }
        public boolean hasNext() {
            if (pos < len) {
                return true;
            } else {
                assert pos == len;
                if (current == null) return false;
                Node next = orderedMap.safeNext(current);
                orderedMap.release(current);
                current = next;
                pos = 0;
                if (current == null) {
                    return false;
                }

                len = findEndIndex(current, high, highInclusive)+1;
                if (len == 0) {
                    orderedMap.release(current);
                    current = null;
                    return false;
                }
                return true;
            }
        }
        public void next() {
            key = current.keys[pos];
            val = (V)current.vals[pos];
            pos++;
        }
        public float key() { return key; }
        public V value() { return val; }
    }

    public FloatObjectIterator<V> iterator() {
        return iterator(Float.MIN_VALUE, Float.MAX_VALUE, true, true);
    }
    public FloatObjectIterator<V> iteratorFrom(float low, boolean lowInclusive) {
        return iterator(low, Float.MAX_VALUE, lowInclusive, true);
    }
    public FloatObjectIterator<V> iteratorTo(float high, boolean highInclusive) {
        return iterator(Float.MIN_VALUE, high, true, highInclusive);
    }
    public FloatObjectIterator<V> iterator(float low, float high, boolean lowInclusive, boolean highInclusive) {
        return new FloatObjectIterator<V>(this, low, high, lowInclusive, highInclusive);
    }

    public static void main(String[] args) {
        /*for (int i=0; i<10; i++)
            parTest();*/
        iterTest();

    }
    static void iterateFromTest() {
        final ConcurrentFloatOrderedListMap<String> map1 = new ConcurrentFloatOrderedListMap<String>();
        map1.put(25, "25");
        map1.put(30, "30");
        map1.put(31, "31");
        FloatObjectIterator<String> it = map1.iteratorFrom(21, false);
        float sum=0;
        while (it.hasNext()) {
            it.next();
            sum += it.key();
        }
        it.cleanup();
        assert sum == 25+30+31;

        it = map1.iteratorFrom(25, false);
        sum=0;
        while (it.hasNext()) {
            it.next();
            sum += it.key();
        }
        it.cleanup();
        assert sum == 30+31;

        it = map1.iteratorFrom(25, true);
        sum=0;
        while (it.hasNext()) {
            it.next();
            sum += it.key();
        }
        it.cleanup();
        assert sum == 25+30+31;
    }
    static void iterTest() {
        iterateFromTest();

        final ConcurrentFloatOrderedListMap<String> map = new ConcurrentFloatOrderedListMap<String>();
        for (int i=-1000; i<1001; i++) {
            map.put(i, ""+i);
        }

        FloatObjectIterator<String> iter = map.iterator(11, 20, true, true);
        float sum = 0;
        while (iter.hasNext()) {
            iter.next();
            sum += iter.key();
        }
        assert sum == (11+20)*5:"sum:"+sum+", != "+(11+20)*5;

        iter = map.iterator(-1000, 1, true, false);
        sum = 0;
        while (iter.hasNext()) {
            iter.next();
            sum += iter.key();
        }
        assert sum == -1000*(1000+1)/2;

        iter = map.iterator(-1000, -995, true, true);
        sum = 0;
        while (iter.hasNext()) {
            iter.next();
            sum += iter.key();
        }
        assert sum == -(1000+999+998+997+996+995);

        iter = map.iteratorFrom(995, true);
        sum = 0;
        while (iter.hasNext()) {
            iter.next();
            sum += iter.key();
        }
        assert sum == (995+996+997+998+999+1000);

        iter = map.iteratorTo(-995, false);
        sum = 0;
        while (iter.hasNext()) {
            iter.next();
            sum += iter.key();
        }
        assert sum == -(1000+999+998+997+996);

        map.doSanityCheck();
        map.clear();
        System.out.println("done");
    }
    static void parTest() {
        final int N = 100000;
        final ConcurrentFloatOrderedListMap<String> map = new ConcurrentFloatOrderedListMap<String>();
        final float[] keys = new float[N];

        final Random r = new Random();
        for (int i=0; i<N; i++) {
            int x = r.nextInt();
            keys[i] = x;
            map.put(x, "" + x);
        }
        map.doSanityCheck();
        System.out.println("Start parallel...");

        long s=System.currentTimeMillis();
        for (int i=0; i<N; i++) {
            map.get(keys[i]);
        }
        long e=System.currentTimeMillis();
        System.out.println("Exec time:"+(e-s)+"ms");

        final int nReader = 8;
        final int nReplacer = 4;
        final int nWriter = 4;
        final int nThread = nReader+nReplacer+nWriter;
        for (int i = 0; i < 5; i++) {
            ParUtil.parallel(nThread, new ParUtil.CallableBlock() {
                @Override
                public void call(int index) {
                    if (index < nReader) {
                        for (int j = 0; j < 35; j++) {
                            for (int i = index; i < N; i += nReader) {
                                String val = map.get(keys[i]);
                                assert val != null && val.equals("" + keys[i]) : "key:" + keys[i] + ", val:" + val;
                            }
                        }
                        //System.out.println("Finished reader thread:" + index);
                    } else if (index < nReader+nReplacer) {
                        for (int j = 0; j < 5; j++) {
                            for (int i = index; i < N; i += nReplacer) {
                                boolean replaced = map.replace(keys[i], "" + keys[i], "" + keys[i]);
                                assert replaced;
                            }
                        }
                        //System.out.println("Finished writer(replace) thread:" + index);
                    } else {
                        for (int i = 0; i < 2 * N; i++) {
                            int x = r.nextInt();
                            map.putAtomic(x, "" + x);
                        }
                        //System.out.println("Finished writer thread:" + index);
                    }
                }
            });
            System.out.println("Finished all parallel... i:" + i);
            map.doSanityCheck();
        }
        /*
        System.out.println("Node.pool size:"+Node.pool.size());
        while(true) {
            Node n = Node.pool.poll();
            if (n==null) break;
            System.out.println("n.refcnt:"+n.refcnt);
        }
        System.out.println("fill ratio:"+map._fillRatio());
        */
    }
}