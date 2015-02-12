import java.util.ArrayList;


public interface Algorithm {
	float avTurnAroundTime();
	float avResponseTime();
	float avWaitingResponse();
	ArrayList<Process> sortProcesses(ArrayList<Process> proc);
}
