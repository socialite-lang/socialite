package socialite.resource;

import socialite.dist.master.MasterNode;
import socialite.parser.GeneratedT;
import socialite.parser.Table;
import socialite.util.BitUtils;
import socialite.util.HashCode;
import socialite.yarn.ClusterConf;

class DistArrayTablePartitionInfo extends ArrayTablePartitionInfo {
	final int arrayBeginIndex;
	final int arrayEndIndex;
	final int shiftForNodeIdx;
	final int arraySizePerNode;
	DistArrayTablePartitionInfo(DistTablePartitionMap _map, Table _t) {
		super(_map, _t);

		arraySizePerNode = computeArraySizePerNode(t);
		shiftForNodeIdx = BitUtils.highestBitPos(arraySizePerNode) - 1;
		arrayBeginIndex = t.arrayBeginIndex();
		arrayEndIndex = t.arrayEndIndex();
	}

	DistTablePartitionMap map() {
		return (DistTablePartitionMap)map;
	}

	int workerNodeNum() {
		return ClusterConf.get().getNumWorkers();
	}

	boolean isMasterNode() {
		return MasterNode.getInstance() != null;
	}
	int computeArraySizePerNode(Table t) {
		int size = BitUtils.nextHighestPowerOf2(t.arrayTableSize());
		int sizePerNode = size / workerNodeNum();
		if (sizePerNode < 1) { sizePerNode = 1; }
		return sizePerNode;
	}
	int[] computeLocalRange(Table t) {
		if (isMasterNode()) return new int[]{-1, -1};

		int selfIdx=map().addrMap.myIndex();
		int arraySizePerNode = computeArraySizePerNode(t);
		int begin = t.arrayBeginIndex() + arraySizePerNode*selfIdx;
		int end = begin + arraySizePerNode - 1;
		if (end > t.arrayEndIndex()) {
			end = t.arrayEndIndex();
		}
		return new int[] {begin, end};
	}

	int computePartitionNum(Table t) {
		int arraySizePerNode = computeArraySizePerNode(t);
		if (arraySizePerNode < map.defaultPartitionNum) {
			return arraySizePerNode;
		}
		return map.defaultPartitionNum;
	}

	public boolean isLocal(int range) {
		return range >= localRangeBeginIdx && range <= localRangeEndIdx;
	}

	public int machineIndexFor(Object o) {
		int rangeVal = (Integer)o;
		return (rangeVal - arrayBeginIndex) >> shiftForNodeIdx;
	}

	public int machineIndexFor(int i) {
		int rangeVal = i;
		return (rangeVal - arrayBeginIndex) >> shiftForNodeIdx;
	}

	public int machineIndexFor(long l) {
		int rangeVal = (int)l;
		return (rangeVal - arrayBeginIndex) >> shiftForNodeIdx;
	}
}

public class DistPartitionInfo extends PartitionInfo {
	public static PartitionInfo create(DistTablePartitionMap map, Table t) {
		if (t.isArrayTable()) {
			return new DistArrayTablePartitionInfo(map, t);
		} else {
			return new DistPartitionInfo(map, t);
		}
	}
	final int myIndex;
	final int maskForNodeIndex;
	final int shiftNodeIdxBits;
	DistPartitionInfo(TablePartitionMap _map, Table _t) {
		super(_map, _t);
		myIndex = map().addrMap.myIndex();
		maskForNodeIndex = workerNodeNum() - 1;
		shiftNodeIdxBits = BitUtils.highestBitPos(workerNodeNum()) - 1;
	}
	int workerNodeNum() {
		return ClusterConf.get().getNumWorkers();
	}
	DistTablePartitionMap map() {
		return (DistTablePartitionMap)map;
	}
	public boolean isLocal(int hash) {
		assert !(t instanceof GeneratedT);
		return machineIndexForHash(hash) == myIndex;
	}

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
		return hash & maskForNodeIndex;
	}
	public int getHashIndex(int hash) {
        if (hash < 0) {
            hash = -hash;
            if (hash == Integer.MIN_VALUE) { hash = 0; }
        }
		return (hash >> shiftNodeIdxBits) & maskForHashIndex;
    }
}
