package service.actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import scala.concurrent.duration.Duration;
import service.core.BgkFood;
import service.messages.*;

public class BurgerKingActor extends AbstractActor {

        private ArrayList<ActorRef> branchRefs =new ArrayList<>();
        private ActorRef clientRef;
        private int SEED_ID=0;
        Map<Integer, FranchiseResponse> DB = new HashMap<>();
        @Override
        public AbstractActor.Receive createReceive() {

            return receiveBuilder()
                .match(String.class,
                        msg -> {
                            if (msg.equals("register")) {
                                System.out.println("broker: I got sender: "+getSender());
                                branchRefs.add(getSender());
                                System.out.println("actor size: "+ branchRefs.size());
                            }
                })
                .match(FranchiseRequest.class,
                        msg ->{
                                System.out.println("applicationReq received!........... ");
                                for (ActorRef ref : branchRefs) {
                                    ref.tell(new FoodRequest(SEED_ID, msg.getHungerInfo()), getSelf());
                                    FranchiseResponse ap = new FranchiseResponse(msg.getHungerInfo(), new ArrayList<>());
                                    DB.put(SEED_ID, ap);
                                }
                                getContext().system().scheduler().scheduleOnce(
                                Duration.create(2, TimeUnit.SECONDS),
                                getSelf(),
                                new RequestDeadline(SEED_ID++),
                                getContext().dispatcher(), null);
                })
                .match(FoodResponse.class,
                        msg ->{
                            DB.get(msg.getId()).getList().add(msg.getFood());
                            System.out.println("get food response from "+getSender().path().name() +": "+msg.getFood().getQuantity()+"kg");
                })
                .match(RequestDeadline.class,
                        msg ->{
                            FranchiseResponse appRes = DB.get(msg.getSEED_ID());
                            //TODO task: integrate with superBroker!
//
                }).build();
        }
}
