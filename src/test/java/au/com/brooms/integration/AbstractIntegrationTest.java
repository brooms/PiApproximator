package au.com.brooms.integration;

import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Abstract integration test containing common functionality used to configure each approximation.
 *
 * @author brooms
 */
abstract class AbstractIntegrationTest {

  /**
   * Really rough value of Pi used to validate results
   */
  protected static final Double BASIC_PI = 3.1415;

  /**
   * Number of steps to estimate Pi to
   */
  protected static final long numberOfSteps = 10000000l;

  /**
   * Number of workers to calculate steps concurrently
   */
  protected static final int numberOfWorkers = 50;

}
