package junction.model;

import junction.simulation.JunctionSimulation;
import massim.core.model.Agent;
import massim.core.model.AgentDescription;
import massim.core.model.Link;

public class Vehicle extends Agent {
    private JunctionSimulation simulation;
    private int queue;
    private int speed = 1;

    public Vehicle(JunctionSimulation simulation, int queue, AgentDescription agent) {
        super(agent);
        this.simulation = simulation;
        this.queue = queue;
    }

    public VehicleState getState(String host) {
        VehicleState state = new VehicleState();
        state.type = "junction";
        state.url = simulation.getUrl(host);
        state.queue = queue;
        state.at = simulation.getVehicleQueue(queue).atLocation(this);
        for (Link link : simulation.getOutLinks()) {
            if (link.url != null && !link.url.equals("")) state.exits.add(link.url);
        }
        state.speed = speed;
        return state;
    }
}
