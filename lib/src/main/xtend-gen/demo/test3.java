package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtext.xbase.lib.InputOutput;
import utils.Load;
import utils.Query;

@SuppressWarnings("all")
public class test3 {
  public static void main(final String[] args) {
    final String path1 = "forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
    final String path2 = "forsyde-io\\sobel-application.fiodl";
    final String root = "generateCode\\c\\single";
    ForSyDeSystemGraph model1 = Load.load(path1);
    ForSyDeSystemGraph model2 = Load.load(path2);
    model2.mergeInPlace(model1);
    final ForSyDeSystemGraph model = model2;
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (SDFChannel.conforms(v)).booleanValue();
      }
    };
    Set<Vertex> sdfs = model.vertexSet().stream().filter(_function).collect(Collectors.<Vertex>toSet());
    final Function<Vertex, String> _function_1 = new Function<Vertex, String>() {
      public String apply(final Vertex sdf) {
        return Query.findSDFChannelDataType(model, sdf);
      }
    };
    Set<String> type = sdfs.stream().<String>map(_function_1).collect(Collectors.<String>toSet());
    for (final String s : type) {
      InputOutput.<String>println(s);
    }
    Query.findVertexByName(model, "");
  }
}
