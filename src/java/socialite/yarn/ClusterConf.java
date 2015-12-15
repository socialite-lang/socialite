package socialite.yarn;

import socialite.util.BitUtils;

/**
 * Created by jiwon on 9/10/15.
 */
public class ClusterConf {
    static ClusterConf inst = new ClusterConf();
    public static ClusterConf get() { return inst; }

    final int maxNumWorkers;
    int numWorkers;
    int numWorkerThreads;
    int workerHeapSize;

    ClusterConf() {
        numWorkers = Integer.parseInt(System.getProperty("socialite.worker.num", "2"));
        numWorkerThreads = Integer.parseInt(System.getProperty("socialite.worker.num_threads", "8"));
        workerHeapSize = Integer.parseInt(System.getProperty("socialite.worker.heap_size", "8192"));

        int max = Integer.parseInt(System.getProperty("socialite.worker.max", "-1"));
        max = Math.max(max, numWorkers*4);
        maxNumWorkers = BitUtils.nextHighestPowerOf2(max);
    }

    public int getNumWorkers() { return numWorkers; }
    public int getMaxNumWorkers() { return maxNumWorkers; }
    public int getNumWorkerThreads() { return numWorkerThreads; }
    public int getWorkerHeapSize() { return workerHeapSize; }
}
