package registry;


import java.rmi.*;
import java.util.List;

public interface IGlobalRegistry extends java.rmi.registry.Registry {
    @Override
    Remote lookup(String name) throws RemoteException, NotBoundException;

    @Override
    void bind(String name, Remote obj) throws RemoteException, AlreadyBoundException;

    @Override
    void unbind(String name) throws RemoteException, NotBoundException;

    @Override
    void rebind(String name, Remote obj) throws RemoteException;

    @Override
    String[] list() throws RemoteException;

    Remote getPrimaryRemote(String service) throws RemoteException;

    List<Remote> remoteListForAService (String service) throws RemoteException;
}
