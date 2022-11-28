package service.messages;

public class RequestDeadline implements MySerializable{
    private int SEED_ID;

    public RequestDeadline(int id) {
        this.SEED_ID=id;
    }

    public RequestDeadline() {
    }

    public int getSEED_ID() {
        return SEED_ID;
    }

    public void setSEED_ID(int SEED_ID) {
        this.SEED_ID = SEED_ID;
    }
}