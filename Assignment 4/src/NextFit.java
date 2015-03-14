import java.util.LinkedList;



public class NextFit extends Algorithm {
	
	private int nextHole = 0;
	
	public NextFit(LinkedList<Process> processesIn) {
		super(processesIn);
	}

	public int findHoleIndex(Process p) {
		int i = 0;
		int holeStart = nextHole;
		int holeSize = 0;
		while (i < 100) {
			while (i < 100 && bitmap[i] != '.') {
				i++;
			}
			holeStart = i;
			while (i < 100 && bitmap[i] == '.') {
				if(holeStart >= 100)
					holeStart = 0;
				else
					holeSize++;
				i++;
			}
			if (p.getSize() <= holeSize){
				nextHole = holeStart;
				// System.out.println("HOLE STARTS AT: " + holeStart);
				return holeStart;
			} 
			holeSize = 0;
		}
		return -1;
	}

}
