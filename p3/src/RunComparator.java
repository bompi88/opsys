import java.util.Comparator;

public class RunComparator implements Comparator<Run> {
    @Override
    public int compare(Run x, Run y) {


        if (computeHeuristics(x.stats) < computeHeuristics(y.stats)) {
            return -1;
        }

        if (computeHeuristics(x.stats) > computeHeuristics(y.stats)) {
            return 1;
        }

        return 0;
    }

    public long computeHeuristics(Statistics stats) {
        return (stats.totalTimeSpentWaitingForMemory +
                stats.totalTimeSpentWaitingForCpu +
                stats.totalCpuTimeProcessingOnlyCompleted +
                stats.totalTimeSpentWaitingForIo +
                stats.totalIOTimeProcessing) / stats.nofCompletedProcesses;
    }
}
