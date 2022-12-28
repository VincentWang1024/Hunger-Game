package service.core;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientAppliation implements Serializable {

    public String getFranchiseName() {
        return franchiseName;
    }

    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;
    }

    private String franchiseName;
    private Integer applicationNumber;
    private ArrayList<Quotation> list;

    public ClientAppliation(){}

    public ClientAppliation(String franchiseName, Integer applicationNumber, ArrayList<Quotation> list) {
        this.franchiseName = franchiseName;
        this.applicationNumber = applicationNumber;
        this.list = list;
    }

    public Integer getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(Integer applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public ArrayList<Quotation> getList() {
        return list;
    }

    public void setList(ArrayList<Quotation> list) {
        this.list = list;
    }
}
