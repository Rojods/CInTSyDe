package template.uniprocessor.SDFChannel
import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.decision.sdf.BoundedSDFChannel
import forsyde.io.java.typed.viewers.decision.sdf.BoundedSDFChannelViewer
import generator.Generator
import template.templateInterface.ChannelTemplate
import utils.Query

/**
 * without distinguish if the sdfchannel is a state variable
 * 
 */
@FileTypeAnno(type=FileType.C_SOURCE)
class SDFChannelInc implements ChannelTemplate{
	Vertex sdfchannel
	override create(Vertex vertex) {
		this.sdfchannel=vertex
		
		'''
		#ifndef  «vertex.getIdentifier().toUpperCase()»_h_ 
		#define «vertex.getIdentifier().toUpperCase()»_h_ 
		
		#define «vertex.getIdentifier().toUpperCase()»_BLOCKING 0
		
		#endif
		'''
	}
	
	override savePath() {
		return "/sdfchannel/sdfchannel_"+sdfchannel.getIdentifier()+".h"
	}
	
}