package service.messages;

import service.core.FoodService;

public class Init {

    FoodService foodService;

    public Init(FoodService foodService) {
    }

    public FoodService getFoodService() {
        return foodService;
    }

    public void setFoodService(FoodService foodService) {
        this.foodService = foodService;
    }

}