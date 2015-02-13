import java.util.ArrayList;


public class SRTAlgorithm implements Algorithm {
	
	public SRTAlgorithm(ArrayList<Process> processes) {
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
		// TODO Auto-generated method stub
		return null;
	}

}
