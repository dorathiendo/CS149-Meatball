import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Algorithm {
	protected ArrayList<Process> processes;
	protected LinkedList<String> timeline;

	/**
	 * Creates an algorithm object that runs the processes sent through the
	 * parameter.
	 * 
	 * @param processesIn
	 *            the processes sent
	 */
	public Algorithm(ArrayList<Process> processesIn) {
		// deep copy of processes
		processes = new ArrayList<Process>();
		for (Process p : processesIn)
			processes.add(new Process(p));
		timeline = new LinkedList<String>();
		run();
	}

	public float avTurnAroundTime() {
		float turnaroundTime = 0;
		for (int i = 0; i < processes.size(); i++) {
			turnaroundTime += processes.get(i).getFinishTime()
					- processes.get(i).getArrivalTime();
		}
		return turnaroundTime / (float) processes.size();
	}

	public float avWaitingResponse() {
		float waitTime = 0;
		for (int i = 0; i < processes.size(); i++) {
			waitTime += processes.get(i).getFinishTime()
					- processes.get(i).getArrivalTime()
					- processes.get(i).getRunTime();
		}
		return waitTime / (float) processes.size();
	}

	public float avResponseTime() {
		float responseTime = 0;
		for (int i = 0; i < processes.size(); i++) {
			responseTime += processes.get(i).getStartTime()
					- processes.get(i).getArrivalTime();
		}
		return responseTime / (float) processes.size();
	}

	public abstract void run();

	public ArrayList<Process> getProcesses() {
		return processes;
	}

	public LinkedList<String> getTimeline() {
		return timeline;
	}
}
