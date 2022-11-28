package service.burgerking;

public class BQService {

    String franchiseName;

    public BQService(String franchiseName) {
        this.franchiseName = franchiseName;
    }

    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;

    }

    public String getFranchiseName() {
        return this.franchiseName;
    }

}
