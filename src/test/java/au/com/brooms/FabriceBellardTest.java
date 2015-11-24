package au.com.brooms;

import au.com.brooms.approximation.FabriceBellard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Test suite for Fabrice-Bellard Pi approximation
 *
 * @author brooms
 */
public class FabriceBellardTest {

  private FabriceBellard formula;

  @Before
  public void setUp() {
    formula = new FabriceBellard();
  }

  /**
   * Test boundary condition when the step is 0 to make sure no weird divide by 0 errors occur
   */
  @Test
  public void testZeroStep() {
    double result = formula.calculate(0);
    Assert.assertEquals(result, 201.07301587301583, 0);
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
