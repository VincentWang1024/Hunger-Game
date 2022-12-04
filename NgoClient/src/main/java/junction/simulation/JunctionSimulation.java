package junction.simulation;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import junction.model.JunctionConfiguration;
import junction.model.JunctionDescription;
import junction.model.Vehicle;
import junction.model.VehicleQueue;
import massim.core.model.Link;

/**
 * Simulation dequeues 1 vehicle from 1 queue per tick
 */
public class JunctionSimulation {
    private JunctionConfiguration configuration;
    private VehicleQueue[] queues;
    private Link[] out;
    private int activeQueue = 0;
    
    public JunctionSimulation(JunctionConfiguration configuration) {
        this.configuration = configuration;

        queues = new VehicleQueue[configuration.inLinks];
        for (int i=0;i<queues.length;i++) {
            queues[i]=new VehicleQueue(this, i);
        }

        out = new Link[configuration.outLinks];
        for (int i=0;i<out.length;i++) {
            out[i]=new Link();
        }
    }

    public JunctionDescription getJunctionDescription(String host) {
        JunctionDescription description = new JunctionDescription(configuration);
        for (VehicleQueue queue : queues) {
            description.queues.add(queue.getQueueDescription(host));
            description.in.add(new Link(queue.getUrl()));
        }
        for (Link link: out) {
            description.out.add(link);
        }
        return description;
    }

    public VehicleQueue getVehicleQueue(int index) {
        return queues[index];
    }

    public JunctionConfiguration getJunction() {
        return configuration;
    }

    public String getUrl(String host) {
        return "http://" + host + "/junctions/" + configuration.id;
    }

    public Link getInLink(int index) {
        return new Link(queues[index].getUrl());
    }

    public Link getOutLink(int index) {
        return out[index];
    }

    public LinkedList<Link> getInLinks() {
        LinkedList<Link> list = new LinkedList<>();
        for (VehicleQueue queue : queues) {
            list.add(new Link(queue.getUrl()));
        }
        return list;
    }

    public LinkedList<Link> getOutLinks() {
        LinkedList<Link> list = new LinkedList<>();
        for (Link link : out) {
            list.add(link);
        }
        return list;
    }

    public void setOutLink(int index, String url) {
        out[index].url = url;
    }

    public Map<String, URI> step(int step) {
        Map<String, URI> map = new HashMap<>();

        int n=0;
        Vehicle vehicle = null;
        VehicleQueue queue = null;
        while (n < queues.length && vehicle == null) {
            queue = queues[activeQueue];
            vehicle = queue.head();
            activeQueue = (activeQueue + 1) % queues.length;
            n++;
        }

        // if you found a queue with a vehicle in it, see if the right action 
        // has been selected (i.e. turn(X) where X is the exit number).
        if (vehicle != null && queue != null) {
            // Process the vehicle...
            System.out.println("[" + vehicle.getAgent().name + "] action: " + vehicle.getAction());
            if (vehicle.getAction().id.equals("turn")) {
                String uri = ((String) vehicle.getAction().parameters[0]);
                
                System.out.println("Transferring to: " + uri);
                RestTemplate template = new RestTemplate();
                URI location = template.postForLocation(uri, vehicle.getAgent());
                System.out.println("Now at: " + location);
                queue.dequeue();
                map.put(vehicle.getAgent().name, location);
            }
        }
        return map;
    }

    public String toHTML() {
        StringBuilder builder = new StringBuilder();
        builder.
            append("<style type='text/css'> td { min-width:30px;height:30px;text-align:center;vertical-align:middle; }</style>").
            append("<table border=1><tr><td></td>");
        for (int i=0; i<queues.length; i++) {
            builder.
                append("<td>").
                append(i).
                append("</td>");
        }
        builder.append("<td></td></tr><tr><td>INLINKS</td>");
        for (int i=0; i<queues.length; i++) {
            builder.
                append("<td>").
                append(queues[i].getUrl()).
                append("</td>");
        }
        builder.append("<td></td></tr><tr><td></td>");
        for (int i=0; i<queues.length; i++) {
            builder.
                append("<td>").
                append(queues[i].toHTML()).
                append("</td>");
        }
        builder.append("<td></td></tr><tr><td>OUTLINKS</td>");
        for (int i=0; i<out.length; i++) {
            builder.
                append("<td>").
                append(out[i].url).
                append("</td>");
        }
        builder.append("<td></td></tr></table>");

        return builder.toString();
    }
}
