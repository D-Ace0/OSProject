package code.OSProject;

import java.net.URL;

public class PCB {
    int processID;
    int burstTime;
    int memoryBoundries[];
    
	public PCB(int processID, int burstTime) {
        this.processID = processID;
        this.burstTime = burstTime;
    }
    
    public int getProcessID() {
		return processID;
	}

	public void setProcessID(int processID) {
		this.processID = processID;
	}

	public int getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

}
