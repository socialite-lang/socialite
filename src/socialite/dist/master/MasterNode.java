package socialite.dist.master;

import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.util.*;
import java.nio.channels.*;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;
import org.apache.hadoop.security.UserGroupInformation;

import socialite.codegen.Epoch;
import socialite.codegen.EpochW;
import socialite.dist.msg.*;
import socialite.dist.worker.WorkerCmd;
import socialite.dist.worker.WorkerNode;
import socialite.engine.Config;
import socialite.resource.WorkerAddrMap;
import socialite.resource.SRuntime;
import socialite.util.Assert;
import socialite.util.SociaLiteException;


public class MasterNode {
	public static final Log L=LogFactory.getLog(MasterNode.class);
	
	static MasterNode theInstance=null;
	public static MasterNode getInstance() {
		if (theInstance==null) 
			theInstance = new MasterNode(Config.dist());
		return theInstance;
	}
	
	public static WorkerAddrMap getCurrentWorkerAddrMap() {
		WorkerAddrMap workerAddrMap;
		if (SRuntime.masterRt()==null) {
			MasterNode master=getInstance();
			workerAddrMap = master.makeWorkerAddrMap();
		} else {
			workerAddrMap = SRuntime.masterRt().getWorkerAddrMap();
		}
		return workerAddrMap;
	}
	public static Object[] callWorkers(WorkerAddrMap workerAddrMap, Method m, Object[] param) {
		Config conf;
		if (SRuntime.masterRt()==null) conf = getInstance().getWorkerConf();
		else conf = SRuntime.masterRt().getConf();
		
		int cmdPort = conf.portMap().workerCmdListen();		
		InetSocketAddress addrs[] = workerAddrMap.getSockAddrs(cmdPort).toArray(new InetSocketAddress[0]);
		try {
			UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
			Object params[][] = new Object[addrs.length][param.length];
			Arrays.fill(params, param);
			Configuration hconf = new Configuration();
			return RPC.call(m, params, addrs, ugi, hconf);
		} catch (Throwable e) {			
			L.fatal("Exception while calling "+m.getName()+" (RPC):");
			L.fatal(ExceptionUtils.getStackTrace(e));
			return null;
		}		
	}
	public static Object[] callWorkers(Method m, Object[] param) {
		return callWorkers(getCurrentWorkerAddrMap(), m, param);		
	}
	
	Config conf; // master node conf
	Config workerConf; // worker node may have different config (e.g. cpu #)
	QueryListener queryListener;
	WorkerReqListener workerListener;
	Map<InetAddress, WorkerCmd> workerMap;
	
	private MasterNode(Config _conf) {
		conf=_conf;
		workerMap=Collections.synchronizedMap(new HashMap<InetAddress, WorkerCmd>());
	}
			
	public void serve() {
		initWorkerReqListener();
		initQueryListener();
	}
	
	void initWorkerReqListener() {
		workerListener = new WorkerReqListener(conf, this);
		workerListener.start();
	}
	
	void initQueryListener() {
		queryListener =new QueryListener(conf, this);
		queryListener.start();	
	}
	public Config getWorkerConf() {
		return workerConf;
	}
	public void setWorkerConf(Config _conf) {
		assert workerConf == null;
		workerConf = _conf;
	}
	public WorkerCmd getWorkerCmd(String _addr) {
		InetAddress workerAddr=null;
		try { workerAddr = InetAddress.getByName(_addr);
		} catch (UnknownHostException e) {
			L.fatal("Invalid address["+_addr+"]:"+e);
			throw new SociaLiteException(e);
		}
		return workerMap.get(workerAddr);
	}
	public Set<InetAddress> getWorkerAddrs() {
		return workerMap.keySet();
	}
	public boolean addWorkerAddr(String _addr) {
		InetAddress workerAddr=null;
		try { workerAddr = InetAddress.getByName(_addr);
		} catch (UnknownHostException e) {
			L.fatal("Invalid address["+_addr+"]:"+e);
			throw new SociaLiteException(e);
		}
		
		if (workerMap.containsKey(workerAddr)) 
			return false;
		
		Configuration hConf=new Configuration();
		String workerIP=workerAddr.getHostAddress();
		int workerCmdPort=conf.portMap().workerCmdListen();
		InetSocketAddress addr=new InetSocketAddress(workerIP, workerCmdPort);
		WorkerCmd workerCmd;
		try {
			workerCmd = (WorkerCmd)RPC.waitForProxy(WorkerCmd.class, WorkerCmd.versionID, addr, hConf);
			workerMap.put(workerAddr, workerCmd);
		} catch(IOException e) {
			L.fatal("Cannot connect to worker:");
			L.fatal(ExceptionUtils.getStackTrace(e));
			return false;
		}
		return true;		
	}
	
	public void beginSession() {
		prepareEpoch();
	}
	public void prepareEpoch() {
		int clusterSize = SRuntime.masterRt().getWorkerAddrMap().size();
		workerListener.initIdleStat(clusterSize);
		halted = false;
	}
	volatile boolean halted=false;
	public void setHalted() { halted = true; }
	public boolean isHalted() { return halted; }
	
	public boolean isClusterIdle() { return epochDone; }
	volatile boolean epochDone=false;
	public void setEpochDone() { epochDone=true; }
	public void waitForEpochDone() {
		while(!epochDone)
			sleep(8);
		epochDone=false;
	}
	void sleep(long millis) {
		try { Thread.sleep(millis);
		} catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Map<InetAddress, WorkerCmd> getWorkerCmdMap() {
		return workerMap;
	}
	public WorkerAddrMap makeWorkerAddrMap() { return makeWorkerAddrMap(-1); }
	public WorkerAddrMap makeWorkerAddrMap(int workerNodeNum) {
		// XXX: should return only the addresses of available workers!
		
		WorkerAddrMap machineMap=new WorkerAddrMap();
		Set<InetAddress> workerAddrs;
		workerAddrs=workerMap.keySet();
		if (workerNodeNum<=0) {
			workerNodeNum = workerAddrs.size();
		}
		int addedWorker=0;
		
		for (InetAddress addr:workerAddrs) {
			machineMap.add(addr);
			addedWorker++;
			if (addedWorker >= workerNodeNum)
				break;
		}
		return machineMap;
	}
	
	
	public void finish() {
		
	}

	static void startMasterNode() {
		MasterNode master = MasterNode.getInstance();		
		master.serve();
		L.info("Master started");
	}
	
	public static void main(String[] args) {
		startMasterNode();
	}

}
