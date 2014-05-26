package socialite.eval;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import socialite.resource.SRuntime;
import socialite.tables.TableInst;
import socialite.util.SociaLiteException;
import socialite.util.SoftRefArrayQueue;
import gnu.trove.map.hash.TIntObjectHashMap;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TmpTablePool {
	static ConcurrentHashMap<Class, SoftRefArrayQueue<TableInst>> globalFreeTableList;
	static ConcurrentHashMap<Class, SoftRefArrayQueue<TableInst>> freeSmallTableList;
	static ConcurrentHashMap<Class, SoftRefArrayQueue<TableInst>>[] localFreeTableList; // indexed by workerid
	static TIntObjectHashMap<TableInst>[] accessedTables; // indexed by workerid
	static Map<Class, Method> tableAlloc, tableAllocSmall;
	static final int localListSize=4; 
	static final int globalListSize=128;
	static final int smallListSize=512;
    static int realWorkerNum; // number of workers except urgentWorkers
    static AtomicInteger allocKB=new AtomicInteger(0);    
    static int maxAllocKB = -1;
    static int maxUrgentAllocKB = -1;
    static int maxRecvAllocKB = -1;

	public static void setRealWorkerNum(int workerNum) {
		realWorkerNum = workerNum;
	}
	
	
	public static void init(int workerNum) {
		globalFreeTableList = new ConcurrentHashMap<Class, SoftRefArrayQueue<TableInst>>();		
		freeSmallTableList = new ConcurrentHashMap<Class, SoftRefArrayQueue<TableInst>>();
		
		tableAlloc = new HashMap<Class, Method>();
		tableAlloc = (Map<Class, Method>)Collections.synchronizedMap(tableAlloc);
		tableAllocSmall = new HashMap<Class, Method>();
		tableAllocSmall = (Map<Class, Method>)Collections.synchronizedMap(tableAllocSmall);

		localFreeTableList = new ConcurrentHashMap[workerNum];
		accessedTables = new TIntObjectHashMap[workerNum];
		for (int i=0; i<workerNum; i++) {
			localFreeTableList[i] = new ConcurrentHashMap<Class, SoftRefArrayQueue<TableInst>>();
			accessedTables[i] = new TIntObjectHashMap<TableInst>();
		}
	}	
	public static void freeAll() {		
		globalFreeTableList.clear();
		freeSmallTableList.clear();
		tableAlloc.clear();
		tableAllocSmall.clear();
		for (int i=0; i<localFreeTableList.length; i++) {
			localFreeTableList[i].clear();
			accessedTables[i].clear();
		}
		allocKB.set(0);
		maxAllocKB=-1;
		maxRecvAllocKB=-1;
		maxUrgentAllocKB=-1;
	}
	static int key(int tableid, int priority) {
		return tableid << 12 | priority;
	}
	public static void invalidate(int workerid, TableInst _t, int priority) {
		if (!_t.isAccessed()) return;
		int key = key(_t.id(), priority);
		/*TableInst t = accessedTables[workerid].get(key);
		assert t==null || t==_t;
		if (t==null) {
			_t.setAccessed(false);
			return;
		} */
		accessedTables[workerid].put(key, null);
		_t.setAccessed(false);
	}
	
	static int tableid(Class tableCls) {		
		try {
			Method getId = tableCls.getMethod("tableid", new Class[]{});
			return (Integer)getId.invoke(null, (Object[])null);
		} catch (Exception e) {
			throw new SociaLiteException(e);
		}
	}
	public static TableInst get(int workerid, Class tableCls, int priority) {
		int tableid=tableid(tableCls);		
		int key = key(tableid, priority);
		TableInst t = accessedTables[workerid].get(key);
		if (t!=null) return t;
		
		t = get(workerid, tableCls);		
		accessedTables[workerid].put(key, t);
		t.setAccessed(true);
		return t;
	}
	public static TableInst _get(int workerid, Class tableCls, int priority) {
		int tableid=tableid(tableCls);		
		int key = key(tableid, priority);
		TableInst t = accessedTables[workerid].get(key);
		if (t!=null) return t;
		
		t = _get(workerid, tableCls);		
		accessedTables[workerid].put(key, t);
		t.setAccessed(true);
		return t;
	}
	public static TableInst __get(int workerid, Class tableCls, int priority) {
		int tableid=tableid(tableCls);		
		int key = key(tableid, priority);
		TableInst t = accessedTables[workerid].get(key);
		if (t!=null) return t;
		
		t = __get(workerid, tableCls);		
		accessedTables[workerid].put(key, t);
		t.setAccessed(true);
		return t;
	}
	
	public static TableInst get(int workerid, Class tableCls) {
		return get_args(workerid, tableCls);
	}
	public static TableInst _get(int workerid, Class tableCls) {
		return get_args(1, workerid, tableCls);
	}	
	public static TableInst __get(int workerid, Class tableCls) {
		return get_args(2, workerid, tableCls);
	}
	public static TableInst get_args(int workerid, Class tableCls, Object... args) {
		return get_args(0, workerid, tableCls, args);
	}
	public static TableInst _get_args(int workerid, Class tableCls, Object... args) {
		return get_args(1, workerid, tableCls, args);
	}
	public static TableInst __get_args(int workerid, Class tableCls, Object... args) {
		return get_args(2, workerid, tableCls, args);
	}
	public static TableInst get_args(int urgency, int workerid, Class tableCls, Object... args) {
		SoftRefArrayQueue<TableInst> q = (SoftRefArrayQueue)localFreeTableList[workerid].get(tableCls);
		if (q==null) {			
			q = new SoftRefArrayQueue<TableInst>(localListSize);
			SoftRefArrayQueue<TableInst> prevQ = localFreeTableList[workerid].putIfAbsent(tableCls, q);
			q = (prevQ==null)? q:prevQ;
		}
		SoftRefArrayQueue<TableInst> q2=getQueueFromGlobal(tableCls);
		int waitTime=2;
		TableInst t;
		do {			
			synchronized(q) { t = q.dequeue(); }
			if (t!=null) {
				assert t.isEmpty():tableCls.getSimpleName()+" not empty, size:"+t.size();
				return t;
			}			
			synchronized(q2) { t = q2.dequeue(); }
			if (t!=null) {
				assert t.isEmpty():tableCls.getSimpleName()+" not empty, size:"+t.size();
				return t;
			}
			int maxAlloc = maxAllocKB();
			if (urgency==1) maxAlloc = maxRecvAllocKB();			
			if (urgency==2) maxAlloc = maxUrgentAllocKB();
			if (allocKB.get() < maxAlloc) break;
			
			synchronized(q2) {
				try { q2.wait(waitTime); } 
				catch (InterruptedException e) { throw new SociaLiteException(e); }	
			}							
			if (waitTime<16) waitTime*=2;
		} while (true);

		t = alloc(tableCls, args); // allocate new table object
		t.setRequireFree(true);
		return t;
	}	
	
	static int maxUrgentAllocKB() {
		if (maxUrgentAllocKB==-1) {
			float availableKB = (int)(SRuntime.freeMemory()/1024.0);
			maxUrgentAllocKB = (int)(availableKB*0.3f);
			//System.out.println(" NOTICE max urgent Alloc in MB:"+maxUrgentAllocKB/1024);
		}
		return maxUrgentAllocKB;
	}
	static int maxRecvAllocKB() {
		if (maxRecvAllocKB==-1) {
			float availableKB = (int)(SRuntime.freeMemory()/1024.0);
			maxRecvAllocKB = (int)(availableKB*0.3f);			
			//System.out.println(" NOTICE max recv Alloc in MB:"+maxRecvAllocKB/1024);
		}
		return maxRecvAllocKB;
	}
	static int maxAllocKB() {
		if (maxAllocKB==-1) {
			float availableKB = (int)(SRuntime.freeMemory()/1024.0);
			maxAllocKB = (int)(availableKB*0.15f);
			//System.out.println(" NOTICE maxAlloc in MB:"+maxAllocKB/1024);
			maxRecvAllocKB();
			maxUrgentAllocKB();
		}
		return maxAllocKB;
	}
	static SoftRefArrayQueue<TableInst> getSmallQueueFromGlobal(Class tableCls) {
		SoftRefArrayQueue<TableInst> q; 
		q = freeSmallTableList.get(tableCls);
		if (q==null) {
			q = new SoftRefArrayQueue<TableInst>(smallListSize);
			SoftRefArrayQueue<TableInst> prevQ=freeSmallTableList.putIfAbsent(tableCls, q);
			q = (prevQ==null)?q:prevQ;
		}
		return q;
	}
	static SoftRefArrayQueue<TableInst> getQueueFromGlobal(Class tableCls) {
		SoftRefArrayQueue<TableInst> q; 
		q = globalFreeTableList.get(tableCls);
		if (q==null) {
			q = new SoftRefArrayQueue<TableInst>(globalListSize);
			SoftRefArrayQueue<TableInst> prevQ = globalFreeTableList.putIfAbsent(tableCls, q);
			q = (prevQ==null)?q:prevQ;
		}
		return q;
	}
	
	public static TableInst __get(Class tableCls) {
		return get_global(2, tableCls);
	}
	public static TableInst _get(Class tableCls) {
		return get_global(1, tableCls);
	}	
	public static TableInst get(Class tableCls, Object... args) {
		return get_global(0, tableCls, args);
	}
	static TableInst get_global(int urgency, Class tableCls, Object... args) {
		SoftRefArrayQueue<TableInst> q = getQueueFromGlobal(tableCls);
		TableInst t=null;
		int waitTime=2;
		do {
			synchronized(q) { t = q.dequeue(); }
			if (t!=null) {
				assert t.isEmpty():"Table["+t.id()+"] size:"+t.size();
				return t;
			}
			int maxAlloc=maxAllocKB();
			if (urgency==1) maxAlloc = maxRecvAllocKB();
			if (urgency==2) maxAlloc = maxUrgentAllocKB();
			if (allocKB.get() < maxAlloc) break;
			
			synchronized(q) {
				try { q.wait(waitTime); }
				catch (InterruptedException e) {throw new SociaLiteException(e);}
			}
			if (waitTime<16) waitTime*=2;
		} while (true);
		
		t = alloc(tableCls, args);
		return t;
	}
	
	public static void free(int workerid, TableInst[] inst) {
		if (inst==null) return;		
		for (int i=0; i<inst.length; i++) {
			if (inst[i]!=null) free(workerid, inst[i]);
		}
	}
	public static void free(int workerid, TableInst inst) {
		if (inst==null) return;
        assert inst.requireFree();
		if (inst.requireFreeSmall()) {
			freeSmall(inst);
			return;
		}		
		
		SoftRefArrayQueue<TableInst> q = (SoftRefArrayQueue)localFreeTableList[workerid].get(inst.getClass());
		if (q==null) {
			q = new SoftRefArrayQueue<TableInst>(localListSize);
			SoftRefArrayQueue<TableInst> prevQ = localFreeTableList[workerid].putIfAbsent(inst.getClass(), q);
			q = (prevQ==null)? q:prevQ;
		}
		assert !q.contains(inst):"queue already has :"+System.identityHashCode(inst);
		synchronized(q) {
			if (q.size() < localListSize) {
				inst.clear();
				q.add(inst);
				q.notifyAll();
				return;
			}
		}
		free(inst);
	}
	
	public static void free(TableInst[] inst) {
		if (inst==null) return;
		for (int i=0; i<inst.length; i++) {
			if (inst[i]!=null) free(inst[i]);
		}
	}
	public static void free(TableInst inst) {
		if (inst==null) return;
		
		if (inst.requireFreeSmall()) {
			freeSmall(inst);
			return;
		}
		assert inst.requireFree():"Table["+inst.id()+"].requireFree() false";

		SoftRefArrayQueue<TableInst> q=getQueueFromGlobal(inst.getClass());	
		boolean added=false;
		synchronized(q) {
			if (q.size() < globalListSize) {
				assert !q.contains(inst);
				inst.clear();
				q.add(inst);
				added=true;
			}
		}		
		if (!added) { forget(inst); }
		
		synchronized(q) { q.notifyAll(); }
	}
	public static void forget(TableInst inst) {
		assert inst!=null;
		allocKB.addAndGet(-(inst.totalAllocSize()+1023)/1024);
	}
	
	public static void status() {
		System.out.println(" NOTICE TmpTable allocMB:"+allocKB.get()/1024);
	}
	
	static TableInst alloc(Class tableCls, Object... args) {
		try {
			Method alloc = tableAlloc.get(tableCls);
			if (alloc==null) {
				Class[] argTypes=null;
				if (args.length!=0) {
					argTypes = new Class[args.length];
					for (int i=0; i<argTypes.length; i++)
						argTypes[i] = args[i].getClass();
				}
				alloc = tableCls.getMethod("create", argTypes);
				tableAlloc.put(tableCls, alloc);
			}
			TableInst t = (TableInst)alloc.invoke(null, (Object[])args);
			allocKB.addAndGet((t.totalAllocSize()+1023)/1024);
			t.setRequireFree(true);
			return t;
		} catch (Exception e) {
			throw new SociaLiteException(e);
		}
	}	
	
	public static TableInst __getSmall(Class tableCls) {
		return getSmall(2, tableCls);
	}
	public static TableInst _getSmall(Class tableCls) {
		return getSmall(1, tableCls);
	}
	public static TableInst getSmall(Class tableCls) {
		return getSmall(0, tableCls);
	}	
	public static TableInst getSmall(int urgency, Class tableCls) {
		TableInst t=null;
		int waitTime=2;
		
		SoftRefArrayQueue<TableInst> q = getSmallQueueFromGlobal(tableCls);
		do {
			synchronized(q) { t = q.dequeue(); }
			if (t!=null) {
				assert t.isEmpty():"Table["+t.id()+"] size:"+t.size();
				return t;
			}
			int maxAlloc = maxAllocKB();
			if (urgency==1) maxAlloc = maxRecvAllocKB();
			if (urgency==2) maxAlloc = maxUrgentAllocKB();
			if (allocKB.get() < maxAlloc) break;
			
			synchronized(q) {
				try { q.wait(waitTime); }
				catch (InterruptedException e) { throw new SociaLiteException(e); }
			}				
			if (waitTime<16) waitTime*=2;			
		} while (true);
				
		t = allocSmall(tableCls);
		return t;
	}	
	public static void freeSmall(TableInst inst) {
		if (inst==null) return;
		
		assert inst.requireFree():"Table["+inst.id()+"].requireFree() false";

		SoftRefArrayQueue<TableInst> q = getSmallQueueFromGlobal(inst.getClass());
		boolean added=false;
		synchronized(q) {
			if (q.size() < smallListSize) {
				assert !q.contains(inst);			
				inst.clear();
				q.add(inst);
				added=true;
			}
		}
		if (!added) { forget(inst); }
		synchronized(q) { q.notifyAll(); }
	}
	static TableInst allocSmall(Class tableCls) {
		try {
			Method alloc = tableAllocSmall.get(tableCls);
			if (alloc==null) {
				Class[] argTypes=null;				
				alloc = tableCls.getMethod("createSmall", argTypes);
				tableAllocSmall.put(tableCls, alloc);
			}
			TableInst t=(TableInst)alloc.invoke(null, (Object[])null);
			t.setRequireFree(true);
			t.setRequireFreeSmall(true);
			allocKB.addAndGet((t.totalAllocSize()+1023)/1024);
			return t;
		} catch (Exception e) {			
			throw new SociaLiteException(e);
		}
	}
}
