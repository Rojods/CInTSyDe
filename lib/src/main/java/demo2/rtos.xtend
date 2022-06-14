package demo2

import generator.Generator

import utils.Load
import forsyde.io.java.drivers.ForSyDeFiodlHandler
import generator.*
import template.rtos.*
import forsyde.io.java.drivers.ForSyDeModelHandler
import template.datatype.DataTypeInc
import template.datatype.DataTypeSrc
import processingModule.InitProcessingModule
import processingModule.SDFChannelProcessingModule
import processingModule.SubsystemUniprocessorModule
import processingModule.SDFActorProcessingModule

/**
 * rtos
 */
class rtos {
	def static void main(String[] args) {

		
		/* testing example2.fiodl*/
		val path = "example2.fiodl"
		val root = "generateCode/example2/rtos/"
		var loader = (new ForSyDeModelHandler)
		var model = loader.loadModel(path)				
		
		var Generator gen = new Generator(model, root)


		Generator.platform=3  //3 is rtos
		//Generator.fifoType=1  fifotype does not matter in RTOS
		
		
		
		var initModule = new InitProcessingModule
		var actorModule = new SDFActorProcessingModule
		var sdfchannelModule = new SDFChannelProcessingModule


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
		//gen.add(subsystem)

		gen.create()

		println(" RTOS end!")
	}
}
