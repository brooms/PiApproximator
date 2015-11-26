package au.com.brooms.approximation;

/**
 * The Madhava-Leibniz approximation to Pi.
 *
 * <p>This is a slight variation to the Gregory-Leibniz estimatio to speed up convergence.</p>
 *
 * @author Brooms
 * @version 0.1.0
 */
public class Madhava implements Approximation {

  public Double calculate(final long step) {

    double stepD = (double) step;

    return Math.pow(-1, stepD) / ((2 * stepD + 1) * Math.pow(3, stepD));
  }

  public Double apply(final double calculation) {
    // Apply a multiplier
    return Math.sqrt(12) * calculation;
  }

  public String name() {
    return "Madhava-Leibniz";
  }
}
