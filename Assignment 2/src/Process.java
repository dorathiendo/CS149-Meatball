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

	/**
	 * Constructor that creates a process with:
	 * -a randomly generated arrival time from 0 to 99
	 * -a randomly expected run time from 0.1 to 10 quanta
	 * -a randomly generated priority: either 1, 2, 3, or 4
	 */
	public Process(String name) {
		this.name = name;
		//(Math.random()*(max-min+1))+min
		arrivalTime = (float) (Math.random()*(99-0+1)+0);
		runTime = (float) (Math.random()*(10-0.1+1)+0.1);
		priority = (int) (Math.random()*(4-1+1)+1);
	}
	
	public double getArrivalTime() {
		return arrivalTime;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public double getRunTime() {
		return runTime;
	}
	
	@Override
	public String toString() {
		return "Name: " + name + "\n" +
				"Arrival Time" + arrivalTime + "\n" +
				"Expected Run Time" + runTime + "\n" +
				"Priority" + priority + "\n";
	}
}
