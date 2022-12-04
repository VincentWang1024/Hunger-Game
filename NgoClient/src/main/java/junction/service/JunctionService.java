package junction.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import junction.model.JunctionConfiguration;
import junction.simulation.JunctionSimulation;

@Component
public class JunctionService {
    private Map<String, JunctionSimulation> simulations = new HashMap<>();
    
    public JunctionSimulation addJunction(JunctionConfiguration configuration) {
        JunctionSimulation simulation = new JunctionSimulation(configuration);
        simulations.put(configuration.id, simulation);
        return simulation;
    }

    public JunctionSimulation getSimulation(String id) {
        return simulations.get(id);
    }

    public Collection<JunctionSimulation> getSimulations() {
        return simulations.values();
    }


}
