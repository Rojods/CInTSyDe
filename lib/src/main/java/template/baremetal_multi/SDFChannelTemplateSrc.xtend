package template.baremetal_multi


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

class SDFChannelTemplateSrc implements ChannelTemplate {
	Vertex sdfchannel
	override savePath() {
		return "/sdfchannel/sdfchannel_"+sdfchannel.getIdentifier()+".c"
	}
	override create(Vertex sdfchannel) {
		var model=Generator.model
		this.sdfchannel=sdfchannel
		
		
		var type = Query.findSDFChannelDataType(Generator.model, sdfchannel)
		var properties = sdfchannel.getProperties()
		'''	
			
			#include "../../datatype/datatype_definition.h"
			«var channelname=sdfchannel.getIdentifier()»
			#include "../../circular_fifo_lib/circular_fifo_lib.h"
			#include "sdfchannel_«channelname».h"
			#include <cheap_s.h>
			
				«IF BoundedSDFChannel.conforms(sdfchannel)»
					«var viewer = new BoundedSDFChannelViewer(sdfchannel)»
					«var maximumTokens =viewer.getMaximumTokens()»
					«IF Query.isOnOneCoreChannel(model,sdfchannel)»
					/* Channel On One Processor */
						«IF Generator.fifoType==1»
						volatile «type» buffer_«channelname»[«maximumTokens+1»];
						int channel_«channelname»_size=«maximumTokens»;
						int buffer_«channelname»_size = «maximumTokens+1»; //Because of circular fifo, the buffer_size=channel_size+1 
						circular_fifo_«type» fifo_«channelname»;
						«ENDIF»
						«IF Generator.fifoType==2»
						circular_fifo fifo_«channelname»;
						volatile «type» buffer_«channelname»[«maximumTokens+1»];
						int channel_«channelname»_size=«maximumTokens»;
						/*Because of circular fifo, the buffer_size=channel_size+1 */
						int buffer_«channelname»_size = «maximumTokens+1»;						
						«ENDIF»
					«ELSE»
					/* Channel Between Two Processors */
					 volatile cheap const fifo_admin_«channelname»=(cheap) «channelname.toUpperCase()»_ADDR;
					 volatile «type» * const fifo_data_«channelname»=(«type»  *)((cheap) «channelname.toUpperCase()»_ADDR +1);			 
					 unsigned int buffer_«channelname»_size=«Query.getBufferSize(sdfchannel)»;
«««					 unsigned int token_«channelname»_size=«Query.getTokenSize(sdfchannel)»;
					 unsigned int token_«channelname»_size= sizeof(«Query.findSDFChannelDataType(model,sdfchannel)»);
	
					«ENDIF»
				«ELSE»
					«IF Query.isOnOneCoreChannel(model,sdfchannel)»
					/* Channel On One Processor */
						«IF Generator.fifoType==1»
						volatile «type» buffer_«channelname»[2];
						unsigned int channel_«channelname»_size = 1;
						unsigned int buffer_«channelname»_size = 2; // Because of circular fifo, the buffer_size=channel_size+1 
						circular_fifo_«type» fifo_«channelname»;
						«ENDIF»
						«IF Generator.fifoType==2»
						circular_fifo fifo_«channelname»;
						volatile «type» buffer_«channelname»[2];
						int channel_«channelname»_size=1;
						/*Because of circular fifo, the buffer_size=channel_size+1 */
						int buffer_«channelname»_size = 2;						
						«ENDIF»
					«ELSE»
					/* Channel Between Two Processors */
					
 					 volatile cheap const fifo_admin_«channelname»=(cheap) «channelname.toUpperCase()»_ADDR;
 					 volatile «type» * const fifo_data_«channelname»=(«type»  *)((cheap) «channelname.toUpperCase()»_ADDR +1); 					 
 					 unsigned int buffer_«channelname»_size=1;
««« 					 unsigned int token_«channelname»_size=«Query.getTokenSize(sdfchannel)»;
 					 unsigned int token_«channelname»_size= sizeof(«Query.findSDFChannelDataType(model,sdfchannel)»);
««« 					 unsigned int token_«channelname»_size=«Query.getTokenSize(sdfchannel)»	;
««« 					 volatile «type» buffer_«channelname»[1];							
					«ENDIF»
				«ENDIF»			
		'''
	}

}
