package service.messages;

import service.core.Food;
import service.core.HungerInfo;

public class FoodResponse {
    private int id;
    private Food food;

    public FoodResponse(int id, Food food) {
        this.id = id;
        this.food = food;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
