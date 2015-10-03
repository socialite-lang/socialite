package socialite.dist.worker;

import java.net.*;
import java.util.*;
import java.io.*;
import java.nio.channels.*;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.RPC;
import org.apache.commons.lang3.exception.ExceptionUtils;

import org.apache.hadoop.net.NetUtils;
import socialite.dist.PortMap;
import socialite.dist.master.WorkerRequest;
import socialite.engine.Config;
import socialite.eval.Manager;
import socialite.resource.SRuntime;
import socialite.resource.SRuntimeWorker;
import socialite.util.ByteBufferPool;
import socialite.util.FastQueue;
import socialite.util.UnresolvedSocketAddr;
import socialite.yarn.ClusterConf;

public class WorkerNode extends Thread {
    public static final Log L=LogFactory.getLog(WorkerNode.class);

    volatile Config conf;
    AtomicBoolean isReady = new AtomicBoolean(false);
    WorkerConnPool connPool;
    CmdListener cmdListener;
    WorkerRequest request;
    Manager manager;
    Thread[] recvThreads;
    FastQueue<RecvTask> recvQ;
    public WorkerNode() {
        //super("WorkerNode Thread");
        connPool = new WorkerConnPool();
    }

    public Config getConf() { return conf; }

    public void serve() {
        initManagerAndWorkers();
        initCmdListener();
        initNetworkResources();
        initRecvThread();
        startListen();
        register();
        try { join(); }
        catch (InterruptedException e) {
            L.info("Terminating WorkerNode:" + e);
        }
    }

    void initNetworkResources() {
        ByteBufferPool.get();
    }
    void initCmdListener() {
        cmdListener = new CmdListener(this);
        cmdListener.start();
    }
    public void initManagerAndWorkers() {
        conf = Config.dist(ClusterConf.get().getNumWorkerThreads());
        manager = Manager.create(conf);
    }
    void initRecvThread() {
        recvQ = Receiver.recvq();
        int recvNum = (ClusterConf.get().getNumWorkerThreads() + 1) / 2;
        if (recvNum < 4) recvNum = 4;
        if (recvNum > 64) recvNum = 64;

        recvThreads = new Thread[recvNum];
        for (int i=0; i<recvThreads.length; i++) {
            Receiver recv=new Receiver(recvQ, connPool, manager, cmdListener);
            recvThreads[i] = new Thread(recv, "Receiver #"+i);
            recvThreads[i].start();
        }
    }
    public boolean isReady() { return isReady.get(); }
    public FastQueue recvQ() { return recvQ; }

    void startListen() {
        start();
    }

    public WorkerConnPool getConnPool() { return connPool; }

    public void run() {
        try {
            while (true) {
                Set<SelectionKey> selectedKeys = connPool.select(5);

                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    if (!key.isValid()) {
                        L.warn("Invalid key:"+key);
                        continue;
                    }

                    if (key.isAcceptable()) {
                        connPool.acceptConn(key);
                    } else if (key.isReadable()) {
                        SocketChannel selectedChannel = (SocketChannel) (key.channel());
                        InetAddress nodeAddr = (selectedChannel.socket()).getInetAddress();

                        connPool.cancelKey(selectedChannel);
                        try {
                            selectedChannel.configureBlocking(true);
                        } catch (IOException e) {
                            L.error("Error while configure blocking:"+e);
                            L.fatal(ExceptionUtils.getStackTrace(e));
                            continue;
                        }

                        RecvTask recv = new RecvTask(nodeAddr, selectedChannel);
                        recvQ.add(recv);
                    } else {
                        L.error("Unexpected key operation(!acceptable, !readable):"+key);
                    }
                } // while selectedKeys
                connPool.registerCanceledConn();
            }
        } catch (Exception e) {
            L.fatal("Error while select() operation:"+e);
            L.fatal(ExceptionUtils.getStackTrace(e));
        } finally {
            L.info("WorkerNode terminating");
        }
    }

    void register() {
        isReady.set(true);

        Configuration hConf=new Configuration();
        String masterAddr=PortMap.worker().masterAddr();
        int reqPort=PortMap.worker().getPort("workerReq");
        InetSocketAddress addr = new InetSocketAddress(masterAddr, reqPort);
        try {
            request = RPC.waitForProxy(WorkerRequest.class,
                    WorkerRequest.versionID,
                    addr, hConf);
        } catch (IOException e) {
            L.fatal("Cannot connect to master:"+e);
            L.fatal(ExceptionUtils.getStackTrace(e));
        }
        String host = NetUtils.getHostname().split("/")[1];
        int cmdPort = PortMap.worker().getPort("workerCmd");
        int dataPort = PortMap.worker().getPort("data");
        request.register(host, cmdPort, dataPort);
        synchronized(this) { /* make conf visible */}
        assert conf!=null;
    }

    public void reportError(int ruleid, Throwable t) {
        L.warn("Worker node error:"+ExceptionUtils.getStackTrace(t));

        SRuntime runtime=SRuntimeWorker.getInst();
        if (runtime==null) {
            L.error("reportError(): Worker runtime is null.");
            return;
        }
        String msg="";
        if (t!=null) msg += t.getClass().getSimpleName()+" ";
        if (t!=null && t.getMessage()!=null) msg = t.getMessage();
        IntWritable workerid = new IntWritable(runtime.getWorkerAddrMap().myIndex());
        request.handleError(workerid, new IntWritable(ruleid), new Text(msg));
    }

    public void reportIdle(int epochId, int ts) {
        SRuntime runtime = SRuntimeWorker.getInst();
        int id=runtime.getWorkerAddrMap().myIndex();
        request.reportIdle(new IntWritable(epochId), new IntWritable(id), new IntWritable(ts));
    }

    public boolean connect(String[] workerAddrs) {
        UnresolvedSocketAddr[] addrs = new UnresolvedSocketAddr[workerAddrs.length];
        for (int i=0; i<workerAddrs.length; i++) {
            String host = workerAddrs[i].split(":")[0];
            int port = Integer.parseInt(workerAddrs[i].split(":")[1]);
            addrs[i] = new UnresolvedSocketAddr(host, port);
        }
        connPool.connect(addrs);
        return true;
    }

    static WorkerNode theWorkerNode;
    public static WorkerNode getInst() {
        return theWorkerNode;
    }
    static void startWorkerNode() {
        theWorkerNode = new WorkerNode();
        try {
            theWorkerNode.serve();
        } catch (Exception e) {
            L.error("Exception while runining worker node:"+ExceptionUtils.getStackTrace(e));
        }
    }
    public static void main(String[] args) {
        startWorkerNode();
    }
}
