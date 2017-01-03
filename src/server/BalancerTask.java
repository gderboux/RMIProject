package server;

import registry.Balancer;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.rmi.RemoteException;
import java.util.TimerTask;


public class BalancerTask extends TimerTask {
    Balancer balancer;
    String hostName;

    public BalancerTask(Balancer balancer, String hostname) {
        this.balancer = balancer;
        this.hostName = hostname;
    }

    @Override
    public void run() {
        OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean();
        try {
            balancer.rebindServer(hostName, bean.getSystemLoadAverage() );
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
