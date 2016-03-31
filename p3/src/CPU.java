import java.util.List;

public class CPU {

    private final Queue cpuQueue;
    private final Memory memory;
    private final Statistics statistics;
    private final Gui gui;
    private Process activeProcess;

    private final long maxCpuTime;

    public CPU(Queue cpuQueue, long maxCpuTime, Statistics statistics, Memory memory, Gui gui) {
        this.cpuQueue = cpuQueue;
        this.memory = memory;
        this.statistics = statistics;
        this.maxCpuTime = maxCpuTime;
        this.gui = gui;
    }

    public void insert(Process process, long clock) {
        cpuQueue.insert(process);
        process.entersReadyQueue(clock);

    }

    /**
     * Kjører en prosess og tar seg av cpu og io events.
     * @param clock	current time
     */
    public Event run(Process process, long clock) {

        // Hvis vi må dele opp prosessen, fordi den ikke rekker å bli ferdig innen max cpu time
        if (process.getTimeToNextIoOperation() > maxCpuTime && process.getCpuTimeNeeded() > maxCpuTime) {
            // Vi må bytte til neste prosess når denne prosessens tid er brukt opp i cpuen,

            return new Event(Constants.SWITCH_PROCESS, clock + maxCpuTime);

        // Hvis vi rekker å bli ferdig med prosessen denne syklusen
        } else if (process.getCpuTimeNeeded() <= maxCpuTime) {
            // Vi må avslutte prosessen når den er ferdig

            return new Event(Constants.END_PROCESS, clock + process.getCpuTimeNeeded());

        // Hvis vi rekker å bruke IO i denne syklusen
        } else if (process.getTimeToNextIoOperation() <= maxCpuTime) {
            // Vi må be om IO tillatelse

            return new Event(Constants.IO_REQUEST, clock + process.getTimeToNextIoOperation());
        }

        return null;
    }

    /**
     * Kjører neste prosess hvis det er prosesser i køen.
     * @param clock	current time
     */
    
    /* The period of the time quantum should not very short as this leads to the process moving through the system very quickly which is positive but 
     * there will be processing overhead involved in handling the clock interrupt, performing scheduling and dispatching the function. A useful guide
     * states that the time quantum should be slightly greater than the time required for a typical interaction process function. 
     */
    
    public Event runNextProcess(long clock) {
        // Hvis vi har en kø må nest prosess få lov til å gå inn i CPU'en,
        // hvis ikke lar vi prosessen fortsette å kjøre.
        if (!cpuQueue.isEmpty()) {

            // hent neste prosess
            activeProcess = cpuQueue.removeNext();
            activeProcess.entersCpu(clock);

            if (gui != null)
                gui.setCpuActive(activeProcess);

            // Kjør den nye prosessen og returner den genererte eventen
            return run(activeProcess, clock);
        } else {
            activeProcess = null;

            if (gui != null)
                gui.setCpuActive(null);
        }

        return null;
    }

    /**
     * Bytter prosess til neste i køen når en SWITCH_PROCESS event fyres.
     * @param clock	current time
     */
    public Event switchProcess(long clock) {
        activeProcess.leftCpu(clock);
        insert(activeProcess, clock);

        if (cpuQueue.getQueueLength() > 1) {
            activeProcess.forcedProcessSwitch();
        }
        activeProcess = null;

        return runNextProcess(clock);
    }

    /**
     * Trigger ny prosess til å kjøre hvis ingen prosess kjører allerede.
     * @param clock	current time
     */
    public Event trigger(long clock) {
        // Hvis vi ikke har en kjørende prosess, kjør en ny prosess.
        if (activeProcess == null) {
            return runNextProcess(clock);
        }

        return null;
    }

    /**
     * Stopper en prosess og oppdaterer statistikken.
     * @param clock	current time
     */
    public Event endProcess(long clock) {
        activeProcess.leftCpu(clock);
        activeProcess.setCompleted();
        activeProcess.updateStatistics(statistics);
        memory.processCompleted(activeProcess);
        activeProcess = null;

        if (gui != null)
            gui.setCpuActive(null);

        return trigger(clock);
    }
    /**
     * Få den kjørende prosessen
     */
    public Process getActiveProcess() {
        return activeProcess;
    }

    
	/**
	 * This method is called when a discrete amount of time has passed.
	 * @param timePassed	The amount of time that has passed since the last call to this method.
	 */
	public void timePassed(long timePassed) {
		statistics.cpuQueueLengthTime += cpuQueue.getQueueLength()*timePassed;
		if (cpuQueue.getQueueLength() > statistics.cpuQueueLargestLength) {
			statistics.cpuQueueLargestLength = cpuQueue.getQueueLength();
		}
	}

    public List<Process> getAll() {
        List<Process> allProcesses = cpuQueue.getAll();
        if (activeProcess != null) {
            allProcesses.add(activeProcess);
        }

        return allProcesses;
    }
}
