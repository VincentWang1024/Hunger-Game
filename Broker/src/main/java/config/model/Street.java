package config.model;

public class Street {
    private String id;
    private int length;

    public Street(String id, int length) {
        this.id = id;
        this.length = length;
    }
    
    public String getId() {
        return id;
    }

    public int getLength() {
        return length;
    }
}
