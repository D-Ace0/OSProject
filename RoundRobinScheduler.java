package code.OSProject;

import java.util.*;

public class RoundRobinScheduler {
    public static void main(String[] args) {
        List<PCB> processes = new ArrayList<>();
        processes.add(new PCB(1, 6));
        processes.add(new PCB(2, 8));
        processes.add(new PCB(3, 7));
        processes.add(new PCB(4, 3));

        int timeQuantum = 2;

        roundRobin(processes, timeQuantum);
    }

    public static void roundRobin(List<PCB> processes, int timeQuantum) {
        Queue<PCB> queue = new LinkedList<>(processes);

        System.out.println("Gantt chart:-");
        int c = 0;

        while (!queue.isEmpty()) {
            PCB currentProcess = queue.poll();
            if (currentProcess.getBurstTime() > timeQuantum) {
            	System.out.println("Process "+currentProcess.getProcessID() + " executes from "
            			+ "time " + (c) + " to " + (c + timeQuantum));
                currentProcess.setBurstTime(currentProcess.getBurstTime() - timeQuantum);
                queue.offer(currentProcess);
                c += timeQuantum;
            } else {
                System.out.println("Process " + currentProcess.getProcessID() + " executes from " +
                        "time " + (c) + " to " + (c + currentProcess.getBurstTime()));
                c += currentProcess.getBurstTime();
            }
        }
        System.out.println("[+] Process Finished Using Round Robin");
    }
}
