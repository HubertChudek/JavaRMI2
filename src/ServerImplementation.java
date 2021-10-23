import java.lang.reflect.Array;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerImplementation implements ServerInterface {
    private Registry registry;
    private final List<AgentInterface> agents = new ArrayList<>();
    private static final int AGENTS_COUNT = 4;

    public ServerImplementation() {
        try {
            lookupAgents();
        } catch (NotBoundException | RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Double> compute(List<Double> matrixA, List<Double> matrixB, boolean async) throws RemoteException {

        System.out.println("Computing...");
        if (async) {
            try {
                return computeAsync(matrixA, matrixB);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return matrixA;
            }
        } else return computeSync(matrixA, matrixB);
    }

    private void lookupAgents() throws RemoteException, NotBoundException {
        // Getting the registry
        registry = LocateRegistry.getRegistry(null);
        // Looking up the registry for the remote object
        for (int i = 1; i <= AGENTS_COUNT; i++) {
            agents.add((AgentInterface) registry.lookup("Agent " + i));
        }
    }

    public List<Double> computeAsync(List<Double> matrixA, List<Double> matrixB) throws RemoteException, InterruptedException {
        List<Double> matrixC = Arrays.asList(0.0,0.0,0.0,0.0);
        //[a11, a12, a21, a22]
        List<Double> pass1 = Arrays.asList(0.0,0.0,0.0,0.0);
        List<Double> pass2 = Arrays.asList(0.0,0.0,0.0,0.0);
        AtomicInteger atomicCounter = new AtomicInteger(0);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < AGENTS_COUNT; i++) { //przejście 1. mnożenia
            threads.add(new Thread(new MultiplyTask(atomicCounter.get(), matrixA, matrixB, pass1, pass2, agents)));
            threads.get(threads.size() - 1).start();
            //Thread.sleep(10);
            atomicCounter.set(atomicCounter.get() + 1);
        }
        for (int i = 0; i < AGENTS_COUNT; i++) {
            threads.get(i).join();
        }

        for (int i = 0; i < AGENTS_COUNT; i++) { //przejście 2. mnożenia
            threads.add(new Thread(new MultiplyTask(atomicCounter.get(), matrixA, matrixB, pass1, pass2, agents)));
            threads.get(threads.size() - 1).start();
            //Thread.sleep(10);
            atomicCounter.set(atomicCounter.get() + 1);
        }
        for (int i = 0; i < AGENTS_COUNT; i++) {
            threads.get(i + 4).join();
        }

        System.out.println("Counter: " + atomicCounter.get());
        System.out.println("Agents size: " + agents.size());
        for (int i = 0; i < AGENTS_COUNT; i++) { //przejście 1. dodawaniania
            threads.add(new Thread(new AddTask(i, matrixC, pass1, pass2, agents)));
            threads.get(threads.size() - 1).start();
            Thread.sleep(10);
            atomicCounter.set(atomicCounter.get() + 1);
        }
        for (int i = 0; i < AGENTS_COUNT; i++) {
            threads.get(i + 8).join();
        }

        return matrixC;
    }

    public List<Double> computeSync(List<Double> matrixA, List<Double> matrixB) throws RemoteException {
        List<Double> matrixC = new ArrayList<>();
        //[a11, a12, a21, a22]
        List<Double> pass1 = new ArrayList<>();
        List<Double> pass2 = new ArrayList<>();

        pass1.add(agents.get(0).multiply(matrixA.get(0), matrixB.get(0)));
        pass1.add(agents.get(1).multiply(matrixA.get(0), matrixB.get(1)));
        pass1.add(agents.get(2).multiply(matrixA.get(2), matrixB.get(0)));
        pass1.add(agents.get(3).multiply(matrixA.get(2), matrixB.get(1)));

        pass2.add(agents.get(0).multiply(matrixA.get(1), matrixB.get(2)));
        pass2.add(agents.get(1).multiply(matrixA.get(1), matrixB.get(3)));
        pass2.add(agents.get(2).multiply(matrixA.get(3), matrixB.get(2)));
        pass2.add(agents.get(3).multiply(matrixA.get(3), matrixB.get(3)));

        for (int i = 0; i < AGENTS_COUNT; i++) {
            matrixC.add(agents.get(i).add(pass1.get(i), pass2.get(i)));
        }

        return matrixC;
    }

    @Override
    public void printMsg() throws RemoteException {
        System.out.println("Hello RMI program!");
    }
}
