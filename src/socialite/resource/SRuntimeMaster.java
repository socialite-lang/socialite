package socialite.resource;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.codegen.Epoch;
import socialite.dist.IdleStat;
import socialite.dist.master.MasterNode;
import socialite.dist.worker.WorkerConnPool;
import socialite.engine.Config;
import socialite.parser.Rule;
import socialite.parser.Table;


public class SRuntimeMaster extends SRuntime {
	public static final Log L=LogFactory.getLog(SRuntimeMaster.class);
    static SRuntimeMaster inst=null;
    public static SRuntimeMaster createTest(WorkerAddrMap addrMap) {
        inst =new SRuntimeMaster(addrMap, Config.dist(1));
        return inst;
    }
    public static SRuntimeMaster create(MasterNode master, WorkerAddrMap addrMap) {
        inst =new SRuntimeMaster(addrMap, master.getWorkerConf());
        return inst;
    }
    public static SRuntimeMaster getInst() { return inst; }

	WorkerAddrMap workerAddrMap;
	WorkerConnPool workerConn;
	Sender sender;
    ConcurrentHashMap<Integer, IdleStat> idleStatMap;

	public SRuntimeMaster(WorkerAddrMap _addrMap, Config _conf) {
		// used by master-node
		workerAddrMap = _addrMap;		
		conf=_conf;
        workerConn = null;
        sender = null;
		tableMap = new HashMap<String, Table>();
        idleStatMap = new ConcurrentHashMap<Integer, IdleStat>(512, 0.75f, 64);
    }

    public IdleStat getIdleStat(int epochId) {
        IdleStat idleStat;
        if (!idleStatMap.containsKey(epochId)) {
            idleStat = new IdleStat();
            IdleStat prev=idleStatMap.putIfAbsent(epochId, idleStat);
            if (prev!=null) idleStat = prev;
        } else {
            idleStat = idleStatMap.get(epochId);
        }
        return idleStat;
    }
    public void notifyIdle(int epochId) {
        IdleStat stat = idleStatMap.get(epochId);
        if (stat == null) return;
        synchronized (stat) {
            stat.notify();
        }
    }
    @Override
    public void waitForIdle(int epochId) throws InterruptedException {
        IdleStat stat;
        if (!idleStatMap.containsKey(epochId)) {
            stat = new IdleStat();
            IdleStat prev=idleStatMap.putIfAbsent(epochId, stat);
            if (prev!=null) stat = prev;
        } else {
            stat = idleStatMap.get(epochId);
        }
        synchronized(stat) {
            while (!stat.allIdle()) {
                L.info("waitForIdle: stat=" + stat);
                stat.wait();
            }
        }

        idleStatMap.remove(epochId);
    }

    public WorkerAddrMap getWorkerAddrMap() {
		return workerAddrMap;
	}
	public Sender sender() { return sender; }
	public Config getConf() { return conf; }

    public TableSliceMap getSliceMap() {
		if (sliceMap==null) {
			int sliceNum = conf.sliceNum();
			int virtualSliceNum = conf.virtualSliceNum();
			int minSliceSize = conf.minSliceSize();					
			sliceMap = new DistTableSliceMap(workerAddrMap, sliceNum, virtualSliceNum, minSliceSize);
		}
		return sliceMap;
	}

}
