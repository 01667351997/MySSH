package server.model;

/**
 *
 * @author binhminh
 */
public class Clients {

    private final int MaxConnect;
    private int CurrentConnect = 0;
    
    
    public int getClientConnect() {
        return CurrentConnect;
    }

    public Clients(int maxCL) {
        this.MaxConnect = maxCL;
    }

    public synchronized void connect() {
        while (CurrentConnect >= MaxConnect) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        CurrentConnect++;
        notify();
    }

    public synchronized void disconnect() {
        
        System.out.println("Have client disconnect! ");
        CurrentConnect--;
    }

    public boolean isOverhead() {
        if (this.CurrentConnect < this.MaxConnect) {
            return false;
        }
        return true;
    }

}
