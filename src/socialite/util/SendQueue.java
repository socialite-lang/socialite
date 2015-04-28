package socialite.util;

import gnu.trove.map.hash.TLongObjectHashMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.EvalRefCount;
import socialite.dist.msg.WorkerMessage;
import socialite.eval.EvalWithTable;

public class SendQueue {
	public static final Log L=LogFactory.getLog(SendQueue.class);
	
	ArrayQueue<WorkerMessage> queue;
	TLongObjectHashMap<WorkerMessage> msgs; 
	List<WorkerMessage> reserved;
    AtomicInteger serializedMsgCount = new AtomicInteger(0);

    Lock lock = new ReentrantLock(true);
    Condition notEmpty = lock.newCondition();
    Condition notFull = lock.newCondition();

	public SendQueue() {
		queue = new ArrayQueue<WorkerMessage>(4*1024);
		msgs = new TLongObjectHashMap<WorkerMessage>(128, 0.8f, 0L);
		reserved = new ArrayList<WorkerMessage>(128);
        reserved = Collections.synchronizedList(reserved);
    }
	public synchronized boolean isEmpty() {
		return queue.isEmpty() && reserved.isEmpty();
	}
	public boolean isLikelyEmpty() {
		return queue.isEmpty() && reserved.isEmpty();
	}
	public int size() { return queue.size(); }
	
	public synchronized void empty() {
		queue.empty();
	}
	long key(WorkerMessage msg) {
		return ((long)msg.getSlaveId())<<32 | 
			   ((long)msg.getTableId())<<1 | 1L;
	}	
	public WorkerMessage reserve() throws InterruptedException {
		WorkerMessage msg=null;
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            msg = queue.get();
            long key = key(msg);
            msgs.remove(key);
        } finally {
            lock.unlock();
        }

        reserved.add(msg);
        if (msg.isSerialized()) {
            serializedMsgCount.decrementAndGet();
        }
        lock.lock();
        try { notFull.signalAll(); }
        finally { lock.unlock(); }
		return msg;
	}

	public void pop(WorkerMessage m) {
        EvalRefCount.getInst().dec(m.getEpochId());
        reserved.remove(m);
	}
	public boolean add(WorkerMessage m) {
		assert !queue.contains(m);
		boolean reuseTableInMsg=false;

        if (m.isSerialized()) {
            serializedMsgCount.incrementAndGet();
        }
        waitIfFull(m);
		long key = key(m);
        boolean incEvalRef=false;
        lock.lock();
		try {
            WorkerMessage prevMsg = msgs.get(key);
			if (m.isSerialized()) {
				queue.add(m);
				reuseTableInMsg = true;
                incEvalRef = true;
			} else if (prevMsg == null) {
				queue.add(m);
				msgs.put(key, m);
				reuseTableInMsg = false;
                incEvalRef = true;
			} else {
				boolean merged = mergeMsg(prevMsg, m);
				if (merged) {
					reuseTableInMsg = true;
				} else {
					queue.add(m);
					msgs.put(key, m); // replace prevMsg with m
					reuseTableInMsg = false;
                    incEvalRef = true;
				}
			}
			notEmpty.signalAll();
		} finally {
            lock.unlock();
        }
        if (incEvalRef) EvalRefCount.getInst().inc(m.getEpochId());
		return reuseTableInMsg;
	}
	
	final int queueSizeLimit=4096*2;
    final int serializedMsgLimit=(int)(ByteBufferPool.getBufferQueueSize()*3/5);
    boolean isFull(WorkerMessage m) {
        if (m.isSerialized()) {
            return serializedMsgCount.get() > serializedMsgLimit;
        } else {
            return queue.size() > queueSizeLimit;
        }
	}
	void waitIfFull(WorkerMessage m) {
        lock.lock();
        try {
            while (isFull(m)) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    throw new SociaLiteException();
                }
            }
        } finally {
            lock.unlock();
        }
    }
	boolean mergeMsg(WorkerMessage prevMsg, WorkerMessage newMsg) {
		if (prevMsg.isSerialized()) return false;
		
		EvalWithTable prevEvalT = prevMsg.evalT;
		EvalWithTable newEvalT = newMsg.evalT;
		assert prevEvalT.getTable().id() == newEvalT.getTable().id();
		
		if (prevEvalT.getTable().vacancy() > newEvalT.getTable().size()) {
			prevEvalT.getTable().addAll(newEvalT.getTable());			
			return true;
		} else {			
			return false;			
		}
	}
}
