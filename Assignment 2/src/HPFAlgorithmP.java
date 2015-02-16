import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class HPFAlgorithmP extends Algorithm {

	public HPFAlgorithmP(ArrayList<Process> processesIn) {
		super(processesIn);
		// TODO Auto-generated constructor stub
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

		// Sort by priority, then by when a process was run last
		Comparator<Process> comp = new Comparator<Process>() {
			@Override
			public int compare(Process p1, Process p2) {
				if (p1.getPriority() == p2.getPriority()) {
					if (p1.getLatestRun() == p2.getLatestRun())
						return 0;
					return p1.getLatestRun() < p2.getLatestRun() ? -1 : 1;
				}
				return p1.getPriority() > p2.getPriority() ? -1 : 1;
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

	public void printQueueStats() {
		ArrayList<ArrayList<Process>> lists = new ArrayList<ArrayList<Process>>();
		lists.add(0, null);
		for (int i = 1; i <= 4; i++)
			lists.add(i, new ArrayList<Process>());
		for (Process p : processes) {
			lists.get(p.getPriority()).add(p);
		}
		System.out.println("Priority-specific Statistics:");
		for (int i = 4; i >= 1; i--) {
			ArrayList<Process> curr = lists.get(i);
			float turnaroundTime = 0;
			float waitTime = 0;
			float responseTime = 0;
			for (int j = 0; j < curr.size(); j++) {
				turnaroundTime += curr.get(j).getFinishTime()
						- curr.get(j).getArrivalTime();
			}
			for (int j = 0; j < curr.size(); j++) {
				waitTime += curr.get(j).getFinishTime()
						- curr.get(j).getArrivalTime()
						- curr.get(j).getRunTime();
			}
			for (int j = 0; j < curr.size(); j++) {
				responseTime += curr.get(j).getStartTime()
						- curr.get(j).getArrivalTime();
			}
			turnaroundTime = turnaroundTime / (float) curr.size();
			waitTime = waitTime / (float) curr.size();
			responseTime = responseTime / (float) curr.size();
			System.out.println("Priority " + i + ":"
					+ "\n\tThroughput: " + curr.size()
					+ "\n\tAverage Turnaround Time: " + turnaroundTime
					+ "\n\tAverage Wait Time: " + waitTime
					+ "\n\tAverage Response Time:" + responseTime);
		}
	}

}
