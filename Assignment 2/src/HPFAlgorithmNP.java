import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class HPFAlgorithmNP extends Algorithm {

	public HPFAlgorithmNP(ArrayList<Process> processesIn) {
		super(processesIn);
	}

	/**
	 * 
	 * @return true if empty
	 */
	public Boolean isQueueEmpty(ArrayList<Queue<Process>> q) {
		for (int i = 0; i < 4; i++) {
			if (!q.get(i).isEmpty())
				return false;
		}
		return true;
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

		ArrayList<Queue<Process>> priorityQueues = new ArrayList<>();
		priorityQueues.add(new LinkedList<Process>()); // lowest
		priorityQueues.add(new LinkedList<Process>());
		priorityQueues.add(new LinkedList<Process>());
		priorityQueues.add(new LinkedList<Process>()); // highest

		int t = 0; // current quantum
		int name = 0;
		Process curr = null;
		while (!isQueueEmpty(priorityQueues) || !awaitingArrival.isEmpty()) {
			// move all processes that have arrived to the readyQueue
			while (!awaitingArrival.isEmpty()
					&& awaitingArrival.peek().getArrivalTime() <= t) {
				for (int i = 0; i < 4; i++) {
					if (awaitingArrival.peek().getPriority() == (i + 1)) {
						curr = awaitingArrival.remove();
						priorityQueues.get(i).add(curr);
						name = i;
						break;
					}
				}
			}
			if (isQueueEmpty(priorityQueues)) {
				timeline.add("-");
				t++;
			} else { // run process in the front of queue for 1 quantum
				if (curr.getStartTime() == -1) {
					if (t > 99) { // no process should START after 99, remove
						// processes that are left
						for (Queue<Process> q : priorityQueues) {
							for (Process p : q) {
								processes.remove(p);
							}
						}
						break;
					}
					curr.setStartTime(t);
				}
				while (curr.getRemainingTime() > 0) { // while not done
					timeline.add(curr.getName());
					curr.executeProcess(t); // this updates remaining time and
					t++;
				}
				priorityQueues.get(name).remove(curr);
			}
		}
	}

}
