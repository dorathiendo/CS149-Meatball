package Paging;

public abstract class Algorithm {
	protected int[] pageFrames = new int[4];
	protected int hitCount = 0;
	protected int missCount = 0;
	
	public int getHitCount(){
		return hitCount;
	}

	public int getMissCount(){
		return missCount;
	}
}
