package socialite.resource;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.functions.MyId;
import socialite.parser.Column;
import socialite.parser.DeltaTable;
import socialite.parser.GeneratedT;
import socialite.parser.PrivateTable;
import socialite.parser.RemoteBodyTable;
import socialite.parser.RemoteHeadTable;
import socialite.parser.Table;
import socialite.util.Assert;
import socialite.util.HashCode;
import socialite.util.SociaLiteException;

public class DistTableSliceMap extends TableSliceMap {
	public static final Log L=LogFactory.getLog(DistTableSliceMap.class);

	WorkerAddrMap addrMap;
	public DistTableSliceMap() { }
	
	public DistTableSliceMap(WorkerAddrMap _addrMap, 
			int _sliceNum, int _virtualSliceNum, int _minSliceSize) {
		super(_sliceNum, _virtualSliceNum, _minSliceSize);
		addrMap = _addrMap;		
	}
	@Override
	public void addTable(Table t) {
		assert !(t instanceof GeneratedT);
		ensureCapacity(t.id()+1);
		sliceInfo[t.id()] = new DistSliceInfo(t);
	}
	@Override
	public int machineIndexFor(int tableId, Object o) {
		DistSliceInfo info = (DistSliceInfo)sliceInfo[tableId];
		return info.machineIndexFor(o);
	}
	@Override
	public int machineIndexFor(int tableId, int i) {
		DistSliceInfo info = (DistSliceInfo)sliceInfo[tableId];
		return info.machineIndexFor(i);
	}
	@Override
	public int machineIndexFor(int tableId, long l) {
		DistSliceInfo info = (DistSliceInfo)sliceInfo[tableId];
		return info.machineIndexFor(l);
	}
	@Override
	public int machineIndexFor(int tableId, float f) {
		DistSliceInfo info = (DistSliceInfo)sliceInfo[tableId];
		return info.machineIndexFor(f);
	}
	@Override
	public int machineIndexFor(int tableId, double d) {
		DistSliceInfo info = (DistSliceInfo)sliceInfo[tableId];
		return info.machineIndexFor(d);
	}
	
	public boolean isLocal(int tableId, int rangeOrHash) {
		return ((DistSliceInfo)sliceInfo[tableId]).isLocal(rangeOrHash);
	}	
	
	class DistSliceInfo extends TableSliceMap.SliceInfo {
		final int arrayPartitionSize;
		final int[] localTableRange;
		DistSliceInfo(Table _t) {
			t = _t;
			if (t.isArrayTable()) {
				arrayPartitionSize=computeArrayPartitionSize();
				//System.out.println(" WARN uncomment DistTableSliceMap getLocalTableRange()");
				localTableRange = computeLocalTableRange();
			} else {
				arrayPartitionSize=-1;
				localTableRange=null;
			}
			//System.out.println(" WARN uncomment DistTableSliceMap init()");
			init(); 
		}
		@Override
		void init() {
			super.init();
			if (t.isArrayTable()) {
				int _virtSliceSize = arraySliceSizes[0];
				primaryVirtualSliceNum = 
						(arrayPartitionSize+_virtSliceSize-1)/_virtSliceSize;
			}
		}
		int computeArrayPartitionSize() {
			assert t.isArrayTable();
			int partSize=(t.arrayTableSize()+addrMap.size()-1)/addrMap.size();
			if (partSize<1) partSize=1;
			return partSize;
		}
		@Override
		int[] computeArraySliceSizes() {
			int[] sliceSizes=super.computeArraySliceSizes();
			if (t.isArrayTable()) {
				int arraySize = arrayPartitionSize;
				int _sliceSize=(arraySize+virtualSliceNum-1)/virtualSliceNum;
				_sliceSize = _sliceSize > minSliceSize? _sliceSize:minSliceSize;
				if (_sliceSize > arraySize) _sliceSize=arraySize;
				sliceSizes[0] = _sliceSize;	
			}
			return sliceSizes;
		}		
		@Override
		public int getRangeIndex(int column, int range) {
			if (column==0) {
				assert t.isArrayTable();
				int idx=(range-t.arrayBeginIndex())%arrayPartitionSize;				
				int sliceSize=arraySliceSizes[column];			
				return idx/sliceSize;
			} else {
				return super.getRangeIndex(column, range);
			}
		}
		@Override
		public int getHashIndex(int hash) {
			return super.getHashIndex(hash>>12);
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
		int[] computeLocalTableRange() {
			int partitionSize=arrayPartitionSize;			
			int selfIdx=addrMap.myIndex();
			if (SRuntimeMaster.getInst()!=null) { selfIdx=0; }
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
				return (rangeVal-t.arrayBeginIndex())/arrayPartitionSize;
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
				return (rangeVal-t.arrayBeginIndex())/arrayPartitionSize;
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
				return (rangeVal-t.arrayBeginIndex())/arrayPartitionSize;
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
			if (hash < 0) {
				hash = -hash;
				if (hash == Integer.MIN_VALUE)
					hash = 0;
			}
			return hash % addrMap.size();
		}
	}
}
