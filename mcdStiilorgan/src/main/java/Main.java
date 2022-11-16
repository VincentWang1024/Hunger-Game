import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.actor.Branch;
import service.core.HungerInfo;
import service.messages.FoodRequest;
import service.stillorgan.StillorganService;
import service.message.Init;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(Branch.class), "mcdStillorgan");
        ref.tell(new Init(new StillorganService()), null);
        ActorSelection selection =
            system.actorSelection("akka.tcp://default@127.0.0.1:2550/user/mcdfranchise");
        selection.tell("register", ref);

        System.out.println("mcdStillorgan registered!");

        //TODO test data
//        ref.tell(new FoodRequest(1, new HungerInfo(5)),null);
    }
}
