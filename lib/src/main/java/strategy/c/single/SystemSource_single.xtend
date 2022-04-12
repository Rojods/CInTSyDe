 package strategy.c.single

import forsyde.io.java.core.ForSyDeSystemGraph


import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import java.util.TreeMap
import java.util.stream.Collectors
import strategy.Strategy
import util.Query
import util.Save
import util.Name
import forsyde.io.java.typed.viewers.moc.sdf.SDFDelay
import java.util.ArrayList
import java.util.TreeSet
import static extension util.Query.getSDFChannelName
import static extension util.Query.getPortRate
import forsyde.io.java.core.EdgeTrait

class SystemSource_single implements Strategy{
	
	ForSyDeSystemGraph model
	String root
	String name = "default"
	new(ForSyDeSystemGraph model){
		this.model=model
	}	
	
	override create() {
		var str = createSource()
		Save.save(root+"/source/system_"+name+".c",str)
	}
	override setSaveRoot(String root) {
		this.root=root
	}
	def String createSource(){
		var sdfChannelSet = model.vertexSet()
								.stream()
								.filter([v|SDFChannel.conforms(v)])
								.map([v|SDFChannel.enforce(v)])
								.collect(Collectors.toSet())
		
		
		var sdfCombSet =model.vertexSet()
								.stream()
								.filter([v|SDFComb.conforms(v)])
								.collect(Collectors.toSet()) 
		// get the firing set
		var firingSet = new TreeMap<Integer,Vertex>();
		for(Vertex v:sdfCombSet ){
			firingSet.put(Query.getFiringSlot(SDFComb::enforce(v)),v)
		}		
		
		
		
		
		'''
			#include "../include/system_«name».h"
			void system_«name»(){
				//create internal channels
				/*
				 create sdf channels 
				 the identifier of sdf channel is the name of created channel
				*/
				«FOR channel:sdfChannelSet  SEPARATOR "\n" AFTER "\n" »
					«var channelName=Name.name(channel)»
					«var long buffersize=Query.getBufferSize(channel)»
					//the max buffer size is «buffersize»,
					//but because the channel is implemented by a circular buffer,
					// the created circular size should be «buffersize+1»
					int buffersize_«channelName» = «buffersize»;
					token_«channelName» arr_«channelName»[buffersize_«channelName»+1];
					circularFIFO_«channelName»   Channel_«channelName» = init_circularFIFO_«channelName»( arr_«channelName»,buffersize_«channelName»+1);
				«ENDFOR»
				
				//SDFDelay
				«sdfDelayHelp()»
					
				while(1){
					«FOR set:firingSet.entrySet() SEPARATOR "\n" AFTER"\n"»
						actor_«Name.name(set.getValue())»(«actorParameter(set.getValue())»);
			
					«ENDFOR»				
					
				}									
			}		
			
		'''	
	
	}
	def actorParameter( Vertex vertex){
		var name =Name.name(vertex)
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
			«FOR port: inputPorts SEPARATOR "," AFTER " " »
			«««		«var int rate = SDFComb.enforce(vertex).getPortRate(port)»
		Channel_«vertex.getSDFChannelName(port,model)»,«SDFComb.enforce(vertex).getPortRate(port)»
			«ENDFOR»
			«««	
		«IF inputPorts.size()==0 »
				«FOR port: outputPorts SEPARATOR "," AFTER " " »
					Channel_«vertex.getSDFChannelName(port,model)»,«SDFComb.enforce(vertex).getPortRate(port)»
				«ENDFOR»
			«ELSE»
				«FOR port: outputPorts SEPARATOR " " AFTER " " »
					,Channel_«vertex.getSDFChannelName(port,model)»,«SDFComb.enforce(vertex).getPortRate(port)»
				«ENDFOR»		
			«ENDIF»
			«IF inputPorts.size()==0&& outputPorts.size()==0»
				func_«name»_combinator
				«ELSE»
				,func_«name»_combinator
			«ENDIF»
		'''
	}	
	private def String sdfDelayHelp(){
		var delays = model.vertexSet().stream().filter([v|SDFDelay.conforms(v)]).collect(Collectors.toSet())
		var sdfdelays=  delays.stream().map([v|SDFDelay.enforce(v)]).collect(Collectors.toSet())
		
		'''
			«FOR delay:sdfdelays   SEPARATOR "\n" AFTER "\n==============="»
				
				«IF delay.getDelayedChannelPort(model).isPresent()»
					«var sdfchannel=delay.getDelayedChannelPort(model).get()»
					«var tokens = (delay.getProperties().get("delayedTokens").unwrap() as ArrayList<Integer>)»
					«var channelName= Name.name(sdfchannel)»
					«FOR  token :tokens SEPARATOR "\n" AFTER"\n"»
						writeToken_«channelName»(FIFO_«channelName»,(token_«channelName»)«token»);
					«ENDFOR»
				«ENDIF»
			«ENDFOR»	
		'''
	}	
	
	def setSystemName(String name){
		this.name=name
	}	

}
