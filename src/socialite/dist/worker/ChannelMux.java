package socialite.dist.worker;

import java.nio.channels.SocketChannel;


public class ChannelMux {
	public static final int channelNum=8;
	
	int _pos;
	SocketChannel[] channels;
	int selected;
	public ChannelMux() {
		channels = new SocketChannel[channelNum];
		_pos=0;
		selected=0;
	}
	public synchronized void add(SocketChannel ch) {
		assert _pos < channelNum;
		channels[_pos] = ch;
		_pos++;
	}
	public synchronized SocketChannel next() {
		selected++;		
		return channels[selected%channelNum];
	}
	public synchronized boolean isFull() {
		if (_pos==channelNum) return true;
		else return false;
	}
	
	public synchronized int size() {
		return _pos;
	}
}
