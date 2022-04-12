package strategy.c.rtos.freertos

import strategy.Strategy


import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.stream.Collectors
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import util.Name
import util.Save
import util.Query
class ConfigFreeRTOS implements Strategy {
	String root
	ForSyDeSystemGraph model	
	new(ForSyDeSystemGraph model) {
		this.model=model
	}		
	override create() {
		Save.save(path(),config())
	}
	
	override setSaveRoot(String root) {
		this.root=root
	}
	def String config(){

		'''
		#ifndef CONFIG_H_
		#define CONFIG_H_
		//#include ""
		
		#define TASK_STACKSIZE 2048
		#endif
		'''		
	}
	private def String path(){
		return root+"/include/freertos_config.h"
	}		
}