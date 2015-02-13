import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class HPFAlgorithm implements Algorithm {

	private ArrayList<Process> readyQueue;
	
	public HPFAlgorithm(ArrayList<Process> processes){
		readyQueue = sortProcesses(processes);
	}

	@Override
	public float avTurnAroundTime() {
		float turnaroundTime = 0;
		for(int i = 0; i < readyQueue.size(); i++) {
			turnaroundTime += readyQueue.get(i).getRunTime() - readyQueue.get(i).getArrivalTime();
		}
		float result = turnaroundTime / (float) readyQueue.size();
		return result;
	}

	@Override
	public float avWaitingResponse() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float avResponseTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Process> sortProcesses(ArrayList<Process> proc) {
		Collections.sort(proc, new Comparator<Process>() {
			public int compare(Process p1, Process p2) {
				if (p1.getPriority() == p2.getPriority())
					return 0;
				return p1.getPriority() < p2.getPriority() ? -1 : 1;
			}
		});
		return proc;
	}

}
