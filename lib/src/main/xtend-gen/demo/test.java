package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import java.util.HashMap;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.InputOutput;
import utils.Load;

@SuppressWarnings("all")
public class test {
  public static void main(final String[] args) {
    final String path = "forsyde-io\\test\\complete-mapped-sobel-model.forsyde.xmi";
    final String path2 = "forsyde-io\\test\\sobel-application.fiodl";
    final String root = "generateCode\\c\\single";
    ForSyDeSystemGraph model1 = Load.load(path);
    ForSyDeSystemGraph model2 = Load.load(path2);
    model1.mergeInPlace(model2);
    ForSyDeSystemGraph model = model1;
    Vertex a = model.queryVertex("GrayScaleY").get();
    SDFChannel sdf = SDFChannel.safeCast(a).get();
    Object _unwrap = sdf.getProperties().get("__initialTokenValues_ordering__").unwrap();
    HashMap<String, Integer> b = ((HashMap<String, Integer>) _unwrap);
    Set<String> c = b.keySet();
    InputOutput.<HashMap<String, Integer>>println(b);
  }
}
