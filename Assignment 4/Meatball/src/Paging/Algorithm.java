package Paging;

public abstract class Algorithm {
	protected static final int NUM_FRAMES = 4;
	protected int[] pageReferences;
	protected int[] pageFrames = new int[NUM_FRAMES];
	protected int hitCount = 0;
	protected int missCount = 0; 

	public Algorithm(int[] pageReferences) {
		this.pageReferences = pageReferences;
		for (int i = 0; i < NUM_FRAMES; i++)
			pageFrames[i] = -1;
		
	}

	public abstract void run();
	
	public String frameString(){
		String output = "";
		for (int frame : pageFrames){
			if (frame == -1)
				output += "  ";
			else
				output += frame + " ";
		}
		return output;
	}
	public int getHitCount() {
		return hitCount;
	}

	public int getMissCount() {
		return missCount;
	}
}
