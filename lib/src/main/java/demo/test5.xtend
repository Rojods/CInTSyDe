package demo
import forsyde.io.java.drivers.ForSyDeModelHandler

import generator.Generator
import generator.InitProcessingModule
import generator.SDFChannelProcessingModule
import generator.SDFCombProcessingModule
import generator.SubsystemUniprocessorModule
import template.baremetal_single.*
import utils.Query

class test5 {
	def static void main(String[] args) {
		val path = "example2.fiodl"
		val root = "generateCode/c/single2"
		var loader = (new ForSyDeModelHandler)
		var model = loader.loadModel(path)		
		
		var a = model.queryVertex("p1impl").get()	
		println("")
	}
}