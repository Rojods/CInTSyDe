package demo

import generator.Generator
import utils.Load
import forsyde.io.java.drivers.ForSyDeFiodlHandler
import generator.*
import template.rtos.*
import forsyde.io.java.drivers.ForSyDeModelHandler

/**
 * rtos
 */
class demo3 {
	def static void main(String[] args) {
		val path = "forsyde-io/modified1/complete-mapped-sobel-model.forsyde.xmi";
		val path2 = "forsyde-io/modified1/sobel-application.fiodl"
		val root = "generateCode/c/rtos"
		 //val roottest="D:\\Users\\LEGION\\Desktop\\Master Thesis\\code\\stm32-nucleo\\freertos_test1\\Core\\mycode"
		var loader = (new ForSyDeModelHandler)
		var model = loader.loadModel(path)
		model.mergeInPlace(loader.loadModel(path2))
		
		
		var Generator gen = new Generator(model, root)
		Generator.PC=0;
		Generator.NUCLEO=1;
		var initModule = new InitProcessingModule
		var actorModule = new SDFCombProcessingModule
		var sdfchannelModule = new SDFChannelProcessingModule
		var subsystem = new SubsystemUniprocessorModule

		/* init module */
		initModule.add(new ConfigRTOSInc)
		initModule.add(new SoftTimerTemplateSrc)
		initModule.add(new Channel)
		initModule.add(new StartTaskTemplateSrcRTOS)
		initModule.add(new StartTaskInc)
		initModule.add(new DataType)
		initModule.add(new DataDefinitionSrc)
		initModule.add(new ExternalDataBlockInc)
		initModule.add(new ExternalDataBlockSrc)
		initModule.add(new FireAllInc)
		initModule.add(new FireAllSrc)
		
		
		/* actor module */
		actorModule.add(new SDFCombTemplateSrcRTOS)
		actorModule.add(new SDFCombInc)
		

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
