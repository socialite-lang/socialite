package socialite.resource;

import socialite.dist.master.MasterNode;
import socialite.parser.ArrayTable;
import socialite.parser.GeneratedT;
import socialite.parser.Table;
import socialite.util.BitUtils;
import socialite.util.HashCode;
import socialite.yarn.ClusterConf;

public interface DistPartitionInfo extends PartitionInfo {
    public static PartitionInfo create(DistTablePartitionMap map, Table t) {
        if (t instanceof  ArrayTable) {
            return new DistArrayTablePartitionInfo(map, t);
        } else {
            return new DistHashPartitionInfo(map, t);
        }
    }
}
class DistArrayTablePartitionInfo implements PartitionInfo {
    Table t;
    DistTablePartitionMap map;
    final int arrayBegin, arrayEnd;
    final int shiftForPartitionIdx;
    final int partitionSize;
    final PartitionNodeMap partitionNodeMap;
    final int myIndex;
    int[] localPartitions;
    DistArrayTablePartitionInfo(DistTablePartitionMap _map, Table _t) {
        map = _map;
        t = _t;
        myIndex = map.addrMap.myIndex();
        arrayBegin = t.arrayBeginIndex();
        arrayEnd = t.arrayEndIndex();

        int size = BitUtils.nextHighestPowerOf2(arrayEnd - arrayBegin + 1);
        int totalPartitionNum = computeTotalPartitionNum(t);
        partitionSize = Math.max(size/totalPartitionNum, 1);
        shiftForPartitionIdx = BitUtils.highestBitPos(partitionSize) - 1;

        int nodeNum = ClusterConf.get().getNumWorkers();
        partitionNodeMap = PartitionNodeMap.create(nodeNum, totalPartitionNum);
        initLocalInfo();
    }

    int computeTotalPartitionNum(Table t) {
        if (t.arrayTableSize() < map.defaultPartitionNum*ClusterConf.get().getMaxNumWorkers()) {
            return t.arrayTableSize();
        }
        int totalPartitionNum = map.defaultPartitionNum*ClusterConf.get().getMaxNumWorkers();
        assert BitUtils.isPowerOf2(totalPartitionNum);
        return totalPartitionNum;
    }

    void initLocalInfo() {
        localPartitions = partitionNodeMap.partitions(myIndex);
    }

    public int partitionNum() { return localPartitions.length; }

    public boolean isLocal(int range) {
        return partitionNodeMap.node((range - arrayBegin) >> shiftForPartitionIdx) == myIndex;
    }

    public int partitionBegin(int partitionIdx) {
        int begin = Math.min(arrayBegin + partitionSize * partitionIdx, arrayEnd);
        return begin;
    }

    public int partitionSize(int partitionIdx) {
        int begin = arrayBegin + partitionSize*partitionIdx;
        int end = arrayBegin + partitionSize*(partitionIdx + 1) - 1;
        if (begin > arrayEnd) {
            return 0;
        } else if (end > arrayEnd) {
            return arrayEnd - begin + 1;
        } else {
            return end - begin + 1;
        }
    }

    public int[] getRange(int partitionIdx) {
        int[] range = new int[2];
        range[0] = arrayBegin + partitionSize * partitionIdx;
        range[1] = arrayBegin + partitionSize * (partitionIdx + 1) - 1;
        if (range[0] > arrayEnd) {
            range[0] = arrayEnd + 1;
            range[1] = arrayEnd;
        } else if (range[1] > arrayEnd) {
            range[1] = arrayEnd;
        }
        return range;
    }

    public int getHashIndex(int hash) {
        throw new UnsupportedOperationException();
    }

    public int getIndex(Object o) {
        Integer rangeVal = (Integer)o;
        return getRangeIndex(rangeVal);
    }
    public int getIndex(int range) {
        return getRangeIndex(range);
    }

    public int getIndex(long val) {
        return getRangeIndex(val);
    }

    public int getRangeIndex(int range) {
        return (range - arrayBegin) >> shiftForPartitionIdx;
    }
    public int getRangeIndex(long range) {
        return (int)((range - arrayBegin) >> shiftForPartitionIdx);
    }
    public int machineIndexFor(Object o) {
        int rangeVal = (Integer)o;
        return partitionNodeMap.node(getRangeIndex(rangeVal));
    }

    public int machineIndexFor(int i) {
        int rangeVal = i;
        return partitionNodeMap.node(getRangeIndex(rangeVal));
    }

    public int machineIndexFor(long l) {
        int rangeVal = (int)l;
        return partitionNodeMap.node(getRangeIndex(rangeVal));
    }

    public int machineIndexFor(float f) {
        throw new UnsupportedOperationException();
    }

    public int machineIndexFor(double d) {
        throw new UnsupportedOperationException();
    }
    public boolean isValidRange(int range) {
        return range >= arrayBegin && range <= arrayEnd;
    }
}

class DistHashPartitionInfo implements PartitionInfo {
    Table t;
    DistTablePartitionMap map;
    final int partitionNum;
    final int maskForHashIndex;
    final PartitionNodeMap partitionNodeMap;

    final int myIndex;
    DistHashPartitionInfo(DistTablePartitionMap _map, Table _t) {
        map = _map;
        t = _t;

        myIndex = map.addrMap.myIndex();

        int totalPartitionNum = ClusterConf.get().getMaxNumWorkers()*map.defaultPartitionNum;
        assert BitUtils.isPowerOf2(totalPartitionNum);
        maskForHashIndex = totalPartitionNum - 1;

        partitionNum = computePartitionNum(t);
        int nodeNum = ClusterConf.get().getNumWorkers();
        partitionNodeMap = PartitionNodeMap.create(nodeNum, totalPartitionNum);
    }

    int computePartitionNum(Table t) {
        if (t instanceof GeneratedT) {
            return 1;
        } else {
            assert BitUtils.isPowerOf2(map.defaultPartitionNum);
            return map.defaultPartitionNum;
        }
    }

    public boolean isLocal(int hash) {
        assert !(t instanceof GeneratedT);
        return machineIndexForHash(hash) == myIndex;
    }

    public int partitionNum() { return partitionNum; }

    public int getHashIndex(int hash) {
        if (hash < 0) {
            hash = -hash;
            if (hash == Integer.MIN_VALUE) { hash = 0; }
        }
        return hash & maskForHashIndex;
    }
    public int getIndex(Object o) {
        return getHashIndex(o.hashCode());
    }

    public int getIndex(int val) {
        return getHashIndex(val);
    }

    public int getIndex(long val) { return getHashIndex(Long.hashCode(val)); }

    public int machineIndexFor(Object o) {
        int hashVal = HashCode.get(o);
        return machineIndexForHash(hashVal);
    }
    public int machineIndexFor(int i) {
        int hashVal = HashCode.get(i);
        return machineIndexForHash(hashVal);
    }
    public int machineIndexFor(long l) {
        int hashVal = HashCode.get(l);
        return machineIndexForHash(hashVal);
    }
    public int machineIndexFor(float f) {
        int hashVal = HashCode.get(f);
        return machineIndexForHash(hashVal);
    }
    public int machineIndexFor(double d) {
        int hashVal = HashCode.get(d);
        return machineIndexForHash(hashVal);
    }

    public int machineIndexForHash(int hash) {
        hash = hash>>12;
        if (hash < 0) {
            hash = -hash;
            if (hash == Integer.MIN_VALUE) { hash = 0; }
        }
        int partitionIdx = hash & maskForHashIndex;
        return partitionNodeMap.node(partitionIdx);
    }

    public int partitionBegin(int partitionIdx) { throw new UnsupportedOperationException(); }
    public int partitionSize(int partitionSize) { throw new UnsupportedOperationException(); }
    public int[] getRange(int partitionIdx) { throw new UnsupportedOperationException(); }
    public int getRangeIndex(int range) { throw new UnsupportedOperationException(); }

    public boolean isValidRange(int range) { return true; }
}
