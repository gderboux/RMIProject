package client;

import common.Data;
import common.Sorter;
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
  private static final String REGISTRY_HOST = "localhost";
  private static final String SERVICE_PASSIVE_REPOSITORY = "Passive Repository";

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
    Data data = (Data) registry.lookup(SERVICE_PASSIVE_REPOSITORY);
    System.out.println("client: retrieved Data stub");

    //Examples
    List<String> list = Arrays.asList("3", "5", "1", "2", "4");
    System.out.println("client: sending " + list);
    list = sorter.sort(list);
    System.out.println("client: received " + list);

    list = Arrays.asList("mars", "saturne", "neptune", "jupiter");
    System.out.println("client: sending " + list);
    list = sorter.reverseSort(list);
    System.out.println("client: received " + list);

    String dataValue = "test2";
    System.out.println("client: sending data  " + dataValue);
    data.setData(dataValue);
    System.out.println("client: receive data  " + data.getData());




    // main terminates here
    System.out.println("client: exiting");

  }

}
