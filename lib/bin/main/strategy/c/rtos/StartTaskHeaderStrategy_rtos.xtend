package strategy.c.rtos
import strategy.Strategy

import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.stream.Collectors
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import util.Name
import util.Save
import util.Query

class StartTaskHeaderStrategy_rtos implements Strategy{
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
			«FOR channel :sdfChannelSet SEPARATOR "" AFTER"\n"  »
			#include "./include/rtos_sdfchannel_«Name.name(channel)».h"
			«ENDFOR»
			«FOR sdf :sdfCombSet SEPARATOR "" AFTER"\n"  »
			#include "./include/rtos_sdfcomb_«Name.name(sdf)».h"
			«ENDFOR»
			#include "./include/config.h";
			
			void StartTask(void* pdata);	
			#endif
		'''
	}
	

	private def String path(){
		return root+"/include/StartTask.h"
	}		
}