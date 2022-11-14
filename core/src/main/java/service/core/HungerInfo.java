package service.core;

import java.io.Serializable;

public class HungerInfo implements Serializable {
    private int reqQuantity;

    public HungerInfo(int reqQuantity) {
        this.reqQuantity = reqQuantity;
    }

    public int getReqQuantity() {
        return reqQuantity;
    }

    public void setReqQuantity(int reqQuantity) {
        this.reqQuantity = reqQuantity;
    }
}
