import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.actor.Branch;
import service.templebar.TempleBarService;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(Branch.class), "mcdTempleBar");
        TempleBarService templeBarService = new TempleBarService("mcdTempleBar");
        ref.tell(templeBarService, null);

        // This is to register the branch to Franchise
        ActorSelection selection = system.actorSelection("akka.tcp://default@127.0.0.1:2550/user/mcdfranchise");
        selection.tell("register " + templeBarService.getServiceName(), ref);

        System.out.println("mcdTempleBar registered!");

    }
}
