package socialite.eval;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.codegen.Epoch;
import socialite.codegen.RuleComp;
import socialite.dist.EvalRefCount;
import socialite.engine.Config;
import socialite.parser.Const;
import socialite.parser.GeneratedT;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.parser.antlr.ClearTable;
import socialite.parser.antlr.DropTable;
import socialite.parser.antlr.TableStmt;
import socialite.resource.SRuntime;
import socialite.resource.TableInstRegistry;
import socialite.resource.TableSliceMap;
import socialite.tables.TableInst;
import socialite.tables.TableUtil;
import socialite.util.Loader;
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
                if (interrupted()) { break; }
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
			initThreads = new InitThread[conf.getWorkerThreadNum()-1];
			barrier = new CyclicBarrier(conf.getWorkerThreadNum());
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
		final int threadNum = conf.getWorkerThreadNum();
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
		for (Class<?> init:epoch.getInitClasses()) {
			List<Const> _consts=epoch.getInitConsts().get(idx);			
			List<Object> consts = new ArrayList<Object>(_consts.size());
			for (int i=0; i<_consts.size(); i++) {
				Const c=_consts.get(i);
				consts.add(c.val);
			}
			initTable(init, consts);
			idx++;
		}
        L.info(" NOTICE set epoch ready (epoch.id="+epoch.id()+")");
        EvalRefCount.getInst().setReady(epoch.id());
        shutdownInitThreads();
	}
	
	void initTable(Class<?> init, List<Object> consts) {
		startInitThreads();				
		
		Constructor<?> c;
		try {
			c = init.getConstructor(int.class, TableInstRegistry.class, TableSliceMap.class);
		} catch (Exception e) {
			throw new SociaLiteException(e);
		}

		int threadNum = conf.getWorkerThreadNum();
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
	
	public void finish() {
		/*for (Table t:epoch.getNewTables()) {
			if (t instanceof GeneratedT) {
				Class klass = TableUtil.load(t.className());
				TmpTablePool.clear(klass);
			}
		}*/
		manager.cleanup();
		runtime.cleanup(epoch);
	}

	@Override
	public void run() {
		init();
        boolean issued = runReally();
        if (issued) { waitForEpochDone(); }
		finish();
	}
    void waitForEpochDone() {
        try {
            runtime.waitForIdle(epoch.id());
        } catch (InterruptedException e) { }
    }
    boolean runReally() {
        boolean issued=false;
        Iterator<RuleComp> it = epoch.topologicalOrder();

        EvalRefCount.getInst().inc(epoch.id());
        while (it.hasNext()) {
            RuleComp rc = it.next();
            issued |= eval(rc);
        }
        EvalRefCount.getInst().dec(epoch.id());
        return issued;
    }

	boolean eval(RuleComp rc) {
        boolean issued=false;
		for (Rule r:rc.getStartingRules()) {
            if (r != null) {
                assert r.getEpochId()==epoch.id();
                try { manager.addCmd(new EvalCommand(epoch.id(), r.id())); }
                catch (InterruptedException e) { break; }
                issued = true;
			}
		}
        return issued;
    }

	public String toString() {
		String str = "Eval-parallel:";
		/*for (Rule r : epoch.getRules()) {
			str += r;
		}*/
		return str;
	}
}
