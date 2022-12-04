package config.model;

public class Junction {
    private String id;
    private int inLinks;
    private int outLinks;

    public Junction(String id, int inLinks, int outLinks) {
        this.id = id;
        this.inLinks = inLinks;
        this.outLinks = outLinks;
    }
    
    public String getId() {
        return id;
    }

    public int getInLinks() {
        return inLinks;
    }

    public int getOutLinks() {
        return outLinks;
    }
}
