/**
 * Assignment 2 Simple class that stores the information about a simulated
 * process.
 * 
 * @author Meatball
 */
public class Process {
	private String name;
	private float arrivalTime;
	private float runTime;
	private int priority;
	private float startTime; // -1 represents not started
	private float finishTime; // -1 represents unfinished
	private float remainingTime;
	private float latestRun; //-1 represents not started

	/**
	 * Constructor that creates a process with: -a randomly generated arrival
	 * time from 0 to 99 -a randomly expected run time from 0.1 to 10 quanta -a
	 * randomly generated priority: either 1, 2, 3, or 4
	 */
	public Process(String name) {
		this.name = name;
		// (Math.random()*range) + min
		arrivalTime = (float) (Math.random() * 99);
		runTime = (float) ((Math.random() * (9.9)) + 0.1);
		// (Math.random()*(max-min+1))+min
		priority = (int) (Math.random() * (4 - 1 + 1) + 1);
		remainingTime = runTime;
		startTime = -1;
		finishTime = -1;
	}

	// to help test
	public Process(String name, float runTime, float arrivalTime, int priority) {
		this.name = name;
		this.runTime = runTime;
		this.arrivalTime = arrivalTime;
		this.priority = priority;
		this.remainingTime = runTime;
		startTime = -1;
		finishTime = -1;
	}

	/**
	 * Clone constructor
	 * 
	 * @param another
	 */
	public Process(Process another) {
		this.name = another.getName();
		this.runTime = another.getRunTime();
		this.arrivalTime = another.getArrivalTime();
		this.priority = another.getPriority();
		this.remainingTime = runTime;
		startTime = -1;
		finishTime = -1;
	}

	/**
	 * Runs the process for 1 quanta, reducing the remaining time by 1.
	 * 
	 * @param time
	 *            the time of the quanta when it was worked on, used to
	 *            calculate finish time if necessary.
	 */
	public void executeProcess(int time) {
		latestRun = time;
		if (remainingTime > 1) {
			remainingTime--;
		} else {
			finishTime = time + remainingTime;
			remainingTime = 0;
		}
	}

	public float getArrivalTime() {
		return arrivalTime;
	}

	public String getName() {
		return name;
	}

	public int getPriority() {
		return priority;
	}

	public float getRunTime() {
		return runTime;
	}

	public void setStartTime(float t) {
		startTime = t;
	}

	public float getStartTime() {
		return startTime;
	}

	public void setFinishTime(float t) {
		finishTime = t;
	}

	public float getFinishTime() {
		return finishTime;
	}

	public float getRemainingTime() {
		return remainingTime;
	}
	
	public float getLatestRun() {
		return latestRun;
	}

	@Override
	public String toString() {
		return "Process: " + name + "\n" + "Arrival Time: " + arrivalTime
				+ "\n" + "Expected Run Time: " + runTime + "\n" + "Priority: "
				+ priority + "\n";
	}

}
