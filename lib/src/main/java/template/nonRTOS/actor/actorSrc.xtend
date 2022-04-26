package template.nonRTOS.actor

import forsyde.io.java.core.EdgeTrait
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import generator.generator
import java.util.TreeSet
import java.util.stream.Collectors
import template.Template
import utils.Global
import utils.Name
import utils.Save

import static extension utils.Query.getChannelName
import static extension utils.Query.getSDFChannelName

class actorSrc implements Template {
	override create() {
		var set = Global.model.vertexSet()
			.stream()
			.filter([v|SDFComb.conforms(v)]).collect(Collectors.toSet())
			
		for(Vertex v: set){
			Save.save(path(v),v.src())
		}
	}	
	
	private def String path(Vertex vertex){
		return generator.root+"/src/sdfcomb_"+Name.name(vertex)+".c"
	}	
	
	
	def String src(Vertex vertex) {

		var name = Name.name(vertex)	
		
		var out= Global.model.outgoingEdgesOf(vertex).stream()
									.filter([
										edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE) 
											||	edgeinfo.hasTrait(EdgeTrait.IMPL_DATAMOVEMENT)
									])
									.map([e|e.getSourcePort().get()])
									.collect(Collectors.toSet())
		
		
		var in = Global.model.incomingEdgesOf(vertex).stream()
									.filter([
										edgeinfo|edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE)
										||	edgeinfo.hasTrait(EdgeTrait.IMPL_DATAMOVEMENT)
									]
											
									)
									.map([e|e.getTargetPort().get()])
									.collect(Collectors.toSet())	
		
		var TreeSet<String> inputPorts =new TreeSet(in)
		var TreeSet<String> outputPorts = new TreeSet(out)
		'''
			#include <stdlib.h>
			#include <stdio.h>
			#include "../inc/sdfcomb_«name».h"
			#include "../inc/config.h"
			#include "../inc/spinlock.h"
			«FOR port : inputPorts SEPARATOR "" AFTER "" »
			extern spinlock spinlock_«getChannelName(vertex,port,Global.model)»;
			«ENDFOR»
			«FOR port : outputPorts SEPARATOR "" AFTER "" »
			extern spinlock spinlock_«getChannelName(vertex,port,Global.model)»;
			«ENDFOR»			
			
			«FOR port : inputPorts SEPARATOR "" AFTER "\n" »
				inline static void read_channel_«name»_«port»(circularFIFO_«getSDFChannelName(vertex,port,Global.model)»* src_channel_ptr, const size_t num, token_«getSDFChannelName(vertex,port,Global.model)»  dst[]){
					//#if defined SINGLE
						for(size_t i=0 ; i < num ;++i){
							#if «getSDFChannelName(vertex,port,Global.model).toUpperCase()»_NONBLOCKING==1
								if(read_circularFIFO_non_blocking_«vertex.getSDFChannelName(port,Global.model)»(src_channel_ptr,dst+i) ==-1){
							#else
								if(read_circularFIFO_blocking_«vertex.getSDFChannelName(port,Global.model)»(src_channel_ptr,dst+i,&spinlock_«vertex.getSDFChannelName(port,Global.model)») ==-1){
							#endif		
								//error
								//abort();
							}
					}
				}
			«ENDFOR»	
			
			«FOR port : outputPorts SEPARATOR "" AFTER "\n" »
				inline static void write_channel_«name»_«port»(token_«getSDFChannelName(vertex,port,Global.model)» src[],const size_t num,circularFIFO_«getSDFChannelName(vertex,port,Global.model)»* dst_channel_ptr){
					for(size_t i=0 ; i < num ;++i){
						#if «getSDFChannelName(vertex,port,Global.model).toUpperCase()»_NONBLOCKING==1
							if(write_circularFIFO_non_blocking_«getSDFChannelName(vertex,port,Global.model)»(dst_channel_ptr,src[i]) ==-1){
						#else
							if(write_circularFIFO_blocking_«getSDFChannelName(vertex,port,Global.model)»(dst_channel_ptr,src[i],&spinlock_«getSDFChannelName(vertex,port,Global.model)») ==-1){
						#endif
								//error
							}
						}
				}
			«ENDFOR»
			
			inline static void combinator(«actorhelp.funcParameterSignature(vertex,inputPorts,outputPorts)»){
				printf("in actor «name»\n");
			
			}
				
			inline void actor_«name»(«actorhelp.actorParameterSignature(vertex,inputPorts,outputPorts)»){
			
				«FOR port:inputPorts  SEPARATOR "" AFTER ""»
					token_«vertex.getChannelName(port,Global.model)» «port»[«port»_rate];
				«ENDFOR»
				«FOR port :outputPorts SEPARATOR "" AFTER ""»
					token_«vertex.getChannelName(port,Global.model)» «port»[«port»_rate];
				«ENDFOR»
				«FOR port : inputPorts SEPARATOR " \n" AFTER "\n" »
					read_channel_«name»_«port»(channel_«port»_ptr,«port»_rate,«port»);
				«ENDFOR»
					
				combinator(«actorhelp.funcParameter(vertex,inputPorts,outputPorts)»);	
				«FOR port : outputPorts SEPARATOR " \n" AFTER "\n" »
					write_channel_«name»_«port»(«port»,«port»_rate,channel_«port»_ptr);
				«ENDFOR»				
			}
			
			
			
							
		'''		
	}
	

	
}
