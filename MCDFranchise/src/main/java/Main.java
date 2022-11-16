import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.core.HungerInfo;
import service.messages.FranchiseRequest;
import service.actor.MacdonaldActor;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(MacdonaldActor.class), "mcdfranchise");

        //test
        Thread.sleep(10000);
        ref.tell(new FranchiseRequest(new HungerInfo(20)),null);
    }
}
