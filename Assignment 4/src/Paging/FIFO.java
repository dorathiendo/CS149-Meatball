package Paging;

public class FIFO extends Algorithm {

	private int[] entryTimes = new int[10];
	private int rank = 0;
	
	public FIFO(int[] pageReferences) {
		super(pageReferences);
	}

	@Override
	public int pageReplace(int referenced) {
		int minRank = 100;
		int index = 0;
		int evicted = -1;
		for (int i = 0; i<NUM_FRAMES; i++) {
			if (pageFrames[i] == -1){
				evicted = pageFrames[i];
				pageFrames[i] = referenced;
				entryTimes[referenced] = rank++;
				return evicted;
			} else if (minRank > entryTimes[pageFrames[i]]) { 
				index = i;
				minRank = entryTimes[pageFrames[i]];
				evicted = pageFrames[i];
			}
		}
		
		entryTimes[referenced] = rank++;
		pageFrames[index] = referenced;
		return evicted;
	}

}
