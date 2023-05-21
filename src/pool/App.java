package pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable {
    private final int id;
    public Processor(int id) {
        this.id = id;
    }
    @Override
    public void run() {
        System.out.println("Starting: " + id);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Completed: " + id);
    }
}
public class App {
    public static void main(String[] args) {
        // recycling threads in using thread pool to avoid overhead of creating new threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {
            executor.submit(new Processor(i));
        }

        // close for task submission
        // won't shut down immediately
        // shutdown when all tasks completed
        executor.shutdown();

        // re-submit after shutting down will cause exception:
        // Exception in thread "main" java.util.concurrent.RejectedExecutionException:
        // Task java.util.concurrent.FutureTask@135fbaa4
        // rejected from java.util.concurrent.ThreadPoolExecutor@45ee12a7[Shutting down, pool size = 2, active threads = 2, queued tasks = 3, completed tasks = 0]
//        for (int i= 5; i < 10; i++) {
//            executor.submit(new Processor(i));
//        }

        System.out.println("All tasks submitted");

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("All tasks completed");
    }
}
