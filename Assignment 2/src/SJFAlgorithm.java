import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SJFAlgorithm implements Algorithm {
	
	public SJFAlgorithm(ArrayList<Process> processes) {
		sortProcesses(processes);
		printTimeline(processes);
		System.out.println(processes);
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
				if (p1.getRunTime() == p2.getRunTime())
					return 0;
				return p1.getRunTime() < p2.getRunTime() ? -1 : 1;
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

}
