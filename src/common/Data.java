package common;

import registry.ReplicationStrategy;
import registry.Statefull;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface Data extends Statefull {

  String getData() throws RemoteException, NotBoundException;

  String getDataLikeACopy() throws RemoteException, NotBoundException;

  void setDataLikeACopy(String value) throws RemoteException;

  void setData(String value) throws RemoteException, NotBoundException;

  ReplicationStrategy getReplicationStrategy() throws RemoteException;
}