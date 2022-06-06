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
import template.baremetal_multi.DataTypeInc;
import template.baremetal_multi.DataTypeSrc;
import template.baremetal_multi.SDFActorInc;
import template.baremetal_multi.SDFActorSrc;
import template.baremetal_multi.SDFChannelInc;
import template.baremetal_multi.SDFChannelTemplateSrc;
import template.baremetal_multi.SpinLockTemplateInc;
import template.baremetal_multi.SpinLockTemplateSrc;
import template.baremetal_multi.SubsystemTemplateIncMulti;
import template.baremetal_multi.SubsystemTemplateSrcMulti;
import template.uniprocessor.fifo.fifo1.FIFOInc1;
import template.uniprocessor.fifo.fifo1.FIFOSrc1;
import template.uniprocessor.fifo.fifo2.FIFOInc2;
import template.uniprocessor.fifo.fifo2.FIFOSrc2;
import template.uniprocessor.fifo.fifo3.FIFOInc3;
import template.uniprocessor.fifo.fifo3.FIFOSrc3;

/**
 * multi cores
 */
@SuppressWarnings("all")
public class demo2 {
  public static void main(final String[] args) {
    try {
      final String path = "example1-2cores.fiodl";
      final String root = "generateCode/c/multi_example1";
      ForSyDeModelHandler loader = new ForSyDeModelHandler();
      ForSyDeSystemGraph model = loader.loadModel(path);
      Generator gen = new Generator(model, root);
      Generator.fifoType = 1;
      Generator.platform = 2;
      SDFChannelProcessingModule sdfchannelModule = new SDFChannelProcessingModule();
      SDFChannelTemplateSrc _sDFChannelTemplateSrc = new SDFChannelTemplateSrc();
      sdfchannelModule.add(_sDFChannelTemplateSrc);
      SDFChannelInc _sDFChannelInc = new SDFChannelInc();
      sdfchannelModule.add(_sDFChannelInc);
      gen.add(sdfchannelModule);
      SDFCombProcessingModule actorModule = new SDFCombProcessingModule();
      SDFActorSrc _sDFActorSrc = new SDFActorSrc();
      actorModule.add(_sDFActorSrc);
      SDFActorInc _sDFActorInc = new SDFActorInc();
      actorModule.add(_sDFActorInc);
      gen.add(actorModule);
      SubsystemMultiprocessorModule subsystem = new SubsystemMultiprocessorModule();
      SubsystemTemplateSrcMulti _subsystemTemplateSrcMulti = new SubsystemTemplateSrcMulti();
      subsystem.add(_subsystemTemplateSrcMulti);
      SubsystemTemplateIncMulti _subsystemTemplateIncMulti = new SubsystemTemplateIncMulti();
      subsystem.add(_subsystemTemplateIncMulti);
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
      gen.add(initModule);
      gen.create();
      InputOutput.<String>println("end!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
