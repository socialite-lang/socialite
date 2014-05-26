package socialite.dist.master;


import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;
import org.apache.hadoop.security.UserGroupInformation;

import socialite.dist.ErrorRecord;
import socialite.dist.PortMap;
import socialite.dist.worker.WorkerCmd;
import socialite.engine.Config;
import socialite.resource.WorkerAddrMap;
import socialite.resource.SRuntime;
import socialite.util.SociaLiteException;
import socialite.util.TextArrayWritable;

public class WorkerReqListener implements WorkerRequest {
	public static final Log L=LogFactory.getLog(WorkerReqListener.class);
	
	String masterAddr;
	int reqListenPort;
	MasterNode master;
	Config conf;
	public WorkerReqListener(Config _conf, MasterNode _master) {
		conf = _conf;
		masterAddr=conf.portMap().masterAddr();
		reqListenPort=conf.portMap().workerReqListen();
		master=_master;		
	}
	
	public void start() {
		try {			
			Configuration hConf = new Configuration();
			int numHandlers = 8;// 8 is probably enough to handle idle request and halt request
			Server server = RPC.getServer(this, masterAddr, reqListenPort, numHandlers, false, hConf);
			server.start();
		} catch (IOException e) {
			L.fatal("Exception while starting WorkerReqListener:");
			L.fatal(ExceptionUtils.getStackTrace(e));
		}
	}
	
	@Override
	public synchronized BooleanWritable register(Text workerIp) {
		Set<InetAddress> otherAddrSet = master.getWorkerAddrs();		
		Text[] otherAddrTexts=new Text[otherAddrSet.size()];
		int i=0;
		for (InetAddress inet:otherAddrSet) {
			otherAddrTexts[i++]=new Text(inet.getHostAddress());
		}
		boolean firstWorkerConnected = i==0;
		TextArrayWritable restAddrs=new TextArrayWritable(otherAddrTexts);
		
		String addr=workerIp.toString();
		master.addWorkerAddr(addr);
		WorkerCmd cmd = master.getWorkerCmd(addr);		
		if (firstWorkerConnected) {
			int workerNum;
			if (Config.systemWorkerNum>0) {
				workerNum = Config.systemWorkerNum;
			} else {
				workerNum = cmd.getCpuNum().get();
			}
			master.setWorkerConf(Config.dist(workerNum));
		}
		Config workerConf = master.getWorkerConf();
		assert workerConf!=null;
		cmd.setWorkerNum(new IntWritable(workerConf.getWorkerNum()));
		BooleanWritable success=cmd.makeConnections(restAddrs);
		int numWorkers=otherAddrSet.size()+1;
		return success;
	}

	class WorkerIdleStat {
		int[] lastIdleTime;
		volatile int idleWorkerNum;
		WorkerIdleStat() { }
		void init(int clusterSize) {
			idleWorkerNum=0;
			lastIdleTime = new int[clusterSize];
			for (int i=0; i<lastIdleTime.length; i++) {
				lastIdleTime[i] = -1;
			}
		}
		void update(int id, int time) {
			if (time==-1) return;
			
			if (lastIdleTime[id]==-1)
				idleWorkerNum++;
			assert idleWorkerNum <= lastIdleTime.length;
			lastIdleTime[id] = time;
		}		
		boolean allIdle() {
			return idleWorkerNum==lastIdleTime.length;
		}
		
		int idleTime(int id) {
			return lastIdleTime[id];
		}
		int idleWorkerNum() {
			return idleWorkerNum;
		}
	}
	WorkerIdleStat idlestat=new WorkerIdleStat();
	
	public void initIdleStat(int clusterSize) {
		synchronized(idlestat) {
			idlestat.init(clusterSize);
		}
	}
	
	@Override 
	public void haltEpoch(IntWritable _workerid) {
		int workerid=_workerid.get();
		synchronized(this) {
			if (master.isHalted()) return;
			master.setHalted();
		}
		try {
			Method halt=WorkerCmd.class.getMethod("haltEpoch", new Class[]{});			
			int cmdPort=PortMap.get().workerCmdListen();
			WorkerAddrMap workerAddrMap = SRuntime.masterRt().getWorkerAddrMap();
			InetSocketAddress _addrs[] = workerAddrMap.getSockAddrs(cmdPort).toArray(new InetSocketAddress[0]);
			InetSocketAddress addrs[] = new InetSocketAddress[_addrs.length-1];
			for (int i=0,j=0; i<_addrs.length; i++) {
				if (i==workerid) continue;
				addrs[j++] = _addrs[i];
			}
			UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
			Object[][] params = new Object[addrs.length][0];
			
			RPC.call(halt, params, addrs, ugi, new Configuration());
		} catch (Exception e) {
			L.fatal("Exception while calling WorkerCmd.haltEpoch():");
			L.fatal(ExceptionUtils.getStackTrace(e));
		}
	}
	@Override
	public void reportIdle(IntWritable _workerId, IntWritable _time) {
		int workerId = _workerId.get();		
		int time = _time.get();
		if (time==-1) return;
		synchronized(idlestat) {			
			idlestat.update(workerId, time);
			if (idlestat.allIdle()) {
				try {
					Method isStillIdle=WorkerCmd.class.getMethod("isStillIdle", new Class[]{IntWritable.class});					
					int cmdPort=PortMap.get().workerCmdListen();
					WorkerAddrMap workerAddrMap = SRuntime.masterRt().getWorkerAddrMap();
					InetSocketAddress addrs[] = workerAddrMap.getSockAddrs(cmdPort).toArray(new InetSocketAddress[0]);
					UserGroupInformation ugi;
					ugi = UserGroupInformation.getCurrentUser();
					Object[][] params = new Object[addrs.length][1];
					for (int i=0; i<params.length; i++) {
						params[i][0] = new IntWritable(idlestat.idleTime(i));
					}
					Configuration hconf=new Configuration();
					Object[] rets = RPC.call(isStillIdle, params, addrs, ugi, hconf);
					for (Object ret:rets) {
						BooleanWritable f=(BooleanWritable)ret;
						if (f.get()==false) return;
					}
					Method epochDone=WorkerCmd.class.getMethod("epochDone", new Class[]{});
					params = new Object[addrs.length][0];
					RPC.call(epochDone, params, addrs, ugi, hconf);
					
					master.setEpochDone();
				} catch (Exception e) {
					L.fatal("Exception while calling WorkerCmd.isStillIdle():");
					L.fatal(ExceptionUtils.getStackTrace(e));
				}			
			}
		}
	}
	@Override
	public long getProtocolVersion(String arg0, long arg1) throws IOException {
		return WorkerRequest.versionID;
	}
	
	public void handleError(IntWritable _workerid, IntWritable _ruleid, Text _errorMsg) {
		try {
			Method halt=WorkerCmd.class.getMethod("haltEpoch", new Class[]{});
			Object p[] = new Object[]{};
			master.callWorkers(halt, p);			
		} catch (Exception e) {
			L.error("Exception while calling WorkerCmd.haltEpoch()");
			L.fatal(ExceptionUtils.getStackTrace(e));
		}
		int ruleid = _ruleid.get();
		String errorMsg = _errorMsg.toString();
		WorkerAddrMap workerAddrMap = SRuntime.masterRt().getWorkerAddrMap();
		InetAddress workerAddr = workerAddrMap.get(_workerid.get());
		errorMsg = workerAddr + ":"+errorMsg;
		ErrorRecord.getInst().addErrorMsg(ruleid, errorMsg);
		
		SRuntime.masterRt().setException(new SociaLiteException(errorMsg));
	}
}
