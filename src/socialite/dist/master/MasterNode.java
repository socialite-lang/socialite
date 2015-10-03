package socialite.dist.master;

import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.util.*;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.RPC;

import org.apache.hadoop.net.NetUtils;
import socialite.dist.worker.WorkerCmd;
import socialite.engine.Config;
import socialite.resource.SRuntimeMaster;
import socialite.resource.WorkerAddrMap;
import socialite.resource.WorkerAddrMapW;
import socialite.util.SociaLiteException;
import socialite.util.TextArrayWritable;
import socialite.util.UnresolvedSocketAddr;
import socialite.yarn.ClusterConf;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

class Call {
    public Method m;
    public Object obj;
    public Object[] params;
    public ReturnHandler handler;
    Call(Method _m, Object _obj, Object[] _params, ReturnHandler _handler) {
        m = _m;
        obj = _obj;
        params = _params;
        handler = _handler;
    }
    public void call() {
        try {
            Object ret = m.invoke(obj, params);
            handler.addReturn(ret);
        } catch (Exception e) {
            handler.except(e);
        }
    }
}
class ReturnHandler {
    private ArrayList<Object> retvals;
    private String errorMsg;
    AtomicInteger invokeCountDown;

    ReturnHandler(int callCount) {
        retvals = new ArrayList<Object>(callCount);
        invokeCountDown = new AtomicInteger(callCount);
    }

    public synchronized void except(Throwable t) {
        countInvocation();
        if (errorMsg==null) errorMsg = "";
        errorMsg += ExceptionUtils.getStackTrace(t);

    }
    void countInvocation() {
        int count = invokeCountDown.decrementAndGet();
        if (count==0) { notify(); }
    }
    public synchronized void waitForDone() throws InterruptedException {
        wait();
    }

    public synchronized void addReturn(Object val) {
        countInvocation();

        if (val!=null) retvals.add(val);
    }
    public synchronized Object[] getReturns() { return retvals.toArray(); }
    public synchronized boolean hasError() { return errorMsg!=null; }
    public synchronized String getErrorMsg() { return errorMsg; }
}

class ParallelRPC implements Runnable {
    ArrayBlockingQueue<Call> queue;
    public ParallelRPC(ArrayBlockingQueue<Call> _queue) {
        queue = _queue;
    }
    public void run()  {
        while (true) {
            Call call;
            try { call = queue.take(); }
            catch (InterruptedException e) { break; }
            call.call();
        }
    }
}

public class MasterNode {
    public static final Log L=LogFactory.getLog(MasterNode.class);

    static MasterNode theInstance=null;
    public static MasterNode create() {
        if (theInstance != null) {
            throw new AssertionError("MasterNode is already created");
        }
        theInstance = new MasterNode(Config.dist());
        return theInstance;
    }
    public static MasterNode getInstance() { return theInstance; }

    static ArrayBlockingQueue<Call> cmdQueue;
    static {
        int cmdIssueThreadNum = ClusterConf.get().getNumWorkers()/4;
        cmdIssueThreadNum = Math.max(cmdIssueThreadNum, 2);
        cmdIssueThreadNum = Math.min(cmdIssueThreadNum, 8);
        cmdQueue = new ArrayBlockingQueue<Call>(cmdIssueThreadNum * 128);
        for (int i = 0; i< cmdIssueThreadNum; i++) {
            Thread t = new Thread(new ParallelRPC(cmdQueue), "RPC Thread #"+i);
            t.start();
        }
    }

    public static Object[] callWorkers(Method m, Object[][] params) throws InterruptedException {
        Collection<WorkerCmd> workerCmds = getInstance().getWorkerCmdMap().values();
        ReturnHandler handler = new ReturnHandler(workerCmds.size());
        int i=0;
        for (WorkerCmd cmd:workerCmds) {
            Object[] param = params[i++];
            Call call = new Call(m, cmd, param, handler);
            cmdQueue.put(call);
        }

        handler.waitForDone();
        if (handler.hasError()) {
            throw new SociaLiteException(handler.getErrorMsg());
        }
        return handler.getReturns();
    }
    public static Object[] callWorkers(Method m, Object[] param, boolean async) throws InterruptedException {
        Collection<WorkerCmd> workerCmds = getInstance().getWorkerCmdMap().values();
        ReturnHandler handler = new ReturnHandler(workerCmds.size());
        for (WorkerCmd cmd:workerCmds) {
            Call call = new Call(m, cmd, param, handler);
            cmdQueue.put(call);
        }
        if (async) return null;

        handler.waitForDone();
        if (handler.hasError()) {
            throw new SociaLiteException(handler.getErrorMsg());
        }
        return handler.getReturns();
    }
    public static void callWorkersAsync(Method m, Object[] param) throws InterruptedException {
        callWorkers(m, param, true);
    }
    public static Object[] callWorkers(Method m, Object[] param) throws InterruptedException {
        return callWorkers(m, param, false);
    }

    Config conf; // master node conf
    Config workerConf; // worker node may have different config (e.g. cpu #)
    QueryListener queryListener;
    WorkerReqListener workerListener;
    ConcurrentMap<UnresolvedSocketAddr, WorkerCmd> workerMap;
    ConcurrentMap<UnresolvedSocketAddr, UnresolvedSocketAddr> workerDataAddrMap; // {cmd-address: data-address}
    int expectedWorkerNum = ClusterConf.get().getNumWorkers();

    private MasterNode(Config _conf) {
        conf = _conf;
        workerMap = new ConcurrentHashMap<UnresolvedSocketAddr, WorkerCmd>();
        workerDataAddrMap = new ConcurrentHashMap<UnresolvedSocketAddr, UnresolvedSocketAddr>();
    }

    public void serve() {
        initWorkerReqListener();
        initQueryListener();
    }

    WorkerCmd createWorkerCmd(UnresolvedSocketAddr workerCmdAddr) {
        if (workerMap.containsKey(workerCmdAddr)) {
            L.warn("createWorkerCmd(): Already existing worker cmd:" + workerCmdAddr);
            return workerMap.get(workerCmdAddr);
        }

        Configuration conf = new Configuration();
        String workerIP = workerCmdAddr.getHostName();
        int cmdPort = workerCmdAddr.getPort();
        InetSocketAddress sockaddr = new InetSocketAddress(workerIP, cmdPort);
        try {
            WorkerCmd workerCmd = RPC.waitForProxy(WorkerCmd.class, WorkerCmd.versionID, sockaddr, conf);
            workerMap.put(workerCmdAddr, workerCmd);
            return workerCmd;
        } catch(IOException e) {
            L.fatal("Cannot connect to worker:");
            L.fatal(ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    void makeWorkerConnections() {
        Collection<UnresolvedSocketAddr> otherAddrs = workerDataAddrMap.values();
        Text[] otherAddrTexts=new Text[otherAddrs.size()];
        int i=0;
        for (UnresolvedSocketAddr sockaddr:otherAddrs) {
            otherAddrTexts[i++]=new Text(sockaddr.getHostName()+":"+sockaddr.getPort());
        }
        TextArrayWritable restAddrs = new TextArrayWritable(otherAddrTexts);
        try {
            Method makeConnections = WorkerCmd.class.getMethod("makeConnections", new Class[]{ArrayWritable.class});
            MasterNode.callWorkers(makeConnections, new Object[]{restAddrs});
        } catch (Exception e) {
            L.fatal("makeWorkerConnections():" + ExceptionUtils.getStackTrace(e));
        }

    }
    public synchronized void registerWorker(String addr, int cmdPort, int dataPort) {
        UnresolvedSocketAddr workerAddr = new UnresolvedSocketAddr(addr, cmdPort);
        createWorkerCmd(workerAddr);
        workerDataAddrMap.put(workerAddr, new UnresolvedSocketAddr(addr, dataPort));
        if (workerMap.size() < expectedWorkerNum) { return; }

        queryListener.init();
        makeWorkerConnections();
        SRuntimeMaster runtime = SRuntimeMaster.getInst();
        WorkerAddrMap addrMap = runtime.getWorkerAddrMap();
        try {
            Method init = WorkerCmd.class.getMethod("init", new Class[]{WorkerAddrMapW.class});
            MasterNode.callWorkers(init, new Object[]{new WorkerAddrMapW(addrMap)});
        } catch (InterruptedException e) {
        } catch (Exception e) {
            L.fatal("Exception while running WorkerCmd.init():" + ExceptionUtils.getStackTrace(e));
        }
    }

    void initWorkerReqListener() {
        workerListener = new WorkerReqListener(conf, this);
        workerListener.start();
    }

    void initQueryListener() {
        queryListener =new QueryListener(conf, this);
        queryListener.start();
    }

    public Config getWorkerConf() {
        return workerConf;
    }
    public void setWorkerConf(Config _conf) {
        assert workerConf == null;
        workerConf = _conf;
    }

    public Map<UnresolvedSocketAddr, WorkerCmd> getWorkerCmdMap() {
        return workerMap;
    }
    public WorkerAddrMap makeWorkerAddrMap() {
        WorkerAddrMap machineMap=new WorkerAddrMap();
        Set<UnresolvedSocketAddr> workerAddrs=workerMap.keySet();
        int workerNodeNum = workerAddrs.size();
        int addedWorker=0;

        for (UnresolvedSocketAddr addr:workerAddrs) {
            machineMap.add(addr, workerDataAddrMap.get(addr));
            addedWorker++;
            if (addedWorker >= workerNodeNum)
                break;
        }
        return machineMap;
    }

    public static void startMasterNode() {
        MasterNode master = MasterNode.create();
        master.serve();
        L.info("Master started");
    }

    public static void main(String[] args) {
        startMasterNode();
    }
}
