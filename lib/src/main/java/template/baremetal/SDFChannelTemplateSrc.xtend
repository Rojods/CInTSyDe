package template.baremetal

import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexAcessor
import forsyde.io.java.core.VertexAcessor.VertexPortDirection
import forsyde.io.java.core.VertexTrait
import generator.Generator
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.Set
import template.templateInterface.ActorTemplate
import utils.Name
import utils.Query
import java.util.stream.Collectors
import forsyde.io.java.core.EdgeInfo
import forsyde.io.java.core.EdgeTrait
import template.templateInterface.ChannelTemplate
/**
 * without distinguish if the sdfchannel is a state variable
 * 
 */
@FileTypeAnno(type=FileType.C_SOURCE)
class SDFChannelTemplateSrc implements ChannelTemplate {

	override create(Vertex sdfchannel) {
		var properties = sdfchannel.getProperties()
		'''	
			«var sdfname=sdfchannel.getIdentifier()»
			#include "../inc/circular_fifo_lib.h"
			«IF sdfchannel.hasTrait("decision::sdf::BoundedSDFChannel")»
				«var maximumTokens = properties.get("maximumTokens").unwrap() as Integer»
				type buffer_«sdfname»[«maximumTokens+1»];
				int buffer_«sdfname»_size = «maximumTokens+1»;
				circular_fifo_type fifo_«sdfname»;
				spinlock spinlock_«sdfname»={.flag=0};
			«ELSE»
				type buffer_«sdfname»[2];
				int buffer_«sdfname»_size = 2;
				circular_fifo_type fifo_«sdfname»;
				spinlock spinlock_«sdfname»={.flag=0};			
«««				«var numOfInitialTokens = properties.get("numOfInitialTokens").unwrap() as Integer»
«««				type «sdfname»[«numOfInitialTokens»]={0};
			«ENDIF»
			
		'''
	}

//	def String help(Vertex sdfchannel) {
//		var String ret = ""
//		sdfchannel.getProperties().get("__initialTokenValues_ordering__").unwrap() as HashMap<String, Integer>
//		'''
//		'''
//	}

}
