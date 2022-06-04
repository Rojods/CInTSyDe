package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.drivers.ForSyDeModelHandler;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class test5 {
  public static void main(final String[] args) {
    try {
      final String path = "example2.fiodl";
      final String root = "generateCode/c/single2";
      ForSyDeModelHandler loader = new ForSyDeModelHandler();
      ForSyDeSystemGraph model = loader.loadModel(path);
      Vertex a = model.queryVertex("p1impl").get();
      InputOutput.<String>println("");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
