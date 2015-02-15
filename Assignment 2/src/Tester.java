import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Tester {

    public static void sort(ArrayList<Process> processes) {
	Collections.sort(processes, new Comparator<Process>() {
	    public int compare(Process p1, Process p2) {
		if (p1.getArrivalTime() == p2.getArrivalTime())
		    return 0;
		return p1.getArrivalTime() < p2.getArrivalTime() ? -1 : 1;
	    }
	});
    }

    public static void printProcesses(ArrayList<Process> list) {
	String s = "Processes:   ";
	for (Process p: list){
	    s += " " + p.getName() + "      ";
	}
	s += "\nArrivalTimes:";
	for (Process p: list) {
	    s += String.format(" %-6.2f ", p.getArrivalTime());
	}
	s += "\nBurst Times: ";
	for (Process p: list) {
	    s += String.format(" %-6.2f ", p.getRunTime());
	}
	s += "\n";
	System.out.print(s);
    }

    public static void printStatistics(Algorithm alg) {
	ArrayList<Process> list = alg.getProcesses();
	String s = "Start Times: ";
	for (int i = 0; i < list.size(); i++) {
	    s += String.format(" %-6.2f ", list.get(i).getStartTime());
	}
	s += "\nFinish Times:";
	for (int i = 0; i < list.size(); i++) {
	    s += String.format(" %-6.2f ", list.get(i).getFinishTime());
	}
	s += "\nAverage Turnaround Time: Calculated = "
		+ alg.avTurnAroundTime() + "\nAverage Wait Time: Calculated = "
		+ alg.avWaitingResponse() + "\nAverage Response Time: "
		+ alg.avResponseTime();
	System.out.println(s);
    }

    public static void main(String[] args) {
	ArrayList<Process> processes = new ArrayList<>();
	int numProcesses = 26; // Will find a better number later
	char name = 'A';
	for (int i = 0; i < numProcesses; i++) {
	    processes.add(new Process(Character.toString(name)));
	    name++;
	}
	sort(processes);
	Algorithm alg = new SRTAlgorithm(processes);
	System.out.println(alg.getTimeline());
	printProcesses(processes);
	printStatistics(alg);
	
	// hard coded processes
	processes.clear();
	processes.add(new Process("A", (float) 12, (float) 0, 1));
	processes.add(new Process("B", (float) 6, (float) 1, 1));
	processes.add(new Process("C", (float) 9, (float) 4, 1));
	processes.add(new Process("D", (float) 4, (float) 1, 1));
	sort(processes);

	System.out.println();
	alg = new FCFSAlgorithm(processes);
	System.out.println(alg.getTimeline());
	printProcesses(processes);
	printStatistics(alg);
    }

}
