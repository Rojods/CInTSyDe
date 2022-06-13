package demo2;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.drivers.ForSyDeModelHandler;
import generator.Generator;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import processingModule.InitProcessingModule;
import processingModule.SDFActorProcessingModule;
import processingModule.SDFChannelProcessingModule;
import processingModule.SubsystemUniprocessorModule;
import template.datatype.DataTypeInc;
import template.datatype.DataTypeSrc;
import template.fifo.fifo1.FIFOInc1;
import template.fifo.fifo1.FIFOSrc1;
import template.fifo.fifo2.FIFOInc2;
import template.fifo.fifo2.FIFOSrc2;
import template.uniprocessor.SDFChannel.SDFChannelInc;
import template.uniprocessor.SDFChannel.SDFChannelSrc;
import template.uniprocessor.actor.SDFActorInc;
import template.uniprocessor.actor.SDFActorSrc;
import template.uniprocessor.subsystem.SubsystemSingleInc;
import template.uniprocessor.subsystem.SubsystemSingleSrc;

/**
 * one core
 */
@SuppressWarnings("all")
public class single_fifo1 {
  public static void main(final String[] args) {
    try {
      final String path = "example2.fiodl";
      final String root = "generateCode/example2/single_fifo1/single";
      ForSyDeModelHandler loader = new ForSyDeModelHandler();
      ForSyDeSystemGraph model = loader.loadModel(path);
      Generator gen = new Generator(model, root);
      Generator.fifoType = 1;
      Generator.platform = 1;
      SDFChannelProcessingModule sdfchannelModule = new SDFChannelProcessingModule();
      SDFChannelSrc _sDFChannelSrc = new SDFChannelSrc();
      sdfchannelModule.add(_sDFChannelSrc);
      SDFChannelInc _sDFChannelInc = new SDFChannelInc();
      sdfchannelModule.add(_sDFChannelInc);
      gen.add(sdfchannelModule);
      SDFActorProcessingModule actorModule = new SDFActorProcessingModule();
      SDFActorSrc _sDFActorSrc = new SDFActorSrc();
      actorModule.add(_sDFActorSrc);
      SDFActorInc _sDFActorInc = new SDFActorInc();
      actorModule.add(_sDFActorInc);
      gen.add(actorModule);
      SubsystemUniprocessorModule subsystem = new SubsystemUniprocessorModule();
      SubsystemSingleSrc _subsystemSingleSrc = new SubsystemSingleSrc();
      subsystem.add(_subsystemSingleSrc);
      SubsystemSingleInc _subsystemSingleInc = new SubsystemSingleInc();
      subsystem.add(_subsystemSingleInc);
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
      gen.add(initModule);
      gen.create();
      InputOutput.<String>println("end!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
