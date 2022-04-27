package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
import generator.Generator;
import generator.SDFCombProcessingModule;
import org.eclipse.xtext.xbase.lib.InputOutput;
import template.baremetal.SDFCombTemplateSrc;
import utils.Load;

@SuppressWarnings("all")
public class demo1 {
  public static void main(final String[] args) {
    final String path = "forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
    final String path2 = "forsyde-io\\sobel-application.fiodl";
    final String root = "generateCode\\c\\single";
    ForSyDeSystemGraph model = Load.load(path2);
    Generator gen = new Generator(model, root);
    SDFCombProcessingModule actorModule = new SDFCombProcessingModule();
    SDFCombTemplateSrc _sDFCombTemplateSrc = new SDFCombTemplateSrc();
    actorModule.add(_sDFCombTemplateSrc);
    gen.add(actorModule);
    gen.create();
    InputOutput.<String>println("end!");
  }
}
