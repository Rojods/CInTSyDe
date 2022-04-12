package strategy.c.rtos

import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import strategy.Strategy
import util.Name
import util.Save

class SDFChannelHeaderStrategy_rtos implements Strategy{
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
		#ifndef                   «tmp»_H_
		#define                   «tmp»_H_
		/*
		define token 
		*/
		typedef «IF token_size == 8»UBYTE«ELSEIF token_size==16»UWORD«ELSEIF token_size==32»ULONG«ELSEIF token_size==64»FP64«ELSE» ULONG«ENDIF» token_«name» ;					
		
		#endif		
						
		'''
	}
	private def String path(Vertex vertex){
		return root+"/include/rtos_sdfchannel_"+Name.name(vertex)+".h"
	}
		
}