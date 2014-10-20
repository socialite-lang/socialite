package socialite.dist.worker;

import java.net.*;
import java.util.*;
import java.io.*;
import java.nio.channels.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.RPC;
import org.apache.commons.lang3.exception.ExceptionUtils;

import socialite.dist.Host;
import socialite.dist.PortMap;
import socialite.dist.master.WorkerRequest;
import socialite.engine.Config;
import socialite.eval.Manager;
import socialite.functions.MyId;
import socialite.functions.NextId;
import socialite.resource.SRuntime;
import socialite.resource.Sender;
import socialite.util.ByteBufferPool;
import socialite.util.FastQueue;

public class WorkerNode extends Thread {
	public static final Log L=LogFactory.getLog(WorkerNode.class);
		
	WorkerConnPool connPool;
	Config conf;
	CmdListener cmdListener;
	WorkerRequest request;
	Manager manager;
	Thread[] recvThreads;
	FastQueue<RecvTask> recvQ;
	boolean networkInitDone=false;
	public WorkerNode() {
		super("WorkerNode Thread");
		connPool = new WorkerConnPool(PortMap.get());
	}
	
	public synchronized void setConf(Config _conf) {
		assert conf == null;
		conf = _conf;		
	}
	public Config getConf() {
		// no syncronization is required since 
		// getConf() is called by threads that are started after setConf().
		return conf; 
	}
	
	public void serve() {
		initCmdListener();				
		register();
		//initIdent();
		initManagerAndWorkers();
		initNetworkResources();
		initRecvThread();
		startListen();
	}
	void initIdent() {
		int myid = MyId.invoke();
		NextId.set(myid);
	}

	void maybeEarlyInitNetworkResources() {
		// for faster bootstraping of SociaLite cluster
		if (Math.random()<0.5) {
			initNetworkResources();	
			networkInitDone=true;
		}
	}
	void initNetworkResources() {
		if (!networkInitDone) {
			ByteBufferPool.get();
		}
	}
	void initCmdListener() {
		cmdListener = new CmdListener(this);
		cmdListener.start();
	}
	void initManagerAndWorkers() {
		manager = Manager.getInst(conf);				
	}
	void initRecvThread() {
		recvQ = Receiver.recvQ();
		int recvNum = Runtime.getRuntime().availableProcessors()/2;
		if (recvNum < 8) recvNum = 8;
		if (recvNum > 64) recvNum = 64;
		recvNum += 2;
		recvThreads = new Thread[recvNum];
		for (int i=0; i<recvThreads.length; i++) {
			Receiver recv=new Receiver(recvQ, connPool, manager, cmdListener);
			recvThreads[i] = new Thread(recv, "Receiver #"+i);
			recvThreads[i].start();
		}
	}
	
	public FastQueue recvQ() { return recvQ; }
	
	void startListen() {		
		start();	
	}
	
	public WorkerConnPool getConnPool() { return connPool; }
	
	public void run() {		
		try {
			while (true) {
				Set<SelectionKey> selectedKeys = connPool.select(5);				
				
				Iterator<SelectionKey> iter = selectedKeys.iterator();
				while (iter.hasNext()) {	
					SelectionKey key = iter.next();			
					iter.remove();
					if (!key.isValid()) {
						L.warn("Invalid key:"+key);
						continue;					
					}
					
					if (key.isAcceptable()) {
					//	L.info("Accepting connection from:"+key);
						connPool.acceptConn(key);
					} else if (key.isReadable()) {
						SocketChannel selectedChannel = (SocketChannel) (key.channel());
						InetAddress nodeAddr = (selectedChannel.socket()).getInetAddress();
						
						connPool.cancelKey(selectedChannel);	
						try { 
							selectedChannel.configureBlocking(true);
						} catch (IOException e) { 
							L.error("Error while configure blocking:"+e);
							L.fatal(ExceptionUtils.getStackTrace(e));
							continue;
						}
					
						RecvTask recv = new RecvTask(nodeAddr, selectedChannel);
						recvQ.add(recv);
						cmdListener.setEpochBusy();
					} else {
						L.error("Unexpected key operation(!acceptable, !readable):"+key);
					}
				} // while selectedKeys
				connPool.registerCanceledConn();
			}
		} catch (Exception e) {
			L.fatal("Error while select() operation:"+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
		}
	}
	
	void register() {
		assert conf==null;
		Configuration hConf=new Configuration();
		String masterAddr=PortMap.get().masterAddr();
		int reqPort=PortMap.get().workerReqListen();
		InetSocketAddress addr = new InetSocketAddress(masterAddr, reqPort);
		try {
			request = (WorkerRequest)RPC.waitForProxy(WorkerRequest.class, 
													  WorkerRequest.versionID,
													  addr, hConf);
		} catch (IOException e) {
			L.fatal("Cannot connect to master:"+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
		}
		request.register(new Text(Host.get()));
		synchronized(this) { /* make conf visible */}
		assert conf!=null;
		//L.info("Finished worker registration");
	}
	
	public boolean likelyIdle() {
		if (manager.likelyIdle() && Sender.sendQ().isLikelyEmpty() && recvQ.isLikelyEmpty())
			return true;
		return false;
	}
	
	public boolean idle() {
		Object addCmdLock = manager.addCmdLock();
		synchronized(addCmdLock) {
			if (manager.idle() && Sender.sendQ().isEmpty() && recvQ.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public boolean doIfIdle(Runnable r) {
		Object addCmdLock = manager.addCmdLock();
		synchronized(addCmdLock) {
			if (manager.idle() && Sender.sendQ().isEmpty() && recvQ.isEmpty()) {
				r.run();
				return true;
			}
		}
		return false;
	}
	
	public void reportError(int ruleid, Throwable t) {
		SRuntime runtime=SRuntime.workerRt();
		if (runtime==null) {
			L.error("reportError(): Worker runtime is null.");
			return;
		}
		String msg="";
		if (t!=null) msg += t.getClass().getSimpleName()+" ";
		if (t!=null && t.getMessage()!=null) msg = t.getMessage(); 
		IntWritable workerid = new IntWritable(runtime.getWorkerAddrMap().myIndex());
		request.handleError(workerid, new IntWritable(ruleid), new Text(msg));
	}
		
	public void reportIdle(int time) {
		SRuntime runtime = SRuntime.workerRt();
		if (runtime==null) return;
		
		int id=runtime.getWorkerAddrMap().myIndex();
		request.reportIdle(new IntWritable(id), new IntWritable(time));
	}
	
	public boolean connect(String[] workerAddrs) {
		InetAddress[] inetAddrs=new InetAddress[workerAddrs.length];
		for (int i=0; i<workerAddrs.length; i++) {
			try {
				inetAddrs[i] = InetAddress.getByName(workerAddrs[i]);
			} catch (UnknownHostException e) {
				L.error("Unexpected address:"+e);
				L.fatal(ExceptionUtils.getStackTrace(e));
				return false;
			}
		}
		connPool.connect(inetAddrs);
		return true;
	}	

	public static void haltOthers() {
		if (theWorkerNode==null) return;
		int myid = SRuntime.workerRt().getWorkerAddrMap().myIndex();		
		theWorkerNode.request.haltEpoch(new IntWritable(myid));
	}
	static WorkerNode theWorkerNode;	
	public static WorkerNode getInst() {
		return theWorkerNode;
	}
	static void startWorkerNode() {
		theWorkerNode = new WorkerNode();
		theWorkerNode.serve();
	}
	public static void main(String[] args) {
		startWorkerNode();
	}
}
