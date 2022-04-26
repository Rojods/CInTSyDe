package template.nonRTOS.fifo.circular.A

import forsyde.io.java.core.Vertex
import generator.generator
import template.Template
import utils.Global
import utils.Name
import utils.Query
import utils.Save

class channelInc implements Template{

	new(){

	}
	
	override create(){
		Global.model.vertexSet()
					.stream()
					.filter([v|v.hasTrait("impl::TokenizableDataBlock")])
					.forEach([v|Save.save(path(v),v.inc())])			
			
	}	

	def String inc(Vertex vertex) {
		//println(vertex)
		val name = Name.name(vertex)
		var String tmp = name.toUpperCase();
		var token_size = Query.getTokenSizeInBits(vertex)
		
		'''
			#ifndef                   «tmp»_H_
			#define                   «tmp»_H_
			
			#include <stdlib.h>
			#include <stdint.h>	
			#include <stdio.h> 
			#include "spinlock.h"
			/*
			define token 
			*/
			typedef «IF token_size == 8»uint8_t«ELSEIF token_size==16»uint16_t«ELSEIF token_size==32»uint32_t«ELSEIF token_size==64»uint64_t«ELSE» uint64_t«ENDIF» token_«name» ;	
			
			/*
			this is a circular buffer for «name»
			*/
			typedef struct 
			{
			    token_«name»* buffer;
			    
			    /*
			    front: the position of the begining
			    */
			    size_t front;
			    
			    /*
			    rear: the position just after the last element
			    */
			    size_t rear;
			    
			    /*
				the size of this buffer
				 */
				size_t size;	    
			}circularFIFO_«name»;
			
			void init_circularFIFO_«name»(circularFIFO_«name»* channel ,token_«name»* buffer,size_t size);
			int read_circularFIFO_non_blocking_«name»(circularFIFO_«name»* channel, token_«name»* data);
			int write_circularFIFO_non_blocking_«name»(circularFIFO_«name»* channel, token_«name» value);
			int read_circularFIFO_blocking_«name»(circularFIFO_«name»* channel, token_«name»* data,spinlock* lock);
			int write_circularFIFO_blocking_«name»(circularFIFO_«name»* channel, token_«name» value,spinlock* lock);
						
			#endif		
			
			
		'''		
	}
	private def String path(Vertex vertex){
		return generator.root+"/inc/sdfchannel_"+Name.name(vertex)+".h"
	}
	

	
}
