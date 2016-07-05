package socialite.eval;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.resource.*;
import socialite.tables.TmpTableInst;
import socialite.tables.TableInst;
import socialite.util.SocialiteFinishEval;
import socialite.tables.Joiner;


abstract class VisitorWrapper extends Joiner {
    Joiner v;
    AtomicInteger counter;
    int limit;
    public VisitorWrapper(Joiner _v, AtomicInteger _counter, int _limit) {
        v=_v;
        counter=_counter;
        limit=_limit;
    }
    public void setWorker(Worker w) { v.setWorker(w); }
    public Worker getWorker() { return v.getWorker(); }
    public int getWorkerId() { return v.getWorkerId(); }

    public int getEpochId() { return v.getEpochId(); }
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
    public TmpTableVisitor(Joiner _v, TableInst[] _tableArray, AtomicInteger _counter, int _limit) {
        super(_v, _counter, _limit);
        tableArray=_tableArray;
    }
    public void cleanup() {
        free();
    }
    void free() {
        for (int i=0; i<tableArray.length; i++) {
            if (tableArray[i]!=null) {
                if (tableArray[i] instanceof TmpTableInst) {
                    TmpTablePool.free((TmpTableInst)tableArray[i]);
                }
            }
        }
    }
}

class VisitorReportingProgress extends VisitorWrapper {
    public static final Log L = LogFactory.getLog(VisitorReportingProgress.class);

    EvalProgress progress;
    TaskReport report;
    int reportFreq=1;
    public VisitorReportingProgress(TaskReport _report, Joiner _v, AtomicInteger _counter, int _limit) {
        super(_v, _counter, _limit);
        report = _report;
        reportFreq = (limit+19)/20;
    }
    public void run() {
        try {
            v.run();
        } catch (SocialiteFinishEval fin) {
            report.updateProgress(getRuleId(), 1.0f);
            throw fin;
        } catch (Error err) {
            L.warn("VisitorReportingProgress got exception:"+err);
            report.addErrorMessage(err.toString());
            throw err;
        } catch (RuntimeException re) {
            report.addErrorMessage(re.toString());
            throw re;
        }
        int count=counter.incrementAndGet();
        if (count%reportFreq==0) {
            float percetage = ((float)count)/limit;
            report.updateProgress(getRuleId(), percetage);
        } else if (count==limit) {
            report.updateProgress(getRuleId(), 1.0f);
        }
    }
    public void cleanup() {

    }
}

public class TaskFactory {
    public static final Log L = LogFactory.getLog(VisitorReportingProgress.class);

    public TaskFactory() { }

    static boolean isNull(TableInst[] tableArray) {
        if (tableArray==null) return true;
        for (int i=0; i<tableArray.length; i++) {
            if (tableArray[i]!=null)
                return false;
        }
        return true;
    }
    public Task[] makeDelta(int ruleid, TableInst deltaT, JoinerBuilder builder) {
        if (deltaT==null) return null;
        EvalTask[] tasks = make(ruleid, new TableInst[]{deltaT}, builder);
        return tasks;
    }
    public EvalTask[] make(int ruleid, TableInst[] tableArray, JoinerBuilder builder) {
        if (isNull(tableArray)) return null;
        Joiner[] joiners = builder.getNewJoinerInst(ruleid, tableArray);
        if (requireFree(tableArray))
            joiners = addTableFree(joiners, tableArray);
        if (joiners==null) return null;

        EvalTask[] tasks = new EvalTask[joiners.length];
        for (int i=0; i<joiners.length; i++) {
            if (joiners[i] == null) continue;
            EvalTask t = new EvalTask(joiners[i]);
            tasks[i] = t;
        }
        return tasks;
    }

    public Task[] make(EvalCommand eval, JoinerBuilder builder, SRuntime runtime) {
        Joiner[] visitors = eval.newInst(builder);

        if (visitors==null) return null;
        if (eval.isReceived()) {
            EvalWithTable et = (EvalWithTable)eval;
            visitors = addTableFree(visitors, new TableInst[]{et.getTable()});
        } else {
            visitors = addTaskReport(visitors, runtime);
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
            if (tableArray[i]!=null && tableArray[i] instanceof TmpTableInst)
                return true;
        }
        return false;
    }

    static Joiner[] addTableFree(Joiner[] joiners, TableInst[] tableArray) {
        Joiner[] wrappers=new Joiner[joiners.length];
        AtomicInteger counter=new AtomicInteger(0);
        int initCounter=0;
        int limit=joiners.length;
        for (int i=0; i<joiners.length; i++) {
            if (joiners[i]==null) {
                wrappers[i] = null;
                initCounter++;
            } else {
                wrappers[i] = new TmpTableVisitor(joiners[i], tableArray, counter, limit);
            }
        }
        counter.set(initCounter);
        return wrappers;
    }

    static int getEpochId(Joiner[] joiners) {
        for (int i=0; i<joiners.length; i++) {
            if (joiners[i]!=null) {
                return joiners[i].getEpochId();
            }
        }
        assert false: "SHould never reach here";
        return -1;
    }
    static Joiner[] addTaskReport(Joiner[] v, SRuntime runtime) {
        Joiner[] wrappers=new Joiner[v.length];
        AtomicInteger counter=new AtomicInteger(0);
        int initCounter=0;
        int limit=v.length;
        TaskReport taskReport = runtime.getTaskReport(getEpochId(v));
        for (int i=0; i<v.length; i++) {
            if (v[i]==null) {
                wrappers[i] = null;
                initCounter++;
            } else {
                wrappers[i] =
                        new VisitorReportingProgress(taskReport, v[i], counter, limit);
            }
        }
        counter.set(initCounter);
        return wrappers;
    }
}
