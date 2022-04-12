package strategy.c.rtos.freertos
import strategy.Strategy

import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.stream.Collectors
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import util.Name
import util.Save
import util.Query

class StartTaskIncFreeRTOS implements Strategy{
	String root
	ForSyDeSystemGraph model	
	new(ForSyDeSystemGraph model) {
		this.model=model
	}
	
	override create() {
		Save.save(path(),createHeader())
	}
	override setSaveRoot(String root) {
		this.root=root
	}
	
	def String createHeader(){
		var sdfChannelSet = model.vertexSet()
								.stream()
								.filter([v|SDFChannel.conforms(v)])
								.collect(Collectors.toSet())
		var sdfCombSet = model.vertexSet()
								.stream()
								.filter([v|SDFComb.conforms(v)])
								.collect(Collectors.toSet())		
		'''
			#ifndef STARTTASK_H_
			#define STARTTASK_H_
			#include "FreeRTOS.h"
			#include "task.h"
			#include "queue.h"
			#include "semphr.h"
			#include "timers.h"
			«FOR channel :sdfChannelSet SEPARATOR "" AFTER"\n"  »
			#include "../include/freertos_sdfchannel_«Name.name(channel)».h"
			«ENDFOR»
			«FOR sdf :sdfCombSet SEPARATOR "" AFTER"\n"  »
			#include "../include/freertos_sdfcomb_«Name.name(sdf)».h"
			«ENDFOR»
			#include "../include/freertos_config.h"
			
			void StartTask(void* pdata);	
			#endif
		'''
	}
	

	private def String path(){
		return root+"/include/freertos_StartTask.h"
	}		
}