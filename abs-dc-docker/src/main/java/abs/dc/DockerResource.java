package abs.dc;

import java.util.Map;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerCmd;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.StartContainerCmd;
import com.github.dockerjava.api.command.StopContainerCmd;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;

/**
 * An implementation of {@link Resource} based on a docker
 * container.
 */
public final class DockerResource implements Resource {

  @Override
  public DeploymentComponent launch(Map<String, String> context) {
    DockerClient docker = createDockerClient();
    String image = findImageName(context);
    CreateContainerCmd containerCmd = docker.createContainerCmd(image);
    CreateContainerResponse containerResponse = containerCmd.exec();
    String containerId = containerResponse.getId();
    StartContainerCmd startContainerCmd = docker.startContainerCmd(containerId);
    startContainerCmd.exec();
    InspectContainerCmd inspectContainerCmd = docker.inspectContainerCmd(containerId);
    InspectContainerResponse container = inspectContainerCmd.exec();
    return new DockerDeploymentComponent(context, containerId, container);
  }

  @Override
  public void terminate(DeploymentComponent dc) {
    DockerClient docker = createDockerClient();
    StopContainerCmd stopContainerCmd = docker.stopContainerCmd(dc.id());
    stopContainerCmd.exec();
  }

  private DockerClient createDockerClient() {
    DockerClientConfig config = DockerClientConfig.createDefaultConfigBuilder().build();
    DockerClient docker = DockerClientBuilder.getInstance(config).build();
    return docker;
  }

  private String findImageName(Map<String, String> context) {
    return context.getOrDefault("docker_image", "abs/abs-api").toString();
  }

}
