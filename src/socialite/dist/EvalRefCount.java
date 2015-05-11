package socialite.dist;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import socialite.util.SociaLiteException;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class EvalRefCount {
    public static final Log L= LogFactory.getLog(EvalRefCount.class);

    public interface IdleCallback { public void call(int id, int idleTimestamp); }

	static volatile EvalRefCount theInst;
    public static EvalRefCount getInst(IdleCallback callback) {
        theInst = new EvalRefCount(callback);
        return theInst;
    }
    public static EvalRefCount getInst() {
        if (theInst==null) {
            throw new SociaLiteException("EvalRefCount is not created yet");
        }
        return theInst;
    }

    ConcurrentHashMap<Integer, AtomicInteger> ready;
    ConcurrentHashMap<Integer, AtomicInteger> counterMap;
    ConcurrentHashMap<Integer, AtomicInteger> idleTimestampMap;
    IdleCallback callback;

    public EvalRefCount(IdleCallback _callback) { this(_callback, 32); }
    public EvalRefCount(IdleCallback _callback, int concurrencyLevel) {
        ready = new ConcurrentHashMap<Integer, AtomicInteger>(256, 0.75f, concurrencyLevel);
        counterMap = new ConcurrentHashMap<Integer, AtomicInteger>(256, 0.75f, concurrencyLevel);
        idleTimestampMap = new ConcurrentHashMap<Integer, AtomicInteger>(256, 0.75f, concurrencyLevel);
        callback = _callback;
	}

    public void setReady(int id) {
        synchronized (ready) {}

        AtomicInteger val = new AtomicInteger(1);
        AtomicInteger prev = ready.putIfAbsent(id, val);
        if (prev!=null) { val = prev; }
        val.incrementAndGet();

        synchronized (val) {
            val.notifyAll();
        }
    }
    public void waitUntilReady(int id) throws InterruptedException {
        synchronized (ready) {}

        AtomicInteger val = new AtomicInteger(0);
        AtomicInteger prev = ready.putIfAbsent(id, val);
        if (prev!=null) { val = prev;}
        synchronized (val) {
            while (true) {
                if (val.get() > 0) { break; }
                val.wait(1);
            }
        }
    }
    public void incBy(int id, int by) {
        if (!counterMap.containsKey(id)) {
            counterMap.putIfAbsent(id, new AtomicInteger(0));
        }
        AtomicInteger counter = counterMap.get(id);
        counter.addAndGet(by);
    }
	public void inc(int id) {
        if (!counterMap.containsKey(id)) {
            counterMap.putIfAbsent(id, new AtomicInteger(0));
        }
        AtomicInteger counter = counterMap.get(id);
        counter.incrementAndGet();
	}

    public void decBy(int id, int by) {
        AtomicInteger cnt = counterMap.get(id);
        if (cnt==null) {
            throw new SociaLiteException("Epoch["+id+"] is not registered to EvalTerminationManager");
        }
        if (cnt.addAndGet(-by)==0) {
            if (!idleTimestampMap.containsKey(id)) {
                idleTimestampMap.putIfAbsent(id, new AtomicInteger(0));
            }
            AtomicInteger ts = idleTimestampMap.get(id);
            callback.call(id, ts.incrementAndGet());
        }
    }
    public void dec(int id) {
        AtomicInteger cnt = counterMap.get(id);
        if (cnt==null) {
            throw new SociaLiteException("Epoch["+id+"] is not registered to EvalTerminationManager");
        }
        int x=cnt.decrementAndGet();
        if (x==0) {
            if (!idleTimestampMap.containsKey(id)) {
                idleTimestampMap.putIfAbsent(id, new AtomicInteger(0));
            }
            AtomicInteger ts = idleTimestampMap.get(id);
            callback.call(id, ts.incrementAndGet());
        }
    }

    public boolean stillIdle(int id, int ts) {
        AtomicInteger counter = counterMap.get(id);
        AtomicInteger timestamp = idleTimestampMap.get(id);
        if (counter==null || timestamp==null) { return false; }
        return counter.get() == 0 && timestamp.get() == ts;
    }

    public void clear(int id) {
        ready.remove(id);
        counterMap.remove(id);
        idleTimestampMap.remove(id);
    }
}
