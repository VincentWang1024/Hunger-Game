package service.core;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FoodService extends Remote {
    public Food makeFood(HungerInfo info) throws RemoteException;
}
