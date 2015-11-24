package au.com.brooms.actors.messages;

import scala.concurrent.duration.Duration;

/**
 * A wrapper for the final approximation result and the duration of the estimation to assess
 * performance.
 *
 * @author Brooms
 * @version 0.1.0
 */
public class ApproximationResult {

  private final Double approximation;
  private final Duration duration;

  public ApproximationResult(Double approximation, Duration duration) {
    this.approximation = approximation;
    this.duration = duration;
  }

  public Double getApproximation() {
    return approximation;
  }

  public Duration getDuration() {
    return duration;
  }
}
