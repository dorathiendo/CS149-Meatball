package Paging;

import java.util.Random;

public class RandomPick extends Algorithm {
	
	public RandomPick(int[] pageReferences) {
		super(pageReferences);
	}

	@Override
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
