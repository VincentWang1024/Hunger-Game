package junction.controller;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import junction.model.EnvironmentState;
import junction.model.JunctionConfiguration;
import junction.model.JunctionDescription;
import junction.model.Vehicle;
import junction.model.VehicleQueue;
import junction.model.VehicleState;
import junction.service.JunctionService;
import junction.service.VehicleService;
import junction.simulation.JunctionSimulation;
import massim.core.model.Action;
import massim.core.model.AgentDescription;
import massim.core.model.Iteration;
import massim.core.model.Link;

@RestController
public class JunctionController {
    private Iteration iteration;
    private Map<String, URI> redirects = new HashMap<>();
    
    @Autowired JunctionService junctionService;
    @Autowired VehicleService vehicleService;
    
    @Value("${server.port}")
    private int port;

    @GetMapping("/junctions/iteration")
    @ResponseStatus(HttpStatus.OK)
    public Iteration getIteration() {
        return iteration;
    }

    // keep food response here
    @PutMapping("/junctions/iteration")
    @CrossOrigin
    public void setIteration(@RequestBody Iteration iteration) {
        this.iteration = iteration;
        System.out.println("Iteration: " + iteration.getIteration());

        // Step streets...
        for (JunctionSimulation simulation : junctionService.getSimulations()) {
            System.out.println("Processing: " + simulation);
            Map<String, URI> exitingVehicles = simulation.step(iteration.getIteration());
            for (Entry<String, URI> entry : exitingVehicles.entrySet()) {
                System.out.println("Removing: " + entry.getKey());
                vehicleService.remove(entry.getKey());
                redirects.put(entry.getKey(), entry.getValue());
            }
        }

        // Send updated states to vehicles...
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        for (Vehicle vehicle : vehicleService.getVehicles()) {
            try {
                HttpEntity<EnvironmentState> entity = new HttpEntity<>(
                    new EnvironmentState(vehicle.getState(getHost())), headers);
                template.put(vehicle.getAgent().webhook, entity);
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Could not update: " + vehicle.getAgent().name);
            }
        }
    }

    @PostMapping(value="/junctions")
    public ResponseEntity<String> addJunction(@RequestBody JunctionConfiguration configuration) {
        JunctionSimulation simulation = junctionService.addJunction(configuration);
        
        try {
            return ResponseEntity.
                        status(HttpStatus.CREATED).
                        header(HttpHeaders.LOCATION, simulation.getUrl(getHost())).
                        build();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // provide food query api here
    @GetMapping(value="/junctions", produces="application/json")
    public ResponseEntity<LinkedList<String>> getStreets() {
        LinkedList<String> list = new LinkedList<>();
        for (JunctionSimulation simulation : junctionService.getSimulations()) {
            try {
                list.add(simulation.getUrl(getHost()));
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping(value="/junctions", produces="text/html")
    public ResponseEntity<String> getStreetsHtml() {
        StringBuilder builder = new StringBuilder();
        builder.append("<TABLE>");
        for (JunctionSimulation simulation : junctionService.getSimulations()) {
            builder
                .append("<TR><TD>")
                .append(simulation.getJunction().id)
                .append("</TD><TD>")
                .append(simulation.toHTML())
                .append("</TD></TR>");
        }
        builder.append("</TABLE>");

        return ResponseEntity.status(HttpStatus.OK).body(builder.toString());
    }

    @GetMapping(value="/junctions/{id}", produces="text/html")
    public ResponseEntity<String> getJunctionHtml(@PathVariable String id) {
        JunctionSimulation simulation = junctionService.getSimulation(id);
        return ResponseEntity.status(HttpStatus.OK).body(simulation.toHTML());
    }

    @GetMapping(value="/junctions/{id}", produces="application/json")
    public ResponseEntity<JunctionDescription> getJunction(@PathVariable String id) {
        JunctionSimulation simulation = junctionService.getSimulation(id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(simulation.getJunctionDescription(getHost()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value="/junctions/{id}/in", produces="application/json")
    public ResponseEntity<LinkedList<Link>> getInLinks(@PathVariable String id) {
        JunctionSimulation simulation = junctionService.getSimulation(id);
        return ResponseEntity.status(HttpStatus.OK).body(simulation.getInLinks());
    }

    @GetMapping(value="/junctions/{id}/in/{index}", produces="application/json")
    public ResponseEntity<Link> getInLink(@PathVariable String id, @PathVariable int index) {
        JunctionSimulation simulation = junctionService.getSimulation(id);
        if (simulation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Link link = simulation.getInLink(index);
        if (link == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(link);
    }

    @PutMapping(value="/junctions/{id}/in/{index}", produces="application/json")
    public ResponseEntity<Link> updateInLink(@PathVariable String id, @PathVariable int index, @RequestBody Link link) {
        if (link.url == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        JunctionSimulation simulation = junctionService.getSimulation(id);
        if (simulation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        VehicleQueue queue = simulation.getVehicleQueue(index);
        if (queue == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        queue.setUrl(link.url);
        return ResponseEntity.status(HttpStatus.OK).body(link);
    }

    @PostMapping(value="/junctions/{id}/in/{index}")
    public ResponseEntity<String> receiveAgent(@PathVariable String id, @PathVariable int index, @RequestBody AgentDescription agentDescription) {
        JunctionSimulation simulation = junctionService.getSimulation(id);
        if (simulation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        VehicleQueue queue = simulation.getVehicleQueue(index);
        if (queue == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Vehicle vehicle = queue.addVehicle(agentDescription);
        vehicleService.add(vehicle);

        try {
            return ResponseEntity.
                        status(HttpStatus.CREATED).
                        header(HttpHeaders.LOCATION, vehicle.getUrl(getHost())).
                        build();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping(value="/junctions/{id}/out", produces="application/json")
    public ResponseEntity<LinkedList<Link>> getOutLinks(@PathVariable String id) {
        JunctionSimulation simulation = junctionService.getSimulation(id);
        return ResponseEntity.status(HttpStatus.OK).body(simulation.getOutLinks());
    }

    @GetMapping(value="/junctions/{id}/out/{index}", produces="application/json")
    public ResponseEntity<Link> getOutLink(@PathVariable String id, @PathVariable int index) {
        JunctionSimulation simulation = junctionService.getSimulation(id);
        if (simulation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Link link = simulation.getOutLink(index);
        if (link == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(link);
    }

    @PutMapping(value="/junctions/{id}/out/{index}", produces="application/json")
    public ResponseEntity<Link> updateOutLink(@PathVariable String id, @PathVariable int index, @RequestBody Link link) {
        if (link.url == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        JunctionSimulation simulation = junctionService.getSimulation(id);
        if (simulation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        simulation.setOutLink(index, link.url);
        return ResponseEntity.status(HttpStatus.OK).body(link);
    }

    @GetMapping("/vehicle/{name}")
    public ResponseEntity<VehicleState> getVehicle(@PathVariable String name) {
        Vehicle vehicle = vehicleService.getVehicle(name);
        if (vehicle == null) {
            URI uri = redirects.get(name);
            if (uri == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity
                    .status(HttpStatus.TEMPORARY_REDIRECT)
                    .header(HttpHeaders.LOCATION, uri.toASCIIString())
                    .build();
        }
        try {
            return ResponseEntity.status(HttpStatus.OK).body(vehicle.getState(getHost()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/vehicle/{name}/action")
    public ResponseEntity<Action> setAction(@PathVariable String name, @RequestBody Action action) {
        System.out.println("[" + name + "] Action: " + action);
        Vehicle vehicle = vehicleService.getVehicle(name);
        if (vehicle == null) {
            URI uri = redirects.get(name);
            if (uri == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity
                    .status(HttpStatus.TEMPORARY_REDIRECT)
                    .header(HttpHeaders.LOCATION, uri.toASCIIString())
                    .build();
        }
        System.out.println("[" + vehicle.getAgent().name + "] Setting action: " + action);
        vehicle.setAction(action);
        return ResponseEntity.status(HttpStatus.OK).body(action);
    }

    private String getHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress() + ":" + port;
    }

}
