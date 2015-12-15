package socialite.resource;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.parser.*;
import socialite.util.Assert;
import socialite.util.BitUtils;
import socialite.util.HashCode;
import socialite.yarn.ClusterConf;

/**
 * Table Partition Map.
 * This maps a value (of the 1st column) to its corresponding partition.
 *
 */
public class TablePartitionMap {
    public static final Log L=LogFactory.getLog(TablePartitionMap.class);

    final int defaultPartitionNum;
    CopyOnWriteArrayList<PartitionInfo> partitionInfo;

    public TablePartitionMap() {
        int threadNum = ClusterConf.get().getNumWorkerThreads();
        int partitionNum = threadNum*16;
        defaultPartitionNum = BitUtils.nextHighestPowerOf2(partitionNum);
        partitionInfo = new CopyOnWriteArrayList<>();
    }

    void addNullPartitionInfo(int maxIdx) {
        ArrayList<PartitionInfo> tmp = new ArrayList<PartitionInfo>(maxIdx+1-partitionInfo.size());
        synchronized(partitionInfo) {
            for (int i = 0; i < maxIdx + 1 - partitionInfo.size(); i++) {
                tmp.add(null);
            }
            partitionInfo.addAll(tmp);
        }
    }
    public void addTable(Table t) {
        assert !(t instanceof GeneratedT) && partitionInfo.get(t.id()) == null;
        if (t.id() >= partitionInfo.size()) {
            addNullPartitionInfo(t.id());
        }
        partitionInfo.set(t.id(), PartitionInfo.create(this, t));
    }

    public int partitionSize(int tableid, int partitionIdx) {
        /** only for {@link ArrayTablePartitionInfo} */
        return partitionInfo.get(tableid).partitionSize(partitionIdx);
    }
    public int partitionBegin(int tableid, int partitionIdx) {
        /** only for {@link ArrayTablePartitionInfo} */
        return partitionInfo.get(tableid).partitionBegin(partitionIdx);
    }
    public int partitionNum(int tableId) {
        return partitionInfo.get(tableId).partitionNum();
    }

    public int getIndex(int tableId, Object o) {
        return partitionInfo.get(tableId).getIndex(o);
    }
    public int getIndex(int tableId, int rangeOrHash) {
        return partitionInfo.get(tableId).getIndex(rangeOrHash);
    }
    public int getIndex(int tableId, long rangeOrHash) { return getHashIndex(tableId, rangeOrHash); }
    public int getIndex(int tableId, float rangeOrHash) { return getHashIndex(tableId, rangeOrHash); }
    public int getIndex(int tableId, double rangeOrHash) { return getHashIndex(tableId, rangeOrHash); }

    public int getRangeIndex(int tableId, int range) {
        return partitionInfo.get(tableId).getRangeIndex(range);
    }
    public int getHashIndex(int tableId, int val) {
        return partitionInfo.get(tableId).getHashIndex(HashCode.get(val));
    }
    public int getHashIndex(int tableId, long val) {
        return partitionInfo.get(tableId).getHashIndex(HashCode.get(val));
    }
    public int getHashIndex(int tableId, double val) {
        return partitionInfo.get(tableId).getHashIndex(HashCode.get(val));
    }
    public int getHashIndex(int tableId, Object val) {
        return partitionInfo.get(tableId).getHashIndex(HashCode.get(val));
    }

    public boolean isLocal(int tableId, Object o) {
        if (o instanceof Integer) {
            int i=(Integer)o;
            return isLocal(tableId, i);
        } else {
            return isLocal(tableId, HashCode.get(o));
        }
    }
    public boolean isLocal(int tableId, int rangeOrHash) {
        Assert.not_supported("TablePartitionMap.isLocal() is only a place-holder.");
        return true;
    }

    public int machineIndexFor(int tableId, Object o) {
        Assert.not_supported("TablePartitionMap.machineIndexFor() is only a place-holder.");
        return -1;
    }
    public int machineIndexFor(int tableId, int i) { return -1; }
    public int machineIndexFor(int tableId, long l) { return -1; }
    public int machineIndexFor(int tableId, float f) { return -1; }
    public int machineIndexFor(int tableId, double d) { return -1; }

    public int[] getRange(int tableId, int partitionIndex) {
        return partitionInfo.get(tableId).getRange(partitionIndex);
    }

}

