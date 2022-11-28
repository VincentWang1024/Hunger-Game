import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.actor.Branch;
import service.stillorgan.StillorganService;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(Branch.class), "mcdStillorgan");
        StillorganService stillorganService = new StillorganService("mcdStillorgan");
        ref.tell(stillorganService, ref);

        // This is to register the branch to Franchise

        ActorSelection selection = system.actorSelection("akka.tcp://default@127.0.0.1:2550/user/mcdfranchise");
        selection.tell("register " + stillorganService.getServiceName(), ref);

        System.out.println("mcdStillorgan registered!");

    }
}
