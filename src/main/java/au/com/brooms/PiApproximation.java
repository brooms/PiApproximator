package au.com.brooms;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import au.com.brooms.actors.Controller;
import au.com.brooms.actors.ResultListener;
import au.com.brooms.actors.messages.StartApproximation;
import au.com.brooms.approximation.Approximation;
import au.com.brooms.approximation.ApproximationFactory;
import au.com.brooms.error.InvalidParametersException;

import javax.inject.Inject;

/**
 * Initialise and configure the akka system and being the approximation.
 *
 * @author Brooms
 * @version 0.1.0
 */
public class PiApproximation {

  /**
   * A factory containing all approximation methods, this will allow the program to dynamically
   * choose an approximation method at runtime.
   */
  @Inject
  public ApproximationFactory factory;

  @Inject
  public PiApproximation() {
  }

  /**
   * Calculate the approximation to Pi based on the configuration parameters.
   *
   * @param numberOfSteps   The number of steps to estimate pi to
   * @param numberOfWorkers The number of actors in the akka system
   * @param name            The name of the approximation method
   * @throws InvalidParametersException When invalid input parameters are passed to the approximation
   */
  public void calculate(final long numberOfSteps, final int numberOfWorkers, final String name)
      throws InvalidParametersException {

    if (numberOfSteps <= 0) {
      throw new InvalidParametersException("Invalid number of steps to perform "
          + numberOfSteps + ", expected > 0.");
    } else if (numberOfWorkers <= 0) {
      throw new InvalidParametersException("Invalid number of workers "
          + numberOfWorkers + ", expected > 0.");
    }

    // Create and initialise the akka system
    ActorSystem system = ActorSystem.create("PiApproximation");

    // Listener that will wait for a final result from the controller before shutting the actor system down
    final ActorRef resultListener = system.actorOf(Props.create(ResultListener.class),
        "resultListener");

    // Get the approximation factory to help dynamically determine which approximation method to use
    Approximation approximation = factory.getApproximation(name);

    // Create the controller
    ActorRef controller = system.actorOf(Props.create(Controller.class, approximation, numberOfSteps,
        numberOfWorkers, resultListener), "controller");

    // Start the calculation
    controller.tell(new StartApproximation(), ActorRef.noSender());
  }
}
