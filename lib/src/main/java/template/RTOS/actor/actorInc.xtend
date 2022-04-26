package template.RTOS.actor

import forsyde.io.java.core.EdgeTrait
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import java.util.TreeSet
import java.util.stream.Collectors
import utils.Name
import utils.Save
import template.Template
import utils.Global
import generator.generator
class actorInc  implements Template{
	

	
	override create() {
		var set = Global.model.vertexSet()
			.stream()
			.filter([v|SDFComb.conforms(v)]).collect(Collectors.toSet())
			
		for(Vertex v: set){
			Save.save(path(v),v.createHeader())
		}	
	}

	
	def String createHeader(Vertex vertex){
		var name = Name.name(vertex)
		var tmp = name.toUpperCase()
		tmp = tmp+"_H_"	
		
		var sdfchannels=Global.model.vertexSet()
							 .stream()
							 .filter([v|SDFChannel::conforms(v)])
							 .map([v|SDFChannel::enforce(v)])
							 .collect(Collectors.toSet())	
//		//find input ports and output ports
//		var out= Global.model.outgoingEdgesOf(vertex).stream()
//									.filter([edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE)])
//									.map([e|e.getSourcePort().get()])
//									.collect(Collectors.toSet())
//		
//		
//		var in = Global.model.incomingEdgesOf(vertex).stream()
//									.filter([edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE)])
//									.map([e|e.getTargetPort().get()])
//									.collect(Collectors.toSet())
//
//		
//		var TreeSet<String> inputPorts =new TreeSet(in)
//		var TreeSet<String> outputPorts = new TreeSet(out)	
		
		'''
			#ifndef  «tmp»
			#define «tmp»
			#include "FreeRTOS.h"
			#include "task.h"
			#include "semphr.h"
			#include "timers.h"
			void task_«name»(void* pdata);
			
			void timer_«name»_callback(TimerHandle_t xTimer);
			#endif		
		'''			
		
			
	}
	private def String path(Vertex vertex){
		return generator.root+"/include/freertos_sdfcomb_"+Name.name(vertex)+".h"
	}	
}
