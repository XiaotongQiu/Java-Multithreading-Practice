package countdownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Processor implements Runnable {
    private CountDownLatch latch;
    private int id;

    public Processor(CountDownLatch latch, int id) {
        this.latch = latch;
        this.id = id;
    }
    @Override
    public void run() {
        System.out.println("Started. " + id);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // count down by 1
        latch.countDown();
    }
}

public class App {
    public static void main(String[] args) {
        // thread wait for counting down from number specified till zero
        CountDownLatch latch = new CountDownLatch(3);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 3; i++) {
            executor.submit(new Processor(latch, i));
        }
        // remember to shut it down!
        executor.shutdown();

        try {
            // wait till count to 0
            // this will block code here s.t. we don't need executro.await()
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Completed.");
    }
}
