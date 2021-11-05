import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServer {
    public static void main(String[] args) throws IOException {
        Registry registry = LocateRegistry.createRegistry(5099);
        registry.rebind("connect", new Servant());
        System.out.println("Server is up and running.");
    }
}
