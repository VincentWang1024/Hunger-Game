package service.actor;

import java.util.ArrayList;

import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import scala.concurrent.duration.Duration;
import service.mcd.MCDService;
import service.messages.FoodRequest;
import service.messages.FoodResponse;
import service.messages.FranchiseRequest;
import service.messages.FranchiseResponse;
import service.messages.RequestDeadline;

public class MCDActor extends AbstractActor {
    String franchiseName;
    ActorRef franchiseRef;

    private ArrayList<ActorRef> branchesRef = new ArrayList<ActorRef>();
    private ArrayList<FoodResponse> foodResponses = new ArrayList<FoodResponse>();
    private int SEED_ID = 0;
    int foodResponseId = 0;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MCDService.class,
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
                            // 3. Get FoodResponse
                            // FoodResponse foodResponse = new FoodResponse(msg.getId(), msg.getName(),
                            // msg.getFoodQuantity());
                            System.out.println("Food Response Recieved from : !" + msg.getName()
                                    + " with food Quantity : " + msg.getFoodQuantity());

                            foodResponses.add(msg);
                            System.out.println(foodResponses);
                            // 4. Create FranchiseResponse
                            // 5.Send FranchiseResponse

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
                .build();
    }

}
