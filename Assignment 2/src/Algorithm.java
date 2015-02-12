import java.util.ArrayList;


public interface Algorithm {
	int avTurnAroundTime();
	int avWaitingResponse();
	int avResponseTime();
	ArrayList<Process> sortProcesses(ArrayList<Process> proc);
	void printTimeline(ArrayList<Process> proc);
}
