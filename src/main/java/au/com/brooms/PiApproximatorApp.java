package au.com.brooms;

import au.com.brooms.dagger.ApproximatorModule;

import dagger.Component;

import java.util.logging.Logger;

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
    if (args.length != 3) {
      System.out.println("--------------------------------------------------------------------------------");
      System.out.println("Incorrect Usage. PiApproximation <numberOfSteps> <numberOfWorkers> <method>");
      System.out.println("Example: PiApproximation 1000000 10 Madhava-Leibniz");
      System.out.println("Methods are: Madhava-Leibniz, Gregory-Leibniz, Fabrice-Bellard, Bailey-Borwein-Plouffe");
      System.out.println("Default approximation methods is Gregory-Leibniz");
      System.out.println("--------------------------------------------------------------------------------");
    } else {

      // Parse the arguments
      long numberOfSteps = Long.parseLong(args[0]);
      int numberOfWorkers = Integer.parseInt(args[1]);

      // Start the approximation calculation
      approximation.piApproximation().calculate(numberOfSteps, numberOfWorkers, args[2]);
    }
  }

}
