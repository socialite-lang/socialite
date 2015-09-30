package socialite.resource;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.parser.Column;
import socialite.parser.Table;
import socialite.parser.GeneratedT;
import socialite.parser.DeltaTable;
import socialite.parser.RemoteHeadTable;
import socialite.util.Assert;
import socialite.util.HashCode;
import socialite.yarn.ClusterConf;

/**
 * Table Partition Map.
 * This maps a value (in 1st column) to its corresponding partition.
 *
 */
public class TablePartitionMap {
	public static final Log L=LogFactory.getLog(TablePartitionMap.class);
	final int partitionNum, virtualPartitionNum, minPartitionSize;
	CopyOnWriteArrayList<PartitionInfo> partitionInfo;

	public TablePartitionMap() {
        int threadNum = ClusterConf.get().getNumWorkerThreads();
		partitionNum = threadNum * 16;
		virtualPartitionNum = partitionNum;
		minPartitionSize = 1;
		partitionInfo = new CopyOnWriteArrayList<PartitionInfo>();
	}

	void addNullPartitionInfo(int maxIdx) {
        ArrayList<PartitionInfo> tmp = new ArrayList<PartitionInfo>();
        synchronized(partitionInfo) {
            for (int i=0; i<maxIdx+1-partitionInfo.size(); i++) {
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
		partitionInfo.set(t.id(), new PartitionInfo(t));
	}
	
	public int maxVirtualPartitionNum() { return virtualPartitionNum; }

	public int partitionNum(int tableId) {
		return partitionInfo.get(tableId).partitionNum();
	}
		
	public int virtualPartitionNum(int tableId) {
		return partitionInfo.get(tableId).virtualPartitionNum();
	}	
	public int getIndex(int tableId, Object o) {
		Table t=partitionInfo.get(tableId).t;
		if (t.isArrayTable()) {
			int range = (Integer)o;
			return partitionInfo.get(tableId).getRangeIndex(range);
		} else {
			int hash = HashCode.get(o);
			return partitionInfo.get(tableId).getHashIndex(hash);
		}
	}	
	public int getIndex(int tableId, int rangeOrHash) {
		Table t=partitionInfo.get(tableId).t;
		if (t.isArrayTable()) {
			return partitionInfo.get(tableId).getRangeIndex(rangeOrHash);
		} else {
			return partitionInfo.get(tableId).getHashIndex(rangeOrHash);
		}
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

	public int localBeginIndex(int tableId) {
		return partitionInfo.get(tableId).getLocalTableRange()[0];
	}
	public int localEndIndex(int tableId) {
		return partitionInfo.get(tableId).getLocalTableRange()[1];
	}
	public int localSize(int tableId) {
		int[] range = partitionInfo.get(tableId).getLocalTableRange();
		return range[1]-range[0]+1;
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
	class PartitionInfo {
		Table t;
		int arrayPartitionSize;
		int _partitionNum;
		PartitionInfo() {}
		PartitionInfo(Table _t) {
			t = _t;
			init();
		}
		void init() {
			arrayPartitionSize = computeArrayPartitionSize();
			if (t instanceof DeltaTable) {
				_partitionNum = 0; // partitioning is dynamically determined by VisitorBuilder.
			} else if (t instanceof RemoteHeadTable) {
				_partitionNum = 1;
			} else if (t.isArrayTable()) {
				_partitionNum = (t.arrayTableSize() + arrayPartitionSize-1)/arrayPartitionSize;
			} else if (t.isModTable()){
				_partitionNum = partitionNum;
			} else { _partitionNum = 1; }
		}
		public int partitionNum() {
			if (t.isPartitioned()) {
				if (t.hasOrderBy()) return 1;
				else return _partitionNum;
			}
			return 1;
		}
		int computeArrayPartitionSize() {
			int partitionSize = -1;
			Column c=t.getColumn(0);
			if (t.isArrayTable()) {
				int[] range = c.getRange();
				int arraySize = range[1] - range[0] + 1;
				partitionSize = (arraySize + virtualPartitionNum - 1) / virtualPartitionNum;
				partitionSize = Math.max(partitionSize, minPartitionSize);
				partitionSize = Math.min(partitionSize, arraySize);
			}
			return partitionSize;
		}

		public int virtualPartitionNum() {
			// returns 0 if dynamically determined. 
			// See VisitorBuilder:getNewVisitorInst()   RuleInfo:getVisitorNum();
			return _partitionNum;
		}

		public int[] getRange(int partitionIdx) {
			Column c=t.getColumn(0);
			assert c.hasRange();

			int[] range = new int[2];
			int beginIdx, endIdx;
			beginIdx = getLocalTableRange()[0];
			endIdx = getLocalTableRange()[1];
			range[0] = beginIdx + arrayPartitionSize * partitionIdx;
			range[1] = beginIdx + arrayPartitionSize * (partitionIdx+1)-1;
			if (range[1] > endIdx) {
                range[1] = endIdx;
            }
			return range;
		}

		public int getRangeIndex(int range) {
			int beginIdx;
			beginIdx = getLocalTableRange()[0];
			int idx = range-beginIdx;
			int partitionSize = arrayPartitionSize;
			return idx/partitionSize;
		}

		public int getHashIndex(int hash) {	
	        if (hash < 0) {
	        	hash = -hash;
	        	if (hash == Integer.MIN_VALUE)
	        		hash = 0;
	        }
	        assert !t.isArrayTable();
	        int _partitionNum = partitionNum;
	        return hash % _partitionNum;
		}
		
		int[] getLocalTableRange() {
			assert t.isArrayTable();
			int[] range = new int[2];			
			range[0] = t.arrayBeginIndex();
			range[1] = t.arrayBeginIndex()+t.arrayTableSize()-1;
			return range;
		}
	}
}
