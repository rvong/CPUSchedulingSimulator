/** PrioritySchedulingAlgorithm.java
 * 
 * A single-queue priority scheduling algorithm.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;
import java.util.*;


public class PrioritySchedulingAlgorithm extends BaseSchedulingAlgorithm implements OptionallyPreemptiveSchedulingAlgorithm {
    private boolean preemptive;
    private PriorityQueue<Process> pq;
    
    PrioritySchedulingAlgorithm() {
    	activeJob = null;
    	preemptive = false;
    	pq = new PriorityQueue<Process>(50,
	    			new Comparator<Process>() {
	    				@Override
	    				public int compare(Process p1, Process p2) {
	    					return processCompare(p1, p2);
	    				}
	    			}
    			);
    }

    public int processCompare(Process p1, Process p2) {
		long pw1 = p1.getPriorityWeight();
		long pw2 = p2.getPriorityWeight();
		
		if (pw1 > pw2)
			return 1;
		else if (pw1 < pw2)
			return -1;
		else
			return 0;
    }
    
    /** Add the new job to the correct queue.*/
    public void addJob(Process p){
    	pq.add(p);
    }
    
    /** Returns true if the job was present and was removed. */
    public boolean removeJob(Process p){
    	if (activeJob == p)
    		activeJob = null;
    	return pq.remove(p);
    }

    /** Transfer all the jobs in the queue of a SchedulingAlgorithm to another, such as
	when switching to another algorithm in the GUI */
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
    	while (! pq.isEmpty()) {
    		otherAlg.addJob(pq.peek());
    		pq.remove();
    	}
    }

    /** Returns the next process that should be run by the CPU, null if none available.*/
    public Process getNextJob(long currentTime){
    	//if (! isJobFinished() && ! isPreemptive())
    	//	return activeJob;
    	
    	if (isJobFinished() || isPreemptive()) {
    		if (pq.isEmpty())
    			activeJob = null;
    		else
    			activeJob = pq.peek();
    	}
    	
    	return activeJob;
    }

    
    public String getName(){
    	return "Single-queue Priority";
    }

    /**
     * @return Value of preemptive.
     */
    public boolean isPreemptive(){
    	
    	return preemptive;
    }
    
    /**
     * @param v  Value to assign to preemptive.
     */
    public void setPreemptive(boolean  v){
    	preemptive = v;
    }
}