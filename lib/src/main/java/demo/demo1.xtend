package demo

import generator.generator
import template.nonRTOS.actor.actorInc
import template.nonRTOS.actor.actorSrc
import template.nonRTOS.fifo.circular.A.channelInc
import template.nonRTOS.fifo.circular.A.channelSrc
import template.nonRTOS.spinlock.spinlock
import template.nonRTOS.subsystem.configInc
import template.nonRTOS.subsystem.subsystemIncUniprocessor
import template.nonRTOS.subsystem.subsystemSrcUniprocessor
import utils.Load

/**
 * demo for uniprocessor
 */
class demo1 {
	def static void main(String[] args) {
		val forsyde="forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
		
		val root="generateCode\\c\\single"
		var model = Load.load(forsyde);	
		
		var gen = new generator(model,root)
		
		gen.add(new channelInc())
		println("inc end")
		gen.add(new channelSrc())
		gen.add(new spinlock())
		
		gen.add(new actorInc())
		gen.add(new actorSrc())
		
		gen.add(new subsystemIncUniprocessor())
		gen.add(new subsystemSrcUniprocessor())
		gen.add(new configInc())
		
		gen.create()
		
		println("end!")
	}
}