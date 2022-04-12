package strategy.c.rtos.freertos
import strategy.Strategy

import strategy.SDFCombFather
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import util.Name
import java.util.stream.Collectors
import forsyde.io.java.core.EdgeTrait
import java.util.TreeSet
import static extension util.Query.getSDFChannelName
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import util.Save
import util.Query

class SDFChannelSrcFreeRTOS   implements Strategy {
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
				v| Save.save(path(v) ,v.createSource())
			]
			)
	}
	
	override setSaveRoot(String root) {
		this.root=root
	}
	
	def String createSource(Vertex vertex) {
		var name = vertex.getIdentifier()		
		'''
		#include "../include/freertos_sdfchannel_«name.replace("/","_")».h"
		QueueHandle_t msg_queue_«name»;
		int queue_length_«name» = «Query.getBufferSize(SDFChannel.enforce(vertex))»;
		long item_size_«name» = «Query.getTokenSize(SDFChannel.enforce(vertex)) »;
		'''

	}
	private def String path(Vertex vertex){
		return root+"/source/freertos_sdfchannel_"+Name.name(vertex)+".c"
	}	
}