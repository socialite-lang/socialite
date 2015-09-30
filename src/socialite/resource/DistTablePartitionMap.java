package socialite.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.master.MasterNode;
import socialite.parser.GeneratedT;
import socialite.parser.Table;
import socialite.util.HashCode;

public class DistTablePartitionMap extends TablePartitionMap {
	public static final Log L=LogFactory.getLog(DistTablePartitionMap.class);

	WorkerAddrMap addrMap;
	public DistTablePartitionMap(WorkerAddrMap _addrMap) {
		super();
		addrMap = _addrMap;		
	}
	@Override
	public void addTable(Table t) {
        assert !(t instanceof GeneratedT) && partitionInfo.get(t.id()) == null;
        if (t.id() >= partitionInfo.size()) {
            addNullPartitionInfo(t.id());
        }
		partitionInfo.add(t.id(), new DistPartitionInfo(t));
	}
	@Override
	public int machineIndexFor(int tableId, Object o) {
		DistPartitionInfo info = (DistPartitionInfo)partitionInfo.get(tableId);
		return info.machineIndexFor(o);
	}
	@Override
	public int machineIndexFor(int tableId, int i) {
		DistPartitionInfo info = (DistPartitionInfo)partitionInfo.get(tableId);
		return info.machineIndexFor(i);
	}
	@Override
	public int machineIndexFor(int tableId, long l) {
		DistPartitionInfo info = (DistPartitionInfo)partitionInfo.get(tableId);
		return info.machineIndexFor(l);
	}
	@Override
	public int machineIndexFor(int tableId, float f) {
		DistPartitionInfo info = (DistPartitionInfo)partitionInfo.get(tableId);
		return info.machineIndexFor(f);
	}
	@Override
	public int machineIndexFor(int tableId, double d) {
		DistPartitionInfo info = (DistPartitionInfo)partitionInfo.get(tableId);
		return info.machineIndexFor(d);
	}
	
	public boolean isLocal(int tableId, int rangeOrHash) {
		return ((DistPartitionInfo)partitionInfo.get(tableId)).isLocal(rangeOrHash);
	}
	
	class DistPartitionInfo extends PartitionInfo {
		int arraySizePerNode;
		final int[] localTableRange;
		DistPartitionInfo(Table _t) {
			t = _t;
			if (t.isArrayTable()) {
				arraySizePerNode = computeArrayPartitionSizePerNode();
				//System.out.println(" WARN uncomment DistTablePartitionMap getLocalTableRange()");
				localTableRange = computeLocalTableRange();
			} else {
				arraySizePerNode = -1;
				localTableRange=null;
			}
			//System.out.println(" WARN uncomment DistTablePartitionMap init()");
			init(); 
		}
		@Override
		void init() {
			super.init();
			if (t.isArrayTable()) {
                int localArraySize = getLocalTableRange()[1]-getLocalTableRange()[0]+1;
				_partitionNum = (localArraySize + arrayPartitionSize - 1)/arrayPartitionSize;
			}
		}
		int computeArrayPartitionSizePerNode() {
			assert t.isArrayTable();
			int partSize=(t.arrayTableSize()+addrMap.size()-1)/addrMap.size();
			if (partSize<1) { partSize=1; }
			return partSize;
		}
		@Override
		int computeArrayPartitionSize() {
			int partitionSize = -1;
			if (t.isArrayTable()) {
				int localArraySize = computeArrayPartitionSizePerNode();
				partitionSize = (localArraySize + virtualPartitionNum - 1) / virtualPartitionNum;
				partitionSize = Math.max(partitionSize, minPartitionSize);
				partitionSize = Math.min(partitionSize, localArraySize);
			}
			return partitionSize;
		}

		public boolean isLocal(int rangeOrHash) {
			if (t.isArrayTable()) {
				return isLocalArrayT(rangeOrHash);
			} else if (t.isModTable()) {
				return isLocalModT(rangeOrHash);		
			} else {
				assert t.hasOrderBy();
				return addrMap.myIndex()==0;
			}
		}
		boolean isLocalArrayT(int range) {
			int[] localRange=getLocalTableRange();
			if (range >= localRange[0] && range <= localRange[1])
				return true;
			return false;
		}
        boolean isMasterNode() {
            return MasterNode.getInstance() != null;
        }
		int[] computeLocalTableRange() {
            if (isMasterNode()) return new int[]{-1, -1};

			int partitionSize= arraySizePerNode;
			int selfIdx=addrMap.myIndex();
			int beginIdx=t.arrayBeginIndex()+partitionSize*selfIdx;
			int endIdx = beginIdx + partitionSize-1;
			if (endIdx > t.arrayBeginIndex()+t.arrayTableSize()-1)
				endIdx = t.arrayBeginIndex()+t.arrayTableSize()-1;			
			if (endIdx < beginIdx) endIdx=beginIdx-1; // so that size=endIdx-beginIdx+1==0
			
			int[] tmp = new int[2];
			tmp[0] = beginIdx;
			tmp[1] = endIdx;
			return tmp;
		}
		int[] getLocalTableRange() {
			assert t.isArrayTable();
			return localTableRange;
		}		
		boolean isLocalModT(int hash) {			
			if (machineIndexForHash(hash) == addrMap.myIndex()) {
				return true;
			}
			return false;		
		}
		void rangeCheck(Table t, int v) {
			if (v < t.arrayBeginIndex() || v > t.arrayEndIndex()) {
				throw new UnsupportedOperationException("Value out of range:"+v+", range=["+t.arrayBeginIndex()+"-"+t.arrayEndIndex()+"]");
			}
		}
		int machineIndexFor(Object o) {
			if (t.isArrayTable()) { 
				int rangeVal = (Integer)o;
				rangeCheck(t, rangeVal);
				return (rangeVal-t.arrayBeginIndex())/arraySizePerNode;
			} else if (t.isModTable()) {
				int hashVal = HashCode.get(o);
				return machineIndexForHash(hashVal);
			} else {
				assert t.hasOrderBy();
				return 0;
			}
		}
		int machineIndexFor(int i) {
			if (t.isArrayTable()) { 
				int rangeVal = i;
				rangeCheck(t, rangeVal);
				return (rangeVal-t.arrayBeginIndex())/arraySizePerNode;
			} else if (t.isModTable()) {
				int hashVal = HashCode.get(i);
				return machineIndexForHash(hashVal);
			} else {
				assert t.hasOrderBy();
				return 0;
			}
		}
		int machineIndexFor(long l) {
			if (t.isArrayTable()) { 
				int rangeVal = (int)l;
				rangeCheck(t, rangeVal);
				return (rangeVal-t.arrayBeginIndex())/arraySizePerNode;
			} else if (t.isModTable()) {
				int hashVal = HashCode.get(l);
				return machineIndexForHash(hashVal);
			} else {
				assert t.hasOrderBy();
				return 0;
			}
		}
		int machineIndexFor(float f) {
			if (t.isArrayTable()) { 
				throw new UnsupportedOperationException("Unexpected float arg:"+f);
			} else if (t.isModTable()) {
				int hashVal = HashCode.get(f);
				return machineIndexForHash(hashVal);
			} else {
				assert t.hasOrderBy();
				return 0;
			}
		}
		int machineIndexFor(double d) {
			if (t.isArrayTable()) { 
				throw new UnsupportedOperationException("Unexpected double arg:"+d);
			} else if (t.isModTable()) {
				int hashVal = HashCode.get(d);
				return machineIndexForHash(hashVal);
			} else {
				assert t.hasOrderBy();
				return 0;
			}
		}
		int machineIndexForHash(int hash) {
            hash = hash>>12;
			if (hash < 0) {
				hash = -hash;
				if (hash == Integer.MIN_VALUE)
					hash = 0;
			}
			return hash % addrMap.size();
		}
	}
}
