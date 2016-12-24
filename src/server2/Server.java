package server2;

import common.Formatter;
import common.Sorter;
import registry.Balancer;
import registry.LocateGlobalRegistry;
import server1.BalancerTask;
import server1.SorterImpl;

import java.net.InetAddress;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;

/**
 * Server program.
 *
 * Note: After the main method exits, the JVM will still run. This is because
 * the skeleton implements a non-daemon listening thread, which waits for
 * incoming requests forever.
 *
 */
public class Server {

  //
  // CONSTANTS
  //
  private static final String SERVICE_NAME = "Formatter";
  private static final String BALANCER = "Balancer";
  private static final int TIME = 60 * 1000;

  //
  // MAIN
  //
  public static void main(String[] args) throws Exception {

    System.out.println("server: running on host " + InetAddress.getLocalHost());
    Registry registry = LocateGlobalRegistry.getRegistry();
    String hostAddress = InetAddress.getLocalHost().getHostAddress();

    //retrieve Balancer stub
    Balancer balancer = (Balancer) registry.lookup(BALANCER);

    //BalancerTask Balancer
    Timer t = new Timer();
    t.scheduleAtFixedRate(new BalancerTask(balancer, hostAddress), 0 ,TIME);

    // instanciate the Sorter remote object
    Formatter formatter = new FormatterImpl();
    System.out.println("server2: instanciated FormatterImpl");
    Formatter stub = (Formatter) UnicastRemoteObject.exportObject(formatter, 0);
    System.out.println("server2: generated skeleton and stub");
    registry.rebind("rmi://" + hostAddress + "/" + SERVICE_NAME, stub);
    System.out.println("server2: registered remote object's stub");

    // main terminates here, but the JVM still runs because of the skeleton
    System.out.println("server2: ready");
  }
}
