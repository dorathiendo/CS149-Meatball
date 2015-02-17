import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Shortest Job First algorithm
 *
 */
public class SJFAlgorithm extends Algorithm {

	public SJFAlgorithm(ArrayList<Process> processesIn) {
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

		// shortest job time first
		Comparator<Process> comp = new Comparator<Process>() {
			@Override
			public int compare(Process p1, Process p2) {
				if (p1.getRunTime() == p2.getRunTime()) {
					return 0;
				}
				return p1.getRunTime() < p2.getRunTime() ? -1 : 1;
			}
		};
		PriorityQueue<Process> readyQueue = new PriorityQueue<Process>(20, comp);

		int t = 0; // current quantum
		while (!readyQueue.isEmpty() || !awaitingArrival.isEmpty()) {
			// move all processes that have arrived to the readyQueue
			while (!awaitingArrival.isEmpty()
					&& awaitingArrival.peek().getArrivalTime() <= t) {
				readyQueue.add(awaitingArrival.remove());
			}
			if (readyQueue.isEmpty()) { // processor idle
				timeline.add("-");
				t++;
			} else { // run process in the front of queue for 1 quantum
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
				while (curr.getRemainingTime() > 0) { // while not done
					timeline.add(curr.getName());
					curr.executeProcess(t); // this updates remaining time and
					t++;
				}
				readyQueue.remove();
			}
		}
	}

}
