package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.drivers.ForSyDeModelHandler;
import generator.Generator;
import generator.InitProcessingModule;
import generator.SDFChannelProcessingModule;
import generator.SDFCombProcessingModule;
import generator.SubsystemMultiprocessorModule;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import template.baremetal.CircularFIFOTemplateInc;
import template.baremetal.CircularFIFOTemplateSrc;
import template.baremetal.DataDefinitionSrc;
import template.baremetal.DataTypeTemplateInc;
import template.baremetal.SDFChannelTemplateSrc;
import template.baremetal.SDFCombTemplateInc;
import template.baremetal.SDFCombTemplateSrc;
import template.baremetal.SpinLockTemplateInc;
import template.baremetal.SpinLockTemplateSrc;
import template.baremetal.multiprocessor.SubsystemTemplateIncMulti;
import template.baremetal.multiprocessor.SubsystemTemplateSrcMulti;

@SuppressWarnings("all")
public class demo2 {
  public static void main(final String[] args) {
    try {
      final String path = "forsyde-io/modified1/complete-mapped-sobel-model.forsyde.xmi";
      final String path2 = "forsyde-io/modified1/sobel-application.fiodl";
      final String root = "generateCode/c/multi";
      ForSyDeModelHandler loader = new ForSyDeModelHandler();
      ForSyDeSystemGraph model = loader.loadModel(path);
      model.mergeInPlace(loader.loadModel(path2));
      Generator gen = new Generator(model, root);
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
      SubsystemMultiprocessorModule subsystem = new SubsystemMultiprocessorModule();
      SubsystemTemplateSrcMulti _subsystemTemplateSrcMulti = new SubsystemTemplateSrcMulti();
      subsystem.add(_subsystemTemplateSrcMulti);
      SubsystemTemplateIncMulti _subsystemTemplateIncMulti = new SubsystemTemplateIncMulti();
      subsystem.add(_subsystemTemplateIncMulti);
      gen.add(subsystem);
      InitProcessingModule initModule = new InitProcessingModule();
      DataTypeTemplateInc _dataTypeTemplateInc = new DataTypeTemplateInc();
      initModule.add(_dataTypeTemplateInc);
      DataDefinitionSrc _dataDefinitionSrc = new DataDefinitionSrc();
      initModule.add(_dataDefinitionSrc);
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
