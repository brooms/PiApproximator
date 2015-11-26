package au.com.brooms.actors.messages;

/**
 * A single worker result contianing a value and the id of the worker.
 *
 * @author Brooms
 * @version 0.1.0
 */
public class WorkerResult {

  private Double value;
  private String id;

  public WorkerResult(final Double value, final String id) {
    this.value = value;
    this.id = id;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(final Double value) {
    this.value = value;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }
}
