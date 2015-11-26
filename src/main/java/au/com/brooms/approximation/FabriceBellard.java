package au.com.brooms.approximation;

/**
 * The Fabrice-Bellard approximation to Pi circa 1997.
 *
 * <p>For a description of the approximation see <a href="https://en.wikipedia.org/wiki/Bellard%27s_formula">Fabrice-Bellard
 * Formula</a></p>
 *
 * @author Brooms
 * @version 0.1.0
 */
public class FabriceBellard implements Approximation {

  public FabriceBellard() {
  }

  /**
   * Calculate the Fabrice-Bellard step for the approximation to Pi.
   *
   * @param step The step number
   * @return The step value
   */
  public Double calculate(final long step) {

    double stepD = (double) step;

    double part1 = Math.pow(-1, stepD) / Math.pow(2, 10 * stepD);
    double part2 = (-1.0 * (Math.pow(2, 5) / (4 * stepD + 1)));
    double part3 = -1 / (4 * stepD + 3);
    double part4 = Math.pow(2, 8) / (10 * stepD + 1);
    double part5 = (-1.0 * (Math.pow(2, 6) / (10 * stepD + 3)));
    double part6 = (-1.0 * (Math.pow(2, 2) / (10 * stepD + 5)));
    double part7 = (-1.0 * (Math.pow(2, 2) / (10 * stepD + 7)));
    double part8 = (1 / (10 * stepD + 9));

    double firstSummation = part2 + part3 + part4 + part5 + part6 + part7 + part8;
    return part1 * firstSummation;
  }

  public Double apply(final double calculation) {
    // Apply a final calculation which is a multiplier
    return (1 / (Math.pow(2, 6))) * calculation;
  }

  public String name() {
    return "Fabrice-Bellard";
  }
}
