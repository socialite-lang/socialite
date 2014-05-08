package socialite.eval;

public class WorkerForUrgentTask  extends Worker {

	WorkerForUrgentTask(int _id) {
		super(_id);
	}
	
	boolean noUrgentTask() {
		int tasknum=0;
		for (int i=0; i<workerQueues.length; i++) {
			LocalQueue q = workerQueues[i];
			if (!q.likelyEmptyAt(0))
				return false;
		}
		return true;
	}
	@Override
	Task reserveTask(int level) {
		assert level==0;
		Task task = localQ.reserveQuick(level);
		if (task!=null) return task;
		
		if (noUrgentTask())
			return null;
		
		task = tryStealTask(level);
		if (task!=null) return task;
		return null;
	}

	@Override
	Task reserveTask() throws InterruptedException {
		Task task = null;
		int wait = 2;
		while (true) {
			task = reserveTask(0);
			if (task!=null) {
				wait = 2;
				return task;
			}
			
			assert localQ.size(1)==0;
			assert localQ.size(0)==0;
			Thread.sleep(wait);
			if (wait < 16) wait = wait*2;
		}		
	}
}
