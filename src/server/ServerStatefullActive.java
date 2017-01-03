package server;

import common.Data;
import registry.LocateGlobalRegistry;
import registry.ReplicationStrategy;

import java.net.InetAddress;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * ServerStateless program.
 *
 * Note: After the main method exits, the JVM will still run. This is because
 * the skeleton implements a non-daemon listening thread, which waits for
 * incoming requests forever.
 *
 */
public class ServerStatefullActive {

  //
  // CONSTANTS
  //
  private static final String SERVICE_ACTIVE_REPOSITORY = "Active DATA";


  //
  // MAIN
  //
  public static void main(String[] args) throws Exception {

    System.out.println("server: running on host " + InetAddress.getLocalHost());
    Registry registry = LocateGlobalRegistry.getRegistry();
    String hostAddress = InetAddress.getLocalHost().getHostAddress();

    // instanciate the Sorter remote object
    Data data = new DataImpl(ReplicationStrategy.ACTIVE, SERVICE_ACTIVE_REPOSITORY);
    System.out.println("server: instanciated DataImpl");
    Data stub = (Data) UnicastRemoteObject.exportObject(data, 0);
    System.out.println("server: generated skeleton and stub");
    registry.rebind("rmi://" + hostAddress + "/" + SERVICE_ACTIVE_REPOSITORY, stub);
    System.out.println("server: registered remote object's stub");

    // main terminates here, but the JVM still runs because of the skeleton
    System.out.println("server: ready");
  }

}
