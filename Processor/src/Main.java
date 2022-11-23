import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

public class Main{
    public static Registry r = null;
    public static ProcessorManager pManager;

    public static void startProcessor(int id, int port) throws IOException {

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    UUID identificador = UUID.fromString(UUID.nameUUIDFromBytes(String.valueOf(port).getBytes()).toString());
                    Registry r = LocateRegistry.createRegistry(port);
                    Processor p = new Processor(identificador,port);
                    pManager = new ProcessorManager(id, port);
                    r.rebind("Processor", pManager);
                    System.out.println("Processor " + id + " ready");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
    public static void main(String[] args) throws IOException {
        startProcessor(1, 2002);
        startProcessor(2, 2003);
    }
}