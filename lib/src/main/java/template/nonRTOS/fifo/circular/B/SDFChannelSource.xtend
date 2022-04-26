package template.nonRTOS.fifo.circular.B


import forsyde.io.java.core.Vertex
import forsyde.io.java.core.ForSyDeSystemGraph
import utils.Name
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import utils.Save
import template.Template

class SDFChannelSource implements Template{
	ForSyDeSystemGraph model
	String root
	new(ForSyDeSystemGraph model){
		this.model=model
	}
	
	override create(){
		 model.vertexSet()
			.stream()
			.filter([v|SDFChannel::conforms(v)])
			.forEach([
				v| Save.save(path(v) ,v.createSource())
			]
			)		
	}
	

	def String createSource(Vertex vertex) {
		var name = vertex.getIdentifier()
		'''
			
			#include "../include/sdfchannel_«name.replace("/","_")».h"
			#include <stdio.h>
			#include "spinlock"
			 
			spinlock spinlock_«name»={.flag=0};
			«type» buffer_«name»[«size»];
			circularFIFO_«name» channel_«name»;
			
			
		'''
	}
	
	private def String path(Vertex vertex){
		return root+"/source/sdfchannel_"+Name.name(vertex)+".c"
	}
	
}
