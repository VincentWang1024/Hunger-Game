package config.model;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

public class JunctionService extends Service {
    public static final String TYPE = "junction";
    
    private Map<String, Junction> junctions = new HashMap<>();

    public JunctionService(String url) {
        super(url, TYPE);
    }

    @Override
    public void init(JsonNode description) {
        JsonNode instances = description.get("instances");
        for (JsonNode instance : instances) {
            junctions.put(
                instance.get("id").asText(),
                new Junction(instance.get("id").asText(), instance.get("inLinks").asInt(), instance.get("inLinks").asInt())
            );
        }
    }

    @Override
    public void setup() {
        RestTemplate template = new RestTemplate();
        for (Junction junction : junctions.values()) {
            URI location = template.postForLocation(getUrl(), junction);
            System.out.println("Created: " + location);
            instanceUris.put(junction.getId(), location);
        }
    }

    public Junction getJunction(String id) {
        return junctions.get(id);
    }
}
