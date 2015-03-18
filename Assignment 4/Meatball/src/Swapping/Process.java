package Swapping;


import java.util.Random;

/**
 * Represents a process uses a random amount of space and has a random duration.
 */
public class Process {
	private char name;
	private int size;
	private int duration;
	private int finish;
	private int index;
	private static final int[] SIZES = { 5, 11, 17, 31 };
	private static final int[] DURATIONS = { 1, 2, 3, 4, 5 };

	public Process(char name) {
		this.name = name;
		Random rand = new Random();
		size = SIZES[rand.nextInt(SIZES.length)];
		duration = DURATIONS[rand.nextInt(DURATIONS.length)];
		finish = 0;
	}
	
	public Process(Process p){
		this.name = p.getName();
		this.size = p.getSize();
		this.duration = p.getDuration();
		this.finish = 0;
		this.index = 0;
	}

	public char getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public int getDuration() {
		return duration;
	}
	
	public void setFinish(int time){
		finish = time + duration;
	}
	
	public int getFinish(){
		return finish;
	}
	
	public int getIndex(){
		return index;
	}
	
	public void setIndex(int i){
		index = i;
	}

	@Override
	public String toString() {
		return "[Process: " + name + ", Size: " + size + ", Duration: "
				+ duration + "]";
	}
}
