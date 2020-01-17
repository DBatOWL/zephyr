package io.zephyr.kernel.core;

import dagger.Module;
import dagger.Provides;
import io.zephyr.kernel.concurrency.KernelScheduler;
import io.zephyr.kernel.concurrency.Scheduler;
import io.zephyr.kernel.concurrency.WorkerPool;
import io.zephyr.kernel.dependencies.DefaultDependencyGraph;
import io.zephyr.kernel.dependencies.DependencyGraph;
import io.zephyr.kernel.launch.KernelOptions;
import java.util.ServiceLoader;
import javax.inject.Singleton;
import lombok.val;

@Module
@SuppressWarnings("PMD.UnusedPrivateMethod")
public class SunshowerKernelInjectionModule {

  //  @Provides
  //  @Singleton
  //  public WorkerPool workerPool() {
  //    // TODO make kernel thread pool configurable
  //    return new ExecutorWorkerPool(Executors.newFixedThreadPool(4));
  //  }

  @Provides
  @Singleton
  public Scheduler<String> kernelScheduler(WorkerPool pool) {
    return new KernelScheduler<>(pool);
  }

  //  @Provides
  //  @Singleton
  //  public ExecutorService executorService(KernelOptions options) {
  //    return Executors.newFixedThreadPool(options.getConcurrency());
  //  }

  @Provides
  @Singleton
  public DependencyGraph dependencyGraph() {
    return new DefaultDependencyGraph();
  }

  @Provides
  @Singleton
  public Kernel sunshowerKernel(
      ModuleManager moduleManager,
      DependencyGraph graph,
      KernelOptions options,
      ClassLoader classLoader,
      Scheduler<String> scheduler) {
    SunshowerKernel.setKernelOptions(options);
    val kernel = new SunshowerKernel(moduleManager, scheduler);
    val classpathManager = moduleClasspathManager(graph, classLoader, kernel);
    kernel.setModuleClasspathManager(classpathManager);
    moduleManager.initialize(kernel);
    return kernel;
  }

  @Provides
  @Singleton
  public ModuleManager pluginManager(DependencyGraph dependencyGraph) {
    return new DefaultModuleManager(dependencyGraph);
  }

  private ModuleClasspathManager moduleClasspathManager(
      DependencyGraph graph, ClassLoader classLoader, Kernel kernel) {
    val result =
        ServiceLoader.load(ModuleClasspathManagerProvider.class, classLoader)
            .findFirst()
            .get()
            .create(graph, kernel);
    return result;
  }
}
