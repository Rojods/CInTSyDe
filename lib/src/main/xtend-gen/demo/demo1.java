package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.drivers.ForSyDeFiodlHandler;
import generator.Generator;
import generator.InitProcessingModule;
import generator.SDFChannelProcessingModule;
import generator.SDFCombProcessingModule;
import generator.SubsystemUniprocessorModule;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import template.baremetal.CircularFIFOTemplateInc;
import template.baremetal.CircularFIFOTemplateSrc;
import template.baremetal.DataTypeTemplateInc;
import template.baremetal.SDFChannelTemplateSrc;
import template.baremetal.SDFCombTemplateInc;
import template.baremetal.SDFCombTemplateSrc;
import template.baremetal.SpinLockTemplateInc;
import template.baremetal.SpinLockTemplateSrc;
import template.baremetal.uniprocessor.SubsystemTemplateInc;
import template.baremetal.uniprocessor.SubsystemTemplateInc2;
import template.baremetal.uniprocessor.SubsystemTemplateSrc;
import utils.Load;

@SuppressWarnings("all")
public class demo1 {
  public static void main(final String[] args) {
    try {
      final String path = "forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
      final String path2 = "forsyde-io\\sobel-application.fiodl";
      final String root = "generateCode\\c\\single";
      ForSyDeSystemGraph model1 = Load.load(path);
      ForSyDeSystemGraph model2 = new ForSyDeFiodlHandler().loadModel(path2);
      model2.mergeInPlace(model1);
      Generator gen = new Generator(model2, root);
      SDFChannelProcessingModule sdfchannelModule = new SDFChannelProcessingModule();
      SDFChannelTemplateSrc _sDFChannelTemplateSrc = new SDFChannelTemplateSrc();
      sdfchannelModule.add(_sDFChannelTemplateSrc);
      gen.add(sdfchannelModule);
      SDFCombProcessingModule actorModule = new SDFCombProcessingModule();
      SDFCombTemplateSrc _sDFCombTemplateSrc = new SDFCombTemplateSrc();
      actorModule.add(_sDFCombTemplateSrc);
      SDFCombTemplateInc _sDFCombTemplateInc = new SDFCombTemplateInc();
      actorModule.add(_sDFCombTemplateInc);
      gen.add(actorModule);
      SubsystemUniprocessorModule subsystem = new SubsystemUniprocessorModule();
      SubsystemTemplateSrc _subsystemTemplateSrc = new SubsystemTemplateSrc();
      subsystem.add(_subsystemTemplateSrc);
      SubsystemTemplateInc2 _subsystemTemplateInc2 = new SubsystemTemplateInc2();
      subsystem.add(_subsystemTemplateInc2);
      SubsystemTemplateInc _subsystemTemplateInc = new SubsystemTemplateInc();
      subsystem.add(_subsystemTemplateInc);
      gen.add(subsystem);
      InitProcessingModule initModule = new InitProcessingModule();
      DataTypeTemplateInc _dataTypeTemplateInc = new DataTypeTemplateInc();
      initModule.add(_dataTypeTemplateInc);
      CircularFIFOTemplateInc _circularFIFOTemplateInc = new CircularFIFOTemplateInc();
      initModule.add(_circularFIFOTemplateInc);
      CircularFIFOTemplateSrc _circularFIFOTemplateSrc = new CircularFIFOTemplateSrc();
      initModule.add(_circularFIFOTemplateSrc);
      SpinLockTemplateInc _spinLockTemplateInc = new SpinLockTemplateInc();
      initModule.add(_spinLockTemplateInc);
      SpinLockTemplateSrc _spinLockTemplateSrc = new SpinLockTemplateSrc();
      initModule.add(_spinLockTemplateSrc);
      gen.add(initModule);
      gen.create();
      InputOutput.<String>println("end!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
