package registry;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by derboux on 18/12/16.
 */
public class GlobalRegistry implements Registry {

    Map<String, Remote> remoteMap = new HashMap<>();

    @Override
    public Remote lookup(String s) throws RemoteException, NotBoundException, AccessException {
        return remoteMap.get(s);
    }

    @Override
    public void bind(String s, Remote remote) throws RemoteException, AlreadyBoundException, AccessException {
        remoteMap.put(s, remote);
    }

    @Override
    public void unbind(String s) throws RemoteException, NotBoundException, AccessException {
        remoteMap.remove(s);
    }

    @Override
    public void rebind(String s, Remote remote) throws RemoteException, AccessException {
        remoteMap.put(s, remote);
    }

    @Override
    public String[] list() throws RemoteException, AccessException {
        return new String[0];
    }
}
