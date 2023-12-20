package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Main {

    public static void executeOperation(Operation operation, int[] memory) {
        int operand1Value = evaluate(operation.operand1, memory);
        int operand2Value = evaluate(operation.operand2, memory);

        switch (operation.type) {
            case "assign":
                memory[getIndex(operation.operand1)] = operand2Value;
                break;
            case "add":
                memory[getIndex(operation.operand1)] += operand2Value;
                break;
            case "subtract":
                memory[getIndex(operation.operand1)] -= operand2Value;
                break;
            case "multiply":
                memory[getIndex(operation.operand1)] *= operand2Value;
                break;
            case "divide":
                memory[getIndex(operation.operand1)] /= operand2Value;
                break;
            case "readFile":
                int data = readFile(operation.operand1);
                memory[getIndex(operation.operand2)] = data;
                break;
            case "writeFile":
                int valueToWrite = operand1Value;
                writeFile(operation.operand2, valueToWrite);
                break;
            case "print":
                System.out.println(operand1Value);
                break;
        }
    }

    public static int evaluate(String operand, int[] memory) { //chatgpt
        if (isNumeric(operand)) {
            return Integer.parseInt(operand);
        } else {
            return memory[getIndex(operand)];
        }
    }

    public static boolean isNumeric(String str) {//chatgpt
        return str.matches("-?\\d+");
    }

    static int getIndex(String variable) {
        return 0;
    }

    public static List<Operation> parseProgramFromFile(String path, String filename) {
        List<Operation> program = new ArrayList<>();

        try {
            Path filePath = Path.of(path, filename);
            BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String type = parts[0];
                String operand1 = parts[1];
                String operand2 = parts.length > 2 ? parts[2] : null;

                program.add(new Operation(type, operand1, operand2));
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return program;
    }

    public static int readFile(String filename) {
        int data = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            data = Integer.parseInt(line);
            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void writeFile(String filename, int data) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(String.valueOf(data));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   public static PCB getNextProcess(Queue<PCB> readyQueue) {
        return readyQueue.poll();
    }
   
   
   public static void printStatus(PCB currentProcess, int[] memory) { //chatGPT 
       System.out.println("Current Process ID: " + currentProcess.processID);
       System.out.println("Memory State:");
       for (int i = 0; i < memory.length; i++) 
           System.out.println("Memory[" + i + "]: " + memory[i]);
       
       System.out.println("------------------------------");
   } 

   public static void finishProcess(PCB currentProcess) {
       System.out.println("Process ID: " + currentProcess.processID + " finished.");
       System.out.println("------------------------------");
   }
    
   

}