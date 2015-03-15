import java.util.LinkedList;

public class NextFit extends Algorithm {

	private int index = 0;

	public NextFit(LinkedList<Process> processesIn) {
		super(processesIn);
	}

	public int findHoleIndex(Process p) {
		int start = index;
		int holeStart = index;
		int holeSize = 0;
		//check for suitable hole between current index and end of bitmap
		while (index < bitmap.length) {
			while (index < bitmap.length && bitmap[index] != '.') {
				index++;
			}
			holeStart = index;
			while (index < bitmap.length && bitmap[index] == '.') {
				holeSize++;
				index++;
			}
			if (p.getSize() <= holeSize) {
				index = holeStart + p.getSize();
				return holeStart;
			}
			holeSize = 0;
		}
		//check for suitable hole between start of bitmap original location
		index = 0;
		while (index < start) {
			while (index < start && bitmap[index] != '.') {
				index++;
			}
			holeStart = index;
			while (index < start && bitmap[index] == '.') {
				holeSize++;
				index++;
			}
			if (p.getSize() <= holeSize) {
				index = holeStart + p.getSize();
				return holeStart;
			}
			holeSize = 0;
		}
		return -1;
	}
}
