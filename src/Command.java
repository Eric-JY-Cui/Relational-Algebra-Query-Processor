import java.util.Scanner;

public class Command {
    Processor processor;
    boolean isActive;

    /**
     * the main class of the project, initialize the program
     */
    public Command(){
        processor = new Processor();
        isActive = true;
        while(isActive){
            commandInput();
        }

    }

    /**
     * a loop that ask the user for command input after the previous command hove been executed
     */
    public void commandInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your command:");
        String input = scanner.nextLine().toLowerCase();
        System.out.println(processor.executeCommand(input));
        System.out.println("----------------------------------------");
        commandInput();
    }
    public static void main(String[] args) {
        new Command();
    }
}
