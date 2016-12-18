package registry;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by derboux on 18/12/16.
 */
public class BalancerImpl implements Balancer {

    Map<String, Integer> serverCharge = new HashMap<>();
    @Override
    public void incrementServer(String serverName) {
        System.out.println("Balancer : serveur" + serverName + " is incremented");
        serverCharge.put(serverName, serverCharge.get(serverName) + 1);
    }

    @Override
    public void decrementServer(String serverName) {
        System.out.println("Balancer : serveur" + serverName + " is decremented");
        serverCharge.put(serverName, serverCharge.get(serverName) - 1);
    }

    @Override
    public void bindServer(String serverName) {
        System.out.println("Balancer : bind" + serverName);
        serverCharge.put(serverName, new Integer(0));
    }

    @Override
    public void unbindServer(String serverName) {
        System.out.println("Balancer : unbind" + serverName);
        serverCharge.remove(serverName);
    }

    @Override
    public String getBestServer(List<String> serverList) {
        return serverCharge.entrySet().stream()
                .filter(map -> serverList.contains(map.getKey()))
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
