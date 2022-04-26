package template.nonRTOS.fifo.circular.B

import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel

import utils.Name
import utils.Save
import template.Template

class SDFChannelHeader implements Template{
	ForSyDeSystemGraph model
	String root
	new(ForSyDeSystemGraph model){
		this.model=model
		//this.root = save
	}
	
	override create(){
		 model.vertexSet()
			.stream()
			.filter([v|SDFChannel::conforms(v)])
			.forEach([
				v| Save.save(path(v) ,v.inc())
			]
			)
			
							
					
		
	}	
	

	/**
	 * @param vertex	this vertex must have moc::sdf::sdfchannel trait
	 */
	def String inc(Vertex vertex) {
		//println(vertex)
		val name = Name.name(vertex)
		var SDFChannel  channel = SDFChannel.enforce(vertex)
		var String tmp = name.toUpperCase();
		var token_size = channel.getTokenSizeInBits()
		
		'''
			#ifndef                   «tmp»_H_
			#define                   «tmp»_H_
			
			#include <stdlib.h>
			#include <stdint.h>	
			#include <stdio.h> 
			
				typedef circularFIFO_«type»  circularFIFO_«name»;
			#endif		
			
			
		'''		
	}
	private def String path(Vertex vertex){
		return root+"/include/sdfchannel_"+Name.name(vertex)+".h"
	}
	

	
}
