/** FCFSSchedulingAlgorithm.java
 * 
 * A first-come first-served scheduling algorithm.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public class FCFSSchedulingAlgorithm extends BaseSchedulingAlgorithm {
	private Vector<Process> jobs;
	
    FCFSSchedulingAlgorithm(){
    	activeJob = null;
    	jobs = new Vector<Process>();
    }

    /** Add the new job to the correct queue.*/
    public void addJob(Process p){
    	jobs.add(p);
    }
    
    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p){
    	if (p == activeJob)
    		activeJob = null;
    	return jobs.remove(p);
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
		for (int i = 0; i < jobs.size(); i++) {
			Process job = jobs.get(0);
			this.removeJob(job);
			otherAlg.addJob(job);
	    }
    }

    public boolean shouldPreempt(long currentTime){
    	return false;
    }

    /** Returns the next process that should be run by the CPU, null if none available.*/
    public Process getNextJob(long currentTime){
    	if (jobs.isEmpty())
    		activeJob = null;
    	else
    		activeJob = jobs.get(0);

    	return activeJob;
    }

    public String getName(){
    	return "First-come first-served";
    }
}