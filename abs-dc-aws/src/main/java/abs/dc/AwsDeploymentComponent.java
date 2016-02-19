package abs.dc;

import java.util.Map;

import com.amazonaws.services.opsworks.model.Instance;

/**
 * An implementation of {@link DeploymentComponent} based on
 * Amazon AWS EC2 instance.
 */
class AwsDeploymentComponent extends AbstractDeploymentComponent implements DeploymentComponent {

  private final Instance instance;

  protected AwsDeploymentComponent(Map<String, String> context, Instance instance) {
    super(instance.getInstanceId(), context);
    this.instance = instance;
  }

  @Override
  public String address() {
    return instance.getPrivateIp();
  }

  @Override
  public Status status() {
    return Status.valueOf(instance.getStatus().toUpperCase());
  }

}
