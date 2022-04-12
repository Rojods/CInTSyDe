package demo

import factory.c.CFactory
import strategy.c.rtos.freertos.fooInc
import strategy.c.rtos.freertos.ConfigFreeRTOS
import strategy.c.rtos.freertos.MainFreeRTOS
import strategy.c.rtos.freertos.SDFChannelIncFreeRTOS
import strategy.c.rtos.freertos.SDFChannelSrcFreeRTOS
import strategy.c.rtos.freertos.SDFCombIncFreeRTOS
import strategy.c.rtos.freertos.SDFCombSrcFreeRTOS
import strategy.c.rtos.freertos.StartTaskIncFreeRTOS
import strategy.c.rtos.freertos.StartTaskSrcFreeRTOS
import util.Load
/**
 * freertos
 */
class demo4 {
	def static void main(String[] args) {
		println("freertos start!");
		val forsyde="forsyde-io\\sobel2mpsoc_mapped_no_outside_port.forsyde.xmi";
		var String root = "generatedCode\\c\\freertos";
		var model = Load.load(forsyde);
		println("freertos start!");
		var  f = new CFactory(root);
		f.add(new MainFreeRTOS(model));
		f.add(new fooInc(model));
		f.add(new SDFChannelIncFreeRTOS(model));
		f.add(new SDFChannelSrcFreeRTOS(model));
		f.add(new SDFCombIncFreeRTOS(model));
		f.add(new SDFCombSrcFreeRTOS(model));
		f.add(new ConfigFreeRTOS(model));
		f.add(new StartTaskSrcFreeRTOS(model));
		f.add(new StartTaskIncFreeRTOS(model));
		f.create();		
		println("freertos end!");
					
	}
}