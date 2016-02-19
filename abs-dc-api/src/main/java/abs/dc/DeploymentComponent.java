package abs.dc;

import java.util.Map;

/**
 * An ABS DeploymentComponent.
 */
public interface DeploymentComponent {

  /**
   * The runtime status of the {@link DeploymentComponent} and
   * {@link Resource} it describes.
   */
  public static enum Status {
    STARTING,

    OPERATIONAL,

    STOPPING,

    TERMINATED;
  }

  /**
   * A unique identifier of the resource.
   * 
   * @return a unique identifier of the resource
   */
  String id();

  /**
   * The potential network address of the resource.
   * 
   * @return an IP or host name presentation of the address of
   *         the resource
   */
  String address();

  /**
   * The operational status of the resource.
   * 
   * @return a value from {@link Status}.
   */
  Status status();

  /**
   * The original context with which the resource of this
   * deployment component was launched.
   * 
   * @return the original resource context
   */
  Map<String, String> context();

}
