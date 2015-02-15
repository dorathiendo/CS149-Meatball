import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class HPFAlgorithmNP implements Algorithm {

	private ArrayList<Process> processes;
	private LinkedList<String> timeline;

	/**
	 * Creates an algorithm object that runs the processes sent through the
	 * parameter.
	 * 
	 * @param processesIn
	 *            the processes sent
	 */
	public HPFAlgorithmNP(ArrayList<Process> processesIn) {
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
	 * 
	 * @return true if empty
	 */
	public Boolean isQueueEmpty(ArrayList<Queue<Process>> q){
		for(int i = 0; i < 4; i++){
			if(!q.get(i).isEmpty())
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
		priorityQueues.add(new LinkedList<Process>()); //lowest
		priorityQueues.add(new LinkedList<Process>());
		priorityQueues.add(new LinkedList<Process>());
		priorityQueues.add(new LinkedList<Process>()); //highest

		int t = 0; // current quantum
		int name = 0;
		Process curr = null;
		while (!isQueueEmpty(priorityQueues) || !awaitingArrival.isEmpty()) {
			// move all processes that have arrived to the readyQueue
			while (!awaitingArrival.isEmpty()
					&& awaitingArrival.peek().getArrivalTime() <= t) {
				for(int i = 0; i<4; i++){
					if(awaitingArrival.peek().getPriority() == (i+1)){
						curr = awaitingArrival.remove();
						priorityQueues.get(i).add(curr);
						name = i;
						break;
					}
				}
			}
			if (isQueueEmpty(priorityQueues)){
				timeline.add("-");
				t++;
			}
			else { // run process in the front of queue for 1 quantum
				if (curr.getStartTime() == -1) {
					if (t > 99) { // no process should START after 99, remove
						// processes that are left
						for(Queue<Process> q : priorityQueues){
							for(Process p : q){
								processes.remove(p);
							}
						}
						break;
					}
					curr.setStartTime(t);
				}
				while(curr.getRemainingTime() > 0){ //while not done
					timeline.add(curr.getName());
					curr.executeProcess(t); // this updates remaining time and
					t++;
				}
				priorityQueues.get(name).remove(curr);
			}
		}
		System.out.println(timeline);
	}

	// for testing
	public ArrayList<Process> getSortedQueue() {
		return processes;
	}

}
