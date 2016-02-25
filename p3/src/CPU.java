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

    public void connectIo(IO io) {
        this.io = io;
    }
}
