package Paging;

import java.util.Random;

public class RandomPick extends Algorithm {
	
	public RandomPick(int[] pageReferences) {
		super(pageReferences);
	}
	
	@Override
	public void run() {
		//go through all the numbers in pageReferences
		for(int referenced: pageReferences){
			boolean pageFault = true;
			//go through each page frame
			for (int frame : pageFrames){
				if (referenced == frame) {
					pageFault = false;
				}
			}
			
			if (pageFault) {
				int evicted = pageReplace(referenced);
				System.out.println(referenced + ": " + frameString()
						+ "\tPage " + evicted + " replaced by "
						+ "Page " + referenced);
				missCount++;
			} else {
				System.out.println(referenced + ": " + frameString());
				hitCount++;
			}
		}
		System.out.println("Hit Count: " + hitCount);		
	}

	public int pageReplace(int referenced) {
		for (int i = 0; i<NUM_FRAMES; i++) {
			if (pageFrames[i] == -1){
				int evicted = pageFrames[i];
				pageFrames[i] = referenced;
				return evicted;
			}
		}
		Random rand = new Random();
		int r = rand.nextInt(4);
		int evicted = pageFrames[r];
		pageFrames[r] = referenced;
		return evicted;
	}

}
