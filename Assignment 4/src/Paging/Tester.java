package Paging;

import java.util.*;

public class Tester {
	
	private static final int NUM_REFERENCES = 100;

	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static void main(String[] args) {
		int[] pageReferences = new int[NUM_REFERENCES];
		pageReferences[0] = 0;
		for (int i = 1 ; i<NUM_REFERENCES; i++){
			int r = randInt(0, 9);
			if (r >= 0 && r < 7)
				pageReferences[i] = (pageReferences[i - 1] + randInt(-1, 1)) % 10;
			else
				pageReferences[i] = (pageReferences[i - 1] + randInt(2, 8)) % 10;
			if (pageReferences[i] == -1)
				pageReferences[i] = 9;
		}
	}

}
