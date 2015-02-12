import java.util.ArrayList;


public class SRTAlgorithm implements Algorithm {

	private ArrayList<Process> readyQueue;
	
	public SRTAlgorithm(ArrayList<Process> processes) {
		sortProcesses(processes);
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
		// TODO Auto-generated method stub
		return null;
	}

}
