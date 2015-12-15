package socialite.resource;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.IdleStat;
import socialite.dist.worker.WorkerConnPool;


public class SRuntimeMaster extends SRuntime {
    public static final Log L=LogFactory.getLog(SRuntimeMaster.class);
    static SRuntimeMaster inst=null;
    public static SRuntimeMaster create(WorkerAddrMap addrMap) {
        inst =new SRuntimeMaster(addrMap);
        return inst;
    }
    public static SRuntimeMaster getInst() { return inst; }

    WorkerAddrMap workerAddrMap;
    WorkerConnPool workerConn;
    Sender sender;
    ConcurrentHashMap<Integer, IdleStat> idleStatMap;

    public SRuntimeMaster(WorkerAddrMap _addrMap) {
        // used by master-node
        workerAddrMap = _addrMap;
        workerConn = null;
        sender = null;
        tableMap = new HashMap<>();
        idleStatMap = new ConcurrentHashMap<>(512, 0.75f, 64);
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

    public TablePartitionMap getPartitionMap() {
        if (partitionMap==null) {
            partitionMap = new DistTablePartitionMap(workerAddrMap);
        }
        return partitionMap;
    }

}
