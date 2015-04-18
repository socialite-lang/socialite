package socialite.tables;

import socialite.visitors.IntVisitor;

import java.util.concurrent.locks.ReentrantLock;

public class SPosLockedIndex extends SPosIndex {
    ReentrantLock lock = new ReentrantLock();

    @Override
    public void clear() {
        lock.lock();
        try {
            super.clear();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void add(int key, int pos) {
        lock.lock();
        try {
            super.add(key, pos);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean contains(int key) {
        lock.lock();
        try {
            return super.contains(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int get(int key) {
        lock.lock();
        try {
            return super.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateBy(int key, IntVisitor v) {
        lock.lock();
        try {
            super.iterateBy(key, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateFrom(int from, boolean fromInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateFrom(from, fromInclusive, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateTo(int to, boolean toInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateTo(to, toInclusive, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateFromTo(int from, boolean fromInclusive, int to,
                              boolean toInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateFromTo(from, fromInclusive, to, toInclusive, v);
        } finally {
            lock.unlock();
        }
    }

    // long-type key
    @Override
    public void add(long key, int pos) {
        lock.lock();
        try {
            super.add(key, pos);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean contains(long key) {
        lock.lock();
        try {
            return super.contains(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int get(long key) {
        lock.lock();
        try {
            return super.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateBy(long key, IntVisitor v) {
        lock.lock();
        try {
            super.iterateBy(key, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateFrom(long from, boolean fromInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateFrom(from, fromInclusive, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateTo(long to, boolean toInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateTo(to, toInclusive, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateFromTo(long from, boolean fromInclusive, int to,
                              boolean toInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateFromTo(from, fromInclusive, to, toInclusive, v);
        } finally {
            lock.unlock();
        }
    }

    // float-type key
    @Override
    public void add(float key, int pos) {
        lock.lock();
        try {
            super.add(key, pos);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean contains(float key) {
        lock.lock();
        try {
            return super.contains(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int get(float key) {
        lock.lock();
        try {
            return super.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateBy(float key, IntVisitor v) {
        lock.lock();
        try {
            super.iterateBy(key, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateFrom(float from, boolean fromInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateFrom(from, fromInclusive, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateTo(float to, boolean toInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateTo(to, toInclusive, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateFromTo(float from, boolean fromInclusive, float to,
                              boolean toInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateFromTo(from, fromInclusive, to, toInclusive, v);
        } finally {
            lock.unlock();
        }
    }

    // double-type key
    @Override
    public void add(double key, int pos) {
        lock.lock();
        try {
            super.add(key, pos);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean contains(double key) {
        lock.lock();
        try {
            return super.contains(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int get(double key) {
        lock.lock();
        try {
            return super.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateBy(double key, IntVisitor v) {
        lock.lock();
        try {
            super.iterateBy(key, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateFrom(double from, boolean fromInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateFrom(from, fromInclusive, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateTo(double to, boolean toInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateTo(to, toInclusive, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateFromTo(double from, boolean fromInclusive, double to,
                              boolean toInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateFromTo(from, fromInclusive, to, toInclusive, v);
        } finally {
            lock.unlock();
        }
    }

    // object-type key
    @Override
    public void add(Object key, int pos) {
        lock.lock();
        try {
            super.add(key, pos);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean contains(Object key) {
        lock.lock();
        try {
            return super.contains(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int get(Object key) {
        lock.lock();
        try {
            return super.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateBy(Object key, IntVisitor v) {
        lock.lock();
        try {
            super.iterateBy(key, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateFrom(Object from, boolean fromInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateFrom(from, fromInclusive, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateTo(Object to, boolean toInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateTo(to, toInclusive, v);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void iterateFromTo(Object from, boolean fromInclusive, Object to,
                              boolean toInclusive, IntVisitor v) {
        lock.lock();
        try {
            super.iterateFromTo(from, fromInclusive, to, toInclusive, v);
        } finally {
            lock.unlock();
        }
    }
}