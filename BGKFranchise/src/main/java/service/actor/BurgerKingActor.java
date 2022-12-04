package service.actor;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import akka.actor.Props;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.FI;
import scala.concurrent.duration.Duration;
import service.burgerking.BQService;
import service.messages.FoodRequest;
import service.messages.FoodResponse;
import service.messages.FranchiseRequest;
import service.messages.FranchiseResponse;
import service.messages.RequestDeadline;
import service.FoodMessages.GetFoodMessage;


public class BurgerKingActor extends AbstractActor {

    String franchiseName;
    ActorRef franchiseRef;
    FranchiseResponse franchiseResponse;


    private ArrayList<ActorRef> branchesRef = new ArrayList<>();
    private ArrayList<FoodResponse> foodResponses = new ArrayList<>();
    private int SEED_ID = 0;

    public static Props props() { return  Props.create(BurgerKingActor.class);}

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
                            franchiseResponse = new FranchiseResponse(franchiseName, foodResponses);
                            System.out.println("Sending Franchise Response" + franchiseResponse);
                            System.out.println("Franchise name : " + franchiseResponse.getFranchiseName());
                            System.out.println("Food Response list : " + franchiseResponse.getFoodResponse());
//                            getSender().tell(fr, franchiseRef);
                        })
                .match(GetFoodMessage.class, handleGetFood())
                .build();
    }

    private FI.UnitApply<GetFoodMessage> handleGetFood() {
                return getUserMessageMessage -> {
                  sender().tell(franchiseResponse, getSelf());
                };
              }
}
