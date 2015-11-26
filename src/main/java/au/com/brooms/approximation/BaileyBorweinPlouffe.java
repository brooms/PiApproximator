package au.com.brooms.approximation;

/**
 * The Bailey-Borwein-Plouffe approximation to Pi.
 *
 * <p>For a description of the approximation see <a href="https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula">Bailey-Borwein-Plouffe
 * Formula</a></p>
 *
 * @author Brooms
 * @version 0.1.0
 */
public class BaileyBorweinPlouffe implements Approximation {

  public BaileyBorweinPlouffe() {
  }

  /**
   * Calculate the Bailey-Borwein-Plouffe step for the approximation to Pi.
   *
   * @param step The step number
   * @return The step value
   */
  public Double calculate(final long step) {

    double part1 = 1 / Math.pow(16, (double) step);
    double part2 = 4 / ((8 * (double) step) + 1);
    double part3 = 2 / ((8 * (double) step) + 4);
    double part4 = 1 / ((8 * (double) step) + 5);
    double part5 = 1 / ((8 * (double) step) + 6);

    double firstSummation = part2 - part3;
    double secondSummation = (-1.0 * part4) - part5;
    double thirdSummation = firstSummation + secondSummation;

    return part1 * thirdSummation;
  }

  public Double apply(final double calculation) {
    return calculation;
  }

  public String name() {
    return "Bailey-Borwein-Plouffe";
  }
}
