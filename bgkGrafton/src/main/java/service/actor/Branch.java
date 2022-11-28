package service.actor;

import akka.actor.AbstractActor;
import service.messages.FoodRequest;
import service.messages.FoodResponse;
import service.grafton.GraftonService;

public class Branch extends AbstractActor {
        private String branchName;
        private int foodQuantity;

        @Override
        public AbstractActor.Receive createReceive() {

                return receiveBuilder()
                                .match(GraftonService.class,
                                                msg -> {

                                                        System.out.println("Branch.FoodService");
                                                        System.out.println("Service name " + msg.getServiceName());
                                                        System.out.println("Food Quantity :" + msg.getFoodQuantity());

                                                        branchName = msg.getServiceName();
                                                        foodQuantity = msg.getFoodQuantity();

                                                })
                                .match(FoodRequest.class,
                                                msg -> {
                                                        System.out.println("Sending Food Response : ");
                                                        getSender().tell(new FoodResponse(msg.getId(), branchName,
                                                                        foodQuantity), getSender());

                                                })
                                .build();
        }
}