package socialite.dist.master;


import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;

import socialite.dist.ErrorRecord;
import socialite.dist.Host;
import socialite.dist.IdleStat;
import socialite.dist.worker.WorkerCmd;
import socialite.engine.Config;
import socialite.resource.SRuntimeMaster;
import socialite.resource.WorkerAddrMap;
import socialite.resource.SRuntime;
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
			int numHandlers = 16;// mostly to handle idle request and halt request
			Server server = RPC.getServer(this, masterAddr, reqListenPort, numHandlers, false, hConf);
			server.start();
		} catch (IOException e) {
			L.fatal("Exception while starting WorkerReqListener:");
			L.fatal(ExceptionUtils.getStackTrace(e));
		}
	}
	
	@Override
	public synchronized void register(Text workerIp) {
		Set<InetAddress> otherAddrSet = master.getWorkerAddrs();		
		Text[] otherAddrTexts=new Text[otherAddrSet.size()];
		int i=0;
		for (InetAddress inet:otherAddrSet) {
			otherAddrTexts[i++]=new Text(inet.getHostAddress());
		}
		boolean firstWorker = (i==0);
		TextArrayWritable restAddrs=new TextArrayWritable(otherAddrTexts);
		
		String addr=workerIp.toString();
		master.addWorkerAddr(addr);
		WorkerCmd cmd = master.getWorkerCmd(addr);		
		if (firstWorker) {
			int workerNum;
			if (Config.systemWorkerNum>0) {
				workerNum = Config.systemWorkerNum;
			} else {
				workerNum = cmd.getWorkerThreadNum().get();
			}
			master.setWorkerConf(Config.dist(workerNum));
		}
		Config workerConf = master.getWorkerConf();
		cmd.setWorkerThreadNum(new IntWritable(workerConf.getWorkerThreadNum()));
		cmd.makeConnections(restAddrs);
        master.addRegisteredWorker(Host.getAddr(addr));
	}

	public void reportIdle(IntWritable _epochId, IntWritable _workerId, IntWritable _time) {
        SRuntimeMaster runtime = SRuntimeMaster.getInst();
        int epochId = _epochId.get();
        IdleStat idleStat = runtime.getIdleStat(epochId);
        idleStat.update(_workerId.get(), _time.get());
        if (idleStat.allIdle()) {
            L.info("All idle for epoch:"+epochId);
            synchronized(idleStat) {
                try {
                    int[] idleTimestamps = idleStat.getIdleTimestamps();
                    Method isStillIdle =
                        WorkerCmd.class.getMethod("isStillIdle", new Class[]{IntWritable.class, IntWritable.class});
                    Object[][] params = new Object[idleTimestamps.length][2];
                    for (int i = 0; i < params.length; i++) {
                        params[i][0] = _epochId;
                        params[i][1] = new IntWritable(idleTimestamps[i]);
                    }
                    Object[] rets = MasterNode.callWorkers(isStillIdle, params);
                    for (Object ret : rets) {
                        BooleanWritable f = (BooleanWritable) ret;
                        if (f.get() == false) {
                            return;
                        }
                    }
                    runtime.notifyIdle(epochId);
                    Method setEpochDone = WorkerCmd.class.getMethod("setEpochDone", new Class[]{IntWritable.class});
                    Object[] param = new Object[]{_epochId};
                    MasterNode.callWorkersAsync(setEpochDone, param);
                    L.info("reportIdle() epoch done, epoch-id=" + epochId);
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
        L.warn("WorkerReqListener: handleError is called with error message:"+_errorMsg);

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
		WorkerAddrMap workerAddrMap = SRuntimeMaster.getInst().getWorkerAddrMap();
		InetAddress workerAddr = workerAddrMap.get(_workerid.get());
		errorMsg = workerAddr + ":"+errorMsg;
		ErrorRecord.getInst().addErrorMsg(ruleid, errorMsg);
	}
}
