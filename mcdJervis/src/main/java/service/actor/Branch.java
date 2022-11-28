package service.actor;

import akka.actor.AbstractActor;
import service.messages.FoodRequest;
import service.messages.FoodResponse;
import service.jervis.JervisService;;

public class Branch extends AbstractActor {
        String branchName;
        private int foodQuantity;

        @Override
        public AbstractActor.Receive createReceive() {

                return receiveBuilder()
                                .match(JervisService.class,
                                                msg -> {

                                                        System.out.println("Branch.FoodService");
                                                        branchName = msg.getServiceName();
                                                        foodQuantity = msg.getFoodQuantity();
                                                        System.out.println("Service name " + msg.getServiceName());
                                                        System.out.println("Food Quantity :" + msg.getFoodQuantity());
                                                })
                                .match(FoodRequest.class,
                                                msg -> {
                                                        System.out.println(
                                                                        "Food request received inside :" + branchName);
                                                        getSender().tell(new FoodResponse(msg.getId(), branchName,
                                                                        foodQuantity), getSender());
                                                })
                                .build();
        }
}