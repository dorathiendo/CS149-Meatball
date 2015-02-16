import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class HPFAlgorithmNP extends Algorithm {

	public HPFAlgorithmNP(ArrayList<Process> processesIn) {
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
				if (p1.getPriority() == p2.getPriority()) {
					if (p1.getArrivalTime() == p2.getArrivalTime())
						return 0;
					return p1.getArrivalTime() < p2.getArrivalTime() ? -1 : 1;
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
	
	public void printQueueStats(){
		ArrayList<ArrayList<Process>> lists = new ArrayList<ArrayList<Process>>();
		lists.add(0, null);
		for (int i = 1; i <= 4; i++)
			lists.add(i, new ArrayList<Process>());
		for (Process p : processes){
			lists.get(p.getPriority()).add(p);
		}
		System.out.println("Priority-specific Statistics:");
		for (int i = 4; i >= 1; i--){
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
			System.out.println("Priority " + i + ":\n" +
					"\tAverage Turnaround Time: " + turnaroundTime +
					"\n\tAverage Wait Time: " + waitTime +
					"\n\tAverage Response Time:" + responseTime);
		}
	}

}
