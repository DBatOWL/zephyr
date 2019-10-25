package io.sunshower.kernel;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class MockModule implements Module {

  final Coordinate coordinate;
  final List<Dependency> dependencies;

  public void addDependency(Dependency dependency) {
    dependencies.add(dependency);
  }

  @Override
  public Set<Library> getLibraries() {
    return null;
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  public Path getModuleDirectory() {
    return null;
  }

  @Override
  public Source getSource() {
    return null;
  }

  @Override
  public Lifecycle getLifecycle() {
    return null;
  }

  @Override
  public Coordinate getCoordinate() {
    return coordinate;
  }

  @Override
  public FileSystem getFileSystem() {
    return null;
  }

  @Override
  public ClassLoader getClassLoader() {
    return null;
  }

  @Override
  public Set<Dependency> getDependencies() {
    return new HashSet<>(dependencies);
  }

  @Override
  public boolean dependsOn(Coordinate m, Transitivity transitivity) {
    return false;
  }

  @Override
  public <S> ServiceLoader<S> resolveServiceLoader(Class<S> type) {
    return null;
  }
}
