

import java.util.LinkedList;

public class FirstFit extends Algorithm {

	public FirstFit(LinkedList<Process> processesIn) {
		super(processesIn);
	}

	public int findHoleIndex(Process p) {
		int i = 0;
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
			if (p.getSize() <= holeSize){
				return holeStart;
			} 
			holeSize = 0;
		}
		return -1;
	}

}
