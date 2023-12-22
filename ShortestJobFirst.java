package code.OSProject;

import java.util.*;

public class ShortestJobFirst {
    public static void main(String[] args) {
        List<PCB> processes = new ArrayList<>();
        processes.add(new PCB(1, 6));
        processes.add(new PCB(2, 8));
        processes.add(new PCB(3, 7));
        processes.add(new PCB(4, 3));

        shortestJobFirst(processes);
    }

    public static void shortestJobFirst(List<PCB> processes) {
        processes.sort(Comparator.comparingInt(pcb -> pcb.burstTime));

        int currentTime = 0;

        System.out.println("Shortest Job First:");

        for (PCB pcb : processes) {
            System.out.println("Process " + pcb.processID + " executes from " + currentTime + " to " + (currentTime + pcb.burstTime));
            currentTime += pcb.burstTime;
        }
        System.out.println("[+] Process Finished Using SJF");

    }
}
