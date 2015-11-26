package au.com.brooms.approximation;

import java.util.HashMap;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Approximation factory containing all possible approximation methods. Allows for runtime lookup
 * and retrieval of approximation methods.
 *
 * @author Brooms
 * @version 0.1.0
 */
public class ApproximationFactory {

  private static Logger logger = Logger.getLogger(Approximation.class.getName());

  @Inject
  @Named("BaileyBorweinPlouffe")
  public Approximation baileyBorweinPlouffe;

  @Inject
  @Named("Fabrice")
  public Approximation fabriceBellard;

  @Inject
  @Named("Madhava")
  public Approximation madhava;

  @Inject
  @Named("Leibniz")
  public Approximation gregoryLeibniz;


  /**
   * Map to store approximation methods.
   */
  HashMap<String, Approximation> approximationMap;

  /**
   * Default Constructor.
   */
  @Inject
  public ApproximationFactory() {
  }

  /**
   * Retrieve the approximation method from the factory.
   *
   * @param name The name of the approximation method
   * @return The approximation method or if the method is not found, default to Gregory-Leibniz approximation
   */
  public Approximation getApproximation(final String name) {

    // Initialise the map if it is empty, lazy initialise here allows for approximation methods to be injected into
    // this instance by the Dagger framework
    if (approximationMap == null) {
      initialiseMap();
    }

    // Find and retrieve the approximation method
    Approximation approximationMethod = approximationMap.get(name.toLowerCase());

    if (approximationMethod == null) {
      // Approximation method not found, default to Gregory-Leibniz
      logger.warning("Unable to find approximation method " + name.toLowerCase()
          + " returning default approximation method.");
      approximationMethod = gregoryLeibniz;
    }

    logger.info("Returning approximation method: " + approximationMethod.name().toLowerCase());
    return approximationMethod;
  }

  /**
   * Initialise the factory.
   */
  private void initialiseMap() {
    approximationMap = new HashMap<>();
    approximationMap.put(fabriceBellard.name().toLowerCase(), fabriceBellard);
    approximationMap.put(madhava.name().toLowerCase(), madhava);
    approximationMap.put(gregoryLeibniz.name().toLowerCase(), gregoryLeibniz);
    approximationMap.put(baileyBorweinPlouffe.name().toLowerCase(), baileyBorweinPlouffe);
  }

}
