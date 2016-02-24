package P2;

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
    	while(size() == queueLength){
    		try{
    			wait();
    		} catch(InterruptedException e) {
    			gui.println("Doorman failed waiting.");
    		}
    		
    	}
    	
        boolean added = super.add(customer);
        gui.fillLoungeChair(this.currentPosition, customer);
        this.currentPosition = incrementPosition(this.currentPosition);
        notifyAll();

        return added;
    }

    /**
     * Pops a customer off the queue.
     * @return
     */
    @Override
    public synchronized Customer pop() {
    	while(size() == 0){
    		try{
    			wait();
//    			gui.println("Barber waiting for new costumer.");
    		} catch(InterruptedException e){
    			gui.println("Barber failed waiting,");
    		}
    	}

        Customer customer = super.pop();
        gui.emptyLoungeChair(this.lastRemoved);
        this.lastRemoved = incrementPosition(this.lastRemoved);
        notifyAll();
        
        return customer;
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