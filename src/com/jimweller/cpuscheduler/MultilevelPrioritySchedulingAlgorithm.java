package com.jimweller.cpuscheduler;

import java.util.*;
public class MultilevelPrioritySchedulingAlgorithm extends RoundRobinSchedulingAlgorithm
													implements OptionallyPreemptiveSchedulingAlgorithm {
	
    private boolean preemptive;
    
    //SJFSchedulingAlgorithm q1;
	RoundRobinSchedulingAlgorithm q2;
	FCFSSchedulingAlgorithm q3;
	
	MultilevelPrioritySchedulingAlgorithm() {
		//super();
		//q1 = new SJFSchedulingAlgorithm();
		q2 = new RoundRobinSchedulingAlgorithm();
		q2.setQuantum(getQuantum() * 2);
		q3 = new FCFSSchedulingAlgorithm();
	}
	
    public void addJob(Process p) {
    	long pw = p.getPriorityWeight();
    	if (pw >= 0 && pw <= 3)
    		//q1.addJob(p);
    		super.addJob(p);
    	else if (pw >= 4 && pw <= 6)
    		q2.addJob(p);
    	else
    		q3.addJob(p);
    }
    
    public boolean removeJob(Process p) {
    	if (p == activeJob)
    		activeJob = null;
    	//return q1.removeJob(p) || q2.removeJob(p) || q3.removeJob(p);
    	return super.removeJob(p) || q2.removeJob(p) || q3.removeJob(p);
    }
    
    @Override
    public Process getNextJob(long currentTime) {
    	if (activeJob == null || isPreemptive() || isJobFinished()) {
    		//activeJob = q1.getNextJob(currentTime);
    		activeJob = super.getNextJob(currentTime);
			
    		if (activeJob == null) {
    			activeJob = q2.getNextJob(currentTime);
    		}
    		if (activeJob == null) {
    			activeJob = q3.getNextJob(currentTime);
    		}
    	}
    	return activeJob;
    }
    
	@Override
    public void transferJobsTo(SchedulingAlgorithm otherAlg) {
		//q1.transferJobsTo(otherAlg);
		super.transferJobsTo(otherAlg);
		q2.transferJobsTo(otherAlg);
		q3.transferJobsTo(otherAlg);
    }
	
	@Override
    public void setQuantum(int v) {
		super.setQuantum(v);
		q2.setQuantum(v * 2);
    }
	
	@Override
    public String getName() {
    	return "Multilevel Priority";
    }
	
	@Override
	public boolean isPreemptive() {
		return preemptive;
	}

	@Override
	public void setPreemptive(boolean v) {
		preemptive = v;
		//q1.setPreemptive(v);
	}

	
}
