package config.model;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

public class AgentService extends Service {
    public static final String TYPE = "agent";
    
    private Map<String, AgentModel> agents = new HashMap<>();

    public AgentService(String url) {
        super(url, TYPE);
    }

    @Override
    public void init(JsonNode description) {
        JsonNode instances = description.get("instances");
        for (JsonNode instance : instances) {
            String name = instance.get("name").asText();
            agents.put(name,
                new AgentModel(name, instance)
            );
        }
    }

    @Override
    public void setup() {
        RestTemplate template = new RestTemplate();
        for (AgentModel agent : agents.values()) {
            URI location = template.postForLocation(getUrl(), agent.init);
            System.out.println("Created: " + location);
            instanceUris.put(agent.name, location);
        }
    }

    public AgentModel getAgent(String name) {
        return agents.get(name);
    }
}
