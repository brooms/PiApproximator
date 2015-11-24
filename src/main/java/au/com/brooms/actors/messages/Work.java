package au.com.brooms.actors.messages;

/**
 * A single piece of work wrapping the step to calculate the approximation between.
 *
 * @author Brooms
 * @version 0.1.0
 */
public class Work {

  private final Long startStep;

  private final Long toStep;

  public Work(final Long startStep, final Long toStep) {
    this.startStep = startStep;
    this.toStep = toStep;
  }

  public Long getStartStep() {
    return this.startStep;
  }

  public Long getToStep() {
    return this.toStep;
  }

}
