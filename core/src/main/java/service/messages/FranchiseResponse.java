package service.messages;


import java.util.ArrayList;

public class FranchiseResponse {
    private String franchiseName;
    private ArrayList<FoodResponse> foodResponse;

    public FranchiseResponse() {
    }

    public FranchiseResponse(String franchiseName, ArrayList<FoodResponse> foodResponse) {
        this.franchiseName = franchiseName;
        this.foodResponse = foodResponse;
    }

    public ArrayList<FoodResponse> getFoodResponse() {
        return foodResponse;
    }

    public void setFoodResponse(ArrayList<FoodResponse> foodResponse) {
        this.foodResponse = foodResponse;
    }

    public FranchiseResponse(String franchiseName) {
        this.franchiseName = franchiseName;

    }

    public String getFranchiseName() {
        return franchiseName;
    }

    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;
    }

}
