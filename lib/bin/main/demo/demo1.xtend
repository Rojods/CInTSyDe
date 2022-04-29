package demo

import utils.Load

import generator.Generator
import generator.*
import template.baremetal.SDFCombTemplateSrc
import forsyde.io.java.drivers.ForSyDeFiodlHandler
import template.baremetal.*

class demo1 {
	def static void main(String[] args) {
		val path="forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
		val path2="forsyde-io\\sobel-application.fiodl"
		val root="generateCode\\c\\single"
		var model1 = Load.load(path)
		var model2 = (new ForSyDeFiodlHandler()).loadModel(path2)	
		
		model2.mergeInPlace(model1)
		
		var Generator gen = new Generator(model2,root)
		
		var actorModule= new SDFCombProcessingModule
		actorModule.add(new SDFCombTemplateSrc)
		actorModule.add(new SDFCombTemplateInc)
		gen.add(actorModule)
		
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