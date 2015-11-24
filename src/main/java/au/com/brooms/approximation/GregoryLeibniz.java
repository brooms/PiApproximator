package au.com.brooms.approximation;

/**
 * The Gregory-Leibniz approximation to Pi.
 *
 * <p>The Leibniz summation is the sum from 0 to n where n is the number of steps the calculate the
 * approximation to, the sum is then multiplied by 4. Each step is calculated by the formula:
 *
 * (-1)^n/(2*n+1)</p>
 */
public class GregoryLeibniz implements Approximation {

  /**
   * Calculate the Gregory Leibniz step for the approximation to Pi.
   *
   * @param step The step number
   * @return The step value
   */
  public Double calculate(long step) {
    return Math.pow(-1, (double) step) / (2 * (double) step + 1);
  }

  public Double apply(double calculation) {
    return 4 * calculation;
  }

  public String name() {
    return "Gregory-Leibniz";
  }
}
