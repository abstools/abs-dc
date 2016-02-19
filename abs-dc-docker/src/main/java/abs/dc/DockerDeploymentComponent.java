package abs.dc;

import java.util.Map;

import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse.ContainerState;

/**
 * An implementation of {@link DeploymentComponent} based on a
 * docker container.
 */
class DockerDeploymentComponent extends AbstractDeploymentComponent implements DeploymentComponent {

  private final InspectContainerResponse container;

  DockerDeploymentComponent(Map<String, String> context, String containerId,
      InspectContainerResponse container) {
    super(containerId, context);
    this.container = container;
  }

  @Override
  public String address() {
    return container.getNetworkSettings().getIpAddress();
  }

  @Override
  public Status status() {
    ContainerState state = container.getState();
    if (state.isRunning()) {
      return Status.OPERATIONAL;
    }
    if (state.isPaused()) {
      return Status.TERMINATED;
    }
    return Status.STARTING;
  }

}
