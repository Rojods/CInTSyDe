package demo2

import forsyde.io.java.drivers.ForSyDeModelHandler
import generator.Generator
import processingModule.InitProcessingModule
import processingModule.SDFChannelProcessingModule
import processingModule.SDFCombProcessingModule
import processingModule.SubsystemUniprocessorModule
import template.datatype.DataTypeInc
import template.datatype.DataTypeSrc
import template.fifo.fifo1.FIFOInc1
import template.fifo.fifo1.FIFOSrc1
import template.fifo.fifo2.FIFOInc2
import template.fifo.fifo2.FIFOSrc2
import template.uniprocessor.SDFChannel.SDFChannelInc
import template.uniprocessor.SDFChannel.SDFChannelSrc
import template.uniprocessor.actor.SDFActorInc
import template.uniprocessor.actor.SDFActorSrc
import template.uniprocessor.subsystem.SubsystemTemplateInc
import template.uniprocessor.subsystem.SubsystemTemplateSrc

import static generator.Generator.*

/**
 * one core
 */
class single_fifo1 {
	def static void main(String[] args) {

		/* testing example2.fiodl    fifo1*/
		val path = "example2.fiodl"
		val root="generateCode/example2/single_fifo1/single"
		var loader = (new ForSyDeModelHandler)
		var model = loader.loadModel(path)				
		var Generator gen = new Generator(model, root)
		
		
		Generator.fifoType=1
		Generator.platform=1 //1 is uniprocessor, 2 is multi, 3 is rtos
		 
		var sdfchannelModule = new SDFChannelProcessingModule
		sdfchannelModule.add(new SDFChannelSrc)
		sdfchannelModule.add(new SDFChannelInc)
		
		gen.add(sdfchannelModule)

		var actorModule = new SDFCombProcessingModule
		actorModule.add(new SDFActorSrc)
		actorModule.add(new SDFActorInc)
		gen.add(actorModule)

		var subsystem = new SubsystemUniprocessorModule
		subsystem.add(new SubsystemTemplateSrc)
		
		subsystem.add(new SubsystemTemplateInc)
		gen.add(subsystem)

		var initModule = new InitProcessingModule
		initModule.add(new DataTypeInc)
		initModule.add(new DataTypeSrc)

		if(Generator.fifoType==1){
			initModule.add(new FIFOInc1)
			initModule.add(new FIFOSrc1)
		}
		
		
		if(Generator.fifoType==2){
			initModule.add(new FIFOInc2)
			initModule.add(new FIFOSrc2)
		}


		
		//initModule.add(new SpinLockTemplateInc)
		//initModule.add(new SpinLockTemplateSrc)

		

		
		gen.add(initModule)

		gen.create()

		println("end!")
	}

}
