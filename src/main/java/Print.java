import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Print extends Remote {
    String echo(String input) throws RemoteException;
    String print(String user, String filename, String printer) throws RemoteException;   // prints file filename on the specified printer
    String queue(String user,String printer) throws RemoteException;   // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
    String topQueue(String user,String printer, int job) throws RemoteException;   // moves job to the top of the queue
    String start(String user) throws RemoteException;   // starts the print server
    String stop(String user) throws RemoteException;   // stops the print server
    String restart(String user) throws RemoteException;   // stops the print server, clears the print queue and starts the print server again
    String status(String user, String printer) throws RemoteException;  // prints status of printer on the user's display
    String readConfig(String user, String parameter) throws RemoteException;   // prints the value of the parameter on the user's display
    String setConfig(String user, String parameter, String value) throws RemoteException;   // sets the parameter to value
    String authenticateUser(String username, String password) throws IOException;
}
