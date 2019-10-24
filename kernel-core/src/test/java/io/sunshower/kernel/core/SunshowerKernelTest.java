package io.sunshower.kernel.core;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SunshowerKernelTest {

  private Kernel kernel;
  private SunshowerKernelConfiguration cfg;

  @BeforeEach
  void setUp() {

    cfg = DaggerSunshowerKernelConfiguration.builder().build();
  }

  @Test
  void ensureKernelIsCreated() {
    kernel = cfg.kernel();
    assertNotNull(kernel, "kernel must not be null");
  }

  @Test
  void ensureInjectionOfPluginManagerWorks() {
    kernel = cfg.kernel();
    assertNotNull(kernel.getModuleManager(), "plugin manager must be injected");
  }
}