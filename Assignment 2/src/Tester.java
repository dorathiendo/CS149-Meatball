import java.util.ArrayList;

public class Tester {

	public static void main(String[] args) {
		ArrayList<Process> processes = new ArrayList<>();
		int numProcesses = 10; // Will find a better number later
		char name = 'A';
		for (int i = 0; i < numProcesses; i++) {
			processes.add(new Process(Character.toString(name)));
			name++;
		}

		System.out.println(processes);
	}

}
