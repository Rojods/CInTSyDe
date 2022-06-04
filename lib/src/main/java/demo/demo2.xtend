package demo

import forsyde.io.java.drivers.ForSyDeModelHandler
import generator.Generator
import generator.SDFChannelProcessingModule
import template.baremetal_multi.*
import generator.SDFCombProcessingModule
import generator.SubsystemMultiprocessorModule
import generator.InitProcessingModule

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
		
		
//		
//		val path = "b.forsyde.xmi"
//		val root = "generateCode/c/multi"
//		var loader = (new ForSyDeModelHandler)
//		var model = loader.loadModel(path)		
		
	
		val path = "test2.fiodl"
		val root = "generateCode/c/multi3"
		var loader = (new ForSyDeModelHandler)
		var model = loader.loadModel(path)		
		
		
		var Generator gen = new Generator(model, root)

		var sdfchannelModule = new SDFChannelProcessingModule
		sdfchannelModule.add(new SDFChannelTemplateSrc)
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
		initModule.add(new CircularFIFOTemplateInc)
		initModule.add(new CircularFIFOTemplateSrc)
		initModule.add(new SpinLockTemplateInc)
		initModule.add(new SpinLockTemplateSrc)
		initModule.add(new Config)
//		initModule.add(new SubsystemInitInc)
//		initModule.add(new SubsystemInitSrc)
		
		
		gen.add(initModule)

		gen.create()

		println("end!")
	}
}
