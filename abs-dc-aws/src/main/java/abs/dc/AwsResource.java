package abs.dc;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.services.opsworks.AWSOpsWorksAsyncClient;
import com.amazonaws.services.opsworks.model.CreateInstanceRequest;
import com.amazonaws.services.opsworks.model.CreateInstanceResult;
import com.amazonaws.services.opsworks.model.DescribeInstancesRequest;
import com.amazonaws.services.opsworks.model.DescribeInstancesResult;
import com.amazonaws.services.opsworks.model.Instance;
import com.amazonaws.services.opsworks.model.StartInstanceRequest;
import com.amazonaws.services.opsworks.model.StopInstanceRequest;

/**
 * A resource implementation using Amazon AWS OpsWorks API.
 */
public final class AwsResource implements Resource {

  private final AWSCredentialsProvider awsCredentialsProvider = new AWSCredentialsProviderChain(
      new EnvironmentVariableCredentialsProvider(), new SystemPropertiesCredentialsProvider(),
      new PropertiesFileCredentialsProvider("aws.properties"));
  private final AWSOpsWorksAsyncClient opsWorksClient =
      new AWSOpsWorksAsyncClient(awsCredentialsProvider);

  @Override
  public DeploymentComponent launch(Map<String, String> context) {
    CreateInstanceRequest createInstanceRequest = new CreateInstanceRequest();
    CreateInstanceResult instanceResult = opsWorksClient.createInstance(createInstanceRequest);
    String instanceId = instanceResult.getInstanceId();
    Future<Void> f =
        opsWorksClient.startInstanceAsync(new StartInstanceRequest().withInstanceId(instanceId));
    try {
      f.get();
      DescribeInstancesResult instances = opsWorksClient
          .describeInstances(new DescribeInstancesRequest().withInstanceIds(instanceId));
      Instance instance = instances.getInstances().get(0);
      return new AwsDeploymentComponent(context, instance);
    } catch (InterruptedException | ExecutionException e) {
      return null;
    }
  }

  @Override
  public void terminate(DeploymentComponent dc) {
    opsWorksClient.stopInstance(new StopInstanceRequest().withInstanceId(dc.id()));
  }

}
