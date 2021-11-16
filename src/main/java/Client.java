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
                        System.out.println("You've chosen to print");
                        System.out.println(print.print(username, "", ""));
                        break;
                    case "2":
                        System.out.println("You've chosen to queue");
                        System.out.println(print.queue(username, ""));
                        break;
                    case "3":
                        System.out.println("You've chosen to top queue");
                        System.out.println(print.topQueue(username, "", 0));
                        break;
                    case "4":
                        System.out.println("You've chosen to start");
                        System.out.println(print.start(username));
                        break;
                    case "5":
                        System.out.println("You've chosen to stop");
                        System.out.println(print.stop(username));
                        break;
                    case "6":
                        System.out.println("You've chosen to restart");
                        System.out.println(print.restart(username));
                        break;
                    case "7":
                        System.out.println("You've chosen to status");
                        System.out.println(print.status(username, ""));
                        break;
                    case "8":
                        System.out.println("You've chosen to read config");
                        System.out.println(print.readConfig(username, ""));
                        break;
                    case "9":
                        System.out.println("You've chosen to set config");
                        System.out.println(print.setConfig(username, "", ""));
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
