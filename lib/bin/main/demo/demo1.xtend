package demo


import forsyde.io.java.drivers.ForSyDeModelHandler
import generator.Generator
import generator.InitProcessingModule
import generator.SDFChannelProcessingModule
import generator.SDFCombProcessingModule
import generator.SubsystemUniprocessorModule
import template.baremetal_single.*

/**
 * one core
 */
class demo1 {
	def static void main(String[] args) {
		val path = "forsyde-io/modified1/complete-mapped-sobel-model.forsyde.xmi";
		val path2 = "forsyde-io/modified1/sobel-application.fiodl"
		val root = "generateCode/c/single/single"

		var loader = (new ForSyDeModelHandler)
		var model = loader.loadModel(path)
		model.mergeInPlace(loader.loadModel(path2))
		var Generator gen = new Generator(model, root)

		var sdfchannelModule = new SDFChannelProcessingModule
		sdfchannelModule.add(new SDFChannelTemplateSrc)
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

		initModule.add(new CircularFIFOTemplateInc)
		initModule.add(new CircularFIFOTemplateSrc)
		initModule.add(new SpinLockTemplateInc)
		initModule.add(new SpinLockTemplateSrc)
		initModule.add(new Config)
		
		//initModule.add(new SubsystemInitInc)
		//initModule.add(new SubsystemInitSrc)
		
		gen.add(initModule)

		gen.create()

		println("end!")
	}
	
	def static void test(String path){
		
		var root = "generateCode/c/test1"
		var loader = (new ForSyDeModelHandler)
		var model = loader.loadModel(path)
		
		
		
		model.mergeInPlace(loader.loadModel(path))
		var Generator gen = new Generator(model, root)

		var sdfchannelModule = new SDFChannelProcessingModule
		sdfchannelModule.add(new SDFChannelTemplateSrc)
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

		initModule.add(new CircularFIFOTemplateInc)
		initModule.add(new CircularFIFOTemplateSrc)
		initModule.add(new SpinLockTemplateInc)
		initModule.add(new SpinLockTemplateSrc)
		initModule.add(new Config)
		
		initModule.add(new SubsystemInitInc)
		initModule.add(new SubsystemInitSrc)
		
		gen.add(initModule)

		gen.create()

		println("end!")		
	}
}
