OS_Lab1
=======

Team Members
Tai-Lan Hirabayashi
Madeline Shortt
Pragya Bajoria

Instructions:
**Please note our readme is .md as we have been using github for our source control (vs .txt). 

For FCFS_Scheduler:
How to run: Run from terminal - The program is already compiled simply run:  java FCFS_Scheduler <jobs.txt/your file>.  It will print out scheduling data. An example of this might be: java FCFS_Scheduler jobs.txt. 
Design: The design was relatively simple, it simply reads in the text file, and executes upon it. It skips the first line of the file (comment line) and then proceeds to calculate. It prints data (total time, average wait time, etc) to the terminal. It is a simple summation function. 

For RoundRobin:
To compile and run the Round Robin Scheduler:
javac RoundRobin.java
java RoundRobin jobs.txt
	
Design: 
I used a LinkedHashMap<Integer, Integer[]> to store the job ID as the key and in the values of array -  the CPU burst, remaining CPU burst, turnaround time and waiting time for each job. I used this because of the multiple values that needed to be stored for each job. It runs through the time quantums from 1s to 10s and prints out avg turnaround time, overall throughput, and avg waiting time. There is also a code block that can be uncommented to print out the individual values per job as well.

For Shortest Job First:
To run ShortestJobFirst from the terminal, type the commands
javac -g ShortestJobFirst.java 	// Compile the program
	java ShortestJobFirst jobs.txt		// Run the program	

Design Choices:
Used an internal Job class to encapsulate all the necessary functionality and attributes required for a job - id, burst time, waiting time, turnaround time, with getter and setter methods for each of them. 
Saved the list of jobs in an ArrayList to allow easy retrieval and modifications of individual elements. Preferred ArrayList to Array since we did not know the number of elements in the text file to initialize the array in the beginning, and traversing the loop to just count the number of elements seemed inefficient. 
Used SelectionSort (O(n log n)) to sort the list of jobs in increasing order of CPU burst size and if they are equal, in increasing order of job ids (FCFS). 

