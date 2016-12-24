package registry;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BalancerImpl implements Balancer {

    Map<String, Double> serverCharge = new HashMap<>();

    @Override
    public void rebindServer(String serverName, double charge) {
        System.out.println(serverName +
                " load average for the last minute : "
                + charge);
        serverCharge.put(serverName, new Double(charge));
    }

    @Override
    public String getBestServer(List<String> serverList) {

        return serverCharge.entrySet().stream()
                .filter(map -> serverList.contains(map.getKey()))
                .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
