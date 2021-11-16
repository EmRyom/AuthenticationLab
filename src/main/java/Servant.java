import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.mindrot.jbcrypt.BCrypt;


public class Servant extends UnicastRemoteObject implements Print {

    HashMap<String,String[]> permissions = new HashMap<String,String[]>();

    public void loadAccessList() throws IOException {
        String line;
        Boolean loading = false;
        BufferedReader reader = new BufferedReader(new FileReader("../resources/passwords.txt"));
        while ((line = reader.readLine())!=null) {
            String[] splitStr = line.split("\\s+");
            if (loading) {
                permissions.put(splitStr[0],Arrays.copyOfRange(splitStr,1,splitStr.length));
            }
            if (splitStr[0].equals(":")) {
                loading = true;
            }
        }

    }

    public Servant() throws IOException {
        loadAccessList();
    }

    @Override
    public String echo(String input) throws RemoteException {
        return "From server: " + input;
    }

    @Override
    public String print(String user, String filename, String printer) throws RemoteException {
        if (Arrays.asList(permissions.get("print")).contains(user)) {
            System.out.println("Print: "+filename+" on "+printer);
            return "Print successful";
        } else {
            return "You do not have permission to print";
        }
    }

    @Override
    public String queue(String user, String printer) throws RemoteException {
        if (Arrays.asList(permissions.get("queue")).contains(user)) {
            System.out.println("Queue");
            return "Queue successful";
        } else {
            return "You do not have permission to queue";
        }

    }

    @Override
    public String topQueue(String user, String printer, int job) throws RemoteException {
        if (Arrays.asList(permissions.get("topQueue")).contains(user)) {
            System.out.println("Top queue");
            return "Top queue successful";
        } else {
            return "You do not have permission to topQueue";
        }

    }

    @Override
    public String start(String user) throws RemoteException {
        if (Arrays.asList(permissions.get("start")).contains(user)) {
            System.out.println("Start");
            return "Start successful";
        } else {
            return "You do not have permission to start";
        }

    }

    @Override
    public String stop(String user) throws RemoteException {
        if (Arrays.asList(permissions.get("stop")).contains(user)) {
            System.out.println("Stop");
            return "Stop successful";
        } else {
            return "You do not have permission to stop";
        }
    }

    @Override
    public String restart(String user) throws RemoteException {
        if (Arrays.asList(permissions.get("restart")).contains(user)) {
            System.out.println("Restart");
            return "Restart successful";
        } else {
            return "You do not have permission to restart";
        }
    }

    @Override
    public String status(String user, String printer) throws RemoteException {
        if (Arrays.asList(permissions.get("status")).contains(user)) {
            System.out.println("Status");
            return "Status successful";
        } else {
            return "You do not have permission to status";
        }
    }

    @Override
    public String readConfig(String user, String parameter) throws RemoteException {
        if (Arrays.asList(permissions.get("readConfig")).contains(user)) {
            System.out.println("Read config");
            return "Read config successful";
        } else {
            return "You do not have permission to readConfig";
        }
    }

    @Override
    public String setConfig(String user, String parameter, String value) throws RemoteException {
        if (Arrays.asList(permissions.get("setConfig")).contains(user)) {
            System.out.println("Set config");
            return "Set config successful";
        } else {
            return "You do not have permission to setConfig";
        }
    }

    @Override
    public String authenticateUser(String username, String password) throws IOException {
        boolean isAuthenticated;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("../resources/passwords.txt"))) {
            isAuthenticated = compareUserInput(bufferedReader, username, password);
        } catch (Exception e) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/passwords.txt"))) {
                isAuthenticated = compareUserInput(bufferedReader, username, password);
            }
        }
        if (isAuthenticated) {
            return username + " authenticated.";
        }
        return "None";
    }

    private boolean compareUserInput(BufferedReader bufferedReader, String username, String password) throws IOException {
        try {
            String lineInPasswordFile;
            while ((lineInPasswordFile = bufferedReader.readLine()) != null) {
                String[] splitStr = lineInPasswordFile.split("\\s+");
                //System.out.println("Username: " + splitStr[0]);
                //System.out.println("Password: " + splitStr[1]);
                if (splitStr[0].equals(username)) {
                    System.out.println("Client credentials recieved.\n" +
                            "Username: " + username +
                            "\nPassword: " + password);
                    bufferedReader.close();
                    //String bCryptPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
                    //System.out.println(bCryptPassword + " password");
                    Boolean comparePasswords = BCrypt.checkpw(password, splitStr[1]);
                    if (comparePasswords) {
                        System.out.println("Input credentials correct. User authenticated.");
                        return true;
                    }
                    System.out.println("Input credentials incorrect. User authentication denied.");
                    return false;
                }
            }
            System.out.println("No user \'" + username + "\' exists in the system.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
