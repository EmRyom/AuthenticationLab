import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.mindrot.jbcrypt.BCrypt;


public class Servant extends UnicastRemoteObject implements Print {

    ArrayList<Printer> printers = new ArrayList<>();
    String parameter;




    private int FindPrinter (String printer) {
        for (int i = printers.size()-1; i > -1; i--) {
            if (printers.get(i).name.equals(printer)) {
                return i;
            }
        }
        return -1;
    }

    public Servant(String parameter) throws RemoteException {
        this.parameter = parameter;


    }

    @Override
    public String echo(String input) throws RemoteException {
        return "From server: " + input;
    }

    @Override
    public void print(String filename, String printer) throws RemoteException {
        int i = FindPrinter(printer);
        if (i==-1) {
            Printer p = new Printer(printer);
            p.addToQueue(filename);
            this.printers.add(p);
        } else {
            printers.get(i).addToQueue(filename);
        }
        System.out.println("Print: "+filename+" on "+printer);
    }

    @Override
    public ArrayList queue(String printer) throws RemoteException {
        System.out.println("Queue: "+printer);
        int i = FindPrinter(printer);
        if (i==-1) {
            ArrayList<Integer> u = new ArrayList<>();
            return u;
        }
        return printers.get(i).Queue;
    }

    @Override
    public void topQueue(String printer, int job) throws RemoteException {
        System.out.println("Top queue "+printer+" was demanded");
        int i = FindPrinter(printer);
        if (i==-1) {
        } else {
            printers.get(i).moveToTop(job);
        }
    }

    @Override
    public void start() throws RemoteException {
        System.out.println("Start server");
    }

    @Override
    public void stop() throws RemoteException {
        System.out.println("Stop server");
    }

    @Override
    public void restart() throws RemoteException {

    }

    @Override
    public String status(String printer) throws RemoteException {
        System.out.println("Status: "+printer);
        int i = FindPrinter(printer);
        if (i==-1) {
            return "Printer "+printer+" doesn't exist";
        } else {
            return printers.get(i).status();
        }

    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        return null;
    }

    @Override
    public void setConfig(String parameter, String value) throws RemoteException {

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
                System.out.println("Username: " + splitStr[0]);
                System.out.println("Password: " + splitStr[1]);
                if (splitStr[0].equals(username)) {
                    bufferedReader.close();
                    //String bCryptPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
                    //System.out.println(bCryptPassword + " password");
                    Boolean comparePasswords = BCrypt.checkpw(password, splitStr[1]);
                    if (comparePasswords) {
                        return true;
                    }
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
