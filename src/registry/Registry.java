package registry;

import java.net.InetAddress;


public class Registry {

    private static final int REGISTRY_PORT = 1099;

    public static synchronized void main(String[] args) throws Exception {

        System.out.println("registry: running on host " + InetAddress.getLocalHost());

        // create the registry on the local machine, on the default port number
        LocateGlobalRegistry.createRegistry();
        System.out.println("registry: listening on port " + REGISTRY_PORT);



        // block forever
        Registry.class.wait();
        System.out.println("registry: exiting (should not happen)");

    }
}
