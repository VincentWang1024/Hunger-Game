package service.messages;

import service.core.HungerInfo;

public class FranchiseRequest implements MySerializable {
    private HungerInfo hungerInfo;

    public FranchiseRequest() {
    }

    public FranchiseRequest(HungerInfo info) {
        this.hungerInfo = info;
    }


    public HungerInfo getHungerInfo() {
        return hungerInfo;
    }

    public void setHungerInfo(HungerInfo hungerInfo) {
        this.hungerInfo = hungerInfo;
    }
}
