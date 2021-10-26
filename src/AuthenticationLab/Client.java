package AuthenticationLab;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Client {

	public static void main(String[] args) {
		try {
			Remote name = Naming.lookup("rmi://localhost:0014/printer");
			Server server = (Server) name;
			
		} catch (NotBoundException|MalformedURLException|RemoteException e) {
			System.err.println("Here is the error: "+e.getMessage());
		}
	}
}
