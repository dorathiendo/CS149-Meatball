import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

public class FCFSAlgorithm implements Algorithm {

	private ArrayList<Process> processes;
	private LinkedList<String> timeline;
	private float avTurnAroundTime;
	private float avWaitingResponse;
	private float avResponseTime;

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
		processes = sortProcesses(processes);
		timeline = new LinkedList<String>();
		// timeline = createTimeline(); //calculates all start times + timeline
		// w/ no spaces
	}

	@Override
	public float avTurnAroundTime() {
		float turnaroundTime = 0;
		for (int i = 0; i < processes.size(); i++) {
			turnaroundTime += processes.get(i).getFinishTime()
					- processes.get(i).getArrivalTime();
		}
		avTurnAroundTime = turnaroundTime / (float) processes.size();
		return avTurnAroundTime;
	}

	@Override
	public float avWaitingResponse() {
		float waitTime = processes.get(0).getStartTime();
		for (int i = 1; i < processes.size(); i++) {
			waitTime += processes.get(i).getStartTime()
					- processes.get(i).getArrivalTime();
		}
		avWaitingResponse = waitTime / (float) processes.size();
		return avWaitingResponse;
	}

	@Override
	public float avResponseTime() {
		float responseTime = 0;
		for (int i = 0; i < processes.size(); i++) {
			responseTime += processes.get(i).getStartTime();
		}
		avResponseTime = responseTime / (float) processes.size();
		return avResponseTime;
	}

	@Override
	public ArrayList<Process> sortProcesses(ArrayList<Process> proc) {
		Collections.sort(proc, new Comparator<Process>() {
			public int compare(Process p1, Process p2) {
				if (p1.getArrivalTime() == p2.getArrivalTime())
					return 0;
				return p1.getArrivalTime() < p2.getArrivalTime() ? -1 : 1;
			}
		});
		return proc;
	}

	public void printTimeline(ArrayList<Process> proc) {
		for (int i = 0; i < proc.size(); i++) {
			System.out.print(proc.get(i).getName());
		}
		System.out.println();
	}

	public ArrayList<Float> finishTime(ArrayList<Process> proc) {
		ArrayList<Float> finishTimes = new ArrayList<Float>();
		finishTimes.add(proc.get(0).getRunTime());
		float time = proc.get(0).getRunTime();
		for (int i = 1; i < proc.size(); i++) {
			for (int j = i; j > 0; j--) {
				time += proc.get(j).getRunTime();
			}
			finishTimes.add(time);
		}
		return finishTimes;
	}

	/**
	 * Andres's attempt: Run the processor, choosing a process for each quantum.
	 * Create the timeline and store statistics as it runs.
	 * 
	 * @return
	 */

	public void run() {
		Queue<Process> awaitingArrival = new LinkedList<Process>();
		for (Process p : processes) {
			awaitingArrival.add(p);
		}
		Queue<Process> readyQueue = new LinkedList<Process>();
		int t = 0; // current quantum
		while (!readyQueue.isEmpty() || !awaitingArrival.isEmpty()) {
			while (!awaitingArrival.isEmpty()
					&& awaitingArrival.peek().getArrivalTime() <= t) {
				// move all processes that have arrived to the readyQueue
				readyQueue.add(awaitingArrival.remove());
			}
			if (readyQueue.isEmpty()) // processor idle
				timeline.add("-");
			else { // run process in front of queue for 1 quantum
				Process curr = readyQueue.peek();
				if (curr.getStartTime() == -1) {
					if (t > 99) { // no process should START after 99, remove processes that are left
						for (Process p : readyQueue)
							processes.remove(p);
						break;
					}
					curr.setStartTime(t);
				}
				timeline.add(curr.getName());
				curr.run(t); // this updates remaining time and finish time of
								// the process
				if (curr.getRemainingTime() == 0) {
					readyQueue.remove();
				}
			}
			t++;
		}
		System.out.println(timeline);
	}

	// creates timeline for FCFS but also start time and finish time
	// doesnt use instance variable
	public LinkedList<String> createTimeline() {
		Process curr = processes.get(0); // first process
		LinkedList<String> timeline = new LinkedList<String>();
		timeline.add(curr.getName()); // you guys can change this to whatever
										// data structure

		float t = curr.getArrivalTime(); // first start time
		processes.get(0).setStartTime(curr.getArrivalTime()); // sets first
																// process

		for (int i = 1; i < processes.size(); i++) {
			t += curr.getRunTime(); // calculate new start time
			curr = processes.get(i); // next process

			if (t < curr.getArrivalTime()) // if current process starts later
				t = curr.getArrivalTime();

			processes.get(i).setStartTime(t);
			timeline.add(curr.getName());
		}

		// calculate finish times after getting start times
		for (int i = 0; i < processes.size(); i++) {
			curr = processes.get(i);
			processes.get(i).setFinishTime(
					curr.getRunTime() + curr.getStartTime());
		}

		return timeline;
	}

	// for testing
	public ArrayList<Process> getSortedQueue() {
		return processes;
	}

	// public void setStartTimes(){;
	// Process curr = readyQueue.get(0); //first process
	// float time = curr.getArrivalTime(); //first start time
	// readyQueue.get(0).setStartTime(curr.getArrivalTime()); //sets first
	// process
	//
	// for(int i = 1; i < readyQueue.size(); i++){
	// time = time + curr.getRunTime(); //calculate new start time
	// curr = readyQueue.get(i); //next process
	//
	// if(time < curr.getArrivalTime()) //if current process starts later
	// time = curr.getArrivalTime();
	//
	// readyQueue.get(i).setStartTime(time);
	// }
	// }

}
