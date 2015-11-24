package au.com.brooms;

import au.com.brooms.approximation.Madhava;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Test suite for Madhava Pi approximation
 *
 * @author brooms
 */
public class MadhavaTest {

  private Madhava formula;

  @Before
  public void setUp() {
    formula = new Madhava();
  }

  /**
   * Test boundary condition when the step is 0 to make sure no weird divide by 0 errors occur
   */
  @Test
  public void testZeroStep() {
    double result = formula.calculate(0);
    Assert.assertEquals(result, 1, 0);
  }

  /**
   * Test the max boundary condition to make sure the universe doesn't implode
   */
  @Test
  public void testMaxStep() {
    double result = formula.calculate(Long.MAX_VALUE);
    Assert.assertNotNull(result);
  }

  /**
   * Test a regular step can be calculated
   */
  @Test
  public void testStep15() {
    double result = formula.calculate(15);
    Assert.assertTrue(result < 0);

  }
}
