package socialite.resource;

import socialite.parser.ArrayTable;
import socialite.parser.GeneratedT;
import socialite.parser.Table;
import socialite.util.BitUtils;

public interface PartitionInfo {
    static PartitionInfo create(TablePartitionMap map, Table t) {
        if (t instanceof ArrayTable) {
            return new ArrayTablePartitionInfo(map, t);
        } else {
            return new HashPartitionInfo(map, t);
        }
    }

    int partitionNum();
    boolean isLocal(int hash);

    int partitionBegin(int partitionIdx);
    int partitionSize(int partitionIdx);
    int[] getRange(int partitionIdx);
    int getIndex(Object o);
    int getIndex(int val);
    int getRangeIndex(int range);
    int getHashIndex(int hash);

    int machineIndexFor(Object o);
    int machineIndexFor(int i);
	int machineIndexFor(long l);
	int machineIndexFor(float f);
	int machineIndexFor(double d);
    boolean isValidRange(int range);
}

class ArrayTablePartitionInfo implements PartitionInfo {
    Table t;
    TablePartitionMap map;

    final int arrayBegin, arrayEnd;
    final int partitionNum, partitionSize;
    final int shiftForPartitionIdx;

    ArrayTablePartitionInfo(TablePartitionMap _map, Table _t) {
        map = _map;
        t = _t;

        arrayBegin = t.arrayBeginIndex();
        arrayEnd = t.arrayEndIndex();

        partitionNum = computePartitionNum(t);
        int size = BitUtils.nextHighestPowerOf2(arrayEnd - arrayBegin + 1);
        partitionSize = Math.max(size/partitionNum, 1);
        shiftForPartitionIdx = BitUtils.highestBitPos(partitionSize) - 1;
    }

    int computePartitionNum(Table t) {
        if (t.arrayTableSize() < map.defaultPartitionNum) {
            return t.arrayTableSize();
        }
        assert BitUtils.isPowerOf2(map.defaultPartitionNum);
        return map.defaultPartitionNum;
    }

    public int partitionNum() { return partitionNum; }

    public boolean isLocal(int hash) { return true; }

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
        range[1] = arrayBegin + partitionSize * (partitionIdx+1)-1;
        if (range[0] > arrayEnd) {
            range[0] = arrayEnd + 1;
            range[1] = arrayEnd;
        } else if (range[1] > arrayEnd) {
            range[1] = arrayEnd;
        }
        return range;
    }

    public int getIndex(Object o) {
        Integer rangeVal = (Integer)o;
        return getRangeIndex(rangeVal);
    }

    public int getIndex(int val) {
        return getRangeIndex(val);
    }
    public int getRangeIndex(int range) {
        return (range - arrayBegin) >> shiftForPartitionIdx;
    }
    public int getHashIndex(int v) { throw new UnsupportedOperationException(); }

    public int machineIndexFor(Object o) { throw new UnsupportedOperationException(); }
    public int machineIndexFor(int i) { throw new UnsupportedOperationException(); }
    public int machineIndexFor(long l) { throw new UnsupportedOperationException(); }
    public int machineIndexFor(float f) { throw new UnsupportedOperationException(); }
    public int machineIndexFor(double d) { throw new UnsupportedOperationException(); }

    public boolean isValidRange(int range) {
        return range >= arrayBegin && range <= arrayEnd;
    }
}

class HashPartitionInfo implements PartitionInfo {
    Table t;
    TablePartitionMap map;
    final int partitionNum;
    final int maskForHashIndex;

    HashPartitionInfo(TablePartitionMap _map, Table _t) {
        map = _map;
        t = _t;
        partitionNum = computePartitionNum(t);
        maskForHashIndex = partitionNum  - 1;
    }
    int computePartitionNum(Table t) {
        if (t instanceof GeneratedT) {
            return 1;
        } else {
            assert BitUtils.isPowerOf2(map.defaultPartitionNum);
            return map.defaultPartitionNum;
        }
    }

    public int partitionNum() { return partitionNum; }

    public boolean isLocal(int hash) { return true; }

    public int getIndex(Object o) {
        return getHashIndex(o.hashCode());
    }
    public int getIndex(int val) {
        return getHashIndex(val);
    }
    public int getHashIndex(int hash) {
        if (hash < 0) {
            hash = -hash;
            if (hash == Integer.MIN_VALUE) { hash = 0; }
        }
        return hash & maskForHashIndex;
    }

    public int partitionBegin(int partitionIdx) { throw new UnsupportedOperationException(); }
    public int partitionSize(int partitionSize) { throw new UnsupportedOperationException(); }
    public int[] getRange(int partitionIdx) { throw new UnsupportedOperationException(); }
    public int getRangeIndex(int range) { throw new UnsupportedOperationException(); }

    public int machineIndexFor(Object o) { throw new UnsupportedOperationException(); }
    public int machineIndexFor(int i) { throw new UnsupportedOperationException(); }
	public int machineIndexFor(long l) { throw new UnsupportedOperationException(); }
	public int machineIndexFor(float f) { throw new UnsupportedOperationException(); }
	public int machineIndexFor(double d) { throw new UnsupportedOperationException(); }

    public boolean isValidRange(int range) { return true; }
}
