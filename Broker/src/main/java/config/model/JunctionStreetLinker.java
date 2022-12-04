package config.model;

import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import massim.core.model.Link;

public class JunctionStreetLinker extends Linker {
    @Override
    public String getType() {
        return "junction-street";
    }

    @Override
    public boolean construct(Map<String, Service> services, JsonNode out, JsonNode in) {
        StreetService streetService = (StreetService) services.get(in.get("type").asText());
        JunctionService junctionService = (JunctionService) services.get(out.get("type").asText());
        String inId = in.get("id").asText();
        String outId = out.get("id").asText();
        String streetUrl = streetService.getInstanceUri(inId).toASCIIString()+"/in";
        String junctionUrl = junctionService.getInstanceUri(outId).toASCIIString()+"/out";

        String inStreetUrl = getFreeLink(streetUrl);
        String outJunctionUrl = getFreeLink(junctionUrl);

        template.put(inStreetUrl, new Link(outJunctionUrl));
        template.put(outJunctionUrl, new Link(inStreetUrl));

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
