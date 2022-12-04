package config.model;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class Service {
    private String url;
    private String type;
    private URI uri;
    protected Map<String, URI> instanceUris = new HashMap<>();

    public Service(String url, String type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    protected void setUri(URI uri) {
        this.uri = uri;
    }

    public URI getUri() {
        return uri;
    }

    public URI getInstanceUri(String id) {
        return instanceUris.get(id);
    }

    abstract public void init(JsonNode description);
    abstract public void setup();
}
