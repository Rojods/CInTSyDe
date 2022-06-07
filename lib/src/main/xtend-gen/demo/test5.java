package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexAcessor;
import forsyde.io.java.core.VertexTrait;
import forsyde.io.java.drivers.ForSyDeModelHandler;
import forsyde.io.java.typed.viewers.impl.Executable;
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class test5 {
  public static void main(final String[] args) {
    try {
      final String path = "example1-2cores.fiodl";
      ForSyDeModelHandler loader = new ForSyDeModelHandler();
      ForSyDeSystemGraph model = loader.loadModel(path);
      Vertex sdfchannel = model.queryVertex("gxsig").get();
      Vertex sdfactor = model.queryVertex("Gx").get();
      Set<Executable> b = SDFActor.safeCast(sdfactor).get().getCombFunctionsPort(model);
      InputOutput.<Set<Executable>>println(b);
      Set<Vertex> c = VertexAcessor.getMultipleNamedPort(model, sdfactor, "combFunctions", VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexAcessor.VertexPortDirection.OUTGOING);
      for (final Vertex t : c) {
        InputOutput.<String>println(t.getIdentifier());
      }
      InputOutput.<Set<Vertex>>println(c);
      InputOutput.<String>println("");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
