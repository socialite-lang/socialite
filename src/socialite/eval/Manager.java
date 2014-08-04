package socialite.eval;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.worker.WorkerNode;
import socialite.engine.Config;
import socialite.eval.EvalCommand;
import socialite.resource.RuleMap;
import socialite.resource.SRuntime;
import socialite.resource.Sender;
import socialite.resource.VisitorBuilder;
import socialite.tables.TableInst;
import socialite.util.Assert;
import socialite.util.ByteBufferPool;
import socialite.util.FastQueue;
import socialite.util.SociaLiteException;
import socialite.visitors.IVisitor;
import gnu.trove.TIntCollection;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.map.hash.TIntObjectHashMap;

public class Manager extends Thread {
	public static final Log L=LogFactory.getLog(Manager.class);
	
	static Manager theInstance = null;
	
	public synchronized static Manager getInst() {
		return theInstance;
	}
	public synchronized static Manager getInst(Config conf) {
		assert theInstance==null;
		theInstance = new Manager(conf);
		return theInstance;
	}
	
	public static void cleanupEpoch() {		
		DeltaStepWindow.reset();
		ByteBufferPool.reset();
		TmpTablePool.freeAll();
	}

	public static void shutdownAll() {
		if (theInstance != null) {
			theInstance.shutdown();
			theInstance = null;
		}
	}

	Config conf;
	SRuntime runtime;
	TaskFactory taskFactory;
	Random rand = new Random();
	Worker[] workers;
	int urgentWorkers=0;
	Thread[] workerThreads;
	final boolean verbose = false;
	
	volatile boolean evalInitDone = false;

	public Manager(Config _conf) {
		super("Manager");
		conf = _conf;
		initWorkers(conf.getWorkerNum());
		taskFactory = new TaskFactory();
	}
	
	public int getWorkerNum() { return workers.length; }

	public void cleanup() {
		resetEvalInitDone();
		Manager.cleanupEpoch();		
	}
	
	public void updateRuntime(SRuntime _runtime) {
		runtime = _runtime;
		for (int i=0; i<workers.length; i++) {
			workers[i].updateRuntime(runtime);
		}
		synchronized (addCmdLock) {}
	}
	VisitorBuilder builder(int rule) {
		assert runtime!=null:"runtime is null";
		return runtime.getVisitorBuilder(rule);
	}
	
	void initWorkers(int _workerNum) {
		if (workers == null) {
			urgentWorkers = _workerNum/2;
			if (_workerNum==1) urgentWorkers=0;
			else if (urgentWorkers<2) 
				urgentWorkers=2;
			
			Worker.initLocalQueues(_workerNum+urgentWorkers);
			TmpTablePool.init(_workerNum+urgentWorkers);
			TmpTablePool.setRealWorkerNum(_workerNum);
			workers = new Worker[_workerNum+urgentWorkers];
			workerThreads = new Thread[_workerNum+urgentWorkers];
			for (int i=0; i<workers.length; i++) {
				Thread t;
				if (i>=workers.length-urgentWorkers) {
					workers[i] = new WorkerForUrgentTask(i);
					t=new Thread(workers[i], "UrgentWorker #"+i);
				} else {
					workers[i] = new Worker(i);
					t=new Thread(workers[i], "Worker #"+i);
				}				
				workerThreads[i] = t;
				t.start();
			}
		} else assert workers.length == _workerNum;
	}
	
	long prevFreeMemory = 0;
	void printMemoryStat() {
		if (!verbose) return;
		Runtime rt = Runtime.getRuntime();
		long freeMem = rt.freeMemory() / 1024 / 1024;
		if (freeMem == prevFreeMemory) return;
		
		prevFreeMemory = freeMem;
		if (freeMem < 500) {
			L.info("[Manager] free mem:"+(freeMem/1024/1024)+"M");
		}
	}
	
	public boolean likelyIdle() {
		return Worker.likelyIdle();
	}	
	public boolean idle() {
		synchronized(addCmdLock) {
			return Worker.idle();
		}
	}

	int pickWorker() {
		int randval = rand.nextInt();
		if (randval<0) randval = -randval;
		randval = randval%workers.length;
		return randval;
	}
	void dispatch(int priority, Task[] tasks) {
		int workerIdx = pickWorker();
		LocalQueue[] workerQueues = Worker.getWorkerQueues();
		for (int i=0; i<tasks.length; i++) {
			Task t = tasks[i];
			if (t==null) continue;
			
			if (priority==-1) {
				workerIdx = (workerIdx+1)%workers.length;	
			} else {
				workerIdx = (workerIdx+1)%(workers.length-urgentWorkers);
			}
			
			LocalQueue q = workerQueues[workers[workerIdx].id()];
			assert q != null;
			q.add(priority, t);
		}
	}
	
	Object addCmdLock = new Object();
	public Object addCmdLock() { return addCmdLock; }	
	public void addCmd(Command cmd) {
		SRuntime _runtime = runtime;
		if (_runtime==null) return;
		
		waitForEvalInit();		
		assert cmd instanceof EvalCommand;
		if (cmd instanceof EvalCommand) {
			EvalCommand eval = (EvalCommand)cmd;				
			Task[] tasks=null;				
			tasks = taskFactory.make(eval, builder(eval.getRuleId()), _runtime);
			
			if (cmd.isReceived()) dispatch(-1, tasks);
			else dispatch(1, tasks);			
		} else { Assert.die("Unsupported command:" + cmd); }	
	}	
	
	public void setEvalInitDone() {	
		synchronized (addCmdLock) {
			evalInitDone=true;
			addCmdLock.notifyAll();
		}
	}
	public void resetEvalInitDone() {
		synchronized (addCmdLock) {
			evalInitDone=false;	
		}		 
	}
	public void waitForEvalInit() {
		synchronized (addCmdLock) {
			if (!evalInitDone) {
				try { addCmdLock.wait(); } 
				catch (InterruptedException e) { 
					throw new SociaLiteException(e); 
				}
			}
		}
	}

	void shutdownWorkers() {
		for (int i=0; i<workerThreads.length; i++) {
			workerThreads[i].interrupt();
		}
		for (int i=0; i<workerThreads.length; i++) {
			try { workerThreads[i].join();
			} catch (InterruptedException e) {
				Assert.die("Manager interrupted while shutting down");
			}
		}
		workerThreads = null;
	}
	public void shutdown() {
		//pool.shutdownNow();
		shutdownWorkers();
		interrupt();
	}

	public synchronized void timesUp() {
		LocalQueue[] workerQueues = Worker.getWorkerQueues();
		for (int i=0; i<workerQueues.length; i++) {
			workerQueues[i].empty();
		}
	}
		
	public void handleError(Task t, Throwable e) {
		if (conf.isDistributed())
			handleErrorDist(t, e);
		else handleErrorLocal(t, e);
	}
	void handleErrorDist(Task t, Throwable e) {
		Worker.haltEpoch();
		WorkerNode.getInst().reportError(t.getRuleId(), e);
	}
	void handleErrorLocal(Task t, Throwable e) {
		runtime.setException(e);
		Worker.haltEpoch();
		L.warn("While executing \""+t+"\",");
		L.warn("  an error was thrown:"+e);		
		L.warn("  Discarding all the remaining tasks.");
	}
	public void run() {
		while (true) {	
			try {
				// In distributed setting, we should report heartbeat to Master node.
				
				Thread.sleep(200);
				/*TmpTablePool.status();
				Sender.sendQ().status();
				System.out.println(" NOTICE SRuntime.freeMemory:"+SRuntime.freeMemory()/1024/1024+"MB");
				printGcInfo();*/				
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	void printGcInfo() {
		long gcTime=0;
		List<GarbageCollectorMXBean> gcs = ManagementFactory.getGarbageCollectorMXBeans();
		for (GarbageCollectorMXBean gc:gcs) {
			gcTime += gc.getCollectionTime();
		}
		System.out.println(" NOTICE gc time:"+gcTime/1000.0+"s");
	}
}
