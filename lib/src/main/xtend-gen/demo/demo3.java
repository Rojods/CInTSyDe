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
import template.rtos.SDFCombTemplateSrcRTOS;
import template.rtos.StartTaskTemplateSrcRTOS;
import utils.Load;

@SuppressWarnings("all")
public class demo3 {
  public static void main(final String[] args) {
    try {
      final String path = "forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
      final String path2 = "forsyde-io\\sobel-application.fiodl";
      final String root = "generateCode\\c\\rtos";
      ForSyDeSystemGraph model1 = Load.load(path);
      ForSyDeSystemGraph model2 = new ForSyDeFiodlHandler().loadModel(path2);
      model2.mergeInPlace(model1);
      Generator gen = new Generator(model2, root);
      InitProcessingModule initModule = new InitProcessingModule();
      SDFCombProcessingModule actorModule = new SDFCombProcessingModule();
      SDFChannelProcessingModule sdfchannelModule = new SDFChannelProcessingModule();
      SubsystemUniprocessorModule subsystem = new SubsystemUniprocessorModule();
      ConfigRTOSInc _configRTOSInc = new ConfigRTOSInc();
      initModule.add(_configRTOSInc);
      Channel _channel = new Channel();
      initModule.add(_channel);
      StartTaskTemplateSrcRTOS _startTaskTemplateSrcRTOS = new StartTaskTemplateSrcRTOS();
      initModule.add(_startTaskTemplateSrcRTOS);
      SDFCombTemplateSrcRTOS _sDFCombTemplateSrcRTOS = new SDFCombTemplateSrcRTOS();
      actorModule.add(_sDFCombTemplateSrcRTOS);
      gen.add(initModule);
      gen.add(actorModule);
      gen.add(sdfchannelModule);
      gen.add(subsystem);
      gen.create();
      InputOutput.<String>println("end!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
