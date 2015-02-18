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

// This holds locks for table partitions.
public class LockMap {
	TableSliceMap sliceMap;
	ReentrantLock[][] locks;
	
	public LockMap() {}	
	public LockMap(int maxTableId, TableSliceMap _sliceMap) {
		locks = new ReentrantLock[maxTableId+1][];
		sliceMap = _sliceMap;
	}
	
	synchronized void ensureCapacity(int minCapacity) {
		int oldCapacity = locks.length;
		if (minCapacity > oldCapacity) {
			int newCapacity = (int)Math.max(minCapacity, oldCapacity*1.5f);
			synchronized(this) {
				locks = Arrays.copyOf(locks, newCapacity);
			}
		}
	}
	public void createLock(Table t) {
		int tableId = t.id();
		if (tableId<0) return;
				
		ensureCapacity(tableId+1);
		
		if (locks[tableId] != null) return;
		
		int virtualSliceNum = sliceMap.maxVirtualSliceNum();
		ReentrantLock[] newLocks = new ReentrantLock[virtualSliceNum];
		for (int i=0; i<newLocks.length; i++) {
			newLocks[i] = new ReentrantLock();
		}
		synchronized (this) {
			locks[tableId] = newLocks;
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
	public ReentrantLock getLock(int tableId, int sliceIdx) {
		return locks[tableId][sliceIdx];
	}	
	
	public void lock(int tableId, int sliceIdx) {
		locks[tableId][sliceIdx].lock();
	}	
	public void unlock(int tableId, int sliceIdx) {
		locks[tableId][sliceIdx].unlock();
	}
}
