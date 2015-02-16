import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RRAlgorithm extends Algorithm {

	public RRAlgorithm(ArrayList<Process> processesIn) {
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
		LinkedList<Process> readyQueue = new LinkedList<Process>();
		int t = 0; // current quantum
		while (!readyQueue.isEmpty() || !awaitingArrival.isEmpty()) {
			// move all processes that have arrived to the readyQueue
			while (!awaitingArrival.isEmpty()
					&& awaitingArrival.peek().getArrivalTime() <= t) {
				readyQueue.addFirst(awaitingArrival.remove());
			}
			if (readyQueue.isEmpty()) // processor idle
				timeline.add("-");
			else { // run process in the front of queue for 1 quantum
				Process curr = readyQueue.peek();
				if (t > 99) {
					// get to next unfinished, but started, process
					while (!readyQueue.isEmpty() && curr.getStartTime() == -1) {
						processes.remove(readyQueue.remove());
						curr = readyQueue.peek();

					}
					// break if no more processes
					if (curr == null)
						break;
				}
				if (curr.getStartTime() == -1) {
					curr.setStartTime(t);
				}
				timeline.add(curr.getName());
				curr.executeProcess(t); // this updates remaining time and
				// finish time of
				// the process
				if (curr.getRemainingTime() == 0) {
					readyQueue.remove();
				} else {
					readyQueue.add(curr);
					readyQueue.remove();
				}
			}
			t++;
		}
	}

}
