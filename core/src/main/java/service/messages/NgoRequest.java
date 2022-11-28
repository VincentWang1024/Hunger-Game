package service.messages;

import java.util.ArrayList;

import service.core.Franchise;

public class NgoRequest{

    //each franchise -
    private ArrayList<Franchise> franchises = new ArrayList<Franchise>();

    public NgoRequest(ArrayList<Franchise> franchises) {
        this.franchises = franchises;
    }

    public NgoRequest() {

    }

    public ArrayList<Franchise> getFranchises() {
        return franchises;
    }

    public void setFranchises(ArrayList<Franchise> franchises) {
        this.franchises = franchises;
    }
}