import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FCFSAlgorithm implements Algorithm {

	private ArrayList<Process> processes;
	private LinkedList<String> timeline;

	/**
	 * Creates an algorithm object that runs the processes sent through the
	 * parameter.
	 * 
	 * @param processesIn
	 *            the processes sent
	 */
	public FCFSAlgorithm(ArrayList<Process> processesIn) {
		// deep copy of processes
		processes = new ArrayList<Process>();
		for (Process p : processesIn)
			processes.add(new Process(p));
		timeline = new LinkedList<String>();
	}

	@Override
	public float avTurnAroundTime() {
		float turnaroundTime = 0;
		for (int i = 0; i < processes.size(); i++) {
			turnaroundTime += processes.get(i).getFinishTime()
					- processes.get(i).getArrivalTime();
		}
		return turnaroundTime / (float) processes.size();
	}

	@Override
	public float avWaitingResponse() {
		float waitTime = processes.get(0).getStartTime();
		for (int i = 1; i < processes.size(); i++) {
			waitTime += processes.get(i).getStartTime()
					- processes.get(i).getArrivalTime();
		}
		return waitTime / (float) processes.size();
	}

	@Override
	public float avResponseTime() {
		float responseTime = 0;
		for (int i = 0; i < processes.size(); i++) {
			responseTime += processes.get(i).getStartTime();
		}
		return responseTime / (float) processes.size();
	}

	/**
	 * Run the processor, choosing a process for each quantum. Create the
	 * timeline and store statistics as it runs.
	 */
	public void run() {
		Queue<Process> awaitingArrival = new LinkedList<Process>();
		for (Process p : processes) {
			awaitingArrival.add(p);
		}
		Queue<Process> readyQueue = new LinkedList<Process>();
		int t = 0; // current quantum
		while (!readyQueue.isEmpty() || !awaitingArrival.isEmpty()) {
			// move all processes that have arrived to the readyQueue
			while (!awaitingArrival.isEmpty()
					&& awaitingArrival.peek().getArrivalTime() <= t) {
				readyQueue.add(awaitingArrival.remove());
			}
			if (readyQueue.isEmpty()) // processor idle
				timeline.add("-");
			else { // run process in the front of queue for 1 quantum
				Process curr = readyQueue.peek();
				if (curr.getStartTime() == -1) {
					if (t > 99) { // no process should START after 99, remove
						// processes that are left
						for (Process p : readyQueue)
							processes.remove(p);
						break;
					}
					curr.setStartTime(t);
				}
				timeline.add(curr.getName());
				curr.executeProcess(t); // this updates remaining time and
				// finish time of
				// the process
				if (curr.getRemainingTime() == 0) {
					readyQueue.remove();
				}
			}
			t++;
		}
		System.out.println(timeline);
	}

	// for testing
	@Override
	public ArrayList<Process> getProcesses() {
		return processes;
	}

}
