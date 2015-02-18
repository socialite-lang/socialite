package socialite.eval;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WorkerForUrgentTask extends Worker {
	public static final Log L=LogFactory.getLog(WorkerForUrgentTask.class);

	WorkerForUrgentTask(int _id) {
		super(_id);
	}
	Task reserveTask() throws InterruptedException {
        return taskQ.reservePrioritized();
	}
}
