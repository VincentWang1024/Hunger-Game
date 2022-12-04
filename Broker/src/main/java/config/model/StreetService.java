package config.model;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

public class StreetService extends Service {
    public static final String TYPE = "street";
    
    private Map<String, Street> streets = new HashMap<>();

    public StreetService(String url) {
        super(url, TYPE);
    }

    @Override
    public void init(JsonNode description) {
        JsonNode instances = description.get("instances");
        for (JsonNode instance : instances) {
            streets.put(
                instance.get("id").asText(), 
                new Street(instance.get("id").asText(), instance.get("length").asInt())
            );
        }
    }

    @Override
    public void setup() {
        RestTemplate template = new RestTemplate();
        for (Street street : streets.values()) {
            URI location = template.postForLocation(getUrl(), street);
            System.out.println("Created: " + location);
            instanceUris.put(street.getId(), location);
        }
    }

    public Street getStreet(String id) {
        return streets.get(id);
    }
}
