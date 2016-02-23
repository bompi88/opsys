/**
 * This class implements the barber's part of the
 * Barbershop thread synchronization example.
 */
public class Barber extends Thread {

    private final CustomerQueue customerQueue;
    private final Gui gui;
    private final int position;

    private boolean running = false;
    private Customer customer = null;

    /**
     * Creates a new barber.
     * @param queue		The customer queue.
     * @param gui		The GUI.
     * @param pos		The position of this barber's chair
     */
    public Barber(CustomerQueue queue, Gui gui, int pos) {
        this.customerQueue = queue;
        this.gui = gui;
        this.position = pos;
    }

    /**
     * Starts the barber running as a separate thread.
     */
    public void startThread() {
        running = true;
        this.start();
    }

    /**
     * Stops the barber thread.
     */
    public void stopThread() {
        running = false;
    }

    /**
     * Work done by a Barber.
     */
    public void run() {
        while(running) {
            int sleepTime = (int)(Math.random() * (Globals.barberSleep + 1));

            // day dream for random amount and then if chair is filled do the work.
            try {
                // Day dream
                gui.barberIsSleeping(this.position);

                gui.println("Barber " + this.position + " is day dreaming.");
                sleep(sleepTime);
                gui.println("Barber " + this.position + " is done day dreaming.");

                gui.barberIsAwake(this.position);

                this.customer = this.customerQueue.pop();

                // Do some barber work
                if (this.customer != null) {
                    gui.fillBarberChair(this.position, this.customer);

                    gui.println("Barber " + this.position + " starts working.");

                    sleep(Globals.barberWork);

                    gui.emptyBarberChair(this.position);
                    gui.println("Barber " + this.position + " finished his barber work.");
                }
            } catch (InterruptedException e) {
                gui.println("Barber " + this.position + " interrupted :(");
            }

        }
    }
}

