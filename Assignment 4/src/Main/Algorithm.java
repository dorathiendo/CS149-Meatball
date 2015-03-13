package Main;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Algorithm {

	protected char[] bitmap = new char[100];
	protected LinkedList<Process> processes;
	protected int swapCount = 0;
	protected int time = 0;
	protected ArrayList<Process> running;

	public Algorithm(LinkedList<Process> processesIn) {
		// deep copy of processes
		processes = new LinkedList<Process>();
		running = new ArrayList<Process>();
		for (Process p : processesIn)
			processes.add(new Process(p));
		for (int i = 0; i < 100; i++)
			bitmap[i] = '.';
	}

	public void run() {
		Process temp = processes.get(0);
		while(time < 60)
		{
			killFinishedProcesses();
			int hole = findHoleIndex(temp);
			while (hole != -1){
				swapProcess(hole, temp);
				if(!processes.isEmpty()){
					temp = processes.peek();
					hole = findHoleIndex(temp);
				} else {
					break;
				}
			}
			time++;
		}
	}

	public void swapProcess(int index, Process p) {
		for (int i = index; i < index + p.getSize(); i++)
			bitmap[i] = p.getName();
		swapCount++;
		p.setIndex(index);
		p.setFinish(time);
		
		running.add(processes.pop());
		System.out.println(p);
		printBitmap();
	}

	public void killFinishedProcesses() {
		Process p;
		for (int i = 0; i < running.size(); i++){
			p = running.get(i);
			if (p.getFinish() <= time) {
				for (int j = p.getIndex(); j < p.getIndex() + p.getSize(); j++)
					bitmap[j] = '.';
				printBitmap();
				running.remove(p);
			}
		}
	}

	public abstract int findHoleIndex(Process p);

	public void printBitmap() {
		String b = "Bitmap: ";
		for (int i = 0; i < bitmap.length; i++)
			b = b + bitmap[i];
		System.out.println(b+"\n");
	}

	public int getNumberOfProcesses() {
		return swapCount;
	}

}
