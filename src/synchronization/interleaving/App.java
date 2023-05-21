package synchronization.interleaving;

public class App {

    private int count = 0;

    // evey object in Java has an intrinsic lock
    // when calling synchronized method
    // you have to acquire the lock before calling it
    // only one thread can acquire the intrinsic lock at the given time

    public synchronized void incremnet() {
        // also guarantee the latest state is visible to all threads
        count++;
    }
    public static void main(String[] args) {
        App app = new App();
        app.doWork();
    }

    public void doWork() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
//                    count++;
                    incremnet();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    // not atomic!
                    // read-incr-write 3 steps
//                    count++;
                    incremnet();
                }
            }
        });

        t1.start();
        t2.start();

        // wait till both t1&t2 to finish
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Count is " + count);
    }
}
