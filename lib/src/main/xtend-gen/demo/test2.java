package demo;

import forsyde.io.java.core.EdgeInfo;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import utils.Load;

@SuppressWarnings("all")
public class test2 {
  public static void main(final String[] args) {
    final String path1 = "forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
    final String path2 = "forsyde-io\\sobel-application.fiodl";
    final String root = "generateCode\\c\\single";
    ForSyDeSystemGraph model1 = Load.load(path1);
    ForSyDeSystemGraph model2 = Load.load(path2);
    model2.mergeInPlace(model1);
    AllDirectedPaths<Vertex, EdgeInfo> dj = new AllDirectedPaths<Vertex, EdgeInfo>(model2);
    test2.test(model2);
  }
  
  public static void test(final ForSyDeSystemGraph model) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method findImplInputPortSet(Vertex) is undefined for the type Class<Query>"
      + "\nThe method findImplOutputPortSet(Vertex) is undefined for the type Class<Query>");
  }
}
