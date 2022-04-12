package strategy.c.single


import strategy.Strategy
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.ForSyDeSystemGraph
import util.Name
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.Set
import java.util.stream.Collectors
import util.Save

class SDFChannelHeader implements Strategy{
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
				v| Save.save(path(v) ,v.createHeader())
			]
			)
			
							
					
		
	}	
	
	override setSaveRoot(String root) {
		this.root=root
	}
	/**
	 * @param vertex	this vertex must have moc::sdf::sdfchannel trait
	 */
	def String createHeader(Vertex vertex) {
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
				#include "spinlock.h"
				/*
				define token 
				*/
				typedef «IF token_size == 8»uint8_t«ELSEIF token_size==16»uint16_t«ELSEIF token_size==32»uint32_t«ELSEIF token_size==64»uint64_t«ELSE» uint64_t«ENDIF» token_«name» ;	
				
				/*
				struct circular_buf_«name»: this is a circular buffer for «name»
				*/
				typedef struct circular_buf_«name»
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
			
			   
				}* circularFIFO_«name»;
				
				/* 
					create a _circular
					channel_channelName
				*/
				circularFIFO_«name» init_circularFIFO_«name»(token_«name»* buffer,size_t size);
				
				
				void destroy_circularFIFO_«name»(circularFIFO_«name» channel);
				
				/* 
					read a token from channel.
					The token is channel «name»
			
				*/
				int read_circularFIFO_non_blocking_«name»(circularFIFO_«name» channel, token_«name»* data);
				
				/*
					write a token to _circular «name».
				*/
				int write_circularFIFO_non_blocking_«name»(circularFIFO_«name» channel, token_«name» value);


				/* 
					read a token from channel.
					The token is channel «name»
			
				*/
				int read_circularFIFO_blocking_«name»(circularFIFO_«name» channel, token_«name»* data,spinlock* lock);
				
				/*
					write a token to _circular «name».
				*/
				int write_circularFIFO_blocking_«name»(circularFIFO_«name» channel, token_«name» value,spinlock* lock);				
				#endif		
				
				
		'''		
	}
	private def String path(Vertex vertex){
		return root+"/include/sdfchannel_"+Name.name(vertex)+".h"
	}
	

	
}
