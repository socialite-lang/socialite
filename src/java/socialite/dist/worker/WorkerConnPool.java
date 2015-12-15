package socialite.dist.worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.net.NetUtils;

import socialite.dist.EvalRefCount;
import socialite.dist.PortMap;
import socialite.dist.msg.WorkerMessage;
import socialite.util.ByteBufferInputStream;
import socialite.util.ByteBufferPool;
import socialite.util.FastInputStream;
import socialite.util.SociaLiteException;
import socialite.util.SocialiteFinishEval;
import socialite.util.UnresolvedSocketAddr;

class ConnDesc {
    public static final Log L=LogFactory.getLog(WorkerConnPool.class);

    static final int SOCK_BUFSIZE=(512)*1024;

    ChannelMux sendChannels;

    ConnDesc() {
        sendChannels = new ChannelMux();
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

    public String toString() {
        String str="ConnDesc(";
        str += "sendCh["+sendChannels.size()+"]:";
        for (int i=0; i<sendChannels.size(); i++) {
            SocketChannel ch=sendChannels.next();
            str += ch.socket() +"/"+ch.socket().getInetAddress()+", ";
        }
        return str;
    }
    public ChannelMux getSendChannels() { return sendChannels; }
}

public class WorkerConnPool {
    public static final Log L=LogFactory.getLog(WorkerConnPool.class);

    final static boolean tcpNoDelay=true; // true turns off Nagle's algorithm

    ConcurrentMap<UnresolvedSocketAddr, ConnDesc> connMap;
    Selector connSelector;
    ServerSocketChannel workerAcceptChannel;

    //Object registerLock; // see http://php.mandelson.org/wp2/?p=311 for details.
    List<SocketChannel> canceledList;

    public WorkerConnPool() {
        connMap = new ConcurrentHashMap<UnresolvedSocketAddr, ConnDesc>();
        canceledList = Collections.synchronizedList(new ArrayList<SocketChannel>(64));
        makeReady();
        //registerLock = "lock";
    }

    Selector getSelector() { return connSelector; }

    public void makeReady() {
        try {
            connSelector = Selector.open();
            workerAcceptChannel = ServerSocketChannel.open();
            workerAcceptChannel.socket().setReuseAddress(true);

            InetAddress inet = InetAddress.getByName(NetUtils.getHostname().split("/")[1]);
            InetSocketAddress bindAddr = new InetSocketAddress(inet, PortMap.worker().usePort("data"));
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

    boolean isMyAddress(UnresolvedSocketAddr addr) {
        boolean isMyAddr = addr.getHostName().equals(NetUtils.getHostname().split("/")[1]) &&
                addr.getPort() == PortMap.worker().getPort("data");
        return isMyAddr;
    }
    public void connect(UnresolvedSocketAddr[] workerAddrs) {
        for (UnresolvedSocketAddr workerAddr:workerAddrs) {
            if (isMyAddress(workerAddr)) { continue; }
            ConnDesc connDesc = new ConnDesc();
            for (int i=0; i<ChannelMux.channelNum; i++) {
                try {
                    SocketChannel sendChannel = SocketChannel.open();
                    assert sendChannel.isBlocking();
                    sendChannel.connect(workerAddr.getInetSocketAddr());
                    boolean success=sendChannel.finishConnect();
                    if (!success) L.fatal("Fail to connect to:"+workerAddr);

                    connDesc.addSendChannel(sendChannel);
                } catch (IOException e) {
                    L.error("Cannot connect to "+ workerAddr);
                    L.fatal(ExceptionUtils.getStackTrace(e));
                }
            }
            ConnDesc prev = connMap.putIfAbsent(workerAddr, connDesc);
            if (prev != null) {
                for (SocketChannel ch:connDesc.getSendChannels()) {
                    prev.addSendChannel(ch);
                }
            }
        }
    }

    public void acceptConn(SelectionKey key) {
        assert key.channel().equals(workerAcceptChannel);

        SocketChannel recvChannel;
        try {
            recvChannel = ((ServerSocketChannel)key.channel()).accept();
            recvChannel.configureBlocking(false);
            recvChannel.register(connSelector, SelectionKey.OP_READ);
        } catch (Exception e) {
            L.fatal("acceptConn():" + ExceptionUtils.getStackTrace(e));
            return;
        }
    }

    public ChannelMux sendChannelMuxFor(UnresolvedSocketAddr workerAddr) {
        ConnDesc connDesc = connMap.get(workerAddr);
        if (connDesc == null) { return null; }

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
            L.fatal("Exception while sending table to "+getRemoteAddress(sendCh)+":"+e);
            L.fatal(ExceptionUtils.getStackTrace(e));
            throw new SociaLiteException(e);
        }
    }
    SocketAddress getRemoteAddress(SocketChannel ch) {
        try { return ch.getRemoteAddress(); }
        catch (IOException e) {
            L.fatal("Cannot get remote address:"+e);
            return null;
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
