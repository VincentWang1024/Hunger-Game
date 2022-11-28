package service.mcd;

import service.core.FranchiseService;

public class MCDService implements FranchiseService {
    String franchiseName;

    public MCDService(String franchiseName) {
        this.franchiseName = franchiseName;
    }

    @Override
    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;

    }

    @Override
    public String getFranchiseName() {
        return this.franchiseName;
    }
}
