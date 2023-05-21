package synchronization.cache;

import java.util.Scanner;

class Processor extends Thread {
    // running might be cached
    // so the while loop cannot be terminated as expected
    // volatile key word is required to guarantee the code would work for ANY system
    private volatile boolean running = true;
    @Override
    public void run() {
//        while (true) {
        while (running) { // reading running
            System.out.println("Hello");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void shutdown() { // write running
        running = false;
    }

}
public class App {
    public static void main(String[] args) {
        Processor proc1 = new Processor();
        proc1.start();

        System.out.println("Press return to stop");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        proc1.shutdown();
    }
}
