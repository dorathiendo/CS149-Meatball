import java.util.ArrayList;


public interface Algorithm {
	float avTurnAroundTime(ArrayList<Process> proc);
	float avResponseTime();
	float avWaitingResponse();
	ArrayList<Process> sortProcesses(ArrayList<Process> proc);
}
