package server1;

import common.Sorter;
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

  //
  // MAIN
  //
  public static void main(String[] args) throws Exception {

    // check the name of the local machine (two methods)
    System.out.println("server: running on host " + InetAddress.getLocalHost());
    System.out.println("server: hostname property "
            + System.getProperty("java.rmi.server.hostname"));

    // instanciate the remote object
    Sorter sorter = new SimpleSorter();
    System.out.println("server: instanciated SimpleSorter");

    // create a skeleton and a stub for that remote object
    Sorter stub = (Sorter) UnicastRemoteObject.exportObject(sorter, 0);
    System.out.println("server: generated skeleton and stub");

    // register the remote object's stub in the registry
    Registry registry = LocateGlobalRegistry.getRegistry();
    registry.rebind(SERVICE_NAME, stub);
    System.out.println("server: registered remote object's stub");

    // main terminates here, but the JVM still runs because of the skeleton
    System.out.println("server: ready");

  }

}
