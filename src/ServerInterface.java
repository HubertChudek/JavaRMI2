import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInterface extends Remote {
    void printMsg() throws RemoteException;

    List<Double> compute(List<Double> a, List<Double> b, boolean async) throws RemoteException;
}
