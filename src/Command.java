import java.util.Scanner;

public class Command {
    Processor processor;
    boolean isActive;

    public Command(){
        processor = new Processor();
        isActive = true;
        while(isActive){
            commandInput();
        }

    }
    public void commandInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your command:");
        String input = scanner.nextLine().toLowerCase();
        String command = input.substring(0,input.indexOf(' '));
        String context = input.substring(input.indexOf(' ')+1);
        processor.executeCommand(command,context);
        System.out.println("----------------------------------------");
        commandInput();
    }
    public static void main(String[] args) {
        new Command();
        /**relation employee(a, b, c){1,2,3,4,5,6}
         * relation employee=e
         * select employee(a>3)
         */


    }
}
