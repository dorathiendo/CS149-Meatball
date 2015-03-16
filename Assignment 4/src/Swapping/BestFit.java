package Swapping;
import java.util.LinkedList;



public class BestFit extends Algorithm {
		
	public BestFit(LinkedList<Process> processesIn) {
		super(processesIn);
	}

	public int findHoleIndex(Process p) {
		int i = 0;
		int bestFitStart = -1;  //the current smallest suitable hole start
		int bestFitSize = 101; //the current smallest suitable hole size
		int holeStart = 0;
		int holeSize = 0;
		while (i < bitmap.length) {
			while (i < bitmap.length && bitmap[i] != '.') {
				i++;
			}
			holeStart = i;
			while (i < bitmap.length && bitmap[i] == '.') {
				holeSize++;
				i++;
			}
			if (p.getSize() <= holeSize  && holeSize < bestFitSize){
				bestFitSize = holeSize;
				bestFitStart = holeStart;
			} 
			holeSize = 0;
		}
		return bestFitStart;
	}
}
