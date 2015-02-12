import java.util.ArrayList;
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

}
