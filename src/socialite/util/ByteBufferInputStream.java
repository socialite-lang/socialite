package socialite.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ByteBufferInputStream extends InputStream {

	ByteBuffer buffer;
	public ByteBufferInputStream(ByteBuffer _buffer) {
		buffer=_buffer;
	}
	
	@Override
	public int available() {
		return buffer.remaining();
	}
	@Override
	public void reset() {
		throw new RuntimeException("Not Implemented");
	}
	@Override
	public int read() throws IOException {
		assert buffer.remaining()>=1;
		return buffer.get();
	}
	
	@Override
	public int read(byte[] bytes, int offset, int length) {
		assert buffer.remaining()>=length;
		buffer.get(bytes, offset, length);
		return length;
	}

	@Override
	public int read(byte[] bytes) {
		return read(bytes, 0, bytes.length);
	}
}
