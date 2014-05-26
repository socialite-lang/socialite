package socialite.resource;


import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.codegen.Epoch;
import socialite.dist.master.MasterNode;
import socialite.dist.worker.WorkerConnPool;
import socialite.engine.Config;
import socialite.eval.Eval;
import socialite.eval.EvalProgress;
import socialite.eval.Manager;
import socialite.eval.Worker;
import socialite.parser.DeltaTable;
import socialite.parser.GeneratedT;
import socialite.parser.PrivateTable;
import socialite.parser.RemoteBodyTable;
import socialite.parser.RemoteHeadTable;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.tables.QueryRunnable;
import socialite.tables.QueryVisitor;
import socialite.tables.TableInst;
import socialite.tables.TableUtil;
import socialite.util.Assert;
import socialite.util.ByteBufferPool;
import socialite.util.FastClassLookup;
import socialite.util.Loader;
import socialite.util.SociaLiteException;

// SocialiteRuntime
public class SRuntime {
	public static final Log L=LogFactory.getLog(SRuntime.class);

	static long usedMemory() {
		return Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();	
	}
	public static long freeMemory() { return maxMemory() - usedMemory(); }
	static long maxMemory() { return Runtime.getRuntime().maxMemory(); }
	
	static SRuntime masterInst=null, theInst=null;
	
	public static SRuntime newTestMasterRt(WorkerAddrMap addrMap) {
		// used by DistCompileTest.java		
		masterInst=new SRuntime(addrMap, null, Config.dist(1));
		return masterInst;
	}
	public static SRuntime newMasterRt(MasterNode master, WorkerAddrMap addrMap) {
		masterInst=new SRuntime(addrMap, null, master.getWorkerConf());
		return masterInst;
	}
	public static SRuntime masterRt() { return masterInst; }
	public static void voidMasterRt() {
		masterInst.setVoid();
		masterInst = null; 
		Manager m=Manager.getInst();
		if (m!=null) m.updateRuntime(null);
	}
	
	public static SRuntime newWorkerRt(Config conf, WorkerAddrMap addrMap, WorkerConnPool conn) { 
		theInst = new SRuntime(addrMap, conn, conf);	
		return theInst;
	}	
	public static SRuntime workerRt() { return theInst; }
	public static void voidWorkerRt() {
		theInst.setVoid();
		theInst = null;
		Manager m=Manager.getInst();
		if (m!=null) m.updateRuntime(null);
	}
	
	Config conf;
	Map<String, Table> tableMap;
	FastClassLookup classLookup;
	TableSliceMap sliceMap;
	TableInstRegistry tableReg;
	LockMap lockMap;
	Throwable exception;
	
	TIntObjectHashMap<VisitorBuilder> builderMap = new TIntObjectHashMap<VisitorBuilder>(16);
	TIntObjectHashMap<RuleMap> rulemapMap = new TIntObjectHashMap<RuleMap>(16);
	
	VisitorBuilder builder;	
	WorkerAddrMap workerAddrMap;
	WorkerConnPool workerConn;
	Sender sender;
	EvalProgress evalProgress=EvalProgress.getInst();
	volatile boolean isVoid = false;
	
	public SRuntime(Config _conf) {
		// used by single-machine compiler
		conf=_conf; 
		tableMap = new HashMap<String, Table>();		
	}
	public SRuntime(WorkerAddrMap _addrMap, Config _conf) {
		// used by master-node
		workerAddrMap = _addrMap;		
		conf=_conf;	
		tableMap = new HashMap<String, Table>();
	}	
	public SRuntime(WorkerAddrMap _addrMap, WorkerConnPool _workerConn, Config _conf) {
		// used by worker-node
		workerAddrMap = _addrMap;
		workerConn = _workerConn;		
		conf=_conf;
		if (workerConn!=null)
			sender = Sender.get(conf, workerAddrMap, workerConn);
		tableMap = new HashMap<String, Table>();
	}
	public void setVoid() { isVoid = true; }
	public boolean isVoid() { return isVoid; }
	
	public void setException(Throwable t) { exception = t; }
	public Throwable getException() { return exception; }
	
	public WorkerAddrMap getWorkerAddrMap() {
		return workerAddrMap;
	}
	public void setAddrMap(WorkerAddrMap _addrMap) {
		workerAddrMap = _addrMap;
	}
	public Sender sender() { return sender; }
	
	public Config getConf() { return conf; }
	
	public FastClassLookup getClassLookup() {
		if (classLookup==null) {
			classLookup = new FastClassLookup();
		}
		return classLookup;
	}
	public TableSliceMap getSliceMap() {
		if (sliceMap==null) {
			int sliceNum = conf.sliceNum();
			int virtualSliceNum = conf.virtualSliceNum();
			int minSliceSize = conf.minSliceSize();					
			
			if (conf.isDistributed()) {
				assert workerAddrMap != null;
				sliceMap = new DistTableSliceMap(workerAddrMap, sliceNum, virtualSliceNum, minSliceSize);
			} else {
				sliceMap = new TableSliceMap(sliceNum, virtualSliceNum, minSliceSize);
			}
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
		synchronized(builderMap) {			
			for (Rule r:rules) {
				builderMap.put(r.id(), builder);
			}
		}
	}
	public VisitorBuilder getVisitorBuilder(Rule rule) {
		synchronized(builderMap) {
			assert builderMap.contains(rule.id());
			return builderMap.get(rule.id());
		}
	}
	public VisitorBuilder getVisitorBuilder(int rule) {
		synchronized(builderMap) {
			assert builderMap.contains(rule);
			return builderMap.get(rule);
		}
	}
	
	public void update(Epoch e) {		
		assert tableMap!=null;
		createVisitorBuilderFor(e.getRules());
		exception = null;
		
		for (Table t:e.getNewTables()) {
			if (t.isCompiled())
				getSliceMap().addTable(t);
		}
			
		for (Table t:e.getAccessedTables()) {
			getSliceMap().addTable(t);
			if (t instanceof RemoteHeadTable ||
					t instanceof RemoteBodyTable) {
				Class<?> tableCls=TableUtil.load(t.className());
				assert tableCls!=null;
				getClassLookup().addClass(tableCls);
			}
			if (!(t instanceof GeneratedT))
				getLockMap().createLock(t);
		}
		
		addRuleMap(e.getRules(), e.getRuleMap());
		for (Rule r:e.getRules()) {
			if (r.isSimpleArrayInit()) continue;
			String visitorClsName = e.getVisitorClassName(r.id());
			if (visitorClsName!=null) {
				Class<?> visitorCls = Loader.forName(visitorClsName);
				getVisitorBuilder(r.id()).setVisitorClass(r.id(), visitorCls);
			}
		}
	}
	public void cleanup(Epoch e) {		
		for (Rule r:e.getRules()) {
			synchronized(rulemapMap) {
				rulemapMap.remove(r.id());
			}
		}
		for (Rule r:e.getRules()) {
			if (r.isSimpleArrayInit()) continue;
			String visitorClsName = e.getVisitorClassName(r.id());
			if (visitorClsName!=null) {
				synchronized(builderMap) {
					builderMap.remove(r.id());
				}
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
				map = old;
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
	static class MergeRuleMap {
		final public RuleMap map;
		MergeRuleMap(RuleMap old, RuleMap newMap) {
			map = new RuleMap();
			map.merge(old);
			map.merge(newMap);
		}
	}
	public void addRuleMap(List<Rule> rules, RuleMap rmap) {
		synchronized(rulemapMap) {
			for (Rule r:rules) {
				assert !rulemapMap.contains(r.id());
				rulemapMap.put(r.id(), rmap);
			}
		}
	}
	public RuleMap getRuleMap(int rule) {
		synchronized(rulemapMap) {
			assert rulemapMap.contains(rule);
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
