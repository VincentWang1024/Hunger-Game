package junction.model;

import java.util.LinkedList;

import junction.simulation.JunctionSimulation;
import massim.core.model.AgentDescription;

public class VehicleQueue {
    private String url;
    private LinkedList<Vehicle> vehicles = new LinkedList<>();
    private JunctionSimulation simulation;
    private int index;

    public VehicleQueue(JunctionSimulation simulation, int index) {
        this.simulation = simulation;
        this.index = index;
    }

    public VehicleQueueDescription getQueueDescription(String host) {
        return new VehicleQueueDescription(url, vehicles, host);
    }

    public Vehicle addVehicle(AgentDescription description) {
        Vehicle vehicle = new Vehicle(simulation, index, description);
        vehicles.add(vehicle);
        return vehicle;
    }

    public Vehicle head() {
        return vehicles.peek();
    }

    public Vehicle dequeue() {
        return vehicles.remove();
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url)  {
        this.url = url;
    }

    public String toHTML() {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Vehicle vehicle : vehicles) {
            if (first) first=false;else builder.append("<br/>");
            builder.append(vehicle.getAgent().name);
        }
        return builder.toString();
    }

    public int atLocation(Vehicle vehicle) {
        int location = 0;
        int i = vehicles.size()-1;
        while (i >= 0 && !vehicles.get(i).equals(vehicle)) {
            i--;
            location++;
        }
        return location;
    }

}