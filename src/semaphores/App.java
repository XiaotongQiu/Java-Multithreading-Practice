package semaphores;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 200; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    Connection.getInstance().connect();
                }
            });
        }

        executor.shutdown();

        executor.awaitTermination(1, TimeUnit.DAYS);

        // used to control access to resource
        // init with 1 permit
//        Semaphore sem = new Semaphore(1);

        // increase number of permit
//        sem.release();

        // decr/use one permit
//        sem.acquire();

        // if there's no permit available
        // acquire will wait till some become available
//        sem.acquire();
//        sem.acquire();

//        System.out.println("Available permits: " + sem.availablePermits());
    }
}
