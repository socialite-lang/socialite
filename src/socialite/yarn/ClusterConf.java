package socialite.yarn;

/**
 * Created by jiwon on 9/10/15.
 */
public class ClusterConf {
    static ClusterConf inst = new ClusterConf();
    public static ClusterConf get() { return inst; }

    int numWorkers;
    int numWorkerThreads;
    int workerHeapSize;

    ClusterConf() {
        numWorkers = Integer.parseInt(System.getProperty("socialite.worker.num", "2"));
        numWorkerThreads = Integer.parseInt(System.getProperty("socialite.worker.num_threads", "8"));
        workerHeapSize = Integer.parseInt(System.getProperty("socialite.worker.heap_size", "4096"));
    }

    public void incWorkers(int num) {
        numWorkers += num;
    }
    public int getNumWorkers() { return numWorkers; }
    public int getNumWorkerThreads() { return numWorkerThreads; }
    public int getWorkerHeapSize() { return workerHeapSize; }
}
