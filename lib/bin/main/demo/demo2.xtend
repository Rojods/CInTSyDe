package demo
import forsyde.io.java.drivers.ForSyDeFiodlHandler

import generator.*

import template.baremetal.CircularFIFOTemplateInc
import template.baremetal.CircularFIFOTemplateSrc
import template.baremetal.DataTypeTemplateInc
import template.baremetal.SDFChannelTemplateSrc
import template.baremetal.SDFCombTemplateInc
import template.baremetal.SDFCombTemplateSrc
import template.baremetal.SpinLockTemplateInc
import template.baremetal.SpinLockTemplateSrc
import template.baremetal.multiprocessor.*
import utils.Load


class demo2 {
	def static void main(String[] args) {
		val path="forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
		val path2="forsyde-io\\sobel-application.fiodl"
		val root="generateCode\\c\\multi"
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
		
		
		var subsystem = new SubsystemMultiprocessorModule
		subsystem.add(new SubsystemTemplateSrcMulti)

		gen.add(subsystem)
		
		
		
		
		
		var initModule= new InitProcessingModule
		initModule.add(new DataTypeTemplateInc)
		initModule.add(new CircularFIFOTemplateInc)
		initModule.add(new CircularFIFOTemplateSrc)
		initModule.add(new SpinLockTemplateInc)
		initModule.add(new SpinLockTemplateSrc)
		
		
		
		
		
		gen.add(initModule)
		
		
		
		gen.create()
		
		println("end!")	
	}
}