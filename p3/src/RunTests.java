import java.util.Comparator;
import java.util.PriorityQueue;

public class RunTests {


    /**
     * Tests different values for the Round Robin project.
     */
    public static void main(String args[]) {

        long simulationLength = 250000;

        long avgIoTime = 225;
        long avgArrivalInterval = 5000;

        long memorySizeMin = 100;
        long maxCpuTimeMin = 100;

        long memorySizeMax = 10000;
        long maxCpuTimeMax = 10000;

        Comparator<Run> comparator = new RunComparator();
        PriorityQueue<Run> heap = new PriorityQueue<>(10, comparator);

        for (long memorySize = memorySizeMin; memorySize <= memorySizeMax; memorySize += 10) {
            for (long maxCpuTime = maxCpuTimeMin; maxCpuTime <= maxCpuTimeMax; maxCpuTime += 10) {
                System.out.println("Process " + ((float) memorySize / memorySizeMax) * 100 + "%");

                Queue memoryQueue = new Queue("memory queue", 10, Constants.EAST);
                Queue cpuQueue = new Queue("CPU queue", 10, Constants.WEST);
                Queue ioQueue = new Queue("I/O queue", 10, Constants.EAST);

                Simulator sim = new Simulator(memoryQueue, cpuQueue, ioQueue, memorySize, maxCpuTime, avgIoTime, simulationLength, avgArrivalInterval, null);
                Statistics stats = sim.simulate();

                Run run = new Run(simulationLength, memorySize, maxCpuTime, avgIoTime, avgArrivalInterval, stats);

                heap.add(run);
            }
        }

        for (int i = 0; i < 10; i++) {
            Run best = heap.remove();

            System.out.println(best);
            best.stats.printReport(simulationLength);
        }
    }
}

