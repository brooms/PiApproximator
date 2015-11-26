package au.com.brooms.actors;

import akka.actor.UntypedActor;

import au.com.brooms.actors.messages.Work;
import au.com.brooms.actors.messages.WorkerResult;
import au.com.brooms.approximation.Approximation;

/**
 * Akka worker to calculate a single step in a Pi approximation.
 *
 * @author Brooms
 * @version 0.1.0
 */
public class Worker extends UntypedActor {

  private Approximation approximation;
  private String name;

  /**
   * Default constructor.
   */
  public Worker() {
  }

  /**
   * Create an instance of this worker using the approximation method and assigning the worker a
   * name.
   *
   * @param approximation The approximation method to use
   * @param name          The name of this worker (primarily for identification and logging
   *                      purposes)
   */
  public Worker(Approximation approximation, String name) {
    this.approximation = approximation;
    this.name = name;
  }

  /**
   * Perform the calculation for the given step range.
   *
   * @param startStep The start step to start from
   * @param toStep The step to calculate to
   * @return The approximation for the step range
   */
  private double calculate(final long startStep, final long toStep) {
    double result = 0;
    for (long step = startStep; step < toStep; step++) {
      result += approximation.calculate(step);
    }
    return result;
  }

  /**
   * Receive and process a message. This message handler will unwrap the a piece of work,
   * perform the approximation calculation at the given step and send a message asynchronously
   * containing the result.
   *
   * @param message The message that invoked this message handler
   */
  public void onReceive(Object message) {
    if (message instanceof Work) {

      Work work = (Work) message;

      // Perform calculation according to the approximation
      double calculation = calculate(work.getStartStep(), work.getToStep());

      // Wrap the calculation in a result object
      WorkerResult result = new WorkerResult(calculation, this.name);

      // Fire the result
      getSender().tell(result, getSelf());
    } else {
      // Handle an unknown message type
      unhandled(message);
    }
  }

  public Approximation getApproximation() {
    return approximation;
  }

  public void setApproximation(Approximation approximation) {
    this.approximation = approximation;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

}
