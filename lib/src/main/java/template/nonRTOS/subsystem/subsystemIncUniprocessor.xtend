package template.nonRTOS.subsystem


import generator.generator

import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.stream.Collectors
import utils.Name
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import utils.Save
import template.Template
import utils.Global

class subsystemIncUniprocessor implements Template {
	new(){

	}	
	
	override create() {
		var str = createHeader()
		Save.save(generator.root+"/inc/subsystem.h",str)
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
			#ifndef SYSTEM_H_
			#define SYSTEM_H_
			
			«FOR channel :sdfChannelSet SEPARATOR "" AFTER"\n"  »
				#include "../inc/sdfchannel_«Name.name(channel)».h"
			«ENDFOR»
			
			«FOR sdf :sdfCombSet SEPARATOR "" AFTER"\n"  »
				#include "../inc/sdfcomb_«Name.name(sdf)».h"
			«ENDFOR»	
			
			//system input and system output
			void subsystem();
			
			#endif
		'''
	}



	
}
