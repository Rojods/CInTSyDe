package template.baremetal_multi

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
class SDFChannelTemplateSrc implements ChannelTemplate {

	override create(Vertex sdfchannel) {
		var model=Generator.model
		var type = Query.findSDFChannelDataType(Generator.model, sdfchannel)
		var properties = sdfchannel.getProperties()
		'''	
			#include "../inc/config.h"
			#include "../inc/spinlock.h"
			#include "../inc/datatype_definition.h"
			«var channelname=sdfchannel.getIdentifier()»
			#include "../inc/circular_fifo_lib.h"
				«IF BoundedSDFChannel.conforms(sdfchannel)»
					«var viewer = new BoundedSDFChannelViewer(sdfchannel)»
					«var maximumTokens =viewer.getMaximumTokens()»
					«IF Query.isOnOneCoreChannel(model,sdfchannel)»
					/* Channel On One Processor */
					volatile «type» buffer_«channelname»[«maximumTokens+1»];
					int channel_«channelname»_size=«maximumTokens»;
					int buffer_«channelname»_size = «maximumTokens+1»; //Because of circular fifo, the buffer_size=channel_size+1 
					circular_fifo_«type» fifo_«channelname»;
					spinlock spinlock_«channelname»={.flag=0};
					«ELSE»
					/* Channel Between Two Processors */
					 volatile cheap const fifo_admin_«channelname»;
					 volatile «type» * const fifo_data_«channelname»;
					// volatile token_t *fifo_ptrs[«Query.getBufferSize(sdfchannel)»];				 
					 unsigned int buffer_«channelname»_size=«Query.getBufferSize(sdfchannel)»;
					 unsigned int token_«channelname»_size=«Query.getTokenSize(sdfchannel)»	;
««« 					 volatile «type» buffer_«channelname»[«maximumTokens»];			
					«ENDIF»
				«ELSE»
					«IF Query.isOnOneCoreChannel(model,sdfchannel)»
					/* Channel On One Processor */
					volatile «type» buffer_«channelname»[2];
					unsigned int channel_«channelname»_size = 1;
					unsigned int buffer_«channelname»_size = 2; // Because of circular fifo, the buffer_size=channel_size+1 
					circular_fifo_«type» fifo_«channelname»;
					spinlock spinlock_«channelname»={.flag=0};	
					«ELSE»
					/* Channel Between Two Processors */
 					 volatile cheap const fifo_admin_«channelname»;
 					 volatile «type» * const fifo_data_«channelname»;
 					//volatile token_t *fifo_ptrs[1];	 					 
 					 unsigned int buffer_«channelname»_size=1;
 					 unsigned int token_«channelname»_size=«Query.getTokenSize(sdfchannel)»	;
««« 					 volatile «type» buffer_«channelname»[1];							
					«ENDIF»
				«ENDIF»			
		'''
	}

}
