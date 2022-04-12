package strategy.c.single

import forsyde.io.java.core.EdgeTrait
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import java.util.TreeSet
import java.util.stream.Collectors
import strategy.Strategy
import util.Name
import util.Save

import static extension util.Query.getSDFChannelName
import strategy.SDFCombFather

class SDFCombHeader  extends SDFCombFather  implements Strategy{
	
	new(ForSyDeSystemGraph model) {
		super(model)
	}
	override create(){
		model.vertexSet()
			.stream()
			.filter([v|SDFComb.conforms(v)])
			.forEach([
				v|Save.save(path(v),v.createHeader())
			])
	}
	
	override setSaveRoot(String root) {
		this.root=root
	}
	
	def String createHeader(Vertex vertex) {
		var name = Name.name(vertex)
		var tmp = name.toUpperCase()
		tmp = tmp+"_H_"
		
		var sdfchannels=model.vertexSet()
							 .stream()
							 .filter([v|SDFChannel::conforms(v)])
							 .map([v|SDFChannel::enforce(v)])
							 .collect(Collectors.toSet())
		
//		var TreeSet<String> inputPorts =new TreeSet
//		var TreeSet<String> outputPorts = new TreeSet		
//		if(SDFComb.enforce(vertex).getConsumption()!==null){
//			inputPorts.addAll(SDFComb.enforce(vertex).getConsumption().keySet())
//		}
//		
//		if(SDFComb.enforce(vertex).getProduction()!==null){
//			outputPorts.addAll(SDFComb.enforce(vertex).getProduction().keySet())
//		}	


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
			«FOR ch:sdfchannels SEPARATOR " \n" AFTER "\n" »
				#include "../include/sdfchannel_«Name.name(ch)».h"
			«ENDFOR»
			void actor_«name»(«actorParameterSignature(vertex, inputPorts,outputPorts)»);
			
			void func_«name»_combinator(«funcParameterSignature(vertex, inputPorts,outputPorts)»);
			
			«FOR port :inputPorts SEPARATOR " \n" AFTER "\n" »
				//void read_channel_«name»_«port»(channel_«vertex.getSDFChannelName(port,model)» src_channel, size_t num, token_«vertex.getSDFChannelName(port,model)»  dst[]);
			«ENDFOR»
			
			«FOR port : outputPorts SEPARATOR " \n" AFTER "\n" »
				//void write_channel_«name»_«port»(token_«vertex.getSDFChannelName(port,model)» src[],size_t num,channel_«vertex.getSDFChannelName(port,model)» dst_channel);
			«ENDFOR»
			#endif		
		'''	
	}
	

	
	
	private def String path(Vertex vertex){
		return root+"/include/sdfcomb_"+Name.name(vertex)+".h"
	}
	
}
