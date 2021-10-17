import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends ServerImplementation {
    public Server() {
        super();
    }

    public static void main(String[] args) {

        try {
            // Exporting the object of implementation class
            // (here we are exporting the remote object to the stub)
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(new Server(), 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Server", stub);

            System.out.println("Server ready.");

        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}
