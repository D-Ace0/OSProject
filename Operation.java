package code.OSProject;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Operation extends PCB{
    private Map<String, Double> variables= new HashMap<>();
    
    public Operation(int Pid, int burstTime) {
    	super(Pid, burstTime);
    }
    
    public void assign(String main,String variable1,String variable2, String operation) {
        switch (operation) {
            case "input":
                readInput(main);
                break;
            case "writeFile":
            	writeFile(main, variable1);
            	break;
            case "readFile":
            	double readF = readFile(variable1);
            	variables.put(main, readF);
            	break;
            case "print":
            	print(main);
            	break;
            case "add":
                double addResult = getVariableValue(variable1) + getVariableValue(variable2);
                variables.put(main, addResult);
                break;
            case "subtract":
            	double subResult = getVariableValue(variable1) - getVariableValue(variable2);
                variables.put(main, subResult);
                break;
            case "multiply":
            	double mulResult = getVariableValue(variable1) * getVariableValue(variable2);
                variables.put(main, mulResult);
                break;
            case "divide":
            	double divisor = getVariableValue(variable2);
                if (divisor != 0) {
                	double divResult = getVariableValue(variable1) / divisor;
                    variables.put(main, divResult);
                } else {
                    System.out.println("Error: Division by zero");
                }
                break;
        }
    }
    
    // Overloaded assign method for cases where no variables are involved
    public void assign(String main, String operation) {
        assign(main, "", "", operation);
    }

    public void assign(String main, String var1, String op) {
    	assign(main,var1,"", op);
	}

    public static void readTxt(Operation operation, String filename) {

        try (BufferedReader reader = new BufferedReader(new FileReader(getResourceFile(filename)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (tokens.length == 2) { // print cases
                	String operationType = tokens[0];
                    String main = tokens[1]; 
                    operation.assign(main, operationType);
                }
                else if(tokens.length == 5) { // arithmetic cases
                    String main = tokens[1];
                    String operationType2 = tokens[2];
                    String var1 = tokens[3];
                    String var2 = tokens[4];
                    operation.assign(main,var1,var2,operationType2);
                }
                else if(tokens.length == 3) { // writefile we assign+input
                	String op = tokens[0];
                	if(op.equals("assign")) { // assign+input
                		String main = tokens[1];
                		String opType = tokens[2];
                		operation.assign(main, opType);
                	}else { //writeFile
                		String main = tokens[1];
                		String var1 = tokens[2];
                		operation.assign(main,var1, op);
                	}
                }
                else if(tokens.length == 4) {
                	String main = tokens[1];
                	String op = tokens[2];
                	String var1 = tokens[3];
                	operation.assign(main,var1, op);
                }
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private double readFile(String filename) {
        File file = new File(System.getProperty("user.dir")+ File.separator +"bin", filename+".txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            return Double.parseDouble(line.trim());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + filename);
            e.printStackTrace();
        }

        return 0.0;
    }




    private static File getResourceFile(String filename) {
        ClassLoader classLoader = Operation.class.getClassLoader();
        URL resourceUrl = classLoader.getResource(filename);

        if (resourceUrl != null) {
            try {
                String filePath = resourceUrl.toURI().getPath();
                if (filename.length() == 1) {
                    filePath = new File(".").getAbsolutePath() + File.separator + filename;
                }

                return new File(filePath);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: Resource not found - " + filename);
        }

        return null;
    }


    
    public void writeFile(String file1, String file2) {
        try {
            File outputFile1 = new File("bin"+ File.separator + file1 + ".txt");
            System.out.println("Writing to file: " + outputFile1.getAbsolutePath());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile1))) {
                writer.write(getVariableValue(file1) + "");
                System.out.println("Contents written to file: " + file1);
            } catch (IOException e) {
                System.out.println("Error writing to file: " + file1);
                e.printStackTrace();
            }

            File outputFile2 = new File("bin"+ File.separator + file2 + ".txt");
            System.out.println("Writing to file: " + outputFile2.getAbsolutePath());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile2))) {
                writer.write(getVariableValue(file2) + "");
                System.out.println("Contents written to file: " + file2);
            } catch (IOException e) {
                System.out.println("Error writing to file: " + file2);
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }


	public double getVariableValue(String variable) {
        return variables.getOrDefault(variable, 0.0);
    }

    public void readInput(String variable) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter value for " + variable + ": ");
            double value = Integer.parseInt(reader.readLine());
            variables.put(variable, value);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading input");
        }
    }
    
    
    public void print(String varname) {
        System.out.println(varname + ": " + getVariableValue(varname));
    }
    
	public static void useRoundRobin(List<PCB> opList) {
	    	RoundRobinScheduler.roundRobin(opList, 2);
	}
	
	public static void useSJF(List<PCB> opList) {
    	ShortestJobFirst.shortestJobFirst(opList);
}
    public static void main(String[] args) {
        Operation operation1 = new Operation(1, 4);
        readTxt(operation1, "Program_1.txt");
        
        Operation operation2 = new Operation(2, 2);
        readTxt(operation2, "Program_2.txt");
        
        Operation operation3 = new Operation(3, 7);
        readTxt(operation3, "Program_3.txt");

        List<PCB> li = new ArrayList<>();
        li.add(operation1);
        li.add(operation2);
        li.add(operation3);
        
        System.out.println("---------------------------");
        
//        useRoundRobin(li);
        
        System.out.println("---------------------------");
        
        useSJF(li);
	}

    
}
