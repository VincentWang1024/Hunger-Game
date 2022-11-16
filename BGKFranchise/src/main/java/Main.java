import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.core.HungerInfo;
import service.messages.FranchiseRequest;
import service.messages.Init;
import service.actor.BurgerKingActor;
import service.burgerking.BQService;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(BurgerKingActor.class), "bgkfranchise");

        //test
        Thread.sleep(10000);
        ref.tell(new FranchiseRequest(new HungerInfo(20)),null);
    }
}
