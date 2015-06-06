package socialite.eval;

import java.lang.management.MemoryUsage;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.resource.SRuntime;
import socialite.tables.TmpTableInst;
import socialite.tables.TableInst;
import socialite.util.SociaLiteException;
import socialite.util.SoftRefArrayQueue;
import gnu.trove.map.hash.TIntObjectHashMap;
import socialite.util.WeakArrayQueue;
import java.lang.management.ManagementFactory;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TmpTablePool {
    public static final Log L= LogFactory.getLog(TmpTablePool.class);

    static Map<Class, WeakArrayQueue<TmpTableInst>> globalFreeTableList =
                        Collections.synchronizedMap(new WeakHashMap<Class, WeakArrayQueue<TmpTableInst>>());
    static Map<Class, WeakArrayQueue<TmpTableInst>> freeSmallTableList =
                        Collections.synchronizedMap(new WeakHashMap<Class, WeakArrayQueue<TmpTableInst>>());
    //static ReferenceQueue<TmpTableInst> refQueue = new ReferenceQueue<TmpTableInst>();

	static Map<Class, Method> tableAlloc = Collections.synchronizedMap(new WeakHashMap<Class, Method>());
    static Map<Class, Method> tableAllocSmall = Collections.synchronizedMap(new WeakHashMap<Class, Method>());
	static final int globalListSize=128+64+32;
	static final int smallListSize=1024+1024+256;
    static AtomicInteger allocKB=new AtomicInteger(0);
    static AtomicInteger urgencyWait=new AtomicInteger(0);
    static AtomicInteger urgencyWaitSmall=new AtomicInteger(0);
    static int maxAllocKB = -1;
    static int maxUrgentAllocKB = -1;
    static int maxRecvAllocKB = -1;

    /*static class TableCleanupRef extends WeakReference<TmpTableInst> {
        //final int myAllocSizeKB;
        public TableCleanupRef(TmpTableInst inst) {
            super(inst, refQueue);
            //myAllocSizeKB = (inst.totalAllocSize()+1023)/1024;
        }
        public void cleanup() {
            allocKB.addAndGet(-myAllocSizeKB);
        }
    }*/

    public static void clear() {
      //XXX: used by runGc(). Need to be called before and after System.gc()
      //     because allocKB is updated by gc (weak-ref-queue)
        globalFreeTableList.clear();
        freeSmallTableList.clear();
        tableAlloc.clear();
        tableAllocSmall.clear();

        allocKB.set(0);
        urgencyWait.set(0);
        urgencyWaitSmall.set(0);
    }
    public static void clear(Class klass) {
    	L.info("Clearing "+klass);
    	WeakArrayQueue<TmpTableInst> queue;
    	queue = globalFreeTableList.get(klass);
    	if (queue != null) { 
    		queue.clear();
    		globalFreeTableList.remove(klass);
    	}    	
    	queue = freeSmallTableList.get(klass);
    	if (queue != null) { 
    		queue.clear();
    		freeSmallTableList.remove(klass);
    	}
    }

	public static void init(int workerNum) {
        /*Thread weakRefMonitor = new Thread() {
            public void run() {
                while (true) {
                    try {
                        TableCleanupRef ref = (TableCleanupRef)refQueue.remove();
                        ref.cleanup();                        
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };
        weakRefMonitor.setDaemon(true);
        weakRefMonitor.start();*/
	}	

	static int tableid(Class tableCls) {		
		try {
			Method getId = tableCls.getMethod("tableid", new Class[]{});
			return (Integer)getId.invoke(null, (Object[])null);
		} catch (Exception e) {
			throw new SociaLiteException(e);
		}
	}
	public static TmpTableInst get(int workerid, Class tableCls, int priority) {
		TmpTableInst t = get(workerid, tableCls);		
		return t;
	}
	public static TmpTableInst _get(int workerid, Class tableCls, int priority) {
		TmpTableInst t = _get(workerid, tableCls);		
		return t;
	}
	public static TmpTableInst __get(int workerid, Class tableCls, int priority) {
		TmpTableInst t = __get(workerid, tableCls);		
		return t;
	}
	
	public static TmpTableInst get(int workerid, Class tableCls) {
		return get_global(0, tableCls);
	}
	public static TmpTableInst _get(int workerid, Class tableCls) {
		return get_global(1, tableCls);
	}	
	public static TmpTableInst __get(int workerid, Class tableCls) {
		return get_global(2, tableCls);
	}
	public static TmpTableInst get_args(int workerid, Class tableCls, Object... args) {
		return get_global(0, tableCls, args);
	}
	public static TmpTableInst _get_args(int workerid, Class tableCls, Object... args) {
		return get_global(1, tableCls, args);
	}
	public static TmpTableInst __get_args(int workerid, Class tableCls, Object... args) {
		return get_global(2, tableCls, args);
	}
	
	static int maxUrgentAllocKB() {
		if (maxUrgentAllocKB==-1) {
			float maxMemKB = (float)(SRuntime.maxMemory()/1024.0);
			maxUrgentAllocKB = (int)Math.min(maxMemKB*0.07f, 1024*1024*8/*max 8GB (in KB)*/);
			L.info(" NOTICE maxUrgentAlloc in MB:"+maxUrgentAllocKB/1024);
		}
		return maxUrgentAllocKB;
	}
	static int maxRecvAllocKB() {
		if (maxRecvAllocKB==-1) {
			maxRecvAllocKB = (int)(maxUrgentAllocKB()*0.8f);
			L.info(" NOTICE maxRecvAlloc:"+maxRecvAllocKB/1024+"MB");
		}
		return maxRecvAllocKB;
	}
	static int maxAllocKB() {
		if (maxAllocKB==-1) {
			maxAllocKB = (int)(maxRecvAllocKB()*0.7f);
			L.info(" NOTICE maxAlloc:"+maxAllocKB/1024+"MB");
		}
		return maxAllocKB;
	}
	
	static WeakArrayQueue<TmpTableInst> getQueue( Map<Class, WeakArrayQueue<TmpTableInst>> freeTableListMap, Class tableCls, int queueInitSize) {
		WeakArrayQueue<TmpTableInst> q; 
		q = freeTableListMap.get(tableCls);
		if (q==null) {
			synchronized(freeTableListMap) {
				WeakArrayQueue<TmpTableInst> prev = freeTableListMap.get(tableCls);
				if (prev == null) {
					q = new WeakArrayQueue<TmpTableInst>(queueInitSize);
					freeTableListMap.put(tableCls, q);
				} else { q = prev; }
			}
		}
		return q;
	}
	static WeakArrayQueue<TmpTableInst> getSmallQueueFromGlobal(Class tableCls) {
		return getQueue(freeSmallTableList, tableCls, smallListSize);
	}
	static WeakArrayQueue<TmpTableInst> getQueueFromGlobal(Class tableCls) {
		return getQueue(globalFreeTableList, tableCls, globalListSize);
	}
	
	public static TmpTableInst __get(Class tableCls) {
		return get_global(2, tableCls);
	}
	public static TmpTableInst _get(Class tableCls) {
		return get_global(1, tableCls);
	}	
	public static TmpTableInst get(Class tableCls, Object... args) {
		return get_global(0, tableCls, args);
	}

    static MemoryUsage heapMemUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
    static long freeMemory() {
        long free = heapMemUsage.getMax() - heapMemUsage.getUsed() - 256*1024*1024;
        if (free < 0) free = 0;
        return free;
        /*if (heapMemUsage.getMax() > heapMemUsage.getCommitted()*3/2) {
            return heapMemUsage.getMax() - heapMemUsage.getUsed();
        }
        return heapMemUsage.getCommitted() - heapMemUsage.getUsed();*/
    }
	static TmpTableInst get_global(int urgency, Class tableCls, Object... args) {
		WeakArrayQueue<TmpTableInst> q = getQueueFromGlobal(tableCls);
        TmpTableInst t;
        int waitTime=2, maxTry=1000000;
        if (urgency==1) maxTry=50;
        if (urgency==2) maxTry=40;

        long waitStart = 0;
        int tryCnt = 0;
        boolean urgencyWaitIncremented = false;
        try {
            do {
                t = null;
                synchronized (q) {
                    if (tryCnt == 0) {
                        t = q.dequeue();
                    } else if (urgency >= 1) {
                        t = q.dequeue();
                    } else if (urgencyWait.get() < 3) {
                        t = q.dequeue();
                    }
                    if (t == null && q.size() > 3) {
                        t = q.dequeue();
                    }
                }
                if (t != null) {
                    assert t.isEmpty();
                    return t;
                }

                long freeMem = freeMemory();
                if (freeMem > 1024 * 1024 * (1024+1024+512)) { break;}
                if (urgency >= 1 && freeMem > 1024 * 1024 * (1024+512+256)) { break; }
                if (urgency >= 2 && freeMem > 1024 * 1024 * (1024+512)) { break; }
                if (tryCnt > maxTry) { break;}
                synchronized (q) {
                    if (urgency >= 1 && !urgencyWaitIncremented) {
                        urgencyWait.incrementAndGet();
                        urgencyWaitIncremented = true;
                    }
                    try { q.wait(waitTime); }
                    catch (InterruptedException e) { throw new SociaLiteException(e); }
                }
                long now = System.currentTimeMillis();
                if (now - waitStart >= 1) {
                    tryCnt++;
                    waitStart = now;
                }
            } while (true);
        } finally {
            if (urgencyWaitIncremented) {
                urgencyWait.decrementAndGet();
            }
        }
		
		t = alloc(tableCls, args);
		return t;
	}
	
	public static void free(int workerid, TableInst[] inst) {
		if (inst==null) return;		
		for (int i=0; i<inst.length; i++) {
			if (inst[i]!=null) free(workerid, (TmpTableInst)inst[i]);
		}
	}
	public static void free(int workerid, TmpTableInst inst) {
		free(inst);
	}
	public static void free(TmpTableInst inst) {
		if (inst==null) return;
		if (!inst.reuse()) {
			forget(inst);
			return;
		}
		
		if (inst.capacity() < inst.ordinaryCapacity()) {
			freeSmall(inst);
			return;
		}
		WeakArrayQueue<TmpTableInst> q = getQueueFromGlobal(inst.getClass());
		assert !q.contains(inst):"queue already has :"+System.identityHashCode(inst);
		
		synchronized(q) {
			if (q.size() < globalListSize) {		
				inst.clear();
				q.add(new WeakReference<TmpTableInst>(inst));
			} else {
				forget(inst);
			}
			q.notifyAll();
		}
	}	
	public static void forget(TmpTableInst inst) {
		allocKB.addAndGet(-(inst.totalAllocSize()+1023)/1024);
	}
	public static void free(TmpTableInst[] inst) {
		if (inst==null) return;
		for (int i=0; i<inst.length; i++) {
			if (inst[i]!=null) free(inst[i]);
		}
	}

	public static void status() {
		System.out.println(" NOTICE TmpTable allocMB:"+allocKB.get()/1024);
	}
	
	static TmpTableInst alloc(Class tableCls, Object... args) {
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
			TmpTableInst t = (TmpTableInst)alloc.invoke(null, (Object[])args);
			allocKB.addAndGet((t.totalAllocSize()+1023)/1024);
			return t;
		} catch (Exception e) {
            throw new SociaLiteException(e);
		}
	}	
	
	public static TmpTableInst __getSmall(Class tableCls) {
		return getSmall(2, tableCls);
	}
	public static TmpTableInst _getSmall(Class tableCls) {
		return getSmall(1, tableCls);
	}
	public static TmpTableInst getSmall(Class tableCls) {
		return getSmall(0, tableCls);
	}	
	public static TmpTableInst getSmall(int urgency, Class tableCls) {
		WeakArrayQueue<TmpTableInst> q = getSmallQueueFromGlobal(tableCls);
		TmpTableInst t;
        int waitTime=2, maxTry=1000000;
        if (urgency==1) maxTry=50;
        if (urgency==2) maxTry=40;

        long waitStart = 0;
        int tryCnt = 0;
        boolean urgencyWaitIncremented = false;
        try {
            do {
                t = null;
                synchronized (q) {
                    if (tryCnt == 0) {
                        t = q.dequeue();
                    } else if (urgency >= 1) {
                        t = q.dequeue();
                    } else if (urgencyWaitSmall.get() < 3) {
                        t = q.dequeue();
                    }
                    if (t == null && q.size() > 3) {
                        t = q.dequeue();
                    }
                }
                if (t != null) {
                    assert t.isEmpty() : "Table[" + t.id() + "] is not empty.";
                    return t;
                }

                long freeMem = freeMemory();
                if (freeMem > 1024 * 1024 * (1024+1024+512)) { break;}
                if (urgency >= 1 && freeMem > 1024 * 1024 * (1024+512+256)) { break; }
                if (urgency >= 2 && freeMem > 1024 * 1024 * (1024+512)) { break; }
                if (tryCnt > maxTry) { break; }
                synchronized (q) {
                    if (urgency >= 1 && !urgencyWaitIncremented) {
                        urgencyWaitSmall.incrementAndGet();
                        urgencyWaitIncremented = true;
                    }
                    try { q.wait(waitTime); }
                    catch (InterruptedException e) { throw new SociaLiteException(e); }
                }
                long now = System.currentTimeMillis();
                if (now - waitStart >= 1) {
                    tryCnt++;
                    waitStart = now;
                }
            } while (true);
        } finally {
            if (urgencyWaitIncremented) {
                urgencyWaitSmall.decrementAndGet();
            }
        }

		t = allocSmall(tableCls);
		return t;
	}	
	public static void freeSmall(TmpTableInst inst) {
		if (inst==null) return;		
		if (!inst.reuse()) {
			forget(inst);
			return;
		}
		
		WeakArrayQueue<TmpTableInst> q = getSmallQueueFromGlobal(inst.getClass());
		assert !q.contains(inst);			
		
		synchronized(q) {
			if (q.size() < smallListSize) {
				inst.clear();
				q.add(new WeakReference<TmpTableInst>(inst));
			} else {
				forget(inst);
			}
			q.notifyAll();
		}
	}
	static TmpTableInst allocSmall(Class tableCls) {
		try {
			Method alloc = tableAllocSmall.get(tableCls);
			if (alloc==null) {
				Class[] argTypes=null;				
				alloc = tableCls.getMethod("createSmall", argTypes);
				tableAllocSmall.put(tableCls, alloc);
			}
			TmpTableInst t=(TmpTableInst)alloc.invoke(null, (Object[])null);
			allocKB.addAndGet((t.totalAllocSize()+1023)/1024);
			return t;
		} catch (Exception e) {			
			throw new SociaLiteException(e);
		}
	}
}
