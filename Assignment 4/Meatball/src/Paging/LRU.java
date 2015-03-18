package Paging;

public class LRU extends Algorithm {

	private int[] lastUse = new int[10];
	
	public LRU(int[] pageReferences) {
		super(pageReferences);
	}
	
	@Override
	public void run() {
		//go through all the numbers in pageReferences
		for(int i = 0; i < pageReferences.length; i++){
			lastUse[pageReferences[i]] = i;
			boolean pageFault = true;
			//go through each page frame
			for (int frame : pageFrames){
				if (pageReferences[i] == frame) {
					pageFault = false;
				}
			}
			
			if (pageFault) {
				
				int evicted = pageReplace(pageReferences[i]);
				System.out.println(pageReferences[i] + ": " + frameString()
						+ "\tPage " + evicted + " replaced by "
						+ "Page " + pageReferences[i]);
				missCount++;
			} else {
				System.out.println(pageReferences[i] + ": " + frameString());
				hitCount++;
			}
		}		
		System.out.println("Hit Count: " + hitCount);
	}

	public int pageReplace(int referenced) {
		int minLastUse = 101;
		int index = 0;
		int evicted = -1;
		for (int i = 0; i<NUM_FRAMES; i++) {
			if (pageFrames[i] == -1){
				evicted = pageFrames[i];
				pageFrames[i] = referenced;
				return evicted;
			} else if (minLastUse > lastUse[pageFrames[i]]) { 
				index = i;
				minLastUse = lastUse[pageFrames[i]];
				evicted = pageFrames[i];
			}
		}
		pageFrames[index] = referenced;
		return evicted;
	}

}
