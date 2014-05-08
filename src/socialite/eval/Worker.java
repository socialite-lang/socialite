package socialite.eval;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.worker.Receiver;
import socialite.dist.worker.WorkerNode;
import socialite.resource.RuleMap;
import socialite.resource.SRuntime;
import socialite.resource.Sender;
import socialite.resource.VisitorBuilder;
import socialite.tables.TableInst;
import socialite.util.Assert;
import socialite.util.FastQueue;
import socialite.util.SocialiteFinishEval;
import gnu.trove.TIntCollection;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TIntObjectHashMap;

public class Worker implements Runnable {
	public static final Log L=LogFactory.getLog(Worker.class);
	static final ThreadLocal<Integer> currentRuleId = new ThreadLocal<Integer>();
	public static int getCurrentRuleId() { return currentRuleId.get(); }
	
	public static List<String> log=Collections.synchronizedList(new ArrayList<String>());
	
	public static final boolean verbose=false;	
	static { if (verbose) L.warn("Turn off the verbose flag in Worker.java"); }

	static Object stealingLock=new Object();
	static LocalQueue[] workerQueues;
	public static void initLocalQueues(int workerNum) {
		workerQueues = new LocalQueue[workerNum];
		for (int i=0; i<workerNum; i++) {
			workerQueues[i] = new LocalQueue(i);
		}
	}
	public static LocalQueue[] getWorkerQueues() {
		assert workerQueues!=null;
		return workerQueues;
	}
	static volatile boolean halted=false;
	public static void setHalted(boolean _halted) { halted = _halted; }
	public static boolean isHalted() { return halted; }
	
	public static void haltEpoch() {
		setHalted(true);
		Worker.emptyQueues();
		if (Sender.sendQ()!=null) 
		    Sender.sendQ().empty();
		if (Receiver.recvQ()!=null) 
		    Receiver.recvQ().empty();
	}
	static void emptyQueues() {
		synchronized(stealingLock) {
			for (LocalQueue q:getWorkerQueues()) {
				q.empty();
			}
		}
	}
	
	public static int getWorkerNum() { return workerQueues.length; }
	
	public static boolean fullyWorking() {
		if (workerQueues.length==1) return false;
		
		for (int i=0; i<workerQueues.length; i++) {
			if (workerQueues[i].isLikelyEmpty())
				return false;
		}
		return true;
	}
	public static boolean likelyIdle() {
		for (int i=0; i<workerQueues.length; i++) {
			if (!workerQueues[i].isLikelyEmpty())
				return false;
		}
		return true;
	}
	public static boolean idle() {
		synchronized(stealingLock) {
			for (int i=0; i<workerQueues.length; i++) {
				if (!(workerQueues[i].isLikelyEmpty() &&
						workerQueues[i].isEmpty()))
					return false;
			}
			return true;
		}
	}
	public static boolean isRunningAny(int[] rules) {
		for (int i=0; i<workerQueues.length; i++) {
			if (workerQueues[i].containsAny(rules))
				return true;
		}
		synchronized(stealingLock) {
			for (int i=0; i<workerQueues.length; i++) {
				if (workerQueues[i].containsAny(rules))
					return true;
			}
			return false;
		}
	}
	static Random r = new Random();
	static int pickWorkerToSteal(int workerid) {
		assert workerid >=0;
		int workerNum = workerQueues.length;
		assert workerNum > 1;
		int rand=r.nextInt();
		if (rand<0) {
			if (rand == Integer.MIN_VALUE) rand++;
			rand = -rand;
		}
		rand = rand%(workerNum-1);
		if (workerid <= rand) rand++;
		
		assert rand >=0 && rand < workerNum;
		return rand;
	}
	public static void logToFile() { logToFile("logs/sched.log"); }
	static void logToFile(String filename) {
		try {
			synchronized(log) {
				if (log.isEmpty()) return;
				FileOutputStream out = new FileOutputStream(filename);
				PrintStream p = new PrintStream(out);
				for (String l:log) 
					p.println(l);
				p.close();
				log.clear();
			}
		} catch (FileNotFoundException e) { 
			L.warn("Cannot create file:"+filename+", "+e.getMessage());
		}		
	}
	
	int id=0;
	TaskFactory factory;
	LocalQueue localQ;
	SRuntime runtime;
	
	Worker() { }
	Worker(int _id) {
		id = _id;	
		localQ = getWorkerQueues()[id];
		factory = new TaskFactory();
	}
	
	public int id() { return id; }
	
	void updateRuntime(SRuntime _runtime) { runtime = _runtime; }	
	RuleMap ruleMap(int rule) { return runtime.getRuleMap(rule); }
	VisitorBuilder builder(int rule) { return runtime.getVisitorBuilder(rule); }
	
	public LocalQueue getQueue() { return localQ; }
	
	boolean nothingToStealAt(int level) {
		for (int i=0; i<workerQueues.length; i++) {
			LocalQueue q = workerQueues[i];
			if (!q.likelyEmptyAt(level))
				return false;
		}
		return true;
	}
	
	Task tryStealTask(int level) {
		if (workerQueues.length<=1) return null;
		
		Task t=null;		
		LocalQueue myQueue = workerQueues[id];
		int workeridToSteal = pickWorkerToSteal(id);
		int tryCount=0;
		int workerNum = workerQueues.length;		
		
		while (true) {
			LocalQueue q = workerQueues[workeridToSteal];
			if (!q.likelyEmptyAt(level)) {			
				synchronized(stealingLock) {
					boolean success = q.steal(myQueue, level);
					if (success) {
						t = localQ.reserveQuick(level);
						assert t!=null;
						return t;
					}
				}
			}
			tryCount++;
			if (tryCount == workerNum-1) return null;
			
			workeridToSteal = (workeridToSteal+1)%workerNum;
			if (workeridToSteal==id)
				workeridToSteal = (workeridToSteal+1)%workerNum;
		}
	}
	Task reserveTask(int level) {
		Task task = localQ.reserveQuick(level);
		if (task!=null) return task;
		
		task = tryStealTask(level);
		if (task!=null) return task;
		return null;
	}
	Task reserveTask() throws InterruptedException {
		Task task=null;
		while (true) {			
			for (int i=0; i<localQ.maxLevel(); i++) {
				task = reserveTask(i);
				if (task!=null) {
					if (i>0) localQ.rotate();
					return task;
				}
			}			
			Thread.sleep(5);			
		}
	}
	void discardTasks() {
		localQ.empty();
	}
	 
	public void addTasksForPriv(int ruleid, TableInst[] privT) {
		if (privT==null) return;
		
		int depRule = ruleMap(ruleid).getPrivDepRule(ruleid);
		Task[] tasks=factory.make(depRule, privT, builder(depRule));
		if (tasks==null) 
			return;		
		localQ.addAll(0, tasks);
	}
	public void addTasksForDelta(int ruleid, TableInst[] deltaT) {
		if (deltaT==null) return;		

		for (int i=0; i<deltaT.length; i++) {
			if (deltaT[i]==null) continue;
			addTasksForDelta(ruleid, deltaT[i], i);
		}		
	}
	
	void sliceTable(TableInst deltaT, int priority) {
		if (getWorkerNum()==1) return;
		
		TmpTablePool.invalidate(id, deltaT, priority);
		int sliceNum = deltaT.filledToCapacity()?4:2;
		if (getWorkerNum() > 4) {
			sliceNum = deltaT.filledToCapacity()?8:4;
		}
		deltaT.setVirtualSliceNum(sliceNum);
	}
	
	int totalRuleCount(TIntArrayList depRules1, TIntArrayList depRulesRest) {
		int count=0;
		if (depRules1!=null) count+= depRules1.size();
		if (depRulesRest!=null) count+= depRulesRest.size();
		return count;
	}
	public void addTasksForDelta(int ruleid, TableInst deltaT, int priority) {
		TIntArrayList depRules = ruleMap(ruleid).getDeltaRules1(ruleid);
		TIntArrayList depRulesRest = ruleMap(ruleid).getDeltaRulesRest(ruleid);
		
		if (totalRuleCount(depRules, depRulesRest)>=2) {
			deltaT.setRequireFree(false);
		}
		
		if (depRules!=null) 
			addTasksForDeltaReally(ruleid, deltaT, priority, depRules, true);
		if (depRulesRest!=null) 
			addTasksForDeltaReally(ruleid, deltaT, priority, depRulesRest, false);
	}
	void maybeSliceDeltaT(TableInst deltaT, int priority) {
		if (priority==0 && !deltaT.nearlyEmpty() && !fullyWorking()) {
			assert deltaT.virtualSliceNum()==1;
			sliceTable(deltaT, priority);
		} else if (priority==0 && deltaT.filledToCapacity() && localQ.likelySize() < 2) {
			assert deltaT.virtualSliceNum()==1;
			sliceTable(deltaT, priority);
		}
	}
	void addTasksForDeltaReally(int ruleid, TableInst deltaT, int priority, TIntArrayList depRules, boolean first) {
		VisitorBuilder _builder;
		for (int i=0; i<depRules.size(); i++) {
			int deltarule = depRules.get(i);
			if (first) maybeSliceDeltaT(deltaT, priority);
			
			 _builder = builder(deltarule);
			Task[] tasks=factory.makeDelta(deltarule, deltaT, _builder);			
			if (tasks==null) continue;
			localQ.addAll(priority, tasks);
		}
	}
	
	void log(long start, long end, Task t) {
		int rid=t.getRuleId();
		String s="=Worker "+id+"| Rule "+rid+"| start:"+start+"| end:"+end+"| task:"+t;
		log.add(s);
	}
	public void run() {
		while (true) {
			Task task=null;
			try {
				task = reserveTask();
				long start=0, end=0;
				if (verbose) start=System.currentTimeMillis();
				currentRuleId.set(task.getRuleId());
				try { 
					task.run(this); 
				} catch (SocialiteFinishEval e) {
					//haltEpoch();					
					//WorkerNode.haltOthers();
				} catch (Throwable e) {
					discardTasks();
					Manager.getInst().handleError(task, e);
				}
				if (verbose) {	
					end=System.currentTimeMillis();
					log(start, end, task);
				}
				if (isHalted()) continue;
				
				EvalTask t=(EvalTask)task;							
				TableInst[] deltaT = t.getResultDeltaTable();
				addTasksForDelta(t.getRuleId(), deltaT);
				
				TableInst[] privT = t.getHeadPrivateTable();				
				addTasksForPriv(t.getRuleId(), privT);
			} catch (InterruptedException e) {
				break;
			} finally {
				if (task!=null) localQ.pop(task);
			}
		}
		logToFile();
	}
}
