import java.util.LinkedList;

/**
 * This class implements a queue of customers as a circular buffer.
 */
public class CustomerQueue extends LinkedList<Customer> {

    private final Gui gui;
    private final int queueLength;
    private int currentPosition = 0;
    private int lastRemoved = 0;

    /**
     * Creates a new customer queue.
     * @param queueLength	The maximum length of the queue.
     * @param gui			A reference to the GUI interface.
     */
    public CustomerQueue(int queueLength, Gui gui) {
        this.gui = gui;
        this.queueLength = queueLength;

    }

    /**
     * Adds a customer to the queue.
     * @param customer The customer to add
     * @return
     */
    @Override
    public synchronized boolean add(Customer customer) {
        boolean success = super.add(customer);
        boolean added = true;

        // Remove the last customer if full
        while (success && size() > this.queueLength) {
            super.remove();
            added = false;
        }

        // only fill the chair if customer got accepted
        if (added) {
            gui.fillLoungeChair(this.currentPosition, customer);

            this.currentPosition = incrementPosition(this.currentPosition);
        }

        return added;
    }

    /**
     * Pops a customer off the queue.
     * @return
     */
    @Override
    public synchronized Customer pop() {

        // Return a customer if any in the queue
        if (super.size() > 0) {
            Customer customer = super.pop();

            gui.emptyLoungeChair(this.lastRemoved);

            this.lastRemoved = incrementPosition(this.lastRemoved);
            return customer;
        }

        return null;
    }

    /**
     * Increment a position in the circular queue.
     * @param pos
     * @return
     */
    private int incrementPosition(int pos) {
        pos = pos + 1;

        if (pos >= this.queueLength) {
            pos = 0;
        }

        return pos;
    }

}