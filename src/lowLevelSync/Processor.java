package lowLevelSync;


import java.util.LinkedList;
import java.util.Random;

public class Processor {
    private LinkedList<Integer> list = new LinkedList<>();
    private final int LIMIT = 10;
    private Object lock = new Object();

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (lock) {
                // if or while?
                // while will go back and check again after being notified
                while (list.size() == LIMIT) {
                    lock.wait(); // emphasize the need to call wait() on the object locked
                }
                list.add(value++);
                lock.notify();
            }

        }
    }

    public void consume() throws InterruptedException {
        Random random = new Random();

        while (true) {
            synchronized (lock) {
                while (list.isEmpty()) {
                    lock.wait();
                }
                System.out.print("List size is: " + list.size());
                int value = list.removeFirst(); // FIFO
                System.out.println("; value is: " + value);
                lock.notify();
            }

            Thread.sleep(random.nextInt(1000));
        }
    }

}
