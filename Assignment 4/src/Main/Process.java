package Main;

import java.util.Random;

/**
 * Represents a process uses a random amount of space and has a random duration.
 */
public class Process {
	private char name;
	private int size;
	private int duration;
	private static final int[] SIZES = { 5, 11, 17, 31 };
	private static final int[] DURATIONS = { 1, 2, 3, 4, 5 };

	public Process(char name) {
		this.name = name;
		Random rand = new Random();
		size = SIZES[rand.nextInt(SIZES.length)];
		duration = DURATIONS[rand.nextInt(DURATIONS.length)];
	}
	
	public Process(Process p){
		this.name = p.getName();
		this.size = p.getSize();
		this.duration = p.getDuration();
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

	@Override
	public String toString() {
		return "Process: " + name + "\nSize: " + size + "\nDuration: "
				+ duration;
	}
}
