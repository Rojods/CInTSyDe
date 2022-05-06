package demo

import forsyde.io.java.drivers.ForSyDeFiodlHandler


import generator.Generator
import generator.InitProcessingModule
import generator.SDFChannelProcessingModule

import template.baremetal.CircularFIFOTemplateInc
import template.baremetal.CircularFIFOTemplateSrc
import template.baremetal.DataTypeTemplateInc
import template.baremetal.SDFChannelTemplateSrc
import template.baremetal.SDFCombTemplateInc
import template.baremetal.SDFCombTemplateSrc
import template.baremetal.SpinLockTemplateInc
import template.baremetal.SpinLockTemplateSrc
import template.baremetal.uniprocessor.SubsystemTemplateInc
import template.baremetal.uniprocessor.SubsystemTemplateInc2
import template.baremetal.uniprocessor.SubsystemTemplateSrc
import utils.Load
import generator.SDFCombProcessingModule
import generator.SubsystemUniprocessorModule
import template.baremetal.DataDefinitionSrc
import template.baremetal.Config
import java.util.stream.Collectors
import template.baremetal.ExternalDataBlockInc
import template.baremetal.ExternalDataBlockSrc

class demo1 {
	def static void main(String[] args) {
		val path="forsyde-io/modified1/complete-mapped-sobel-model.forsyde.xmi";
		val path2="forsyde-io/modified1/sobel-application.fiodl"
		val root="generateCode/c/single"
		//val root2 = "D:\\Users\\LEGION\\Desktop\\Master Thesis\\code\\stm32-nucleo\\uniprocessor_test1\\Core\\mycode"
		var model1 = Load.load(path)
		var model2 = (new ForSyDeFiodlHandler()).loadModel(path2)	
		
		model2.mergeInPlace(model1)
		

		var Generator gen = new Generator(model2,root)
		
		
		var sdfchannelModule = new SDFChannelProcessingModule
		sdfchannelModule.add(new SDFChannelTemplateSrc)
		gen.add(sdfchannelModule)
		
		
		
		var actorModule= new SDFCombProcessingModule
		actorModule.add(new SDFCombTemplateSrc)
		actorModule.add(new SDFCombTemplateInc)
		gen.add(actorModule)
		
		
		var subsystem = new SubsystemUniprocessorModule
		subsystem.add(new SubsystemTemplateSrc)
		subsystem.add(new SubsystemTemplateInc2)
		subsystem.add(new SubsystemTemplateInc)
		gen.add(subsystem)
		
		
		
		
		
		var initModule= new InitProcessingModule
		initModule.add(new DataTypeTemplateInc)
		initModule.add(new DataDefinitionSrc)
		
		initModule.add(new CircularFIFOTemplateInc)
		initModule.add(new CircularFIFOTemplateSrc)
		initModule.add(new SpinLockTemplateInc)
		initModule.add(new SpinLockTemplateSrc)
		
		initModule.add(new Config)
		initModule.add(new ExternalDataBlockInc)
		initModule.add(new ExternalDataBlockSrc)
		
		
		
		
		gen.add(initModule)
		
		
		
		gen.create()
		
		println("end!")		
	}
}