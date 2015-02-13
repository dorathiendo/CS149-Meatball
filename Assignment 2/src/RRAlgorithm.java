import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class RRAlgorithm implements Algorithm {
	
	public RRAlgorithm(ArrayList<Process> processes) {
		sortProcesses(processes);
	}

	@Override
	public float avTurnAroundTime(ArrayList<Process> proc) {
		float turnaroundTime = 0;
		for(int i = 0; i < proc.size(); i++) {
			turnaroundTime += proc.get(i).getRunTime() - proc.get(i).getArrivalTime();
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
		//sort into FCFS, then alternate for RR?
		return proc;
	}

	@Override
	public void printTimeline(ArrayList<Process> proc) {
		for(int i = 0; i < proc.size(); i++){
			System.out.print(proc.get(i).getName());
		}
		System.out.println();
	}

}
