package common;

import registry.Statefull;

import java.rmi.RemoteException;

public interface Data extends Statefull {

  String getData() throws RemoteException;

  void setData(String value) throws RemoteException;
}