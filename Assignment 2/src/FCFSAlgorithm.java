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
		System.out.println(finishTime(processes));
		printTimeline(processes);
		System.out.println(processes);
		System.out.println("Avg turnaround:" + avTurnAroundTime(processes, finishTime(processes)));
	}

	//test this
	public float avTurnAroundTime(ArrayList<Process> proc, ArrayList<Float> fTimes) {
		float turnaroundTime = 0;
		for(int i = 0; i < proc.size(); i++) {
			turnaroundTime += fTimes.get(i) - proc.get(i).getRunTime();
		}
		float result = turnaroundTime / (float) proc.size();
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
				if (p1.getArrivalTime() == p2.getArrivalTime())
					return 0;
				return p1.getArrivalTime() < p2.getArrivalTime() ? -1 : 1;
			}
		});
		return proc;
	}

	@Override
	public void printTimeline(ArrayList<Process> proc) {
		for(int i = 0; i < proc.size(); i++){
			System.out.print(proc.get(i).getName());
		}
		System.out.println();
	}

	public ArrayList<Float> finishTime(ArrayList<Process> proc) {
		ArrayList<Float> finishTimes = new ArrayList<Float>();
		finishTimes.add(proc.get(0).getRunTime());
		float time = proc.get(0).getRunTime();
		for(int i = 1; i < proc.size(); i++) {
			for(int j = i; j > 0; j--) {
				time += proc.get(j).getRunTime();
			}
			finishTimes.add(time);
		}
		return finishTimes;
	}


	@Override
	public float avTurnAroundTime(ArrayList<Process> proc) {
		// TODO Auto-generated method stub
		return 0;
	}

}
