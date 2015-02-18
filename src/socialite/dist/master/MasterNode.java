package socialite.dist.master;

import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.util.*;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.ipc.RPC;

import socialite.dist.Host;
import socialite.dist.worker.WorkerCmd;
import socialite.engine.Config;
import socialite.resource.SRuntimeMaster;
import socialite.resource.WorkerAddrMap;
import socialite.resource.SRuntime;
import socialite.resource.WorkerAddrMapW;
import socialite.util.SociaLiteException;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
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
	public static MasterNode getInstance() {
		if (theInstance==null) 
			theInstance = new MasterNode(Config.dist());
		return theInstance;
	}

    static ArrayBlockingQueue<Call> cmdQueue;
    static {
        int workerNum = Config.getWorkerNodeNum();
        cmdQueue = new ArrayBlockingQueue<Call>(workerNum*256);
        int cmdThreadNum = workerNum;
        if (cmdThreadNum>32) cmdThreadNum = 32;
        for (int i=0; i<cmdThreadNum; i++) {
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
	Map<InetAddress, WorkerCmd> workerMap;
    Set<InetAddress> workersToRegister;
	
	private MasterNode(Config _conf) {
		conf=_conf;
		workerMap=Collections.synchronizedMap(new LinkedHashMap<InetAddress, WorkerCmd>());
        workersToRegister = Collections.synchronizedSet(Host.getAddrs(Config.getWorkers()));
        monitorWorkerRegistration();
	}
			
	public void serve() {
		initWorkerReqListener();
		initQueryListener();
	}

    void monitorWorkerRegistration() {
        class MonitorRegistration implements Runnable {
            public void run() {
                long wait = 5*1000;
                long maxWait = 60*1000;
                int maxTry=20;
                for (int _try=0; _try<maxTry; _try++) {
                    try { Thread.sleep(wait); }
                    catch (InterruptedException e) { break; }
                    if (workersToRegister.isEmpty()) { break; }
                    else {
                        InetAddress[] workers = workersToRegister.toArray(new InetAddress[]{});
                        String msg = "Waiting for "+workers.length+" worker(s):";
                        for (InetAddress a:workers) {
                            msg += " "+a;
                        }
                        L.info(msg);

                        if (wait<maxWait) wait=wait*2;
                    }
                }
                if (!workersToRegister.isEmpty()) {
                    L.warn("Stopped monitoring worker registrations.");
                }
            }
        }
        Thread monitor = new Thread(new MonitorRegistration());
        monitor.start();
    }
    public synchronized void addRegisteredWorker(InetAddress workerAddr) {
        workersToRegister.remove(workerAddr);
        if (!workersToRegister.isEmpty())
            return;

        // We are in the middle of worker registration.
        // This is asynchronous, so that the registration can immediately terminate.
        new Thread(new Runnable() {
            public void run() {
                // this needs to be asynchronous
                queryListener.init();
                SRuntimeMaster runtime = SRuntimeMaster.getInst();
                WorkerAddrMap addrMap = runtime.getWorkerAddrMap();
                try {
                    Method init = WorkerCmd.class.getMethod("init", new Class[]{WorkerAddrMapW.class});
                    MasterNode.callWorkers(init, new Object[]{new WorkerAddrMapW(addrMap)});
                    L.info("All workers registered. Ready to run queries.");
                } catch (InterruptedException e) {
                } catch (Exception e) {
                    L.fatal("Exception while running WorkerCmd.init():" + ExceptionUtils.getStackTrace(e));
                }
            }
        }).start();
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
	public WorkerCmd getWorkerCmd(String _addr) {
		InetAddress workerAddr=null;
		try { workerAddr = InetAddress.getByName(_addr);
		} catch (UnknownHostException e) {
			L.fatal("Invalid address["+_addr+"]:"+e);
			throw new SociaLiteException(e);
		}
		return workerMap.get(workerAddr);
	}
	public Set<InetAddress> getWorkerAddrs() {
		return workerMap.keySet();
	}
	public boolean addWorkerAddr(String _addr) {
		InetAddress workerAddr=null;
		try { workerAddr = InetAddress.getByName(_addr);
		} catch (UnknownHostException e) {
			L.fatal("Invalid address["+_addr+"]:"+e);
			throw new SociaLiteException(e);
		}
		
		if (workerMap.containsKey(workerAddr)) 
			return false;
		
		Configuration hConf=new Configuration();
		String workerIP=workerAddr.getHostAddress();
		int workerCmdPort=conf.portMap().workerCmdListen();
		InetSocketAddress addr=new InetSocketAddress(workerIP, workerCmdPort);
		WorkerCmd workerCmd;
		try {
			workerCmd = (WorkerCmd)RPC.waitForProxy(WorkerCmd.class, WorkerCmd.versionID, addr, hConf);
			workerMap.put(workerAddr, workerCmd);
		} catch(IOException e) {
			L.fatal("Cannot connect to worker:");
			L.fatal(ExceptionUtils.getStackTrace(e));
			return false;
		}
		return true;		
	}

	public Map<InetAddress, WorkerCmd> getWorkerCmdMap() {
		return workerMap;
	}
	public WorkerAddrMap makeWorkerAddrMap() {
		WorkerAddrMap machineMap=new WorkerAddrMap();
		Set<InetAddress> workerAddrs=workerMap.keySet();
		int workerNodeNum = workerAddrs.size();
		int addedWorker=0;
		
		for (InetAddress addr:workerAddrs) {
			machineMap.add(addr);
			addedWorker++;
			if (addedWorker >= workerNodeNum)
				break;
		}
		return machineMap;
	}
	
	static void startMasterNode() {
		MasterNode master = MasterNode.getInstance();		
		master.serve();
		L.info("Master started");
	}
	
	public static void main(String[] args) {
		startMasterNode();
	}
}
