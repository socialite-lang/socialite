package socialite.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ByteBufferOutputStream extends OutputStream {
	public static final Log L=LogFactory.getLog(ByteBufferOutputStream.class);
	static ByteBufferPool pool = ByteBufferPool.get();
	
	ByteBuffer buffer;
	Runnable capIncCallback;
	float incby = 2.0f;
	public ByteBufferOutputStream() {		
		buffer=pool.alloc();		
	}
	public ByteBufferOutputStream(int size) {
		buffer=pool.allocBiggerThan(size);
	}
	public void onCapacityInc(float _incby, Runnable r) {
		capIncCallback = r;
		incby = _incby;
	}
	void ensureCapacity(int size) {
		if (buffer.remaining() < size) {		
			
			int newCapacity = (int)Math.max(buffer.capacity()*incby, buffer.position()+size+1);
			ByteBuffer newBuffer = pool.alloc(newCapacity);	
			
			L.warn("Reallocating bytebuffer... from:"+buffer.capacity()+" to:"+newCapacity);
			
			buffer.flip();
			newBuffer.put(buffer);
			pool.free(buffer);
			buffer = newBuffer;
			
			if (capIncCallback!=null) {
				capIncCallback.run();
			}
		}
	}
	@Override
	public void write(int b) throws IOException {
		ensureCapacity(1);
		buffer.put((byte)b);
	}
	
	@Override
	public void write(byte[] bytes) throws IOException {
		ensureCapacity(bytes.length);
		buffer.put(bytes);
	}
	@Override
	public void write(byte[] bytes, int offset, int length) throws IOException {
		ensureCapacity(length);
		buffer.put(bytes, offset, length);
	}
	
	public ByteBuffer buffer() {
		buffer.flip();
		return buffer;
	}

}
