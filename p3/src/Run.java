public class Run {
    public Statistics stats;

    public long simulationLength;
    public long memorySize;
    public long maxCpuTime;
    public long avgIoTime;
    public long avgArrivalInterval;

    public Run(long simulationLength, long memorySize, long maxCpuTime, long avgIoTime, long avgArrivalInterval, Statistics stats) {
        this.simulationLength = simulationLength;
        this.memorySize = memorySize;
        this.maxCpuTime = maxCpuTime;
        this.avgIoTime = avgIoTime;
        this.avgArrivalInterval = avgArrivalInterval;
        this.stats = stats;
    }

    public String toString() {
        return "SimulationLength: " + simulationLength + ", Memsize: " +
                memorySize + ", maxCpuTime: " + maxCpuTime + ", avgIoTime: " +
                avgIoTime + ", avgArrivalIntervalMax: " + avgArrivalInterval + ", Average time spent in system per finished process: " +
                (stats.totalTimeSpentWaitingForMemory + stats.totalTimeSpentWaitingForCpu +
                        stats.totalCpuTimeProcessingOnlyCompleted + stats.totalTimeSpentWaitingForIo +
                        stats.totalIOTimeProcessing) / stats.nofCompletedProcesses;
    }
}

