package socialite.eval;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.codegen.Epoch;
import socialite.codegen.RuleComp;
import socialite.dist.EvalRefCount;
import socialite.parser.Const;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.parser.antlr.ClearTable;
import socialite.parser.antlr.DropTable;
import socialite.parser.antlr.TableStmt;
import socialite.resource.SRuntime;
import socialite.tables.TableInst;
import socialite.util.SociaLiteException;
import socialite.yarn.ClusterConf;

class InitThread extends Thread {
    static final Log L = LogFactory.getLog(EvalParallel.class);

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

    public EvalParallel(SRuntime _runtime, Epoch _epoch) {
        runtime = _runtime;
        epoch = _epoch;

        if (epoch.getRules().size()>0) {
            Rule r=epoch.getRules().get(0);
            ruleMap = runtime.getRuleMap(r.id());
        }
        partitionMap = runtime.getPartitionMap();
        tableRegistry = runtime.getTableRegistry();
        manager = Manager.getInst();

        if (!manager.isAlive()) manager.start();
    }

    protected void parallelInit(int i, Runnable r) {
        initThreads[i].set(r);
    }

    protected void startInitThreads() {
        if (initThreads == null) {
            int numThreads = ClusterConf.get().getNumWorkerThreads();
            initThreads = new InitThread[numThreads - 1];
            barrier = new CyclicBarrier(ClusterConf.get().getNumWorkerThreads());
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
        final int numThreads = ClusterConf.get().getNumWorkerThreads();
        class ParClear implements Runnable {
            int id;
            ParClear(int _id) { id=_id; }
            public void run() {
                int from = (tableArray.length + numThreads -1)/ numThreads*id;
                int to = (tableArray.length + numThreads -1)/ numThreads*(id+1);
                if (from >= tableArray.length) {
                    return;
                }
                if (to > tableArray.length) to=tableArray.length;
                for (int i=from; i<to; i++) {
                    tableArray[i].clear();
                }
            }
        }
        for (int i = 0; i< numThreads - 1; i++) {
            parallelInit(i, new ParClear(i));
        }

        new ParClear(numThreads - 1).run();
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
                clearTable(s.id());
            } else {
                assert s instanceof DropTable;
                dropTable(s.id());
            }
        }
        for (Pair<Table, List<Const>> init: epoch.getInitRuleInfo()) {
            List<Const> consts = init.getRight();
            List<Object> initvals = new ArrayList<>(init.getRight().size());
            for (int i=0; i<consts.size(); i++) {
                initvals.add(consts.get(i).val);
            }
            initTable(init.getLeft(), initvals);
        }

        L.info(" NOTICE set epoch ready (epoch.id="+epoch.id()+")");
        EvalRefCount.getInst().setReady(epoch.id());
        shutdownInitThreads();
    }

    void initTable(Table t, List<Object> consts) {
        final TableInst[] tableArray = tableRegistry.getTableInstArray(t.id());
        if (tableArray==null) return;

        startInitThreads();
        final int numThreads = ClusterConf.get().getNumWorkerThreads();
        class ParInit implements Runnable {
            int id;
            List<Object> args;
            ParInit(int _id, List<Object> _args) {
                id = _id;
                args = _args;
            }
            public void run() {
                int from = (tableArray.length + numThreads - 1)/numThreads*id;
                int to = (tableArray.length + numThreads - 1)/numThreads*(id+1);
                if (to > tableArray.length) to=tableArray.length;
                for (int i=from; i<to; i++) {
                    tableArray[i].init(args);
                }
            }
        }

        for (int i=0; i<numThreads-1; i++) {
            parallelInit(i, new ParInit(i, consts));
        }
        new ParInit(numThreads - 1, consts).run();
        barrierWait();
    }

    public void finish() {
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
        try {
            while (it.hasNext()) {
                RuleComp rc = it.next();
                issued |= eval(rc);
            }
        } finally {
            EvalRefCount.getInst().dec(epoch.id());
            return issued;
        }
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
