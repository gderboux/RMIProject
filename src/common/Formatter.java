package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The Formatter interface defines a service that <code>format</code> and
 * lists of <code>String</code>s.
 */
public interface Formatter extends Remote {

  List<String> toUpperCase(List<String> list) throws RemoteException;

}
