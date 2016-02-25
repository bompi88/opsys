public class CPU {

    private final Queue cpuQueue;
    private final Memory memory;
    private final Statistics statistics;
    private IO io;

    private final long maxCpuTime;

    public CPU(Queue cpuQueue, long maxCpuTime, Statistics statistics, Memory memory) {
        this.cpuQueue = cpuQueue;
        this.memory = memory;
        this.statistics = statistics;
        this.maxCpuTime = maxCpuTime;
    }

    public void insert(Process process) {
        cpuQueue.insert(process);
    }

    public Event process() {
        // TODO: implement this
        Process process = cpuQueue.getNext();

        return new Event(Constants.END_PROCESS, 10);
    }

    public void endProcess() {
        Process process = cpuQueue.removeNext();
        memory.processCompleted(process);
    }

    public boolean hasNext() {
        return !cpuQueue.isEmpty();
    }

    public void connectIo(IO io) {
        this.io = io;
    }

    public long getMaxTime() {
        return this.maxCpuTime;
    }
}
