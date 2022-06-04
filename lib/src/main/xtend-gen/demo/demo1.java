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
import template.uniprocessor.SDFChannel.SDFChannelTemplateSrc;
import template.uniprocessor.actor.SDFActorInc;
import template.uniprocessor.actor.SDFActorSrc;
import template.uniprocessor.fifo.SpinLockTemplateInc;
import template.uniprocessor.fifo.SpinLockTemplateSrc;
import template.uniprocessor.fifo.fifo1.FIFOInc1;
import template.uniprocessor.fifo.fifo1.FIFOSrc1;
import template.uniprocessor.fifo.fifo2.FIFOInc2;
import template.uniprocessor.fifo.fifo2.FIFOSrc2;
import template.uniprocessor.fifo.fifo3.FIFOInc3;
import template.uniprocessor.fifo.fifo3.FIFOSrc3;
import template.uniprocessor.others.Config;
import template.uniprocessor.others.DataTypeInc;
import template.uniprocessor.others.DataTypeSrc;
import template.uniprocessor.subsystem.SubsystemTemplateInc;
import template.uniprocessor.subsystem.SubsystemTemplateSrc;

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
      Generator.fifoType = 2;
      SDFChannelProcessingModule sdfchannelModule = new SDFChannelProcessingModule();
      SDFChannelTemplateSrc _sDFChannelTemplateSrc = new SDFChannelTemplateSrc();
      sdfchannelModule.add(_sDFChannelTemplateSrc);
      gen.add(sdfchannelModule);
      SDFCombProcessingModule actorModule = new SDFCombProcessingModule();
      SDFActorSrc _sDFActorSrc = new SDFActorSrc();
      actorModule.add(_sDFActorSrc);
      SDFActorInc _sDFActorInc = new SDFActorInc();
      actorModule.add(_sDFActorInc);
      gen.add(actorModule);
      SubsystemUniprocessorModule subsystem = new SubsystemUniprocessorModule();
      SubsystemTemplateSrc _subsystemTemplateSrc = new SubsystemTemplateSrc();
      subsystem.add(_subsystemTemplateSrc);
      SubsystemTemplateInc _subsystemTemplateInc = new SubsystemTemplateInc();
      subsystem.add(_subsystemTemplateInc);
      gen.add(subsystem);
      InitProcessingModule initModule = new InitProcessingModule();
      DataTypeInc _dataTypeInc = new DataTypeInc();
      initModule.add(_dataTypeInc);
      DataTypeSrc _dataTypeSrc = new DataTypeSrc();
      initModule.add(_dataTypeSrc);
      if ((Generator.fifoType == 1)) {
        FIFOInc1 _fIFOInc1 = new FIFOInc1();
        initModule.add(_fIFOInc1);
        FIFOSrc1 _fIFOSrc1 = new FIFOSrc1();
        initModule.add(_fIFOSrc1);
      }
      if ((Generator.fifoType == 2)) {
        FIFOInc2 _fIFOInc2 = new FIFOInc2();
        initModule.add(_fIFOInc2);
        FIFOSrc2 _fIFOSrc2 = new FIFOSrc2();
        initModule.add(_fIFOSrc2);
      }
      if ((Generator.fifoType == 3)) {
        FIFOInc3 _fIFOInc3 = new FIFOInc3();
        initModule.add(_fIFOInc3);
        FIFOSrc3 _fIFOSrc3 = new FIFOSrc3();
        initModule.add(_fIFOSrc3);
      }
      SpinLockTemplateInc _spinLockTemplateInc = new SpinLockTemplateInc();
      initModule.add(_spinLockTemplateInc);
      SpinLockTemplateSrc _spinLockTemplateSrc = new SpinLockTemplateSrc();
      initModule.add(_spinLockTemplateSrc);
      Config _config = new Config();
      initModule.add(_config);
      gen.add(initModule);
      gen.create();
      InputOutput.<String>println("end!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
