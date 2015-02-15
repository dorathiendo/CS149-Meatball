import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;

public class Tester {

	public static void sort(ArrayList<Process> processes){
		Collections.sort(processes, new Comparator<Process>() {
			public int compare(Process p1, Process p2) {
				if (p1.getArrivalTime() == p2.getArrivalTime())
					return 0;
				return p1.getArrivalTime() < p2.getArrivalTime() ? -1 : 1;
			}
		});
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


		// sort queue by arrival time


		SJFAlgorithm alg = new SJFAlgorithm(processes);
		alg.run();
		// System.out.println("FCFS Timeline: " + FCFS.createTimeline());

		String test = "Arrival Times: ";
		for (int i = 0; i < alg.getSortedQueue().size(); i++) {
			test += "(" + alg.getSortedQueue().get(i).getName()+", "
					+ alg.getSortedQueue().get(i).getArrivalTime() + ") ";
		}
		test += "\nBurst Times: ";
		for (int i = 0; i < alg.getSortedQueue().size(); i++) {
			test += alg.getSortedQueue().get(i).getRunTime() + " ";
		}
		test += "\nStart Times: ";
		for (int i = 0; i < alg.getSortedQueue().size(); i++) {
			test += alg.getSortedQueue().get(i).getStartTime() + " ";
		}
		test += "\nFinish Times: ";
		for (int i = 0; i < alg.getSortedQueue().size(); i++) {
			test += alg.getSortedQueue().get(i).getFinishTime() + " ";
		}
		test += "\nAverage Turnaround Time: " + alg.avTurnAroundTime()
				+ "\nAverage Wait Time: " + alg.avWaitingResponse()
				+ " \nAverage Response Time: " + alg.avResponseTime();
		System.out.println(test);

		// hard coded processes
		//processes.clear();
//		processes.add(new Process("A", (float) 12, (float) 0, 1));
//		processes.add(new Process("B", (float) 6, (float) 1, 1));
//		processes.add(new Process("C", (float) 9, (float) 4, 1));
//		processes.add(new Process("D", (float) 4, (float) 1, 1));

		
//		System.out.println();
//		SJFAlgorithm alg = new SJFAlgorithm(processes);
//		alg.run();
//
//		String test = "Arrival Times: ";
//		for (int i = 0; i < alg.getSortedQueue().size(); i++) {
//			test += alg.getSortedQueue().get(i).getArrivalTime() + " ";
//		}
//		test += "\nBurst Times: ";
//		for (int i = 0; i < alg.getSortedQueue().size(); i++) {
//			test += alg.getSortedQueue().get(i).getRunTime() + " ";
//		}
//		test += "\nStart Times: ";
//		for (int i = 0; i < alg.getSortedQueue().size(); i++) {
//			test += alg.getSortedQueue().get(i).getStartTime() + " ";
//		}
//		test += "\nFinish Times: ";
//		for (int i = 0; i < alg.getSortedQueue().size(); i++) {
//			test += alg.getSortedQueue().get(i).getFinishTime() + " ";
//		}
//		test += "\nAverage Turnaround Time: Calculated = "
//				+ alg.avTurnAroundTime()
//				+ "\nAverage Wait Time: Calculated = "
//				+ alg.avWaitingResponse()
//				+ "\nAverage Response Time: "
//				+ alg.avResponseTime();
//		System.out.println(test);

	}

}
