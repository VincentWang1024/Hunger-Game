package service.core;

public class McdFood implements Food{
    private int quantity;

    public McdFood(){

    }

    public McdFood(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "McdFood{" +
                "quantity=" + quantity +
                '}';
    }
}
