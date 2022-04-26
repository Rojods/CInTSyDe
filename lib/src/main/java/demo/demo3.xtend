package demo
import utils.Load

import generator.generator
import template.RTOS.actor.*
import template.RTOS.channel.*
import template.RTOS.subsystem.*
/**
 * RTOS
 */
class demo3 {
	def static void main(String[] args) {
		val forsyde="forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
		//val forsyde="forsyde-io\\test1.forsyde.xmi"
		val root="generateCode\\c\\freertos"
		var model = Load.load(forsyde);	
		
		var gen = new generator(model,root)	
		gen.add(new actorInc() )
		gen.add(new actorSrc() )
		gen.add(new channelSrc() )
		gen.add(new channelInc() )
		gen.add(new Config())
		gen.add(new StartTaskInc())
		gen.add(new StartTaskSrc())
		gen.add(new System())
		gen.create()
		println("end!")
	}
	
	
}