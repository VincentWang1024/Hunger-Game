package config.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class Linker {
    private static Map<String, Linker> linkers = new HashMap<>();

    public static void register(Linker linker) {
        linkers.put(linker.getType(), linker);
    }

    public static Linker get(String type) {
        return linkers.get(type);
    }

    abstract public boolean construct(Map<String, Service> services, JsonNode out, JsonNode in);
    abstract public String getType();
}
