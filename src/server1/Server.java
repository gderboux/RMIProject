package server1;

import common.Sorter;
import registry.Balancer;
import registry.LocateGlobalRegistry;

import java.net.InetAddress;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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
  private static final String SERVICE_NAME = "Sorter";
  private static final String BALANCER = "Balancer";
  private static String hostAddress = null;

  private static Balancer balancer = null;

  //
  // MAIN
  //
  public static void main(String[] args) throws Exception {

    System.out.println("server: running on host " + InetAddress.getLocalHost());
    Registry registry = LocateGlobalRegistry.getRegistry();
    hostAddress = InetAddress.getLocalHost().getHostAddress();

    //retrieve Balancer stub
    balancer = (Balancer) registry.lookup(BALANCER);
    balancer.bindServer(hostAddress);


    // instanciate the Sorter remote object
    Sorter sorter = new SorterImpl();
    System.out.println("server: instanciated SorterImpl");
    Sorter stub = (Sorter) UnicastRemoteObject.exportObject(sorter, 0);
    System.out.println("server: generated skeleton and stub");
    registry.rebind("rmi://" + hostAddress + "/" + SERVICE_NAME, stub);
    System.out.println("server: registered remote object's stub");

    // main terminates here, but the JVM still runs because of the skeleton
    System.out.println("server: ready");
  }

  public static Balancer getBalancer() {
    return balancer;
  }

  public static String getHostAddress() {
    return hostAddress;
  }

}
