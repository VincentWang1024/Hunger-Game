package junction.model;

import java.util.LinkedList;

import massim.core.model.Link;

public class JunctionDescription {
    public JunctionDescription(JunctionConfiguration configuration) {
        this.id = configuration.id;
    }

    public String id;
    public LinkedList<VehicleQueueDescription> queues = new LinkedList<>();
    public LinkedList<Link> in = new LinkedList<>();
    public LinkedList<Link> out = new LinkedList<>();
}
