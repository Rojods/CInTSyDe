package strategy.c.single

import forsyde.io.java.core.EdgeTrait
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import java.util.TreeSet
import java.util.stream.Collectors
import strategy.SDFCombFather
import strategy.Strategy
import util.Name
import util.Save

import static extension util.Query.getSDFChannelName

class SDFCombSource extends SDFCombFather implements Strategy {

	new(ForSyDeSystemGraph model){
		super(model)
	}		
	
	override create(){
		var set = model.vertexSet()
			.stream()
			.filter([v|SDFComb.conforms(v)]).collect(Collectors.toSet())
			
		for(Vertex v: set){
			Save.save(path(v),v.createSource())
		}
	
	}
	override setSaveRoot(String root) {
		this.root=root
	}	
	
	def String createSource(Vertex vertex){

		var name = Name.name(vertex)
		
//		var TreeSet<String> inputPorts =new TreeSet
//		var TreeSet<String> outputPorts = new TreeSet		
//		
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
		//println("==="+vertex.getIdentifier())
		
		var in = model.incomingEdgesOf(vertex).stream()
									.filter([edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE)])
									.map([e|e.getTargetPort().get()])
									.collect(Collectors.toSet())

		
		var TreeSet<String> inputPorts =new TreeSet(in)
		var TreeSet<String> outputPorts = new TreeSet(out)
		
		'''
			#include <stdlib.h>
			#include <stdio.h>
			#include "../include/sdfcomb_«name».h"
			
			«FOR port : inputPorts SEPARATOR " \n" AFTER "\n" »
				inline static void read_channel_«name»_«port»(const circularFIFO_«getSDFChannelName(vertex,port,model)» src_channel, const size_t num, token_«getSDFChannelName(vertex,port,model)»  dst[]){
					//#if defined SINGLE
						for(size_t i=0 ; i < num ;++i){
								if(read_circularFIFO_non_blocking_«vertex.getSDFChannelName(port,model)»(src_channel,dst+i) ==-1){
									
								//error
								//abort();
							}
					}
				}
			«ENDFOR»	
			
			«FOR port : outputPorts SEPARATOR " \n" AFTER "\n" »
				inline static void write_channel_«name»_«port»(const token_«getSDFChannelName(vertex,port,model)» src[],const size_t num,circularFIFO_«getSDFChannelName(vertex,port,model)» dst_channel){
					for(size_t i=0 ; i < num ;++i){
							if(write_circularFIFO_non_blocking_«getSDFChannelName(vertex,port,model)»(dst_channel,src[i]) ==-1){
								//error
							}
						}
				}
			«ENDFOR»	
			inline void actor_«name»(«actorParameterSignature(vertex,inputPorts,outputPorts)»){
				/*
				define array
				*/
				//array aiming to storing data from input ports
				«FOR port:inputPorts  SEPARATOR "\n" AFTER "\n"»
					token_«vertex.getSDFChannelName(port,model)» «port»[«port»_rate];
				«ENDFOR»
				//array aiming to writing data to input ports
				«FOR port :outputPorts SEPARATOR "\n" AFTER "\n"»
					token_«vertex.getSDFChannelName(port,model)» «port»[«port»_rate];
				«ENDFOR»
				«FOR port : inputPorts SEPARATOR " \n" AFTER "\n" »
					read_channel_«name»_«port»(channel_«port»,«port»_rate,«port»);
				«ENDFOR»	
				func_«name»_combinator(«funcParameter(vertex,inputPorts,outputPorts)»);	
				«FOR port : outputPorts SEPARATOR " \n" AFTER "\n" »
					write_channel_«name»_«port»(«port»,«port»_rate,channel_«port»);
				«ENDFOR»				
			}
			
			//void func_actorName_combinator(portName[], portName_rate ....)
			inline void func_«name»_combinator(«funcParameterSignature(vertex,inputPorts,outputPorts)»){
				printf("in actor «name»\n");
			
			}
			
							
		'''
	}	
		
	private def String path(Vertex vertex){
		return root+"/source/sdfcomb_"+Name.name(vertex)+".c"
	}	

}
