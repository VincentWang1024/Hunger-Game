package service.messages;

import service.core.NgoInfo;

public class NgoResponse {

    private int appnum;
    private NgoInfo ngoInfo;
    private int totalFood;


    public NgoResponse(int appnum, NgoInfo info, int totalFood) {
        this.appnum = appnum;
        this.ngoInfo = info;
        this.totalFood = totalFood;

    }

    public NgoResponse() {
    }


    public int getAppnum() {
        return appnum;
    }


    public void setAppnum(int appnum) {
        this.appnum = appnum;
    }


    public NgoInfo getNgoInfo() {
        return ngoInfo;
    }


    public void setNgoInfo(NgoInfo ngoInfo) {
        this.ngoInfo = ngoInfo;
    }


    public int getTotalFood() {
        return totalFood;
    }


    public void setTotalFood(int totalFood) {
        this.totalFood = totalFood;
    }
    
}
