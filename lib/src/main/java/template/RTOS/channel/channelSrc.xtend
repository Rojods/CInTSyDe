package template.RTOS.channel

import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import generator.generator
import template.Template
import utils.Global
import utils.Name
import utils.Query
import utils.Save

class channelSrc   implements Template {

	
	override create() {
		 Global.model.vertexSet()
			.stream()
			.filter([v|v.hasTrait("impl::TokenizableDataBlock")])
			.forEach([
				v| Save.save(path(v) ,v.createSource())
			]
			)
	}
	

	def String createSource(Vertex vertex) {
		var name = vertex.getIdentifier()		
		'''
			#include "../include/freertos_sdfchannel_«name.replace("/","_")».h"
			QueueHandle_t msg_queue_«name»;
			int queue_length_«name» = «Query.getBufferSize(vertex)»;
			long item_size_«name» = «Query.getTokenSize(vertex)»;
		'''

	}
	private def String path(Vertex vertex){
		return generator.root+"/source/freertos_sdfchannel_"+Name.name(vertex)+".c"
	}	
}
