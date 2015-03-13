package Main;

import java.util.*;

public class Tester {

	private static final int NUM_PROCESSES = 120;
	private static LinkedList<Process> queue = new LinkedList<Process>();
	
	public static void main(String[] args){
		System.out.println("Entering Tester");
		
		//add processes to queue
		char tmp = 'A';
		for(int i = 0; i<NUM_PROCESSES; i++){
			queue.add(new Process(tmp));
			tmp = (char) ('A' + (char) (i%26));
		}
		
		FirstFit firstfit = new FirstFit(queue);
		firstfit.run();
		
		
		
//		Run each algorithm 5 times simulating 1 minute each time to get an average of the
//		number of processes successfully swapped into memory (but not necessarily completed)
//		during the minute. Generate enough new random processes before each run so that the
//		job queue is never empty. According to the algorithm, allocate them into memory in the
//		order that you generated them.

//		For each algorithm, print the average number of processes that were successfully
//		swapped in (but not necessarily completed). Each time a process is swapped in, or a
//		process completes and therefore is removed from memory, print a memory map, e.g.,
//		AAAAA....BBBBBBBB..CCCC where the characters are the process names (one
//		character per MB) and the dots are holes (one per MB). Indicate which process entered
//		or left. For an entering process, also print its size and duration.
//		
//		For each algorithm, print the average number of processes (over the 5 runs) that were
//		successfully swapped in.
	}
}
