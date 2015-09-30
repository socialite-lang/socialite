package socialite.dist.worker;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;


public class ChannelMux implements Iterable<SocketChannel> {
    public static final int channelNum=4;

    int pos;
    SocketChannel[] channels;
    AtomicInteger selected;
    public ChannelMux() {
        channels = new SocketChannel[channelNum];
        pos = 0;
        selected = new AtomicInteger(0);
    }

    public synchronized void add(SocketChannel ch) {
        assert pos < channelNum;
        channels[pos] = ch;
        pos++;
    }
    public synchronized SocketChannel next() {
        int s = selected.incrementAndGet();
        return channels[s%channelNum];
    }
    public synchronized boolean isFull() {
        if (pos==channelNum) return true;
        else return false;
    }
	
    public synchronized int size() {
		return pos;
	}

    public Iterator<SocketChannel> iterator() {
        return new Iterator<SocketChannel>() {
            int cur = 0;
            public boolean hasNext() { return cur < channels.length; }
            public SocketChannel next() { return channels[cur++]; }
            public void remove() { }
        };
    }
}

