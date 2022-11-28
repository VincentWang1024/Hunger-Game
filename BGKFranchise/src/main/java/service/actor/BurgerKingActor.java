package service.actor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import akka.actor.Props;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import scala.concurrent.duration.Duration;
import service.burgerking.BQService;
import service.messages.FoodRequest;
import service.messages.FoodResponse;
import service.messages.FranchiseRequest;
import service.messages.FranchiseResponse;
import service.messages.RequestDeadline;
import service.messages.http.Registration;
import service.messages.http.Result;
import service.messages.http.Step;
import service.messages.http.Unregister;
import service.util.RestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import service.util.WebResponse;


public class BurgerKingActor extends AbstractActor {
    String franchiseName;
    ActorRef franchiseRef;

    private ArrayList<ActorRef> branchesRef = new ArrayList<ActorRef>();
    private ArrayList<FoodResponse> foodResponses = new ArrayList<FoodResponse>();
    private int SEED_ID = 0;

    public static Props props() { return  Props.create(BurgerKingActor.class);}

    private List<Registration> registrations = new LinkedList<>();
    private RestClient restClient = new RestClient();
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(BQService.class,
                        msg -> {
                            franchiseRef = getSender();
                            franchiseName = msg.getFranchiseName();
                            System.out.println(msg.getFranchiseName());

                        })
                .match(String.class,
                        msg -> {
                            if (msg.startsWith("register")) {
                                branchesRef.add(getSender());
                                System.out.println("Registered : " + getSender());
                            }
                        })
                .match(FranchiseRequest.class,
                        msg -> {
                            // 1. FranchiseRequest received
                            System.out.println("Franchise Request recieved!");
                            // 2. Send FoodRequest
                            for (ActorRef branch : branchesRef) {
                                branch.tell(new FoodRequest(SEED_ID), getSelf());
                            }
                            getContext().system().scheduler().scheduleOnce(
                                    Duration.create(2, TimeUnit.SECONDS),
                                    getSelf(),
                                    new RequestDeadline(SEED_ID++),
                                    getContext().dispatcher(), null);
                        })
                .match(FoodResponse.class,
                        msg -> {
                            System.out.println("Food response receieved");
                            System.out.println("Food Response Recieved from : !" + msg.getName()
                                    + " with food Quantity : " + msg.getFoodQuantity());
                            foodResponses.add(msg);
                            System.out.println(foodResponses);
                        })
                .match(RequestDeadline.class,
                        msg -> {
                            // here the get sender will be the reference of super broker
                            FranchiseResponse fr = new FranchiseResponse(franchiseName, foodResponses);
                            System.out.println("Sending Franchise Response" + fr);
                            System.out.println("Franchise name : " + fr.getFranchiseName());
                            System.out.println("Food Response list : " + fr.getFoodResponse());

                            getSender().tell(fr, franchiseRef);
                        })
                .match(Registration.class,
                        msg -> {
                            registrations.add(msg);
                            getSender().tell(new Result(), getSelf());
                        })
                .match(Unregister.class,
                        msg -> {
                            for(Registration registration : registrations){
                                if(registration.getName().equals(msg.getName())){
                                    registrations.remove(registration);
                                }
                            }
                            getSender().tell(new Result(), getSelf());
                        })
                .match(Step.class,
                        msg -> {
                            String json = mapper.writeValueAsString(msg);
                            System.out.println("step [broker]: " + msg.getStep());
                            for(Registration registration : registrations){
                                System.out.println("Sending to: " + registration.getUrl() + " ["+registration.getName()+ "]");
                                if(!registration.isFailed()){
                                    try {
                                        WebResponse response = restClient.put(registration.getUrl(), RestClient.JSON_TYPE, json);
                                        if (response == null) registration.setFailed(true);
                                    } catch (Throwable th) {
                                        th.printStackTrace();
                                        registration.setFailed(true);
                                    }
                                }
                            }
                })
                .build();
    }

}
