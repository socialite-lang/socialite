package socialite.resource;

import socialite.parser.GeneratedT;
import socialite.parser.Table;
import socialite.util.BitUtils;

public interface PartitionInfo {
    static PartitionInfo create(TablePartitionMap map, Table t) {
        if (t.isArrayTable()) {
            return new ArrayTablePartitionInfo(map, t);
        } else {
            return new HashPartitionInfo(map, t);
        }
    }

    public int partitionNum();
    public boolean isLocal(int hash);

    public int partitionBegin(int partitionIdx);
    public int partitionSize(int partitionIdx);
    public int[] getRange(int partitionIdx);
    public int getIndex(Object o);
    public int getIndex(int val);
    public int getRangeIndex(int range);
    public int getHashIndex(int hash);

    public int machineIndexFor(Object o);
    public int machineIndexFor(int i);
	public int machineIndexFor(long l);
	public int machineIndexFor(float f);
	public int machineIndexFor(double d);
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
}
