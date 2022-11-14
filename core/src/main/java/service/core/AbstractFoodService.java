package service.core;

import java.rmi.RemoteException;
import java.util.Random;

public abstract class AbstractFoodService implements FoodService {
    private Random random = new Random();

    protected int makeFood(int min, int range) {
   		return min + random.nextInt(range);
   	}
}
