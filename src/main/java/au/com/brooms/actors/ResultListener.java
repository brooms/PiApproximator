package au.com.brooms.actors;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import au.com.brooms.actors.messages.ApproximationResult;

/**
 * Result listener to listen for the final approximation result and shutdown the actor system
 * cleanly.
 *
 * @author Brooms
 * @version 0.1.0
 */
public class ResultListener extends UntypedActor {

  LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  /**
   * Receive and process a message. This message handler will unwrap the approximation result,
   * print it out to standard out and terminate the actor system gracefully.
   *
   * @param message The message that invoked this message handler
   */
  public void onReceive(Object message) {
    if (message instanceof ApproximationResult) {

      ApproximationResult approximation = (ApproximationResult) message;

      // Print out the result
      log.info("Pi Approximation: %0.20f", approximation.getApproximation());
      log.info("Total time (ms): %d", approximation.getDuration().toMillis());

      // Terminate the actor system
      getContext().system().terminate();
    } else {
      unhandled(message);
    }
  }
}
