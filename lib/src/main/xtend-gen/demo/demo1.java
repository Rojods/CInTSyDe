package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.drivers.ForSyDeModelHandler;
import generator.Generator;
import generator.InitProcessingModule;
import generator.SDFChannelProcessingModule;
import generator.SDFCombProcessingModule;
import generator.SubsystemUniprocessorModule;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import template.baremetal.CircularFIFOTemplateInc;
import template.baremetal.CircularFIFOTemplateSrc;
import template.baremetal.Config;
import template.baremetal.DataDefinitionSrc;
import template.baremetal.DataTypeTemplateInc;
import template.baremetal.SDFChannelTemplateSrc;
import template.baremetal.SDFCombTemplateInc;
import template.baremetal.SDFCombTemplateSrc;
import template.baremetal.SpinLockTemplateInc;
import template.baremetal.SpinLockTemplateSrc;
import template.baremetal.uniprocessor.SubsystemInitInc;
import template.baremetal.uniprocessor.SubsystemInitSrc;
import template.baremetal.uniprocessor.SubsystemTemplateInc;
import template.baremetal.uniprocessor.SubsystemTemplateSrc;

/**
 * one core
 */
@SuppressWarnings("all")
public class demo1 {
  public static void main(final String[] args) {
    try {
      final String path = "forsyde-io/modified1/complete-mapped-sobel-model.forsyde.xmi";
      final String path2 = "forsyde-io/modified1/sobel-application.fiodl";
      final String root = "generateCode/c/single/single";
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
      SubsystemUniprocessorModule subsystem = new SubsystemUniprocessorModule();
      SubsystemTemplateSrc _subsystemTemplateSrc = new SubsystemTemplateSrc();
      subsystem.add(_subsystemTemplateSrc);
      SubsystemTemplateInc _subsystemTemplateInc = new SubsystemTemplateInc();
      subsystem.add(_subsystemTemplateInc);
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
      Config _config = new Config();
      initModule.add(_config);
      SubsystemInitInc _subsystemInitInc = new SubsystemInitInc();
      initModule.add(_subsystemInitInc);
      SubsystemInitSrc _subsystemInitSrc = new SubsystemInitSrc();
      initModule.add(_subsystemInitSrc);
      gen.add(initModule);
      gen.create();
      InputOutput.<String>println("end!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static void test(final String path) {
    try {
      String root = "generateCode/c/test1";
      ForSyDeModelHandler loader = new ForSyDeModelHandler();
      ForSyDeSystemGraph model = loader.loadModel(path);
      model.mergeInPlace(loader.loadModel(path));
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
      SubsystemUniprocessorModule subsystem = new SubsystemUniprocessorModule();
      SubsystemTemplateSrc _subsystemTemplateSrc = new SubsystemTemplateSrc();
      subsystem.add(_subsystemTemplateSrc);
      SubsystemTemplateInc _subsystemTemplateInc = new SubsystemTemplateInc();
      subsystem.add(_subsystemTemplateInc);
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
      Config _config = new Config();
      initModule.add(_config);
      SubsystemInitInc _subsystemInitInc = new SubsystemInitInc();
      initModule.add(_subsystemInitInc);
      SubsystemInitSrc _subsystemInitSrc = new SubsystemInitSrc();
      initModule.add(_subsystemInitSrc);
      gen.add(initModule);
      gen.create();
      InputOutput.<String>println("end!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
