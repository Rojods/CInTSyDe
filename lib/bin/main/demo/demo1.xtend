package demo

import utils.Load
import generator.Generator
import generator.SDFChannelProcessingModule
import generator.SDFCombProcessingModule
import template.baremetal.SDFCombTemplateSrc

class demo1 {
	def static void main(String[] args) {
		val path="forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
		val path2="forsyde-io\\sobel-application.fiodl"
		val root="generateCode\\c\\single"
		var model = Load.load(path2);	
		var Generator gen = new Generator(model,root)
		
		var actorModule= new SDFCombProcessingModule
		actorModule.add(new SDFCombTemplateSrc)
		gen.add(actorModule)
		
		
		gen.create()
		
		println("end!")		
	}
}