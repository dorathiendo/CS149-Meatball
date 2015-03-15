import java.util.LinkedList;



public class WorstFit extends Algorithm {
		
	public WorstFit(LinkedList<Process> processesIn) {
		super(processesIn);
	}

	public int findHoleIndex(Process p) {
		int i = 0;
		int worstFitStart = -1;  //the current biggest suitable hole start
		int worstFitSize = -1; //the current biggest suitable hole size
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
			if (p.getSize() <= holeSize  && holeSize > worstFitSize){
				worstFitSize = holeSize;
				worstFitStart = holeStart;
			} 
			holeSize = 0;
		}
		return worstFitStart;
	}
}
