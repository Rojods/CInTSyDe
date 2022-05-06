package demo

import forsyde.io.java.drivers.ForSyDeModelHandler
import generator.Generator
import generator.InitProcessingModule
import generator.SDFChannelProcessingModule
import generator.SDFCombProcessingModule
import generator.SubsystemMultiprocessorModule
import template.baremetal.CircularFIFOTemplateInc
import template.baremetal.CircularFIFOTemplateSrc
import template.baremetal.DataDefinitionSrc
import template.baremetal.DataTypeTemplateInc
import template.baremetal.SDFChannelTemplateSrc
import template.baremetal.SDFCombTemplateInc
import template.baremetal.SDFCombTemplateSrc
import template.baremetal.SpinLockTemplateInc
import template.baremetal.SpinLockTemplateSrc
import template.baremetal.multiprocessor.SubsystemTemplateIncMulti
import template.baremetal.multiprocessor.SubsystemTemplateSrcMulti

class demo2 {
	def static void main(String[] args) {
		val path = "forsyde-io/modified1/complete-mapped-sobel-model.forsyde.xmi";
		val path2 = "forsyde-io/modified1/sobel-application.fiodl"
		val root = "generateCode/c/multi"
		var loader = (new ForSyDeModelHandler)
		var model = loader.loadModel(path)
		model.mergeInPlace(loader.loadModel(path2))
		var Generator gen = new Generator(model, root)

		var sdfchannelModule = new SDFChannelProcessingModule
		sdfchannelModule.add(new SDFChannelTemplateSrc)
		gen.add(sdfchannelModule)

		var actorModule = new SDFCombProcessingModule
		actorModule.add(new SDFCombTemplateSrc)
		actorModule.add(new SDFCombTemplateInc)
		gen.add(actorModule)

		var subsystem = new SubsystemMultiprocessorModule
		subsystem.add(new SubsystemTemplateSrcMulti)
		subsystem.add(new SubsystemTemplateIncMulti)
		gen.add(subsystem)

		var initModule = new InitProcessingModule
		initModule.add(new DataTypeTemplateInc)
		initModule.add(new DataDefinitionSrc)
		initModule.add(new CircularFIFOTemplateInc)
		initModule.add(new CircularFIFOTemplateSrc)
		initModule.add(new SpinLockTemplateInc)
		initModule.add(new SpinLockTemplateSrc)

		gen.add(initModule)

		gen.create()

		println("end!")
	}
}
