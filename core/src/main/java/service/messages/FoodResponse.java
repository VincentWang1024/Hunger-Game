package service.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import service.core.BgkFood;
import service.core.Food;
import service.core.HungerInfo;
import service.core.McdFood;

public class FoodResponse implements MySerializable {
    private int id;
    private Food food;

    @JsonCreator
    public FoodResponse(int id, Food food) {
        this.id = id;
        this.food = food;
    }


    public FoodResponse() {
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
