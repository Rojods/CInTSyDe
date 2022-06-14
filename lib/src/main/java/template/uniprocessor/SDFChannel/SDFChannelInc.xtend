package template.uniprocessor.SDFChannel

import forsyde.io.java.core.Vertex
import template.templateInterface.ChannelTemplate

/**
 * without distinguish if the sdfchannel is a state variable
 * 
 */

class SDFChannelInc implements ChannelTemplate{
	Vertex sdfchannel
	override create(Vertex vertex) {
		this.sdfchannel=vertex
		
		'''
		#ifndef  «vertex.getIdentifier().toUpperCase()»_h_ 
		#define «vertex.getIdentifier().toUpperCase()»_h_ 
		
		//#define «vertex.getIdentifier().toUpperCase()»_BLOCKING 0
		
		#endif
		'''
	}
	
	override savePath() {
		return "/sdfchannel/sdfchannel_"+sdfchannel.getIdentifier()+".h"
	}
	
}