package strategy.c.rtos.freertos
import strategy.Strategy

import strategy.SDFCombFather
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import util.Name
import java.util.stream.Collectors
import forsyde.io.java.core.EdgeTrait
import java.util.TreeSet
import static extension util.Query.getSDFChannelName
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import util.Save
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb

class SDFCombIncFreeRTOS  extends SDFCombFather implements Strategy{
	
	new(ForSyDeSystemGraph model) {
		super(model)
	}
	
	override create() {
		var set = model.vertexSet()
			.stream()
			.filter([v|SDFComb.conforms(v)]).collect(Collectors.toSet())
			
		for(Vertex v: set){
			Save.save(path(v),v.createHeader())
		}	
	}
	
	override setSaveRoot(String root) {
		this.root=root
	}
	
	def String createHeader(Vertex vertex){
		var name = Name.name(vertex)
		var tmp = name.toUpperCase()
		tmp = tmp+"_H_"	
		
		var sdfchannels=model.vertexSet()
							 .stream()
							 .filter([v|SDFChannel::conforms(v)])
							 .map([v|SDFChannel::enforce(v)])
							 .collect(Collectors.toSet())	
		//find input ports and output ports
		var out= model.outgoingEdgesOf(vertex).stream()
									.filter([edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE)])
									.map([e|e.getSourcePort().get()])
									.collect(Collectors.toSet())
		
		
		var in = model.incomingEdgesOf(vertex).stream()
									.filter([edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE)])
									.map([e|e.getTargetPort().get()])
									.collect(Collectors.toSet())

		
		var TreeSet<String> inputPorts =new TreeSet(in)
		var TreeSet<String> outputPorts = new TreeSet(out)	
		
		'''
			#ifndef  «tmp»
			#define «tmp»
			#include "freertos_config.h"
			#include "FreeRTOS.h"
			#include "task.h"
			#include "semphr.h"
			#include "timers.h"
			«FOR ch:sdfchannels SEPARATOR " \n" AFTER "\n" »
				#include "../include/freertos_sdfchannel_«Name.name(ch)».h"
			«ENDFOR»
			void task_«name»(void* pdata);

			void timer_«name»_callback(TimerHandle_t xTimer);
			#endif		
		'''			
		
			
	}
	private def String path(Vertex vertex){
		return root+"/include/freertos_sdfcomb_"+Name.name(vertex)+".h"
	}	
}
