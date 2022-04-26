package template.RTOS.subsystem

import generator.generator
import template.Template
import utils.Save

class Config implements Template{

	override create() {
		Save.save(path(),config())
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
		return generator.root+"/include/freertos_config.h"
	}		
}
