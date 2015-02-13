import java.util.ArrayList;


public interface Algorithm {
	float avTurnAroundTime(ArrayList<Process> proc);
	float avWaitingResponse();
	float avResponseTime();
	ArrayList<Process> sortProcesses(ArrayList<Process> proc);
	void printTimeline(ArrayList<Process> proc);
}
