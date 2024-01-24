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
        System.out.println(processor.executeCommand(input));
        System.out.println("----------------------------------------");
        commandInput();
    }
    public static void main(String[] args) {
        new Command();
        /**
         * relation x(a, b, c){1,2,3,4,5,6}
         * relation y(d,e){6,7,3,8,0,7}
         * relation x=e
         * select x(a>3)
         * project x(b,c)
         * join x(c=d)y
         * relation y = project x(b,c)
         */

        /**
         * relation x(a, b){1,11,2,22,3,33,4,44}
         * relation y(a, b){1,11,5,55,6,66,3,33,9,99}
         * intersect x(y)
         * union x(y)
         * minus x(y)
         */


    }
}
