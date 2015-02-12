import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class HPFAlgorithm implements Algorithm {
	
	public HPFAlgorithm(ArrayList<Process> processes){
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
				if (p1.getPriority() == p2.getPriority())
					return 0;
				return p1.getPriority() < p2.getPriority() ? -1 : 1;
			}
		});
		return proc;
	}

}
