package strategy.c.rtos.freertos

import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import java.util.stream.Collectors
import strategy.Strategy
import util.Save

class fooInc implements Strategy{
		String root
	ForSyDeSystemGraph model	
	new(ForSyDeSystemGraph model) {
		this.model=model
	}	
	override create() {
		Save.save(path(),main())
	}
	
	override setSaveRoot(String root) {
		this.root=root
	}
	
	def String main(){		
		'''
			
		#ifndef FOO_H_
		#define FOO_H_
		#include "../include/freertos_StartTask.h"
		#include "../include/freertos_config.h"
		#include "FreeRTOS.h"
		#include "task.h"
		#include "semphr.h"
		#include "timers.h"
		int system();
		#endif		
	
	
		'''
	}
	

	private def String path(){
		return root+"/include/foo.h"
	}
}