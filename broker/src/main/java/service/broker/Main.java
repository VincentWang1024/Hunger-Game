package service.broker;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

// import service.core.ClientApplication; //ngorequest
// import service.core.ClientInfo; //ngoinfo
// import service.core.Quotation; //FoodResponse

import service.messages.NgoRequest;
import service.core.Franchise;
import service.core.NgoInfo;
import service.messages.FoodRequest;
import service.messages.FoodResponse;
import service.messages.NgoResponse;

@RestController
public class Main {

        static List<String> urls = new ArrayList<String>() {
                {
                        add("http://localhost:2551/foodrequest"); //bgk
                        add("http://localhost:2550/foodrequest"); //mcd
                }
        };
        // private Map<Integer, NgoResponse> cache = new HashMap<>();

        @PostMapping(value = "/ngofoodrequest")
        public ResponseEntity<NgoRequest> getFood(@RequestBody NgoInfo info) {
                ArrayList<Franchise> foodResponseFranchise = new ArrayList<>();

                HttpHeaders headers = new HttpHeaders();
                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<NgoInfo> request = new HttpEntity<>(info);
                for (String url : urls) {
                        foodResponseFranchise.add(restTemplate.postForObject(url, request,
                                        Franchise.class));
                }
                NgoRequest ngoRequest = new NgoRequest(foodResponseFranchise);
                return new ResponseEntity<>(ngoRequest, headers, HttpStatus.CREATED);
        }
            // return new ResponseEntity<>(ngoResponse, headers, HttpStatus.CREATED);
                // // return new ResponseEntity<>(clientAppliation, headers, HttpStatus.CREATED);
                // public ResponseEntity<ClientApplication> getQuotations(@RequestBody ClientInfo info) {
                // LinkedList<FoodResponse> foodResponse = new LinkedList<>();
                // // LinkedList<Quotation> quotations = new LinkedList<>();
                

                // HttpEntity<NgoInfo> request = new HttpEntity<>(info);
                // HttpEntity<ClientInfo> request = new HttpEntity<>(info);

                // for (String url : urls) {
                //         foodResponse.add(restTemplate.postForObject(url, request, FoodResponse.class));
                //         // quotations.add(restTemplate.postForObject(url, request,
                //         //                 Quotation.class));
                // }

                // for (int i = 0; i < foodResponse.size(); i++) {
                //         totalFood += foodResponse.get(i).getFood().getQuantity();
                //         System.out.print("foodResponse: " + foodResponse.get(i) + " ");
                // }
                // // for (FoodResponse fr : foodResponse) {
                // //         System.out.println(fr);
                // // }
                // System.out.print("totalFood" + totalFood);

                // // comment by A : convert ngoRequest to ngoResponse ---
                // //also change return type to ngoResponse
                // NgoResponse ngoResponse = new NgoResponse(appnum, info, totalFood);
                // cache.put(appnum, ngoResponse);

                // // ClientApplication clientAppliation = new ClientApplication(appnum, info, quotations);
                // // cache.put(appnum, clientAppliation);
                // appnum++;
                // return new ResponseEntity<>(ngoResponse, headers, HttpStatus.CREATED);
                // // return new ResponseEntity<>(clientAppliation, headers, HttpStatus.CREATED);

                
                 
                 


        // @RequestMapping(value = "/applications/{application_number}", method = RequestMethod.GET)
        // public ClientApplication getApplications(
        //                 @PathVariable("application_number") int num)
        //                 throws URISyntaxException {
        //         return cache.get(num);

        // }

        // @RequestMapping(value = "/applications", method = RequestMethod.GET)
        // public Map<Integer, ClientApplication> applications()
        //                 throws URISyntaxException {
        //         return cache;
        // }
// @GetMapping(value = "/test")
// public String test() {
//         return "jaaaa";
// }
}