public class IO {

    private final Queue ioQueue;
    private final CPU cpu;
    private final Statistics statistics;

    private final long avgIoTime;

    public IO(Queue ioQueue, long avgIoTime, Statistics statistics, CPU cpu) {
        this.ioQueue = ioQueue;
        this.cpu = cpu;
        this.statistics = statistics;
        this.avgIoTime = avgIoTime;
    }

    public void insert(Process process) {
        ioQueue.insert(process);
    }

    public long process() {
        // TODO: implement this
        return 0;
    }

    public boolean hasNext() {
        return !ioQueue.isEmpty();
    }
}