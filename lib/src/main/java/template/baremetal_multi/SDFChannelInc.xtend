package template.baremetal_multi


import forsyde.io.java.core.Vertex
import generator.Generator
import template.templateInterface.ChannelTemplate
import utils.Query

/**
 * without distinguish if the sdfchannel is a state variable
 * 
 */

class SDFChannelInc  implements ChannelTemplate{
	Vertex sdfchannel
	override create(Vertex vertex) {
		this.sdfchannel=vertex
		'''
		#ifndef  «vertex.getIdentifier().toUpperCase()»_H_ 
		#define «vertex.getIdentifier().toUpperCase()»_H_ 
		
		«IF  Query.isOnOneCoreChannel(Generator.model,vertex)»
		//#define «vertex.getIdentifier().toUpperCase()»_BLOCKING 0
		«ELSE»
		#define «vertex.getIdentifier().toUpperCase()»_ADDR  0x80020000
		«ENDIF»		
		#endif
		'''
		
 
	}
	
	override savePath() {
		return "/sdfchannel/sdfchannel_"+sdfchannel.getIdentifier()+".h"
	}

	

}