package abs.dc;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * A base abstract implementation of
 * {@link DeploymentComponent}.
 */
public abstract class AbstractDeploymentComponent implements DeploymentComponent {

  private final String id;
  private final Map<String, String> context;

  protected AbstractDeploymentComponent(Map<String, String> context) {
    this(UUID.randomUUID().toString(), context);
  }

  protected AbstractDeploymentComponent(String id, Map<String, String> context) {
    this.id = id;
    this.context = context;
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public Map<String, String> context() {
    return Collections.unmodifiableMap(context);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return String.format("DC(%s,%s,%s)", id, status(), address());
  }

}
