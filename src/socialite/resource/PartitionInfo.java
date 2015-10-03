package socialite.resource;

import socialite.parser.GeneratedT;
import socialite.parser.Table;
import socialite.util.BitUtils;


class ArrayTablePartitionInfo extends PartitionInfo {
    final int arrayPartitionSize;
    final int localRangeBeginIdx, localRangeEndIdx;
    final int shiftForPartitionIdx;

    ArrayTablePartitionInfo(TablePartitionMap _map, Table _t) {
        super(_map, _t);

        int[] localRange = computeLocalRange(t);
        localRangeBeginIdx = localRange[0];
        localRangeEndIdx = localRange[1];

        int size = BitUtils.nextHighestPowerOf2(localRangeEndIdx - localRangeBeginIdx + 1);
        arrayPartitionSize = Math.max(size/partitionNum, 1);
        shiftForPartitionIdx = BitUtils.highestBitPos(arrayPartitionSize) - 1;
    }

    int[] computeLocalRange(Table t) {
        return new int[] {t.arrayBeginIndex(), t.arrayEndIndex()};
    }
    int computePartitionNum(Table t) {
        if (t.arrayTableSize() < map.defaultPartitionNum) {
            return t.arrayTableSize();
        }
        assert BitUtils.nextHighestPowerOf2(map.defaultPartitionNum) == map.defaultPartitionNum;
        return map.defaultPartitionNum;
    }

    public int partitionBegin(int partitionIdx) {
        int begin = Math.min(localRangeBeginIdx + arrayPartitionSize * partitionIdx, localRangeEndIdx);
        return begin;
    }

    public int partitionSize(int partitionIdx) {
        int begin = localRangeBeginIdx + arrayPartitionSize * partitionIdx;
        int end = localRangeBeginIdx + arrayPartitionSize * (partitionIdx+1)-1;
        if (begin > localRangeEndIdx) {
            return 0;
        } else if (end > localRangeEndIdx) {
            return localRangeEndIdx - begin + 1;
        } else {
            return end - begin + 1;
        }
    }

    public int[] getRange(int partitionIdx) {
        int[] range = new int[2];
        range[0] = localRangeBeginIdx + arrayPartitionSize * partitionIdx;
        range[1] = localRangeBeginIdx + arrayPartitionSize * (partitionIdx+1)-1;
        if (range[0] > localRangeEndIdx) {
            range[0] = localRangeBeginIdx;
        }
        if (range[1] > localRangeEndIdx) {
            range[1] = localRangeBeginIdx;
        }
        return range;
    }
    public int getRangeIndex(int range) {
        return (range - localRangeBeginIdx) >> shiftForPartitionIdx;
    }
    public int[] getLocalTableRange() {
        return new int[] {localRangeBeginIdx, localRangeEndIdx};
    }
}
public class PartitionInfo {
    public static PartitionInfo create(TablePartitionMap map, Table t) {
        if (t.isArrayTable()) {
            return new ArrayTablePartitionInfo(map, t);
        } else {
            return new PartitionInfo(map, t);
        }
    }

    Table t;
    TablePartitionMap map;
    final int partitionNum;
    final int maskForHashIndex;

    PartitionInfo(TablePartitionMap _partitionMap, Table _t) {
        map = _partitionMap;
        t = _t;
        partitionNum = computePartitionNum(t);
        maskForHashIndex = partitionNum  - 1;
    }
    int computePartitionNum(Table t) {
        if (t instanceof GeneratedT) {
            return 1;
        } else {
            return map.defaultPartitionNum;
        }
    }

    public int partitionNum() {
        return partitionNum;
    }

    public int getHashIndex(int hash) {
        if (hash < 0) {
            hash = -hash;
            if (hash == Integer.MIN_VALUE) { hash = 0; }
        }
        return hash & maskForHashIndex;
    }

    public boolean isLocal(int hash) { return true; }

    public int partitionBegin(int partitionIdx) {
        throw new UnsupportedOperationException();
    }
    public int partitionSize(int partitionSize) {
        throw new UnsupportedOperationException();
    }
    public int[] getRange(int partitionIdx) {
        throw new UnsupportedOperationException();
    }

    public int getRangeIndex(int range) {
        throw new UnsupportedOperationException();
    }

    public int machineIndexFor(Object o) {
        throw new UnsupportedOperationException();
    }
    public int machineIndexFor(int i) {
        throw new UnsupportedOperationException();
	}
	public int machineIndexFor(long l) {
        throw new UnsupportedOperationException();
	}
	public int machineIndexFor(float f) {
        throw new UnsupportedOperationException();
	}
	public int machineIndexFor(double d) {
        throw new UnsupportedOperationException();
	}

    public int[] getLocalTableRange() {
        throw new UnsupportedOperationException();
    }
}
