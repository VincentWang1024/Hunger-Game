package service.core;
import java.util.*;

public class Franchise {
    private String franchiseName;
    private String franchiseBranchName;
    private int totalBranchFood;

    

    public Franchise(String franchiseName, String franchiseBranchName, int totalBranchFood) {
        this.franchiseName = franchiseName;
        this.franchiseBranchName = franchiseBranchName;
        this.totalBranchFood = totalBranchFood;
    }

    public Franchise() {
    }

    public String getFranchiseName() {
        return franchiseName;
    }
    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;
    }
    public String getFranchiseBranchName() {
        return franchiseBranchName;
    }
    public void setFranchiseBranchName(String franchiseBranchName) {
        this.franchiseBranchName = franchiseBranchName;
    }
    public int getTotalBranchFood() {
        return totalBranchFood;
    }
    public void setTotalBranchFood(int totalBranchFood) {
        this.totalBranchFood = totalBranchFood;
    }
    


}
