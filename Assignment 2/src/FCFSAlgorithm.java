import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * First Come First Serve algorithm
 *
 */
public class FCFSAlgorithm extends Algorithm {

	public FCFSAlgorithm(ArrayList<Process> processesIn) {
		super(processesIn);
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
	}

}
