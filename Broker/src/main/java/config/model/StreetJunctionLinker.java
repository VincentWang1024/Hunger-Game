package config.model;

import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import massim.core.model.Link;

public class StreetJunctionLinker extends Linker {
    @Override
    public String getType() {
        return "street-junction";
    }

    @Override
    public boolean construct(Map<String, Service> services, JsonNode out, JsonNode in) {
        StreetService streetService = (StreetService) services.get(out.get("type").asText());
        JunctionService junctionService = (JunctionService) services.get(in.get("type").asText());
        String inId = in.get("id").asText();
        String outId = out.get("id").asText();
        String streetUrl = streetService.getInstanceUri(outId).toASCIIString()+"/out";
        String junctionUrl = junctionService.getInstanceUri(inId).toASCIIString()+"/in";

        String outStreetUrl = getFreeLink(streetUrl);
        String inJunctionUrl = getFreeLink(junctionUrl);

        template.put(outStreetUrl, new Link(inJunctionUrl));
        template.put(inJunctionUrl, new Link(outStreetUrl));

        return true;
    }

    private RestTemplate template = new RestTemplate();

    private String getFreeLink(String url) {
        Link[] links = template.getForEntity(url, Link[].class).getBody();
        if (links == null) return null;
    
        int index = 0;
        Link link = null;
        boolean finished = false;
        while (!finished && index < links.length) {
            link = links[index];
            if (link.url == null || link.url.equals("")) {
                finished = true;
            } else {
                index++;
            }
        }
        if (!finished) {
            return null;
        }
        return url+"/"+index;
    }

}
