import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class RRAlgorithm implements Algorithm {
	
	public RRAlgorithm(ArrayList<Process> processes) {
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
