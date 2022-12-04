package config.model;

import com.fasterxml.jackson.databind.JsonNode;

public class AgentModel {
    public String name;
    public JsonNode init;

    public AgentModel(String name, JsonNode init) {
        this.name = name;
        this.init = init;
    }

    public AgentModel() {}
}
