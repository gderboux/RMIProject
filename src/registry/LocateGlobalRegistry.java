package registry;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static java.rmi.registry.Registry.REGISTRY_PORT;


public class LocateGlobalRegistry {

    private static final String REGISTRY = "REGISTRY";
    private static final String REGISTRY_HOST = "localhost";

    public static Registry createRegistry() throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(REGISTRY_PORT);

        GlobalRegistry glob_reg = new GlobalRegistry();
        // create a skeleton and a stub for that remote object
        Remote stub = UnicastRemoteObject.exportObject(glob_reg, 0);
        System.out.println("server: generated skeleton and stub");
        registry.rebind(REGISTRY, stub);

        return registry;
    }

    public static Registry getRegistry() throws AccessException, RemoteException, NotBoundException {
        return LocateGlobalRegistry.getRegistry(REGISTRY_HOST);
    }

    public static Registry getRegistry(String host) throws AccessException, RemoteException, NotBoundException {
        return (Registry) LocateRegistry.getRegistry(host).lookup(REGISTRY);
    }
}
