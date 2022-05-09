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
import template.rtos.Channel;
import template.rtos.ConfigRTOSInc;
import template.rtos.DataDefinitionSrc;
import template.rtos.DataType;
import template.rtos.ExternalDataBlockInc;
import template.rtos.ExternalDataBlockSrc;
import template.rtos.FireAllInc;
import template.rtos.FireAllSrc;
import template.rtos.SDFCombInc;
import template.rtos.SDFCombTemplateSrcRTOS;
import template.rtos.SoftTimerTemplateSrc;
import template.rtos.StartTaskInc;
import template.rtos.StartTaskTemplateSrcRTOS;

/**
 * rtos
 */
@SuppressWarnings("all")
public class demo3 {
  public static void main(final String[] args) {
    try {
      final String path = "forsyde-io/modified1/complete-mapped-sobel-model.forsyde.xmi";
      final String path2 = "forsyde-io/modified1/sobel-application.fiodl";
      final String root = "generateCode/c/rtos";
      ForSyDeModelHandler loader = new ForSyDeModelHandler();
      ForSyDeSystemGraph model = loader.loadModel(path);
      model.mergeInPlace(loader.loadModel(path2));
      Generator gen = new Generator(model, root);
      Generator.PC = 0;
      Generator.NUCLEO = 1;
      InitProcessingModule initModule = new InitProcessingModule();
      SDFCombProcessingModule actorModule = new SDFCombProcessingModule();
      SDFChannelProcessingModule sdfchannelModule = new SDFChannelProcessingModule();
      SubsystemUniprocessorModule subsystem = new SubsystemUniprocessorModule();
      ConfigRTOSInc _configRTOSInc = new ConfigRTOSInc();
      initModule.add(_configRTOSInc);
      SoftTimerTemplateSrc _softTimerTemplateSrc = new SoftTimerTemplateSrc();
      initModule.add(_softTimerTemplateSrc);
      Channel _channel = new Channel();
      initModule.add(_channel);
      StartTaskTemplateSrcRTOS _startTaskTemplateSrcRTOS = new StartTaskTemplateSrcRTOS();
      initModule.add(_startTaskTemplateSrcRTOS);
      StartTaskInc _startTaskInc = new StartTaskInc();
      initModule.add(_startTaskInc);
      DataType _dataType = new DataType();
      initModule.add(_dataType);
      DataDefinitionSrc _dataDefinitionSrc = new DataDefinitionSrc();
      initModule.add(_dataDefinitionSrc);
      ExternalDataBlockInc _externalDataBlockInc = new ExternalDataBlockInc();
      initModule.add(_externalDataBlockInc);
      ExternalDataBlockSrc _externalDataBlockSrc = new ExternalDataBlockSrc();
      initModule.add(_externalDataBlockSrc);
      FireAllInc _fireAllInc = new FireAllInc();
      initModule.add(_fireAllInc);
      FireAllSrc _fireAllSrc = new FireAllSrc();
      initModule.add(_fireAllSrc);
      SDFCombTemplateSrcRTOS _sDFCombTemplateSrcRTOS = new SDFCombTemplateSrcRTOS();
      actorModule.add(_sDFCombTemplateSrcRTOS);
      SDFCombInc _sDFCombInc = new SDFCombInc();
      actorModule.add(_sDFCombInc);
      gen.add(initModule);
      gen.add(actorModule);
      gen.add(sdfchannelModule);
      gen.add(subsystem);
      gen.create();
      InputOutput.<String>println(" RTOS end!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
