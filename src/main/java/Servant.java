import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Servant extends UnicastRemoteObject implements Print {

    public Servant() throws RemoteException {
        super();
    }

    @Override
    public String echo(String input) throws RemoteException {
        return "From server: " + input;
    }

    @Override
    public void print(String filename, String printer) throws RemoteException {

    }

    @Override
    public ArrayList queue(String printer) throws RemoteException {
        return null;
    }

    @Override
    public void topQueue(String printer, int job) throws RemoteException {

    }

    @Override
    public void start() throws RemoteException {
        System.out.println("Test");
    }

    @Override
    public void stop() throws RemoteException {

    }

    @Override
    public void restart() throws RemoteException {

    }

    @Override
    public String status(String printer) throws RemoteException {
        return null;
    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        return null;
    }

    @Override
    public void setConfig(String parameter, String value) throws RemoteException {

    }

    @Override
    public String authenticate(String username, String password) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/passwords.txt"))) {
            String lineInPasswordFile;
            while ((lineInPasswordFile = bufferedReader.readLine()) != null) {
                String[] splitStr = lineInPasswordFile.split("\\s+");
                System.out.println("Username: " + splitStr[0]);
                System.out.println("Password: " + splitStr[1]);
                if (splitStr[0].equals(username) && splitStr[1].equals(password)) {
                    return username + " authenticated.";
                }
            }
        }
        return "None";
    }
}
