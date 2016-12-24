package registry;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Balancer extends Remote {

    void rebindServer(String serverName, double charge) throws RemoteException;

    String getBestServer(List<String> serverList) throws RemoteException;
}
