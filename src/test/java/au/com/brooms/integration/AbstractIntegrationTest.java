package au.com.brooms.integration;


/**
 * Abstract integration test containing common functionality used to configure each approximation.
 *
 * @author brooms
 */
abstract class AbstractIntegrationTest {

  /**
   * Number of steps to estimate Pi to
   */
  protected static final long numberOfSteps = 1000000l;

  /**
   * Number of workers to calculate steps concurrently
   */
  protected static final int numberOfWorkers = 10;

}
