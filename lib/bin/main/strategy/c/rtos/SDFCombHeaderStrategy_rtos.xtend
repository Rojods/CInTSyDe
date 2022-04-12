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
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb

class SDFCombHeaderStrategy_rtos  extends SDFCombFather implements Strategy{
	
	new(ForSyDeSystemGraph model) {
		super(model)
	}
	
	override create() {
		var set = model.vertexSet()
			.stream()
			.filter([v|SDFComb.conforms(v)]).collect(Collectors.toSet())
			
		for(Vertex v: set){
			Save.save(path(v),v.createHeader())
		}	
	}
	
	override setSaveRoot(String root) {
		this.root=root
	}
	
	def String createHeader(Vertex vertex){
		var name = Name.name(vertex)
		var tmp = name.toUpperCase()
		tmp = tmp+"_H_"	
		
		var sdfchannels=model.vertexSet()
							 .stream()
							 .filter([v|SDFChannel::conforms(v)])
							 .map([v|SDFChannel::enforce(v)])
							 .collect(Collectors.toSet())	
		//find input ports and output ports
		var out= model.outgoingEdgesOf(vertex).stream()
									.filter([edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE)])
									.map([e|e.getSourcePort().get()])
									.collect(Collectors.toSet())
		
		
		var in = model.incomingEdgesOf(vertex).stream()
									.filter([edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE)])
									.map([e|e.getTargetPort().get()])
									.collect(Collectors.toSet())

		
		var TreeSet<String> inputPorts =new TreeSet(in)
		var TreeSet<String> outputPorts = new TreeSet(out)	
		
		'''
			#ifndef  «tmp»
			#define «tmp»
			#include "config.h"
			«FOR ch:sdfchannels SEPARATOR " \n" AFTER "\n" »
				#include "../include/rtos_sdfchannel_«Name.name(ch)».h"
			«ENDFOR»
			void task_«name»(void* pdata);
			
			void func_«name»_combinator(«funcParameterSignature(vertex, inputPorts,outputPorts)»);
			
			«FOR port :inputPorts SEPARATOR " \n" AFTER "\n" »
			void read_channel_«name»_«port»(OS_EVENT *  src_msg_queue, size_t num, token_«vertex.getSDFChannelName(port,model)»  dst[]);
			«ENDFOR»
			
			«FOR port : outputPorts SEPARATOR " \n" AFTER "\n" »
			void write_channel_«name»_«port»(token_«vertex.getSDFChannelName(port,model)» src[],size_t num,OS_EVENT *  dst_msg_queue);
			«ENDFOR»
			void task_«name»_callback(void *ptmr, void *callback_arg);
			#endif		
		'''			
		
			
	}
	private def String path(Vertex vertex){
		return root+"/include/rtos_sdfcomb_"+Name.name(vertex)+".h"
	}	
}
