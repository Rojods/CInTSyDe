package template.nonRTOS.subsystem

import forsyde.io.java.core.EdgeTrait
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import forsyde.io.java.typed.viewers.moc.sdf.SDFDelay
import generator.generator
import java.util.ArrayList
import java.util.TreeMap
import java.util.TreeSet
import java.util.stream.Collectors
import template.Template
import utils.Global
import utils.Name
import utils.Query
import utils.Save

import static extension utils.Query.getPortRate
import static extension utils.Query.getSDFChannelName

class subsystemSrcUniprocessor implements Template{

	new(){

	}	
	
	override create() {
		var str = createSource()
		Save.save(generator.root+"/src/subsystem.c",str)
	}

	def String createSource(){
		var sdfChannelSet = Global.model.vertexSet()
								.stream()
								.filter([v|SDFChannel.conforms(v)])
								.map([v|SDFChannel.enforce(v)])
								.collect(Collectors.toSet())
		
		
		var sdfCombSet =Global.model.vertexSet()
								.stream()
								.filter([v|SDFComb.conforms(v)])
								.collect(Collectors.toSet()) 
								
		var system_in_out = Global.model.vertexSet()
								.stream()
								.filter([v|v.hasTrait("impl::TokenizableDataBlock")])
								.filter([v|!v.hasTrait("moc::sdf::SDFChannel")])
								.collect(Collectors.toSet())
		// get the firing set
		var firingSet = new TreeMap<Integer,Vertex>();
		for(Vertex v:sdfCombSet ){
			firingSet.put(Query.getFiringSlot(SDFComb::enforce(v)),v)
		}		
		
		
		
		
		'''
			#include "../inc/subsystem.h"
				«FOR channel:sdfChannelSet  SEPARATOR "\n" AFTER "" »
					«var channelName=Name.name(channel)»
					extern circularFIFO_«channelName» channel_«channelName»;
					extern token_«channelName» arr_«channelName»[];
					extern int buffersize_«channelName»;
				«ENDFOR»
				«FOR channel:system_in_out  SEPARATOR "" AFTER "" »
					extern circularFIFO_«Name.name(channel)» channel_«Name.name(channel)»;
				«ENDFOR»
			void subsystem(){
				//create internal channels
				/*
				 create sdf channels 
				 the identifier of sdf channel is the name of created channel
				*/
				«FOR channel:sdfChannelSet  SEPARATOR "" AFTER "\n" »
					«var channelName=Name.name(channel)»
					init_circularFIFO_«channelName»(&channel_«channelName»,arr_«channelName»,buffersize_«channelName»);
				«ENDFOR»
				
				//SDFDelay
				«subsystemHelp.sdfDelayHelpB(sdfChannelSet)»
					
				while(1){
					/*round robin*/
					«FOR set:firingSet.entrySet() SEPARATOR "\n" AFTER"\n"»
						actor_«Name.name(set.getValue())»(«subsystemHelp.actorParameter(set.getValue())»);
					«ENDFOR»				
					
				}									
			}		
			
		'''	
	
	}
	
}