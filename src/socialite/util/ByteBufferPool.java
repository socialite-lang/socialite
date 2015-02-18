package socialite.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.resource.SRuntime;

public class ByteBufferPool {
	public static final Log L=LogFactory.getLog(ByteBufferPool.class);
	final static boolean verbose=false;
	
	final static int smallBufferSize=132*1024;
	final static int bufferSize=1280*1024; // see WorkerConnPool:ConnDesc.SOCK_BUFSIZE
	
	final static long directBufferAlloc=1024*1024*256;
	final static int directBufferAllocKB=(int)directBufferAlloc/1024;
	final static int maxDynBufferNum=(int)(directBufferAlloc/bufferSize);
	
	static ByteBufferPool theInst=null;			
	
	public static long getDirectBufferAlloc() { return directBufferAlloc; }
	public static int getDirectBufferAllocKB() { return directBufferAllocKB; }
	public static int smallBufferSize() { return smallBufferSize; }
	public static int bufferSize() { return bufferSize; }	
	
	static {
		if (verbose) L.warn("ByteBufferPool: verbose flag is on!");
	}
	
	public static ByteBufferPool get() {
		if (theInst==null) {
			theInst = new ByteBufferPool(directBufferAlloc);
		}
		return theInst;
	}
	
	// for usage statistics
	int smallBufferCnt=0;
	int bufferCnt=0;
	int largeBufferCnt=0;
	
	ConcurrentLinkedQueue<ByteBuffer> smallBufferList;
	ConcurrentLinkedQueue<ByteBuffer> bufferList;	
	SoftRefArrayQueue<ByteBuffer> dynBufferList;
	
	ByteBufferPool(long preallocSize) {
		int smallBufferNum = (int)(preallocSize * 0.2 / smallBufferSize)+128;
		int bufferNum = (int)(preallocSize * 0.8 / bufferSize);		
		
		smallBufferList = new ConcurrentLinkedQueue<ByteBuffer>();
		for (int i=0; i<smallBufferNum; i++) {
			ByteBuffer bb=ByteBuffer.allocateDirect(smallBufferSize);
			smallBufferList.add(bb);
		}

		bufferList = new ConcurrentLinkedQueue<ByteBuffer>();
		for (int i=0; i<bufferNum; i++) {
			ByteBuffer bb=ByteBuffer.allocateDirect(bufferSize);
			bufferList.add(bb);
		}

		dynBufferList = new SoftRefArrayQueue<ByteBuffer>(maxDynBufferNum);
	}
	
	public static boolean bufferAvailable() {
		return !get().bufferList.isEmpty() || !get().dynBufferList.isEmpty();
	}
	public static boolean smallBufferAvailable() {
		return !get().smallBufferList.isEmpty();
	}
	
	public ByteBuffer alloc() {
		return alloc(bufferSize);
	}
	public ByteBuffer allocBiggerThan(int size) {
		if (size < 4096) size += 512;
		
		return alloc(size, false);
	}
	
	ByteBuffer allocFrom(ConcurrentLinkedQueue<ByteBuffer> freeList, int _bufferSize, boolean limit) {
		ByteBuffer bb=null;
		
		bb = freeList.poll();		
		if (bb!=null) {
			if (limit && _bufferSize<bb.capacity())
				bb.limit(_bufferSize);
			return bb;
		}
		if (verbose) { allocedBuffer.addAndGet(_bufferSize); }
		return ByteBuffer.allocate(_bufferSize);
	}
	
	ByteBuffer allocFromBuffer(int _bufferSize, boolean limit) {
		ByteBuffer bb=null;
		bb = bufferList.poll();		
		if (bb!=null) {
			if (limit && _bufferSize<bb.capacity())
				bb.limit(_bufferSize);
			return bb;
		}
		synchronized(dynBufferList) {
			bb = dynBufferList.get();
		}
		if (bb!=null) {
			if (limit && _bufferSize<bb.capacity())
				bb.limit(_bufferSize);
			return bb;
		}		

		assert bufferSize >= _bufferSize;
		bb = ByteBuffer.allocate(bufferSize);
		if (verbose) { allocedBuffer.addAndGet(bb.capacity()); }
		if (limit && _bufferSize<bb.capacity())
			bb.limit(_bufferSize);
		return bb;
	}
	
	public ByteBuffer alloc(int size) {
		return alloc(size, true);
	}
	
	public ByteBuffer alloc(int size, boolean limit) {
		if (size <= smallBufferSize) {
			if (verbose) smallBufferCnt++;
			return allocFrom(smallBufferList, size, limit);
		} else if (size <= bufferSize) {
			if (verbose) bufferCnt++;
			return allocFromBuffer(size, limit);
		} else {
			if (verbose) {
				largeBufferCnt++;			
				allocedBuffer.addAndGet(size);
			}
			return ByteBuffer.allocate(size);
		}		
	}
	
	AtomicInteger allocedBuffer = new AtomicInteger(0);
	public void free(ByteBuffer bb) {		
		if (bb.isDirect()) {
			stats();
			bb.clear();
			if (bb.capacity()==smallBufferSize) {
				smallBufferList.add(bb);
			} else if (bb.capacity()==bufferSize) {
				bufferList.add(bb);	
			} else {
				assert false:"impossible!";
			}			
		} else {
			if (verbose) { allocedBuffer.addAndGet(-bb.capacity()); }
			
			stats();
			if (bb.capacity() == bufferSize) {
				bb.clear();
				synchronized(dynBufferList) {
					if (dynBufferList.size() < maxDynBufferNum)
						dynBufferList.add(bb);					
				}
			}
		}
	}
	
	int callCnt=0;
	void stats() {
		if (verbose) {
			callCnt++;
			if (callCnt %512==1) {
				L.info("ByteBufferPool statistics");
				L.info("    smallBuffercnt:"+smallBufferCnt);
				L.info("    buffercnt:"+bufferCnt);
				L.info("    dynBuffercnt:"+dynBufferList.size());
				L.info("    largeBuffercnt:"+largeBufferCnt);
				L.info("    dynBufferList.size():"+dynBufferList.size());
				
				L.info("    NOTICE allocedBuffer:"+allocedBuffer.get()/1024/1024+"MB");
			}
		}
	}
	
	public static void main(String[] args) {
		Runtime r=Runtime.getRuntime();
		long mem = (long)(r.totalMemory()/1024.0/1024.0/1024.0);
		System.out.println("Total memory:"+mem+"GB");
	}
}
