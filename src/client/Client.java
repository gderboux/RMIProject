package client;

import common.Formatter;
import common.Sorter;
import registry.Balancer;
import registry.LocateGlobalRegistry;

import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;

/**
 * Client program.
 */
public class Client {

  //
  // CONSTANTS
  //
  private static final String SERVICE_SORTER = "Sorter";
  private static final String SERVICE_FORMATTER = "Formatter";
  private static final String REGISTRY_HOST = "localhost";

  //
  // MAIN
  //
  public static void main(String[] args) throws Exception {

    // locate the registry that runs on the remote object's server
    Registry registry = LocateGlobalRegistry.getRegistry(REGISTRY_HOST);
    System.out.println("client: retrieved registry");

    // retrieve the stub of the remote object by its name
    Sorter sorter = (Sorter) registry.lookup(SERVICE_SORTER);
    System.out.println("client: retrieved Sorter stub");
    Formatter formatter = (Formatter) registry.lookup(SERVICE_FORMATTER);
    System.out.println("client: retrieved Formatter stub");

    //Examples
    List<String> list = Arrays.asList("3", "5", "1", "2", "4");
    System.out.println("client: sending " + list);
    list = sorter.sort(list);
    System.out.println("client: received " + list);

    list = Arrays.asList("mars", "saturne", "neptune", "jupiter");
    System.out.println("client: sending " + list);
    list = sorter.reverseSort(list);
    System.out.println("client: received " + list);

    list = Arrays.asList("mars", "saturne", "neptune", "jupiter");
    System.out.println("client: sending " + list);
    list = formatter.toUpperCase(list);
    System.out.println("client: received " + list);


    // main terminates here
    System.out.println("client: exiting");

  }

}
