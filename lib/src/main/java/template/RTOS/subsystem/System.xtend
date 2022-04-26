package template.RTOS.subsystem

import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import generator.generator
import java.util.stream.Collectors
import template.Template
import utils.Global
import utils.Save

class System implements Template {

	override create() {
		Save.save(pathsrc(),src())
		Save.save(pathinc(),inc())
	}
	

	
	def String src(){
		var sdfChannelSet = Global.model.vertexSet()
								.stream()
								.filter([v|SDFChannel.conforms(v)])
								.collect(Collectors.toSet())
		var sdfCombSet = Global.model.vertexSet()
								.stream()
								.filter([v|SDFComb.conforms(v)])
								.collect(Collectors.toSet())		
		'''
			
			#include "../include/system.h"
			#include "../include/freertos_StartTask.h"
			#include "../include/freertos_config.h"
			#include "FreeRTOS.h"
			#include "task.h"
			#include "semphr.h"
			#include "timers.h"
			/*******************
			*	StartTask stack*
			********************/
			extern StackType_t task_StartTask_stk[TASK_STACKSIZE]; 
			extern StaticTask_t tcb_start;
			
			int subsystem(){
			
			
			
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
	

	private def String pathsrc(){
		return generator.root+"/source/system.c"
	}	
	
	def String inc(){		
		'''
				
			#ifndef SYSTEM_FREERTOS_H_
			#define SYSTEM_FREERTOS_H_

				int subsystem();
			#endif		
			
			
			'''
	}
	

	private def String pathinc(){
		return generator.root+"/include/system.h"
	}	
}
