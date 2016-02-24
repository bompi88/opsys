package P2;
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
//            long sleepTime = (long) (Globals.doormanSleep * Math.random()); // hvorfor er denne long?
//          For tilfeldig  sleeptime mellom min og max
          long sleepTime = Constants.MIN_DOORMAN_SLEEP + (long)(Math.random() * (Constants.MAX_DOORMAN_SLEEP - Constants.MIN_DOORMAN_SLEEP + 1));

            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                gui.println("Doorman interrupted :(");
            }
            
            
            if (this.customerQueue.add(new Customer())) {
                gui.println("Customer added to queue.");
//              notify en eller annen barber hvis de venter på kunder
//            	gui.println("Barber # has been notified of a new costumer.");
                
            } else {
//            	bruk wait() sånn at dørmannen stopper opp helt til det er ledige plasser i venterommet
//            	gui.println("Doorman is waiting for a free chair.");
            }
        }
    }
}
