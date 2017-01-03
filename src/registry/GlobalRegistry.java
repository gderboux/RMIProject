package registry;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GlobalRegistry implements IGlobalRegistry {

    private static final String BALANCER = "Balancer";

    Map<String, Remote> remoteMap = new HashMap<>();


    @Override
    public Remote lookup(String s) throws RemoteException, NotBoundException {
        if (s.equals(BALANCER)) {
            return remoteMap.get(BALANCER);
        }
        List<String> availableServerList = availableServerList(s);
        if (availableServerList != null) {
            Remote remote = remoteMap.get(availableServerList.get(0));
            if (remote instanceof Statefull) {
                return remote;
            }
        }
        Balancer balancer = (Balancer) this.lookup(BALANCER);
        String bestServer = findBestServer(s, availableServerList, balancer);
        System.out.println(bestServer);
        return remoteMap.get(bestServer);
    }

    private String findBestServer(String serviceName, List<String> availableServerList, Balancer balancer) throws NotBoundException {
        return remoteMap.keySet().stream()
                .filter(key -> {
                    try {
                        return key.contains(balancer.getBestServer(availableServerList));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .filter(key2 -> key2.contains(serviceName))
                .findFirst()
                .orElseThrow(NotBoundException::new);
    }

    private List<String> availableServerList(String s) {
        return remoteMap.keySet().stream()
                .map(key -> key.split("/"))
                .filter(keyTab -> keyTab[keyTab.length - 1].equals(s))
                .map(keyTab2 -> keyTab2[keyTab2.length -2])
                .collect(Collectors.toList());
    }

    @Override
    public void bind(String s, Remote remote) throws RemoteException, AlreadyBoundException {
        remoteMap.put(s, remote);
    }

    @Override
    public void unbind(String s) throws RemoteException, NotBoundException {
        remoteMap.remove(s);
    }

    @Override
    public void rebind(String s, Remote remote) throws RemoteException {
        remoteMap.put(s, remote);
    }

    @Override
    public String[] list() throws RemoteException {
        return new String[0];
    }

    @Override
    public Remote getPrimaryRemote(String service) throws RemoteException {
        return remoteMap.get("rmi://" + availableServerList(service).get(0) + "/" + service);
    }
}
