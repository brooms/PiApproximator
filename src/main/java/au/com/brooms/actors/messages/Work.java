package au.com.brooms.actors.messages;

/**
 * A single piece of work wrapping the step to calculate the approximation at.
 *
 * @author Brooms
 * @version 0.1.0
 */
public class Work {

  private final Long step;

  public Work(Long startStep) {
    this.step = startStep;
  }

  public Long getStep() {
    return step;
  }

}
