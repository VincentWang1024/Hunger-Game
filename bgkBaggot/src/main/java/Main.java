import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.actor.Branch;
import service.core.HungerInfo;
import service.messages.FoodRequest;
import service.baggot.BaggotService;
import service.message.Init;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(Branch.class), "bgkBaggot");
        ref.tell(new Init(new BaggotService()), null);
        ActorSelection selection =
            system.actorSelection("akka.tcp://default@127.0.0.1:2551/user/vendor");
        selection.tell("register", ref);

        System.out.println("bgkBaggot registered!");

        //TODO test data
        ref.tell(new FoodRequest(1, new HungerInfo(5)),null);
    }
}
