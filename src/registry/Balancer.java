package registry;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Balancer extends Remote {

    void incrementServerCharge(String serverName) throws RemoteException;

    void decrementServerCharge(String serverName) throws RemoteException;

    void rebindServer(String serverName) throws RemoteException;

    String getBestServer(List<String> serverList) throws RemoteException;
}
