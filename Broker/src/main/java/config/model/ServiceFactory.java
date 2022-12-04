package config.model;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class ServiceFactory {
    public static Map<String, Class<? extends Service>> services = new HashMap<>();
    
    public Service create(JsonNode description) {
        String url = description.get("url").asText();
        String type = description.get("type").asText();

        Class<? extends Service> cls = services.get(type);
        System.out.println("Type: " + type);
        System.out.println("Class: "  +cls);
        Service service = null;
        try {
            Constructor<? extends Service> cons = cls.getConstructor(String.class);
            service = cons.newInstance(url);
            service.init(description);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return service;
    }
}
