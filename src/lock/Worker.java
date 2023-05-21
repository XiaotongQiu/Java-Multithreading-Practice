package lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker  {
    private final Random random = new Random();

    // why use separate lock object instead of locking on list itself?
    // -> avoid any possible confusions
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private final List<Integer> list1 = new ArrayList<>();
    private final List<Integer> list2 = new ArrayList<>();


    // synchronized function cause blocking here
    // because both stageOne&stageTwo will acquire the same intrinsic lock of the Worker object
    // but actually it is not necessary
    // we can accept that t1 run stageOne and t2 run stageTwo at same time

//    public synchronized void stageOne() {
    public void stageOne() {
        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list1.add(random.nextInt(100));
        }
    }

//    public synchronized void stageTwo() {
    public void stageTwo() {
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list2.add(random.nextInt(100));
        }

    }

    public void process() {
        for (int i = 0; i < 1000; i++) {
            stageOne();
            stageTwo();
        }
    }

    public void main() {
        System.out.println("Starting ...");
        long start = System.currentTimeMillis();

        Thread t1 = new Thread(this::process);

        Thread t2 = new Thread(this::process);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long end = System.currentTimeMillis();

        System.out.println("Time taken: " + (end-start));
        System.out.println("List1: " + list1.size() + "; List2: " + list2.size());
    }
}
