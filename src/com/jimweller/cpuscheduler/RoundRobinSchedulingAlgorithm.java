

/** RoundRobinSchedulingAlgorithm.java
 * 
 * A scheduling algorithm that randomly picks the next job to go.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public class RoundRobinSchedulingAlgorithm extends BaseSchedulingAlgorithm {

    /** the timeslice each process gets */
    private int quantum;
    
    private Queue<Process> jobs;
    private int timeRemaining;
    
    RoundRobinSchedulingAlgorithm() {
    	activeJob = null;
    	jobs = new LinkedList<Process>();
    	quantum = 10;
    	timeRemaining = quantum;
    }

    /** Add the new job to the correct queue. */
    public void addJob(Process p) {
    	jobs.add(p);
    }

    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p) {
    	if (p == activeJob)
    		activeJob = null;
    	return jobs.remove(p);
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
		for (int i = 0; i < jobs.size(); i++) {
			Process job = jobs.poll();
			otherAlg.addJob(job);
	    }
    }

    /**
     * Get the value of quantum.
     * 
     * @return Value of quantum.
     */
    public int getQuantum() {
    	return quantum;
    }

    /**
     * Set the value of quantum.
     * 
     * @param v
     *            Value to assign to quantum.
     */
    public void setQuantum(int v) {
    	this.quantum = v;
    }

    /**
     * Returns the next process that should be run by the CPU, null if none
     * available.
     */
    public Process getNextJob(long currentTime) {
    	if (timeRemaining == 0) jobs.add(activeJob);	// if time remaining reaches zero, switch out add to end of waiting queue
 
    	if (activeJob == null || isJobFinished() || timeRemaining == 0) {
    		activeJob = jobs.poll();
    		timeRemaining = quantum;
    	}

    	timeRemaining--;
    	return activeJob;
    }

    public String getName() {
    	return "Round Robin";
    }
}