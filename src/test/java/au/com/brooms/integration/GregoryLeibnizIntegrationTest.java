package au.com.brooms.integration;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;

import au.com.brooms.actors.Controller;
import au.com.brooms.actors.messages.ApproximationResult;
import au.com.brooms.actors.messages.StartApproximation;
import au.com.brooms.approximation.Approximation;
import au.com.brooms.approximation.GregoryLeibniz;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Actor system test cases to test controller, worker and result listener using different
 * approximation methods.
 *
 * @author brooms
 */
public class GregoryLeibnizIntegrationTest extends AbstractIntegrationTest {

  /**
   * Akka test system
   */
  protected static ActorSystem system;

  @BeforeClass
  public static void setup() {
    // Initialise an actor system
    system = ActorSystem.create("GregoryLeibniz");
  }

  @AfterClass
  public static void teardown() {
    // Clean up and shutdown
    JavaTestKit.shutdownActorSystem(system);
    system = null;
  }

  /**
   * Test the actor system using the Gregory-Leibniz approximation
   */
  @Test
  public void testGregoryLeibniz() {
    new JavaTestKit(system) {{

      // Approximation method
      Approximation approximation = new GregoryLeibniz();

      // Create the controller - the entry point to the system
      ActorRef controller = system.actorOf(
          Props.create(Controller.class, approximation, numberOfSteps, numberOfWorkers, getRef()), "controller");

      // Send a start message to the controller
      controller.tell(new StartApproximation(), ActorRef.noSender());

      // Wait for a result and extract it
      final ApproximationResult result =
          new ExpectMsg<ApproximationResult>("ApproximationResult") {

            @Override
            protected ApproximationResult match(Object o) {
              if (o instanceof ApproximationResult) {
                return ((ApproximationResult) o);
              } else {
                throw noMatch();
              }
            }
          }.get();

      // Some basic asserts - we should have a result and the approximation should be pretty close to Pi,
      // something is clearly wrong if the calculation if executes in under zero milliseconds
      Assert.assertNotNull(result);
      Assert.assertTrue(result.getApproximation() > BASIC_PI);
      Assert.assertTrue(result.getDuration().toMillis() > 0);

      // Print out the results
      System.out.println("--------------------------------------------------------------------------------");
      System.out.println("Pi approximation (" + approximation.name() + "): " + result.getApproximation());
      System.out.println("Duration: " + result.getDuration());
      System.out.println("--------------------------------------------------------------------------------");

    }};
  }

}