package startingThread.thread;

class Runner extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Hello " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class App {
    public static void main(String[] args) {
        Runner runner1 = new Runner();
        // if we use runner1.run() here,
        // the code will run in main thread
        // instead of a new thread
        runner1.start();

        Runner runner2 = new Runner();
        runner2.start();
    }
}
