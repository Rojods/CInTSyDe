package demo;

import com.google.common.base.Objects;
import forsyde.io.java.core.EdgeInfo;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import java.util.function.Predicate;
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
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        String _identifier = v.getIdentifier();
        return Objects.equal(_identifier, "GrayScale");
      }
    };
    Vertex a = model2.vertexSet().stream().filter(_function).findAny().orElse(null);
    final Predicate<Vertex> _function_1 = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        String _identifier = v.getIdentifier();
        return Objects.equal(_identifier, "GrayScaleImpl");
      }
    };
    Vertex b = model2.vertexSet().stream().filter(_function_1).findAny().orElse(null);
    final Predicate<Vertex> _function_2 = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        String _identifier = v.getIdentifier();
        return Objects.equal(_identifier, "system_img_source");
      }
    };
    Vertex d = model2.vertexSet().stream().filter(_function_2).findAny().orElse(null);
    final Predicate<Vertex> _function_3 = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        String _identifier = v.getIdentifier();
        return Objects.equal(_identifier, "Abs");
      }
    };
    Vertex f = model2.vertexSet().stream().filter(_function_3).findAny().orElse(null);
  }
}
