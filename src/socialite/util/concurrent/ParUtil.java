package socialite.util.concurrent;


import java.util.concurrent.Callable;

public class ParUtil {

    public interface CallableBlock {
        void call(int index);
    }
    public static void parallel(int numThreads, final CallableBlock call) {
        Thread[] threads = new Thread[numThreads];
        for (int i=0; i<numThreads; i++) {
            final int index = i;
            threads[i] = new Thread("Thread #"+i) {
                public void run() {
                    call.call(index);
                }
            };
        }

        for (Thread t:threads) {
            t.start();
        }

        for (Thread t:threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
