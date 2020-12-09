package dataman;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 *
 * @author apulus8050
 */
public class Dataman {
    
    public static void RunAnswerChecker(Scanner kb) {
        String input, operator;
        int op1, op2, ans, maxAttempts = 3;
        System.out.println("Answer Checker");
        do {
            AnswerChecker.AskForEquationPrompt(); //Ask user for equation
            input = EquationParser.getUserEquation(kb); //get user's input
            if(input.equalsIgnoreCase("q")) { //if input equals "q"
                System.out.println("\nReturning to Main Menu...\n");
                break; //break the loop. quit.
            }
            else {    //else..
                try { //try parsing the user's equation
                    operator = EquationParser.getOperator(input); //get operator
                    op1 = EquationParser.getFirstOperand(input, operator); //get first operand
                    op2 = EquationParser.getSecondOperand(input, operator); //get second operand
                    ans = EquationParser.getUserAnswer(input); //get user's answer                
                    
                    if(op1 != -1 && op2 != -1) { //if operands don't equal -1, then
                        EquationParser.displayEquation(op1, operator, op2, ans); //display equation
                        AnswerChecker.switchToOperation(op1, operator, op2, ans); //check equation
                    }                
                    else {
                        AnswerChecker.improperEquationPrompt();
                    }
                    AnswerChecker.displayScoreAndAttempts(maxAttempts); //display score/attempts when maxAttempts reached.
                }
                catch(Exception e) {
                    AnswerChecker.improperEquationPrompt();
                }
            }
        }while(true);          
    }
    
    public static void RunMemoryBank(Scanner kb) {
        String input, ansInput, operator;
        int op1, op2, ans, numCorrect = 0;
        ArrayList<String> problemBank = new ArrayList<>();
        int lineCount = 0;
        
        //create file if not exists/ read file if exists
        File dataFile = new File("memorybank.txt");
        try {
            if (!dataFile.exists()) { //if file not exist
                dataFile.createNewFile(); //then create file
            }
            else { //if file does exist
                Scanner reader = new Scanner(dataFile); 
                while(reader.hasNextLine()) { //while there are lines to read
                    ++lineCount; //increment lineCount
                    String line = reader.nextLine(); //read the line.
                    problemBank.add(line); //add the line to problemBank ArrayList
                }   
                reader.close();
            }
        }
        catch(IOException e) {
            System.out.println("An error occured.");
        }
        
        do {
            MemoryBank.DisplayMenu(); //Display Memory Bank Options
            input = kb.next(); //catch input
            System.out.println();

            switch(input) { //switch input
                case "1": //Try Memory Bank Problems
                    if(lineCount == 0) { //if line count is 0, means no problems in bank.
                        System.out.println("There are no problems"
                                         + " in the Memory Bank. \n"
                                         + "Enter some problems in"
                                         + " the Memory Bank.");
                    }
                    else {
                        for(int i = 0; i < problemBank.size(); ++i) {
                            System.out.println("Problem #" + (i+1) + " of " + problemBank.size());
                            System.out.print("\t" + problemBank.get(i));
                            do {
                                try {
                                    operator = EquationParser.getOperator(problemBank.get(i));
                                    op1 = EquationParser.getFirstOperand(problemBank.get(i), operator);
                                    op2 = EquationParser.getSecondOperand(problemBank.get(i), operator);
                                    do {
                                        try {
                                            ansInput = kb.next();
                                            ans = Integer.parseInt(ansInput);
                                            break;
                                        }
                                        catch(Exception e) {
                                           System.out.print("\t" + problemBank.get(i));
                                        }
                                    } while(true);
                                    
                                    if(EquationParser.answerIsCorrect(op1, operator, op2, ans)) {
                                        ++numCorrect;
                                    }
                                    break;
                                }
                                catch(Exception e) {
                                    
                                }   
                            } while(true);
                            System.out.println();
                        } //end for-loop
                        System.out.println("You got " + numCorrect + " out of " 
                                             + problemBank.size() + " right.");
                    }
                    break;
                case "2": //Write problems to file.
                    System.out.println("There are " + problemBank.size()
                       + " problems in the memory bank.");
                    for(int i = 0; i < problemBank.size(); ++i) {
                        System.out.println((i+1) + ". " + problemBank.get(i));
                    }

                    break;
                default:
                    break;
            }
            System.out.println();
        } while(!input.equalsIgnoreCase("3"));
        
        
        
//        //read from file.
//        try {
//            Scanner reader = new Scanner(dataFile);
//            while(reader.hasNextLine()) {
//                ++lineCount;
//                String line = reader.nextLine();
//                System.out.println(line);
//            }
//            System.out.println(lineCount);
//            reader.close();
//        }
//        catch(FileNotFoundException e) {
//            System.out.println("File Not Found");
//        }
//        
//        //write to file.
//        try{
//            FileWriter writer = new FileWriter("memorybank.txt");
//            writer.write("10+10=\n");
//            writer.write("10+11=\n");
//            writer.write("10+12=\n");
//            writer.close();
//        }
//        catch(IOException e) {
//            System.out.println("An error occured.");
//        }
    }
    
    public static void DisplayMenu() {
        System.out.println("Dataman Main Menu");
        System.out.print("1. Answer Checker"
                     + "\n2. Number Guesser"
                     + "\n3. Memory Bank"
                     + "\n4. Exit"
                     + "\nChoose: ");
    }
    
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        boolean replay = true;
        String menuChoice;
        
        do {
            DisplayMenu();
            menuChoice = kb.nextLine();  
            System.out.println();
            switch(menuChoice) {
                case "1":
                    RunAnswerChecker(kb);
                    break;
                case "2":
                    System.out.println("Number Guesser"); //TODO number guesser
                    
                    break;
                case "3":
                    RunMemoryBank(kb);
                    break;
                case "4":
                    System.out.println("Exit");
                    replay = false;
                    break;
                default:
                    break;                
            }            
        }while(replay);        
    }//end main    
}//end class
