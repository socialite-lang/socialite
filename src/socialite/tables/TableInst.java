package socialite.tables;

import java.util.Iterator;

import java.io.Externalizable;

import socialite.visitors.IVisitor;

public interface TableInst extends Externalizable {
	public int id();
	public int size();
	public boolean isEmpty();
	public void clear();
	public void clear(int from, int to);
	
	public void setRequireFree(boolean flag);
	public boolean requireFree();
	public void setRequireFreeSmall(boolean flag);
	public boolean requireFreeSmall();

	public boolean isSmall(); // to determine buffer size in TmpTablePool
	public boolean isTooSmall(); // used by FastInputStream (received). if too small, TmpTablePool is not used for allocation
	public int vacancy(); // capacity - current size
	
	public boolean nearlyEmpty(); // empty, relatively to its capacity. used for splitting tasks (for DeltaTable)  in Worker.
	public boolean filledToCapacity();
	
	public TableInst copy();	
	public void addAllFast(TableInst table);

	public void setAccessed(boolean access);
	public boolean isAccessed();
	
	// if the table slice map returns 0,
	// VisitorBuilder uses the following method to decide
	// the # of slices and the # of visitors
	public int virtualSliceNum();
	public void setVirtualSliceNum(int sliceNum);

	// these are only for temporary tables (delta-table, remote-table).
	public double rowSize();
	public int totalAllocSize(); // total allocated memory for this table instance.
	public int totalDataSize(); // total size of data in this table instance.
	public void incEstimFactor(float incby);
	public int sizeEstimFactor();
	public void setSharedSizeEstimFactor(int estimFactor);
	
	public boolean removeLast();
}