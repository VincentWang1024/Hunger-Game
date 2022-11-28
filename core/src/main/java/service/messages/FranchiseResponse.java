package service.messages;

import java.util.ArrayList;
import java.util.List;

public class FranchiseResponse {
    String franchiseName;
    ArrayList<FoodResponse> foodResponse = new ArrayList<FoodResponse>();

    public FranchiseResponse() {
    }

    public FranchiseResponse(String franchiseName, ArrayList<FoodResponse> foodResponse) {
        this.franchiseName = franchiseName;
        this.foodResponse = foodResponse;
    }

    public List<FoodResponse> getFoodResponse() {
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
