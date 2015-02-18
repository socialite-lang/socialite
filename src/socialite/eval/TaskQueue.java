package socialite.eval;

import socialite.dist.EvalRefCount;
import socialite.util.ArrayQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

enum Priority {
	Top(0),
    High(1),
    Normal(2);
	private static int numLevels = values().length;
	public static int numLevels() { return numLevels; }
	
	private int value;
	private Priority(int val) { value = val; }
	public int value() { return value; }
}
public class TaskQueue {
    static final int MAX_LEVEL=Priority.numLevels();
    static final int DEFAULT_CAPACITY=256;

    final ReentrantLock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    ArrayQueue<Task> recvQ;
    List<ArrayQueue<Task>> queues;
    List<Task> reservedTasks;

    public TaskQueue() {
        queues = new ArrayList<ArrayQueue<Task>>(Priority.numLevels());
        for (int i=0; i<Priority.numLevels(); i++) {
            queues.add(new ArrayQueue<Task>(DEFAULT_CAPACITY));
        }
        reservedTasks = Collections.synchronizedList(new ArrayList<Task>());
    }

    public void add(Priority priority, Task task) {
        EvalRefCount.getInst().inc(task.getEpochId());

        if (priority.value() >= Priority.numLevels())
        	priority = Priority.Normal;
        ArrayQueue<Task> q = queues.get(priority.value());
        task.setPriority(priority.value());

        lock.lock();
        try {
            q.add(task);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
    public void addAll(Priority priority, Task[] tasks) {
        EvalRefCount evalRefCount = EvalRefCount.getInst();
        int taskCount=0, epochId=-1;
        for (Task t:tasks) {
            if (t==null) continue;
            assert epochId==-1 || epochId == t.getEpochId();
            epochId = t.getEpochId();
            taskCount++;
        }
        if (taskCount>0) evalRefCount.incBy(epochId, taskCount);

        if (priority.value() >= Priority.numLevels()) 
        	priority = Priority.Normal;
        ArrayQueue<Task> q = queues.get(priority.value());

        lock.lock();
        try {
            for (Task t:tasks) {
                if (t==null) continue;
                t.setPriority(priority.value());
                q.add(t);
            }
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void empty() {
        lock.lock();
        try {
            for (ArrayQueue<Task> q:queues) {
                q.clear();
            }
            notFull.signal();
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            for (ArrayQueue<Task> q:queues) {
                if (!q.isEmpty()) return false;
            }
            return true;
        } finally {
            lock.unlock();
        }
    }
    
    Random r = new Random();
    Task stealTask(int level) {
    	TaskQueue[] workerQueues = Worker.getWorkerQueues();
    	int workerNum = workerQueues.length;
    	int workerId = r.nextInt(workerNum);
    	Task task = null;
    	for (int i=0; i<workerNum; i++) {
    		TaskQueue workerQueue = workerQueues[workerId];
    		ArrayQueue<Task> queue = workerQueue.queues.get(level);
    		if (!queue.isEmpty()) {
    			if (workerQueue.lock.tryLock()) {
    				try { task = queue.get(); } 
        			finally { workerQueue.lock.unlock(); }
        			if (task != null) 
        				return task;
    			}
    		}
    		workerId = (workerId+1) % workerNum;
    	}
    	return null;
    }
    Task reserveReally() throws InterruptedException {
    	return reserveReally(Priority.numLevels());
    }
    Task reserveReally(int threshold) throws InterruptedException {
        Task t=null;
    	
        lock.lock();
        try {
            while (true) {
            	for (int i = Priority.Top.value(); i < threshold; i++) {
                    ArrayQueue<Task> q=queues.get(i);
                    t = q.get();
                    if (t == null) {
                    	lock.unlock();
                    	try { t = stealTask(i); }
                    	finally { lock.lock(); }
                    	if (t == null) continue;
                    }
                    reservedTasks.add(t);
                    return t;
                }                
                notEmpty.await(400, TimeUnit.MICROSECONDS);                
            }
        } finally {
            lock.unlock();
        }
    }
    public boolean isWaitingForTasks() {
    	return lock.hasWaiters(notEmpty);
    }
    public Task reserve() throws InterruptedException {
        return reserveReally();
    }
    public Task reservePrioritized() throws InterruptedException {
        return reserveReally(Priority.High.value());
    }

    public void pop(Task task) {
        EvalRefCount.getInst().dec(task.getEpochId());
        reservedTasks.remove(task);
    }
}
