package server;

import common.Data;
import registry.IGlobalRegistry;

import java.rmi.RemoteException;
import java.util.TimerTask;


public class SubscriptionTask extends TimerTask {

    private Data dataStub;
    private IGlobalRegistry registry;
    private String serviceName;

    @Override
    public void run() {
        try {
            Data primaryStub = (Data) registry.getPrimaryRemote(serviceName);
            System.out.println("Valeur du service Primaire : " + primaryStub.getData());
            System.out.println("Valeur du service actif : " + dataStub.getData());
            dataStub.setData(primaryStub.getData());
            System.out.println("Mise à jour du stub actif");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SubscriptionTask(IGlobalRegistry registry, String serviceName, Data dataStub) {
        this.registry = registry;
        this.serviceName = serviceName;
        this.dataStub = dataStub;
    }
}
