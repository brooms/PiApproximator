package au.com.brooms.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;

import au.com.brooms.actors.messages.ApproximationResult;
import au.com.brooms.actors.messages.StartApproximation;
import au.com.brooms.actors.messages.Work;
import au.com.brooms.actors.messages.WorkerResult;
import au.com.brooms.approximation.Approximation;

import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Controller used to configure the approximation, create workers and route traffic to the workers.
 *
 * <p>As results are produced by the workers, the controller will listen for these results to calculate
 * the final approximation. It will also handle any post-processing.</p>
 *
 * @author Brooms
 * @version 0.1.0
 */
public class Controller extends UntypedActor {

  LoggingAdapter log = Logging.getLogger(getContext().system(), this);


  private final long numberOfSteps;
  private final long numberOfWorkers;
  private final long chunkSize;
  private final long chunkRemainder;

  private double pi;
  private long results;

  // Start time
  private final long start = System.currentTimeMillis();

  private final ActorRef listener;
  private final Router router;

  private final Approximation approximation;

  /**
   * Default constructor.
   *
   * @param approximation The approximation method
   * @param numberOfSteps The number of steps to estimate pi to
   * @param numberOfWorkers The number of workers to use in the estimation
   * @param listener The result listener to notify when the estimation is complete
   */
  public Controller(final Approximation approximation, final long numberOfSteps,
                    final int numberOfWorkers, final ActorRef listener) {

    this.numberOfSteps = numberOfSteps;
    this.numberOfWorkers = numberOfWorkers;
    this.listener = listener;
    this.approximation = approximation;

    // Number of bundles of work
    this.chunkSize = numberOfSteps / numberOfWorkers;
    // The remainder if the number of step is not cleanly divisible by the number of workers
    this.chunkRemainder = numberOfSteps % numberOfWorkers;

    log.info("Initialising system with " + numberOfWorkers + " workers.");

    // Create the workers
    List<Routee> routees = new ArrayList<Routee>();
    for (int i = 0; i < numberOfWorkers; i++) {
      ActorRef workerActorRef = getContext().actorOf(Props.create(Worker.class, approximation,
          "Worker " + i));

      getContext().watch(workerActorRef);
      routees.add(new ActorRefRoutee(workerActorRef));
    }

    log.info("Using basic Round Robin routing.");
    router = new Router(new RoundRobinRoutingLogic(), routees);

  }

  /**
   * Receive and process a message (i.e. this is the message handler). This handler will start the
   * approximation calculation and route work to workers.
   *
   * @param message The message that invoked this message handler
   */
  public void onReceive(Object message) {


    if (message instanceof StartApproximation) {

      log.info("Starting approximation for " + numberOfSteps + " iterations.");
      log.info("Work divided across " + numberOfWorkers + " workers.");

      // Route work to the workers
      for (long group = 0; group < numberOfWorkers; group++) {
        long start = group * chunkSize;
        long end = start + chunkSize;

        if (group == numberOfWorkers - 1) {
          log.debug("Work remainder is " + chunkRemainder);
          end += chunkRemainder;
        }

        log.debug("Chunk for Worker " + group + " is [" + start + ", " + end + "]");

        router.route(new Work(start, end), getSelf());
      }

    } else if (message instanceof WorkerResult) {

      // Extract the result
      WorkerResult result = (WorkerResult) message;

      // Increment the value of Pi
      pi += result.getValue();

      // The number of results the controller has received
      results += 1;

      // Wait until all results have been received (since there is no chunking, it will
      // be the number of steps/iterations)
      if (results == numberOfWorkers) {

        // Apply any final calculations
        pi = approximation.apply(pi);

        // Send the result to the listener after calculating duration
        Duration duration = Duration.create(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
        listener.tell(new ApproximationResult(pi, duration), getSelf());

        // Stops this actor and all children
        getContext().stop(getSelf());
      }
    } else {
      unhandled(message);
    }
  }
}
