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
		for (Process p : list) {
			s += " " + p.getName() + "      ";
		}
		s += "\nArrivalTimes:";
		for (Process p : list) {
			s += String.format(" %-6.2f ", p.getArrivalTime());
		}
		s += "\nBurst Times: ";
		for (Process p : list) {
			s += String.format(" %-6.2f ", p.getRunTime());
		}
		s += "\nPriority:    ";
		for (Process p : list) {
			s += String.format("   %2d   ", p.getPriority());
		}
		s += "\n";
		System.out.print(s);
	}

	public static void printStatistics(Algorithm alg) {
		ArrayList<Process> list = alg.getProcesses();
		String s = "Processes:   ";
		for (int i = 0; i < list.size(); i++) {
			s += " " + list.get(i).getName() + "      ";
		}
		s += "\nStart Times: ";
		for (int i = 0; i < list.size(); i++) {
			s += String.format(" %-6.2f ", list.get(i).getStartTime());
		}
		s += "\nFinish Times:";
		for (int i = 0; i < list.size(); i++) {
			s += String.format(" %-6.2f ", list.get(i).getFinishTime());
		}
		s += "\nThroughput per 100 Quanta: "
				+ ((float) list.size() / alg.getTimeline().size()) * 100;
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
		printProcesses(processes);
		System.out.println();
		System.out.println("FCFS:");
		Algorithm alg = new FCFSAlgorithm(processes);
		System.out.println(alg.getTimeline());
		printStatistics(alg);
		
		System.out.println("\nRR:");
		alg = new RRAlgorithm(processes);
		System.out.println(alg.getTimeline());
		printStatistics(alg);
		
		System.out.println("\nSJF:");
		alg = new SJFAlgorithm(processes);
		System.out.println(alg.getTimeline());
		printStatistics(alg);
		
		System.out.println("\nSRT:");
		alg = new SRTAlgorithm(processes);
		System.out.println(alg.getTimeline());
		printStatistics(alg);
		
		System.out.println("\nHPF Non-preemptive:");
		HPFAlgorithmNP alg2 = new HPFAlgorithmNP(processes);
		System.out.println(alg2.getTimeline());
		printStatistics(alg2);
		alg2.printQueueStats();
		
		System.out.println("\nHPF preemptive:");
		HPFAlgorithmP alg3 = new HPFAlgorithmP(processes);
		System.out.println(alg3.getTimeline());
		printStatistics(alg3);
		alg3.printQueueStats();

		// hard coded processes
//		processes.clear();
//		processes.add(new Process("A", (float) 12, (float) 0, 1));
//		processes.add(new Process("B", (float) 6, (float) 1, 1));
//		processes.add(new Process("C", (float) 9, (float) 4, 1));
//		processes.add(new Process("D", (float) 4, (float) 1, 1));
//		processes.add(new Process("E", (float) 5, (float) 0, 1));
//		sort(processes);
//
//		System.out.println();
//		Algorithm alg2 = new RRAlgorithm(processes);
//		System.out.println(alg2.getTimeline());
//		printProcesses(processes);
//		printStatistics(alg2);
	}

}
