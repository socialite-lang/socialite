package socialite.eval;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryNotificationInfo;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.util.List;
import java.util.Random;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.EvalRefCount;
import socialite.dist.worker.WorkerNode;
import socialite.engine.Config;
import socialite.resource.SRuntime;
import socialite.resource.VisitorBuilder;
import socialite.util.Assert;
import socialite.util.ByteBufferPool;

class LowMemoryDetector {
	static MemoryPoolMXBean findTenuredGenPool() {
		for (MemoryPoolMXBean pool: ManagementFactory.getMemoryPoolMXBeans()) {
			if (pool.getType() == MemoryType.HEAP && pool.isUsageThresholdSupported()) {
				return pool;
			}
		}
		throw new AssertionError("Could not find tenured space");
	}

	static void install() {
		/*MemoryPoolMXBean tenuredPool = findTenuredGenPool();
		long maxTenuredPool = tenuredPool.getUsage().getMax();
		long threshold = Math.max((long)(maxTenuredPool*0.9), maxTenuredPool-256*1024*1024);
		System.out.println("threshold:"+threshold+", maxTenuredPool:"+maxTenuredPool);
		tenuredPool.setCollectionUsageThreshold(threshold);

		MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
		NotificationEmitter emitter = (NotificationEmitter) mbean;
		emitter.addNotificationListener(new NotificationListener() {
				public void handleNotification(Notification n, Object hb) {
					if (n.getType().equals(MemoryNotificationInfo.MEMORY_COLLECTION_THRESHOLD_EXCEEDED)) {
						System.out.println("Memory threshold exceeded! free memory:"+SRuntime.freeMemory()/1024/1024+"MB");
					}
				}}, null, null);
				*/
	}
}
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
		//DeltaStepWindow.reset();
	}

	public static void shutdownAll() {
		if (theInstance != null) {
			theInstance.shutdown();
			theInstance = null;
		}
	}

	Config conf;
	volatile SRuntime runtime;
	TaskFactory taskFactory;
	Random rand = new Random();
	Worker[] workers;
	int urgentWorkers=0;
	Thread[] workerThreads;
	final boolean verbose = false;

	public Manager(Config _conf) {
		super("Manager");
		conf = _conf;
		initWorkers(conf.getWorkerThreadNum());
		taskFactory = new TaskFactory();
		LowMemoryDetector.install();
	}
	
	public int getWorkerNum() { return workers.length; }

	public void cleanup() {
		Manager.cleanupEpoch();		
	}
	
	public void setRuntime(SRuntime _runtime) {
		runtime = _runtime;
		for (int i=0; i<workers.length; i++) {
			workers[i].setRuntime(runtime);
		}
	}
	VisitorBuilder builder(int rule) {
		assert runtime!=null:"runtime is null";
		VisitorBuilder builder = runtime.getVisitorBuilder(rule);
        if (builder == null) {
            L.error("Builder for rule["+rule+"] is null");
        }
        return builder;
	}
	
	void initWorkers(int _workerNum) {
        assert _workerNum>0;
		if (workers == null) {
			if (_workerNum==1) urgentWorkers=0;
			else {
                urgentWorkers = _workerNum/3;
                if (urgentWorkers<2) urgentWorkers=2;
                if (urgentWorkers>12) urgentWorkers=12;
            }
			Worker.initTaskQueues(_workerNum + urgentWorkers);
			TmpTablePool.init(_workerNum+urgentWorkers);
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


    int rand(int upto) {
        assert upto>0;
        int randval = rand.nextInt();
        if (randval<0) {
        	randval = -randval;
        	if (randval == Integer.MIN_VALUE)
        		randval = 0;
        }
        return randval%upto;
    }
	void dispatch(Priority priority, Task[] tasks) {
		TaskQueue[] workerQueues = Worker.getWorkerQueues();

        int maxWorkerIdx;
        if (priority==Priority.Top) {
            maxWorkerIdx = workers.length;
        } else {
            maxWorkerIdx = workers.length-urgentWorkers;
        }
        int workerIdx = rand(maxWorkerIdx);

		for (int i=0; i<tasks.length; i++) {
			Task t = tasks[i];if (t==null) continue;

			TaskQueue q = workerQueues[workers[workerIdx].id()];
            q.add(priority, t);
            workerIdx = (workerIdx+1)%maxWorkerIdx;
		}
	}

    public void addEvalCmd(EvalCommand evalCmd) throws InterruptedException {
        SRuntime _runtime = runtime;
        EvalRefCount.getInst().waitUntilReady(evalCmd.getEpochId());
        Task[] tasks=null;
        VisitorBuilder builder = builder(evalCmd.getRuleId());
        if (builder == null) {
            do {
                L.error("Builder for rule["+evalCmd.getRuleId()+"] is null, epochId:"+evalCmd.getEpochId()+", try waiting once more");
                EvalRefCount.getInst().waitUntilReady(evalCmd.getEpochId());
                builder = builder(evalCmd.getRuleId());
            } while (builder == null);
        }
        tasks = taskFactory.make(evalCmd, builder, _runtime);
        int taskCount=0;
        for (Task t:tasks) {
            if (t==null) continue;
            taskCount++;
        }
        EvalRefCount.getInst().incBy(evalCmd.getEpochId(), taskCount);
        if (evalCmd.isReceived()) dispatch(Priority.Top, tasks);
        else dispatch(Priority.Normal, tasks);
        EvalRefCount.getInst().decBy(evalCmd.getEpochId(), taskCount);
    }
    public void addLoadCmd(ConcurrentLoadCommand loadCmd) throws InterruptedException {
        SRuntime _runtime = runtime;
        Task[] tasks=null;
        tasks = taskFactory.make(loadCmd, _runtime);
        if (loadCmd.isReceived()) dispatch(Priority.Top, tasks);
        else dispatch(Priority.Normal, tasks);
    }
    public void addCmd(Command cmd) throws InterruptedException {
        if (cmd instanceof EvalCommand) {
            addEvalCmd((EvalCommand) cmd);
        } else if (cmd instanceof ConcurrentLoadCommand) {
            addLoadCmd((ConcurrentLoadCommand) cmd);
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
		Worker.haltEpoch();
		L.warn("While executing \""+t+"\",");
		L.warn("  an error was thrown:"+e);		
		L.warn("  Discarding all the remaining tasks.");
	}
	
	
	public void run() {
		while (true) {	
			try {
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
