package au.com.brooms.error;

/**
 * Exception to handle when invalid parameters are passed to the estimation.
 *
 * @author Brooms
 * @version 0.1.0
 */
public class InvalidParametersException extends Exception {

  public InvalidParametersException(String message) {
    super(message);
  }
}
