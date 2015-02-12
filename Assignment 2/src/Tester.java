import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Tester {

	public static void main(String[] args) {
		ArrayList<Process> readyQueue = new ArrayList<>();
		int numProcesses = 10; // Will find a better number later
		char name = 'A';
		for (int i = 0; i < numProcesses; i++) {
			readyQueue.add(new Process(Character.toString(name)));
			name++;
		}
		// sort queue by arrival time
		Collections.sort(readyQueue, new Comparator<Process>() {
			public int compare(Process p1, Process p2) {
				if (p1.getArrivalTime() == p2.getArrivalTime())
					return 0;
				return p1.getArrivalTime() < p2.getArrivalTime() ? -1 : 1;
			}
		});
		
		FCFSAlgorithm FCFS = new FCFSAlgorithm(readyQueue);
	}

}
