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
	
	/**
	 * Gets the average turnaround time of the algorithm.
	 * @return the average turnaround time. 
	 */
	public float avTurnAroundTime() {
		float turnaroundTime = 0;
		for (int i = 0; i < processes.size(); i++) {
			turnaroundTime += processes.get(i).getFinishTime()
					- processes.get(i).getArrivalTime();
		}
		return turnaroundTime / (float) processes.size();
	}
	
	/**
	 * Gets the average wait time of the algorithm.
	 * @return the average wait time.
	 */
	public float avWaitingResponse() {
		float waitTime = 0;
		for (int i = 0; i < processes.size(); i++) {
			waitTime += processes.get(i).getFinishTime()
					- processes.get(i).getArrivalTime()
					- processes.get(i).getRunTime();
		}
		return waitTime / (float) processes.size();
	}
	
	/**
	 * Gets the average response time of the algorithm.
	 * @return the average response time.
	 */
	public float avResponseTime() {
		float responseTime = 0;
		for (int i = 0; i < processes.size(); i++) {
			responseTime += processes.get(i).getStartTime()
					- processes.get(i).getArrivalTime();
		}
		return responseTime / (float) processes.size();
	}
	
	/**
	 * Runs the algorithm with the processes. 
	 */
	public abstract void run();
	
	/**
	 * Gets the processes used.
	 * @return the list of processes
	 */
	public ArrayList<Process> getProcesses() {
		return processes;
	}
	
	/**
	 * Returns the list of processes (timeline) in the order they were run.
	 * @return the timeline. 
	 */
	public LinkedList<String> getTimeline() {
		return timeline;
	}
}
