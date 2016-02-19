package abs.dc;

import java.util.Map;

import abs.dc.DeploymentComponent.Status;

/**
 * A minimal abstraction for a resource. A resource provide one
 * or more of computation power (CPU), memory, or IO.
 */
public interface Resource {

  /**
   * Create and launch a resource. After this operation, the
   * resource must be in {@link Status#OPERATIONAL} status.
   * 
   * @param context the parameters required to launch the
   *        resource
   * @return a {@link DeploymentComponent} descriptor of the
   *         resource
   */
  DeploymentComponent launch(Map<String, String> context);

  /**
   * Stop and terminate the resource described by the deployment
   * component. After this operation, the resource must be in
   * {@link Status#TERMINATED} status.
   * 
   * @param dc the {@link DeploymentComponent} descriptor of the
   *        resource
   */
  void terminate(DeploymentComponent dc);

}
