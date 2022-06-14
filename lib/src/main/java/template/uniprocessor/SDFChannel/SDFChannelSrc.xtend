package template.uniprocessor.SDFChannel

import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.decision.sdf.BoundedSDFChannel
import forsyde.io.java.typed.viewers.decision.sdf.BoundedSDFChannelViewer
import generator.Generator
import template.templateInterface.ChannelTemplate
import utils.Query


class SDFChannelSrc implements ChannelTemplate {
	Vertex sdfchannel

	override savePath() {
		return "/sdfchannel/sdfchannel_" + this.sdfchannel.getIdentifier() + ".c"
	}

	override create(Vertex sdfchannel) {
		var model = Generator.model
		this.sdfchannel = sdfchannel
		var type = Query.findSDFChannelDataType(Generator.model, sdfchannel)
		var properties = sdfchannel.getProperties()
		'''	
			
			«var sdfname=sdfchannel.getIdentifier()»
			#include "sdfchannel_«sdfname».h"
			#include "../../circular_fifo_lib/circular_fifo_lib.h"
				«IF BoundedSDFChannel.conforms(sdfchannel)»
					«var viewer = new BoundedSDFChannelViewer(sdfchannel)»
					«var maximumTokens =viewer.getMaximumTokens()»
						«IF Generator.fifoType==1»	
						volatile «type» buffer_«sdfname»[«maximumTokens+1»];
						int channel_«sdfname»_size=«maximumTokens»;
						/*Because of circular fifo, the buffer_size=channel_size+1 */
						int buffer_«sdfname»_size = «maximumTokens+1»;
						circular_fifo_«type» fifo_«sdfname»;
						«ENDIF»
						«IF Generator.fifoType==2»
							circular_fifo fifo_«sdfname»;
							volatile «type» buffer_«sdfname»[«maximumTokens+1»];
							int channel_«sdfname»_size=«maximumTokens»;
							/*Because of circular fifo, the buffer_size=channel_size+1 */
							int buffer_«sdfname»_size = «maximumTokens+1»;
							
							«ENDIF»
			
					«ELSE»
						«IF Generator.fifoType==1»	
							volatile «type» buffer_«sdfname»[2];
							int channel_«sdfname»_size = 1;
							/*Because of circular fifo, the buffer_size=channel_size+1 */
							int buffer_«sdfname»_size = 2;
							circular_fifo_«type» fifo_«sdfname»;
							«ENDIF»
							«IF Generator.fifoType==2»
								circular_fifo fifo_«sdfname»;
								volatile «type» buffer_«sdfname»[2];
								int channel_«sdfname»_size=1;
								/*Because of circular fifo, the buffer_size=channel_size+1 */
								int buffer_«sdfname»_size = 2;
								«ENDIF»
							«ENDIF»			
		'''
	}

}
