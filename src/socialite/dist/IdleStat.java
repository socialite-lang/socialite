package socialite.dist;

import socialite.engine.Config;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class IdleStat {
    AtomicIntegerArray lastIdleTimestamps;
    AtomicInteger idleWorkerNum;

    public IdleStat() {
        int workerNum = Config.getWorkerNodeNum();
        idleWorkerNum = new AtomicInteger(0);

        int[] ts = new int[workerNum];
        Arrays.fill(ts, -1);
        lastIdleTimestamps = new AtomicIntegerArray(ts);
    }
    public void update(int workerid, int timestamp) {
        if (lastIdleTimestamps.compareAndSet(workerid, -1, timestamp)) {
            idleWorkerNum.incrementAndGet();
        } else {
            while (true) {
                int prev = lastIdleTimestamps.get(workerid);
                if (prev > timestamp) break;
                boolean success=lastIdleTimestamps.compareAndSet(workerid, prev, timestamp);
                if (success) break;
            }
        }
    }
    public boolean allIdle() {
        return idleWorkerNum.get()==lastIdleTimestamps.length();
    }
    public int[] getIdleTimestamps() {
        int[] ts = new int[lastIdleTimestamps.length()];
        for (int i=0; i<ts.length; i++) {
            ts[i] = lastIdleTimestamps.get(i);
        }
        return ts;
    }
}