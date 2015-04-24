package socialite.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.msg.WorkerMessage;
import socialite.dist.worker.ChannelMux;
import socialite.dist.worker.WorkerConnPool;
import socialite.engine.Config;
import socialite.eval.EvalWithTable;
import socialite.tables.TableInst;
import socialite.util.ByteBufferPool;
import socialite.util.SendQueue;
import socialite.util.SociaLiteException;

public class Sender {
	static final Log L=LogFactory.getLog(Sender.class);
	
	static Sender theInstance=null;
	public static Sender get(Config conf, WorkerAddrMap _workerAddrMap, WorkerConnPool _workerConn) {
		if (theInstance==null) {
			theInstance=new Sender(conf, _workerAddrMap, _workerConn);
		} else {
			theInstance.init(conf, _workerAddrMap, _workerConn);
		}
		return theInstance;
	}
	public static void shutdown() {
		if (theInstance!=null) {
			for (Thread t:theInstance.senderThreads) {
				t.interrupt();
			}
			theInstance.senderThreads=null;
		}
	}
	public static SendQueue sendQ() {
		if (theInstance==null) return null;
		return theInstance.sendQ;
	}
	
	WorkerAddrMap workerAddrMap;
	WorkerConnPool workerConn;
	ChannelMux[] sendChannel$;
	SendQueue sendQ;
	Thread[] senderThreads;
	
	public Sender(Config conf, WorkerAddrMap _workerAddrMap, WorkerConnPool _workerConn) {
		init(conf, _workerAddrMap, _workerConn);
	}
	
	public void init(Config conf, WorkerAddrMap _workerAddrMap, WorkerConnPool _workerConn) {
		workerAddrMap = _workerAddrMap;
		workerConn = _workerConn;
		sendChannel$ = new ChannelMux[workerAddrMap.size()];
		int senderNum = Runtime.getRuntime().availableProcessors()/4;
		if (senderNum < 10) senderNum = 10;
		if (senderNum > 64) senderNum = 64;
		initSenderThreads(senderNum);
	}
	void shutdownSenderThreads() {
		for (Thread t:senderThreads) {
			if (t!=null) {
				t.interrupt();
				try { t.join(); }
				catch (InterruptedException e) { }
			}			
		}
	}
	void initSenderThreads(int senderNum) {
		if (senderThreads!=null) {
			shutdownSenderThreads();
		}
		sendQ = new SendQueue();
		senderThreads = new Thread[senderNum];
		for (int i=0; i<senderNum; i++) {
			SendReally sender = new SendReally(sendQ, this);
			senderThreads[i] = new Thread(sender, "Sender #"+i);
			senderThreads[i].start();
		}
	}

	void cacheSendChannelFor(int machineIdx) {
		if (sendChannel$[machineIdx]==null) {
			InetAddress inetAddr=workerAddrMap.get(machineIdx);			
			ChannelMux sendChMux = workerConn.sendChannelMuxFor(inetAddr);
			sendChannel$[machineIdx] = sendChMux;
			if (sendChMux==null) {
				throw new SociaLiteException("SendChannel is null for machine:"+machineIdx);
			}
		}
	}
	
	public boolean send(int machineIdx, EvalWithTable eval) {
		TableInst origT = eval.getTable();
		assert origT!=null;
		WorkerMessage workerMsg = new WorkerMessage(eval);
		boolean reuseTable = send(machineIdx, workerMsg);
		if (reuseTable) origT.clear();		
		return reuseTable;
	}	
	
	void serializeMsg(WorkerMessage workerMsg) {
		try { workerMsg.serialize(false); }
		catch (IOException e) {
			L.fatal("Exception while serializing worker message:"+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
			throw new SociaLiteException(e);
		}	
	}	
	boolean send(int machineIdx, WorkerMessage workerMsg) {
		if (machineIdx==-1) {
			return broadcast(workerMsg);
		}
		
		cacheSendChannelFor(machineIdx);		
		workerMsg.setSlaveId(machineIdx);		
		int estimated = workerMsg.guessMessageSize();
		int bufferSize = ByteBufferPool.bufferSize();
		int smallBufferSize = ByteBufferPool.smallBufferSize();
		if (estimated > bufferSize/2 && estimated <= bufferSize) {
			if (ByteBufferPool.bufferAvailable()) {
				serializeMsg(workerMsg);	
			}	
		} else if (estimated > smallBufferSize/2 && estimated <= smallBufferSize){
			if (ByteBufferPool.smallBufferAvailable()) {
				serializeMsg(workerMsg);				
			}
		}		
		return sendQ.add(workerMsg);
	}
	boolean broadcast(WorkerMessage workerMsg) {
		int self = workerAddrMap.myIndex();
		for (int i=0; i<workerAddrMap.size(); i++) {
			if (i!=self) cacheSendChannelFor(i);
		}
		
		workerMsg.setSlaveId(-1);
		serializeMsg(workerMsg);
		return sendQ.add(workerMsg);
	}
}

class SendTask {
	int machineIdx;
	ByteBuffer buffer;
	SendTask(int idx, ByteBuffer bb) {
		machineIdx=idx;
		buffer=bb;
	}
}
class SendReally implements Runnable {
	public static final Log L=LogFactory.getLog(Sender.class);	
	
	static volatile long totalTimeToSend=0;
	SendQueue sendQ;
	Sender sender;
	SendReally(SendQueue _sendQ, Sender _sender) {
		sendQ=_sendQ;
		sender=_sender;
	}
		
	@Override
	public void run() {
		while (true) {
			WorkerMessage m=null;
			ByteBuffer buffer=null;
			try {
				m = sendQ.reserve();
				synchronized(m) {
					buffer = m.serialize();
				}
                int epochId = m.getEpochId();
                if (m.getSlaveId()==-1) {
					int self = sender.workerAddrMap.myIndex();
					for (int i=0; i<sender.sendChannel$.length; i++) {
						if (i==self) continue;
						sender.workerConn.send(sender.sendChannel$[i], epochId, buffer);
					}						
					buffer.rewind();
				} else {
					sender.workerConn.send(sender.sendChannel$[m.getSlaveId()], epochId, buffer);
				}
			} catch (InterruptedException ie) {
				break;
			} catch (Exception e) {				
				L.fatal("Exception while sending table:"+e);
				L.fatal(ExceptionUtils.getStackTrace(e));
			} finally {
				sendQ.pop(m);
				if (buffer!=null)
					ByteBufferPool.get().free(buffer);
			}
		}			
	}
}
