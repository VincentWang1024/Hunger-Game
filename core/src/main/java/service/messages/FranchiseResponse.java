package service.messages;

import service.core.Food;
import service.core.HungerInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FranchiseResponse implements MySerializable {
    private HungerInfo info;
    private ArrayList<Food> list;

    public FranchiseResponse() {
    }

    public FranchiseResponse(HungerInfo info, ArrayList<Food> list) {
        this.info = info;
        this.list = list;
    }


    public HungerInfo getInfo() {
        return info;
    }

    public void setInfo(HungerInfo info) {
        this.info = info;
    }

    public ArrayList<Food> getList() {
        return list;
    }

    public void setList(ArrayList<Food> list) {
        this.list = list;
    }
}
