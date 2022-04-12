package strategy.c.rtos.freertos

import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import strategy.Strategy
import util.Name
import util.Save

class SDFChannelIncFreeRTOS implements Strategy{
	String root
	ForSyDeSystemGraph model	
	new(ForSyDeSystemGraph model) {
		this.model=model
	}
	
	override create() { 
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
	def String createHeader(Vertex vertex){
		val name = Name.name(vertex)
		var SDFChannel  channel = SDFChannel.enforce(vertex)
		var String tmp = name.toUpperCase();
		var token_size = channel.getTokenSizeInBits()
		'''
		#ifndef                   «tmp»_FREERTOS_H_
		#define                   «tmp»_FREERTOS_H_
		#include <stdlib.h>
		#include <stdint.h>	
		#include <stdio.h> 
		#include "FreeRTOS.h"
		#include "queue.h"
		#include "semphr.h"
		#include "timers.h"
		
		/*
		define token 
		*/
		typedef «IF token_size == 8»uint8_t«ELSEIF token_size==16»uint16_t«ELSEIF token_size==32»uin32_t«ELSE» uint32_t«ENDIF» token_«name» ;					
		
		#endif		
						
		'''
	}
	private def String path(Vertex vertex){
		return root+"/include/freertos_sdfchannel_"+Name.name(vertex)+".h"
	}
		
}