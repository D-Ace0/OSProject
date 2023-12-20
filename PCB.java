package code;

public class PCB {
    int processID;
    int programCounter;
    int[] memoryBoundaries;

    public PCB(int processID, int programCounter, int[] memoryBoundaries) {
        this.processID = processID;
        this.programCounter = programCounter;
        this.memoryBoundaries = memoryBoundaries;
    }
}
