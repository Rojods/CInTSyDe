package strategy.c.rtos
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

class SDFChannelSourceStrategy_rtos   implements Strategy {
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
		#include "../include/rtos_sdfchannel_«name.replace("/","_")».h"
		OS_EVENT* msg_queue_«name»;
		
		int buffersize_«name» = «Query.getBufferSize(SDFChannel.enforce(vertex))»;
		void* msg_arr_«name»[buffersize_«name»];
		
		'''

	}
	private def String path(Vertex vertex){
		return root+"/source/rtos_sdfchannel_"+Name.name(vertex)+".c"
	}	
}