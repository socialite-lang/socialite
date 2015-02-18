package socialite.resource;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.codegen.Analysis;
import socialite.parser.Column;
import socialite.parser.DeltaRule;
import socialite.parser.DeltaTable;
import socialite.parser.RemoteBodyTable;
import socialite.parser.RemoteHeadTable;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.util.Assert;
import socialite.util.HashCode;

/**
 * Table Slice(Partition) Map.
 * This maps a value (in 1st column) to its corresponding partition.
 *
 */
public class TableSliceMap implements Externalizable {	
	private static final long serialVersionUID = 42;
	public static final Log L=LogFactory.getLog(TableSliceMap.class);

	int sliceNum, virtualSliceNum, minSliceSize;
	SliceInfo[] sliceInfo;
		
	public TableSliceMap() { }	
	public TableSliceMap(int _sliceNum, int _virtualSliceNum, int _minSliceSize) {
		sliceNum = _sliceNum;
		virtualSliceNum = _virtualSliceNum;
		minSliceSize = _minSliceSize;
		sliceInfo = new SliceInfo[16];	
	}
		
	public void addTable(Table t) {	
		assert t.id() >= 0;
		int tid=t.id();
		ensureCapacity(tid+1);
		
		if (sliceInfo[t.id()]!=null) { return; }
		sliceInfo[t.id()] = new SliceInfo(t);		
	}
	
	public int maxVirtualSliceNum() { return virtualSliceNum; }
	
	public int sliceNum(int tableId) {
		return sliceInfo[tableId].sliceNum();
	}
		
	public int virtualSliceNum(int tableId) {
		return sliceInfo[tableId].virtualSliceNum();		
	}	
	public int virtualSliceNum(int tableId, int columnIdx) {
		return sliceInfo[tableId].virtualSliceNum(columnIdx);
	}
	
	public int[] getRange(int tableId, int column, int sliceIdx) {
		return sliceInfo[tableId].getRange(column, sliceIdx);		
	}
	public int[] getRange(int tableId, int sliceIdx) {
		return sliceInfo[tableId].getRange(sliceIdx);
	}
	public int getIndex(int tableId, Object o) {
		Table t=sliceInfo[tableId].t;
		if (t.isArrayTable()) {
			int range = (Integer)o;
			return sliceInfo[tableId].getRangeIndex(range);
		} else {
			int hash = HashCode.get(o);
			return sliceInfo[tableId].getHashIndex(hash);
		}
	}	
	public int getIndex(int tableId, int column, Object o) {
		Table t = sliceInfo[tableId].t;
		Column c=t.getColumn(column);
		if (c.hasRange()) {
			int range = (Integer)o;
			return getIndex(tableId, column, range);
		} else {
			assert column==0;
			return getIndex(tableId, o);
		}
	}
	public int getIndex(int tableId, int rangeOrHash) {
		Table t=sliceInfo[tableId].t;
		if (t.isArrayTable()) {
			return sliceInfo[tableId].getRangeIndex(rangeOrHash);
		} else {
			return sliceInfo[tableId].getHashIndex(rangeOrHash);
		}
	}
    public int getIndex(int tableId, long rangeOrHash) { return getHashIndex(tableId, rangeOrHash); }
    public int getIndex(int tableId, float rangeOrHash) { return getHashIndex(tableId, rangeOrHash); }
    public int getIndex(int tableId, double rangeOrHash) { return getHashIndex(tableId, rangeOrHash); }

	public int getIndex(int tableId, int column, int rangeOrHash) {
		Table t = sliceInfo[tableId].t;
		Column c=t.getColumn(column);
		if (c.hasRange()) return sliceInfo[tableId].getRangeIndex(column, rangeOrHash);
		assert column==0;
		return sliceInfo[tableId].getHashIndex(rangeOrHash);
	}
	public int getRangeIndex(int tableId, int range) {
		return sliceInfo[tableId].getRangeIndex(range);
	}
	public int getRangeIndex(int tableId, int column, int range) {
		return sliceInfo[tableId].getRangeIndex(column, range);
	}	
	public int getHashIndex(int tableId, int val) { return sliceInfo[tableId].getHashIndex(HashCode.get(val)); }
	public int getHashIndex(int tableId, long val) {
		return sliceInfo[tableId].getHashIndex(HashCode.get(val));
	}
	public int getHashIndex(int tableId, double val) {
		return sliceInfo[tableId].getHashIndex(HashCode.get(val));
	}
	public int getHashIndex(int tableId, Object val) {
		return sliceInfo[tableId].getHashIndex(HashCode.get(val));
	}

	synchronized int ensureCapacity(int minCapacity) {		
		int oldCapacity=sliceInfo.length;
		if (minCapacity > oldCapacity) {
			int newCapacity = (oldCapacity*3)/2+1;
			newCapacity = Math.max(minCapacity, newCapacity);			
			sliceInfo = Arrays.copyOf(sliceInfo, newCapacity);
			return newCapacity;
		}
		return oldCapacity;
	}
	
	public int localBeginIndex(int tableId) {
		return sliceInfo[tableId].getLocalTableRange()[0];
	}
	public int localEndIndex(int tableId) {
		return sliceInfo[tableId].getLocalTableRange()[1];
	}
	public int localSize(int tableId) {
		int[] range = sliceInfo[tableId].getLocalTableRange();
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
		Assert.not_supported("TableSliceMap.isLocal() is only a place-holder.");
		return true;
	}
	
	public int machineIndexFor(int tableId, Object o) {
		Assert.not_supported("TableSliceMap.machineIndexFor() is only a place-holder.");
		return -1;
	}
	public int machineIndexFor(int tableId, int i) { return -1; }
	public int machineIndexFor(int tableId, long l) { return -1; }
	public int machineIndexFor(int tableId, float f) { return -1; }
	public int machineIndexFor(int tableId, double d) { return -1; }

	class SliceInfo {
		Table t;
		int[] arraySliceSizes;
		int primaryVirtualSliceNum;
		SliceInfo() {}
		SliceInfo(Table _t) {
			t = _t;
			init();
		}
		void init() {
			arraySliceSizes=computeArraySliceSizes();
			if (t instanceof DeltaTable) {
				primaryVirtualSliceNum = 0; // slice num is dynamically determined by VisitorBuilder.
			} else if (t instanceof RemoteHeadTable) {
				primaryVirtualSliceNum = 1;
			} else if (t.isArrayTable()) {
				int _virtSliceSize = arraySliceSizes[0];				
				primaryVirtualSliceNum = (t.arrayTableSize() + _virtSliceSize-1)/_virtSliceSize;
			} else if (t.isModTable()){
				primaryVirtualSliceNum = sliceNum; 
				if (t.hasOrderBy())
					primaryVirtualSliceNum=1;
			} else { primaryVirtualSliceNum = 1; }
		}
		public int sliceNum() {
			if (t.isSliced()) {
				if (t.hasOrderBy()) return 1;
				else return sliceNum;
			}
			return 1;
		}
		
		int[] computeArraySliceSizes() {
			int[] sliceSizes=new int[t.numColumns()];
			int prevSliceSize=-1;
			for (int i=0; i<t.numColumns(); i++) {
				if (t.nestingBegins(i)) prevSliceSize=-1;
				
				Column c=t.getColumn(i);
				if (c.hasRange()) {
					int[] range = c.getRange();
					int arraySize = range[1]-range[0]+1;
					int _sliceSize = (arraySize+virtualSliceNum-1)/virtualSliceNum;				
					_sliceSize = _sliceSize > minSliceSize? _sliceSize:minSliceSize;
					if (_sliceSize > arraySize) _sliceSize = arraySize;				
					sliceSizes[i] = _sliceSize; 
					prevSliceSize = sliceSizes[i];
				} else { sliceSizes[i] = prevSliceSize; }
			}
			return sliceSizes;
		}		
		public int virtualSliceNum() {
			// returns 0 if dynamically determined. 
			// See VisitorBuilder:getNewVisitorInst()   RuleInfo:getVisitorNum();
			return primaryVirtualSliceNum; 
		}
		public int virtualSliceNum(int columnIdx) {
			if (columnIdx==0) return virtualSliceNum();
			
			Column c = t.getColumn(columnIdx);
			if (c.hasRange()) {
				int arraySize = c.getRange()[1] - c.getRange()[0]+1;
				int virtSliceSize = arraySliceSizes[columnIdx];
				return (arraySize+virtSliceSize-1)/virtSliceSize;
			} else return 1;
		}
		
		public int[] getRange(int sliceIdx) {
			return getRange(0, sliceIdx);
		}
		public int[] getRange(int column, int sliceIdx) {
			Column c=t.getColumn(column);
			assert c.hasRange();
			
			int[] range=new int[2];
			int beginIdx, endIdx;
			if (column==0) {
				beginIdx = getLocalTableRange()[0];
				endIdx = getLocalTableRange()[1];
			} else {
				beginIdx = c.getRange()[0];
				endIdx=c.getRange()[1];
			} 
			range[0] = beginIdx+arraySliceSizes[column]*sliceIdx;
			range[1] = beginIdx+arraySliceSizes[column]*(sliceIdx+1)-1;
			if (range[1] > endIdx) {
                range[1] = endIdx;
            }
			return range;
		}
		
		public int getRangeIndex(int range) {
			return getRangeIndex(0, range);
		}		
		public int getRangeIndex(int column, int range) {
			Column c = t.getColumn(column);
			int beginIdx;
			if (column==0) beginIdx = getLocalTableRange()[0]; 
			else beginIdx = c.getRange()[0];
			int idx=range-beginIdx;
			
			int sliceSize=arraySliceSizes[column];			
			return idx/sliceSize;
		}
		public int getHashIndex(int hash) {	
	        if (hash < 0) {
	        	hash = -hash;
	        	if (hash == Integer.MIN_VALUE)
	        		hash = 0;
	        }
	        assert !t.isArrayTable();
	        int _sliceNum = sliceNum;
	        if (t.hasOrderBy()) _sliceNum=1;
	        return hash % _sliceNum;
		}
		
		int[] getLocalTableRange() {
			assert t.isArrayTable();
			int[] range = new int[2];			
			range[0] = t.arrayBeginIndex();
			range[1] = t.arrayBeginIndex()+t.arrayTableSize()-1;
			return range;
		}
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}
}
