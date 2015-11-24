package au.com.brooms;

import au.com.brooms.approximation.GregoryLeibniz;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Test suite for Gregory Leibniz Pi approximation formula
 *
 * @author brooms
 */
public class GregoryLeibnizTest {

  /**
   * The expected value of the Gregory Leibniz approximation at step 15
   */
  public static final double STEP_15_EXPECTED_VAL = -0.03225806451612903;
  private GregoryLeibniz formula;

  @Before
  public void setUp() {
    formula = new GregoryLeibniz();
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
   * Test the odd steps are negative
   */
  @Test
  public void testOddStepIsNegative() {
    double result = formula.calculate(3);
    Assert.assertTrue(result < 0);
  }

  /**
   * Test the event steps are positive
   */
  @Test
  public void testEvenStepIsPositive() {
    double result = formula.calculate(4);
    Assert.assertTrue(result > 0);
  }

  /**
   * Test step 15 is equal to the expected value i.e. a regular step
   */
  @Test
  public void testStep15() {
    double result = formula.calculate(15);
    double expected = STEP_15_EXPECTED_VAL;
    Assert.assertEquals(result, expected, 0);

  }
}
