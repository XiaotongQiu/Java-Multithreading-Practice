package waitNotify;

import java.util.Scanner;

public class Processor {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread running ....");
            // method from Object class
            // resource efficient
            // only can be called inside synchronized block
            // handover control of lock
            // and resume until:
            // 1. possible to regain the lock
            // 2. notify by another thread
            wait();
            System.out.println("Resumed");
        }
    }

    public void consume() throws InterruptedException {
        // to produce something first..
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);
        // use the same lock (this)
        synchronized (this) {
            System.out.println("Waiting for return key.");
            scanner.nextLine();
            System.out.println("Return key pressed.");
            // called in synchronized block
            // notify another thread locked on the same lock to wake up
            notify();
            // but when notify() is called,
            // the lock will NOT be immediately handed over to another thread
            // the lock will be release after synchronized block finished
            System.out.println("Consumer still got the lock!");
            Thread.sleep(5000);
        }

    }
}
