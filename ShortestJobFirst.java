import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Performs the shortest job first scheduling algorithm on a list of jobs.
 * @author pragyabajoria
 *
 */
public class ShortestJobFirst {
	
	public static void main(String args[]) {
		
		if (args.length < 1) {
			System.out.println("Please enter a file name as a command line argument!");
			return;
		}
		String fileName = args[0];
		File inputFile = new File(fileName);	
		try {
			FileReader fileReader = new FileReader(inputFile);
			BufferedReader br = new BufferedReader(fileReader);
			ArrayList<Job> jobs = new ArrayList<Job>();
			int totalWaitingTime = 0, totalTurnaroundTime = 0, totalTime = 0;
			
			// Reading input from the file
			br.readLine();
			String str;
			while ((str = br.readLine()) != null) {
				String[] line = str.split(",");
				jobs.add(new Job(toInt(line[0]), toInt(line[1])));
			}
			
			// Sorting them in increasing order of CPU Burst Time
			sortJobList(jobs);
			
			// Setting the number of jobs
			int numJobs = jobs.size();
			
			// The waiting time for the first job will be 0.
			jobs.get(0).setWaitingTime(0);
			
			// Calculating the waiting time, turnaround time and total time for all the jobs.
			for (int i = 0; i < jobs.size(); i++) {
				
				Job currentJob = jobs.get(i);
				int currentBurstTime = currentJob.getBurstTime();
				
				// Computing the waiting time
				int currentWaitingTime = i != 0 ? jobs.get(i-1).getWaitingTime() + jobs.get(i-1).getBurstTime() : 0;
				currentJob.setWaitingTime(currentWaitingTime);
				totalWaitingTime += currentWaitingTime;
				
				// Computing the turnaround time
				int currentTurnaroundTime = currentBurstTime + currentWaitingTime;
				currentJob.setTurnaroundTime(currentTurnaroundTime);
				totalTurnaroundTime += 	currentTurnaroundTime;
				
				// Computing the total time taken.
				totalTime += currentBurstTime;
			}
						
			System.out.println("Average Waiting Time: " + (double) totalWaitingTime / numJobs);
			System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / numJobs);
			System.out.println("Overall Throughput: " + (double) (numJobs * 60) / totalTime);
			
		} catch (FileNotFoundException e1) {
			System.out.println("Given file was not found!");
			e1.printStackTrace();
        } catch (IOException e) {
			System.out.println("Incorrect input was entered!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Sorts the job list in increasing order of burst time
	 * @param list the list of jobs
	 */
	public static void sortJobList(ArrayList<Job> list) {
		for(int i = 0; i < list.size() - 1; i++) {
			for(int j = i+1 ; j < list.size(); j++) {
				Job job1 = list.get(i);
				Job job2 = list.get(j);
				
				// Sort by slower burst time. If two jobs have the same burst time, we sort in increasing order
				// order of job ids.
				if(job1.getBurstTime() > job2.getBurstTime()
						|| (job1.getBurstTime() == job2.getBurstTime() && job1.getId() > job2.getId())) {
					Collections.swap(list, i, j);
				}
			}
		}
	}
	
	/**
	 * Converts the string into an Integer.
	 * @param str
	 * @return the integer form of the string.
	 */
	public static int toInt(String str) {
		return Integer.parseInt(str);
	}
}

/**
 * Internal job class that has the necessary functionality for each job
 * @author pragyabajoria
 *
 */
class Job {
	private int id;
	private int burstTime;
	private int waitingTime;
	private int turnaroundTime;

	public Job(int id, int burstTime) {
		this.id = id;
		this.burstTime = burstTime;
	}
	
	public int getId() {
		return id;
	}
	
	public int getBurstTime() {
		return burstTime;
	}
	
	public int getWaitingTime() {
		return waitingTime;
	}
	
	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}
	
	public int getTurnaroundTime() {
		return turnaroundTime;
	}

	public void setTurnaroundTime(int turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}
}
