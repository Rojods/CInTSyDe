package demo

import forsyde.io.java.drivers.ForSyDeModelHandler
import generator.Generator

//import template.baremetal_multi.DataTypeInc
//import template.baremetal_multi.DataTypeSrc
import template.baremetal_multi.SDFActorInc
import template.baremetal_multi.SDFActorSrc
import template.baremetal_multi.SDFChannelInc
import template.baremetal_multi.SDFChannelTemplateSrc
//import template.baremetal_multi.SpinLockTemplateInc
//import template.baremetal_multi.SpinLockTemplateSrc
import template.baremetal_multi.SubsystemTemplateIncMulti
import template.baremetal_multi.SubsystemTemplateSrcMulti
import template.fifo.fifo1.FIFOInc1
import template.fifo.fifo1.FIFOSrc1
import template.fifo.fifo2.FIFOInc2
import template.fifo.fifo2.FIFOSrc2
import template.fifo.fifo3.FIFOInc3
import template.fifo.fifo3.FIFOSrc3

import static generator.Generator.*
import template.fifo.SpinLockTemplateInc
import template.fifo.SpinLockTemplateSrc
import template.datatype.DataTypeInc
import template.datatype.DataTypeSrc
import processingModule.SDFChannelProcessingModule
import processingModule.SDFCombProcessingModule
import processingModule.SubsystemMultiprocessorModule
import processingModule.InitProcessingModule

/**
 * multi cores
 */
class demo2 {
	def static void main(String[] args) {
//		val path = "forsyde-io/modified1/complete-mapped-sobel-model.forsyde.xmi";
//		val path2 = "forsyde-io/modified1/sobel-application.fiodl"
//		val root = "generateCode/c/multi"
//		var loader = (new ForSyDeModelHandler)
//		var model = loader.loadModel(path)
//		model.mergeInPlace(loader.loadModel(path2))
		
		
		/* testing example1.fiodl*/
		val path = "example1-2cores.fiodl"
		val root = "generateCode/c/multi_example1"
		var loader = (new ForSyDeModelHandler)
		var model = loader.loadModel(path)		
		
//		/* testing  test2.fiodl */
//		val path = "test2.fiodl"
//		val root = "generateCode/c/multi_test2"
//		var loader = (new ForSyDeModelHandler)
//		var model = loader.loadModel(path)		
		
		
		var Generator gen = new Generator(model, root)
		Generator.fifoType=1
		Generator.platform=2
		var sdfchannelModule = new SDFChannelProcessingModule
		sdfchannelModule.add(new SDFChannelTemplateSrc)
		sdfchannelModule.add(new SDFChannelInc)
	
		
		
		
		
		gen.add(sdfchannelModule)

		var actorModule = new SDFCombProcessingModule
		actorModule.add(new SDFActorSrc)
		actorModule.add(new SDFActorInc)
		gen.add(actorModule)

		var subsystem = new SubsystemMultiprocessorModule
		subsystem.add(new SubsystemTemplateSrcMulti)
		subsystem.add(new SubsystemTemplateIncMulti)

		
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
		if(Generator.fifoType==3){
			initModule.add(new FIFOInc3)
			initModule.add(new FIFOSrc3)
		}		
		
		
		initModule.add(new SpinLockTemplateInc)
		initModule.add(new SpinLockTemplateSrc)

		
		
		gen.add(initModule)

		gen.create()

		println("end!")
	}
}
