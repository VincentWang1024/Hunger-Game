package service.messages;

import service.core.FranchiseService;

public class Init {

    FranchiseService franchiseService;

    public Init(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }

    public FranchiseService getFranchiseService() {
        return franchiseService;
    }

    public void setFranchiseService(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }

}
