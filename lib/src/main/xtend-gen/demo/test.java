package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.drivers.ForSyDeModelHandler;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * load fiodl file
 */
@SuppressWarnings("all")
public class test {
  public static void main(final String[] args) {
    try {
      final String path = "a.fiodl";
      ForSyDeSystemGraph model = new ForSyDeModelHandler().loadModel(path);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
