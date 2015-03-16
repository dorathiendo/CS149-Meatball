

import java.util.*;

public class Tester {

	private static final int NUM_PROCESSES = 120;
	
	public static void main(String[] args){
		int[] sumSwaps = new int[4];
		System.out.println("Entering Tester");
		
		for (int i = 0; i<5; i++){
			LinkedList<Process> queue = new LinkedList<Process>();
			char tmp;
			for(int j = 0; j<NUM_PROCESSES; j++){
				tmp = (char) ('A' + (char) (j%26));
				queue.add(new Process(tmp));
			}
			
			System.out.println("RUN " + i + "\nFirst Fit:");
			FirstFit firstfit = new FirstFit(queue);
			firstfit.run();
			sumSwaps[0] = sumSwaps[0] + firstfit.getSwapCount();
			
			System.out.println("Next Fit:");
			NextFit nextfit = new NextFit(queue);
			nextfit.run();
			sumSwaps[1] = sumSwaps[1] + nextfit.getSwapCount();
			
			System.out.println("Best Fit:");
			BestFit bestfit = new BestFit(queue);
			bestfit.run();
			sumSwaps[2] = sumSwaps[2] + bestfit.getSwapCount();
			
			System.out.println("Worst Fit:");
			WorstFit worstfit = new WorstFit(queue);
			worstfit.run();
			sumSwaps[3] = sumSwaps[3] + worstfit.getSwapCount();
			
			System.out.println("----------------------------------\n");
		}
		
		System.out.println("Average Number of Swapped Processes:");
		System.out.println("First Fit: " + (sumSwaps[0]/5.0));
		System.out.println("Next Fit: " + (sumSwaps[1]/5.0));
		System.out.println("Best Fit: " + (sumSwaps[2]/5.0));
		System.out.println("Worst Fit: " + (sumSwaps[3]/5.0));
	}
}
