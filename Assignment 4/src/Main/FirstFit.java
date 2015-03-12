package Main;

import java.util.ArrayList;
import java.util.LinkedList;

public class FirstFit extends Algorithm{
	
	int time = 0;
	int index = 0;
		
	public FirstFit(LinkedList<Process> processesIn){
		super(processesIn);
	}

	@Override
	public void run() {
		
		index = 0;
		while(time < 0){
			
			time++;
		}	
	}
	
			
	public int findHoleIndex(Process p){
		int count = 0;
		
		//find the index.
		for(int i = 0; i < bitmap.length; i++){
			if(bitmap[i] == '.'){
				index = i;
				break;
			}
		}
		
		//find the size of hole
		for(int i = index; i < bitmap.length; i++){
			if(bitmap[i] == '.'){
				count++;
			}
			else {
				break;
			}
		}
		
		return count;
	}
	

}
