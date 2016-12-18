package client;

import common.Sorter;
import registry.LocateGlobalRegistry;

import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;

/**
 * Client program.
 *
 * Note: For the the client to retrieve the stub of the remote object, it needs
 * to know: (1) what the name of the object is, (2) which machine hosts the
 * remote object.
 *
 */
public class Client {

  //
  // CONSTANTS
  //
  private static String SERVICE_NAME = "Sorter";
  private static String REGISTRY_HOST = "localhost";

  //
  // MAIN
  //
  public static void main(String[] args) throws Exception {

    // locate the registry that runs on the remote object's server
    Registry registry = LocateGlobalRegistry.getRegistry(REGISTRY_HOST);
    System.out.println("client: retrieved registry");

    // retrieve the stub of the remote object by its name
    Sorter sorter = (Sorter) registry.lookup(SERVICE_NAME);
    System.out.println("client: retrieved Sorter stub");

    // call the remote object to perform sorts and reverse sorts
    List<String> list = Arrays.asList("3", "5", "1", "2", "4");
    System.out.println("client: sending " + list);

    list = sorter.sort(list);
    System.out.println("client: received " + list);

    list = Arrays.asList("mars", "saturne", "neptune", "jupiter");
    System.out.println("client: sending " + list);

    list = sorter.reverseSort(list);
    System.out.println("client: received " + list);

    // main terminates here
    System.out.println("client: exiting");

  }

}
