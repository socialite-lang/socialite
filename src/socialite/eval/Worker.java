package socialite.eval;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.worker.Receiver;
import socialite.resource.RuleMap;
import socialite.resource.SRuntime;
import socialite.resource.Sender;
import socialite.resource.VisitorBuilder;
import socialite.tables.TableInst;
import socialite.tables.TmpTableInst;
import socialite.util.SocialiteFinishEval;
import gnu.trove.list.array.TIntArrayList;

public class Worker implements Runnable {
	public static final Log L=LogFactory.getLog(Worker.class);
	static final ThreadLocal<Integer> currentRuleId = new ThreadLocal<Integer>();
	public static int getCurrentRuleId() { return currentRuleId.get(); }
	
	public static List<String> log=Collections.synchronizedList(new ArrayList<String>());
	
	public static final boolean verbose=false;	
	static { if (verbose) L.warn("Turn off the verbose flag in Worker.java"); }

	static TaskQueue[] workerQueues;
	public static void initTaskQueues(int workerNum) {
		workerQueues = new TaskQueue[workerNum];
		for (int i=0; i<workerNum; i++) {
			workerQueues[i] = new TaskQueue();
		}
	}
	public static TaskQueue[] getWorkerQueues() { return workerQueues; }

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
		for (TaskQueue q:getWorkerQueues()) {
			q.empty();
		}
	}
	
	public static int getWorkerNum() { return workerQueues.length; }
	
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
	TaskQueue taskQ;
	volatile SRuntime runtime;
	
	Worker() { }
	Worker(int _id) {
		id = _id;	
		taskQ = getWorkerQueues()[id];
		factory = new TaskFactory();
	}
	
	public int id() { return id; }
	
	void setRuntime(SRuntime _runtime) { runtime = _runtime; }
	RuleMap ruleMap(int rule) { return runtime.getRuleMap(rule); }
	VisitorBuilder builder(int rule) { return runtime.getVisitorBuilder(rule); }
	
	public TaskQueue getQueue() { return taskQ; }

	Task reserveTask() throws InterruptedException {
        return taskQ.reserve();
	}
	void discardTasks() {
		taskQ.empty();
	}
	
	public void addTasksForDelta(int ruleid, TableInst[] deltaT) {
		if (deltaT==null) return;		

		for (int i=0; i<deltaT.length; i++) {
			if (deltaT[i]==null) continue;
			Priority p;
			if (i==0) p = Priority.High;
			else p = Priority.Normal;
			addTasksForDelta(ruleid, (TmpTableInst)deltaT[i], p);
		}		
	}
	
	int totalRuleCount(TIntArrayList depRules1, TIntArrayList depRulesRest) {
		int count=0;
		if (depRules1!=null) count+= depRules1.size();
		if (depRulesRest!=null) count+= depRulesRest.size();
		return count;
	}
	public void addTasksForDelta(int ruleid, TmpTableInst deltaT, int priority) {
		// XXX: fix VisitorCodeGen to generate proper code
		if (priority==0) {
			addTasksForDelta(ruleid, deltaT, Priority.High);
		} else {
			addTasksForDelta(ruleid, deltaT, Priority.Normal);
		}
	}
	public void addTasksForDelta(int ruleid, TmpTableInst deltaT, Priority priority) {
		TIntArrayList depRules = ruleMap(ruleid).getDeltaRules1(ruleid);
		TIntArrayList depRulesRest = ruleMap(ruleid).getDeltaRulesRest(ruleid);
		
		if (totalRuleCount(depRules, depRulesRest)>=2) {
			deltaT.setReuse(false);
		}
		
		if (depRules!=null) 
			addTasksForDeltaReally(ruleid, deltaT, priority, depRules, true);
		if (depRulesRest!=null) 
			addTasksForDeltaReally(ruleid, deltaT, priority, depRulesRest, false);
	}

	void addTasksForDeltaReally(int ruleid, TmpTableInst deltaT, Priority priority, TIntArrayList depRules, boolean first) {
		VisitorBuilder _builder;
		for (int i=0; i<depRules.size(); i++) {
			int deltarule = depRules.get(i);
			
			 _builder = builder(deltarule);
			Task[] tasks=factory.makeDelta(deltarule, deltaT, _builder);			
			if (tasks==null) continue;
			taskQ.addAll(priority, tasks);
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
					L.error("Error while running:"+task+", error:"+ e);
                    L.error(ExceptionUtils.getStackTrace(e));
					discardTasks();
					Manager.getInst().handleError(task, e);
				}
				if (verbose) {	
					end=System.currentTimeMillis();
					log(start, end, task);
				}
				if (isHalted()) continue;
                if (task instanceof EvalTask) {
                    EvalTask t = (EvalTask) task;
                    TableInst[] deltaT = t.getResultDeltaTable();   
                    addTasksForDelta(t.getRuleId(), deltaT);
                }
			} catch (InterruptedException e) {
				break;
			} finally {
				if (task!=null) taskQ.pop(task);
			}
		}
		logToFile();
	}
}
