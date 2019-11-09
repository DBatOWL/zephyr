package io.sunshower.kernel.core.lifecycle;

import io.sunshower.kernel.concurrency.Context;
import io.sunshower.kernel.concurrency.Task;
import io.sunshower.kernel.concurrency.TaskException;
import io.sunshower.kernel.concurrency.TaskStatus;
import io.sunshower.kernel.core.Kernel;
import io.sunshower.kernel.module.KernelModuleEntry;
import io.sunshower.kernel.module.ModuleListParser;
import lombok.val;

public class KernelModuleListReadPhase implements Task {
  public static final String INSTALLED_MODULE_LIST = "MODULE_LIST_INSTALLED";

  public KernelModuleListReadPhase() {}

  @Override
  public TaskValue run(Context context) {
    val fs = context.get(Kernel.class).getFileSystem();

    if (fs == null) {
      throw new TaskException(TaskStatus.UNRECOVERABLE);
    }
    val entries = ModuleListParser.read(fs, KernelModuleEntry.MODULE_LIST);
    context.set(INSTALLED_MODULE_LIST, entries);
    return null;
  }
}