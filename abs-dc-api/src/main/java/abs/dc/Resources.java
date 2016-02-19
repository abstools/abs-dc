package abs.dc;

import java.util.HashSet;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public final class Resources implements Resource {
  private static final ServiceLoader<Resource> LOADER = ServiceLoader.load(Resource.class);
  private static final ExecutorService E = Executors.newWorkStealingPool();

  private static final BiFunction<Resource, Map<String, String>, DeploymentComponent> LAUNCHER =
      (r, context) -> r.launch(context);
  private static final BiConsumer<Resource, DeploymentComponent> TERMINATOR =
      (r, dc) -> r.terminate(dc);

  static Resource loadResources() {
    LOADER.reload();
    final Set<Resource> resources = new HashSet<>();
    LOADER.iterator().forEachRemaining(r -> resources.add(r));
    if (resources.isEmpty()) {
      throw new IllegalArgumentException("No Resource implementation found.");
    }
    if (resources.size() > 1) {
      throw new UnsupportedOperationException(
          "Multiple Resource implementations is not supported.");
    }
    return resources.iterator().next();
  }

  @Override
  public DeploymentComponent launch(Map<String, String> context) {
    try {
      Future<DeploymentComponent> f = E.submit(() -> LAUNCHER.apply(loadResources(), context));
      return f.get();
    } catch (InterruptedException | ExecutionException e) {
      return null;
    }
  }

  @Override
  public void terminate(DeploymentComponent dc) {
    try {
      Future<?> f = E.submit(() -> TERMINATOR.accept(loadResources(), dc));
      f.get();
    } catch (InterruptedException | ExecutionException e) {
      // Ignore
    }
  }

}
