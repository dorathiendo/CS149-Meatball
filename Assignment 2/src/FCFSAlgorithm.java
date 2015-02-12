import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class FCFSAlgorithm implements Algorithm {

	private ArrayList<Process> readyQueue;
	private LinkedList<String> timeline;
	private float avgTurnAroundTime;
	private float avgResponseTime;
	private float avgArrivalTime;

	/**
	 * Creates an algorithm object that runs the processes sent through the
	 * parameter.
	 * 
	 * @param processes
	 */
	public FCFSAlgorithm(ArrayList<Process> processes) {
		readyQueue = processes;
		sortProcesses(processes);
	}

	@Override
	public int avTurnAroundTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int avWaitingResponse() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int avResponseTime() {
		// TODO Auto-generated method stub
		return 0;
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

}
