package Main;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Algorithm {
	
	protected char[] bitmap = new char[100];
	protected LinkedList<Process> processes;
	protected int swapCount = 0;
	
	public Algorithm(LinkedList<Process> processesIn) {
		// deep copy of processes
		printBitmap(); //print init bitmap
		processes = new LinkedList<Process>();
		for (Process p : processesIn)
			processes.add(new Process(p));
		
		for(int i = 0; i<100; i++)
			bitmap[i] = '.';
	}
	
	public abstract void run();
	
	public abstract int findHoleIndex(Process p);
	
	public void printBitmap(){
		System.out.println("Bitmap: " + bitmap);		
	}
	
	public int getNumberOfProcesses(){
		return swapCount;
	}
	
}
