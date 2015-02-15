import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SJFAlgorithm implements Algorithm {

    private ArrayList<Process> readyQueue;

    public SJFAlgorithm(ArrayList<Process> processes) {
    }

    @Override
    public float avTurnAroundTime() {
	float turnaroundTime = 0;
	for (int i = 0; i < readyQueue.size(); i++) {
	    turnaroundTime += readyQueue.get(i).getRunTime()
		    - readyQueue.get(i).getArrivalTime();
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

}
