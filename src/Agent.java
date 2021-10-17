import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Agent extends AgentImplementation {
    public Agent() {
    }

    public static void main(String[] args) {
        String agentName = "Agent " + args[0];
        try {
            // Exporting the object of implementation class
            // (here we are exporting the remote object to the stub)
            AgentInterface stub = (AgentInterface) UnicastRemoteObject.exportObject(new Agent(), 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(agentName, stub);

            System.out.println(agentName + " ready.");

        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}
