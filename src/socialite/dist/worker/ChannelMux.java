package socialite.dist.worker;

import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;


public class ChannelMux {
	public static final int channelNum=4;

	int _pos;
	SocketChannel[] channels;
	AtomicInteger selected;
	public ChannelMux() {
		channels = new SocketChannel[channelNum];
		_pos = 0;
		selected = new AtomicInteger(0);
	}
	public synchronized void add(SocketChannel ch) {
		assert _pos < channelNum;
		channels[_pos] = ch;
		_pos++;
	}
	public synchronized SocketChannel next() {
		int s = selected.incrementAndGet();
		return channels[s%channelNum];
	}
	public synchronized boolean isFull() {
		if (_pos==channelNum) return true;
		else return false;
	}
	
	public synchronized int size() {
		return _pos;
	}
}
