package strategy.c.multi

import strategy.Strategy
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import java.util.Set
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import util.Name

import java.util.stream.Collectors
import util.Save
import util.Query
import forsyde.io.java.core.Vertex
import java.util.Optional
import java.util.TreeSet
import forsyde.io.java.core.VertexAcessor
import java.util.TreeMap
import java.util.ArrayList
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.platform.GenericProcessingModule
import forsyde.io.java.core.VertexTrait
import forsyde.io.java.core.VertexAcessor.VertexPortDirection
import static extension util.Query.getSDFChannelName
import static extension util.Query.getPortRate
import forsyde.io.java.core.EdgeTrait

class MainSourceStrategy_multi  implements Strategy{
	ForSyDeSystemGraph model
	String root
	//String system = "default"
	new(ForSyDeSystemGraph model){
		this.model=model
		//this.root = save
	}
	override create() {
		//Save.save(root+"/main_"+system+".c",createSource())
		createManyTileSource
	}
	
	override setSaveRoot(String root) {
		this.root=root
	}	
	def  createManyTileSource(){
		var tiles = model.vertexSet().stream().filter([v|GenericProcessingModule.conforms(v)]).collect(Collectors.toSet())
		for(Vertex t : tiles){
			var slotOrders = getActorOrder(t)
			slotOrders.stream().filter([n|n.isPresent()]).forEach([n|println(n.get().getPorts())])
			var str = generate(slotOrders)
			Save.save(this.root+"/main_"+Name.name(t)+".c",str)
			
		}		
	}
	//for many processor
	def String generate(ArrayList<Optional<Vertex>> actors){		
		'''
		#include<stdlib.h>
		#include <stdio.h>		
		
		«FOR actor:actors SEPARATOR "" AFTER "\n"»
		«IF actor.isPresent() »
		#include "./include/sdfcomb_«Name.name(actor.get())».h"
		«ENDIF»
		«ENDFOR»
		//include channel.h
		«FOR actor:actors SEPARATOR "" AFTER "\n"»
			«IF actor.isPresent() »
				«FOR port:actor.get().getPorts()»
					«IF port!="combinator"&&port!="combFunction"»
				#include "./include/sdfchannel_«Query.getSDFChannelName(actor.get(),port,model)».h"
					«ENDIF»
				«ENDFOR»
			«ENDIF»
		«ENDFOR»		
		
		
		//create FIFO 	
		«FOR actor:actors SEPARATOR "\n" AFTER "\n"»
			«IF actor.isPresent() »
				«FOR port:actor.get().getPorts()»
					«IF port!="combinator"&&port!="combFunction"»
						«var channel = VertexAcessor.getNamedPort(model,actor.get(),port,VertexTrait.MOC_SDF_SDFCHANNEL,VertexPortDirection.BIDIRECTIONAL)»
						«IF channel.isPresent()»
							«var channelName=Name.name(channel.get())»
							/*
								Channel: «channelName»
							*/
							int buffersize_«channelName»=«Query.getBufferSize(SDFChannel.enforce(channel.get()))»;
							token_«channelName» arr_«channelName»[buffersize_«channelName»+1];
							channel_«channelName»   Channel_«channelName» = create_channel_«channelName»( arr_«channelName»,buffersize_«channelName»+1);				
						«ENDIF»
					«ENDIF»
				«ENDFOR»
			«ENDIF»
		«ENDFOR»		
		int main(){
			
			while(1){
			«FOR actor:actors SEPARATOR "" AFTER "\n"»
			«IF actor.isPresent() »
			actor_«Name.name(actor.get())»(«actorParameter(actor.get())»);
			«ENDIF»
			«ENDFOR»	
			}
			
			return 0;
		}
		
		
		'''
	}



	def ArrayList<Optional<Vertex>> getActorOrder(Vertex tile){
		var order = VertexAcessor.getNamedPort(model,tile,"contained",VertexTrait.PLATFORM_RUNTIME_TIMETRIGGEREDSCHEDULER,VertexPortDirection.OUTGOING)
		
		
		var Vertex scheduler;
		if(order.isPresent()){
			scheduler = order.get()
			//ordered slot port name, ascending
			var  TreeSet<String> slots= new TreeSet (scheduler.getPorts())
			
			slots.remove("contained")
			
			var ArrayList<Optional<Vertex>> actors = new ArrayList<Optional<Vertex>>(slots.size())
			
			var iter = slots.iterator()
			while(iter.hasNext()){
				var String slotPortName= iter.next()
				var optional_actor=VertexAcessor.getNamedPort(model,scheduler,slotPortName,VertexTrait.MOC_SDF_SDFCOMB,VertexPortDirection.OUTGOING)
				actors.add(optional_actor)
			}
			
			return actors			
		}else{
			//processing module does not have scheduler
			return null
		}
		
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
}