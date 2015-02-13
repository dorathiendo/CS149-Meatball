import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

public class FCFSAlgorithm implements Algorithm {

	private ArrayList<Process> readyQueue;
	private LinkedList<String> timeline;
	private float avTurnAroundTime;
	private float avWaitingResponse;
	private float avResponseTime;

	/**
	 * Creates an algorithm object that runs the processes sent through the
	 * parameter.
	 * 
	 * @param processes
	 */
	public FCFSAlgorithm(ArrayList<Process> processes) {
		readyQueue = sortProcesses(processes);
		timeline = createTimeline(); //calculates all start times + timeline w/ no spaces
	}

	@Override
	public float avTurnAroundTime(ArrayList<Process> proc) {
		float turnaroundTime = 0;
		for(int i = 0; i < proc.size(); i++) {
			turnaroundTime += proc.get(i).getRunTime() - proc.get(i).getArrivalTime();
		}
		avTurnAroundTime = turnaroundTime / (float) proc.size();
		return avTurnAroundTime;
	}

	@Override
	public float avWaitingResponse() {
		float waitTime = readyQueue.get(0).getStartTime();
		for(int i = 1; i < readyQueue.size(); i++){
			waitTime += readyQueue.get(i).getStartTime() - readyQueue.get(i).getArrivalTime();
		}
		avWaitingResponse = waitTime / (float) readyQueue.size();
		return avWaitingResponse;
	}

	@Override
	public float avResponseTime() {
		float responseTime = 0;
		for(int i = 0; i < readyQueue.size(); i++){
			responseTime += readyQueue.get(i).getStartTime();
		}
		avResponseTime = responseTime / (float) readyQueue.size();
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
	
	//just for FCFS, gets start time for each process as well
	public LinkedList<String> createTimeline(){
		Process curr = readyQueue.get(0); //first process
		LinkedList<String> timeline = new LinkedList<String>();
		timeline.add(curr.getName()); //you guys can change this to whatever data structure
		
		float t = curr.getArrivalTime(); //first start time
		readyQueue.get(0).setStartTime(curr.getArrivalTime()); //sets first process
		
		for(int i = 1; i < readyQueue.size(); i++){	
			t += curr.getRunTime(); //calculate new start time
			curr = readyQueue.get(i); //next process
			
			if(t < curr.getArrivalTime()) //if current process starts later
				t = curr.getArrivalTime();
			
			readyQueue.get(i).setStartTime(t);
			timeline.add(curr.getName());
		}
		return timeline;
	}
	
	//for testing
	public ArrayList<Process> getSortedQueue(){
		return readyQueue;
	}
	
//	public void setStartTimes(){;
//		Process curr = readyQueue.get(0); //first process
//		float time = curr.getArrivalTime(); //first start time
//		readyQueue.get(0).setStartTime(curr.getArrivalTime()); //sets first process
//		
//		for(int i = 1; i < readyQueue.size(); i++){	
//			time = time + curr.getRunTime(); //calculate new start time
//			curr = readyQueue.get(i); //next process
//			
//			if(time < curr.getArrivalTime()) //if current process starts later
//				time = curr.getArrivalTime();
//			
//			readyQueue.get(i).setStartTime(time);
//		}
//	}

}
