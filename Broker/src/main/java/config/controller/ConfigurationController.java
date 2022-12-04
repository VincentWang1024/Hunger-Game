package config.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import config.model.AgentService;
import config.model.JunctionService;
import config.model.JunctionStreetLinker;
import config.model.Linker;
import config.model.Registration;
import config.model.Service;
import config.model.ServiceFactory;
import config.model.StreetJunctionLinker;
import config.model.StreetService;

@RestController
public class ConfigurationController {
    private JsonNode configuration;
    private Map<String, Service> services = new HashMap<>();
    private ServiceFactory factory = new ServiceFactory();

    private ObjectMapper mapper = new ObjectMapper();
    {
        configuration = mapper.createObjectNode();
        ServiceFactory.services.put(StreetService.TYPE, StreetService.class);
        ServiceFactory.services.put(JunctionService.TYPE, JunctionService.class);
        ServiceFactory.services.put(AgentService.TYPE, AgentService.class);
        Linker.register(new StreetJunctionLinker());
        Linker.register(new JunctionStreetLinker());
    }

    @PutMapping("/configuration")
    public ResponseEntity<JsonNode> setConfiguration(@RequestBody JsonNode configuration) {
        this.configuration = configuration;

        JsonNode serviceDescriptions = configuration.get("services");
        JsonNode links = configuration.get("links");
        JsonNode clock = configuration.get("clock");
        if (serviceDescriptions == null || links == null) {
            return new ResponseEntity<JsonNode>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        for (JsonNode serviceDescription : serviceDescriptions) {
            Service service = factory.create(serviceDescription);
            if (service == null) 
                return new ResponseEntity<JsonNode>(HttpStatus.UNPROCESSABLE_ENTITY);
            else {
                services.put(service.getType(), service);
                try {
                    service.setup();
                } catch (HttpClientErrorException exception) {
                    System.out.println("Configuration Error: " + exception.getRawStatusCode());
                    return new ResponseEntity<JsonNode>(serviceDescription, HttpStatus.CONFLICT);
                }
            }
        }

        for (JsonNode link : links) {
            JsonNode out = link.get("out");
            JsonNode in = link.get("in");

            // contruct Link class to capture code to combine 
            String type = out.get("type").asText()+"-"+in.get("type").asText();
            if (Linker.get(type) == null) {
                System.out.println("WARNING: No linker of type: " + type);
                return new ResponseEntity<JsonNode>(configuration, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Linker.get(type).construct(services, out, in);
        }

        if (clock != null) {
            String clockUrl = clock.get("url").asText();
            System.out.println("Clock at: " + clockUrl);
            RestTemplate template = new RestTemplate();
            for (Entry<String,Service> entry : services.entrySet()) {
                System.out.println("\tStarting: "  + entry.getKey() + " [" + entry.getValue().getUrl() +"/iteration]");
                //BGK post request
                template.postForLocation(clockUrl, new Registration(entry.getKey(), entry.getValue().getUrl()+"/iteration") );

                //MCD post request

            }
        }

        return new ResponseEntity<JsonNode>(configuration, HttpStatus.OK);
    }

    @GetMapping("/configuration")
    public ResponseEntity<JsonNode> getConfiguration() {
        return new ResponseEntity<JsonNode>(configuration, HttpStatus.OK);
    }
}
