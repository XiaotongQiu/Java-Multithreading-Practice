package reentrantLock;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {

    private int count = 0;

    // if one thread is locked on this reentrant lock
    // it can lock again if wanted
    // the number of time of locking will be recorded
    // and you need to unlock it by the same number of time to unlock it

    private Lock lock = new ReentrantLock();

    // to use signal() & await()
    // i.e. notify&wait
    // we need Condition object, which can be obtained from lock
    private Condition cond = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }
    public void firstThread() throws InterruptedException {
        // similar to synchronize
        // but this fashion is not good
        // if some exception happens in increment, the lock might not be unlocked
        // use try finally instead! it guarantees the unlock() will always be called
//        lock.lock();
//        increment();
//        lock.unlock();

        lock.lock();
        System.out.println("Waiting ....");
        // to use signal/await
        // we need the lock first, very similar to synchronize block and notify/wait
        cond.await(); // head over the lock

        System.out.println("Woken up!");

        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();

        System.out.println("Press the return key!");
        new Scanner(System.in).nextLine();
        System.out.println("Got the return key!");

        // this will wake up the awaiting thread
        // but this will NOT hand over the lock
        // so unlock is necessary!
        cond.signal();

        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void finished() {
        System.out.println("Count is: " + count);
    }
}
