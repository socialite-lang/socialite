package socialite.util;

import gnu.trove.map.hash.TLongObjectHashMap;

import java.util.ArrayList;
import java.util.List;

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
	
	public SendQueue() {
		queue = new ArrayQueue<WorkerMessage>(4*1024);
		msgs = new TLongObjectHashMap<WorkerMessage>(128, 0.8f, 0L);
		reserved = new ArrayList<WorkerMessage>(128);
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
	public synchronized WorkerMessage reserve() throws InterruptedException {
		WorkerMessage msg=null;
		while (queue.isEmpty()) {
			wait();
		}
		msg = queue.get();		
		reserved.add(msg);
		
		long key = key(msg);
		msgs.remove(key);
		
		return msg;
	}

	public void pop(WorkerMessage m) {
        EvalRefCount.getInst().dec(m.getEpochId());
        synchronized (this) {
            reserved.remove(m);
        }
		synchronized(condFull) {
			condFull.notifyAll();
		}
	}
	public boolean add(WorkerMessage m) {
		assert !queue.contains(m);
		boolean reuseTableInMsg=false;
		if (!m.isSerialized()) {
			waitIfFull();
		}		
		long key = key(m);
        boolean incEvalRef=false;
		synchronized(this) {
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
			notifyAll();
		}
        if (incEvalRef) EvalRefCount.getInst().inc(m.getEpochId());
		return reuseTableInMsg;
	}
	
	final int queueSizeLimit=
			(int)(ByteBufferPool.getDirectBufferAlloc()/ByteBufferPool.bufferSize()*2);
	Object condFull = new Object();
	boolean isFull() {	
		return queue.size() > queueSizeLimit;
	}
	void waitIfFull() {
		synchronized (condFull) {
			while (isFull()) {
				try { condFull.wait(); }
				catch (InterruptedException e) {
					throw new SociaLiteException();
				}
			}	
		}
	}	
	boolean mergeMsg(WorkerMessage prevMsg, WorkerMessage newMsg) {
		if (prevMsg.isSerialized()) return false;
		
		EvalWithTable prevEvalT = prevMsg.evalT;
		EvalWithTable newEvalT = newMsg.evalT;
		assert prevEvalT.getTable().id() == newEvalT.getTable().id();
		
		if (prevEvalT.getTable().vacancy() >= newEvalT.getTable().size()) {
			prevEvalT.getTable().addAll(newEvalT.getTable());			
			return true;
		} else {			
			return false;			
		}
	}
}
