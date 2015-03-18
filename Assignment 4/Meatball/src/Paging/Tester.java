package Paging;

import java.util.*;

public class Tester {

	private static final int NUM_REFERENCES = 100;

	/**
	 * Returns a pseudo-random number between min and max, inclusive. The
	 * difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 * 
	 * @param min
	 *            Minimum value
	 * @param max
	 *            Maximum value. Must be greater than min.
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
		
		int[] sumHits = new int[5];
		for (int i = 0; i < 5; i++) {
			int[] pageReferences = new int[NUM_REFERENCES];
			pageReferences[0] = 0;
			for (int j = 1; j < NUM_REFERENCES; j++) {
				int r = randInt(0, 9);
				if (r >= 0 && r < 7)
					pageReferences[j] = (pageReferences[j - 1] + randInt(-1, 1)) % 10;
				else
					pageReferences[j] = (pageReferences[j - 1] + randInt(2, 8)) % 10;
				if (pageReferences[j] == -1)
					pageReferences[j] = 9;
			}

			String p = "";
			for (int j = 0; j < NUM_REFERENCES; j++)
				p += pageReferences[j] + " ";
			System.out.println(p);

			System.out.println("\nRandom Pick: ");
			RandomPick randompick = new RandomPick(pageReferences);
			randompick.run();
			sumHits[0] += randompick.getHitCount();

			System.out.println("\nFIFO: ");
			FIFO fifo = new FIFO(pageReferences);
			fifo.run();
			sumHits[1] += fifo.getHitCount();

			System.out.println("\nLFU: ");
			LFU lfu = new LFU(pageReferences);
			lfu.run();
			sumHits[2] += lfu.getHitCount();

			System.out.println("\nMFU: ");
			MFU mfu = new MFU(pageReferences);
			mfu.run();
			sumHits[3] += mfu.getHitCount();

			System.out.println("\nLRU: ");
			LRU lru = new LRU(pageReferences);
			lru.run();
			sumHits[4] += lru.getHitCount();
			
			System.out.println("\n");
		}
		System.out.println("Hit Ratios:");
		System.out.println("Random Pick: " + sumHits[0]/500.0);
		System.out.println("FIFO: " + sumHits[1]/500.0);
		System.out.println("LFU: " + sumHits[2]/500.0);
		System.out.println("MFU: " + sumHits[3]/500.0);
		System.out.println("LRU: " + sumHits[4]/500.0);
	}

}
