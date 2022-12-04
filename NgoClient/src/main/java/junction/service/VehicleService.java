package junction.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import junction.model.Vehicle;

@Component
public class VehicleService {
    Map<String, Vehicle> vehicles = new HashMap<>();

    public void add(Vehicle vehicle) {
        vehicles.put(vehicle.getAgent().name, vehicle);
    }

    public Vehicle remove(String name) {
        return vehicles.remove(name);
    }
    
    public Collection<Vehicle> getVehicles() {
        return vehicles.values();
    }

    public Vehicle getVehicle(String name) {
        return vehicles.get(name);
    }
}
