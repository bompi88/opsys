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
            gui.setIoActive(currentIoProcess);

            // Send event til når IO er ferdig
            return new Event(Constants.END_IO, clock + getRandomIoTime());
        }
        return null;
    }

    public long getRandomIoTime() {
        return avgIoTime;
    }

    public Event endIoProcess(long clock) {
        Process oldProcess = currentIoProcess;

        // Oppdater når prosessen gikk ut av IO
        oldProcess.leftIO(clock);
        oldProcess.setTimeToNextIoOperation();
        gui.setIoActive(null);

        // Putt prosessen i CPU-køen igjen
        cpu.insert(oldProcess, clock);

        currentIoProcess = null;
        return cpu.trigger(clock);
    }
}
