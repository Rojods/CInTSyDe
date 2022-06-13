package demo2;

import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.drivers.ForSyDeModelHandler;
import generator.Generator;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import processingModule.InitProcessingModule;
import processingModule.SDFActorProcessingModule;
import processingModule.SDFChannelProcessingModule;
import processingModule.SubsystemMultiprocessorModule;
import template.baremetal_multi.SDFActorInc;
import template.baremetal_multi.SDFActorSrc;
import template.baremetal_multi.SDFChannelInc;
import template.baremetal_multi.SDFChannelSrc;
import template.baremetal_multi.SubsystemIncMulti;
import template.baremetal_multi.SubsystemSrcMulti;
import template.datatype.DataTypeInc;
import template.datatype.DataTypeSrc;
import template.fifo.fifo1.FIFOInc1;
import template.fifo.fifo1.FIFOSrc1;
import template.fifo.fifo2.FIFOInc2;
import template.fifo.fifo2.FIFOSrc2;

@SuppressWarnings("all")
public class multi_fifo1 {
  public static void main(final String[] args) {
    try {
      final String path = "example2.fiodl";
      final String root = "generateCode/example2/multi_fifo1";
      ForSyDeModelHandler loader = new ForSyDeModelHandler();
      ForSyDeSystemGraph model = loader.loadModel(path);
      Generator gen = new Generator(model, root);
      Generator.fifoType = 1;
      Generator.platform = 2;
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
      SubsystemMultiprocessorModule subsystem = new SubsystemMultiprocessorModule();
      SubsystemSrcMulti _subsystemSrcMulti = new SubsystemSrcMulti();
      subsystem.add(_subsystemSrcMulti);
      SubsystemIncMulti _subsystemIncMulti = new SubsystemIncMulti();
      subsystem.add(_subsystemIncMulti);
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
