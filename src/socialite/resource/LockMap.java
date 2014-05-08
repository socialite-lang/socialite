package socialite.resource;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import socialite.parser.Table;
import socialite.util.Assert;

// Contains r/w locks for table slices
public class LockMap /*implements Externalizable*/ {
	//private static final long serialVersionUID = 1;
	
	static class ArrayTableLockArray {
		// using Java's synchronization guarantee for final field
		public final Object[][] array;
		public int[] granularity;
		ArrayTableLockArray (Object[][] old, int[] oldGranular, int size) { 
			array = Arrays.copyOf(old, size);
			granularity = Arrays.copyOf(oldGranular, size);
		}
	}
	static class LockArray {
		// using Java's synchronization guarantee for final field
		public final Lock[][] array;
		LockArray(Lock[][] old, int size) { array = Arrays.copyOf(old, size); }
	}
	TableSliceMap sliceMap;
	Lock[][] locks;
	Object[][] arrayTableLocks;
	//Lock[][] arrayTableLocks;
	int[] arrayLockGranularity;
	
	public LockMap() {}	
	public LockMap(int maxTableId, TableSliceMap _sliceMap) {
		locks = new ReentrantLock[maxTableId+1][];
		arrayTableLocks = new Object[maxTableId+1][];
		//arrayTableLocks = new ReentrantLock[maxTableId+1][];
		arrayLockGranularity = new int[maxTableId+1];
		sliceMap = _sliceMap;
	}
	
	synchronized void ensureCapacity(int minCapacity) {
		int oldCapacity = locks.length;
		if (minCapacity > oldCapacity) {
			int newCapacity = (int)Math.max(minCapacity, oldCapacity*1.5);
			locks = new LockArray(locks, newCapacity).array;
			ArrayTableLockArray tmp = 
					new ArrayTableLockArray(arrayTableLocks, arrayLockGranularity, newCapacity);
			arrayLockGranularity = tmp.granularity;
			arrayTableLocks = tmp.array;
		}
	}
	
	static class ObjectMonitorArray {
		public final Object[] array;
		ObjectMonitorArray(int size) {
			array = new Object[size];
			for (int i=0; i<array.length; i++) {
				array[i] = new Object();
			}
		}
	}
	static class ReentLockArray {
		public final ReentrantLock[] array;
		ReentLockArray(int size) { 
			array = new ReentrantLock[size];
			for (int i=0; i<array.length; i++) 
				array[i] = new ReentrantLock();
		}
	}
	void createArrayTableLock(Table t) {
		int tid = t.id();
		assert t.isArrayTable();
		int size=sliceMap.localSize(tid);
	
		int sliceNum = sliceMap.maxVirtualSliceNum()*96;
		int granularity = (sliceMap.localSize(tid)+sliceNum-1)/sliceNum;
		if (granularity < 1) granularity = 1;
		int lockSize = (size+granularity-1) / granularity;
		
		if (arrayTableLocks[tid]!=null && arrayTableLocks[tid].length==lockSize) 
			return; // already created.
		
		if (SRuntime.masterRt()!=null) {
			arrayTableLocks[tid] = new Object[0];
			arrayLockGranularity[tid] = granularity;
		} else {
			arrayTableLocks[tid] = new ObjectMonitorArray(lockSize).array;
			arrayLockGranularity[tid] = granularity;
		}
	}
	public void createLock(Table t) {
		int tableId = t.id();
		if (tableId<0) return;
				
		ensureCapacity(tableId+1);
		if (t.isArrayTable()) createArrayTableLock(t);
		
		if (locks[tableId] != null) return;
		
		int virtualSliceNum = sliceMap.maxVirtualSliceNum();
		if (SRuntime.masterRt()!=null) {
			locks[tableId] = new ReentrantLock[0];
		} else {
			if (locks[tableId]!=null && locks[tableId].length == virtualSliceNum)
				return;
			locks[tableId] = new ReentLockArray(virtualSliceNum).array;
		}
	}
	
	public void lock(int tableId) {
		for (int i=0; i<locks[tableId].length; i++) 
			locks[tableId][i].lock();
	}	
	public void unlock(int tableId) {
		for (int i=0; i<locks[tableId].length; i++) 
			locks[tableId][i].unlock();
	}	
	/*public void arraylock(int tableid, int arrayindex) {
		int lockindex = arrayindex/arrayLockGranularity;
		arrayTableLocks[tableid][lockindex].lock();
	}
	public void arrayunlock(int tableid, int arrayindex) {
		int lockindex = arrayindex/arrayLockGranularity;
		arrayTableLocks[tableid][lockindex].unlock();
	}*/	
	public Object arraylock(int tableid, int arrayindex) {
		int lockindex=arrayindex/arrayLockGranularity[tableid];
		return arrayTableLocks[tableid][lockindex];
	}
	public void lock(int tableId, int sliceIdx) {
		locks[tableId][sliceIdx].lock();
	}	
	public void unlock(int tableId, int sliceIdx) {
		locks[tableId][sliceIdx].unlock();
	}
/*
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		sliceMap=(TableSliceMap)in.readObject();
		locks = new Lock[in.readInt()][];
		for (int i=0; i<locks.length; i++) {
			if (in.readBoolean()) {
				int tableId=i;
				int virtualSliceNum = sliceMap.virtualSliceNum(tableId);
				locks[tableId] = new ReentrantLock[virtualSliceNum];
				for (int j=0; j<locks[tableId].length; j++) {
					locks[tableId][j] = new ReentrantLock();
				}
			}
		}		
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(sliceMap);
		out.writeInt(locks.length);
		for (int i=0; i<locks.length; i++) {
			if (locks[i]==null) out.writeBoolean(false);
			else out.writeBoolean(true);
		}
	}*/
}
