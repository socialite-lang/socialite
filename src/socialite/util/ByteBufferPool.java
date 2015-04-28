package socialite.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
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
	
	final static long directBufferAlloc=1024*1024*(512+128+64);

	static ByteBufferPool theInst=null;			

    public static int getBufferQueueSize() { return (int)getDirectBufferAlloc()/bufferSize(); }
	public static long getDirectBufferAlloc() { return directBufferAlloc; }
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

    ArrayBlockingQueue<ByteBuffer> smallBufferList;
    ArrayBlockingQueue<ByteBuffer> bufferList;

	ByteBufferPool(long preallocSize) {
		int smallBufferNum = (int)(preallocSize * 0.2 / smallBufferSize)+128;
		int bufferNum = (int)(preallocSize * 0.8 / bufferSize);		
		
		smallBufferList = new ArrayBlockingQueue<ByteBuffer>(smallBufferNum, true);
		for (int i=0; i<smallBufferNum; i++) {
			ByteBuffer bb=ByteBuffer.allocateDirect(smallBufferSize);
			smallBufferList.add(bb);
		}

		bufferList = new ArrayBlockingQueue<ByteBuffer>(bufferNum, true);
		for (int i=0; i<bufferNum; i++) {
			ByteBuffer bb=ByteBuffer.allocateDirect(bufferSize);
			bufferList.add(bb);
		}
	}
	
	public static boolean bufferAvailable() { return !get().bufferList.isEmpty();	}
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
	
	ByteBuffer allocFrom(ArrayBlockingQueue<ByteBuffer> freeList, int _bufferSize, boolean limit) {
        try {
		    ByteBuffer bb=freeList.take();
            if (limit && _bufferSize < bb.capacity()) {
                bb.limit(_bufferSize);
            }
            return bb;
        } catch (InterruptedException e) {
            return null;
        }
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
			return allocFrom(bufferList, size, limit);
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
				L.info("    largeBuffercnt:"+largeBufferCnt);
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
