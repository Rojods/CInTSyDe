package strategy.c.rtos.freertos

import strategy.Strategy


import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.stream.Collectors
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import util.Name
import util.Save
import util.Query

class MainFreeRTOS implements Strategy {
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
		var sdfChannelSet = model.vertexSet()
								.stream()
								.filter([v|SDFChannel.conforms(v)])
								.collect(Collectors.toSet())
		var sdfCombSet = model.vertexSet()
								.stream()
								.filter([v|SDFComb.conforms(v)])
								.collect(Collectors.toSet())		
		'''
			
			#include "../include/foo.h"
			/*******************
			*	StartTask stack*
			********************/
			extern StackType_t task_StartTask_stk[TASK_STACKSIZE]; 
			extern StaticTask_t tcb_start;
			
			int system_entrance(){
			


				xTaskCreateStatic(StartTask,
							"init",
							TASK_STACKSIZE,
							NULL,
							configMAX_PRIORITIES-1,
							task_StartTask_stk,
							&tcb_start
							);
							
	
				
				
				vTaskStartScheduler();
				
				return 0;
			}		
		'''
	}
	

	private def String path(){
		return root+"/source/foo.c"
	}		
}