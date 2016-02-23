/**
 * This class implements the doorman's part of the
 * Barbershop thread synchronization example.
 */
public class Doorman extends Thread {

    private final CustomerQueue customerQueue;
    private final Gui gui;

    private boolean running = false;

    /**
     * Creates a new doorman.
     * @param queue		The customer queue.
     * @param gui		A reference to the GUI interface.
     */
    public Doorman(CustomerQueue queue, Gui gui) {
        this.customerQueue = queue;
        this.gui = gui;
    }

    /**
     * Starts the doorman running as a separate thread.
     */
    public void startThread() {
        running = true;
        this.start();
    }

    /**
     * Stops the doorman thread.
     */
    public void stopThread() {
        running = false;
    }

    /**
     * What the doorman should do while awake
     */
    public void run() {
        while(running) {
            try {
                this.sleep((long) (Globals.doormanSleep * Math.random()));
            } catch (InterruptedException e) {
                gui.println("Doorman interrupted :(");
            }
            if (this.customerQueue.add(new Customer())) {
                gui.println("Customer added to queue.");
            }
        }
    }
}
