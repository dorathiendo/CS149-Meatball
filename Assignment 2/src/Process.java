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

	/**
	 * Creates a deep copy of a process.
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
	
	/**
	 * Gets the arrial time of the process.
	 * @return the arrival time
	 */
	public float getArrivalTime() {
		return arrivalTime;
	}
	
	/**
	 * Gets the name of the process.
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the priority of the process.
	 * @return priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Gets the run time of the process.
	 * @return run time
	 */
	public float getRunTime() {
		return runTime;
	}
	
	/**
	 * Sets the start time of the process.
	 * @param t, the start time
	 */
	public void setStartTime(float t) {
		startTime = t;
	}

	/**
	 * Gets the start time.
	 * @return start time
	 */
	public float getStartTime() {
		return startTime;
	}

	/**
	 * Sets the finish time of the process.
	 * @param t, the finish time.
	 */
	public void setFinishTime(float t) {
		finishTime = t;
	}

	/**
	 * Gets the finish time of the process. 
	 * @return the finish time
	 */
	public float getFinishTime() {
		return finishTime;
	}

	/**
	 * Gets the remaining time of the process. 
	 * @return the remaining time
	 */
	public float getRemainingTime() {
		return remainingTime;
	}
	
	/**
	 * Gets the latest run of the process.
	 * @return lastest run
	 */
	public float getLatestRun() {
		return latestRun;
	}

	/**
	 * Returns print out of Process.
	 * @return the print out in string format.
	 */
	@Override
	public String toString() {
		return "Process: " + name + "\n" + "Arrival Time: " + arrivalTime
				+ "\n" + "Expected Run Time: " + runTime + "\n" + "Priority: "
				+ priority + "\n";
	}

}
