import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Client() {
    }

    public static void main(String[] args) {

        List<List<Double>> dataFromUser = getDataFromUser();

        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);
            // Looking up the registry for the remote object
            ServerInterface stub = (ServerInterface) registry.lookup("Server");

            // Calling the remote method using the obtained object
            System.out.println("Starting remote method.");
            long millis = System.currentTimeMillis();
            List<Double> values = stub.compute(dataFromUser.get(0), dataFromUser.get(1), true); //Przełącznik współbieżnego wykonania
            System.out.println("Remote method invoked! Time elapsed: " + (System.currentTimeMillis() - millis) / 1000L + " s");
            System.out.println("Multiplied matrix: ");
            for (double value : values) {
                System.out.print(value + " ");
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }

    private static List<List<Double>> getDataFromUser() {
        //[a11, a12, a21, a22]
        List<Double> matrixA = new ArrayList<>();
        List<Double> matrixB = new ArrayList<>();

        System.out.println("Podaj składniki macierzy A w kolejności: a11 a12 a21 a22. Akceptuj każdy Enterem");
        try {
            matrixA.add(Double.parseDouble(reader.readLine()));
            matrixA.add(Double.parseDouble(reader.readLine()));
            matrixA.add(Double.parseDouble(reader.readLine()));
            matrixA.add(Double.parseDouble(reader.readLine()));
            System.out.println("Podaj składniki macierzy B w kolejności: b11 b12 b21 b22. Akceptuj każdy Enterem");
            matrixB.add(Double.parseDouble(reader.readLine()));
            matrixB.add(Double.parseDouble(reader.readLine()));
            matrixB.add(Double.parseDouble(reader.readLine()));
            matrixB.add(Double.parseDouble(reader.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<List<Double>> list = new ArrayList<>();
        list.add(matrixA);
        list.add(matrixB);
        return list;
    }
}
