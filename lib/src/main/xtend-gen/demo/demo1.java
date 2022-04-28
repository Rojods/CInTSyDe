package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.drivers.ForSyDeFiodlHandler;
import generator.Generator;
import generator.InitProcessingModule;
import generator.SDFCombProcessingModule;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import template.baremetal.DataTypeTemplateInc;
import template.baremetal.SDFCombTemplateInc;
import template.baremetal.SDFCombTemplateSrc;

@SuppressWarnings("all")
public class demo1 {
  public static void main(final String[] args) {
    try {
      final String path = "forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
      final String path2 = "forsyde-io\\sobel-application.fiodl";
      final String root = "generateCode\\c\\single";
      ForSyDeSystemGraph model2 = new ForSyDeFiodlHandler().loadModel(path2);
      Generator gen = new Generator(model2, root);
      SDFCombProcessingModule actorModule = new SDFCombProcessingModule();
      SDFCombTemplateSrc _sDFCombTemplateSrc = new SDFCombTemplateSrc();
      actorModule.add(_sDFCombTemplateSrc);
      SDFCombTemplateInc _sDFCombTemplateInc = new SDFCombTemplateInc();
      actorModule.add(_sDFCombTemplateInc);
      gen.add(actorModule);
      InitProcessingModule initModule = new InitProcessingModule();
      DataTypeTemplateInc _dataTypeTemplateInc = new DataTypeTemplateInc();
      initModule.add(_dataTypeTemplateInc);
      gen.add(initModule);
      gen.create();
      InputOutput.<String>println("end!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
