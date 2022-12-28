package service.broker;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.core.ClientAppliation;
import service.core.Quotation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
public class Broker {
    Integer applicationNumber = 0;
    String[] URIs = new String[]{
            "http://mcdJervis:8081/quotations/",
            "http://mcdStillorgan:8082/quotations/",
            "http://mcdTempleBar:8083/quotations/",
    };

    private Map<Integer, ClientAppliation> applications = new HashMap<>();

    @RequestMapping(value="/applications",method= RequestMethod.POST)
    public ResponseEntity<ClientAppliation> createApplications() throws URISyntaxException {
            ArrayList<Quotation> list =  getQuotations();
            ClientAppliation application = new ClientAppliation("MacDonald", applicationNumber++, list);
            applications.put(application.getApplicationNumber(), application);
            String path = ServletUriComponentsBuilder.fromCurrentContextPath().
                    build().toUriString()+ "/applications/"+application.getApplicationNumber();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(path));
            return new ResponseEntity<>(application, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value="/quotations",method= RequestMethod.POST)
    public ArrayList<Quotation> getQuotations() {
        RestTemplate restTemplate = new RestTemplate();
        ArrayList<Quotation> list = new ArrayList<>();
            HttpEntity request = new HttpEntity(null);
            for (String uri: URIs) {
                Quotation quotation = restTemplate.postForObject(uri, request, Quotation.class);
                list.add(quotation);
            }
            return list;
    }

    @RequestMapping(value="/applications/{applicationNumber}",method=RequestMethod.GET)
    public ClientAppliation getApplication(@PathVariable("applicationNumber") Integer applicationNumber) {
        ClientAppliation application = applications.get(applicationNumber);
        if (application == null) throw new NoSuchApplicationException();
        return application;
    }

    @RequestMapping(value="/applications",method=RequestMethod.GET)
    public Map<Integer,ClientAppliation> getApplication() {
        if (applications == null) throw new NoSuchApplicationException();
        return applications;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class NoSuchApplicationException extends RuntimeException {
//        static final long serialVersionUID = -6516152229878843037L;
        static final long serialVersionUID = -7516152229878843037L;
    }
}

