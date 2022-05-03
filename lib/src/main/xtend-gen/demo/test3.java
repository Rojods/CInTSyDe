package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.impl.Executable;
import forsyde.io.java.typed.viewers.impl.ExecutableViewer;
import forsyde.io.java.typed.viewers.moc.sdf.SDFCombViewer;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.InputOutput;
import utils.Load;
import utils.Query;

@SuppressWarnings("all")
public class test3 {
  public static void main(final String[] args) {
    final String path1 = "forsyde-io\\test\\complete-mapped-sobel-model.forsyde.xmi";
    final String path2 = "forsyde-io\\test\\sobel-application.fiodl";
    final String root = "generateCode\\c\\single";
    ForSyDeSystemGraph model1 = Load.load(path1);
    ForSyDeSystemGraph model2 = Load.load(path2);
    model2.mergeInPlace(model1);
    final ForSyDeSystemGraph model = model2;
    Vertex v = Query.findVertexByName(model, "Abs");
    Set<Executable> a = new SDFCombViewer(v).getCombFunctionsPort(model);
    for (final Executable e : a) {
      {
        String c = ((ExecutableViewer) e).getViewedVertex().getIdentifier();
        InputOutput.<String>println(c);
      }
    }
  }
}
