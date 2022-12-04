package junction.model;

import java.util.LinkedList;

public class VehicleQueueDescription {
    public String url;
    public LinkedList<String> vehicles = new LinkedList<>();

    public VehicleQueueDescription(String url, LinkedList<Vehicle> vehicles, String host) {
        this.url = url;
        for (Vehicle vehicle : vehicles) {
            this.vehicles.add(vehicle.getUrl(host));
        }
    }

    public VehicleQueueDescription() {}
}
