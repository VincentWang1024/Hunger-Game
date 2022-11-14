package service.messages;

import service.core.HungerInfo;

public class FoodRequest implements MySerializable {
    private int id;
    private HungerInfo hungerInfo;

    public FoodRequest(int id, HungerInfo hungerInfo) {
        this.id = id;
        this.hungerInfo = hungerInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HungerInfo getHungerInfo() {
        return hungerInfo;
    }

    public void setHungerInfo(HungerInfo hungerInfo) {
        this.hungerInfo = hungerInfo;
    }
}
