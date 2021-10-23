import java.util.List;

public class MultiplyTask implements Runnable {

    private final int taskNumber;
    private final List<Double> matrixA;
    private final List<Double> matrixB;
    private final List<Double> pass1;
    private final List<Double> pass2;
    private final List<AgentInterface> agents;

    public MultiplyTask(int taskNumber, List<Double> matrixA, List<Double> matrixB, List<Double> pass1, List<Double> pass2, List<AgentInterface> agents) {
        this.taskNumber = taskNumber;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.pass1 = pass1;
        this.pass2 = pass2;
        this.agents = agents;
    }

    @Override
    public void run() {
        try {
            switch (taskNumber) {
                case 0 -> pass1.set(0, (agents.get(0).multiply(matrixA.get(0), matrixB.get(0))));
                case 1 -> pass1.set(1, (agents.get(1).multiply(matrixA.get(0), matrixB.get(1))));
                case 2 -> pass1.set(2, (agents.get(2).multiply(matrixA.get(2), matrixB.get(0))));
                case 3 -> pass1.set(3, (agents.get(3).multiply(matrixA.get(2), matrixB.get(1))));
                case 4 -> pass2.set(0, (agents.get(0).multiply(matrixA.get(1), matrixB.get(2))));
                case 5 -> pass2.set(1, (agents.get(1).multiply(matrixA.get(1), matrixB.get(3))));
                case 6 -> pass2.set(2, (agents.get(2).multiply(matrixA.get(3), matrixB.get(2))));
                case 7 -> pass2.set(3, (agents.get(3).multiply(matrixA.get(3), matrixB.get(3))));
                default -> throw new IndexOutOfBoundsException(taskNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
