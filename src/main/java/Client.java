import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) throws IOException, NotBoundException {
        Print print = (Print) Naming.lookup("rmi://localhost:5099/connect");
        System.out.println("---" + print.echo("Hey server") + " " + print.getClass().getName());
        Scanner scanner = new Scanner(System.in);
        String authenticationString = "None";
        String username = "";
        while (true) {
            if (!authenticationString.equals("None")) {
                System.out.println("Congratulations on being authenticated and welcome to the printer server." +
                        "\nWhat can I do for you today? Your options are:" +
                        "\n1. Print" +
                        "\n2. Queue" +
                        "\n3. Top queue" +
                        "\n4. Start" +
                        "\n5. Stop" +
                        "\n6. Restart" +
                        "\n7. Status" +
                        "\n8. Read config" +
                        "\n9. Set config" +
                        "\nPlease enter the number of your desired option.");
                String userInput = scanner.nextLine();
                switch (userInput) {
                    case "1":
                        System.out.println("You've chosen to print." +
                                "\nPlease enter the file you wish to print.");
                        String filename = scanner.nextLine();
                        System.out.println("Thank you. Now please enter the desired printer.");
                        String printer = scanner.nextLine();
                        System.out.println("Thank you. Processing your request.");
                        System.out.println(print.print(username, filename, printer));
                        break;
                    case "2":
                        System.out.println("You've chosen to queue." +
                                "\nPlease enter the printer you wish to see the queue of.");
                        String printer2 = scanner.nextLine();
                        System.out.println("Thank you. Processing your request.");
                        System.out.println(print.queue(username, printer2));
                        break;
                    case "3":
                        System.out.println("You've chosen to top queue." +
                                "\nPlease enter the printer you wish to top queue on.");
                        String printer3 = scanner.nextLine();
                        System.out.println("Thank you. Now please enter the job number.");
                        int job = Integer.parseInt(scanner.nextLine());
                        System.out.println("Thank you. Processing your request.");
                        System.out.println(print.topQueue(username, printer3, job));
                        break;
                    case "4":
                        System.out.println("You've chosen to start." +
                                "\nProcessing your request.");
                        System.out.println(print.start(username));
                        break;
                    case "5":
                        System.out.println("You've chosen to stop." +
                                "\nProcessing your request.");
                        System.out.println(print.stop(username));
                        break;
                    case "6":
                        System.out.println("You've chosen to restart." +
                                "\nProcessing your request.");
                        System.out.println(print.restart(username));
                        break;
                    case "7":
                        System.out.println("You've chosen to status." +
                                "\nPlease enter the printer you wish to check status on.");
                        printer3 = scanner.nextLine();
                        System.out.println("Thank you. Processing your request.");
                        System.out.println(print.status(username, printer3));
                        break;
                    case "8":
                        System.out.println("You've chosen to read config." +
                                "\nPlease enter the parameter you want read.");
                        String parameter = scanner.nextLine();
                        System.out.println("Thank you. Processing your request.");
                        System.out.println(print.readConfig(username, parameter));
                        break;
                    case "9":
                        System.out.println("You've chosen to set config." +
                                "\nPlease enter the parameter you want set.");
                        String parameter2 = scanner.nextLine();
                        System.out.println("Thank you. Now please enter the value you want it to be.");
                        String value = scanner.nextLine();
                        System.out.println("Thank you. Processing your request.");
                        System.out.println(print.setConfig(username, parameter2, value));
                        break;
                    default:
                        System.out.println("You did not enter a correct option. Please try again.");
                        break;

                }
            } else {
                System.out.println("You are currently not authenticated." +
                        "\nPlease enter your username:");
                username = scanner.nextLine();
                System.out.println("Thank you. Now please enter your password");
                String password = scanner.nextLine();
                System.out.println("Thank you. Processing your authentication.");
                authenticationString = print.authenticateUser(username, password);
                if (authenticationString.equals("None")) {
                    System.out.println("You entered incorrect credentials. Please enter them again.");
                } else {
                    System.out.println(authenticationString + " Welcome!");
                }
            }
        }
    }
}
