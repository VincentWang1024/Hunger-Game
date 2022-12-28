package service.bgkfranchise;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import service.core.ClientAppliation;
import service.core.Quotation;

import java.net.URISyntaxException;
import java.util.*;

import static com.mongodb.client.model.Filters.lt;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


@RestController
public class Broker {
    static long applicationNumber = 0;
    String[] URIs = new String[]{
            "http://mcdFranchise:8086/applications/",
            "http://bgkFranchise:8087/applications/",
    };

    String config = "mongodb+srv://admin:admin@cluster0.ado3i.mongodb.net/Hunger_Game?retryWrites=true&w=majority";


    private List<ClientAppliation> applications;

    @RequestMapping(value="/applications",method= RequestMethod.POST)
    public ResponseEntity<List<Document>> createApplications() throws URISyntaxException {
        requestHandler();
        List<Document> res = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(config)) {
            MongoDatabase database = mongoClient.getDatabase("Hunger_Game");
            MongoCollection<Document> collection = database.getCollection("vendor");

            MongoCursor<Document> cursor = collection.find().iterator();
            try {
                while(cursor.hasNext()) {
                    res.add(cursor.next());
                }
            } finally {
                cursor.close();
            }
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(res, headers, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value="/applications",method=RequestMethod.GET)
    public ResponseEntity<List<Document>> getApplication() {
        List<Document> res = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(config)) {
            MongoDatabase database = mongoClient.getDatabase("Hunger_Game");
            MongoCollection<Document> collection = database.getCollection("vendor");

            MongoCursor<Document> cursor = collection.find().iterator();
            try {
                while(cursor.hasNext()) {
                    res.add(cursor.next());
                }
            } finally {
                cursor.close();
            }
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(res, headers, HttpStatus.CREATED);
        }
    }

    public void requestHandler() {
        RestTemplate restTemplate = new RestTemplate();
//        ArrayList<ClientAppliation> list = new ArrayList<>();
        HttpEntity request = new HttpEntity(null);

        applications = new ArrayList<>();
        applicationNumber++;

        for (String uri: URIs) {
            ClientAppliation clientAppliation = restTemplate.postForObject(uri, request, ClientAppliation.class);
//            list.add(clientAppliation);
            List<Quotation> list = clientAppliation.getList();
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(list, new TypeToken<List<Quotation>>() {}.getType());
            JsonArray jsonArray = element.getAsJsonArray();

            applications.add(clientAppliation);

            //mongoDB persistence
            try (MongoClient mongoClient = MongoClients.create(config)) {
                        MongoDatabase database = mongoClient.getDatabase("Hunger_Game");
                        MongoCollection<Document> collection = database.getCollection("vendor");

                        try {
                            InsertOneResult result = collection.insertOne(
                                    new Document()
                                    .append("franchiseName", clientAppliation.getFranchiseName())
                                    .append("appId", applicationNumber)
                                    .append("foodInfo", jsonArray.toString())
                            );
                            System.out.println("Success! Inserted document id: " + result.getInsertedId());
                        } catch (MongoException me) {
                            System.err.println("Unable to insert due to an error: " + me);
                        }
            }
        }
    }


    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class NoSuchApplicationException extends RuntimeException {
//        static final long serialVersionUID = -6516152229878843037L;
        static final long serialVersionUID = -7516152229878843037L;
    }
}

