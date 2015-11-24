package au.com.brooms.approximation;

/**
 * Interface for Pi approximation formulas.
 *
 * @author Brooms
 * @version 0.1.0
 */
public interface Approximation {

  /**
   * Calculate a step of the approximation.
   *
   * @param step The step number
   * @return The value of the approximation for the step
   */
  Double calculate(final long step);

  /**
   * Apply a final calculation to the result if required.
   *
   * @param calculation The calculation
   * @return The estimation of Pi
   */
  Double apply(final double calculation);

  /**
   * The name of the approximation being used.
   *
   * @return The approximation formula name
   */
  String name();

}
