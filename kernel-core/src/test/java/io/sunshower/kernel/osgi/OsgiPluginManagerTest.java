package io.sunshower.kernel.osgi;

import io.sunshower.kernel.Kernel;
import io.sunshower.kernel.launch.KernelOptions;
import io.sunshower.test.common.Tests;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class OsgiPluginManagerTest {

  private Kernel kernel;

  @BeforeEach
  void setUp() {
    val options = new KernelOptions();
    options.setStorage(Tests.createTemp("plugins").getAbsolutePath());
    kernel = new OsgiEnabledKernel(options);
  }

  @Test
  void ensureCopyingWorks() throws MalformedURLException, ExecutionException, InterruptedException {
    val projectfile = Tests.projectOutput("kernel-tests:test-plugins:test-plugin-1", "war");
    val a =
        kernel.getPluginManager().loadExtensionFile(projectfile.toURI().toURL()).getFuture().get();
  }
}