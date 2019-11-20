package io.zephyr.kernel.command.commands;

import io.zephyr.api.CommandRegistry;
import io.zephyr.api.CommandRegistryDecorator;
import io.zephyr.kernel.command.commands.server.ServerCommandSet;

public class DefaultCommands implements CommandRegistryDecorator {
  @Override
  public void decorate(CommandRegistry registry) {
    registry.register(new ServerCommandSet());
  }
}
