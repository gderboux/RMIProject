package server;

import common.Data;
import registry.IGlobalRegistry;
import registry.LocateGlobalRegistry;
import registry.ReplicationStrategy;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataImpl implements Data {

    private String data;
    private ReplicationStrategy replicationStrategy;
    private String serviceName;

    @Override
    public String getData() throws RemoteException, NotBoundException {
        if (replicationStrategy.equals(ReplicationStrategy.PASSIVE)) {
            return data;
        }

        IGlobalRegistry registry = (IGlobalRegistry) LocateGlobalRegistry.getRegistry();
        List<Data> remoteList = (List<Data>) (List<?>)registry.remoteListForAService(serviceName);
        Map<String, Integer> valueListOccurence = new HashMap<>();
        remoteList.stream()
                .map(remote -> {
                    try {
                        System.out.println("valeur de l'objet " + remote + " : " + remote.getDataLikeACopy());
                        return remote.getDataLikeACopy();
                    }  catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }).forEach(s -> {
            if (valueListOccurence.containsKey(s)) {
                valueListOccurence.put(s, valueListOccurence.get(s).intValue() + 1);
            } else {
                valueListOccurence.put(s, new Integer(1));
            }
        });

        return valueListOccurence.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    @Override
    public void setData(String value) throws RemoteException, NotBoundException {
        System.out.println(this + ": receveid " + value);
        this.data = value;

        if (replicationStrategy.equals(ReplicationStrategy.ACTIVE)) {
            IGlobalRegistry registry = (IGlobalRegistry) LocateGlobalRegistry.getRegistry();
            List<Data> remoteList = (List<Data>) (List<?>)registry.remoteListForAService(serviceName);
            remoteList.forEach(remote -> {
                try {
                    remote.setDataLikeACopy(value);
                } catch (Exception e) {
                  e.printStackTrace();
                }
            });
        }
    }

    public void setDataLikeACopy(String value) {
        this.data = value;
    }

    public String getDataLikeACopy() {
        return this.data;
    }

    @Override
    public ReplicationStrategy getReplicationStrategy() throws RemoteException {
        return this.replicationStrategy;
    }

    DataImpl(ReplicationStrategy replicationStrategy, String serviceName) {
        this.replicationStrategy = replicationStrategy;
        this.serviceName = serviceName;
    }
}
