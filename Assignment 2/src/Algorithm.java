import java.util.ArrayList;

public interface Algorithm {
	float avTurnAroundTime();
	float avResponseTime();
	float avWaitingResponse();
	void run();
	ArrayList<Process> getProcesses();
}
