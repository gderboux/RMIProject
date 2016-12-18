package registry;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Balancer extends Remote {

    void incrementServer(String serverName) throws RemoteException;

    void decrementServer(String serverName) throws RemoteException;

    void bindServer(String serverName) throws RemoteException;

    void unbindServer(String serverName) throws RemoteException;

    String getBestServer(List<String> serverList) throws RemoteException;
}
