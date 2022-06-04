package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.drivers.ForSyDeModelHandler;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class test3 {
  public static void main(final String[] args) {
    try {
      final String path = "forsyde-io/modified1/complete-mapped-sobel-model.forsyde.xmi";
      final String path2 = "forsyde-io/modified1/sobel-application.fiodl";
      final String root = "generateCode/c/single/single";
      ForSyDeModelHandler loader = new ForSyDeModelHandler();
      ForSyDeSystemGraph model = loader.loadModel(path);
      model.mergeInPlace(loader.loadModel(path2));
      new ForSyDeModelHandler().writeModel(model, "example1.fiodl");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
