package socialite.eval;

import java.util.concurrent.atomic.AtomicInteger;

import socialite.resource.SRuntime;
import socialite.resource.VisitorBuilder;
import socialite.tables.TableInst;
import socialite.util.SocialiteFinishEval;
import socialite.visitors.IVisitor;
import socialite.visitors.VisitorImpl;


abstract class VisitorWrapper extends VisitorImpl {
	IVisitor v;	
	AtomicInteger counter;
	int limit;
	public VisitorWrapper(IVisitor _v, AtomicInteger _counter, int _limit) {
		v=_v;
		counter=_counter;
		limit=_limit;
	}
	public void setWorker(Worker w) { v.setWorker(w); }
	public Worker getWorker() { return v.getWorker(); }
	public int getWorkerId() { return v.getWorkerId(); }	
	
	public int getRuleId() { return v.getRuleId(); }
	public TableInst[] getDeltaTables() { return v.getDeltaTables(); }
	public TableInst[] getPrivateTable() { return v.getPrivateTable(); }
	
	public void run() {
		v.run();
		int count=counter.incrementAndGet();
		if (count==limit) 
			cleanup();
	}
	public abstract void cleanup();
	
	public String toString() { return v.toString(); }
	public boolean hasPriority() { return v.hasPriority(); }
	public void incPriority() { v.incPriority(); }
}

class TmpTableVisitor extends VisitorWrapper {
	TableInst[] tableArray;
	public TmpTableVisitor(IVisitor _v, TableInst[] _tableArray, AtomicInteger _counter, int _limit) {
		super(_v, _counter, _limit);
		tableArray=_tableArray;
	}
	public void cleanup() {
		free();
	}
	void free() {
		for (int i=0; i<tableArray.length; i++) {
			if (tableArray[i]!=null) {				
				if (tableArray[i].requireFree()) {
					TmpTablePool.free(tableArray[i]);
				}
			}
		}
	}	
}

class VisitorReportingProgress extends VisitorWrapper {
	EvalProgress progress;
	int reportFreq=1;
	public VisitorReportingProgress(EvalProgress _progress, IVisitor _v, AtomicInteger _counter, int _limit) {
		super(_v, _counter, _limit);
		progress = _progress;
		reportFreq = (limit+19)/20;
		progress.init(getRuleId());
	}
	public void run() {
		try {
			v.run();
		} catch (SocialiteFinishEval fin) {
			progress.update(getRuleId(), limit, limit);
			throw fin;
		} catch (Error err) {
			progress.halt(getRuleId());
			throw err;
		} catch (RuntimeException re) {
			progress.halt(getRuleId());
			throw re;
		}
		int count=counter.incrementAndGet();
		if (count%reportFreq==0 || count==limit) {
			progress.update(getRuleId(), count, limit);
		} 
	}
	public void cleanup() {
		
	}
}

public class TaskFactory {
	public TaskFactory() { }
	
	static boolean isNull(TableInst[] tableArray) {
		if (tableArray==null) return true;
		for (int i=0; i<tableArray.length; i++) {
			if (tableArray[i]!=null)
				return false;
		}
		return true;
	}
	public Task[] makeDelta(int ruleid, TableInst deltaT, VisitorBuilder builder) {
		if (deltaT==null) return null;
		EvalTask[] tasks = make(ruleid, new TableInst[]{deltaT}, builder);
		if (deltaT.isAccessed()) {
			assert tasks.length==1;			
			tasks[0].setDeltaT(deltaT);			
		}
		return tasks;
	}
	public EvalTask[] make(int ruleid, TableInst[] tableArray, VisitorBuilder builder) {
		if (isNull(tableArray)) return null;
		IVisitor[] visitors;
		visitors = builder.getNewVisitorInst(ruleid, tableArray);
		if (requireFree(tableArray)) 
			visitors = addTableFree(visitors, tableArray);
		if (visitors==null) return null;
		
		EvalTask[] tasks = new EvalTask[visitors.length];
		for (int i=0; i<visitors.length; i++) {
			if (visitors[i] == null) continue;
			EvalTask t = new EvalTask(visitors[i]);
			tasks[i] = t;
		}
		return tasks;
	}
	
	public Task[] make(EvalCommand eval, VisitorBuilder builder, SRuntime runtime) {
		IVisitor[] visitors = eval.newInst(builder);
		if (visitors==null) return null;
		if (eval.isReceived()) {
			EvalWithTable et = (EvalWithTable)eval;
			visitors = addTableFree(visitors, new TableInst[]{et.getTable()});
		} else {
			visitors = addProgressReport(visitors, runtime);
		}
		Task[] tasks = new Task[visitors.length];
		for (int i=0; i<visitors.length; i++) {
			if (visitors[i]==null) continue;
			Task t = new EvalTask(visitors[i]);
			tasks[i] = t;
		}
		return tasks;
	}
	
	boolean requireFree(TableInst[] tableArray) {
		if (tableArray==null) return false;
		for (int i=0; i<tableArray.length; i++) {
			if (tableArray[i]!=null && tableArray[i].requireFree())
				return true;
		}
		return false;
	}
			
	static IVisitor[] addTableFree(IVisitor[] visitors, TableInst[] tableArray) {
		IVisitor[] wrappers=new IVisitor[visitors.length];
		AtomicInteger counter=new AtomicInteger(0);
		int initCounter=0;
		int limit=visitors.length;
		for (int i=0; i<visitors.length; i++) {
			if (visitors[i]==null) {
				wrappers[i] = null;
				initCounter++;					
			} else {
				wrappers[i] = new TmpTableVisitor(visitors[i], tableArray, counter, limit);				
			}
		}
		counter.set(initCounter);
		return wrappers;
	}
	
	static IVisitor[] addProgressReport(IVisitor[] v, SRuntime runtime) {
		IVisitor[] wrappers=new IVisitor[v.length];
		AtomicInteger counter=new AtomicInteger(0);
		int initCounter=0;
		int limit=v.length;
		EvalProgress evalReport = runtime.getProgress();
		for (int i=0; i<v.length; i++) {
			if (v[i]==null) {
				wrappers[i] = null;
				initCounter++;					
			} else {
				wrappers[i] = 
					new VisitorReportingProgress(evalReport, v[i], counter, limit);				
			}
		}
		counter.set(initCounter);		
		return wrappers;
	}
}
