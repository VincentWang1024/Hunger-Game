import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.actor.BurgerKingActor;
import service.burgerking.BQService;
import service.messages.FranchiseRequest;
import java.time.Duration;
import java.util.concurrent.CompletionStage;
import akka.stream.javadsl.Flow;

import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import service.messages.http.Registration;
import service.messages.http.Result;
import service.messages.http.Unregister;


public class Main {
    public static void main(String[] args) throws InterruptedException {
//        ActorSystem system = ActorSystem.create();
//        ActorRef ref = system.actorOf(Props.create(BurgerKingActor.class), "bgkfranchise");
//        ref.tell(new BQService("Burger King"), ref);

        ActorSystem system = ActorSystem.create();
        Http http = Http.get(system);

        ClockServer app = new ClockServer(system);
        Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow(system);
        http.newServerAt("0.0.0.0", 9000).bindFlow(routeFlow);

        System.out.println("Server online at http://0.0.0.0:9000/");
    }
}

class ClockServer extends AllDirectives {

    private Duration TIMEOUT = Duration.ofSeconds(5L);


//    private ActorRef clock;
//    private ActorRef broker;
    private ActorRef bgkFranchise;

    public ClockServer(ActorSystem system) throws InterruptedException {
//        clock = system.actorOf(Clock.props());
        bgkFranchise = system.actorOf(BurgerKingActor.props());
//        bgkFranchise = system.actorOf(Props.create(BurgerKingActor.class), "bgkfranchise");
        bgkFranchise.tell(new BQService("Burger King"), bgkFranchise);

        // test for franchise should be here
        Thread.sleep(10000);
        bgkFranchise.tell(new FranchiseRequest(), null);
    }

    Route createRoute() {
        return route(
                pathPrefix("registry", () -> getRegistryRoutes())
        );
    }

    private Route getRegistryRoutes() {
        return route(
                post(() -> //Register endpoint to clock
                    entity(Jackson.unmarshaller(Registration.class), registration -> {
                        CompletionStage<Result> register = Patterns.ask(bgkFranchise, registration, TIMEOUT)
                                .thenApply(Result.class::cast);

                        return onSuccess(() -> register, msg -> complete(StatusCodes.OK, msg, Jackson.marshaller()));
                    })
                ),
                delete(() -> { //Unregister current endpoint registered to clock
                            CompletionStage<Result> unregister = Patterns.ask(bgkFranchise, new Unregister(), TIMEOUT)
                                    .thenApply(Result.class::cast);
                            return onSuccess(() -> unregister, msg -> complete(StatusCodes.OK, msg, Jackson.marshaller()));
                        }
                )
        );
    }
}

