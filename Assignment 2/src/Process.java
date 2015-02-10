
public class Process {
	private float arrivalTime; //0 through 99 (quanta)
	private float runTime; //0.1 through 10 quanta
	private int priority; //1-4, 1 is highest
	
	public Process(){
		//(int)(Math.random()*(max-min+1))+min
		
		arrivalTime = (int)(Math.random()*(99-0+1)+0);
		runTime = (int)(Math.random()*(10-0.1+1)+0.1);
		priority = (int)(Math.random()*(4-1+1)+1);
	}
	
	public float getArrivalTime(){
		return arrivalTime;
	}
	
	public float getRunTime(){
		return runTime;
	}
	
	public int getPriority(){
		return priority;
	}
}
