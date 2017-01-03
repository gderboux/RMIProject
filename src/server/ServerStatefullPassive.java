package server;

import common.Data;
import registry.IGlobalRegistry;
import registry.LocateGlobalRegistry;
import registry.ReplicationStrategy;

import java.net.InetAddress;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;

/**
 * ServerStateless program.
 *
 * Note: After the main method exits, the JVM will still run. This is because
 * the skeleton implements a non-daemon listening thread, which waits for
 * incoming requests forever.
 *
 */
public class ServerStatefullPassive {

  //
  // CONSTANTS
  //
  private static final String SERVICE_PASSIVE_REPOSITORY = "Passive DATA";
  private static final int TIME = 5 * 1000;


  //
  // MAIN
  //
  public static void main(String[] args) throws Exception {

    System.out.println("server: running on host " + InetAddress.getLocalHost());
    IGlobalRegistry registry = (IGlobalRegistry)LocateGlobalRegistry.getRegistry();
    String hostAddress = InetAddress.getLocalHost().getHostAddress();

    // instanciate the remote object
    Data data = new DataImpl(ReplicationStrategy.PASSIVE, SERVICE_PASSIVE_REPOSITORY);
    System.out.println("server: instanciated DataImpl");
    Data stub = (Data) UnicastRemoteObject.exportObject(data, 0);
    System.out.println("server: generated skeleton and stub");

    registry.rebind("rmi://" + hostAddress + "/" + SERVICE_PASSIVE_REPOSITORY, stub);
    System.out.println("server: registered remote object's stub");

    //abonnement du stub au stub primaire
    if (registry.getPrimaryRemote(SERVICE_PASSIVE_REPOSITORY) != null) {
      Timer t = new Timer();
      t.scheduleAtFixedRate(new SubscriptionTask(registry, SERVICE_PASSIVE_REPOSITORY, stub), 0 ,TIME);
    }

    // main terminates here, but the JVM still runs because of the skeleton
    System.out.println("server: ready");
  }

}
