package junction.model;

import java.util.LinkedList;

import massim.core.model.BasicState;

public class VehicleState extends BasicState {
    public int at;
    public int speed;
    public int queue;
    public LinkedList<String> exits = new LinkedList<>();
}
