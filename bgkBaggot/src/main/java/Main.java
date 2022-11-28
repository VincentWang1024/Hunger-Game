import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.actor.Branch;
import service.baggot.BaggotService;
// import service.messages.FranchiseRequest;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(Branch.class), "bgkBaggot");
        BaggotService baggotService = new BaggotService("bgkBaggot");
        ref.tell(baggotService, ref);

        // This is to register the Branch with Franchise
        ActorSelection selection = system.actorSelection("akka.tcp://default@127.0.0.1:2551/user/bgkfranchise");
        selection.tell("register " + baggotService.getServiceName(), ref);

        System.out.println("bgkBaggot registered!");

    }
}
