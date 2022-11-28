import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.messages.FranchiseRequest;
import service.messages.Init;

import service.actor.MCDActor;
import service.mcd.MCDService;;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(MCDActor.class), "mcdfranchise");
        ref.tell(new Init(new MCDService("MCD")), ref);

        Thread.sleep(20000);
        ref.tell(new FranchiseRequest(), ref);
    }
}
