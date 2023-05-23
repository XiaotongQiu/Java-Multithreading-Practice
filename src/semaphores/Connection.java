package semaphores;

import java.util.concurrent.Semaphore;

public class Connection {
    // a singleton
    private static Connection instance  = new Connection();

    // limit connections to max 10 at anytime
    private Semaphore sem = new Semaphore(10, true);

    private int connections = 0;
    private Connection() {

    }

    public static Connection getInstance()  {
        return instance;
    }

    public void connect() {
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            doConnect();
        } finally {
            sem.release();
        }
    }

    public void doConnect() {
        synchronized (this) {
            connections++;
            System.out.println("Current connections: " + connections);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        synchronized (this) {
            connections--;
        }

    }
}
