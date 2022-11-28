package service.core;

import java.util.Random;

public class AbstractFoodService implements FoodService {

    private Random random = new Random();

    @Override
    public int getFoodQuantity() {
        return random.nextInt(50);

    }

}
