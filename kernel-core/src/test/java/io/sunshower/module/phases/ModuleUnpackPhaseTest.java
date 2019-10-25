package io.sunshower.module.phases;

import static org.junit.jupiter.api.Assertions.*;

import io.sunshower.kernel.Library;
import io.sunshower.kernel.process.KernelProcess;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.Collections;
import java.util.Set;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings({
  "PMD.AvoidDuplicateLiterals",
  "PMD.JUnitTestContainsTooManyAsserts",
  "PMD.JUnitAssertionsShouldIncludeMessage"
})
class ModuleUnpackPhaseTest extends AbstractModulePhaseTestCase {

  @Test
  void ensureModuleTransferWorksUpToTransferPhase() throws Exception {
    install("test-plugin-2");
    context.setContextValue(
        ModuleUnpackPhase.LIBRARY_DIRECTORIES, Collections.singleton("WEB-INF/lib/"));
    val process = createProcess();
    process.call();

    Set<Library> values = context.getContextValue(ModuleUnpackPhase.INSTALLED_LIBRARIES);
    assertFalse(values.isEmpty(), "installed libraries must have contents");
  }

  @Test
  void ensureModuleTransferResolvesLibrariesCorrectly() throws Exception {

    install("test-plugin-2");
    context.setContextValue(
        ModuleUnpackPhase.LIBRARY_DIRECTORIES, Collections.singleton("WEB-INF/lib/"));
    val process = createProcess();
    process.call();

    Set<Library> values = context.getContextValue(ModuleUnpackPhase.INSTALLED_LIBRARIES);
    assertEquals(values.size(), 3, "must have correct libraries");
  }

  @Test
  void ensureAllInstalledLibrariesExist() throws Exception {

    install("test-plugin-2");
    context.setContextValue(
        ModuleUnpackPhase.LIBRARY_DIRECTORIES, Collections.singleton("WEB-INF/lib/"));
    val process = createProcess();
    process.call();

    Set<Library> values = context.getContextValue(ModuleUnpackPhase.INSTALLED_LIBRARIES);
    for (val lib : values) {
      assertTrue(lib.getFile().exists());
    }
  }

  private KernelProcess createProcess() throws Exception {
    val process = new KernelProcess(context);
    process.addPhase(new ModuleDownloadPhase());
    process.addPhase(new ModuleScanPhase());
    process.addPhase(new ModuleTransferPhase());
    process.addPhase(new ModuleUnpackPhase());
    return process;
  }

  @Override
  @AfterEach
  void tearDown() throws IOException {
    super.tearDown();
    FileSystem fs = context.getContextValue(ModuleTransferPhase.MODULE_FILE_SYSTEM);
    fs.close();
  }
}
