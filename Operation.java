package code.OSProject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Operation extends PCB{
    private Map<String, Double> variables= new HashMap<>();
    
    public Operation(int burstTime, int Pid) {
    	super(Pid, burstTime);
    }

    public void assign(String main,String variable1,String variable2, String operation) {
        switch (operation) {
            case "input":
                readInput(main);
                break;
            case "writeFile":
            	
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

    private void assign(String main, String var1, String op) {
    	assign(main,var1,"", op);
	}

    public static void readTxt(Operation operation, String filepath) {
        File file = new File(filepath);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    


	private double getVariableValue(String variable) {
        return variables.getOrDefault(variable, 0.0);
    }

    private void readInput(String variable) {
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
    

    public static void main(String[] args) {
        Operation operation = new Operation(0, 0);

        readTxt(operation, "G:\\OS project\\code\\OS\\src\\code\\OSProject\\Program_1.txt");
	}

    
}
