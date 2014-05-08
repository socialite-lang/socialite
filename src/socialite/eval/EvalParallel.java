package socialite.eval;

import gnu.trove.list.array.TIntArrayList;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.codegen.Epoch;
import socialite.codegen.RuleComp;
import socialite.engine.Config;
import socialite.parser.Const;
import socialite.parser.Rule;
import socialite.parser.antlr.ClearTable;
import socialite.parser.antlr.DropTable;
import socialite.parser.antlr.TableStmt;
import socialite.resource.SRuntime;
import socialite.resource.TableInstRegistry;
import socialite.resource.TableSliceMap;
import socialite.tables.TableInst;
import socialite.util.SociaLiteException;

class InitThread extends Thread {
	public static final Log L = LogFactory.getLog(EvalParallel.class);

	CyclicBarrier barrier;
	Runnable r;

	public InitThread(CyclicBarrier _barrier, String name) {
		super(name);
		barrier = _barrier;
	}

	public void run() {
		while (true) {
			try {
				waitForTask();
				r.run();
				barrier();
			} catch (InterruptedException ie) { 
			    break; 
			}
		}
	}

	public synchronized void set(Runnable _r) {
		r = _r;
		this.notify();
	}

	synchronized void waitForTask() throws InterruptedException {
		if (r == null)
			wait();
	}

	void barrier() throws InterruptedException {
		r = null;
		try { barrier.await(); } 
		catch (BrokenBarrierException e) { throw new SociaLiteException(e); }
	}
}

public class EvalParallel extends Eval {
	public static final Log L = LogFactory.getLog(EvalParallel.class);

	protected InitThread[] initThreads;
	protected CyclicBarrier barrier;
	Manager manager;

	public EvalParallel(SRuntime _runtime, Epoch _epoch, Config _conf) {		
		runtime = _runtime;
		epoch = _epoch;
		conf = _conf;
		
		if (epoch.getRules().size()>0) {
			Rule r=epoch.getRules().get(0);
			ruleMap = runtime.getRuleMap(r.id());
		}
		sliceMap = runtime.getSliceMap();
		tableRegistry = runtime.getTableRegistry();
		manager = Manager.getInst();
		
		if (!manager.isAlive()) manager.start();
	}

	protected void parallelInit(int i, Runnable r) {
		initThreads[i].set(r);
	}

	protected void startInitThreads() {
		if (initThreads == null) {
			initThreads = new InitThread[conf.getWorkerNum()-1];
			barrier = new CyclicBarrier(conf.getWorkerNum());
			for (int i = 0; i < initThreads.length; i++) {
				initThreads[i] = new InitThread(barrier, "InitThread #" + i);
				initThreads[i].start();
			}
		}
	}

	protected void shutdownInitThreads() {
		if (initThreads != null) {
			for (int i = 0; i < initThreads.length; i++) {				
				initThreads[i].interrupt();
			}
			initThreads = null;
		}
	}

	protected void barrierWait() {
		try {
			barrier.await();
		} catch (InterruptedException e) {
			throw new SociaLiteException(e);
		} catch (BrokenBarrierException e) {
			L.error("InitThread.barrier(): Barrier Exception:" + e);
			throw new SociaLiteException(e);
		}
	}

	void clearTable(final int tid) {
		if (tid<0) return; 
		final TableInst[] tableArray = tableRegistry.getTableInstArray(tid);
		if (tableArray==null) return;
		
		startInitThreads();
		final int threadNum = conf.getWorkerNum();
		class ParClear implements Runnable {
			int id;
			ParClear(int _id) { id=_id; }
			public void run() {
				if (tableArray.length==1) {
					int size = sliceMap.localSize(tid);
					if (size<threadNum) {
						if (id==0) { tableArray[0].clear(); }
						return;
					}
					int from = sliceMap.localBeginIndex(tid)+(size+threadNum-1)/threadNum*id;
					int to = sliceMap.localBeginIndex(tid)+(size+threadNum-1)/threadNum*(id+1);
					if (to>sliceMap.localEndIndex(tid)+1) to = sliceMap.localEndIndex(tid)+1;					
					
					tableArray[0].clear(from, to);	
				} else {
					int from = (tableArray.length+threadNum-1)/threadNum*id;
					int to = (tableArray.length+threadNum-1)/threadNum*(id+1);
					if (to > tableArray.length) to=tableArray.length;
					for (int i=from; i<to; i++) {
						tableArray[i].clear();
					}
				}				
			}
		}
		for (int i=0; i<threadNum-1; i++) {
			parallelInit(i, new ParClear(i));
		}
		new ParClear(threadNum-1).run();
		barrierWait();
	}
	void dropTable(int tid) {
		if (tid<0) return; 
		TableInst[] tableArray = tableRegistry.getTableInstArray(tid);
		if (tableArray==null) return;
		
		for (int i=0; i<tableArray.length; i++) 
			tableArray[i] = null;
	}
	
	public void init() { 
		for (TableStmt s:epoch.tableStmts()) {
			if (s instanceof ClearTable) {
				clearTable(((ClearTable)s).id());
			} else {
				assert s instanceof DropTable;
				dropTable(((DropTable)s).id());
			} 				
		}
		int idx=0;
		for (Class init:epoch.getInitClasses()) {
			List<Const> _consts=epoch.getInitConsts().get(idx);			
			List consts = new ArrayList(_consts.size());
			for (int i=0; i<_consts.size(); i++) {
				Const c=_consts.get(i);
				consts.add(c.val);
			}
			initTable(init, consts);
			idx++;
		}
	}
	
	void initTable(Class init, List consts) {
		startInitThreads();				
		
		Constructor<? extends Runnable> c;
		try {
			c = init.getConstructor(int.class, TableInstRegistry.class, TableSliceMap.class);
		} catch (Exception e) {
			throw new SociaLiteException(e);
		}

		int threadNum = conf.getWorkerNum();
		try {
			for (int i=0; i<threadNum-1; i++) {
				InitRunnable r=(InitRunnable)c.newInstance(i, tableRegistry, sliceMap);
				r.setArgs(consts);
				parallelInit(i, r);
			}
			InitRunnable r = (InitRunnable)c.newInstance(threadNum-1, tableRegistry, sliceMap);
			r.setArgs(consts);
			r.run();
			barrierWait();
		} catch (Exception e) {
			throw new SociaLiteException(e);
		}
	}
	
	
	void initDone() {
		manager.setEvalInitDone();
	}

	public void finish() {
		shutdownInitThreads();
		manager.cleanup();
		runtime.cleanup(epoch);
	}

	@Override
	public void run() {
		//long start= System.currentTimeMillis();
		init();
		initDone();
		
		Iterator<RuleComp> it = epoch.topologicalOrder();
		while (it.hasNext()) {
			RuleComp rc = it.next();
			if (rc.scc()) evalSCC(rc);
			else eval(rc);
		}
		finish();
		//L.info("Eval time:"+(System.currentTimeMillis()-start)+"ms");
	}

	void evalSCC(RuleComp scc) {		
		for (Rule r:scc.getStartingRules()) {
			if (r != null) {
				manager.addCmd(new EvalCommand(r.id()));
			}
		}
		waitForFinish(scc);
	}

	void eval(RuleComp rc) {
		for (Rule r:rc.getStartingRules()) {
			if (r != null) {
				manager.addCmd(new EvalCommand(r.id()));				
			}
		}
		waitForFinish(rc);
	}

	private void waitForFinish(RuleComp rc) {
		shutdownInitThreads();

		TIntArrayList _rules = new TIntArrayList();
		for (Rule r:rc.getRules())
			_rules.add(r.id());
		int rules[] = _rules.toArray();
		
		while (true) {
			boolean stillRunning=Worker.isRunningAny(rules);
			if (!stillRunning) break;			
			sleep(16);
		}
	}

	void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		String str = "Eval-parallel:";
		for (Rule r : epoch.getRules()) {
			str += r;
		}
		return str;
	}
}
