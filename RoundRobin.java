import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/*
 * Madeline Shortt
 * CS322: OS
 * CPU Scheduling
 * Round Robin
 */
public class RoundRobin {
	private static int avgTurnaroundTime;
	private static int avgWaitingTime;
	private static double throughput;
	
	public static void main(String[] args) 
	{
		
		if(args.length == 0)
		{
			System.out.println("Error: must pass the name of a text file as an argument"); 
			return;
		}
		for(int i = 1; i <= 10; i++)
		{
			//start the Round Robin Scheduling!
			roundRobinScheduling(readFile(args[0]), i);
			System.out.println("For a system with a time quantum of "+i+" sec: ");
			System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
			System.out.println("Overall Throughput: " + throughput);
			System.out.println("Average Waiting time: " + avgWaitingTime);
		}

	}
	
	public static Map<Integer, Integer[]> readFile(String fileName)
	{
				//variable to store the key, value pairs (job id and CPU burst) of the jobs
				Map<Integer, Integer[]> jobs = new LinkedHashMap<Integer, Integer[]>();
				FileReader file = null;
				//try/catch for finding the file
				try {
					file = new FileReader(fileName);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				BufferedReader br = new BufferedReader(file);
				String line;
				//try/catch for reading the file
				try {
					//array to hold the string after it is split on ","
					String[] elements;
					//this just reads the headers of the columns (#Job Id,CPU Burst (seconds))
					br.readLine();
					while ((line = br.readLine()) != null) {
					   elements = line.split(",");
					   Integer[] current = new Integer[4];
					   current[0] = Integer.parseInt(elements[1]);
					   current[1] = Integer.parseInt(elements[1]);
					   jobs.put(Integer.parseInt(elements[0]), current);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return jobs;
	}
	
	/*
	 * This methods implements the Round Robin Scheduling policy
	 */
	public static void roundRobinScheduling(Map<Integer, Integer[]> jobs, int timeQuantum)
	{
		//assuming time quantum is in seconds
		//time in the system, in this idealized system we can track this by incrementing the time 
		//whenever the CPU executes and by the amount of time it executes
		int time = 0;
		//even though job information is stored in a hashmap, the job id's must be stored in a FIFO queue
		//to simulate the ready queue. When a job is ready to execute, then the CPU bust information will 
		//be fetched from the hashmap
		Queue<Integer> readyQ = new LinkedList<Integer>();
		//add all the job keys to the queue
		Set<Integer> keySet = jobs.keySet();
		Iterator<Integer> it = keySet.iterator();
		while(it.hasNext())
		{
			readyQ.add((Integer) it.next());
		}
		
		
		//round robin scheduler
		while(readyQ.size()!=0)
		{
			//scheduler picks the first process from the ready queue (key)
			Integer currentProcess = readyQ.remove();
			//Sets timer to interrupt the process after one time quantum, dispatches the process
			//(fetch the CPU burst of the given job id, value)
			Integer CPUtime = (jobs.get(currentProcess))[1];

			//if there is less than 1 time quantum on the current process
			if(CPUtime - timeQuantum < 0)
			{
				//add appropriate time to the system time
				time += CPUtime;
				//update the remaining CPU time of the current process to be equal to zero
				jobs.get(currentProcess)[1] = 0;
				//this job is now done, record the turnaround time for this job
				jobs.get(currentProcess)[2] = time;
				//release the CPU
			}
			else {
				//add appropriate time to the system
				time += timeQuantum;
				//update the CPU burst, subtracting the time quantum that has been executed
				jobs.get(currentProcess)[1] -= timeQuantum;
				//check to see if process is done
				if(jobs.get(currentProcess)[1] == 0)
				{
					//don't put the process back on the queue because it's done!
					//update the remaining CPU time of the current process to be equal to zero
					jobs.get(currentProcess)[1] = 0;
					//this job is now done, record the turnaround time for this job
					jobs.get(currentProcess)[2] = time;
				}
				else
				{
					//add this job back to the back of the readyQ
					readyQ.add(currentProcess);
					//release the CPU
				}
			}
			//uncomment this line to see the progression of the ready queue 
			//System.out.println(readyQ.toString());
		}
		rrCalculations(time, jobs, timeQuantum);
	}
	
	public static void rrCalculations(int time, Map<Integer, Integer[]> jobs, int timeQuantum)
	{
		int numJobs = 0;
		int tTime = 0;
		int wTime = 0;
		//calculate the waiting time
		Iterator<Integer[]> it = jobs.values().iterator();
		while (it.hasNext())
		{
		  Integer[] current = it.next();
		  current[3] = current[2] - current[0];
		  numJobs++;
		  tTime += current[2];
		  wTime += current[3];
		}
		
		avgTurnaroundTime = tTime/numJobs;
		throughput = ((double)numJobs*60)/(double)time;
		avgWaitingTime = wTime/numJobs;
		
		//uncomment this line to see the turnaround and waiting time for each job individually
		/*
		for(int i = 1; i <= numJobs; i++)
		{
			System.out.println("Job id: " + i);
			System.out.println("Initial CPU bust time: " + (jobs.get(i))[0]);
			System.out.println("Remaining CPU burst time: "+(jobs.get(i))[1]);
			System.out.println("Turnaround time: "+(jobs.get(i))[2]);
			System.out.println("Waiting time: " + (jobs.get(i))[3]);
		}
		*/
	}

}
