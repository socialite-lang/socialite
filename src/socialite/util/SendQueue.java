package socialite.util;

import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.msg.WorkerMessage;
import socialite.eval.EvalWithTable;
import socialite.resource.SRuntime;
import socialite.tables.TableInst;

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
	public void status() {
		System.out.println(" WARN SendQ size:"+queue.size());
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

	public synchronized void pop(WorkerMessage m) {
		reserved.remove(m);
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
		synchronized(this) {
			WorkerMessage prevMsg = msgs.get(key);
			if (m.isSerialized()) {
				queue.add(m);
				reuseTableInMsg = true;
			} else if (prevMsg == null) {
				queue.add(m);
				msgs.put(key, m);
				reuseTableInMsg = false;				
			} else {
				boolean merged = mergeMsg(prevMsg, m);
				if (merged) {
					reuseTableInMsg = true;
				} else {
					queue.add(m);
					msgs.put(key, m); // prevMsg is removed
					reuseTableInMsg = false;
				}
			}
			notifyAll();
		}
		return reuseTableInMsg;
	}
	
	final int queueSizeLimit=
			(int)(ByteBufferPool.getDirectBufferAlloc()/ByteBufferPool.bufferSize()*1.5);
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
		assert newEvalT.getSliceIdx() == 0: "new sliceIdx:"+newEvalT.getSliceIdx();
		assert prevEvalT.getSliceIdx() == 0: "prevEval sliceIdx:"+prevEvalT.getSliceIdx();		
		assert prevEvalT.getTable().id() == newEvalT.getTable().id();
		
		if (prevEvalT.getTable().vacancy() >= newEvalT.getTable().size()) {
			prevEvalT.getTable().addAllFast(newEvalT.getTable());			
			return true;
		} else {			
			return false;			
		}
	}
}
