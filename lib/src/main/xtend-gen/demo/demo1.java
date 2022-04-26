package demo;

import forsyde.io.java.core.ForSyDeSystemGraph;
import generator.generator;
import org.eclipse.xtext.xbase.lib.InputOutput;
import template.nonRTOS.actor.actorInc;
import template.nonRTOS.actor.actorSrc;
import template.nonRTOS.fifo.circular.A.channelInc;
import template.nonRTOS.fifo.circular.A.channelSrc;
import template.nonRTOS.spinlock.spinlock;
import template.nonRTOS.subsystem.configInc;
import template.nonRTOS.subsystem.subsystemIncUniprocessor;
import template.nonRTOS.subsystem.subsystemSrcUniprocessor;
import utils.Load;

/**
 * demo for uniprocessor
 */
@SuppressWarnings("all")
public class demo1 {
  public static void main(final String[] args) {
    final String forsyde = "forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
    final String root = "generateCode\\c\\single";
    ForSyDeSystemGraph model = Load.load(forsyde);
    generator gen = new generator(model, root);
    channelInc _channelInc = new channelInc();
    gen.add(_channelInc);
    InputOutput.<String>println("inc end");
    channelSrc _channelSrc = new channelSrc();
    gen.add(_channelSrc);
    spinlock _spinlock = new spinlock();
    gen.add(_spinlock);
    actorInc _actorInc = new actorInc();
    gen.add(_actorInc);
    actorSrc _actorSrc = new actorSrc();
    gen.add(_actorSrc);
    subsystemIncUniprocessor _subsystemIncUniprocessor = new subsystemIncUniprocessor();
    gen.add(_subsystemIncUniprocessor);
    subsystemSrcUniprocessor _subsystemSrcUniprocessor = new subsystemSrcUniprocessor();
    gen.add(_subsystemSrcUniprocessor);
    configInc _configInc = new configInc();
    gen.add(_configInc);
    gen.create();
    InputOutput.<String>println("end!");
  }
}
