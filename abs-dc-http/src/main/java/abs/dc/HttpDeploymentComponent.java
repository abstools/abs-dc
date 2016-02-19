package abs.dc;

import java.util.Map;

/**
 * An implementation of {@link DeploymentComponent} based on a
 * locally running HTTP server as an instance of a resource.
 */
public final class HttpDeploymentComponent extends AbstractDeploymentComponent {

  private final String address;
  private Status status = Status.OPERATIONAL;

  protected HttpDeploymentComponent(String id, Map<String, String> context) {
    this(id, context, "localhost");
  }

  protected HttpDeploymentComponent(String id, Map<String, String> context, String address) {
    super(id, context);
    this.address = address;
  }

  @Override
  public String address() {
    return address;
  }

  @Override
  public Status status() {
    return status;
  }

  public String port() {
    return context().getOrDefault("port", "8080").toString();
  }

}
