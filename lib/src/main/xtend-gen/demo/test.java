package demo;

import com.google.common.base.Objects;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.eclipse.xtext.xbase.lib.InputOutput;
import utils.Load;

@SuppressWarnings("all")
public class test {
  public static void main(final String[] args) {
    final String path = "forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
    final String path2 = "forsyde-io\\sobel-application.fiodl";
    final String root = "generateCode\\c\\single";
    ForSyDeSystemGraph model1 = Load.load(path);
    ForSyDeSystemGraph model2 = Load.load(path2);
    model1.mergeInPlace(model2);
    final Predicate<Vertex> _function = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (SDFComb.conforms(v)).booleanValue();
      }
    };
    final Consumer<Vertex> _function_1 = new Consumer<Vertex>() {
      public void accept(final Vertex v) {
        InputOutput.<Vertex>println(v);
      }
    };
    model1.vertexSet().stream().filter(_function).forEach(_function_1);
    final Predicate<Vertex> _function_2 = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        return (SDFChannel.conforms(v)).booleanValue();
      }
    };
    final Predicate<Vertex> _function_3 = new Predicate<Vertex>() {
      public boolean test(final Vertex v) {
        String _identifier = v.getIdentifier();
        return Objects.equal(_identifier, "GrayScaleY");
      }
    };
    Vertex a = model1.vertexSet().stream().filter(_function_2).filter(_function_3).findAny().get();
  }
}
