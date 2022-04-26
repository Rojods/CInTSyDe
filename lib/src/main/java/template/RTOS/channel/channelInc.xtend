package template.RTOS.channel

import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import generator.generator
import template.Template
import utils.Global
import utils.Name
import utils.Query
import utils.Save

class channelInc implements Template{

	
	override create() { 
		 Global.model.vertexSet()
			.stream()
			.filter([v|v.hasTrait("impl::TokenizableDataBlock")])
			.forEach([
				v| Save.save(path(v) ,v.createHeader())
			]
			)		
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
		return generator.root+"/include/freertos_sdfchannel_"+Name.name(vertex)+".h"
	}
		
}
