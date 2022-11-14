package service.actor;

import akka.actor.AbstractActor;
import service.core.Food;
import service.core.FoodService;
import service.core.McdFood;
import service.message.Init;
import service.messages.FoodRequest;
import service.messages.FoodResponse;

public class Branch extends AbstractActor {
    private FoodService service;
    @Override
    public AbstractActor.Receive createReceive() {

        return receiveBuilder()
                .match(Init.class,
                        msg -> service = msg.service)
                .match(FoodRequest.class,
                        msg -> {
                             Food food = service.makeFood(msg.getHungerInfo());
                            System.out.println("request quantity: "+msg.getHungerInfo().getReqQuantity());
                            System.out.println("response quantity: "+ food.getQuantity());
                            getSender().tell(
                                    new FoodResponse(msg.getId(), food), getSelf());
                }).build();
    }
}