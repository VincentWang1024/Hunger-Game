import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.pattern.PatternsCS;
import akka.stream.javadsl.Flow;
import akka.util.Timeout;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import service.actor.BurgerKingActor;
import service.messages.FranchiseRequest;
import service.messages.FranchiseResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import static akka.http.javadsl.server.PathMatchers.longSegment;


public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ActorSystem system = ActorSystem.create();

//        ActorRef franchiseActor = system.actorOf(BurgerKingActor.props(), "franchiseActor");
//        FoodServer server = new FoodServer(system);
        Http http = Http.get(system);

        FoodServer app = new FoodServer(system);
        Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow(system);
        http.newServerAt("0.0.0.0", 9000).bindFlow(routeFlow);

        System.out.println("Server online at http://0.0.0.0:9000/");
    }
}


class FoodServer extends AllDirectives {

  private Duration TIMEOUT = Duration.ofSeconds(5L);
  private ActorRef franchiseActor;

  FoodServer(ActorSystem system) {
    this.franchiseActor = system.actorOf(BurgerKingActor.props());
  }

//  @Override
//  public Route routes() {
//    return pathPrefix(segment("food").slash(longSegment()), (id) -> route(getFood()));
//  }

// (fake) async database query api
//    private CompletionStage<Optional<FranchiseResponse>> fetchItem(long itemId) {
//    return CompletableFuture.completedFuture(Optional.of(new FranchiseResponse("foo", itemId)));
//    }
Route createRoute() {
        return route(
                pathPrefix("food", () -> getRegistryRoutes())
        );
    }

//    Route getRegistryRoutes() {
//
//        return concat(
//          get(() ->
//            pathPrefix("food", () ->
//              path(longSegment(), (Long id) -> {
//                final CompletionStage<Optional<FranchiseResponse>> futureMaybeItem = PatternsCS.ask(franchiseActor, new FranchiseRequest(), timeout)
//                              .thenApply(obj -> (Optional<FranchiseResponse>) obj);;
//                return onSuccess(futureMaybeItem, maybeItem ->
//                  maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
//                    .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found"))
//                );
//              }))));
//      }

//  private Route getFood() {
//    return get(() -> {
//      CompletionStage<Optional<FranchiseResponse>> food = PatternsCS.ask(franchiseActor, new FranchiseRequest(), timeout)
//              .thenApply(obj -> (Optional<FranchiseResponse>) obj);
//
//      return onSuccess(() -> food, performed -> {
//        if (performed.isPresent())
//          return complete(StatusCodes.OK, performed.get(), Jackson.marshaller());
//        else
//          return complete(StatusCodes.NOT_FOUND);
//      });
//    });
//  }
private Route getRegistryRoutes() {
        return route(
                // post(() -> //Register endpoint to clock
                //     entity(Jackson.unmarshaller(Registration.class), registration -> {
                //         CompletionStage<Result> register = Patterns.ask(broker, registration, TIMEOUT)
                //                 .thenApply(Result.class::cast);

                //         return onSuccess(() -> register, msg -> complete(StatusCodes.OK, msg, Jackson.marshaller()));
                //     })
                // ),
                // delete(() -> { //Unregister current endpoint registered to clock
                //             CompletionStage<Result> unregister = Patterns.ask(broker, new Unregister(), TIMEOUT)
                //                     .thenApply(Result.class::cast);
                //             return onSuccess(() -> unregister, msg -> complete(StatusCodes.OK, msg, Jackson.marshaller()));
                //         }
                // )
                get(() -> {
                            CompletionStage<FranchiseResponse> fr = Patterns.ask(franchiseActor, new FranchiseRequest(), TIMEOUT)
                                    .thenApply(FranchiseResponse.class::cast);
                            return onSuccess(() -> fr, msg -> complete(StatusCodes.OK, msg, Jackson.marshaller()));
                        }
                )
        );
    }
}


