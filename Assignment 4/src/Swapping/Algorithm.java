package Swapping;


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

		while (time < 60) {
			System.out.println("TIME = " + time);
			killFinishedProcesses();
			if (!processes.isEmpty()) {
				Process nextProcess = processes.peek();
				int hole = findHoleIndex(nextProcess);
				while (hole != -1) {
					swapProcess(hole, nextProcess);
					if (!processes.isEmpty()) {
						nextProcess = processes.peek();
						hole = findHoleIndex(nextProcess);
					} else {
						break;
					}
				}
			}
			time++;
		}
		System.out.println("Processes Swapped: " + swapCount);
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
		for (int i = 0; i < running.size(); i++) {
			p = running.get(i);
			if (p.getFinish() <= time) {
				System.out.println("Removing Process " + p.getName());
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
		System.out.println(b + "\n");
	}

	public int getSwapCount() {
		return swapCount;
	}

}
