package io.zephyr.kernel.dependencies;

import io.sunshower.gyre.Analyzer;
import io.zephyr.kernel.Coordinate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;
import lombok.val;

final class CoordinateAnalyzer implements Analyzer<Coordinate, String> {
  @Override
  public Iterator<String> segments(Coordinate key) {
    return new CoordinateIterator(key);
  }

  private static class CoordinateIterator implements Iterator<String> {
    final Iterator<String> parts;

    static final Pattern pattern = Pattern.compile("\\.");

    public CoordinateIterator(Coordinate coordinate) {
      val parts = new ArrayList<String>();
      put(parts, coordinate.getGroup());

      put(parts, coordinate.getName());
      val version = coordinate.getVersion();
      if (version != null) {
        put(parts, version.toString());
      }
      this.parts = parts.iterator();
    }

    static void put(Collection<String> collection, String value) {
      if (value != null) {
        collection.addAll(Arrays.asList(pattern.split(value)));
      }
    }

    @Override
    public boolean hasNext() {
      return parts.hasNext();
    }

    @Override
    public String next() {
      return parts.next();
    }
  }
}
