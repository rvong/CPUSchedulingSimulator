/** SJFSchedulingAlgorithm.java
 * 
 * A shortest job first scheduling algorithm.
 *
 * @author: Kyle Benson
 * Winter 2013
 *
 */
package com.jimweller.cpuscheduler;

import java.util.*;

public class SJFSchedulingAlgorithm extends PrioritySchedulingAlgorithm {//implements OptionallyPreemptiveSchedulingAlgorithm {
	@Override
	public int processCompare(Process p1, Process p2) {
		long pw1 = p1.getBurstTime();
		long pw2 = p2.getBurstTime();
		
		if (pw1 > pw2)
			return 1;
		else if (pw1 < pw2)
			return -1;
		else
			return 0;
    }
	
	@Override
    public String getName(){
    	return "Shortest job first";
    }
}