package socialite.tables;

import socialite.eval.Worker;
import socialite.tables.TableInst;

public abstract class Joiner implements Runnable {
	protected Worker worker = null;
    private boolean priority = false;

    public void incPriority() {
        priority = true;
    }
    public boolean hasPriority() {
        return priority;
    }

    public void setWorker(Worker w) { worker = w; }
    public Worker getWorker() { return worker; }

    public int getWorkerId() { return worker.id(); }

    public int getEpochId() { return -1; }

    public int getRuleId() { return -1; }

    public TableInst[] getDeltaTables() { return null; }
    public TableInst[] getPrivateTable() { return null; }
}
