import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class FCFS_Scheduler {
	
	public static void fcfsScheduler(String fileName) throws IOException{
		BufferedReader fileLines = new BufferedReader(new FileReader(fileName));
		
		//read in the first line which is not a job
		fileLines.readLine();
		
		String line;
		int totalTime =0;
		int processes =0;
		int waitTime=0;
		while ((line = fileLines.readLine()) != null) {
			
			String[] splitLine = line.split(",");
			//job id in this case is irrelevant as information inputted is sequential
			int jobId = Integer.parseInt(splitLine[0]);
			int seconds = Integer.parseInt(splitLine[1]);
			
			//this is a new process
			processes++;
			//the wait time is the time all inputs ahead of this process have taken
			if(jobId!=1){
				waitTime+=seconds;
			}
			//the total time is totaltime + the runtime of this process
			totalTime+=seconds;
		   // process the line.
		}
		System.out.println("For fcfs scheduler: ");
		System.out.println("The number of processes are: "+ processes);
		System.out.println("The Turnaround is: " + totalTime);
		System.out.println("The average time is: " + totalTime/processes);
		System.out.println("The throughput is: " + processes/(totalTime/60) );
		System.out.println("The wait time is: " + waitTime);
		System.out.println("The Average wait time is: " + waitTime/processes);
		System.out.println("------");
		fileLines.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {

			BufferedReader br = 
					new BufferedReader(new InputStreamReader(System.in));

			String fileName=br.readLine();
			br.close();
			fcfsScheduler(fileName);
			//TODO the other schedulers

	}


}
