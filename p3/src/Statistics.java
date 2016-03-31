/**
 * This class contains a lot of public variables that can be updated
 * by other classes during a simulation, to collect information about
 * the run.
 */
public class Statistics
{
	/** The number of processes that have exited the system */
	public long nofCompletedProcesses = 0;

	/** The number of processes that have entered the system */
	public long nofCreatedProcesses = 0;

    /** Number of (forced) process switches */
    public long nofForcedProcessSwitches = 0;

    /** Number of processed I/O operations */
    public long nofProcessedIoOperations = 0;

    /** Total CPU time spent processing */
    public long totalCpuTimeProcessing = 0;

    /** Total time spent in IO */
    public long totalIOTimeProcessing = 0;

    /** Total CPU time spent waiting */
    public long totalCpuTimeWaiting = 0;

    /** The total time that all completed processes have spent waiting for memory */
	public long totalTimeSpentWaitingForMemory = 0;

    /** The total time that all completed processes have spent waiting for cpu */
    public long totalTimeSpentWaitingForCpu = 0;

    /** The total time that all completed processes have spent waiting for io */
    public long totalTimeSpentWaitingForIo = 0;

	/** The time-weighted length of the memory queue, divide this number by the total time to get average queue length */
	public long memoryQueueLengthTime = 0;

    /** The time-weighted length of the cpu queue, divide this number by the total time to get average queue length */
    public long cpuQueueLengthTime = 0;

    /** The time-weighted length of the io queue, divide this number by the total time to get average queue length */
    public long ioQueueLengthTime = 0;

    /** The largest memory queue length that has occured */
	public long memoryQueueLargestLength = 0;

    /** The largest cpu queue length that has occured */
    public long cpuQueueLargestLength = 0;

    /** The largest io queue length that has occured */
    public long ioQueueLargestLength = 0;

    /** Number of processes that has been inserted into the CPU Queue */
    public long nofProcessesInsertedIntoCpuQueue = 0;

    /** Number of processes that has been inserted into the I/O Queue */
    public long nofProcessesInsertedIntoIoQueue = 0;

    /**
	 * Prints out a report summarizing all collected data about the simulation.
	 * @param simulationLength	The number of milliseconds that the simulation covered.
	 */
	public void printReport(long simulationLength) {
		System.out.println();
		System.out.println("Simulation statistics:");

        System.out.println();
		System.out.println("Number of completed processes: " + nofCompletedProcesses);
		System.out.println("Number of created processes: " + nofCreatedProcesses);
        System.out.println("Number of (forced) process switches: " + nofForcedProcessSwitches);
        System.out.println("Number of processed I/O operations: " + nofProcessedIoOperations);
        System.out.println("Average throughput (processes per second): " +
                (float)nofCompletedProcesses * 1000 / (totalCpuTimeProcessing + (simulationLength - totalCpuTimeProcessing)));

        System.out.println();
        System.out.println("Total CPU time spent processing: " + totalCpuTimeProcessing);
        System.out.println("Fraction of CPU time spent processing: " +
                ((double)100*totalCpuTimeProcessing / simulationLength + "%"));
        System.out.println("Total CPU time spent waiting: " + (simulationLength - totalCpuTimeProcessing));
        System.out.println("Fraction of CPU time spent waiting: " + 
                (double)(100-((double)100*totalCpuTimeProcessing/simulationLength))+"%");

        System.out.println();
		System.out.println("Largest occuring memory queue length: " + memoryQueueLargestLength);
		System.out.println("Average memory queue length: " + (float)memoryQueueLengthTime / simulationLength);
        System.out.println("Largest occuring cpu queue length: " + cpuQueueLargestLength);
        System.out.println("Average cpu queue length: " + (float)cpuQueueLengthTime / simulationLength);
        System.out.println("Largest occuring I/O queue length: " + ioQueueLargestLength);
        System.out.println("Average I/O queue length: " + (float)ioQueueLengthTime / simulationLength);

		if(nofCompletedProcesses > 0) {
			System.out.println("Average # of times a process has been placed in memory queue: " + 1);
            System.out.println("Average # of times a process has been placed in cpu queue: " +
                    (float)nofProcessesInsertedIntoCpuQueue / nofCreatedProcesses);
            System.out.println("Average # of times a process has been placed in I/O queue: " +
                    (float)nofProcessesInsertedIntoIoQueue / nofCreatedProcesses);

            System.out.println();
            
			System.out.println("Average time spent in system per process: " +
                    simulationLength / nofCreatedProcesses + " ms");
            System.out.println("Average time spent waiting for memory per process: " +
                    totalTimeSpentWaitingForMemory / nofCreatedProcesses + " ms");
            System.out.println("Average time spent waiting for cpu per process: " +
                    totalTimeSpentWaitingForCpu / nofCreatedProcesses + " ms");
            System.out.println("Average time spent processing per process: " +
                    totalCpuTimeProcessing / nofCreatedProcesses + " ms");
            System.out.println("Average time spent waiting for I/O per process: " +
                    totalTimeSpentWaitingForIo / nofCreatedProcesses + " ms");
            System.out.println("Average time spent in I/O per process: " +
                    totalIOTimeProcessing / nofCreatedProcesses + " ms");
		}
	}
}
