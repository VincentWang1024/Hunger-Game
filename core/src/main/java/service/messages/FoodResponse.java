package service.messages;

public class FoodResponse implements MySerializable {
    int id;
    String name;
    int foodQuantity;

    public FoodResponse() {

    }

    public FoodResponse(int id, String name, int foodQuantity) {
        this.id = id;
        this.name = name;
        this.foodQuantity = foodQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

}
