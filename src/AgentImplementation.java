public class AgentImplementation implements AgentInterface {

    @Override
    public double add(double a, double b) {
        System.out.println("Adding...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return a + b;
    }

    @Override
    public double multiply(double a, double b) {
        System.out.println("Mutliplying...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return a * b;
    }
}
