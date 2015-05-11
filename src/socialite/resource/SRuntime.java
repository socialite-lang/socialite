package socialite.resource;


import gnu.trove.impl.sync.TSynchronizedIntObjectMap;
import gnu.trove.map.TIntObjectMap;
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
import socialite.engine.Config;
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

// SocialiteRuntime
public class SRuntime {
	public static final Log L=LogFactory.getLog(SRuntime.class);

	static long usedMemory() {
		return Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();	
	}
	public static long freeMemory() { return maxMemory() - usedMemory(); }
	public static long maxMemory() { return Runtime.getRuntime().maxMemory(); }

    static SRuntime inst=null;
    public static SRuntime create(Config conf) {
        inst = new SRuntime(conf);
        return inst;
    }
    public static SRuntime getInst() { return inst; }

    Config conf;
	Map<String, Table> tableMap;
    TableSliceMap sliceMap;
	TableInstRegistry tableReg;
	LockMap lockMap;

	TIntObjectMap<VisitorBuilder> builderMap = new TSynchronizedIntObjectMap<VisitorBuilder>(new TIntObjectHashMap<VisitorBuilder>(128));
	TIntObjectMap<RuleMap> rulemapMap = new TSynchronizedIntObjectMap<RuleMap>(new TIntObjectHashMap<RuleMap>(128));
	
	EvalProgress evalProgress=EvalProgress.getInst();

    ConcurrentHashMap<Integer, Object> idleMap;
    class LocalIdleCallback implements EvalRefCount.IdleCallback {
        public void call(int id, int idleTimestamp) {
            Object o = new Object();
            Object prev = idleMap.putIfAbsent(id, o);
            if (prev!=null) o = prev;

            synchronized(o) { o.notify(); }
        }
    }
    public SRuntime() {
        tableMap = new HashMap<String, Table>();
    }
	SRuntime(Config _conf) {
		conf=_conf; 
		tableMap = new HashMap<String, Table>();
        idleMap = new ConcurrentHashMap<Integer, Object>(128, 0.75f, 32);
        EvalRefCount.getInst(new LocalIdleCallback());
    }
    public void waitForIdle(int epochId) throws InterruptedException {
        Object o = new Object();
        synchronized (o) {
            Object prev = idleMap.putIfAbsent(epochId, o);
            if (prev==null) {
                o.wait();
            }
        }
        idleMap.remove(epochId);
        EvalRefCount.getInst().clear(epochId);
    }
	public WorkerAddrMap getWorkerAddrMap() { throw new SociaLiteException("Not supported"); }
	public Sender sender() { throw new SociaLiteException("Not supported"); }
	
	public Config getConf() { return conf; }
	
	public TableSliceMap getSliceMap() {
		if (sliceMap==null) {
			int sliceNum = conf.sliceNum();
			int virtualSliceNum = conf.virtualSliceNum();
			int minSliceSize = conf.minSliceSize();
            sliceMap = new TableSliceMap(sliceNum, virtualSliceNum, minSliceSize);
		}
		return sliceMap;
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
		for (Rule r:rules) {
			builderMap.put(r.id(), builder);
		}
	}
	public VisitorBuilder getVisitorBuilder(Rule rule) {
		assert builderMap.containsKey(rule.id());
		return builderMap.get(rule.id());
	}
	public VisitorBuilder getVisitorBuilder(int rule) {
		assert builderMap.containsKey(rule);
		return builderMap.get(rule);
	}
	
	public void update(Epoch e) {		
		assert tableMap!=null;
		createVisitorBuilderFor(e.getRules());

		for (Table t:e.getNewTables()) {
			if (t.isCompiled()) {
                getSliceMap().addTable(t);
            }
            if (t instanceof GeneratedT) {
                Class<?> tableCls=TableUtil.load(t.className());
                assert tableCls!=null;
            } else {
                getLockMap().createLock(t);
            }
		}

		addRuleMap(e.getRules(), e.getRuleMap());
		for (Rule r:e.getRules()) {
			if (r.isSimpleArrayInit()) continue;
			String visitorClsName = e.getVisitorClassName(r.id());
			if (visitorClsName!=null) {
				Class<?> visitorCls = Loader.forName(visitorClsName);
                if (visitorCls == null) {
                    L.error("Visitor class ("+visitorClsName+") is null for rule:"+r.id());
                }
				getVisitorBuilder(r.id()).setVisitorClass(r.id(), visitorCls);
			}
		}
	}
	public void cleanup(Epoch e) {
        for (Rule r:e.getRules()) {
			rulemapMap.remove(r.id());
		}
		for (Rule r:e.getRules()) {
			if (r.isSimpleArrayInit()) continue;
			String visitorClsName = e.getVisitorClassName(r.id());
			if (visitorClsName!=null) {
				builderMap.remove(r.id());
			}
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
		for (Rule r:rules) {
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
