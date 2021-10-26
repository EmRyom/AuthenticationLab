import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main( String[] args ) throws MalformedURLException, NotBoundException, RemoteException {
        Service service = (Service) Naming.lookup("rmi://localhost:5099/server");
        System.out.println("--- "  + service.echo("Hey server!"));

    }
}
