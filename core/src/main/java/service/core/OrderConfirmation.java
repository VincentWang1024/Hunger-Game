package service.core;

public class OrderConfirmation {

    String orderConf;

    //default Constructor
    public OrderConfirmation(){

    }
    
    public OrderConfirmation(String orderConf) {
        this.orderConf = orderConf;
    }

    //getter and setter method
    public String getOrderConf() {
        return orderConf;
    }

    public void setOrderConf(String orderConf) {
        this.orderConf = orderConf;
    }

    
}
