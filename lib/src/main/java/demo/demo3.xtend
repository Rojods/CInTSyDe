package demo

import generator.Generator

import utils.Load
import forsyde.io.java.drivers.ForSyDeFiodlHandler
import generator.*
import template.rtos.*
import forsyde.io.java.drivers.ForSyDeModelHandler
import template.datatype.DataTypeInc
import template.datatype.DataTypeSrc
import processingModule.InitProcessingModule
import processingModule.SDFCombProcessingModule
import processingModule.SDFChannelProcessingModule
import processingModule.SubsystemUniprocessorModule

/**
 * rtos
 */
class demo3 {
	def static void main(String[] args) {
//		val path = "forsyde-io/modified1/complete-mapped-sobel-model.forsyde.xmi";
//		val path2 = "forsyde-io/modified1/sobel-application.fiodl"
//		val root = "generateCode/c/rtos2"
//		 //val roottest="D:\\Users\\LEGION\\Desktop\\Master Thesis\\code\\stm32-nucleo\\freertos_test1\\Core\\mycode"
//		var loader = (new ForSyDeModelHandler)
//		var model = loader.loadModel(path)
//		model.mergeInPlace(loader.loadModel(path2))
		
		
		/* testing example1.fiodl*/
		val path = "example1-2cores.fiodl"
		val root = "generateCode/c/rtos_example1"
		var loader = (new ForSyDeModelHandler)
		var model = loader.loadModel(path)				
		
		var Generator gen = new Generator(model, root)
		Generator.PC=0;
		Generator.NUCLEO=1;
		Generator.platform=3
		Generator.fifoType=1
		var initModule = new InitProcessingModule
		var actorModule = new SDFCombProcessingModule
		var sdfchannelModule = new SDFChannelProcessingModule
		var subsystem = new SubsystemUniprocessorModule

		/* init module */
		initModule.add(new ConfigRTOSInc)
		initModule.add(new SoftTimerSrc)
		initModule.add(new SDFChannelSrc)
		initModule.add(new StartTaskSrc)
		initModule.add(new StartTaskInc)
		
		initModule.add(new DataTypeInc)
		initModule.add(new DataTypeSrc)

		
		
		/* actor module */
		actorModule.add(new SDFActorSrc)
		actorModule.add(new SDFActorInc)
		

		/* channel module */
		/* subsystem module */
		gen.add(initModule)
		gen.add(actorModule)
		gen.add(sdfchannelModule)
		gen.add(subsystem)

		gen.create()

		println(" RTOS end!")
	}
}
