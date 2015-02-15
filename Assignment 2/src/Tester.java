import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;

public class Tester {

    public static void main(String[] args) {
	ArrayList<Process> processes = new ArrayList<>();
	int numProcesses = 26; // Will find a better number later
	char name = 'A';
	for (int i = 0; i < numProcesses; i++) {
	    processes.add(new Process(Character.toString(name)));
	    name++;
	}

	// sort queue by arrival time
	Collections.sort(processes, new Comparator<Process>() {
	    public int compare(Process p1, Process p2) {
		if (p1.getArrivalTime() == p2.getArrivalTime())
		    return 0;
		return p1.getArrivalTime() < p2.getArrivalTime() ? -1 : 1;
	    }
	});

	FCFSAlgorithm FCFS = new FCFSAlgorithm(processes);
	FCFS.run();
	// System.out.println("FCFS Timeline: " + FCFS.createTimeline());

	String test = "Arrival Times: ";
	for (int i = 0; i < FCFS.getSortedQueue().size(); i++) {
	    test += FCFS.getSortedQueue().get(i).getArrivalTime() + " ";
	}
	test += "\nBurst Times: ";
	for (int i = 0; i < FCFS.getSortedQueue().size(); i++) {
	    test += FCFS.getSortedQueue().get(i).getRunTime() + " ";
	}
	test += "\nStart Times: ";
	for (int i = 0; i < FCFS.getSortedQueue().size(); i++) {
	    test += FCFS.getSortedQueue().get(i).getStartTime() + " ";
	}
	test += "\nFinish Times: ";
	for (int i = 0; i < FCFS.getSortedQueue().size(); i++) {
	    test += FCFS.getSortedQueue().get(i).getFinishTime() + " ";
	}
	test += "\nAverage Turnaround Time: " + FCFS.avTurnAroundTime()
		+ "\nAverage Wait Time: " + FCFS.avWaitingResponse()
		+ " \nAverage Response Time: " + FCFS.avResponseTime();
	System.out.println(test);

	// hard coded processes
	processes.clear();
	processes.add(new Process("A", (float) 12, (float) 0, 1));
	processes.add(new Process("B", (float) 6, (float) 1, 1));
	processes.add(new Process("C", (float) 9, (float) 4, 1));

	FCFS = new FCFSAlgorithm(processes);
	FCFS.run();
	// System.out.println("\nTEEEESSSST: \nFCFS Timeline: "
	// + FCFS.createTimeline());

	test = "Arrival Times: ";
	for (int i = 0; i < FCFS.getSortedQueue().size(); i++) {
	    test += FCFS.getSortedQueue().get(i).getArrivalTime() + " ";
	}
	test += "\nBurst Times: ";
	for (int i = 0; i < FCFS.getSortedQueue().size(); i++) {
	    test += FCFS.getSortedQueue().get(i).getRunTime() + " ";
	}
	test += "\nStart Times: ";
	for (int i = 0; i < FCFS.getSortedQueue().size(); i++) {
	    test += FCFS.getSortedQueue().get(i).getStartTime() + " ";
	}
	test += "\nFinish Times: ";
	for (int i = 0; i < FCFS.getSortedQueue().size(); i++) {
	    test += FCFS.getSortedQueue().get(i).getFinishTime() + " ";
	}
	test += "\nAverage Turnaround Time: Calculated = "
		+ FCFS.avTurnAroundTime()
		+ " Actual = 17.33\nAverage Wait Time: Calculated = "
		+ FCFS.avWaitingResponse()
		+ ", Actual = 8.33 \nAverage Response Time: "
		+ FCFS.avResponseTime();
	System.out.println(test);

    }

}
