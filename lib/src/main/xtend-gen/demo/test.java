package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
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
  }
}
