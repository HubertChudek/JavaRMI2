import java.rmi.RemoteException;
import java.util.List;

public class AddTask implements Runnable {

    private final int taskNumber;
    private final List<Double> matrixC;
    private final List<Double> pass1;
    private final List<Double> pass2;
    private final List<AgentInterface> agents;

    public AddTask(int taskNumber, List<Double> matrixC, List<Double> pass1, List<Double> pass2, List<AgentInterface> agents) {
        this.taskNumber = taskNumber;
        this.matrixC = matrixC;
        this.pass1 = pass1;
        this.pass2 = pass2;
        this.agents = agents;
    }

    @Override
    public void run() {
        try {
            matrixC.set(taskNumber, agents.get(taskNumber).add(pass1.get(taskNumber), pass2.get(taskNumber)));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}