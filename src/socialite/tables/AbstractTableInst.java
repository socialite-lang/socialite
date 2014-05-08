package socialite.tables;

import socialite.util.Assert;

public abstract class AbstractTableInst implements TableInst {
	static int estim = 1;
	
	public boolean isEmpty() { return size() == 0; }
	
	transient boolean requireFree=false;
	public void setRequireFree(boolean flag) { requireFree = flag; }	
	public boolean requireFree() { return requireFree; }	
	transient boolean requireFreeSmall=false;
	public void setRequireFreeSmall(boolean flag) { requireFreeSmall = flag; }
	public boolean requireFreeSmall() { return requireFreeSmall; }	
	
	public boolean isSmall() { return false; }
	public boolean isTooSmall() { return false; }	
	public int vacancy() { return 0; }
	
	public void clear(int from, int to) {
		Assert.not_supported();
	}
	
	public boolean nearlyEmpty() { return false; }
	public boolean filledToCapacity() {
		Assert.not_implemented();
		return false;
	}
	
	public TableInst copy() { 
		Assert.not_supported();
		return null;
	}	
	public void addAllFast(TableInst table) {
		Assert.not_supported();
	}
	
	public void setAccessed(boolean access) {}
	public boolean isAccessed() { return false;}
	
	protected int virtualSliceNum=1;
	public int virtualSliceNum() { return virtualSliceNum; }
	public void setVirtualSliceNum(int sliceNum) { virtualSliceNum=sliceNum; }

	public double rowSize() { return 16.0; }
	public int totalAllocSize() {
		assert false:"totalAllocSize() should be overriden.";
		return -1; 
	}
	public int totalDataSize() {
		assert false:"totalDataSize() should be overriden.";
		return -1; 
	}	
	int _estim = -1;
	public void incEstimFactor(float incby) { _estim = (int)(_estim*incby); }
	public int sizeEstimFactor() {
		if (_estim < 0) _estim = estim;
		return _estim; 
	}
	public void setSharedSizeEstimFactor(int v) {
		if (v > estim) {
			estim = v;
		}
	}
	public int sharedSizeEstimFactor() {
		return estim;
	}
	
	public boolean removeLast() {
		Assert.not_supported();
		return false;
	}
}
