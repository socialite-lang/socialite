package socialite.dist.worker;

//import java.net.*;
//import java.util.*;
//import java.nio.channels.*;
//import java.io.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.EvalRefCount;
import socialite.dist.PortMap;
import socialite.dist.msg.*;
import socialite.util.ByteBufferInputStream;
import socialite.util.ByteBufferPool;
import socialite.util.FastInputStream;
import socialite.util.SociaLiteException;
import socialite.util.SocialiteFinishEval;


class ConnDesc {
	public static final Log L=LogFactory.getLog(WorkerConnPool.class);
	
	static final int SOCK_BUFSIZE=(512)*1024;
	
	ChannelMux sendChannels;
	ChannelMux recvChannels;
	
	ConnDesc() {
		sendChannels = new ChannelMux();
		recvChannels = new ChannelMux();
	}
	
	void setChannelOption(SocketChannel ch, boolean send) {
		try {
			ch.socket().setTcpNoDelay(WorkerConnPool.tcpNoDelay);			
			if (send) ch.socket().setSendBufferSize(SOCK_BUFSIZE); // send
			else ch.socket().setReceiveBufferSize(SOCK_BUFSIZE*2); // recv			
		} catch (SocketException e) {
			L.error("Cannot set TCP_NODELAY option:"+e);
		}
	}
	void addSendChannel(SocketChannel sendCh) {
		sendChannels.add(sendCh);
		setChannelOption(sendCh, true);
	}
	void addRecvChannel(SocketChannel recvCh) {
		recvChannels.add(recvCh);
		setChannelOption(recvCh, false);
	}
	
	public String toString() {
		String str="ConnDesc(";
		str += "sendCh["+sendChannels.size()+"]:";
		for (int i=0; i<sendChannels.size(); i++) {
			SocketChannel ch=sendChannels.next();
			str += ch.socket() +"/"+ch.socket().getInetAddress()+", ";
		}
		str += "\nrecvCh["+recvChannels.size()+"]:";
		for (int i=0; i<recvChannels.size(); i++) {
			SocketChannel ch=recvChannels.next();
			str += ch.socket() +"/"+ch.socket().getInetAddress()+", ";
		}
		return str;
	}
}

public class WorkerConnPool {
	public static final Log L=LogFactory.getLog(WorkerConnPool.class);

	final static boolean tcpNoDelay=true; // true turns off Nagle's algorithm
	
	Map<InetAddress, ConnDesc> connMap;	
	Selector connSelector;
	ServerSocketChannel workerAcceptChannel;

	//Object registerLock; // see http://php.mandelson.org/wp2/?p=311 for details. 
	List<SocketChannel> canceledList;
	PortMap portMap;

	public WorkerConnPool(PortMap _map) {
		portMap = _map;
		connMap = Collections.synchronizedMap(new HashMap<InetAddress, ConnDesc>());
		canceledList = Collections.synchronizedList(new ArrayList<SocketChannel>(64));
		makeReady();
		//registerLock = "lock";
	}

	Selector getSelector() { return connSelector; }

	public void makeReady() {
		try {
			connSelector = Selector.open();
			workerAcceptChannel = ServerSocketChannel.open();
			InetSocketAddress bindAddr=new InetSocketAddress(portMap.workerToWorkerListen());
			workerAcceptChannel.socket().bind(bindAddr);
			workerAcceptChannel.configureBlocking(false);			
			workerAcceptChannel.register(connSelector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			L.fatal("Exception while opening socket for workers:"+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void selectNow() {
		try { connSelector.selectNow(); }
		catch (IOException e) {
			throw new SociaLiteException(e);
		}
	}
	public Set<SelectionKey> select() {
		return select(0);
	}
	//public int registerLockCounter=0;
	public Set<SelectionKey> select(long timeout) {
		try {
			//synchronized(registerLock) { registerLockCounter++;}
			connSelector.select(timeout);
			return connSelector.selectedKeys();
		} catch (IOException e) {
			L.fatal("Exception while WorkerConnPool.select():"+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}
	
	public void connect(InetAddress[] workers) {
		Map<InetAddress, ConnDesc> map = beginConnect(workers);
		finishConnect(map);		
		registerRecvChannel(map.values());
	}
	
	Map<InetAddress, ConnDesc> beginConnect(InetAddress[] workers) {
		Map<InetAddress, ConnDesc> halfConnected = new HashMap<InetAddress, ConnDesc>();
		
		for (InetAddress workerAddr:workers) {
			ConnDesc connDesc = new ConnDesc();
			for (int i=0; i<ChannelMux.channelNum; i++) {
				try {		
					SocketChannel sendChannel = SocketChannel.open();
					int workerListen=portMap.workerToWorkerListen();
					InetSocketAddress inetSocketAddr=new InetSocketAddress(workerAddr, workerListen);
					
					assert sendChannel.isBlocking();
					sendChannel.connect(inetSocketAddr);
					boolean success=sendChannel.finishConnect();
					if (!success) L.fatal("Fail to connect to:"+workerAddr);
					
					connDesc.addSendChannel(sendChannel);					
				} catch (IOException e) {
					L.error("Cannot connect to "+ workerAddr);				
					L.fatal(ExceptionUtils.getStackTrace(e));
				}
			}
			halfConnected.put(workerAddr, connDesc);
		}
		return halfConnected;
	}
	
	void finishConnect(Map<InetAddress, ConnDesc> halfConnected) {
		int accepted=0;
		while (accepted<halfConnected.size()) {
			try { connSelector.select();
			} catch (IOException e) {
				L.fatal("Error while finishing connection process:"+e);
				L.fatal(ExceptionUtils.getStackTrace(e));
			}

			Iterator<SelectionKey> iter = (connSelector.selectedKeys()).iterator();
			while (iter.hasNext()) {
				SelectionKey key = iter.next();
				iter.remove();

				if (!(key.isValid() && key.isAcceptable())) {
					L.error("Unexpected key operation:"+key);
					continue;
				}
				try {
					SocketChannel recvChannel = ((ServerSocketChannel)key.channel()).accept();
					InetAddress workerAddr = recvChannel.socket().getInetAddress();
					
					ConnDesc connDesc = halfConnected.get(workerAddr);
					if (connDesc==null) L.error("Unexpected worker:"+workerAddr);
					
					connDesc.addRecvChannel(recvChannel);
					if (connDesc.recvChannels.isFull()) {
						accepted++;
						connMap.put(workerAddr, connDesc);
					}
				} catch (IOException e) {
					L.fatal("Exception while waiting for workers to connect:"+e);
					L.fatal(ExceptionUtils.getStackTrace(e));
				}
			}
		}
	}
	void registerRecvChannel(Collection<ConnDesc> connDescs) {
		for (ConnDesc cd : connDescs) {
			ChannelMux recvChannels = cd.recvChannels;
			assert recvChannels.size() == ChannelMux.channelNum;
			for (int i=0; i<ChannelMux.channelNum; i++) {
				SocketChannel recvChannel = recvChannels.next(); 
				try {
					recvChannel.configureBlocking(false);
					recvChannel.register(connSelector, SelectionKey.OP_READ);
				} catch (ClosedChannelException e) {
					L.fatal("Error while setting non-blocking option:"+e);
					L.fatal(ExceptionUtils.getStackTrace(e));
				} catch (IOException e) {
					L.fatal("Error while setting non-blocking option:"+e);
					L.fatal(ExceptionUtils.getStackTrace(e));
				}				
			}
		}
	}
	
	public void acceptConn(SelectionKey key) {
		assert key.channel().equals(workerAcceptChannel);
		
		SocketChannel recvChannel=null;
		try {		
			recvChannel = ((ServerSocketChannel)key.channel()).accept();
		} catch (Exception e) {
			L.fatal("Exception while accepting worker:"+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
			return;
		}
		
		InetAddress workerAddr = (recvChannel.socket()).getInetAddress();
		int workerPort=portMap.workerToWorkerListen();
		
		ConnDesc connDesc;
		if (connMap.containsKey(workerAddr)) 
			connDesc = connMap.get(workerAddr);
		else connDesc = new ConnDesc();
		connDesc.addRecvChannel(recvChannel);
		try {
			SocketChannel sendChannel = SocketChannel.open();
			InetSocketAddress inetSocketAddr=new InetSocketAddress(workerAddr, workerPort);
			sendChannel.connect(inetSocketAddr);
			if (!sendChannel.finishConnect()) {
				L.fatal("Error while accepting connection from:"+ workerAddr);
				return;
			}			
			connDesc.addSendChannel(sendChannel);
			sendChannel.configureBlocking(true);
						
			recvChannel.configureBlocking(false);
			recvChannel.register(connSelector, SelectionKey.OP_READ);
		} catch (IOException e) {
			L.fatal("Error while finishing connection to worker:"+workerAddr+", "+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
			return;
		}
		
		if (!connMap.containsKey(workerAddr))
			connMap.put(workerAddr, connDesc);
	}

	public ChannelMux sendChannelMuxFor(InetAddress workerAddr) {
		ConnDesc connDesc = connMap.get(workerAddr);
		if (connDesc == null) return null;
		
		return connDesc.sendChannels;		
	}
	
	public void send(ChannelMux sendChannelMux, int epochId, ByteBuffer bb) {
		ByteBuffer tmp = ByteBuffer.allocate(4);
		tmp.putInt(epochId);
        tmp.flip();
		SocketChannel sendCh = sendChannelMux.next();
		try {
			synchronized (sendCh) {
                while(tmp.hasRemaining())
                    sendCh.write(tmp);
                tmp.clear();
                tmp.putInt(bb.remaining());
                tmp.flip();
				while(tmp.hasRemaining())
					sendCh.write(tmp);
				while(bb.hasRemaining())
					sendCh.write(bb);
			}
		} catch (IOException e) {
			L.fatal("Exception while sending table to "+sendCh.socket().getInetAddress()+", "+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
			throw new SociaLiteException(e);
		}
	}
	
	public void cancelKey(SocketChannel channel) {
		SelectionKey key=channel.keyFor(connSelector);
		key.cancel();
	}
	
	WorkerMessage recv(InetAddress nodeAddr, SocketChannel channel) {
		WorkerMessage workerMsg=null;
		SocketChannel recvChannel = channel;
		ByteBuffer tmp = ByteBuffer.allocate(4);
		ByteBuffer buffer=null;

        try {
            do {
                int r=recvChannel.read(tmp);
                assert r!=-1;
            } while (tmp.hasRemaining());
            tmp.flip();
            int epochId = tmp.getInt();
            EvalRefCount.getInst().inc(epochId);

            tmp.clear();
			do {
				int r=recvChannel.read(tmp);
				assert r!=-1;
			} while (tmp.hasRemaining());
			tmp.flip();
			int size = tmp.getInt();

			buffer=ByteBufferPool.get().alloc(size);
			do { 
				int r =recvChannel.read(buffer);
				assert r!=-1;
			} while (buffer.hasRemaining());			
		} catch (Exception e) {
			L.fatal("Error while recv(from:"+nodeAddr+"):"+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
			return null;
		} finally {
			registerCanceledChannel(recvChannel);				
		}

		buffer.flip();		
		try {
			ByteBufferInputStream bbis = new ByteBufferInputStream(buffer);
			ObjectInputStream ois=new FastInputStream(bbis);
			workerMsg = (WorkerMessage)ois.readObject();
			ois.close();				
		} catch (SocialiteFinishEval e1) { 
			workerMsg = null;
		} catch (Exception e) {
			L.fatal("Error while creating WorkerMessage object:"+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
		} finally {
			ByteBufferPool.get().free(buffer);
		}
		
		return workerMsg;
	}
	
	public static InetAddress getNodeAddr(SelectionKey key) {
		SocketChannel selectedChannel;
		selectedChannel = (SocketChannel) (key.channel());
		return (selectedChannel.socket()).getInetAddress();
	}
	void registerCanceledChannel(SocketChannel sc) {
		try {
			sc.configureBlocking(false);
			canceledList.add(sc);	
		} catch (IOException e) {
			L.fatal("Exception while re-registering canceled channel:"+e);			
			L.fatal(ExceptionUtils.getStackTrace(e));
		}
		connSelector.wakeup();
	}
	void registerCanceledConn() {
		if (canceledList.isEmpty()) return;
		try {
			connSelector.selectNow(); // cleanup canceled keys
			//synchronized(registerLock) {
				synchronized(canceledList) {
					for (SocketChannel sc : canceledList) {
						sc.register(connSelector, SelectionKey.OP_READ);						
					}
					canceledList.clear();
				}
			//}
		} catch (Exception e) {
			L.fatal("Exception while registering cancelled channels:"+ e);
			L.fatal(ExceptionUtils.getStackTrace(e));
		}
	}
}	
