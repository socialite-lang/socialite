package socialite.tables;

import java.io.Externalizable;

// For storing data to transfer to remote machines,
// or for storing deltas.
public abstract class TmpTableInst extends AbstractTableInst implements Externalizable{
	abstract public int ordinaryCapacity();
	abstract public int capacity();
	abstract public int size();
	abstract public int vacancy(); // capacity - current size
	abstract public boolean isSmall(); // has small # of elems compared to ordinaryCapacity()
		
	abstract public void addAll(TmpTableInst table);
	
	boolean _reuse=true;
	public void setReuse(boolean reuse) { _reuse = reuse; }
	public boolean reuse() { return _reuse; }
	// if the table slice map returns 0,
	// VisitorBuilder uses the following method to decide
	// the # of slices and the # of visitors
	public int virtualSliceNum() { return 1; }

	// Estimates the size of data in the table to copy to network buffer
	abstract public int totalDataSize(); // total size of data in this table instance.
	float _estim = -1;
	public void incEstimFactor(float incby) {
		_estim = _estim*incby;
	}
	public float sizeEstimFactor() {
		if (_estim<0) _estim = sharedSizeEstimFactor();
		return _estim;
	}
	abstract public void setSharedSizeEstimFactor(float estimFactor);
	abstract public float sharedSizeEstimFactor();
}
