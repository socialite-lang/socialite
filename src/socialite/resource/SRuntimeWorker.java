package socialite.resource;

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
import socialite.dist.worker.WorkerConnPool;
import socialite.dist.worker.WorkerNode;
import socialite.engine.Config;
import socialite.eval.Eval;
import socialite.eval.EvalProgress;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.tables.QueryRunnable;
import socialite.tables.QueryVisitor;
import socialite.tables.TableInst;
import socialite.util.Loader;
import socialite.util.SociaLiteException;

public class SRuntimeWorker extends SRuntime {
	public static final Log L=LogFactory.getLog(SRuntimeWorker.class);
    static SRuntimeWorker inst=null;
    public static SRuntimeWorker create(Config conf, WorkerAddrMap addrMap, WorkerConnPool conn) {
        inst = new SRuntimeWorker(addrMap, conn, conf);
        return inst;
    }
    public static SRuntimeWorker getInst() {
        return inst;
    }

    WorkerAddrMap workerAddrMap;
	WorkerConnPool workerConn;
	Sender sender;

	public SRuntimeWorker(WorkerAddrMap _addrMap, WorkerConnPool _workerConn, Config _conf) {
		workerAddrMap = _addrMap;
		workerConn = _workerConn;		
		conf=_conf;
		sender = Sender.get(conf, workerAddrMap, workerConn);
		tableMap = new HashMap<String, Table>();
        idleMap = new ConcurrentHashMap<Integer, Object>(128, 0.75f, 32);
        EvalRefCount.getInst(new WorkerNodeIdleCallback());
	}

    static class WorkerNodeIdleCallback implements EvalRefCount.IdleCallback {
        public void call(int id, int idleTimestamp) {
            WorkerNode.getInst().reportIdle(id, idleTimestamp);
        }
    }

	public WorkerAddrMap getWorkerAddrMap() {
		return workerAddrMap;
	}
	public Sender sender() { return sender; }
	public Config getConf() { return conf; }

	public DistTableSliceMap getSliceMap() {
		if (sliceMap==null) {
			int sliceNum = conf.sliceNum();
			int virtualSliceNum = conf.virtualSliceNum();
			int minSliceSize = conf.minSliceSize();					
			sliceMap = new DistTableSliceMap(workerAddrMap, sliceNum, virtualSliceNum, minSliceSize);
		}
		return (DistTableSliceMap )sliceMap;
	}
	public TableInstRegistry getTableRegistry() {
		if (tableReg==null)
			tableReg = new TableInstRegistry(this);
		return tableReg;
	}
	public LockMap getLockMap() {
		if (lockMap==null) {
			assert tableMap!=null;
			int maxId=0;
			for (Table t:tableMap.values()) {
				if (t.id() > maxId) maxId = t.id();
			}
			lockMap = new LockMap(maxId, getSliceMap());
		}
		return lockMap;
	}
	
	public void createVisitorBuilderFor(List<Rule> rules) {		
		VisitorBuilder builder = new VisitorBuilder(this, rules);
		synchronized(builderMap) {			
			for (Rule r:rules) {
				builderMap.put(r.id(), builder);
			}
		}
	}
	public VisitorBuilder getVisitorBuilder(Rule rule) {
		synchronized(builderMap) {
			assert builderMap.containsKey(rule.id());
			return builderMap.get(rule.id());
		}
	}
	public VisitorBuilder getVisitorBuilder(int rule) {
		synchronized(builderMap) {
			assert builderMap.containsKey(rule);
			return builderMap.get(rule);
		}
	}
	

	public Map<String, Table> getTableMap() {
		assert tableMap!=null;		
		return tableMap;
	}
	
	static class MergeTableMap {
		final public Map<String, Table> map;
		MergeTableMap(Map<String, Table> old, Map<String, Table> newMap) {
			if (old.keySet().equals(newMap.keySet())) {
				map = newMap;
			} else {
				map = new HashMap<String, Table>(old);
				map.putAll(newMap);
			}
		}
	}
	public void updateTableMap(Map<String, Table> _tableMap) {
		assert tableMap!=null;
		tableMap = new MergeTableMap(tableMap, _tableMap).map;
	}

	public void addRuleMap(List<Rule> rules, RuleMap rmap) {
		synchronized(rulemapMap) {
			for (Rule r:rules) {
				assert !rulemapMap.containsKey(r.id());
				rulemapMap.put(r.id(), rmap);
			}
		}
	}
	public RuleMap getRuleMap(int rule) {
		synchronized(rulemapMap) {
			assert rulemapMap.containsKey(rule);
			return rulemapMap.get(rule);
		}
	}
	
	public Eval getEvalInst(Epoch epoch) {
		@SuppressWarnings("rawtypes")
		Class evalClass=epoch.getEvalclass();
		if (evalClass==null) return null;
		
		Eval inst=null;
		try {
		    @SuppressWarnings("unchecked")
			Constructor<? extends Runnable> c = evalClass.getConstructor(SRuntime.class, Epoch.class, Config.class);
		    inst = (Eval)c.newInstance(this, epoch, conf);
		} catch (Exception e) {		        
			L.fatal("Cannot get/call constructor of "+evalClass+":"+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
			throw new SociaLiteException(e);
		} 
		return inst;
	}
	
	@SuppressWarnings("unchecked")
	public QueryRunnable getQueryInst(int queryTableId, String queryClsName, QueryVisitor qv) {
		Constructor<? extends Runnable> c=null;
		
		TableInst[] tableArray = tableReg.getTableInstArray(queryTableId);			
		Object tableArg = tableArray.length==1? tableArray[0]:tableArray;
		@SuppressWarnings("rawtypes")
		Class queryClass = Loader.forName(queryClsName);
		Class<?> type=tableArg.getClass();
		try {
			c=queryClass.getConstructor(type, QueryVisitor.class, TableSliceMap.class);
			QueryRunnable qr = (QueryRunnable)c.newInstance(tableArg, qv, sliceMap);				
			return qr;
		} catch (Exception e) {
			L.fatal("getQueryInst(): Cannot retrieve constructor of "+queryClsName+", "+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
			throw new SociaLiteException(e);
		}
	}
	
	public EvalProgress getProgress() {		
		return evalProgress;
	}
}
