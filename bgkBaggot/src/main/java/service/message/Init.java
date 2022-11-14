package service.message;

import service.core.FoodService;

public class Init {

    public FoodService service;

    public Init(FoodService foodService) {
        this.service=foodService;
    }
}