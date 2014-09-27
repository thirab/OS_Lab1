import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class FCFS_Scheduler {
	
	public static void fcfsScheduler(String fileName) throws IOException{
		BufferedReader fileLines = new BufferedReader(new FileReader(fileName));
		
		//read in the first line which is not a job
		fileLines.readLine();
		
		//hold the line value globally
		String line;
		
		//set the initial time values
		int totalTime =0;
		int processes =0;
		int waitTime=0;
		while ((line = fileLines.readLine()) != null) {
			if(line!=""){
				//split the line to get the values (id, seconds)
				String[] splitLine = line.split(",");
				//job id in this case is irrelevant as information inputted is sequential
				int jobId = Integer.parseInt(splitLine[0]);
				int seconds = Integer.parseInt(splitLine[1]);
			
				//this is a new process
				processes++;
				//the wait time is the time all inputs ahead of this process have taken
				if(jobId!=1){
					waitTime+=totalTime;
				}
				//the total time is totaltime + the runtime of this process
				totalTime+=seconds;
		   		// process the line.
			}
		}
		
		//print to terminal user
		System.out.println("For fcfs scheduler: ");
		System.out.println("The number of processes are: "+ processes);
		System.out.println("The Turnaround is: " + (double)(totalTime+waitTime)/processes);
		System.out.println("The average time is: " + (double) totalTime/processes);
		System.out.println("The throughput is: " + (double) processes/totalTime*60 );
		System.out.println("The wait time is: " + waitTime);
		System.out.println("The Average wait time is: " + (double) waitTime/processes);
		System.out.println("------");
		fileLines.close();
	}

	/**
	 *The Program does not handler erroneous lines, mislabeled files etc
	 *@param args
	 */
	public static void main(String[] args) throws IOException {
			
			if(args.length ==0){
				System.out.println("There was no file specified");
				return;
			}
			String message = args[0];
			
			fcfsScheduler(message);
			//TODO the other schedulers

	}


}
