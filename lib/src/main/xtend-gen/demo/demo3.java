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
import template.rtos.Channel;
import template.rtos.ConfigRTOSInc;
import template.rtos.DataDefinitionSrc;
import template.rtos.DataType;
import template.rtos.SDFCombInc;
import template.rtos.SDFCombTemplateSrcRTOS;
import template.rtos.SoftTimerTemplateSrc;
import template.rtos.StartTaskInc;
import template.rtos.StartTaskTemplateSrcRTOS;
import utils.Load;

/**
 * rtos
 */
@SuppressWarnings("all")
public class demo3 {
  public static void main(final String[] args) {
    try {
      final String path = "forsyde-io/test/complete-mapped-sobel-model.forsyde.xmi";
      final String path2 = "forsyde-io/test/sobel-application.fiodl";
      final String root = "generateCode/c/rtos";
      final String roottest = "D:\\Users\\LEGION\\Desktop\\Master Thesis\\code\\stm32-nucleo\\freertos_test1\\Core\\mycode";
      ForSyDeSystemGraph model1 = Load.load(path);
      ForSyDeSystemGraph model2 = new ForSyDeFiodlHandler().loadModel(path2);
      model2.mergeInPlace(model1);
      Generator gen = new Generator(model2, roottest);
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
