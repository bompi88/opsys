import java.util.List;

public class IO {

    private final Queue ioQueue;
    private final CPU cpu;
    private final Gui gui;
    private final Statistics statistics;
    private Process currentIoProcess = null;

    private final long avgIoTime;

    public IO(Queue ioQueue, long avgIoTime, Statistics statistics, CPU cpu, Gui gui) {
        this.ioQueue = ioQueue;
        this.cpu = cpu;
        this.gui = gui;
        this.statistics = statistics;
        this.avgIoTime = avgIoTime;
    }

    public Event insert(Process process, long clock) {
        ioQueue.insert(process);
        process.entersIoQueue();

        // Hvis det ikke kjører en IO prosess, kjør den nye med en gang
        if (currentIoProcess == null) {
            currentIoProcess = ioQueue.removeNext();

            if (gui != null)
                gui.setIoActive(currentIoProcess);

            // Send event til når IO er ferdig
            return new Event(Constants.END_IO, clock + getRandomIoTime());
        }
        return null;
    }

    /**
     * Neste prosess bruker IO hvis det er prosesser i køen.
     * @param clock	current time
     */
    public Event runNextProcess(long clock) {
        // Hvis vi har en kø må neste prosess få lov til å gå bruke IO,
        // hvis ikke lar vi prosessen fortsette å kjøre.
        if (!ioQueue.isEmpty()) {

            // hent neste prosess
            currentIoProcess = ioQueue.removeNext();
            currentIoProcess.entersIO(clock);

            if (gui != null)
                gui.setIoActive(currentIoProcess);

            // Send event til når IO er ferdig
            return new Event(Constants.END_IO, clock + getRandomIoTime());
        } else {
            currentIoProcess = null;

            if (gui != null)
                gui.setIoActive(null);
        }

        return null;
    }

    public Event endIoProcess(long clock) {
        Process oldProcess = currentIoProcess;

        // Oppdater når prosessen gikk ut av IO
        oldProcess.leftIO(clock);
        oldProcess.setTimeToNextIoOperation();

        if (gui != null)
            gui.setIoActive(null);

        // Putt prosessen i CPU-køen igjen
        cpu.insert(oldProcess, clock);
        currentIoProcess = null;
        return cpu.trigger(clock);
        
    }

    /**
     * Trigger ny prosess til å bruke IO hvis ingen prosess bruker IO allerede.
     * @param clock	current time
     */
    public Event trigger(long clock) {
        // Hvis vi ikke har en kjørende prosess, kjør en ny prosess.
        if (currentIoProcess == null) {
            return runNextProcess(clock);
        }

        return null;
    }

    public long getRandomIoTime() {
        return (long) (Math.random() * avgIoTime) * 2 + 1;
    }
    
    /**
	 * This method is called when a discrete amount of time has passed. 
	 * Use this method to track how long processes have been waiting in the queue.
	 * @param timePassed	The amount of time that has passed since the last call to this method.
	 */
	public void timePassed(long timePassed) {
		statistics.ioQueueLengthTime += ioQueue.getQueueLength()*timePassed;
		if (ioQueue.getQueueLength() > statistics.ioQueueLargestLength) {
			statistics.ioQueueLargestLength = ioQueue.getQueueLength();
		}

	}

    public List<Process> getAll() {
        List<Process> allProcesses = ioQueue.getAll();
        if (currentIoProcess != null) {
            allProcesses.add(currentIoProcess);
        }

        return allProcesses;
    }

}
