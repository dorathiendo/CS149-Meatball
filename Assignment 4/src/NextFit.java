import java.util.LinkedList;



public class NextFit extends Algorithm {
	
	private int index = 0;
	
	public NextFit(LinkedList<Process> processesIn) {
		super(processesIn);
	}

	public int findHoleIndex(Process p) {
		int holeStart = index;  //56
		int holeSize = 0;
		for (int i = 0; i < bitmap.length; i++){
			while (i < bitmap.length && bitmap[index] != '.') {
				index = (index + 1) % 100;
				i++;
			}
			holeStart = index;
			
			while (i < bitmap.length && bitmap[index] == '.') {
				holeSize++;
				index = (index + 1) % 100;
				i++;
			}
			
			if (p.getSize() <= holeSize){
				index = holeStart;
				System.out.println("HOLE STARTS AT: " + holeStart);
				return holeStart;
			} 
			holeSize = 0;
		}
		return -1;
	}
}
