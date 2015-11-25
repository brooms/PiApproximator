package au.com.brooms;

import au.com.brooms.dagger.ApproximatorModule;
import au.com.brooms.error.InvalidParametersException;

import dagger.Component;

/**
 * PiApproximationApp - a main class to launch the PiApproximation actor system This application will estimate the value
 * of Pi to a number of steps based on an approximation method.
 *
 * <p>Possible approximation methods are: Madhava-Leibniz, Gregory-Leibniz, Fabrice-Bellard and Bailey-Borwein-Plouffe
 *
 * Usage: PiApproximation numberOfSteps numberOfWorkers method Example Usage: PiApproximation 1000000 10
 * Madhava-Leibniz</p>
 *
 * @author Brooms
 * @version 0.1.0
 */
public class PiApproximatorApp {

  /**
   * Initialise the Dagger dependency injection framework.
   */
  @Component(modules = ApproximatorModule.class)
  interface Approximation {
    PiApproximation piApproximation();
  }

  /**
   * Main method.
   *
   * @param args Expected args: numberOfSteps numberOfWorkers method
   */
  public static void main(String[] args) {

    // Build the dependency graph and initialise Dagger DI
    Approximation approximation = au.com.brooms.DaggerPiApproximatorApp_Approximation.builder().build();

    // Check the number of arguments, if the right number of arguments are not present print out usage instructions
    if (args.length < 2) {
      System.out.println("--------------------------------------------------------------------------------");
      System.out.println("Incorrect Usage. PiApproximation <numberOfSteps> <numberOfWorkers> <method>");
      System.out.println("Example: PiApproximation 1000000 10 Gregory-Leibniz");
      System.out.println("Methods are: Gregory-Leibniz, Madhava-Leibniz, Fabrice-Bellard, Bailey-Borwein-Plouffe");
      System.out.println("Default approximation method is Gregory-Leibniz if none is specified.");
      System.out.println("--------------------------------------------------------------------------------");
    } else {

      // Parse the arguments
      try {
        long numberOfSteps = Long.parseLong(args[0]);
        int numberOfWorkers = Integer.parseInt(args[1]);

        // Default the estimation method
        String approximationMethod = "Gregory-Leibniz";
        if (args.length == 3) {
          approximationMethod = args[2];
        }

        // Start the approximation calculation
        approximation.piApproximation().calculate(numberOfSteps, numberOfWorkers, approximationMethod);

      } catch (NumberFormatException ex) {
        // Will occur if bad numbers are passed as arguments
        System.out.println("Unable to parse numberOfSteps or numberOfWorkers. Not a valid number");
        ex.printStackTrace();
      } catch (InvalidParametersException iex) {
        // Will occur if the numberOfSteps or numberOfWorkers <= 0
        iex.printStackTrace();
      }
    }
  }

}
