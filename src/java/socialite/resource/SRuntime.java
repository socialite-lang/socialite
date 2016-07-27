package socialite.resource;


import gnu.trove.impl.sync.TSynchronizedIntObjectMap;
import gnu.trove.map.TIntFloatMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntFloatHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.codegen.Epoch;
import socialite.dist.EvalRefCount;
import socialite.eval.*;
import socialite.parser.GeneratedT;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.tables.QueryRunnable;
import socialite.tables.QueryVisitor;
import socialite.tables.TableInst;
import socialite.tables.TableUtil;
import socialite.util.Loader;
import socialite.util.SociaLiteException;
import socialite.util.concurrent.AtomicUtil;

// SocialiteRuntime
public class SRuntime {
    public static final Log L = LogFactory.getLog(SRuntime.class);

    static long usedMemory() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    public static long freeMemory() {
        return maxMemory() - usedMemory();
    }

    public static long maxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    static SRuntime inst = null;

    public static SRuntime create() {
        inst = new SRuntime();
        return inst;
    }

    public static SRuntime getInst() {
        return inst;
    }

    volatile Map<String, Table> tableMap;
    TablePartitionMap partitionMap;
    TableInstRegistry tableReg;

    TIntObjectMap<JoinerBuilder> builderMap = new TSynchronizedIntObjectMap<>(new TIntObjectHashMap<>(128));
    TIntObjectMap<RuleMap> rulemapMap = new TSynchronizedIntObjectMap<>(new TIntObjectHashMap<>(128));

    ConcurrentHashMap<Integer, TaskReport> idleMap; /* epoch-id to TaskReport mapping */

    class LocalIdleCallback implements EvalRefCount.IdleCallback {
        public void call(int id, int idleTimestamp) {
            TaskReport r = new TaskReport();
            TaskReport prev = idleMap.putIfAbsent(id, r);
            if (prev != null) { r = prev; }
            r.setDone();
            synchronized (r) {
                r.notify();
            }
        }
    }

    public SRuntime() {
        tableMap = new HashMap<>();
        idleMap = new ConcurrentHashMap<>(128, 0.75f, 32);
        EvalRefCount.getInst(new LocalIdleCallback());
    }

    public void waitForIdle(int epochId) throws InterruptedException {
        TaskReport r = new TaskReport ();
        TaskReport prev = idleMap.putIfAbsent(epochId, r);
        if (prev != null) { r = prev; }

        synchronized (r) {
            while (!r.isDone()) {
                r.wait();
            }
        }
        idleMap.remove(epochId);
        EvalRefCount.getInst().clear(epochId);
        if (r.isFailed()) {
            throw new RuntimeException(r.getErrorMessage());
        }
    }

    public WorkerAddrMap getWorkerAddrMap() {
        throw new SociaLiteException("Not supported");
    }

    public Sender sender() {
        throw new SociaLiteException("Not supported");
    }

    public TablePartitionMap getPartitionMap() {
        if (partitionMap == null) {
            partitionMap = new TablePartitionMap();
        }
        return partitionMap;
    }

    public TableInstRegistry getTableRegistry() {
        if (tableReg == null)
            tableReg = new TableInstRegistry(this);
        return tableReg;
    }

    public void createVisitorBuilderFor(List<Rule> rules) {
        JoinerBuilder builder = new JoinerBuilder(this, rules);
        for (Rule r : rules) {
            builderMap.put(r.id(), builder);
        }
    }

    public JoinerBuilder getJoinerBuilder(int rule) {
        assert builderMap.containsKey(rule);
        return builderMap.get(rule);
    }

    public void update(Epoch e) {
        assert tableMap != null;
        createVisitorBuilderFor(e.getRules());

        for (Table t : e.getNewTables()) {
            getPartitionMap().addTable(t);
            if (t instanceof GeneratedT) {
                Class<?> tableCls = TableUtil.load(t.className());
                assert tableCls != null;
            }
        }

        addRuleMap(e.getRules(), e.getRuleMap());
        for (Rule r:e.getRules()) {
            if (r.isSimpleArrayInit()) { continue; }

            String visitorClsName = e.getVisitorClassName(r.id());
            if (visitorClsName != null) {
                Class<?> visitorCls = Loader.forName(visitorClsName);
                if (visitorCls == null) {
                    L.error("Visitor class (" + visitorClsName + ") is null for rule:" + r.id());
                }
                getJoinerBuilder(r.id()).setJoinerClass(r.id(), visitorCls);
            }
        }
    }

    public void cleanup(Epoch e) {
        for (Rule r : e.getRules()) {
            rulemapMap.remove(r.id());
        }
        for (Rule r : e.getRules()) {
            if (r.isSimpleArrayInit()) continue;
            String visitorClsName = e.getVisitorClassName(r.id());
            if (visitorClsName != null) {
                builderMap.remove(r.id());
            }
        }
    }

    public Map<String, Table> getTableMap() {
        assert tableMap != null;
        return tableMap;
    }

    public void updateTableMap(Map<String, Table> _tableMap) {
        tableMap = AtomicUtil.atomicMerge(tableMap, _tableMap);
    }

    public void addRuleMap(List<Rule> rules, RuleMap rmap) {
        for (Rule r : rules) {
            assert !rulemapMap.containsKey(r.id());
            rulemapMap.put(r.id(), rmap);
        }
    }

    public RuleMap getRuleMap(int rule) {
        assert rulemapMap.containsKey(rule);
        return rulemapMap.get(rule);
    }

    public Eval getEvalInst(Epoch epoch) {
        @SuppressWarnings("rawtypes")
        Class evalClass = epoch.getEvalclass();
        if (evalClass == null) return null;

        Eval inst = null;
        try {
            @SuppressWarnings("unchecked")
            Constructor<? extends Runnable> c = evalClass.getConstructor(SRuntime.class, Epoch.class);
            inst = (Eval) c.newInstance(this, epoch);
        } catch (Exception e) {
            L.fatal("Cannot get/call constructor of " + evalClass + ":" + e);
            L.fatal(ExceptionUtils.getStackTrace(e));
            throw new SociaLiteException(e);
        }
        return inst;
    }

    @SuppressWarnings("unchecked")
    public QueryRunnable getQueryInst(int queryTableId, String queryClsName, QueryVisitor qv) {
        Constructor<? extends Runnable> c = null;

        TableInst[] tableArray = tableReg.getTableInstArray(queryTableId);
        Object tableArg = tableArray;
        @SuppressWarnings("rawtypes")
        Class queryClass = Loader.forName(queryClsName);
        Class<?> type = tableArg.getClass();
        try {
            c = queryClass.getConstructor(type, QueryVisitor.class, TablePartitionMap.class);
            QueryRunnable qr = (QueryRunnable) c.newInstance(tableArg, qv, partitionMap);
            return qr;
        } catch (Exception e) {
            L.fatal("getQueryInst(): Cannot retrieve constructor of " + queryClsName + ", " + e);
            L.fatal(ExceptionUtils.getStackTrace(e));
            throw new SociaLiteException(e);
        }
    }
    public TaskReport getTaskReport(int epochId) {
        TaskReport r = idleMap.get(epochId);
        if (r == null) {
            r = new TaskReport();
            TaskReport prev = idleMap.putIfAbsent(epochId, r);
            if (prev != null) { r = prev; }
        }
        return r;
    }

    public synchronized TIntFloatMap getProgress() {
        TIntFloatMap progressMap = new TIntFloatHashMap();
        for (TaskReport report: idleMap.values()) {
            progressMap.putAll(report.getProgressMap());
        }
        return progressMap;
    }
}
