package registry;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static java.rmi.registry.Registry.REGISTRY_PORT;


public class LocateGlobalRegistry {

    private static final String REGISTRY = "REGISTRY";
    private static final String BALANCER = "Balancer";
    private static final String REGISTRY_HOST = "localhost";

    public static Registry createRegistry() throws RemoteException, AlreadyBoundException, NotBoundException {
        Registry registry = LocateRegistry.createRegistry(REGISTRY_PORT);

        GlobalRegistry globalRegistry = new GlobalRegistry();
        Remote globalRegistryStub = UnicastRemoteObject.exportObject(globalRegistry, 0);
        registry.rebind(REGISTRY, globalRegistryStub);

        Balancer balancer = new BalancerImpl();
        Registry registryGlobal = LocateGlobalRegistry.getRegistry();
        Remote balancerStub = UnicastRemoteObject.exportObject(balancer, 0);
        registryGlobal.rebind(BALANCER, balancerStub);

        return registry;
    }

    public static Registry getRegistry() throws RemoteException, NotBoundException {
        return LocateGlobalRegistry.getRegistry(REGISTRY_HOST);
    }

    public static Registry getRegistry(String host) throws RemoteException, NotBoundException {
        return (Registry) LocateRegistry.getRegistry(host).lookup(REGISTRY);
    }
}
