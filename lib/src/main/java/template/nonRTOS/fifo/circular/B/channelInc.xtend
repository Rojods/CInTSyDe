package template.nonRTOS.fifo.circular.B



import forsyde.io.java.core.Vertex
import forsyde.io.java.core.ForSyDeSystemGraph
import utils.Name
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.Set
import java.util.stream.Collectors
import utils.Save
import template.Template

class channelInc implements Template{
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
	

	def String inc(Vertex v) {
	
		Set<String> types	
		'''
			#ifndef                   CHANNELS_TYPES_H_
			#define                   CHANNELS_TYPES_H_
			
			#include <stdlib.h>
			#include <stdint.h>	
			#include <stdio.h> 
			#include "spinlock.h"
			«FOR type:types SEPARATOR " \n" AFTER "\n" »
			
				typedef struct 
				{
				    «type»* buffer;
				    size_t front;
				    size_t rear;
				size_t size;	    
				}circularFIFO_«type»;
								
				void init_circularFIFO_«type»(circularFIFO_«type»* channel ,«type»* buffer,size_t size);
				int read_circularFIFO_non_blocking_«type»(circularFIFO_«type»* channel, «type»* data);
				int write_circularFIFO_non_blocking_«type»(circularFIFO_«type»* channel, «type» value);
				int read_circularFIFO_blocking_«type»(circularFIFO_«type»* channel, «type»* data,spinlock* lock);
				int write_circularFIFO_blocking_«type»(circularFIFO_«type»* channel, «type» value,spinlock* lock);
			«ENDFOR»		
			#endif		
			
			
		'''		
	}
	private def String path(Vertex vertex){
		return root+"/include/sdfchannel_"+Name.name(vertex)+".h"
	}
	

	
}
