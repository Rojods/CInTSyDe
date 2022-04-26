package template.RTOS.subsystem

import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import java.util.stream.Collectors
import template.Template
import utils.Global
import utils.Name
import utils.Save
import generator.generator
class StartTaskInc implements Template{

	override create() {
		Save.save(path(),createHeader())
	}	
	def String createHeader(){
		var sdfChannelSet = Global.model.vertexSet()
								.stream()
								.filter([v|SDFChannel.conforms(v)])
								.collect(Collectors.toSet())
		var sdfCombSet = Global.model.vertexSet()
								.stream()
								.filter([v|SDFComb.conforms(v)])
								.collect(Collectors.toSet())		
		'''
			#ifndef STARTTASK_H_
			#define STARTTASK_H_

			#include "FreeRTOS.h"
			#include "task.h"
			#include "semphr.h"
			#include "timers.h"
			
			void StartTask(void* pdata);	
			#endif
		'''
	}
	

	private def String path(){
		return generator.root+"/include/freertos_StartTask.h"
	}		
}
