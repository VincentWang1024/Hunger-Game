package service.core;

public class BgkFood implements Food {
    private int quantity;

    public BgkFood(){

    }

    public BgkFood(int quantity) {
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
        return "BgkFood{" +
                "quantity=" + quantity +
                '}';
    }
}
